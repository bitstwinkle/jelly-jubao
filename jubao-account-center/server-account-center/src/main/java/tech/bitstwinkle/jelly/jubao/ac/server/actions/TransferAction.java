/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.server.actions;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author suuyoo.wg on 2019/10/8
 */
public class TransferAction implements Serializable {

  private static final long serialVersionUID = -8767137239636636079L;

  private String idempotentId;

  /**
   * 支出账户
   */
  private String srcAccid;

  /**
   * 收入账户
   */
  private String destAccid;

  /**
   * 转账金额
   */
  private BigDecimal amount;

  /**
   * 转账事由业务码
   */
  private String bizCode;

  /**
   * 转账事由业务ID
   */
  private String bizId;

  /**
   * 支出方备注
   */
  private String srcMemo;

  /**
   * 收入方备注
   */
  private String destMemo;

  /**
   * 业务参数
   */
  private JSONObject bizParas;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("idempotentId", idempotentId)
        .add("srcAccid", srcAccid)
        .add("destAccid", destAccid)
        .add("amount", amount)
        .add("bizCode", bizCode)
        .add("bizId", bizId)
        .add("srcMemo", srcMemo)
        .add("destMemo", destMemo)
        .add("bizParas", bizParas)
        .toString();
  }

  public String getIdempotentId() {
    return idempotentId;
  }

  public void setIdempotentId(String idempotentId) {
    this.idempotentId = idempotentId;
  }

  public String getSrcAccid() {
    return srcAccid;
  }

  public void setSrcAccid(String srcAccid) {
    this.srcAccid = srcAccid;
  }

  public String getDestAccid() {
    return destAccid;
  }

  public void setDestAccid(String destAccid) {
    this.destAccid = destAccid;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getBizCode() {
    return bizCode;
  }

  public void setBizCode(String bizCode) {
    this.bizCode = bizCode;
  }

  public String getBizId() {
    return bizId;
  }

  public void setBizId(String bizId) {
    this.bizId = bizId;
  }

  public String getSrcMemo() {
    return srcMemo;
  }

  public void setSrcMemo(String srcMemo) {
    this.srcMemo = srcMemo;
  }

  public String getDestMemo() {
    return destMemo;
  }

  public void setDestMemo(String destMemo) {
    this.destMemo = destMemo;
  }

  public JSONObject getBizParas() {
    return bizParas;
  }

  public void setBizParas(JSONObject bizParas) {
    this.bizParas = bizParas;
  }
}
