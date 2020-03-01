/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.bc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import tech.bitstwinkle.jelly.bc.facade.BillingService;
import tech.bitstwinkle.jelly.bc.facade.domains.BillingTask;
import tech.bitstwinkle.jelly.bc.facade.requests.BillingConsultReq;
import tech.bitstwinkle.jelly.bc.facade.requests.RuleCreateReq;
import tech.bitstwinkle.jelly.bc.server.engine.RuleEngine;
import tech.bitstwinkle.jelly.commons.asserts.RequestAssert;
import tech.bitstwinkle.jelly.commons.proto.Response;
import tech.bitstwinkle.jelly.platform.access.annotation.AccessEntrance;
import tech.bitstwinkle.jelly.bc.server.components.RuleComponent;
import tech.bitstwinkle.jelly.bc.server.dal.RuleEntity;

/**
 * @author suuyoo.wg on 2020/3/1
 */
@RestController
public class BillingServiceImpl implements BillingService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BillingService.class);

  @Autowired
  private RuleComponent ruleComponent;

  @Autowired
  private RuleEngine ruleEngine;

  @Override
  @AccessEntrance
  public Response<String> createRule(RuleCreateReq request) {
    RequestAssert.notNull(request, "request");
    RequestAssert.notBlank(request.getBizCode(), "request.bizCode");
    RequestAssert.notBlank(request.getBizMatch(), "request.bizMatch");
    RequestAssert.notBlank(request.getRuleScript(), "request.ruleScript");

    RuleEntity ruleEntity = new RuleEntity();
    ruleEntity.setBizCode(request.getBizCode());
    ruleEntity.setBizMatch(request.getBizMatch());
    ruleEntity.setRuleScript(request.getRuleScript());
    ruleEntity.setMemo(request.getMemo());
    ruleEntity.setRuleType(request.getRuleType());

    ruleComponent.createRuleEntity(ruleEntity);

    return new Response<>(ruleEntity.getRuleid());
  }

  @Override
  @AccessEntrance
  public Response<BillingTask> consult(BillingConsultReq request) {
    RequestAssert.notNull(request, "request");
    RequestAssert.notBlank(request.getBizCode(), "request.bizCode");
    RequestAssert.notEmpty(request.getContext(), "request.context");

    BillingTask task = ruleEngine.calculate(request.getBizCode(), request.getContext());
    return new Response<>(task);
  }
}
