/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.bc.facade.requests;

import com.google.common.base.MoreObjects;
import java.util.Map;
import tech.bitstwinkle.jelly.commons.proto.Request;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public class BillingConsultReq extends Request {

  private static final long serialVersionUID = -7211393987098177991L;

  private String bizCode;

  private Map<String, Object> context;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("bizCode", bizCode)
        .add("context", context)
        .toString();
  }

  public String getBizCode() {
    return bizCode;
  }

  public void setBizCode(String bizCode) {
    this.bizCode = bizCode;
  }

  public Map<String, Object> getContext() {
    return context;
  }

  public void setContext(Map<String, Object> context) {
    this.context = context;
  }
}
