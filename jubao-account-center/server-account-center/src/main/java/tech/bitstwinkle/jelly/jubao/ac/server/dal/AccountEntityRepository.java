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
public interface AccountEntityRepository extends JpaRepository<AccountEntity, String> {

    /**
     * 根据业务信息判断是否已经存在
     *
     * @param bizId
     * @param bizCode
     * @return
     */
    boolean existsByBizIdAndBizCode(String bizId, String bizCode);


    /**
     * 根据业务信息判断是否已经存在
     *
     * @param bizId
     * @param bizCode
     * @return
     */
    AccountEntity findByBizIdAndBizCode(String bizId, String bizCode);

}
