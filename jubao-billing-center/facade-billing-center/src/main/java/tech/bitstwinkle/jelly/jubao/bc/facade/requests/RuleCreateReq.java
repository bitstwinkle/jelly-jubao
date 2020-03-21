/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.bc.facade.requests;

import com.google.common.base.MoreObjects;
import tech.bitstwinkle.jelly.jubao.bc.facade.enums.RuleTypeEnum;
import tech.bitstwinkle.jelly.commons.proto.Request;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public class RuleCreateReq extends Request {

  private static final long serialVersionUID = 9186177804002128161L;

  /**
   * 业务类型
   */
  private String bizCode;

  /**
   * 匹配信息
   */
  private String bizMatch;

  private RuleTypeEnum ruleType = RuleTypeEnum.IN_PROPORTION;

  /**
   * 规则脚本
   */
  private String ruleScript;

  private String memo;

  public String getBizCode() {
    return bizCode;
  }

  public void setBizCode(String bizCode) {
    this.bizCode = bizCode;
  }

  public String getBizMatch() {
    return bizMatch;
  }

  public void setBizMatch(String bizMatch) {
    this.bizMatch = bizMatch;
  }

  public RuleTypeEnum getRuleType() {
    return ruleType;
  }

  public void setRuleType(RuleTypeEnum ruleType) {
    this.ruleType = ruleType;
  }

  public String getRuleScript() {
    return ruleScript;
  }

  public void setRuleScript(String ruleScript) {
    this.ruleScript = ruleScript;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("bizCode", bizCode)
        .add("bizMatch", bizMatch)
        .add("ruleType", ruleType)
        .add("ruleScript", ruleScript)
        .add("memo", memo)
        .toString();
  }
}
