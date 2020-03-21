/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.bc.server.listeners;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tech.bitstwinkle.jelly.commons.ids.JellyIds;
import tech.bitstwinkle.jelly.jubao.bc.facade.domains.BillingTask;
import tech.bitstwinkle.jelly.jubao.bc.facade.mq.BillingMessageReq;
import tech.bitstwinkle.jelly.jubao.bc.server.engine.RuleEngine;
import tech.bitstwinkle.jelly.platform.mq.BaseMessageListener;
import tech.bitstwinkle.jelly.platform.mq.MessageCodec;
import tech.bitstwinkle.jelly.platform.mq.MessageConsumeResultEnum;
import tech.bitstwinkle.jelly.platform.mq.MessageSendHelper;

/**
 * @author suuyoo.wg on 2020/3/21
 */
public class BillingCalcListener extends BaseMessageListener<BillingMessageReq> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BillingCalcListener.class);

  @Autowired
  private RuleEngine ruleEngine;


  private ProducerBean execProducer;
  private String execMsgTopic = "BILLING_TOPIC";
  private String execMsgTag = "BILLING_EXEC";


  @Override
  public Class<BillingMessageReq> getPayloadClass() {
    return BillingMessageReq.class;
  }

  @Override
  public MessageConsumeResultEnum onConsume(BillingMessageReq payload) {
    LOGGER.info("calc for {}", payload);
    BillingTask billingTask = ruleEngine.calculate(payload.getBizCode(), payload.getContext());
    if (execProducer != null) {

      MessageSendHelper
          .send(execProducer, new Message(execMsgTopic, execMsgTag, JellyIds.uniqueId(),
              MessageCodec.encode(billingTask)));
    }

    LOGGER.info("billing task => {}", billingTask);
    return MessageConsumeResultEnum.SUCCESS;
  }

  public void setExecProducer(ProducerBean execProducer) {
    this.execProducer = execProducer;
  }

  public void setExecMsgTopic(String execMsgTopic) {
    this.execMsgTopic = execMsgTopic;
  }

  public void setExecMsgTag(String execMsgTag) {
    this.execMsgTag = execMsgTag;
  }
}
