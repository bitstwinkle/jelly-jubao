/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.bc.server.engine;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.LoggerFactory;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public class ExpressionWrapper {
  private static final Lock lock = new ReentrantLock();

  private String strExpression;

  public ExpressionWrapper(String strExpression){
    this.strExpression = strExpression;
  }

  private Expression expression;
  private boolean canBeCompiled = true;

  public Object execute(Map<String, Object> context){
    if(!canBeCompiled){
      return null;
    }

    Expression expression = this.loadExpression();
    if(expression!=null) {
      Object obj = this.expression.execute(context);
      return obj;
    }else{
      return null;
    }
  }

  private Expression loadExpression(){
    if(this.expression==null){
      lock.lock();
      try{
        if(this.expression==null){
          this.expression = this.compileExpression();
          if(this.expression==null){
            this.canBeCompiled = false;
          }else{
            releaseStrExpresion();
          }
        }
      }finally {
        lock.unlock();
      }
    }
    return this.expression;
  }

  private Expression compileExpression(){
    Expression exp;
    try {
      exp = AviatorEvaluator.compile(strExpression);
    }catch (Throwable throwable){
      exp = null;
      LoggerFactory.getLogger(RuleMatch.class).error("can not compile the expression", throwable);
    }
    return exp;
  }

  private void releaseStrExpresion(){
    this.strExpression = null;
  }
}
