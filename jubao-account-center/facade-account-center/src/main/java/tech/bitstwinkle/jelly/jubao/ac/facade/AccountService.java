/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.facade;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.bitstwinkle.jelly.commons.proto.Response;
import tech.bitstwinkle.jelly.jubao.ac.facade.domains.Account;
import tech.bitstwinkle.jelly.jubao.ac.facade.domains.Bill;
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

/**
 * 账户服务
 * @author suuyoo.wg on 2020/3/1
 */
@FeignClient(name = Summary.SERVICE_NAME, contextId = "tech.bitstwinkle.jelly.jubao.ac.facade.AccountService")
public interface AccountService {

  /**
   * 开户
   * @param request
   * @return
   */
  @PostMapping(value = Summary.PATH + "/openAccount")
  Response<Account> openAccount(@RequestBody AccountOpenReq request);

  /**
   * 账户冻结
   * @param request
   * @return
   */
  @PostMapping(value = Summary.PATH + "/freezeAccount")
  Response<Account> freezeAccount(@RequestBody AccountFreezeReq request);

  /**
   * 账户解冻
   * @param request
   * @return
   */
  @PostMapping(value = Summary.PATH + "/unfreezeAccount")
  Response<Account> unfreezeAccount(@RequestBody AccountUnfreezeReq request);


  /**
   * 充值
   * @param request
   * @return
   */
  @PostMapping(value = Summary.PATH + "/funding")
  Response<Bill> funding(@RequestBody FundingReq request);

  /**
   * 提现申请
   * @param request
   * @return
   */
  @PostMapping(value = Summary.PATH + "/withdrawApply")
  Response<String> withdrawApply(@RequestBody WithdrawApplyReq request);

  /**
   * 提现推进
   * @param request
   * @return
   */
  @PostMapping(value = Summary.PATH + "/withdrawAdvance")
  Response<Bill> withdrawAdvance(@RequestBody WithdrawAdvanceReq request);

  /**
   * 提现撤销
   * @param request
   * @return
   */
  @PostMapping(value = Summary.PATH + "/withdrawCancel")
  Response<Bill> withdrawCancel(@RequestBody WithdrawCancelReq request);

  /**
   * 冻结资金
   * @param request
   * @return
   */
  @PostMapping(value = Summary.PATH + "/freezeFund")
  Response<String> freezeFund(@RequestBody FundFreezeReq request);

  /**
   * 解冻资金
   * @param request
   * @return
   */
  @PostMapping(value = Summary.PATH + "/unfreezeFund")
  Response<String> unfreezeFund(@RequestBody FundUnfreezeReq request);



  /**
   * 资金转账
   * @param request
   * @return
   */
  @PostMapping(value = Summary.PATH + "/transfer")
  Response<Bill> transfer(@RequestBody TransferReq request);

}
