/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.bc.server.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.bitstwinkle.jelly.jubao.bc.facade.enums.RuleStatusEnum;
import tech.bitstwinkle.jelly.jubao.bc.facade.enums.RuleTypeEnum;
import tech.bitstwinkle.jelly.jubao.bc.server.dal.RuleEntity;
import tech.bitstwinkle.jelly.jubao.bc.server.dal.RuleEntityRepository;
import tech.bitstwinkle.jelly.jubao.bc.facade.domains.BillingTask;
import tech.bitstwinkle.jelly.jubao.bc.server.engine.scripts.InProportionScript;

/**
 * @author suuyoo.wg on 2020/3/1
 */
@Component
public class RuleEngine {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuleEngine.class);

  private static final Lock lock = new ReentrantLock();
  private Map<String, List<Rule>> rules = new HashMap<>();

  @Autowired
  private RuleEntityRepository ruleEntityRepository;

  public BillingTask calculate(String bizCode, Map<String, Object> context){
    List<Rule> ruleList = getRuleList(bizCode);
    boolean matched;
    Rule matchedRule = null;
    for(Rule rule : ruleList){
      matched = rule.isMatched(context);
      if(matched){
        matchedRule = rule;
        break;
      }
    }
    if(matchedRule==null){
      return null;
    }

    return matchedRule.calculate(context);

  }

  private List<Rule> getRuleList(String bizCode){
    List<Rule> ruleList = rules.get(bizCode);
    if(ruleList==null || ruleList.isEmpty()){
      lock.lock();
      try{
        if(ruleList==null||ruleList.isEmpty()) {
          ruleList = loadRuleList(bizCode);
          rules.put(bizCode, ruleList);
        }
      }finally {
        lock.unlock();
      }
    }
    return ruleList;
  }

  private List<Rule> loadRuleList(String bizCode){
    List<RuleEntity> dbList = ruleEntityRepository.findAllByBizCodeAndStatus(bizCode, RuleStatusEnum.AVAILABLE);
    List<Rule> ruleList = new ArrayList<>();
    for(RuleEntity entity : dbList){
      if(entity.getRuleType() == RuleTypeEnum.IN_PROPORTION){
        Rule rule = new Rule(entity.getRuleid(), entity.getBizMatch(), new InProportionScript(entity.getRuleScript()));
        ruleList.add(rule);
      }
    }
    return ruleList;
  }

}
