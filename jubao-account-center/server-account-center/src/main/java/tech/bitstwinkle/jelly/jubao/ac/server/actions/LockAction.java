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
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundChannelEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundDirectionEnum;

/**
 * @author suuyoo.wg on 2019/10/9
 */
public class LockAction implements Serializable {

  private static final long serialVersionUID = -5976027881693403194L;

  private String idempotentId;

  private String accid;

  private BigDecimal amount;

  private FundDirectionEnum direction;

  private FundChannelEnum channel;

  private String bizCode;

  private String bizId;

  private String memo;

  private JSONObject bizParas;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("idempotentId", idempotentId)
        .add("accid", accid)
        .add("amount", amount)
        .add("direction", direction)
        .add("channel", channel)
        .add("bizCode", bizCode)
        .add("bizId", bizId)
        .toString();
  }

  public String getIdempotentId() {
    return idempotentId;
  }

  public void setIdempotentId(String idempotentId) {
    this.idempotentId = idempotentId;
  }

  public String getAccid() {
    return accid;
  }

  public void setAccid(String accid) {
    this.accid = accid;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public FundDirectionEnum getDirection() {
    return direction;
  }

  public void setDirection(FundDirectionEnum direction) {
    this.direction = direction;
  }

  public FundChannelEnum getChannel() {
    return channel;
  }

  public void setChannel(FundChannelEnum channel) {
    this.channel = channel;
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

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public JSONObject getBizParas() {
    return bizParas;
  }

  public void setBizParas(JSONObject bizParas) {
    this.bizParas = bizParas;
  }
}
