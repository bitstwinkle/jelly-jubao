/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.bc.facade.enums;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public enum RuleStatusEnum {

  /**
   * AVAILABLE
   */
  AVAILABLE("可用"),

  /**
   * DISABLED
   */
  DISABLED("不可用"),

  ;

  private String alias;

  RuleStatusEnum(String alias) {
    this.alias = alias;
  }

  public String getAlias() {
    return this.alias;
  }

  public String getCode() {
    return this.name();
  }

}
