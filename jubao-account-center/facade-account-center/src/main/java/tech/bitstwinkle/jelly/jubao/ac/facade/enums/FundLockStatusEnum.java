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
public enum FundLockStatusEnum {

  /**
   * 已锁定
   */
  LOCKING("已锁定"),

  /**
   * 已执行
   */
  EXECUTED("已执行"),

  /**
   * 已取消
   */
  CANCELED("已取消")
  ;

  private String alias;

  FundLockStatusEnum(String alias) {
    this.alias = alias;
  }

  public String getAlias() {
    return this.alias;
  }

  public String getCode() {
    return this.name();
  }

}
