/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.server;

/**
 * @author suuyoo.wg on 2020/03/01
 */
public final class IdGeneratorConst {

  public interface ACCID {
    /**
     * ACCID 14位时间戳 + 4位SEQ + 1校验位
     *
     * 每秒最多产生9999账户
     */
    String BEAN = "JUBAO_ACCOUNT_ID_GENERATOR";
    String TIMESTAMP_TPL = "yyyyMMddHHmmss";
    String BIZ_CODE = "UNIQUE_JUBAO_ACCOUNT_ID";
    long SEQ_MIN = 1L;
    long SEQ_MAX = 9999L;
    long SEQ_STEP = 1000L;
  }

  public interface ACCOUNT_BILLID {
    /**
     * ACCOUNT_BILLID 14位时间戳 + 9位SEQ + 1校验位
     *
     * 每秒最多产生999999999流水
     */
    String BEAN = "JUBAO_ACCOUNT_BILL_ID_GENERATOR";
    String TIMESTAMP_TPL = "yyyyMMddHHmmss";
    String BIZ_CODE = "UNIQUE_JUBAO_ACCOUNT_BILL_ID";
    long SEQ_MIN = 1L;
    long SEQ_MAX = 99999999L;
    long SEQ_STEP = 10000L;
  }

}
