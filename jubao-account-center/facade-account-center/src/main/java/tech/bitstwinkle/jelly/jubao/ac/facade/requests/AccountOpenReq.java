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
import tech.bitstwinkle.jelly.commons.money.MoneyHelper;
import tech.bitstwinkle.jelly.commons.proto.Request;

/**
 * 账户开户，账户创建
 *
 * @author suuyoo.wg on 2020/03/01
 */
public class AccountOpenReq extends Request {

    private static final long serialVersionUID = -4002332742133919799L;

    /**
     * 主体类型
     */
    private String subjectType;

    /**
     * 主体ID
     */
    private String subjectId;

    /**
     * 业务码
     */
    private String bizCode;

    /**
     * 业务编号
     */
    private String bizId;

    /**
     * 开户币种
     */
    private String currency = MoneyHelper.DEFAULT_CURRENCY;

    /**
     * 账户描述
     */
    private String memo;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("subjectType", subjectType)
            .add("subjectId", subjectId)
            .add("bizCode", bizCode)
            .add("bizId", bizId)
            .add("currency", currency)
            .add("memo", memo)
            .toString();
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
