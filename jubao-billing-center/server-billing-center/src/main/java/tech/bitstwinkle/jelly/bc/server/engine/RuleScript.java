/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.bc.server.engine;

import java.util.Map;
import tech.bitstwinkle.jelly.bc.facade.enums.RuleTypeEnum;
import tech.bitstwinkle.jelly.bc.facade.domains.BillingTask;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public interface RuleScript {

  RuleTypeEnum getType();

  BillingTask execute(Map<String, Object> context);

}
