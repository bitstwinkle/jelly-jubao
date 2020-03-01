/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.facade.errors;

import tech.bitstwinkle.jelly.commons.errors.JellyError;
import tech.bitstwinkle.jelly.commons.errors.JellyErrorGetter;
import tech.bitstwinkle.jelly.commons.errors.JellyErrorTemplate;

/**
 * @author 淑尤
 */
public enum AccountErrorEnum implements JellyErrorGetter {

  /**
   * 无效的币种类型 | 需要提供1个参数（参数名）
   */
  INVALID_CURRENCY("INVALID_CURRENCY", "invalid currency : {0}", "无效的币种类型: {0}"),

  /**
   * 账户已存在| 需要提供2个参数（参数名）
   */
  ACCOUNT_EXIST("ACCOUNT_EXIST", "account exists : {0}@{1}", "该账户已存在: {0}@{1}"),

  /**
   * 账户不存在| 需要提供1个参数（账户ID）
   */
  ACCOUNT_MISS("ACCOUNT_MISS", "account miss : {0}", "该账户不存在: {0}"),

  /**
   * 账户无效| 需要提供2个参数（账户ID,状态）
   */
  ACCOUNT_INVALID("ACCOUNT_INVALID", "account invalid : {0} {1}", "该账户不可用: {0} {1}"),

  /**
   * 余额不足
   */
  BALANCE_NOT_ENOUGH("BALANCE_NOT_ENOUGH", "balance not enough {0}", "余额不足: {0}"),

  /**
   * 冻结单不存在| 需要提供1个参数（冻结单号）
   */
  FREEZE_SHEET_MISS("FREEZE_SHEET_MISS", "freeze not exists : {0}", "该冻结单据不存在: {0}"),

  /**
   * 锁定单不存在| 需要提供1个参数（锁定单号）
   */
  LOCK_SHEET_MISS("LOCK_SHEET_MISS", "lock sheet not exists : {0}", "该锁定单据不存在: {0}"),

  /**
   * 锁定单状态不正确| 需要提供2个参数（锁定单号, 当前状态）
   */
  LOCK_SHEET_STATUS_INVALID("LOCK_SHEET_STATUS_INVALID", "lock sheet status is invalid : {0} {1}",
      "该锁定单据状态不符: {0} {1}"),

  /**
   * 锁定单和账单信息不一致| 需要提供2个参数（锁定单号, 账单号）
   */
  LOCK_BILL_NOT_CONSISTENCY("LOCK_BILL_NOT_CONSISTENCY",
      "lock sheet and bill not consistency : {0} {1}",
      "锁定单和账单信息不一致: {0} {1}"),

  /**
   * 账单不存在| 需要提供1个参数（账单ID）
   */
  ACCOUNT_BILL_MISS("ACCOUNT_BILL_MISS", "account bill miss : {0}", "该账单不存在: {0}"),

  ;

  private JellyErrorTemplate template;

  AccountErrorEnum(String code, String messageTpl, String viewTpl) {
    this.template = new JellyErrorTemplate(code, messageTpl, viewTpl);
  }

  @Override
  public JellyError getError(Object... args) {
    return this.template.getError(args);
  }
}
