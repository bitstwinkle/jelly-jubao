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

/**
 * @author suuyoo.wg on 2019/10/8
 */
public class FundingAction implements Serializable {

    private static final long serialVersionUID = 5024420801204524768L;

    private String idempotentId;

    /**
     * 资金进入的账户ID
     */
    private String accid;

    /**
     * 资金渠道
     */
    private FundChannelEnum channel;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 事由业务码
     */
    private String bizCode;

    /**
     * 事由业务ID
     */
    private String bizId;

    /**
     * 事由备注
     */
    private String memo;

    /**
     * 业务参数
     */
    private JSONObject bizParas;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accid", accid)
                .add("idempotentId", idempotentId)
                .add("channel", channel)
                .add("amount", amount)
                .add("bizCode", bizCode)
                .add("bizId", bizId)
                .add("memo", memo)
                .add("bizParas", bizParas)
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

    public FundChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(FundChannelEnum channel) {
        this.channel = channel;
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
