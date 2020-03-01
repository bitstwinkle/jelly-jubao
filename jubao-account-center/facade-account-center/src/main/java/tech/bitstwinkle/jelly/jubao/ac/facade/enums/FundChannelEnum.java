/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.facade.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public enum FundChannelEnum {

  /**
   * 内部账户
   */
  INNER("内部账户"),

  /**
   * “银联”渠道
   */
  UNIONPAY("银联商务"),

  /**
   * “海尔”渠道
   */
  HAIER("海尔"),

  /**
   * 微信
   */
  WECHAT("微信"),

  /**
   * 支付宝
   */
  ALIPAY("支付宝"),

  /**
   * 连连支付
   */
  LIANLIAN("连连支付");

  private String alias;

  FundChannelEnum(String alias) {
    this.alias = alias;
  }

  public String getAlias() {
    return this.alias;
  }

  public String getCode() {
    return this.name();
  }

  /**
   * 通过code查找
   *
   * @param code
   * @return
   */
  public static FundChannelEnum getEnumByCode(String code) {
    for (FundChannelEnum decorationEnum : FundChannelEnum.values()) {
      if (StringUtils.equals(code, decorationEnum.getCode())) {
        return decorationEnum;
      }
    }
    return null;
  }
}