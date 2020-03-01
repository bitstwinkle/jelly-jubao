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
import tech.bitstwinkle.jelly.commons.proto.Request;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public class WithdrawCancelReq extends Request {

  private static final long serialVersionUID = -6400304093817176645L;

  private String withdrawApplyId;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("withdrawApplyId", withdrawApplyId)
        .toString();
  }

  public String getWithdrawApplyId() {
    return withdrawApplyId;
  }

  public void setWithdrawApplyId(String withdrawApplyId) {
    this.withdrawApplyId = withdrawApplyId;
  }
}
