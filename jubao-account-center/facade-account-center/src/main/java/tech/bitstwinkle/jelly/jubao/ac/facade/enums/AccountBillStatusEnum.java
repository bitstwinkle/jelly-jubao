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
 * @author suuyoo.wg on 2020/03/01
 */
public enum AccountBillStatusEnum {

    /**
     * 已完成
     */
    FINISHED("已完成"),


    /**
     * 待执行
     * 需要后续执行结算，手动触发
     */
    WAITEXECUT("待执行"),

    /**
     * 执行中
     */
    EXECUTING("执行中"),

    /**
     * 由于安全等原因，、冻结
     */
    CANCELED("已撤销");

    private String alias;

    AccountBillStatusEnum(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getCode() {
        return this.name();
    }

}
