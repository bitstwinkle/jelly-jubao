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
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.AccountStatusEnum;
import tech.bitstwinkle.jelly.platform.dal.SelfPkEntity;

/**
 * @author suuyoo.wg
 */
@Entity
@Table(name = "account_center_account")
@EntityListeners(AuditingEntityListener.class)
public class AccountEntity extends SelfPkEntity {

  private static final long serialVersionUID = -1385842794063917934L;

  /**
   * 账户ID
   */
  @Id
  private String accid;

  /**
   * 业务码
   */
  private String bizCode;

  /**
   * 业务ID
   */
  private String bizId;

  /**
   * 主体类型
   */
  private String subjectType;

  /**
   * 主体ID
   */
  private String subjectId;

  /**
   * 币种
   */
  private String currency;

  /**
   * 余额(可为负数)
   */
  private BigDecimal balance;

  /**
   * 冻结金额
   */
  private BigDecimal freezeAmount;

  /**
   * 系统金额
   */
  private BigDecimal systemAmount;

  @Enumerated(EnumType.STRING)
  private AccountStatusEnum status;

  private String memo;

  @Version
  private long version;

  /**
   * 计算可用余额
   * @return
   */
  public BigDecimal calcAvailableBalance() {
    BigDecimal availableBalance = this.balance;
    availableBalance = availableBalance.subtract(systemAmount);
    availableBalance = availableBalance.subtract(freezeAmount);
    return availableBalance;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("accid", accid)
        .add("bizCode", bizCode)
        .add("bizId", bizId)
        .add("subjectType", subjectType)
        .add("subjectId", subjectId)
        .add("currency", currency)
        .add("balance", balance)
        .add("freezeAmount", freezeAmount)
        .add("systemAmount", systemAmount)
        .add("status", status)
        .toString();
  }

  public String getAccid() {
    return accid;
  }

  public void setAccid(String accid) {
    this.accid = accid;
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

  public String getSubjectType() {
    return subjectType;
  }

  public void setSubjectType(String subjectType) {
    this.subjectType = subjectType;
  }

  public String getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(String subjectId) {
    this.subjectId = subjectId;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public BigDecimal getFreezeAmount() {
    return freezeAmount;
  }

  public void setFreezeAmount(BigDecimal freezeAmount) {
    this.freezeAmount = freezeAmount;
  }

  public BigDecimal getSystemAmount() {
    return systemAmount;
  }

  public void setSystemAmount(BigDecimal systemAmount) {
    this.systemAmount = systemAmount;
  }

  public AccountStatusEnum getStatus() {
    return status;
  }

  public void setStatus(AccountStatusEnum status) {
    this.status = status;
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
