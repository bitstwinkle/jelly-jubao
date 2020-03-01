/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.server.dal;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author suuyoo.wg on 2019/10/7
 */
public interface AccountBillEntityRepository extends JpaRepository<AccountBillEntity, String> {

  /**
   * 幂等判断
   * @param idempotentId
   * @return
   */
  boolean existsByIdempotentId(String idempotentId);

  /**
   * 根据幂等ID获取对应实体
   * @param idempotentId
   * @return
   */
  AccountBillEntity getByIdempotentId(String idempotentId);

}
