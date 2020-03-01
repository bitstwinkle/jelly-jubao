/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.bc.server.engine.scripts;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import tech.bitstwinkle.jelly.bc.facade.enums.RuleTypeEnum;
import tech.bitstwinkle.jelly.bc.facade.domains.BillingTask;
import tech.bitstwinkle.jelly.bc.server.engine.RuleScript;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public class InProportionScript implements RuleScript {

  private static final Lock lock = new ReentrantLock();

  private String strBillingTaskTpl;

  private BillingTaskTpl billingTaskTpl;
  private boolean canBeCompile = true;

  public InProportionScript(String strBillingTaskTpl){
    this.strBillingTaskTpl = strBillingTaskTpl;
  }

  @Override
  public RuleTypeEnum getType() {
    return RuleTypeEnum.IN_PROPORTION;
  }

  @Override
  public BillingTask execute(Map<String, Object> context) {
    if(!canBeCompile){
      return null;
    }
    compile();
    return billingTaskTpl.execute(context);
  }

  private void compile(){
    if(this.billingTaskTpl==null){
      lock.lock();
      try{
        if(this.billingTaskTpl==null){
          this.billingTaskTpl = JSONObject.parseObject(this.strBillingTaskTpl, BillingTaskTpl.class);
          this.canBeCompile = this.billingTaskTpl!=null;
        }
      }catch (Throwable throwable){
        this.canBeCompile = false;
      }finally {
        lock.unlock();
      }
    }
  }

}
