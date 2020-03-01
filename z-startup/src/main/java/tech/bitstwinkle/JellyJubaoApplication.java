/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author suuyoo.wg on 2020/3/1
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableJpaAuditing
@EnableTransactionManagement
@EnableFeignClients("tech.bitstwinkle")
public class JellyJubaoApplication {

  public static void main(String[] args) {
    SpringApplication.run(JellyJubaoApplication.class, args);
  }

}
