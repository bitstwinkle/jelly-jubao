/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.server.dal;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.MoreObjects;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.AccountBillStatusEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundChannelEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.FundDirectionEnum;
import tech.bitstwinkle.jelly.platform.dal.SelfPkEntity;
import tech.bitstwinkle.jelly.platform.dal.converters.JpaJsonConverter;

/**
 * @author suuyoo.wg on 2019/10/7
 */
@Entity
@Table(name = "account_center_bill")
@EntityListeners(AuditingEntityListener.class)
public class AccountBillEntity extends SelfPkEntity {

    private static final long serialVersionUID = -5827161456796947157L;

    /**
     * 幂等ID
     */
    private String idempotentId;

    /**
     * 账单ID，19位
     */
    @Id
    private String billid;

    /**
     * 账户ID
     */
    private String accid;

    /**
     * 资金方向
     */
    @Enumerated(EnumType.STRING)
    private FundDirectionEnum direction;

    /**
     * 资金渠道
     */
    @Enumerated(EnumType.STRING)
    private FundChannelEnum fundChannel;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 业务码
     */
    private String bizCode;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 业务发生时间
     */
    private Date billDt;

    /**
     * 业务描述
     */
    private String memo;

    /**
     * 业务参数
     */
    @Convert(converter = JpaJsonConverter.class)
    private JSONObject bizParas;

    /**
     * 操作后余额
     */
    private BigDecimal afterBalance;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    private AccountBillStatusEnum status;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("idempotentId", idempotentId)
                .add("billid", billid)
                .add("accid", accid)
                .add("direction", direction)
                .add("fundChannel", fundChannel)
                .add("amount", amount)
                .add("bizCode", bizCode)
                .add("bizId", bizId)
                .add("status", status)
                .toString();
    }

    public String getIdempotentId() {
        return idempotentId;
    }

    public void setIdempotentId(String idempotentId) {
        this.idempotentId = idempotentId;
    }

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public FundDirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(FundDirectionEnum direction) {
        this.direction = direction;
    }

    public FundChannelEnum getFundChannel() {
        return fundChannel;
    }

    public void setFundChannel(FundChannelEnum fundChannel) {
        this.fundChannel = fundChannel;
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

    public Date getBillDt() {
        return billDt;
    }

    public void setBillDt(Date billDt) {
        this.billDt = billDt;
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

    public BigDecimal getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(BigDecimal afterBalance) {
        this.afterBalance = afterBalance;
    }

    public AccountBillStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AccountBillStatusEnum status) {
        this.status = status;
    }
}
