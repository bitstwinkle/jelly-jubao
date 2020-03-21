/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.bc.facade;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.bitstwinkle.jelly.jubao.bc.facade.domains.BillingTask;
import tech.bitstwinkle.jelly.jubao.bc.facade.requests.BillingConsultReq;
import tech.bitstwinkle.jelly.jubao.bc.facade.requests.RuleCreateReq;
import tech.bitstwinkle.jelly.commons.proto.Response;

/**
 * @author suuyoo.wg on 2020/3/1
 */
@FeignClient(name = Summary.SERVICE_NAME, contextId = "tech.bitstwinkle.jelly.jubao.bc.facade.BillingService")
public interface BillingService {

  /**
   * 创建规则，返回规则ID
   * @param request
   * @return
   */
  @PostMapping(value = Summary.PATH + "/createRule")
  Response<String> createRule(@RequestBody RuleCreateReq request);

  /**
   * 咨询计费结果
   * @param request
   * @return
   */
  @PostMapping(value = Summary.PATH + "/consult")
  Response<BillingTask> consult(@RequestBody BillingConsultReq request);
}
