/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.server.components;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tech.bitstwinkle.jelly.commons.asserts.RequestAssert;
import tech.bitstwinkle.jelly.commons.errors.JellyException;
import tech.bitstwinkle.jelly.commons.ids.JellyIds;
import tech.bitstwinkle.jelly.idfactory.JellyIdGenerator;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.AccountBillStatusEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundDirectionEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundLockStatusEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.errors.AccountErrorEnum;
import tech.bitstwinkle.jelly.jubao.ac.server.IdGeneratorConst.ACCOUNT_BILLID;
import tech.bitstwinkle.jelly.jubao.ac.server.actions.LockAction;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountBillEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountBillEntityRepository;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountEntityRepository;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.LockSheetEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.LockSheetEntityRepository;
import tech.bitstwinkle.jelly.platform.clock.JellyClock;
import tech.bitstwinkle.jelly.platform.tx.JellyTxTemplate;

/**
 * 资金锁
 * @author suuyoo.wg on 2019/10/8
 */
@Component
public class FundLockComponent {

  private static final Logger LOGGER = LoggerFactory.getLogger(FundLockComponent.class);

  @Autowired
  private JellyClock jellyClock;

  @Resource
  @Qualifier(ACCOUNT_BILLID.BEAN)
  private JellyIdGenerator billidGenerator;

  @Autowired
  private JellyTxTemplate jellyTx;

  @Autowired
  private AccountEntityRepository accountEntityRepository;

  @Autowired
  private LockSheetEntityRepository lockSheetEntityRepository;

  @Autowired
  private AccountBillEntityRepository accountBillEntityRepository;

  @Autowired
  private AccountComponent accountComponent;

  @Autowired
  private BillComponent billComponent;

  /**
   * 执行资金锁定
   *
   * @param lockAction
   * @return
   */
  public LockSheetEntity lock(LockAction lockAction) {

    LOGGER.info("lock: {}", lockAction);

    RequestAssert.notNull(lockAction, "lockAction");

    /**
     * 幂等判断
     */
    boolean idempotentCheck = lockSheetEntityRepository.existsByIdempotentId(lockAction.getIdempotentId());
    if(idempotentCheck){
      LOGGER.warn("[idempotent]: {}, [ignore]", lockAction);
      return lockSheetEntityRepository.getByIdempotentId(lockAction.getIdempotentId());
    }

    AccountEntity accountEntity = accountComponent.loadAccountEntity(lockAction.getAccid());
    accountComponent.checkAccount(accountEntity);

    BigDecimal lockAmount = lockAction.getAmount();

    /**
     * 如果为转出，需要判定余额是否足够
     */
    if (lockAction.getDirection() == FundDirectionEnum.OUT) {
      BigDecimal availableBalance = accountEntity.calcAvailableBalance();
      if (availableBalance.compareTo(lockAmount) < 0) {
        throw new JellyException(
            AccountErrorEnum.BALANCE_NOT_ENOUGH.getError(lockAction.getAccid()));
      }
    }

    /**
     * 执行金额变动
     */
    BigDecimal newSystemAmount = accountEntity.getSystemAmount();
    switch (lockAction.getDirection()) {
      case IN:
        newSystemAmount = newSystemAmount.subtract(lockAmount);
        break;
      case OUT:
        newSystemAmount = newSystemAmount.add(lockAmount);
        break;
    }
    accountEntity.setSystemAmount(newSystemAmount);

    /**
     * 记录账单
     */
    AccountBillEntity billEntity = new AccountBillEntity();

    Date billDt = jellyClock.now();
    String billid = billidGenerator.generateId();

    billEntity.setIdempotentId(lockAction.getIdempotentId());
    billEntity.setBillid(billid);
    billEntity.setStatus(AccountBillStatusEnum.EXECUTING);
    billEntity.setBillDt(billDt);
    billEntity.setAmount(lockAmount);
    billEntity.setAfterBalance(accountEntity.calcAvailableBalance());
    billEntity.setAccid(lockAction.getAccid());
    billEntity.setDirection(lockAction.getDirection());
    billEntity.setFundChannel(lockAction.getChannel());
    billEntity.setBizCode(lockAction.getBizCode());
    billEntity.setBizId(lockAction.getBizId());
    billEntity.setMemo(lockAction.getMemo());
    billEntity.setBizParas(lockAction.getBizParas());

    /**
     * 注册锁定单
     */
    LockSheetEntity lockSheetEntity = new LockSheetEntity();
    lockSheetEntity.setIdempotentId(lockAction.getIdempotentId());
    String lockSheetId = JellyIds.uniqueId();
    lockSheetEntity.setSheetId(lockSheetId);
    lockSheetEntity.setBillid(billid);
    lockSheetEntity.setStatus(FundLockStatusEnum.LOCKING);
    lockSheetEntity.setAmount(lockAmount);
    lockSheetEntity.setAccid(lockAction.getAccid());
    lockSheetEntity.setDirection(lockAction.getDirection());
    lockSheetEntity.setBizCode(lockAction.getBizCode());
    lockSheetEntity.setBizId(lockAction.getBizId());
    lockSheetEntity.setMemo(lockAction.getMemo());
    lockSheetEntity.setLockDt(billDt);

    jellyTx.execute(status -> {
      accountEntityRepository.save(accountEntity);
      accountBillEntityRepository.save(billEntity);
      lockSheetEntityRepository.save(lockSheetEntity);
      return true;
    });

    return lockSheetEntity;
  }

