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
public enum FundUnfreezeTriggerTypeEnum {

  /**
   * 时间触发
   */
  DATETIME_TRIGGER("时间触发"),

  /**
   * 事件触发
   */
  EVENT_TRIGGER("事件触发");

  private String alias;

  FundUnfreezeTriggerTypeEnum(String alias) {
    this.alias = alias;
  }

  public String getCode() {
    return this.name();
  }

  public String getAlias() {
    return this.alias;
  }


}
