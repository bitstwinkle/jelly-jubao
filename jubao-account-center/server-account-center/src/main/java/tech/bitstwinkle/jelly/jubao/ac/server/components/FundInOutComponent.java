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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tech.bitstwinkle.jelly.commons.asserts.RequestAssert;
import tech.bitstwinkle.jelly.commons.money.MoneyHelper;
import tech.bitstwinkle.jelly.idfactory.JellyIdGenerator;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.AccountBillStatusEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundDirectionEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundLockStatusEnum;
import tech.bitstwinkle.jelly.jubao.ac.server.IdGeneratorConst.ACCOUNT_BILLID;
import tech.bitstwinkle.jelly.jubao.ac.server.actions.FundingAction;
import tech.bitstwinkle.jelly.jubao.ac.server.actions.LockAction;
import tech.bitstwinkle.jelly.jubao.ac.server.actions.WithdrawAction;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountBillEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountBillEntityRepository;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountEntityRepository;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.LockSheetEntity;
import tech.bitstwinkle.jelly.platform.clock.JellyClock;
import tech.bitstwinkle.jelly.platform.tx.JellyTxTemplate;

/**
 * 资金流入流出导致内部账户变化
 *
 * @author suuyoo.wg on 2019/10/8
 */
@Component
public class FundInOutComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundInOutComponent.class);

    @Autowired
    private JellyClock jellyClock;

    @Autowired
    private AccountComponent accountComponent;

    @Autowired
    private FundLockComponent fundLockComponent;

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
     * 资金预注入
     * 后续需要内部执行结算，手动触发
     *
     * @param fundingAction
     */
    public AccountBillEntity preflowOut(WithdrawAction fundingAction) {
        return execflowOut(fundingAction, AccountBillStatusEnum.WAITEXECUT);

    }

    /**
     * 资金注入
     *
     * @param fundingAction
     */
    public AccountBillEntity flowOut(WithdrawAction fundingAction) {
        return execflowOut(fundingAction, AccountBillStatusEnum.FINISHED);
    }


    /**
     * 资金预流出
     * 后续需要内部执行结算，手动触发
     *
     * @param wthdrawAction
     */
    public AccountBillEntity execflowOut(WithdrawAction wthdrawAction, AccountBillStatusEnum billStatusEnum) {

        LOGGER.info("funding: {}", wthdrawAction);
        RequestAssert.notNull(wthdrawAction, "wthdrawAction");
        /**
         * 幂等判断
         */
        boolean idempotentCheck = accountBillEntityRepository.existsByIdempotentId(wthdrawAction.getIdempotentId());
        if (idempotentCheck) {
            LOGGER.warn("[idempotent]: {}, [ignore]", wthdrawAction);
            return accountBillEntityRepository.getByIdempotentId(wthdrawAction.getIdempotentId());
        }
        BigDecimal outAmount = wthdrawAction.getAmount();

        //TODO 待出账，账户暂时不变动，看后续是否调整
        AccountEntity accountEntity = accountComponent.loadAccountEntity(wthdrawAction.getAccid());
        //预账户不冻结
        if (StringUtils.equals(AccountBillStatusEnum.FINISHED.getCode(), billStatusEnum.getCode())) {
            BigDecimal newBalance = MoneyHelper.minus(accountEntity.getBalance(), outAmount);
            accountEntity.setBalance(newBalance);
        }

        AccountBillEntity billEntity = new AccountBillEntity();
        Date billDt = jellyClock.now();
        String billid = billidGenerator.generateId();
        //幂等id为billId
        billEntity.setIdempotentId(billid);
        billEntity.setBillid(billid);
        billEntity.setAccid(wthdrawAction.getAccid());
        billEntity.setDirection(FundDirectionEnum.OUT);
        billEntity.setStatus(AccountBillStatusEnum.WAITEXECUT);
        billEntity.setFundChannel(wthdrawAction.getChannel());
        billEntity.setAmount(wthdrawAction.getAmount());
        billEntity.setBizCode(wthdrawAction.getBizCode());
        billEntity.setBizId(wthdrawAction.getBizId());
        billEntity.setBillDt(billDt);
        billEntity.setMemo(wthdrawAction.getMemo());
        billEntity.setBizParas(wthdrawAction.getBizParas());
        billEntity.setAfterBalance(accountEntity.getBalance());

        jellyTx.execute(status -> {
            accountBillEntityRepository.save(billEntity);
            accountEntityRepository.save(accountEntity);
            return true;
        });

        return billEntity;
    }

    /**
     * 资金预注入
     * 后续需要内部执行结算，手动触发
     *
     * @param fundingAction
     */
    public AccountBillEntity prefunding(FundingAction fundingAction) {
        return execfundIn(fundingAction, AccountBillStatusEnum.WAITEXECUT);

    }

    /**
     * 资金注入
     *
     * @param fundingAction
     */
    public AccountBillEntity funding(FundingAction fundingAction) {
        return execfundIn(fundingAction, AccountBillStatusEnum.FINISHED);
    }


    /**
     * 资金注入公共方法
     *
     * @param fundingAction
     */
    private AccountBillEntity execfundIn(FundingAction fundingAction, AccountBillStatusEnum billStatusEnum) {


        LOGGER.info("funding: {}", fundingAction);
        RequestAssert.notNull(fundingAction, "fundingAction");
        /**
         * 幂等判断
         */
        boolean idempotentCheck = accountBillEntityRepository.existsByIdempotentId(fundingAction.getIdempotentId());
        if (idempotentCheck) {
            LOGGER.warn("[idempotent]: {}, [ignore]", fundingAction);
            return accountBillEntityRepository.getByIdempotentId(fundingAction.getIdempotentId());
        }
        BigDecimal inAmount = fundingAction.getAmount();
        AccountEntity accountEntity = accountComponent.loadAccountEntity(fundingAction.getAccid());

        //预账户不冻结
        if (StringUtils.equals(AccountBillStatusEnum.FINISHED.getCode(), billStatusEnum.getCode())) {
            BigDecimal newBalance = MoneyHelper.add(accountEntity.getBalance(), inAmount);
            accountEntity.setBalance(newBalance);
        }
        AccountBillEntity billEntity = new AccountBillEntity();
        Date billDt = jellyClock.now();
        String billid = billidGenerator.generateId();
        //幂等id ，暂时下biildId
        billEntity.setIdempotentId(billid);
        billEntity.setBillid(billid);
        billEntity.setAccid(fundingAction.getAccid());
        billEntity.setDirection(FundDirectionEnum.IN);
        billEntity.setStatus(billStatusEnum);
        billEntity.setFundChannel(fundingAction.getChannel());
        billEntity.setAmount(fundingAction.getAmount());
        billEntity.setBizCode(fundingAction.getBizCode());
        billEntity.setBizId(fundingAction.getBizId());
        billEntity.setBillDt(billDt);
        billEntity.setMemo(fundingAction.getMemo());
        billEntity.setBizParas(fundingAction.getBizParas());
        billEntity.setAfterBalance(accountEntity.getBalance());

        jellyTx.execute(status -> {
            accountBillEntityRepository.save(billEntity);
            accountEntityRepository.save(accountEntity);
            return true;
        });

        return billEntity;

    }


    /**
     * 开始提现，构建锁定单
     *
     * @param withdrawAction
     * @return 提现单据
     */
    public String startWithdraw(WithdrawAction withdrawAction) {

        LOGGER.info("start withdraw: {}", withdrawAction);

        RequestAssert.notNull(withdrawAction, "withdrawAction");

        LockAction lockAction = new LockAction();
        lockAction.setIdempotentId(withdrawAction.getIdempotentId());
        lockAction.setAccid(withdrawAction.getAccid());
        lockAction.setAmount(withdrawAction.getAmount());
        lockAction.setBizCode(withdrawAction.getBizCode());
        lockAction.setBizId(withdrawAction.getBizId());
        lockAction.setDirection(FundDirectionEnum.OUT);
        lockAction.setChannel(withdrawAction.getChannel());
        lockAction.setBizParas(withdrawAction.getBizParas());
        lockAction.setMemo(withdrawAction.getMemo());

        LockSheetEntity lockSheetEntity = fundLockComponent.lock(lockAction);

        return lockSheetEntity.getSheetId();

    }

    /**
     * 提现推进
     *
     * @param withdrawId
     */
    public AccountBillEntity withdrawAdvance(String withdrawId) {
        return fundLockComponent.lockAdvance(withdrawId, FundLockStatusEnum.EXECUTED);
    }

    /**
     * 提现撤销
     *
     * @param withdrawId
     */
    public AccountBillEntity withdrawCancel(String withdrawId) {
        return fundLockComponent.lockAdvance(withdrawId, FundLockStatusEnum.CANCELED);
    }

}
