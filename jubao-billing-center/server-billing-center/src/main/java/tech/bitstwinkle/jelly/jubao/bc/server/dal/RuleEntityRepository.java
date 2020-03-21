/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.bc.server.dal;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.bitstwinkle.jelly.jubao.bc.facade.enums.RuleStatusEnum;

/**
 * @author suuyoo.wg on 2019/10/7
 */
public interface RuleEntityRepository extends JpaRepository<RuleEntity, String> {

  /**
   * 根据业务信息获取规则列表
   *
   * @param bizCode
   * @param status
   * @return
   */
  List<RuleEntity> findAllByBizCodeAndStatus(String bizCode, RuleStatusEnum status);

}
