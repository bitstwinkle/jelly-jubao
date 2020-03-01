/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.server;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.web.bind.annotation.RestController;
import tech.bitstwinkle.jelly.commons.asserts.RequestAssert;
import tech.bitstwinkle.jelly.commons.errors.enums.ParameterErrorEnum;
import tech.bitstwinkle.jelly.commons.errors.enums.SystemErrorEnum;
import tech.bitstwinkle.jelly.commons.money.MoneyHelper;
import tech.bitstwinkle.jelly.commons.proto.Response;
import tech.bitstwinkle.jelly.jubao.ac.facade.AccountService;
import tech.bitstwinkle.jelly.jubao.ac.facade.domains.Account;
import tech.bitstwinkle.jelly.jubao.ac.facade.domains.Bill;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.AccountStatusEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundUnfreezeTriggerTypeEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.requests.AccountFreezeReq;
import tech.bitstwinkle.jelly.jubao.ac.facade.requests.AccountOpenReq;
import tech.bitstwinkle.jelly.jubao.ac.facade.requests.AccountUnfreezeReq;
import tech.bitstwinkle.jelly.jubao.ac.facade.requests.FundFreezeReq;
import tech.bitstwinkle.jelly.jubao.ac.facade.requests.FundUnfreezeReq;
import tech.bitstwinkle.jelly.jubao.ac.facade.requests.FundingReq;
import tech.bitstwinkle.jelly.jubao.ac.facade.requests.TransferReq;
import tech.bitstwinkle.jelly.jubao.ac.facade.requests.WithdrawAdvanceReq;
import tech.bitstwinkle.jelly.jubao.ac.facade.requests.WithdrawApplyReq;
import tech.bitstwinkle.jelly.jubao.ac.facade.requests.WithdrawCancelReq;
import tech.bitstwinkle.jelly.jubao.ac.server.actions.FreezeAction;
import tech.bitstwinkle.jelly.jubao.ac.server.actions.FundingAction;
import tech.bitstwinkle.jelly.jubao.ac.server.actions.TransferAction;
import tech.bitstwinkle.jelly.jubao.ac.server.actions.WithdrawAction;
import tech.bitstwinkle.jelly.jubao.ac.server.components.AccountComponent;
import tech.bitstwinkle.jelly.jubao.ac.server.components.FundFreezeComponent;
import tech.bitstwinkle.jelly.jubao.ac.server.components.FundInOutComponent;
import tech.bitstwinkle.jelly.jubao.ac.server.components.TransferComponent;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountBillEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.FreezeSheetEntity;
import tech.bitstwinkle.jelly.platform.access.annotation.AccessEntrance;
import tech.bitstwinkle.jelly.platform.copier.ObjectCopier;

/**
 * 账户服务实现
 *
 * @author suuyoo.wg
 */
