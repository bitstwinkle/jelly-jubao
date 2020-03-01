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
 * @author suuyoo.wg on 2020/03/01
 */
public enum FundDirectionEnum {

  /**
   * 收入
   */
  IN("收入"),

  /**
   * 支出
   */
  OUT("支出")
  ;

  private String alias;

  FundDirectionEnum(String alias) {
    this.alias = alias;
  }

  public String getAlias() {
    return this.alias;
  }

  public String getCode() {
    return this.name();
  }

}
