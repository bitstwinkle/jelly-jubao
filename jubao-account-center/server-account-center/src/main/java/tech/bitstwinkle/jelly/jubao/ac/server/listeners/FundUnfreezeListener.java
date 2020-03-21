/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.server.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import tech.bitstwinkle.jelly.jubao.ac.server.components.FundFreezeComponent;
import tech.bitstwinkle.jelly.platform.mq.BaseMessageListener;
import tech.bitstwinkle.jelly.platform.mq.MessageConsumeResultEnum;

/**
 * 监听解冻消息执行解冻
 * @author suuyoo.wg on 2020/3/21
 */
public class FundUnfreezeListener extends BaseMessageListener<String> {

  @Autowired
  private FundFreezeComponent fundFreezeComponent;

  @Override
  public Class<String> getPayloadClass() {
    return String.class;
  }

  @Override
  public MessageConsumeResultEnum onConsume(String payload) {
    String freezeSheetId = payload;
    fundFreezeComponent.unfreeze(freezeSheetId);
    return MessageConsumeResultEnum.SUCCESS;
  }
}
