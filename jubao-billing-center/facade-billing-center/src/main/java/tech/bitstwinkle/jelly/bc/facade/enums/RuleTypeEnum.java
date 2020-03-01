/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.bc.facade.enums;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public enum RuleTypeEnum {

  /**
   * IN_PROPORTION
   */
  IN_PROPORTION("按比例"),

  /**
   * GROOVY
   */
  GROOVY("GROOVY脚本"),

  ;

  private String alias;

  RuleTypeEnum(String alias) {
    this.alias = alias;
  }

  public String getAlias() {
    return this.alias;
  }

  public String getCode() {
    return this.name();
  }

}
