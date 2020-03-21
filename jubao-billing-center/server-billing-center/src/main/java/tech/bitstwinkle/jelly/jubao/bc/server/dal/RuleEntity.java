/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.bc.server.dal;

import com.google.common.base.MoreObjects;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tech.bitstwinkle.jelly.jubao.bc.facade.enums.RuleStatusEnum;
import tech.bitstwinkle.jelly.jubao.bc.facade.enums.RuleTypeEnum;
import tech.bitstwinkle.jelly.platform.dal.SelfPkEntity;

/**
 * @author suuyoo.wg
 */
@Entity
@Table(name = "billing_center_rule")
@EntityListeners(AuditingEntityListener.class)
public class RuleEntity extends SelfPkEntity {

  private static final long serialVersionUID = 5841893365402344936L;

  /**
   * 规则ID
   */
  @Id
  private String ruleid;

  /**
   * 业务类型
   */
  private String bizCode;

  /**
   * 匹配信息
   */
  private String bizMatch;

  @Enumerated(EnumType.STRING)
  private RuleTypeEnum ruleType;

  /**
   * 规则脚本
   */
  private String ruleScript;

  @Enumerated(EnumType.STRING)
  private RuleStatusEnum status;

  private String memo;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("ruleid", ruleid)
        .add("bizCode", bizCode)
        .add("ruleType", ruleType)
        .add("status", status)
        .add("memo", memo)
        .toString();
  }

  public String getRuleid() {
    return ruleid;
  }

  public void setRuleid(String ruleid) {
    this.ruleid = ruleid;
  }

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

  public RuleStatusEnum getStatus() {
    return status;
  }

  public void setStatus(RuleStatusEnum status) {
    this.status = status;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }
}
