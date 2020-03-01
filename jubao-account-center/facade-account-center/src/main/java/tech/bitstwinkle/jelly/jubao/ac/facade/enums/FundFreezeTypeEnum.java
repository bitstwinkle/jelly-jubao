/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.facade.enums;

/**
 * @author suuyoo.wg on 2019/10/7
 */
public enum FundFreezeTypeEnum {

  /**
   * 业务冻结
   */
  BIZ("业务冻结"),

  /**
   * 风控冻结
   */
  RISK_CONTROL("风控冻结")
  ;

  private String alias;

  FundFreezeTypeEnum(String alias) {
    this.alias = alias;
  }

  public String getAlias() {
    return this.alias;
  }

  public String getCode() {
    return this.name();
  }

}
