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
import javax.annotation.Resource;
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
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundChannelEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundDirectionEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.errors.AccountErrorEnum;
import tech.bitstwinkle.jelly.jubao.ac.server.IdGeneratorConst.ACCOUNT_BILLID;
import tech.bitstwinkle.jelly.jubao.ac.server.actions.TransferAction;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountBillEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountBillEntityRepository;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountEntityRepository;
import tech.bitstwinkle.jelly.platform.clock.JellyClock;
import tech.bitstwinkle.jelly.platform.tx.JellyTxTemplate;

/**
 * 账户转账
 * @author suuyoo.wg on 2019/10/8
 */
@Component
public class TransferComponent {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransferComponent.class);

  @Autowired
  private JellyClock jellyClock;

  @Autowired
  private AccountComponent accountComponent;

  @Resource
  @Qualifier(ACCOUNT_BILLID.BEAN)
  private JellyIdGenerator billidGenerator;

  @Autowired
  private JellyTxTemplate jellyTx;

  @Autowired
  private AccountEntityRepository accountEntityRepository;

  @Autowired
  private AccountBillEntityRepository accountBillEntityRepository;


  /**
   * 内部账户转账
   * @param transferAction
   * @return 返回SRC的BILL单
   */
  public AccountBillEntity transfer(TransferAction transferAction) {

    LOGGER.info("transfer: {}", transferAction);

    RequestAssert.notNull(transferAction, "transferAction");

    /**
     * 幂等判断
     */
    boolean idempotentCheck = accountBillEntityRepository.existsByIdempotentId(transferAction.getIdempotentId());
    if(idempotentCheck){
      LOGGER.warn("[idempotent]: {}, [ignore]", transferAction);
      return accountBillEntityRepository.getByIdempotentId(transferAction.getIdempotentId());
    }

    BigDecimal transferAmount = transferAction.getAmount();

    AccountEntity srcAccountEntity = accountComponent
        .loadAccountEntity(transferAction.getSrcAccid());
    accountComponent.checkAccount(srcAccountEntity);

    /**
     * 判断可用余额是否足够
     */
    BigDecimal srcAvailableBalance = srcAccountEntity.calcAvailableBalance();
    if (srcAvailableBalance.compareTo(transferAmount) < 0) {
      throw new JellyException(
          AccountErrorEnum.BALANCE_NOT_ENOUGH.getError(srcAvailableBalance, transferAmount));
    }

    AccountEntity destAccountEntity = accountComponent
        .loadAccountEntity(transferAction.getDestAccid());
    accountComponent.checkAccount(destAccountEntity);

    BigDecimal srcNewBalance = srcAccountEntity.getBalance().subtract(transferAmount);
    BigDecimal destNewBalance = destAccountEntity.getBalance().add(transferAmount);

    Date billDt = jellyClock.now();
    String srcBillid = billidGenerator.generateId();
    String destBillid = billidGenerator.generateId();

    AccountBillEntity srcBillEntity = new AccountBillEntity();
    AccountBillEntity destBillEntity = new AccountBillEntity();

    srcBillEntity.setIdempotentId(transferAction.getIdempotentId());
    srcBillEntity.setBillid(srcBillid);
    srcBillEntity.setAccid(srcAccountEntity.getAccid());
    srcBillEntity.setDirection(FundDirectionEnum.OUT);
    srcBillEntity.setFundChannel(FundChannelEnum.INNER);
    srcBillEntity.setAmount(transferAmount);
    srcBillEntity.setBizCode(transferAction.getBizCode());
    srcBillEntity.setBizId(transferAction.getBizId());
    srcBillEntity.setBillDt(billDt);
    srcBillEntity.setMemo(transferAction.getSrcMemo());
    srcBillEntity.setBizParas(transferAction.getBizParas());
    srcBillEntity.setAfterBalance(srcNewBalance);
    srcBillEntity.setStatus(AccountBillStatusEnum.FINISHED);

    /**
     * 对于一次转账，只用控制SRC幂等即能控制DEST幂等
     */
    destBillEntity.setIdempotentId(JellyIds.uniqueId());

    destBillEntity.setBillid(destBillid);
    destBillEntity.setAccid(destAccountEntity.getAccid());
    destBillEntity.setStatus(AccountBillStatusEnum.FINISHED);
    destBillEntity.setDirection(FundDirectionEnum.IN);
    destBillEntity.setFundChannel(FundChannelEnum.INNER);
    destBillEntity.setAmount(transferAmount);
    destBillEntity.setBizCode(transferAction.getBizCode());
    destBillEntity.setBizId(transferAction.getBizId());
    destBillEntity.setBillDt(billDt);
    destBillEntity.setMemo(transferAction.getDestMemo());
    destBillEntity.setBizParas(transferAction.getBizParas());
    destBillEntity.setAfterBalance(destNewBalance);

    srcAccountEntity.setBalance(srcNewBalance);
    destAccountEntity.setBalance(destNewBalance);

    jellyTx.execute(status -> {
      accountBillEntityRepository.save(srcBillEntity);
      accountBillEntityRepository.save(destBillEntity);
      accountEntityRepository.save(srcAccountEntity);
      accountEntityRepository.save(destAccountEntity);
      return true;
    });

    LOGGER.info("transfer ok. src bill: {}; dest bill: {}", srcBillid, destBillid);

    return srcBillEntity;
  }
}
