/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

##
## 账户中心账户主表
##
DROP TABLE IF EXISTS `account_center_account`;
CREATE TABLE `account_center_account`
(
    `auto_id`       bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `gmt_create`    datetime            NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据插入时间',
    `gmt_modified`  datetime                     DEFAULT NULL COMMENT '数据更改时间',
    `accid`         varchar(19)         NOT NULL COMMENT '账户id',
    `biz_code`      varchar(32)         NOT NULL DEFAULT '' COMMENT '账户业务类型',
    `biz_id`        varchar(32)         NOT NULL DEFAULT '' COMMENT '账号的业务码',
    `subject_type`  varchar(32)         NOT NULL COMMENT '账户拥有方主体类型',
    `subject_id`    varchar(32)         NOT NULL COMMENT '账户拥有方主体ID',
    `currency`      varchar(32)         NOT NULL COMMENT '账户币种',
    `balance`       decimal(20, 4)      NOT NULL COMMENT '账户余额',
    `freeze_amount` decimal(20, 4)               DEFAULT NULL COMMENT '账户冻结金额',
    `system_amount` decimal(20, 4)               DEFAULT NULL COMMENT '账户系统金额',
    `status`        varchar(32)         NOT NULL COMMENT '账户状态',
    `memo`          varchar(200)                 DEFAULT NULL COMMENT '账户说明',
    `version`       bigint(20) unsigned NOT NULL COMMENT '乐观锁',
    PRIMARY KEY (`accid`),
    UNIQUE KEY `auto_id` (`auto_id`),
    UNIQUE KEY `uk_biz_info` (`biz_id`, `biz_code`),
    KEY `idx_owner` (`subject_id`, `subject_type`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='账户中心账户主表';

##
## 账户中心账户对账单
##
DROP TABLE IF EXISTS `account_center_bill`;
CREATE TABLE `account_center_bill`
(
    `auto_id`       bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增编号',
    `gmt_create`    datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`  datetime                     DEFAULT NULL COMMENT '最后修改时间',
    `idempotent_id` varchar(32)         NOT NULL COMMENT '幂等ID',
    `billid`        varchar(24)         NOT NULL COMMENT '单据编号',
    `accid`         varchar(19)         NOT NULL COMMENT '对应账户ID',
    `direction`     varchar(10)         NOT NULL COMMENT '资金方向',
    `fund_channel`  varchar(32)         NOT NULL COMMENT '资金渠道',
    `amount`        decimal(20, 4)      NOT NULL COMMENT '金额',
    `biz_code`      varchar(32)         NOT NULL COMMENT '业务编码',
    `biz_id`        varchar(32)         NOT NULL COMMENT '业务编号',
    `bill_dt`       datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '业务时间',
    `memo`          varchar(200)        NOT NULL COMMENT '业务描述',
    `biz_paras`     json                         DEFAULT NULL COMMENT '业务参数',
    `after_balance` decimal(20, 4)      NOT NULL COMMENT '操作后资金余额',
    `status`        varchar(16)         NOT NULL COMMENT '状态',
    PRIMARY KEY (`billid`),
    UNIQUE KEY `uk_auto_id` (`auto_id`),
    UNIQUE KEY `uk_idempotent_id` (`idempotent_id`),
    KEY `idx_auto_id` (`auto_id`),
    KEY `idx_biz_id` (`biz_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='账户单据表';

##
## 账户中心资金冻结解冻工单表
##
DROP TABLE IF EXISTS `account_center_freeze_sheet`;
CREATE TABLE `account_center_freeze_sheet`
(
    `auto_id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `gmt_create`            datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    `gmt_modified`          datetime                     DEFAULT NULL COMMENT '记录最后修改时间',
    `idempotent_id` varchar(32)         NOT NULL COMMENT '幂等ID',
    `sheet_id`              varchar(32)         NOT NULL COMMENT '冻结单据编号(UUID)',
    `accid`                 varchar(19)         NOT NULL COMMENT '关联的账户号',
    `freeze_type`           varchar(20)         NOT NULL COMMENT '冻结类型',
    `amount`                decimal(20, 4)      NOT NULL COMMENT '冻结金额',
    `status`                varchar(32)         NOT NULL COMMENT '冻结状态',
    `unfreeze_trigger_type` varchar(32)         NOT NULL COMMENT '解冻触发器类型',
    `expect_unfreeze_dt`    datetime                     DEFAULT NULL COMMENT '预期解冻时间',
    `freeze_dt`             datetime            NOT NULL COMMENT '冻结时间',
    `unfreeze_dt`           datetime                     DEFAULT NULL COMMENT '实际解冻时间',
    `memo`                  varchar(200)        NOT NULL COMMENT '冻结描述',
    `version`               bigint(20) unsigned NOT NULL COMMENT '乐观锁',
    PRIMARY KEY (`sheet_id`),
    UNIQUE KEY `uk_auto_id` (`auto_id`),
    UNIQUE KEY `uk_idempotent_id` (`idempotent_id`),
    KEY `idx_accid` (`accid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='账户中心资金冻结解冻工单表';

##
## 账户中心资金锁定工单表
##
DROP TABLE IF EXISTS `account_center_lock_sheet`;
CREATE TABLE `account_center_lock_sheet`
(
    `auto_id`      bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增编号',
    `gmt_create`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime                     DEFAULT NULL COMMENT '最后修改时间',
    `idempotent_id` varchar(32)         NOT NULL COMMENT '幂等ID',
    `sheet_id`     varchar(32)         NOT NULL DEFAULT '' COMMENT '锁定单据编号(UUID)',
    `accid`        varchar(19)         NOT NULL COMMENT '对应账户ID',
    `billid`       varchar(24)         NOT NULL COMMENT '关联的账单ID',
    `direction`    varchar(10)         NOT NULL COMMENT '资金方向',
    `amount`       decimal(20, 4)      NOT NULL COMMENT '金额',
    `status`       varchar(32)         NOT NULL COMMENT '锁定状态',
    `biz_code`     varchar(32)         NOT NULL COMMENT '业务编码',
    `biz_id`       varchar(32)         NOT NULL COMMENT '业务编号',
    `lock_dt`      datetime            NOT NULL COMMENT '锁定时间',
    `lock_adv_dt`  datetime                     DEFAULT NULL COMMENT '锁定推进时间',
    `memo`         varchar(100)        NOT NULL COMMENT '业务描述',
    `version`      bigint(20) unsigned NOT NULL COMMENT '乐观锁',
    PRIMARY KEY (`sheet_id`),
    UNIQUE KEY `uk_auto_id` (`auto_id`),
    UNIQUE KEY `uk_idempotent_id` (`idempotent_id`),
    KEY `idx_accid` (`accid`),
    KEY `idx_billid` (`billid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='账户中心资金锁定工单表';