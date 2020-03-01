/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.server.dal;

import com.google.common.base.MoreObjects;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundFreezeStatusEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundFreezeTypeEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundUnfreezeTriggerTypeEnum;
import tech.bitstwinkle.jelly.platform.dal.SelfPkEntity;

/**
 * @author suuyoo.wg on 2019/10/7
 */
@Entity
@Table(name = "account_center_freeze_sheet")
@EntityListeners(AuditingEntityListener.class)
public class FreezeSheetEntity extends SelfPkEntity {

  private static final long serialVersionUID = 5005027263530249754L;

  /**
   * 幂等ID
   */
  private String idempotentId;

  /**
   * 冻结工作单ID
   */
  @Id
  private String sheetId;

  /**
   * 所冻结资金的账户ID
   */
  private String accid;

  /**
   * 资金冻结类型
   */
  @Enumerated(EnumType.STRING)
  private FundFreezeTypeEnum freezeType;

  /**
   * 冻结金额
   */
  private BigDecimal amount;

  /**
   * 冻结状态
   */
  @Enumerated(EnumType.STRING)
  private FundFreezeStatusEnum status;

  /**
   * 解冻触发器类型
   */
  @Enumerated(EnumType.STRING)
  private FundUnfreezeTriggerTypeEnum unfreezeTriggerType;

  /**
   * 当解冻触发器类型为DATETIME_TRIGGER时，需要设置预期解冻时间
   */
  private Date expectUnfreezeDt;

  private Date freezeDt;
  private Date unfreezeDt;

  /**
   * 冻结原因
   */
  private String memo;

  @Version
  private long version;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("idempotentId", idempotentId)
        .add("sheetId", sheetId)
        .add("accid", accid)
        .add("freezeType", freezeType)
        .add("amount", amount)
        .add("status", status)
        .toString();
  }

  public String getIdempotentId() {
    return idempotentId;
  }

  public void setIdempotentId(String idempotentId) {
    this.idempotentId = idempotentId;
  }

  public String getSheetId() {
    return sheetId;
  }

  public void setSheetId(String sheetId) {
    this.sheetId = sheetId;
  }

  public String getAccid() {
    return accid;
  }

  public void setAccid(String accid) {
    this.accid = accid;
  }

  public FundFreezeTypeEnum getFreezeType() {
    return freezeType;
  }

  public void setFreezeType(FundFreezeTypeEnum freezeType) {
    this.freezeType = freezeType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public FundFreezeStatusEnum getStatus() {
    return status;
  }

  public void setStatus(FundFreezeStatusEnum status) {
    this.status = status;
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

  public Date getFreezeDt() {
    return freezeDt;
  }

  public void setFreezeDt(Date freezeDt) {
    this.freezeDt = freezeDt;
  }

  public Date getUnfreezeDt() {
    return unfreezeDt;
  }

  public void setUnfreezeDt(Date unfreezeDt) {
    this.unfreezeDt = unfreezeDt;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }
}
