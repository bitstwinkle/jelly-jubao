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
public enum AccountStatusEnum {

  /**
   * 初始化
   */
  INIT("初始化"),

  /**
   * 已生效
   */
  EFFECTIVE("已生效"),

  /**
   * 由于安全等原因，、冻结
   */
  FROZEN("已冻结"),

  /**
   * 已失效
   */
  INEFFECTIVE("已失效");

  private String alias;

  AccountStatusEnum(String alias) {
    this.alias = alias;
  }

  public String getAlias() {
    return this.alias;
  }

  public String getCode() {
    return this.name();
  }

  public boolean isAvailable() {
    return this == EFFECTIVE;
  }

}
