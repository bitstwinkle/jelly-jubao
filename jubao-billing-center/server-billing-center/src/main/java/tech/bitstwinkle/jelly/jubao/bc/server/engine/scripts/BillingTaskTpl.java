/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.bc.server.engine.scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tech.bitstwinkle.jelly.commons.asserts.RequestAssert;
import tech.bitstwinkle.jelly.jubao.bc.facade.domains.BillingTask;
import tech.bitstwinkle.jelly.jubao.bc.facade.domains.TransferJob;
import tech.bitstwinkle.jelly.jubao.bc.server.engine.ExpressionWrapper;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public class BillingTaskTpl {

  private ExpressionWrapper strWrapper;

  private List<TransferJobTpl> transfers;

  public BillingTask execute(Map<String, Object> context){
    BillingTask billingTask = new BillingTask();
    Object srcObj = strWrapper.execute(context);
    RequestAssert.notNull(srcObj, "src");
    RequestAssert.isTrue((srcObj instanceof String), "src not string");
    billingTask.setSrc((String)srcObj);

    if(transfers!=null && !transfers.isEmpty()) {
      List<TransferJob> list = new ArrayList<>(transfers.size());
      for (TransferJobTpl tpl : transfers){
        TransferJob job = tpl.execute(context);
        list.add(job);
      }
      billingTask.setTransfers(list);
    }
    return billingTask;
  }

  public void setSrc(String src){
    this.strWrapper = new ExpressionWrapper(src);
  }

  public void setTransfers(List<TransferJobTpl> transfers){
    this.transfers = transfers;
  }

}
