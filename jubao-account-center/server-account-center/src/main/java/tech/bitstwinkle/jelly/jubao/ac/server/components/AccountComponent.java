/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.jubao.ac.server.components;

import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tech.bitstwinkle.jelly.commons.asserts.RequestAssert;
import tech.bitstwinkle.jelly.commons.errors.JellyException;
import tech.bitstwinkle.jelly.idfactory.JellyIdGenerator;
import tech.bitstwinkle.jelly.jubao.ac.facade.enums.AccountStatusEnum;
import tech.bitstwinkle.jelly.jubao.ac.facade.errors.AccountErrorEnum;
import tech.bitstwinkle.jelly.jubao.ac.server.IdGeneratorConst.ACCID;
import tech.bitstwinkle.jelly.jubao.ac.server.IdGeneratorConst.ACCOUNT_BILLID;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountEntity;
import tech.bitstwinkle.jelly.jubao.ac.server.dal.AccountEntityRepository;
import tech.bitstwinkle.jelly.platform.clock.JellyClock;
import tech.bitstwinkle.jelly.platform.tx.JellyTxTemplate;

/**
 * 账户组件
 *
 * @author suuyoo.wg on 2020/03/01
 */
@Component
public class AccountComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountComponent.class);

    @Autowired
    private JellyClock jellyClock;

    @Resource
    @Qualifier(ACCID.BEAN)
    private JellyIdGenerator accidGenerator;

    @Resource
    @Qualifier(ACCOUNT_BILLID.BEAN)
    private JellyIdGenerator billidGenerator;

    @Autowired
    private JellyTxTemplate jellyTx;

    @Autowired
    private AccountEntityRepository accountEntityRepository;

    /**
     * 创建账户
     * @param accountEntity
     * @return
     */
    public AccountEntity createAccountEntity(AccountEntity accountEntity) {
        RequestAssert.notNull(accountEntity, "accountEntity");

        /**
         * 根据bizCode 和 bizId 判断是否存在
         */
        boolean existsByBiz = accountEntityRepository
                .existsByBizIdAndBizCode(accountEntity.getBizId(), accountEntity.getBizCode());

        if (existsByBiz) {
            throw new JellyException(AccountErrorEnum.ACCOUNT_EXIST
                    .getError(accountEntity.getBizId(), accountEntity.getBizCode()));
        }

        String accid = accidGenerator.generateId();
        accountEntity.setAccid(accid);

        jellyTx.execute(status -> {
            accountEntityRepository.save(accountEntity);
            return true;
        });

        return accountEntity;
    }

    /**
     * 设置账户状态
     * @param accid
     * @param targetStatus
     * @return
     */
    public AccountEntity setAccountStatus(String accid, AccountStatusEnum targetStatus){
        RequestAssert.notBlank(accid, "accid");
        RequestAssert.notNull(targetStatus, "targetStatus");
        AccountEntity accountEntity = loadAccountEntity(accid);
        if(accountEntity.getStatus()==targetStatus){
            return accountEntity;
        }
        accountEntity.setStatus(targetStatus);
        jellyTx.execute(status -> {
            accountEntityRepository.saveAndFlush(accountEntity);
            return true;
        });

        return accountEntity;
    }

    /**
     * 装载AccountEntity，若不存在则抛出异常
     *
     * @param accid
     * @return
     */
    public AccountEntity loadAccountEntity(String accid) {
        Optional<AccountEntity> optional = accountEntityRepository.findById(accid);
        if (!optional.isPresent()) {
            throw new JellyException(AccountErrorEnum.ACCOUNT_MISS.getError(accid));
        }
        return optional.get();
    }

    /**
     * 判断账户是否有效
     *
     * @param accountEntity
     */
    public void checkAccount(AccountEntity accountEntity) {
        if (!accountEntity.getStatus().isAvailable()) {
            throw new JellyException(AccountErrorEnum.ACCOUNT_INVALID.getError(accountEntity.getAccid(), accountEntity.getStatus()));
        }
    }


    /**
     * 通过
     *
     * @param bizid
     * @return
     */
    public AccountEntity getAccountEntityByMidAndBizCode(String bizid, String bizCode) {
        AccountEntity optional = accountEntityRepository.findByBizIdAndBizCode(bizid, bizCode);
        return optional;
    }

}