  /**
   * 执行锁定的资金任务推进
   */
  public AccountBillEntity lockAdvance(String lockSheetId, FundLockStatusEnum lockStatus) {
    LOGGER.info("lock advance: {} to {}", lockSheetId, lockStatus);

    RequestAssert.notBlank(lockSheetId, "lockSheetId");
    RequestAssert.notNull(lockStatus, "lockStatus");
    RequestAssert.isTrue(
        lockStatus == FundLockStatusEnum.EXECUTED || lockStatus == FundLockStatusEnum.CANCELED,
        "status must be EXECUTED OR CANCELED");

    LockSheetEntity lockSheetEntity = loadLockSheetEntity(lockSheetId);
    AccountBillEntity billEntity = billComponent.loadBillEntity(lockSheetEntity.getBillid());

    /**
     * 判定单据状态是否已经推进到期望的状态
     */
    if (lockSheetEntity.getStatus() == lockStatus) {
      LOGGER.warn("status has been advanced. ignore this lock advance: {}", lockSheetEntity);
      return billEntity;
    }

    /**
     * 判定单据状态是否是LOCKING
     */
    if (lockSheetEntity.getStatus() != FundLockStatusEnum.LOCKING) {
      LOGGER.error("lock status is invalid: {}", lockSheetEntity);
      throw new JellyException(AccountErrorEnum.LOCK_SHEET_STATUS_INVALID
          .getError(lockSheetId, lockSheetEntity.getStatus()));
    }

    checkConsistency(lockSheetEntity, billEntity);

    AccountEntity accountEntity = accountComponent.loadAccountEntity(lockSheetEntity.getAccid());

    /**
     * 执行推进
     */
    BigDecimal lockAmount = lockSheetEntity.getAmount();
    BigDecimal newSystemAmount = accountEntity.getSystemAmount();
    BigDecimal newBalance = accountEntity.getBalance();
    AccountBillStatusEnum newBillStatus = billEntity.getStatus();
    switch (lockStatus) {
      case EXECUTED: {
        newBillStatus = AccountBillStatusEnum.FINISHED;
        switch (lockSheetEntity.getDirection()) {
          case IN:
            newSystemAmount = newSystemAmount.subtract(lockAmount);
            newBalance = newBalance.add(lockAmount);
            break;
          case OUT:
            newSystemAmount = newSystemAmount.add(lockAmount);
            newBalance = newBalance.subtract(lockAmount);
            break;
        }
      }
      break;
      case CANCELED: {
        newBillStatus = AccountBillStatusEnum.CANCELED;
        switch (lockSheetEntity.getDirection()) {
          case IN:
            newSystemAmount = newSystemAmount.add(lockSheetEntity.getAmount());
            break;
          case OUT:
            newSystemAmount = newSystemAmount.subtract(lockSheetEntity.getAmount());
            break;
        }
      }
      break;
    }

    LOGGER.info("change account {}: systemAmount:{}, balance:{}", lockSheetEntity, newSystemAmount,
        newBalance);

    accountEntity.setSystemAmount(newSystemAmount);
    accountEntity.setBalance(newBalance);

    billEntity.setStatus(newBillStatus);

    lockSheetEntity.setStatus(lockStatus);
    lockSheetEntity.setLockAdvDt(jellyClock.now());

    jellyTx.execute(status -> {
      accountEntityRepository.save(accountEntity);
      accountBillEntityRepository.save(billEntity);
      lockSheetEntityRepository.save(lockSheetEntity);
      return true;
    });

    LOGGER.info("lock advance: {} to {} [OK]", lockSheetId, lockStatus);

    return billEntity;

  }

  /**
   * 装载lockSheetId，若不存在则抛出异常
   *
   * @param lockSheetId
   * @return
   */
  public LockSheetEntity loadLockSheetEntity(String lockSheetId) {
    Optional<LockSheetEntity> optional = lockSheetEntityRepository.findById(lockSheetId);
    if (!optional.isPresent()) {
      throw new JellyException(AccountErrorEnum.LOCK_SHEET_MISS.getError(lockSheetId));
    }
    return optional.get();
  }

  /**
   * 冗余判断，判断锁定单和账单信息一致性
   *
   * @param lock
   * @param bill
   */
  private void checkConsistency(LockSheetEntity lock, AccountBillEntity bill) {
    boolean consistency = true;
    consistency &= StringUtils.equals(lock.getAccid(), bill.getAccid());
    consistency &= lock.getAmount().compareTo(bill.getAmount()) == 0;
    consistency &= StringUtils.equals(lock.getBizCode(), bill.getBizCode());
    consistency &= StringUtils.equals(lock.getBizId(), bill.getBizId());
    consistency &= lock.getDirection() == bill.getDirection();
    consistency &= bill.getStatus() == AccountBillStatusEnum.EXECUTING;

    if (!consistency) {
      LOGGER.error("lock and bill not consistency: {} vs {}", lock, bill);
      throw new JellyException(
          AccountErrorEnum.LOCK_BILL_NOT_CONSISTENCY.getError(lock.getSheetId(), bill.getBillid()));
    }
  }

}
