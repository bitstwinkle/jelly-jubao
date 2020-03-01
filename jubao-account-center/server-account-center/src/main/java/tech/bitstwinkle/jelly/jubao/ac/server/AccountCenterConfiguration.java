/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *  
 */

package tech.bitstwinkle.jelly.jubao.ac.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.bitstwinkle.jelly.idfactory.JellyIdFactory;
import tech.bitstwinkle.jelly.idfactory.JellyIdGenerator;
import tech.bitstwinkle.jelly.idfactory.domains.Sequence;
import tech.bitstwinkle.jelly.jubao.ac.server.IdGeneratorConst.ACCID;
import tech.bitstwinkle.jelly.jubao.ac.server.IdGeneratorConst.ACCOUNT_BILLID;

/**
 * @author suuyoo.wg on 2019/10/7
 */
@Configuration
public class AccountCenterConfiguration {

  @Autowired
  private JellyIdFactory jellyIdFactory;

  @Bean(ACCID.BEAN)
  public JellyIdGenerator accidGenerator(){
    Sequence sequence = new Sequence();
    sequence.setBizCode(ACCID.BIZ_CODE);
    sequence.setMin(ACCID.SEQ_MIN);
    sequence.setMax(ACCID.SEQ_MAX);
    sequence.setStep(ACCID.SEQ_STEP);
    JellyIdGenerator wattIdGenerator = jellyIdFactory.createIdGenerator(ACCID.TIMESTAMP_TPL, sequence);
    return wattIdGenerator;
  }

  @Bean(ACCOUNT_BILLID.BEAN)
  public JellyIdGenerator accountBillidGenerator(){
    Sequence sequence = new Sequence();
    sequence.setBizCode(ACCOUNT_BILLID.BIZ_CODE);
    sequence.setMin(ACCOUNT_BILLID.SEQ_MIN);
    sequence.setMax(ACCOUNT_BILLID.SEQ_MAX);
    sequence.setStep(ACCOUNT_BILLID.SEQ_STEP);
    JellyIdGenerator wattIdGenerator = jellyIdFactory.createIdGenerator(ACCOUNT_BILLID.TIMESTAMP_TPL, sequence);
    return wattIdGenerator;
  }

}
