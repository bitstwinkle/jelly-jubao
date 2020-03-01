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
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundDirectionEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundLockStatusEnum;
import tech.bitstwinkle.jelly.platform.dal.SelfPkEntity;

/**
 * @author suuyoo.wg on 2019/10/7
 */
@Entity
@Table(name = "account_center_lock_sheet")
@EntityListeners(AuditingEntityListener.class)
public class LockSheetEntity extends SelfPkEntity {

  private static final long serialVersionUID = 5483819789075944040L;
  /**
   * 幂等ID
   */
  private String idempotentId;

  @Id
  private String sheetId;

  private String accid;

  private String billid;

  @Enumerated(EnumType.STRING)
  private FundDirectionEnum direction;

  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  private FundLockStatusEnum status;

  private String bizCode;
  private String bizId;

  private Date lockDt;
  private Date lockAdvDt;

  private String memo;

  @Version
  private long version;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("idempotentId", idempotentId)
        .add("sheetId", sheetId)
        .add("accid", accid)
        .add("billid", billid)
        .add("direction", direction)
        .add("amount", amount)
        .add("status", status)
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

  public String getBillid() {
    return billid;
  }

  public void setBillid(String billid) {
    this.billid = billid;
  }

  public FundDirectionEnum getDirection() {
    return direction;
  }

  public void setDirection(FundDirectionEnum direction) {
    this.direction = direction;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public FundLockStatusEnum getStatus() {
    return status;
  }

  public void setStatus(FundLockStatusEnum status) {
    this.status = status;
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

  public Date getLockDt() {
    return lockDt;
  }

  public void setLockDt(Date lockDt) {
    this.lockDt = lockDt;
  }

  public Date getLockAdvDt() {
    return lockAdvDt;
  }

  public void setLockAdvDt(Date lockAdvDt) {
    this.lockAdvDt = lockAdvDt;
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
