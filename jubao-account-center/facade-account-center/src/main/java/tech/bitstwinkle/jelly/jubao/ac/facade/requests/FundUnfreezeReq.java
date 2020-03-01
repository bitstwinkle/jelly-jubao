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
 * 资金解冻
 * @author suuyoo.wg on 2020/3/1
 */
public class FundUnfreezeReq extends Request {

  private static final long serialVersionUID = 664344350612875771L;

  private String freezeSheetId;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("freezeSheetId", freezeSheetId)
        .toString();
  }

  public String getFreezeSheetId() {
    return freezeSheetId;
  }

  public void setFreezeSheetId(String freezeSheetId) {
    this.freezeSheetId = freezeSheetId;
  }
}
