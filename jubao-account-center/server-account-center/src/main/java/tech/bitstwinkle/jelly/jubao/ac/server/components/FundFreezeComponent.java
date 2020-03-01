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
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.bitstwinkle.jelly.commons.asserts.RequestAssert;
import tech.bitstwinkle.jelly.commons.errors.JellyException;
import tech.bitstwinkle.jelly.commons.ids.JellyIds;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundFreezeStatusEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.errors.AccountErrorEnum;
import tech.bitstwinkle.jelly.jubao.ac.server.actions.FreezeAction;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountEntityRepository;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.FreezeSheetEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.FreezeSheetEntityRepository;
import tech.bitstwinkle.jelly.platform.clock.JellyClock;
import tech.bitstwinkle.jelly.platform.tx.JellyTxTemplate;

/**
 * 资金冻结
 * @author suuyoo.wg on 2019/10/8
 */
@Component
public class FundFreezeComponent {

  private static final Logger LOGGER = LoggerFactory.getLogger(FundFreezeComponent.class);

  @Autowired
  private JellyClock jellyClock;

  @Autowired
  private JellyTxTemplate wattTx;

  @Autowired
  private AccountComponent accountComponent;

  @Autowired
  private AccountEntityRepository accountEntityRepository;

  @Autowired
  private FreezeSheetEntityRepository freezeSheetEntityRepository;

  public FreezeSheetEntity freeze(FreezeAction freezeAction){

    LOGGER.info("freeze: {}", freezeAction);

    RequestAssert.notNull(freezeAction, "freezeAction");

    /**
     * 幂等判断
     */
    boolean idempotentCheck = freezeSheetEntityRepository.existsByIdempotentId(freezeAction.getIdempotentId());
    if(idempotentCheck){
      LOGGER.warn("[idempotent]: {}, [ignore]", freezeAction);
      return freezeSheetEntityRepository.getByIdempotentId(freezeAction.getIdempotentId());
    }

    AccountEntity accountEntity = accountComponent.loadAccountEntity(freezeAction.getAccid());

    BigDecimal availableBalanceAmount = accountEntity.calcAvailableBalance();
    BigDecimal accountFreezeAmount = accountEntity.getFreezeAmount();
    BigDecimal freezeAmount = freezeAction.getAmount();

    /**
     * 判断余额是否足够冻结
     */
    if(availableBalanceAmount.compareTo(freezeAmount)<0){
      throw new JellyException(
          AccountErrorEnum.BALANCE_NOT_ENOUGH.getError(availableBalanceAmount, freezeAmount));
    }

    BigDecimal accountNewFreezeAmount = accountFreezeAmount.add(freezeAmount);
    accountEntity.setFreezeAmount(accountNewFreezeAmount);

    String sheetId = JellyIds.uniqueId();
    FreezeSheetEntity freezeSheetEntity = new FreezeSheetEntity();
    freezeSheetEntity.setIdempotentId(freezeAction.getIdempotentId());
    freezeSheetEntity.setSheetId(sheetId);
    freezeSheetEntity.setFreezeType(freezeAction.getType());
    freezeSheetEntity.setAccid(freezeAction.getAccid());
    freezeSheetEntity.setAmount(freezeAction.getAmount());
    freezeSheetEntity.setStatus(FundFreezeStatusEnum.FREEZEN);
    freezeSheetEntity.setMemo(freezeAction.getMemo());
    freezeSheetEntity.setUnfreezeTriggerType(freezeAction.getUnfreezeTriggerType());
    freezeSheetEntity.setExpectUnfreezeDt(freezeAction.getExpectUnfreezeDt());
    freezeSheetEntity.setFreezeDt(jellyClock.now());

    wattTx.execute(status -> {
      accountEntityRepository.save(accountEntity);
      freezeSheetEntityRepository.save(freezeSheetEntity);
      return true;
    });

    LOGGER.info("freeze ok: {}", sheetId);

    return freezeSheetEntity;
  }

  /**
   * 根据冻结工单解冻金额
   *
   * @param freezeSheetId
   */
  public void unfreeze(String freezeSheetId) {

    LOGGER.info("unfreeze: {}", freezeSheetId);

    RequestAssert.notBlank(freezeSheetId, "freezeSheetId");

    FreezeSheetEntity freezeSheetEntity = loadFreezeSheetEntity(freezeSheetId);

    if (freezeSheetEntity.getStatus() == FundFreezeStatusEnum.UNFROZEN) {
      LOGGER.warn("has been unfrozen: {}", freezeSheetEntity);
      return;
    }

    AccountEntity accountEntity = accountComponent.loadAccountEntity(freezeSheetEntity.getAccid());
    BigDecimal accountFreezeAmount = accountEntity.getFreezeAmount();
    BigDecimal freezeAmount = freezeSheetEntity.getAmount();
    BigDecimal newAccountFreezeAmount = accountFreezeAmount.subtract(freezeAmount);
    accountEntity.setFreezeAmount(newAccountFreezeAmount);
    freezeSheetEntity.setStatus(FundFreezeStatusEnum.UNFROZEN);
    freezeSheetEntity.setUnfreezeDt(jellyClock.now());

    wattTx.execute(status -> {
      accountEntityRepository.save(accountEntity);
      freezeSheetEntityRepository.save(freezeSheetEntity);
      return true;
    });

    LOGGER.info("unfreeze {} ok, {}", freezeSheetId, accountEntity);

  }

  /**
   * 装载AccountEntity，若不存在则跑出异常
   *
   * @param freezeSheetId
   * @return
   */
  public FreezeSheetEntity loadFreezeSheetEntity(String freezeSheetId) {
    Optional<FreezeSheetEntity> optional = freezeSheetEntityRepository.findById(freezeSheetId);
    if (!optional.isPresent()) {
      throw new JellyException(AccountErrorEnum.FREEZE_SHEET_MISS.getError(freezeSheetId));
    }
    return optional.get();
  }

}
