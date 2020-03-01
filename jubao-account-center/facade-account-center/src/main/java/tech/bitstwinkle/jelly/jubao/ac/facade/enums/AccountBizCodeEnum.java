/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.facade.enums;

/**
 * 账户业务类型
 *
 * @author mb
 * @since 2019/11/25 16:42
 */
public enum AccountBizCodeEnum {


    /**
     * 虚拟账户
     */
    FICTITIOUS("虚拟账户"),


    /**
     * 真实账户
     */
    REAL("真实账户");

    private String alias;

    AccountBizCodeEnum(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getCode() {
        return this.name();
    }


}
