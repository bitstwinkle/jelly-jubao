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
 * 账户解冻
 *
 * @author suuyoo.wg on 2020/03/01
 */
public class AccountUnfreezeReq extends Request {

  private static final long serialVersionUID = -5033087045433791311L;

  private String accid;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("accid", accid)
        .toString();
  }

  public String getAccid() {
    return accid;
  }

  public void setAccid(String accid) {
    this.accid = accid;
  }
}