@RestController
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private final BeanCopier accountCopier = ObjectCopier.getBeanCopier(AccountEntity.class, Account.class);
    private final BeanCopier billCopier = ObjectCopier.getBeanCopier(AccountBillEntity.class, Bill.class);

    /**
     * 账户基础组件
     */
    @Autowired
    private AccountComponent accountComponent;

    /**
     * 账户资金进出
     */
    @Autowired
    private FundInOutComponent fundInOutComponent;

    /**
     * 账户资金冻结
     */
    @Autowired
    private FundFreezeComponent fundFreezeComponent;

    /**
     * 资金转账
     */
    @Autowired
    private TransferComponent transferComponent;

    /**
     * 创建单个账户
     *
     * @param request
     * @return
     */
    @Override
    @AccessEntrance
    public Response<Account> openAccount(@Valid AccountOpenReq request) {
        RequestAssert.notNull(request, "request");
        RequestAssert.notBlank(request.getSubjectType(), "req.subjectType");
        RequestAssert.notBlank(request.getSubjectId(), "req.subjectId");
        RequestAssert.notBlank(request.getBizCode(), "req.bizCode");
        RequestAssert.notBlank(request.getBizId(), "req.bizId");
        RequestAssert.isTrue(MoneyHelper.isValidCurrency(request.getCurrency()), "req.currency is invalid");

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBizCode(request.getBizCode());
        accountEntity.setBizId(request.getBizId());
        accountEntity.setCurrency(request.getCurrency());
        accountEntity.setSubjectType(request.getSubjectType());
        accountEntity.setSubjectId(request.getSubjectId());
        accountEntity.setMemo(request.getMemo());
        accountEntity.setStatus(AccountStatusEnum.EFFECTIVE);
        accountEntity.setBalance(MoneyHelper.ZERO);
        accountEntity.setFreezeAmount(MoneyHelper.ZERO);
        accountEntity.setSystemAmount(MoneyHelper.ZERO);

        accountComponent.createAccountEntity(accountEntity);

        Account account = new Account();
        accountCopier.copy(accountEntity, account, null);

        return new Response<>(account);
    }

    @Override
    @AccessEntrance
    public Response<Account> freezeAccount(AccountFreezeReq request) {
        RequestAssert.notNull(request, "request");
        RequestAssert.notBlank(request.getAccid(), "req.accid");
        AccountEntity accountEntity = accountComponent.setAccountStatus(request.getAccid(), AccountStatusEnum.FROZEN);

        Account account = new Account();
        accountCopier.copy(accountEntity, account, null);

        return new Response<>(account);
    }

    @Override
    @AccessEntrance
    public Response<Account> unfreezeAccount(AccountUnfreezeReq request) {
        RequestAssert.notNull(request, "request");
        RequestAssert.notBlank(request.getAccid(), "req.accid");

        AccountEntity accountEntity = accountComponent.setAccountStatus(request.getAccid(), AccountStatusEnum.EFFECTIVE);

        Account account = new Account();
        accountCopier.copy(accountEntity, account, null);

        return new Response<>(account);
    }

    @Override
    @AccessEntrance
    public Response<Bill> funding(FundingReq request) {
        RequestAssert.notNull(request, "request");
        RequestAssert.notBlank(request.getIdempotentId(), "req.idempotentId");
        RequestAssert.notBlank(request.getAccid(), "req.accid");
        RequestAssert.notNull(request.getChannel(), "req.channel");
        RequestAssert.notNull(request.getAmount(), "req.amount");
        RequestAssert.notBlank(request.getBizCode(), "req.bizCode");
        RequestAssert.notBlank(request.getBizId(), "req.bizId");

        FundingAction fundingAction = new FundingAction();
        fundingAction.setIdempotentId(request.getIdempotentId());
        fundingAction.setAccid(request.getAccid());
        fundingAction.setBizCode(request.getBizCode());
        fundingAction.setBizId(request.getBizId());
        fundingAction.setAmount(request.getAmount());
        fundingAction.setChannel(request.getChannel());
        fundingAction.setMemo(request.getMemo());
        fundingAction.setBizParas(request.getBizParas());
        AccountBillEntity billEntity = fundInOutComponent.funding(fundingAction);

        Bill bill = new Bill();
        billCopier.copy(billEntity, bill, null);
        return new Response<>(bill);
    }

    @Override
    @AccessEntrance
    public Response<String> withdrawApply(WithdrawApplyReq request) {
        RequestAssert.notNull(request, "request");
        RequestAssert.notBlank(request.getIdempotentId(), "req.idempotentId");
        RequestAssert.notBlank(request.getAccid(), "req.accid");
        RequestAssert.notNull(request.getAmount(), "req.amount");
        RequestAssert.notNull(request.getChannel(), "req.channel");
        RequestAssert.notBlank(request.getBizCode(), "req.bizCode");
        RequestAssert.notBlank(request.getBizId(), "req.bizId");

        WithdrawAction withdrawAction = new WithdrawAction();
        withdrawAction.setIdempotentId(request.getIdempotentId());
        withdrawAction.setAccid(request.getAccid());
        withdrawAction.setBizCode(request.getBizCode());
        withdrawAction.setBizId(request.getBizId());
        withdrawAction.setChannel(request.getChannel());
        withdrawAction.setAmount(request.getAmount());
        withdrawAction.setMemo(request.getMemo());
        withdrawAction.setBizParas(request.getBizParas());

        String withdrawApplyId = fundInOutComponent.startWithdraw(withdrawAction);

        return new Response<>(withdrawApplyId);
    }

    @Override
    @AccessEntrance
    public Response<Bill> withdrawAdvance(WithdrawAdvanceReq request) {
        RequestAssert.notNull(request, "request");
        RequestAssert.notBlank(request.getWithdrawApplyId(), "req.withdrawApplyId");

        AccountBillEntity billEntity = fundInOutComponent.withdrawAdvance(request.getWithdrawApplyId());

        Bill bill = new Bill();
        billCopier.copy(billEntity, bill, null);
        return new Response<>(bill);
    }

    @Override
    @AccessEntrance
    public Response<Bill> withdrawCancel(WithdrawCancelReq request) {
        RequestAssert.notNull(request, "request");
        RequestAssert.notBlank(request.getWithdrawApplyId(), "req.withdrawApplyId");

        AccountBillEntity billEntity = fundInOutComponent.withdrawCancel(request.getWithdrawApplyId());

        Bill bill = new Bill();
        billCopier.copy(billEntity, bill, null);
        return new Response<>(bill);
    }

    @Override
    @AccessEntrance
    public Response<String> freezeFund(FundFreezeReq request) {
        RequestAssert.notNull(request, "request");
        RequestAssert.notBlank(request.getIdempotentId(), "req.idempotentId");
        RequestAssert.notBlank(request.getAccid(), "req.accid");
        RequestAssert.notNull(request.getAmount(), "req.amount");
        RequestAssert.notNull(request.getUnfreezeTriggerType(), "req.unfreezeTriggerType");
        if(request.getUnfreezeTriggerType()==FundUnfreezeTriggerTypeEnum.DATETIME_TRIGGER){
            RequestAssert.notNull(request.getExpectUnfreezeDt(), "req.expectUnfreezeDt");
        }

        FreezeAction freezeAction = new FreezeAction();
        freezeAction.setIdempotentId(request.getIdempotentId());
        freezeAction.setAccid(request.getAccid());
        freezeAction.setType(request.getType());
        freezeAction.setUnfreezeTriggerType(request.getUnfreezeTriggerType());
        freezeAction.setExpectUnfreezeDt(request.getExpectUnfreezeDt());
        freezeAction.setAmount(request.getAmount());
        freezeAction.setMemo(request.getMemo());

        FreezeSheetEntity freezeSheetEntity = fundFreezeComponent.freeze(freezeAction);
        return new Response<>(freezeSheetEntity.getSheetId());
    }

    @Override
    @AccessEntrance
    public Response<String> unfreezeFund(FundUnfreezeReq request) {
        RequestAssert.notNull(request, "request");
        RequestAssert.notBlank(request.getFreezeSheetId(), "req.freezeSheetId");

        fundFreezeComponent.unfreeze(request.getFreezeSheetId());

        return new Response<>(request.getFreezeSheetId());
    }

    @Override
    public Response<Bill> transfer(TransferReq request) {
        RequestAssert.notNull(request, "request");
        RequestAssert.notBlank(request.getIdempotentId(), "req.idempotentId");
        RequestAssert.notBlank(request.getSrcAccid(), "req.srcAccId");
        RequestAssert.notBlank(request.getDestAccid(), "req.destAccId");
        RequestAssert.notNull(request.getAmount(), "req.amount");
        RequestAssert.notBlank(request.getBizCode(), "req.bizCode");
        RequestAssert.notBlank(request.getBizId(), "req.bizId");

        TransferAction transferAction = new TransferAction();
        transferAction.setIdempotentId(request.getIdempotentId());
        transferAction.setSrcAccid(request.getSrcAccid());
        transferAction.setDestAccid(request.getDestAccid());
        transferAction.setAmount(request.getAmount());
        transferAction.setBizCode(request.getBizCode());
        transferAction.setBizId(request.getBizId());
        transferAction.setSrcMemo(request.getSrcMemo());
        transferAction.setDestMemo(request.getDestMemo());
        transferAction.setBizParas(request.getBizParas());

        AccountBillEntity billEntity = transferComponent.transfer(transferAction);

        Bill bill = new Bill();
        billCopier.copy(billEntity, bill, null);
        return new Response<>(bill);
    }

}
