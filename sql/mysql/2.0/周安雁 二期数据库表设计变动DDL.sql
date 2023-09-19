DROP TABLE IF EXISTS `kugga_db`.`duke_league_auth`;
DROP TABLE IF EXISTS `kugga_db`.`duke_league_rule_template`;
DROP TABLE IF EXISTS `kugga_db`.`duke_task_auth_league`;
ALTER TABLE duke_league_task RENAME duke_task;

ALTER TABLE `kugga_db`.`duke_task`
    MODIFY COLUMN `status` int NOT NULL COMMENT '任务状态 0待支付 1已发布 2被接单 3已完成 4待退款 5已退款' AFTER `type`;

ALTER TABLE `kugga_db`.`duke_task`
    MODIFY COLUMN `order_record` varchar(256) NULL DEFAULT NULL COMMENT '任务相关的订单记录' AFTER `amount`;

ALTER TABLE duke_task RENAME COLUMN generics_params to business_params;

ALTER TABLE `kugga_db`.`duke_task`
    MODIFY COLUMN `business_params` varchar(256) NULL DEFAULT NULL COMMENT '业务参数' AFTER `order_record`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    DROP COLUMN `use_league_name`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    DROP COLUMN `use_user_last_name`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    DROP COLUMN `use_user_first_name`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    DROP COLUMN `by_league_name`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    DROP COLUMN `by_user_last_name`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    DROP COLUMN `by_user_first_name`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    DROP COLUMN `call_back_url`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    DROP COLUMN `order_no`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    MODIFY COLUMN `task_type` int NOT NULL COMMENT '任务类型  1 推荐报告 2 认证 3 聊天' AFTER `task_id`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    MODIFY COLUMN `use_league_id` bigint NULL DEFAULT NULL COMMENT '主动 公会ID' AFTER `use_user_id`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    MODIFY COLUMN `by_league_id` bigint NULL DEFAULT NULL COMMENT '被动 公会ID' AFTER `by_user_id`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    ADD COLUMN `pay_type` int NOT NULL COMMENT '付费类型 0免费 1付费' AFTER `by_league_id`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    MODIFY COLUMN `amount` decimal(10, 2) NOT NULL COMMENT '公告栏展示金额' AFTER `pay_type`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    MODIFY COLUMN `expires_time` datetime NULL DEFAULT NULL COMMENT '失效时间' AFTER `amount`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    ADD COLUMN `creator` varchar(64) NOT NULL COMMENT '创建者' AFTER `expires_time`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    MODIFY COLUMN `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' AFTER `creator`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    ADD COLUMN `updater` varchar(64) NOT NULL COMMENT '更新者' AFTER `create_time`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    ADD COLUMN `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' AFTER `updater`;

CREATE TABLE `kugga_db`.`duke_business_params`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `business_id` varchar(64) NOT NULL COMMENT '业务ID 比如任务ID等',
    `params`      json        NOT NULL COMMENT '业务参数',
    `creator`     varchar(64) NOT NULL COMMENT '创建者',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) NOT NULL COMMENT '更新者',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '业务参数表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `kugga_db`.`duke_task_chat`
(
    `id`            bigint         NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `task_chat_key` varchar(128)   NOT NULL COMMENT '任务聊天key',
    `task_id`       varchar(255)   NOT NULL COMMENT '任务ID',
    `notice_id`     bigint         NULL     DEFAULT NULL COMMENT '公会公告栏ID',
    `app_order_no`  varchar(64)    NOT NULL COMMENT '内部订单号',
    `amount`        decimal(10, 2) NOT NULL COMMENT '推荐报告金额',
    `pay_type`      int            NOT NULL COMMENT '付费类型 0免费 1付费',
    `status`        varchar(32)    NOT NULL COMMENT '状态 0未支付 1已支付 2已分账 3待退款 4已退款',
    `creator`       varchar(64)    NOT NULL COMMENT '创建者',
    `create_time`   datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`       varchar(64)    NOT NULL COMMENT '更新者',
    `update_time`   datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)         NULL     DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '任务聊天表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `kugga_db`.`duke_task_league_auth`
(
    `id`           bigint         NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `task_id`      varchar(255)   NOT NULL COMMENT '任务ID',
    `notice_id`    bigint         NULL     DEFAULT NULL COMMENT '公会公告栏ID',
    `app_order_no` varchar(64)    NOT NULL COMMENT '内部订单号',
    `amount`       decimal(10, 2) NOT NULL COMMENT '推荐报告金额',
    `pay_type`     int            NOT NULL COMMENT '付费类型 0免费 1付费',
    `status`       varchar(32)    NOT NULL COMMENT '状态 0未支付 1已支付 2已分账 3待退款 4已退款',
    `creator`      varchar(64)    NOT NULL COMMENT '创建者',
    `create_time`  datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64)    NOT NULL COMMENT '更新者',
    `update_time`  datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)         NULL     DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '任务 公会认证 订单表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `kugga_db`.`duke_task_report`
(
    `id`           bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '推荐报告ID',
    `task_id`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID',
    `notice_id`    bigint                                                        NULL     DEFAULT NULL COMMENT '公会公告栏ID',
    `app_order_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '内部订单号',
    `amount`       decimal(10, 2)                                                NOT NULL COMMENT '推荐报告金额',
    `status`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci        NOT NULL COMMENT '状态 0未支付 1已支付 2已分账 3已退款',
    `pay_type`     int                                                           NOT NULL COMMENT '付费类型 0免费 1付费',
    `expires_time` datetime                                                      NOT NULL COMMENT '失效时间',
    `creator`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '创建者',
    `create_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '更新者',
    `update_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)                                                        NULL     DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '任务 写推荐报告 订单表'
  ROW_FORMAT = DYNAMIC;

ALTER TABLE `kugga_db`.`duke_task`
    ADD COLUMN `pay_type` int NOT NULL COMMENT '付费类型 0免费 1付费' AFTER `status`;

ALTER TABLE `kugga_db`.`duke_task`
    MODIFY COLUMN `expires_time` datetime NOT NULL COMMENT '失效时间' AFTER `business_params`;

ALTER TABLE `kugga_db`.`duke_task`
    ADD COLUMN `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建者' AFTER `expires_time`;

ALTER TABLE `kugga_db`.`duke_task`
    MODIFY COLUMN `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' AFTER `creator`;

ALTER TABLE `kugga_db`.`duke_task`
    ADD COLUMN `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新者' AFTER `create_time`;

ALTER TABLE `kugga_db`.`duke_task`
    MODIFY COLUMN `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' AFTER `updater`;

ALTER TABLE `kugga_db`.`duke_task`
    ADD COLUMN `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除' AFTER `update_time`;

ALTER TABLE `kugga_db`.`duke_task_league_auth`
    ADD COLUMN `by_auth_league_id` bigint NOT NULL COMMENT '被认证公会ID' AFTER `id`;

ALTER TABLE `kugga_db`.`duke_task_league_auth`
    MODIFY COLUMN `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID' AFTER `by_auth_league_id`;

ALTER TABLE `kugga_db`.`duke_task_league_auth`
    MODIFY COLUMN `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态 0未支付 1已支付 2已分账 3待退款 4已退款' AFTER `pay_type`;

ALTER TABLE `kugga_db`.`duke_league_notice`
    ADD COLUMN `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除' AFTER `update_time`;
