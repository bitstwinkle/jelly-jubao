/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

##
## 计费中心规则主表
##
DROP TABLE IF EXISTS `billing_center_rule`;
CREATE TABLE `billing_center_rule`
(
    `auto_id`      bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `gmt_create`   datetime            NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据插入时间',
    `gmt_modified` datetime                     DEFAULT NULL COMMENT '数据更改时间',
    `ruleid`       varchar(32)         NOT NULL COMMENT '规则id',
    `biz_code`     varchar(128)        NOT NULL DEFAULT '' COMMENT '账户业务类型',
    `biz_match`    varchar(100)                 DEFAULT NULL COMMENT '匹配信息',
    `rule_type`    varchar(32)         NOT NULL DEFAULT '' COMMENT '规则类型',
    `rule_script`  text                         DEFAULT NULL COMMENT '规则脚本',
    `status`       varchar(32)         NOT NULL COMMENT '规则状态',
    `memo`         varchar(200)                 DEFAULT NULL COMMENT '简要描述',
    PRIMARY KEY (`ruleid`),
    UNIQUE KEY `auto_id` (`auto_id`),
    KEY `idx_biz_code` (`biz_code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='计费中心规则主表';