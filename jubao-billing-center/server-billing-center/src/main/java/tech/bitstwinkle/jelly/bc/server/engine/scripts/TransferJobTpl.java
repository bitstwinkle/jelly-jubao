/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.bc.server.engine.scripts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import tech.bitstwinkle.jelly.commons.asserts.RequestAssert;
import tech.bitstwinkle.jelly.bc.facade.domains.TransferJob;
import tech.bitstwinkle.jelly.bc.server.engine.ExpressionWrapper;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public class TransferJobTpl {

  private ExpressionWrapper destWrapper;
  private ExpressionWrapper amountWrapper;

  public void setDest(String dest) {
    this.destWrapper = new ExpressionWrapper(dest);
  }

  public void setAmount(String amount) {
    this.amountWrapper = new ExpressionWrapper(amount);
  }

  public TransferJob execute(Map<String, Object> context){
    TransferJob transferJob = new TransferJob();
    Object destObj = destWrapper.execute(context);
    RequestAssert.notNull(destObj, "dest");
    RequestAssert.isTrue((destObj instanceof String), "dest not string, it is " + destObj);
    transferJob.setDest((String)destObj);

    Object amountObj = amountWrapper.execute(context);
    RequestAssert.notNull(amountObj, "amount");
    RequestAssert.isTrue((amountObj instanceof Number), "amount not number, it is " + amountObj);
    Number amountNumber = (Number)amountObj;
    transferJob.setAmount(new BigDecimal(amountNumber.doubleValue()).setScale(4, RoundingMode.HALF_UP));

    return transferJob;
  }
}
