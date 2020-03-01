/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.bc.server.engine;

import com.google.common.base.MoreObjects;
import java.util.Map;
import tech.bitstwinkle.jelly.bc.facade.domains.BillingTask;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public class Rule {
  private final String ruleid;
  private final RuleMatch ruleMatch;
  private final RuleScript ruleScript;

  public Rule(String ruleid, String strRuleMatch, RuleScript ruleScript){
    this.ruleid = ruleid;
    this.ruleMatch = new RuleMatch(strRuleMatch);
    this.ruleScript = ruleScript;
  }

  public boolean isMatched(Map<String, Object> context){
    return this.ruleMatch.isMatched(context);
  }

  public BillingTask calculate(Map<String, Object> context){
    return this.ruleScript.execute(context);
  }

  public String getRuleid() {
    return ruleid;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("ruleid", ruleid)
        .toString();
  }
}
