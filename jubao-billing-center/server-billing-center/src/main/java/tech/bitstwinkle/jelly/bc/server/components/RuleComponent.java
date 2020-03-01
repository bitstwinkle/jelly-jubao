/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.bc.server.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.bitstwinkle.jelly.bc.facade.enums.RuleStatusEnum;
import tech.bitstwinkle.jelly.commons.asserts.RequestAssert;
import tech.bitstwinkle.jelly.commons.ids.JellyIds;
import tech.bitstwinkle.jelly.platform.tx.JellyTxTemplate;
import tech.bitstwinkle.jelly.bc.server.dal.RuleEntity;
import tech.bitstwinkle.jelly.bc.server.dal.RuleEntityRepository;

/**
 * @author suuyoo.wg on 2020/3/1
 */
@Component
public class RuleComponent {

  @Autowired
  private RuleEntityRepository ruleEntityRepository;

  @Autowired
  private JellyTxTemplate jellyTx;

  public RuleEntity createRuleEntity(RuleEntity ruleEntity){
    RequestAssert.notNull(ruleEntity, "ruleEntity");

    String ruleid = JellyIds.uniqueId();
    ruleEntity.setRuleid(ruleid);
    ruleEntity.setStatus(RuleStatusEnum.AVAILABLE);

    jellyTx.execute(status -> {
      ruleEntityRepository.save(ruleEntity);
      return true;
    });

    return ruleEntity;
  }

}
