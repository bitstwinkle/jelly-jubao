/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.server.components;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.bitstwinkle.jelly.commons.errors.JellyException;
import tech.bitstwinkle.jelly.jubao.ac.facade.errors.AccountErrorEnum;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountBillEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountBillEntityRepository;

/**
 * 账单组件
 *
 * @author suuyoo.wg on 2019/10/9
 */
@Component
public class BillComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillComponent.class);

    @Autowired
    private AccountBillEntityRepository accountBillEntityRepository;


    /**
     * 装载AccountBillEntity，若不存在则跑出异常
     *
     * @param billid
     * @return
     */
    public AccountBillEntity loadBillEntity(String billid) {
        Optional<AccountBillEntity> optional = accountBillEntityRepository.findById(billid);
        if (!optional.isPresent()) {
            throw new JellyException(AccountErrorEnum.ACCOUNT_BILL_MISS.getError(billid));
        }
        return optional.get();
    }

}
