/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.facade.requests;

import com.google.common.base.MoreObjects;
import java.math.BigDecimal;
import java.util.Date;
import tech.bitstwinkle.jelly.commons.proto.Request;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundFreezeTypeEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundUnfreezeTriggerTypeEnum;

/**
 * 资金冻结
 * @author suuyoo.wg on 2020/3/1
 */
public class FundFreezeReq extends Request {

  private static final long serialVersionUID = 8777524747866523851L;

  private String idempotentId;

  private FundFreezeTypeEnum type = FundFreezeTypeEnum.BIZ;

  private String accid;

  private BigDecimal amount;

  private FundUnfreezeTriggerTypeEnum unfreezeTriggerType = FundUnfreezeTriggerTypeEnum.EVENT_TRIGGER;

  private Date expectUnfreezeDt;

  private String memo;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("idempotentId", idempotentId)
        .add("type", type)
        .add("accid", accid)
        .add("amount", amount)
        .add("unfreezeTriggerType", unfreezeTriggerType)
        .add("expectUnfreezeDt", expectUnfreezeDt)
        .add("memo", memo)
        .toString();
  }

  public String getIdempotentId() {
    return idempotentId;
  }

  public void setIdempotentId(String idempotentId) {
    this.idempotentId = idempotentId;
  }

  public FundFreezeTypeEnum getType() {
    return type;
  }

  public void setType(FundFreezeTypeEnum type) {
    this.type = type;
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

  public FundUnfreezeTriggerTypeEnum getUnfreezeTriggerType() {
    return unfreezeTriggerType;
  }

  public void setUnfreezeTriggerType(
      FundUnfreezeTriggerTypeEnum unfreezeTriggerType) {
    this.unfreezeTriggerType = unfreezeTriggerType;
  }

  public Date getExpectUnfreezeDt() {
    return expectUnfreezeDt;
  }

  public void setExpectUnfreezeDt(Date expectUnfreezeDt) {
    this.expectUnfreezeDt = expectUnfreezeDt;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }
}
