DROP TABLE IF EXISTS `duke_red_packet_order`;
CREATE TABLE `duke_red_packet_order`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
    `app_order_no` varchar(64)  NULL DEFAULT NULL COMMENT '内部订单号',
    `wallet_order_no` varchar(64)  NULL DEFAULT NULL COMMENT '钱包订单号',
    `payer_id` bigint(20) NOT NULL COMMENT '付款方id',
    `account_type` varchar(24)  NULL DEFAULT NULL COMMENT '付款方账户类型',
    `account_id` varchar(64)  NOT NULL COMMENT '钱包账户',
    `pay_channel` varchar(24)  NULL DEFAULT 'balance' COMMENT '支付方式【balance：余额支付，paypal：paypal支付】',
    `amount` decimal(13, 2) NULL DEFAULT NULL COMMENT '红包总金额',
    `fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '手续费',
    `currency` varchar(24)  NULL DEFAULT NULL COMMENT '币种',
    `status` varchar(24)  NOT NULL DEFAULT 'draft' COMMENT '状态： draft 待发放；processing 处理中；success 发放成功；failed 发放失败；',
    `complete_time` varchar(24)  NULL DEFAULT NULL COMMENT '完成时间',
    `remark` varchar(256)  NULL DEFAULT NULL COMMENT '备注',
    `creator` varchar(64)  NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64)  NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_order_no`(`app_order_no`) USING BTREE
) ENGINE = InnoDB COMMENT = '红包订单表' ROW_FORMAT = DYNAMIC;


DROP TABLE IF EXISTS `duke_red_packet_order_detail`;
CREATE TABLE `duke_red_packet_order_detail`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单明细id',
    `app_order_no` varchar(64)  NULL DEFAULT NULL COMMENT '内部订单号',
    `receiver_id` bigint(20) NULL DEFAULT NULL COMMENT '收款方id',
    `account_type` varchar(64)  NULL DEFAULT NULL COMMENT '收款方账户类型',
    `account_id` varchar(64)  NULL DEFAULT NULL COMMENT '收款方钱包账户',
    `amount` decimal(13, 2) NULL DEFAULT NULL COMMENT '收款金额',
    `remark` varchar(64)  NULL DEFAULT NULL COMMENT '备注',
    `creator` varchar(64)  NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64)  NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '红包订单详情表' ROW_FORMAT = DYNAMIC;

ALTER TABLE duke_league_member ADD growth_value BIGINT DEFAULT 10 COMMENT '成长值' AFTER join_time;
ALTER TABLE duke_league_member ADD growth_level INT DEFAULT 1 COMMENT '成长等级' AFTER growth_value;
-- 初始化用户公会等级
UPDATE duke_league_member SET growth_value = 10, growth_level = 1 WHERE deleted = 0;

DROP TABLE IF EXISTS `duke_growth_info`;
CREATE TABLE `duke_growth_info`  (
     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
     `member_id` bigint(20) NOT NULL COMMENT '公会成员表ID  duke_league_member.id',
     `growth_type` varchar(64) NULL DEFAULT NULL COMMENT '成长类型',
     `growth_value` int(11) NULL DEFAULT NULL COMMENT '成长值',
     `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     `updater` varchar(64) NULL DEFAULT NULL COMMENT '更新者',
     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已删除 0未删除 1已删除',
     PRIMARY KEY (`id`) USING BTREE
) COMMENT = '公会成员成长详情表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `duke_red_packet`;
CREATE TABLE `duke_red_packet`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '红包id',
    `app_order_no` varchar(64)  NULL DEFAULT NULL COMMENT '内部订单号',
    `user_id` bigint(20) NULL DEFAULT NULL COMMENT '发放用户id',
    `league_id` bigint(20) NULL DEFAULT NULL COMMENT '发放公会',
    `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '红包总金额',
    `red_packet_param` json NULL COMMENT '红包分配参数',
    `business_status` varchar(20)  NULL DEFAULT NULL COMMENT '红包状态：0-初始化；1-已下单；2-成功；3-失败',
    `creator` varchar(64)  NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64)  NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 644 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '红包任务表' ROW_FORMAT = DYNAMIC;
