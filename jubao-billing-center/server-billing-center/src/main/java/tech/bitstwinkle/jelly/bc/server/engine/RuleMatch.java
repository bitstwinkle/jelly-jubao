/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.bc.server.engine;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.LoggerFactory;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public class RuleMatch {

  private static final Lock lock = new ReentrantLock();

  private ExpressionWrapper matchWrapper;

  public RuleMatch(String strExpression){
    this.matchWrapper = new ExpressionWrapper(strExpression);
  }

  public boolean isMatched(Map<String, Object> context){

    Object obj = this.matchWrapper.execute(context);
    if(obj==null){
      return false;
    }

    if(obj instanceof Boolean){
      return (Boolean)obj;
    }

    return false;
  }

}
