/*
 Navicat Premium Data Transfer

 Source Server         : localhost-mysql
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : kugga-bos-spring

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 30/06/2022 15:32:20
*/

-- ----------------------------
-- create database
-- ----------------------------
CREATE database if NOT EXISTS `kugga-bos-spring` default character set utf8mb4 collate utf8mb4_0900_as_cs;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS`
(
    `SCHED_NAME`    varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TRIGGER_NAME`  varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `BLOB_DATA`     blob,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    KEY `SCHED_NAME` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS`
(
    `SCHED_NAME`    varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `CALENDAR`      blob                                                          NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS`
(
    `SCHED_NAME`      varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TRIGGER_NAME`    varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TRIGGER_GROUP`   varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TIME_ZONE_ID`    varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_CRON_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `CRON_EXPRESSION`, `TIME_ZONE_ID`)
VALUES ('schedulerName', 'payNotifyJob', 'DEFAULT', '* * * * * ?', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS`
(
    `SCHED_NAME`        varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `ENTRY_ID`          varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL,
    `TRIGGER_NAME`      varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TRIGGER_GROUP`     varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `INSTANCE_NAME`     varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `FIRED_TIME`        bigint                                                        NOT NULL,
    `SCHED_TIME`        bigint                                                        NOT NULL,
    `PRIORITY`          int                                                           NOT NULL,
    `STATE`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL,
    `JOB_NAME`          varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `JOB_GROUP`         varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `IS_NONCONCURRENT`  varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   DEFAULT NULL,
    `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
    KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
    KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
    KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
    KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
    KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS`
(
    `SCHED_NAME`        varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `JOB_NAME`          varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `JOB_GROUP`         varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `DESCRIPTION`       varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `JOB_CLASS_NAME`    varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `IS_DURABLE`        varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL,
    `IS_NONCONCURRENT`  varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL,
    `IS_UPDATE_DATA`    varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL,
    `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL,
    `JOB_DATA`          blob,
    PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
    KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
    KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `JOB_CLASS_NAME`, `IS_DURABLE`,
                                `IS_NONCONCURRENT`, `IS_UPDATE_DATA`, `REQUESTS_RECOVERY`, `JOB_DATA`)
VALUES ('schedulerName', 'payNotifyJob', 'DEFAULT', NULL,
        'com.hisun.kugga.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0',
        0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000057400104A4F425F48414E444C45525F4E414D4574000C7061794E6F746966794A6F627800);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS`
(
    `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `LOCK_NAME`  varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_LOCKS` (`SCHED_NAME`, `LOCK_NAME`)
VALUES ('schedulerName', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` (`SCHED_NAME`, `LOCK_NAME`)
VALUES ('schedulerName', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`
(
    `SCHED_NAME`    varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE`
(
    `SCHED_NAME`        varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `INSTANCE_NAME`     varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `LAST_CHECKIN_TIME` bigint                                                        NOT NULL,
    `CHECKIN_INTERVAL`  bigint                                                        NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_SCHEDULER_STATE` (`SCHED_NAME`, `INSTANCE_NAME`, `LAST_CHECKIN_TIME`, `CHECKIN_INTERVAL`)
VALUES ('schedulerName', 'Yunai.local1635571630493', 1635572537879, 15000);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`
(
    `SCHED_NAME`      varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TRIGGER_NAME`    varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TRIGGER_GROUP`   varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `REPEAT_COUNT`    bigint                                                        NOT NULL,
    `REPEAT_INTERVAL` bigint                                                        NOT NULL,
    `TIMES_TRIGGERED` bigint                                                        NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`
(
    `SCHED_NAME`    varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TRIGGER_NAME`  varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `STR_PROP_1`    varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `STR_PROP_2`    varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `STR_PROP_3`    varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `INT_PROP_1`    int                                                           DEFAULT NULL,
    `INT_PROP_2`    int                                                           DEFAULT NULL,
    `LONG_PROP_1`   bigint                                                        DEFAULT NULL,
    `LONG_PROP_2`   bigint                                                        DEFAULT NULL,
    `DEC_PROP_1`    decimal(13, 4)                                                DEFAULT NULL,
    `DEC_PROP_2`    decimal(13, 4)                                                DEFAULT NULL,
    `BOOL_PROP_1`   varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   DEFAULT NULL,
    `BOOL_PROP_2`   varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS`
(
    `SCHED_NAME`     varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TRIGGER_NAME`   varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `TRIGGER_GROUP`  varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `JOB_NAME`       varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `JOB_GROUP`      varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `DESCRIPTION`    varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `NEXT_FIRE_TIME` bigint                                                        DEFAULT NULL,
    `PREV_FIRE_TIME` bigint                                                        DEFAULT NULL,
    `PRIORITY`       int                                                           DEFAULT NULL,
    `TRIGGER_STATE`  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL,
    `TRIGGER_TYPE`   varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL,
    `START_TIME`     bigint                                                        NOT NULL,
    `END_TIME`       bigint                                                        DEFAULT NULL,
    `CALENDAR_NAME`  varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `MISFIRE_INSTR`  smallint                                                      DEFAULT NULL,
    `JOB_DATA`       blob,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    KEY `IDX_QRTZ_T_J` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
    KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
    KEY `IDX_QRTZ_T_C` (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
    KEY `IDX_QRTZ_T_G` (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
    KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
    KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
    KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
    KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
    KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
    KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
    KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
    KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`,
                                         `TRIGGER_STATE`) USING BTREE,
    CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`,
                             `NEXT_FIRE_TIME`, `PREV_FIRE_TIME`, `PRIORITY`, `TRIGGER_STATE`, `TRIGGER_TYPE`,
                             `START_TIME`, `END_TIME`, `CALENDAR_NAME`, `MISFIRE_INSTR`, `JOB_DATA`)
VALUES ('schedulerName', 'payNotifyJob', 'DEFAULT', 'payNotifyJob', 'DEFAULT', NULL, 1635572540000, 1635572539000, 5,
        'WAITING', 'CRON', 1635294882000, 0, NULL, 0,
        0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D707400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E5471007E000B7800);
COMMIT;

-- ----------------------------
-- Table structure for infra_api_access_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_api_access_log`;
CREATE TABLE `infra_api_access_log`
(
    `id`               bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `trace_id`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '' COMMENT '链路追踪编号',
    `user_id`          bigint                                                         NOT NULL DEFAULT '0' COMMENT '用户编号',
    `user_type`        tinyint                                                        NOT NULL DEFAULT '0' COMMENT '用户类型',
    `application_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '应用名',
    `request_method`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '' COMMENT '请求方法名',
    `request_url`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '请求地址',
    `request_params`   varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求参数',
    `user_ip`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '用户 IP',
    `user_agent`       varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '浏览器 UA',
    `begin_time`       datetime                                                       NOT NULL COMMENT '开始请求时间',
    `end_time`         datetime                                                       NOT NULL COMMENT '结束请求时间',
    `duration`         int                                                            NOT NULL COMMENT '执行时长',
    `result_code`      int                                                            NOT NULL DEFAULT '0' COMMENT '结果码',
    `result_msg`       varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '结果提示',
    `creator`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time`      datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time`      datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`        bigint                                                         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 35150
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='API 访问日志表';

-- ----------------------------
-- Records of infra_api_access_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_api_error_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_api_error_log`;
CREATE TABLE `infra_api_error_log`
(
    `id`                           int                                                            NOT NULL AUTO_INCREMENT COMMENT '编号',
    `trace_id`                     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '链路追踪编号\n     *\n     * 一般来说，通过链路追踪编号，可以将访问日志，错误日志，链路追踪日志，logger 打印日志等，结合在一起，从而进行排错。',
    `user_id`                      int                                                            NOT NULL DEFAULT '0' COMMENT '用户编号',
    `user_type`                    tinyint                                                        NOT NULL DEFAULT '0' COMMENT '用户类型',
    `application_name`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '应用名\n     *\n     * 目前读取 spring.application.name',
    `request_method`               varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '请求方法名',
    `request_url`                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '请求地址',
    `request_params`               varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求参数',
    `user_ip`                      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '用户 IP',
    `user_agent`                   varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '浏览器 UA',
    `exception_time`               datetime                                                       NOT NULL COMMENT '异常发生时间',
    `exception_name`               varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '异常名\n     *\n     * {@link Throwable#getClass()} 的类全名',
    `exception_message`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NOT NULL COMMENT '异常导致的消息\n     *\n     * {@link cn.iocoder.common.framework.util.ExceptionUtil#getMessage(Throwable)}',
    `exception_root_cause_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NOT NULL COMMENT '异常导致的根消息\n     *\n     * {@link cn.iocoder.common.framework.util.ExceptionUtil#getRootCauseMessage(Throwable)}',
    `exception_stack_trace`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NOT NULL COMMENT '异常的栈轨迹\n     *\n     * {@link cn.iocoder.common.framework.util.ExceptionUtil#getServiceException(Exception)}',
    `exception_class_name`         varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '异常发生的类全名\n     *\n     * {@link StackTraceElement#getClassName()}',
    `exception_file_name`          varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '异常发生的类文件\n     *\n     * {@link StackTraceElement#getFileName()}',
    `exception_method_name`        varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '异常发生的方法名\n     *\n     * {@link StackTraceElement#getMethodName()}',
    `exception_line_number`        int                                                            NOT NULL COMMENT '异常发生的方法所在行\n     *\n     * {@link StackTraceElement#getLineNumber()}',
    `process_status`               tinyint                                                        NOT NULL COMMENT '处理状态',
    `process_time`                 datetime                                                                DEFAULT NULL COMMENT '处理时间',
    `process_user_id`              int                                                                     DEFAULT '0' COMMENT '处理用户编号',
    `creator`                      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time`                  datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`                      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time`                  datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`                      bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`                    bigint                                                         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 627
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统异常日志';

-- ----------------------------
-- Records of infra_api_error_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_codegen_column
-- ----------------------------
DROP TABLE IF EXISTS `infra_codegen_column`;
CREATE TABLE `infra_codegen_column`
(
    `id`                       bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `table_id`                 bigint                                                        NOT NULL COMMENT '表编号',
    `column_name`              varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段名',
    `data_type`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段类型',
    `column_comment`           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段描述',
    `nullable`                 bit(1)                                                        NOT NULL COMMENT '是否允许为空',
    `primary_key`              bit(1)                                                        NOT NULL COMMENT '是否主键',
    `auto_increment`           char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci      NOT NULL COMMENT '是否自增',
    `ordinal_position`         int                                                           NOT NULL COMMENT '排序',
    `java_type`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT 'Java 属性类型',
    `java_field`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT 'Java 属性名',
    `dict_type`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '字典类型',
    `example`                  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '数据示例',
    `create_operation`         bit(1)                                                        NOT NULL COMMENT '是否为 Create 创建操作的字段',
    `update_operation`         bit(1)                                                        NOT NULL COMMENT '是否为 Update 更新操作的字段',
    `list_operation`           bit(1)                                                        NOT NULL COMMENT '是否为 List 查询操作的字段',
    `list_operation_condition` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '=' COMMENT 'List 查询操作的条件类型',
    `list_operation_result`    bit(1)                                                        NOT NULL COMMENT '是否为 List 查询操作的返回字段',
    `html_type`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '显示类型',
    `creator`                  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`              datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`                  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`              datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`                  bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1114
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='代码生成表字段定义';

-- ----------------------------
-- Records of infra_codegen_column
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_codegen_table
-- ----------------------------
DROP TABLE IF EXISTS `infra_codegen_table`;
CREATE TABLE `infra_codegen_table`
(
    `id`                    bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `data_source_config_id` bigint                                                        NOT NULL COMMENT '数据源配置的编号',
    `scene`                 tinyint                                                       NOT NULL DEFAULT '1' COMMENT '生成场景',
    `table_name`            varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名称',
    `table_comment`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表描述',
    `remark`                varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '备注',
    `module_name`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '模块名',
    `business_name`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '业务名',
    `class_name`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '类名称',
    `class_comment`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '类描述',
    `author`                varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '作者',
    `template_type`         tinyint                                                       NOT NULL DEFAULT '1' COMMENT '模板类型',
    `parent_menu_id`        bigint                                                                 DEFAULT NULL COMMENT '父菜单编号',
    `creator`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`           datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`           datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`               bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 98
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='代码生成表定义';

-- ----------------------------
-- Records of infra_codegen_table
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_config`;
CREATE TABLE `infra_config`
(
    `id`          int                                                           NOT NULL AUTO_INCREMENT COMMENT '参数主键',
    `category`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '参数分组',
    `type`        tinyint                                                       NOT NULL COMMENT '参数类型',
    `name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
    `config_key`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数键名',
    `value`       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数键值',
    `visible`     bit(1)                                                        NOT NULL COMMENT '是否可见',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='参数配置表';

-- ----------------------------
-- Records of infra_config
-- ----------------------------
BEGIN;
INSERT INTO `infra_config` (`id`, `category`, `type`, `name`, `config_key`, `value`, `visible`, `remark`, `creator`,
                            `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1, 'ui', 1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', b'0',
        '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow', 'admin', '2021-01-05 17:03:48', '1',
        '2022-03-26 23:10:31', b'0');
INSERT INTO `infra_config` (`id`, `category`, `type`, `name`, `config_key`, `value`, `visible`, `remark`, `creator`,
                            `create_time`, `updater`, `update_time`, `deleted`)
VALUES (2, 'biz', 1, '用户管理-账号初始密码', 'sys.user.init-password', '123456', b'0', '初始化密码 123456', 'admin',
        '2021-01-05 17:03:48', '1', '2022-03-20 02:25:51', b'0');
INSERT INTO `infra_config` (`id`, `category`, `type`, `name`, `config_key`, `value`, `visible`, `remark`, `creator`,
                            `create_time`, `updater`, `update_time`, `deleted`)
VALUES (3, 'ui', 1, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', b'0', '深色主题theme-dark，浅色主题theme-light', 'admin',
        '2021-01-05 17:03:48', '', '2021-01-19 03:05:21', b'0');
INSERT INTO `infra_config` (`id`, `category`, `type`, `name`, `config_key`, `value`, `visible`, `remark`, `creator`,
                            `create_time`, `updater`, `update_time`, `deleted`)
VALUES (4, '1', 2, 'xxx', 'demo.test', '10', b'0', '5', '', '2021-01-19 03:10:26', '', '2021-01-20 09:25:55', b'0');
INSERT INTO `infra_config` (`id`, `category`, `type`, `name`, `config_key`, `value`, `visible`, `remark`, `creator`,
                            `create_time`, `updater`, `update_time`, `deleted`)
VALUES (6, 'biz', 2, '登陆验证码的开关', 'kugga.captcha.enable', 'true', b'1', NULL, '1', '2022-02-17 00:03:11', '1',
        '2022-04-04 12:51:40', b'0');
COMMIT;

-- ----------------------------
-- Table structure for infra_data_source_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_data_source_config`;
CREATE TABLE `infra_data_source_config`
(
    `id`          bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '主键编号',
    `name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '参数名称',
    `url`         varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据源连接',
    `username`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户名',
    `password`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '密码',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='数据源配置表';

-- ----------------------------
-- Records of infra_data_source_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_file
-- ----------------------------
DROP TABLE IF EXISTS `infra_file`;
CREATE TABLE `infra_file`
(
    `id`          bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '文件编号',
    `config_id`   bigint                                                                  DEFAULT NULL COMMENT '配置编号',
    `name`        varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '文件名',
    `path`        varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '文件路径',
    `url`         varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件 URL',
    `type`        varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT NULL COMMENT '文件类型',
    `size`        int                                                            NOT NULL COMMENT '文件大小',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 85
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='文件表';

-- ----------------------------
-- Records of infra_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_file_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_file_config`;
CREATE TABLE `infra_file_config`
(
    `id`          bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`        varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '配置名',
    `storage`     tinyint                                                        NOT NULL COMMENT '存储器',
    `remark`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '备注',
    `master`      bit(1)                                                         NOT NULL COMMENT '是否为主配置',
    `config`      varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储配置',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 18
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='文件配置表';

-- ----------------------------
-- Records of infra_file_config
-- ----------------------------
BEGIN;
INSERT INTO `infra_file_config` (`id`, `name`, `storage`, `remark`, `master`, `config`, `creator`, `create_time`,
                                 `updater`, `update_time`, `deleted`)
VALUES (4, '数据库', 1, '我是数据库', b'0',
        '{\"@class\":\"com.hisun.kugga.framework.file.core.client.db.DBFileClientConfig\",\"domain\":\"http://127.0.0.1:48080\"}',
        '1', '2022-03-15 23:56:24', '1', '2022-03-26 21:39:26', b'0');
INSERT INTO `infra_file_config` (`id`, `name`, `storage`, `remark`, `master`, `config`, `creator`, `create_time`,
                                 `updater`, `update_time`, `deleted`)
VALUES (5, '本地磁盘', 10, '测试下本地存储', b'0',
        '{\"@class\":\"com.hisun.kugga.framework.file.core.client.local.LocalFileClientConfig\",\"basePath\":\"/Users/yunai/file_test\",\"domain\":\"http://127.0.0.1:48080\"}',
        '1', '2022-03-15 23:57:00', '1', '2022-03-26 21:39:26', b'0');
INSERT INTO `infra_file_config` (`id`, `name`, `storage`, `remark`, `master`, `config`, `creator`, `create_time`,
                                 `updater`, `update_time`, `deleted`)
VALUES (11, 'S3 - 七牛云', 20, NULL, b'1',
        '{\"@class\":\"com.hisun.kugga.framework.file.core.client.s3.S3FileClientConfig\",\"endpoint\":\"s3-cn-south-1.qiniucs.com\",\"domain\":\"http://test.kugga.iocoder.cn\",\"bucket\":\"ruoyi-vue-pro\",\"accessKey\":\"b7yvuhBSAGjmtPhMFcn9iMOxUOY_I06cA_p0ZUx8\",\"accessSecret\":\"kXM1l5ia1RvSX3QaOEcwI3RLz3Y2rmNszWonKZtP\"}',
        '1', '2022-03-19 18:00:03', '1', '2022-03-26 21:39:26', b'0');
COMMIT;

-- ----------------------------
-- Table structure for infra_file_content
-- ----------------------------
DROP TABLE IF EXISTS `infra_file_content`;
CREATE TABLE `infra_file_content`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `config_id`   bigint                                                        NOT NULL COMMENT '配置编号',
    `path`        varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
    `content`     mediumblob                                                    NOT NULL COMMENT '文件内容',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='文件表';

-- ----------------------------
-- Records of infra_file_content
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_job
-- ----------------------------
DROP TABLE IF EXISTS `infra_job`;
CREATE TABLE `infra_job`
(
    `id`              bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '任务编号',
    `name`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务名称',
    `status`          tinyint                                                      NOT NULL COMMENT '任务状态',
    `handler_name`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '处理器的名字',
    `handler_param`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '处理器的参数',
    `cron_expression` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'CRON 表达式',
    `retry_count`     int                                                          NOT NULL DEFAULT '0' COMMENT '重试次数',
    `retry_interval`  int                                                          NOT NULL DEFAULT '0' COMMENT '重试间隔',
    `monitor_timeout` int                                                          NOT NULL DEFAULT '0' COMMENT '监控超时时间',
    `creator`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '创建者',
    `create_time`     datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '更新者',
    `update_time`     datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='定时任务表';

-- ----------------------------
-- Records of infra_job
-- ----------------------------
BEGIN;
INSERT INTO `infra_job` (`id`, `name`, `status`, `handler_name`, `handler_param`, `cron_expression`, `retry_count`,
                         `retry_interval`, `monitor_timeout`, `creator`, `create_time`, `updater`, `update_time`,
                         `deleted`)
VALUES (5, '支付通知 Job', 2, 'payNotifyJob', NULL, '* * * * * ?', 0, 0, 0, '1', '2021-10-27 08:34:42', '1',
        '2022-04-03 20:35:25', b'0');
COMMIT;

-- ----------------------------
-- Table structure for infra_job_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_job_log`;
CREATE TABLE `infra_job_log`
(
    `id`            bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '日志编号',
    `job_id`        bigint                                                       NOT NULL COMMENT '任务编号',
    `handler_name`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '处理器的名字',
    `handler_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '处理器的参数',
    `execute_index` tinyint                                                      NOT NULL DEFAULT '1' COMMENT '第几次执行',
    `begin_time`    datetime                                                     NOT NULL COMMENT '开始执行时间',
    `end_time`      datetime                                                              DEFAULT NULL COMMENT '结束执行时间',
    `duration`      int                                                                   DEFAULT NULL COMMENT '执行时长',
    `status`        tinyint                                                      NOT NULL COMMENT '任务状态',
    `result`        varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci        DEFAULT '' COMMENT '结果数据',
    `creator`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '创建者',
    `create_time`   datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '更新者',
    `update_time`   datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 25295
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='定时任务日志表';

-- ----------------------------
-- Records of infra_job_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_test_demo
-- ----------------------------
DROP TABLE IF EXISTS `infra_test_demo`;
CREATE TABLE `infra_test_demo`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '名字',
    `status`      tinyint                                                       NOT NULL DEFAULT '0' COMMENT '状态',
    `type`        tinyint                                                       NOT NULL COMMENT '类型',
    `category`    tinyint                                                       NOT NULL COMMENT '分类',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 108
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='字典类型表';

-- ----------------------------
-- Records of infra_test_demo
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for member_user
-- ----------------------------
DROP TABLE IF EXISTS `member_user`;
CREATE TABLE `member_user`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `nickname`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '用户昵称',
    `avatar`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像',
    `status`      tinyint                                                       NOT NULL COMMENT '状态',
    `mobile`      varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '手机号',
    `password`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
    `register_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '注册 IP',
    `login_ip`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '最后登录IP',
    `login_date`  datetime                                                               DEFAULT NULL COMMENT '最后登录时间',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint                                                        NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_mobile` (`mobile`) USING BTREE COMMENT '手机号'
) ENGINE = InnoDB
  AUTO_INCREMENT = 248
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户';

-- ----------------------------
-- Records of member_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_app
-- ----------------------------
DROP TABLE IF EXISTS `pay_app`;
CREATE TABLE `pay_app`
(
    `id`                bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '应用编号',
    `name`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '应用名',
    `status`            tinyint                                                        NOT NULL COMMENT '开启状态',
    `remark`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '备注',
    `pay_notify_url`    varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付结果的回调地址',
    `refund_notify_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '退款结果的回调地址',
    `merchant_id`       bigint                                                         NOT NULL COMMENT '商户编号',
    `creator`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time`       datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time`       datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`         bigint                                                         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='支付应用信息';

-- ----------------------------
-- Records of pay_app
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_channel
-- ----------------------------
DROP TABLE IF EXISTS `pay_channel`;
CREATE TABLE `pay_channel`
(
    `id`          bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '商户编号',
    `code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '渠道编码',
    `status`      tinyint                                                        NOT NULL COMMENT '开启状态',
    `remark`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '备注',
    `fee_rate`    double                                                         NOT NULL DEFAULT '0' COMMENT '渠道费率，单位：百分比',
    `merchant_id` bigint                                                         NOT NULL COMMENT '商户编号',
    `app_id`      bigint                                                         NOT NULL COMMENT '应用编号',
    `config`      varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付渠道配置',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint                                                         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 18
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='支付渠道\n';

-- ----------------------------
-- Records of pay_channel
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_merchant
-- ----------------------------
DROP TABLE IF EXISTS `pay_merchant`;
CREATE TABLE `pay_merchant`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '商户编号',
    `no`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商户号',
    `name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商户全称',
    `short_name`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商户简称',
    `status`      tinyint                                                      NOT NULL COMMENT '开启状态',
    `remark`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint                                                       NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='支付商户信息';

-- ----------------------------
-- Records of pay_merchant
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_notify_log
-- ----------------------------
DROP TABLE IF EXISTS `pay_notify_log`;
CREATE TABLE `pay_notify_log`
(
    `id`           bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '日志编号',
    `task_id`      bigint                                                         NOT NULL COMMENT '通知任务编号',
    `notify_times` tinyint                                                        NOT NULL COMMENT '第几次被通知',
    `response`     varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求参数',
    `status`       tinyint                                                        NOT NULL COMMENT '通知状态',
    `creator`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time`  datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time`  datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`    bigint                                                         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 363051
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='支付通知 App 的日志';

-- ----------------------------
-- Records of pay_notify_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_notify_task
-- ----------------------------
DROP TABLE IF EXISTS `pay_notify_task`;
CREATE TABLE `pay_notify_task`
(
    `id`                bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '任务编号',
    `merchant_id`       bigint                                                         NOT NULL COMMENT '商户编号',
    `app_id`            bigint                                                         NOT NULL COMMENT '应用编号',
    `type`              tinyint                                                        NOT NULL COMMENT '通知类型',
    `data_id`           bigint                                                         NOT NULL COMMENT '数据编号',
    `status`            tinyint                                                        NOT NULL COMMENT '通知状态',
    `merchant_order_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '商户订单编号',
    `next_notify_time`  datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下一次通知时间',
    `last_execute_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次执行时间',
    `notify_times`      tinyint                                                        NOT NULL COMMENT '当前通知次数',
    `max_notify_times`  tinyint                                                        NOT NULL COMMENT '最大可通知次数',
    `notify_url`        varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异步通知地址',
    `creator`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time`       datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time`       datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`         bigint                                                         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 112
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='商户支付、退款等的通知\n';

-- ----------------------------
-- Records of pay_notify_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_order
-- ----------------------------
DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order`
(
    `id`                   bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '支付订单编号',
    `merchant_id`          bigint                                                         NOT NULL COMMENT '商户编号',
    `app_id`               bigint                                                         NOT NULL COMMENT '应用编号',
    `channel_id`           bigint                                                                  DEFAULT NULL COMMENT '渠道编号',
    `channel_code`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT NULL COMMENT '渠道编码',
    `merchant_order_id`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '商户订单编号',
    `subject`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '商品标题',
    `body`                 varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '商品描述',
    `notify_url`           varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异步通知地址',
    `notify_status`        tinyint                                                        NOT NULL COMMENT '通知商户支付结果的回调状态',
    `amount`               bigint                                                         NOT NULL COMMENT '支付金额，单位：分',
    `channel_fee_rate`     double                                                                  DEFAULT '0' COMMENT '渠道手续费，单位：百分比',
    `channel_fee_amount`   bigint                                                                  DEFAULT '0' COMMENT '渠道手续金额，单位：分',
    `status`               tinyint                                                        NOT NULL COMMENT '支付状态',
    `user_ip`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '用户 IP',
    `expire_time`          datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单失效时间',
    `success_time`         datetime                                                                DEFAULT CURRENT_TIMESTAMP COMMENT '订单支付成功时间',
    `notify_time`          datetime                                                                DEFAULT CURRENT_TIMESTAMP COMMENT '订单支付通知时间',
    `success_extension_id` bigint                                                                  DEFAULT NULL COMMENT '支付成功的订单拓展单编号',
    `refund_status`        tinyint                                                        NOT NULL COMMENT '退款状态',
    `refund_times`         tinyint                                                        NOT NULL COMMENT '退款次数',
    `refund_amount`        bigint                                                         NOT NULL COMMENT '退款总金额，单位：分',
    `channel_user_id`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '渠道用户编号',
    `channel_order_no`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT NULL COMMENT '渠道订单号',
    `creator`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time`          datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time`          datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`              bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`            bigint                                                         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 125
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='支付订单\n';

-- ----------------------------
-- Records of pay_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_order_extension
-- ----------------------------
DROP TABLE IF EXISTS `pay_order_extension`;
CREATE TABLE `pay_order_extension`
(
    `id`                  bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '支付订单编号',
    `no`                  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付订单号',
    `order_id`            bigint                                                       NOT NULL COMMENT '支付订单编号',
    `channel_id`          bigint                                                       NOT NULL COMMENT '渠道编号',
    `channel_code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道编码',
    `user_ip`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
    `status`              tinyint                                                      NOT NULL COMMENT '支付状态',
    `channel_extras`      varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '支付渠道的额外参数',
    `channel_notify_data` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci        DEFAULT NULL COMMENT '支付渠道异步通知的内容',
    `creator`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '创建者',
    `create_time`         datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '更新者',
    `update_time`         datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint                                                       NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 124
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='支付订单\n';

-- ----------------------------
-- Records of pay_order_extension
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_refund
-- ----------------------------
DROP TABLE IF EXISTS `pay_refund`;
CREATE TABLE `pay_refund`
(
    `id`                 bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '支付退款编号',
    `merchant_id`        bigint                                                         NOT NULL COMMENT '商户编号',
    `app_id`             bigint                                                         NOT NULL COMMENT '应用编号',
    `channel_id`         bigint                                                         NOT NULL COMMENT '渠道编号',
    `channel_code`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '渠道编码',
    `order_id`           bigint                                                         NOT NULL COMMENT '支付订单编号 pay_order 表id',
    `trade_no`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '交易订单号 pay_extension 表no 字段',
    `merchant_order_id`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '商户订单编号（商户系统生成）',
    `merchant_refund_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '商户退款订单号（商户系统生成）',
    `notify_url`         varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异步通知商户地址',
    `notify_status`      tinyint                                                        NOT NULL COMMENT '通知商户退款结果的回调状态',
    `status`             tinyint                                                        NOT NULL COMMENT '退款状态',
    `type`               tinyint                                                        NOT NULL COMMENT '退款类型(部分退款，全部退款)',
    `pay_amount`         bigint                                                         NOT NULL COMMENT '支付金额,单位分',
    `refund_amount`      bigint                                                         NOT NULL COMMENT '退款金额,单位分',
    `reason`             varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '退款原因',
    `user_ip`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT NULL COMMENT '用户 IP',
    `channel_order_no`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '渠道订单号，pay_order 中的channel_order_no 对应',
    `channel_refund_no`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT NULL COMMENT '渠道退款单号，渠道返回',
    `channel_error_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '渠道调用报错时，错误码',
    `channel_error_msg`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '渠道调用报错时，错误信息',
    `channel_extras`     varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '支付渠道的额外参数',
    `expire_time`        datetime                                                                DEFAULT NULL COMMENT '退款失效时间',
    `success_time`       datetime                                                                DEFAULT NULL COMMENT '退款成功时间',
    `notify_time`        datetime                                                                DEFAULT NULL COMMENT '退款通知时间',
    `creator`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time`        datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time`        datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`            bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`          bigint                                                         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='退款订单';

-- ----------------------------
-- Records of pay_refund
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_dept
-- ----------------------------
DROP TABLE IF EXISTS `system_dept`;
CREATE TABLE `system_dept`
(
    `id`             bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '部门id',
    `name`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '部门名称',
    `parent_id`      bigint                                                       NOT NULL DEFAULT '0' COMMENT '父部门id',
    `sort`           int                                                          NOT NULL DEFAULT '0' COMMENT '显示顺序',
    `leader_user_id` bigint                                                                DEFAULT NULL COMMENT '负责人',
    `phone`          varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '联系电话',
    `email`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '邮箱',
    `status`         tinyint                                                      NOT NULL COMMENT '部门状态（0正常 1停用）',
    `creator`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '创建者',
    `create_time`    datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '更新者',
    `update_time`    datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`      bigint                                                       NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 112
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='部门表';

-- ----------------------------
-- Records of system_dept
-- ----------------------------
BEGIN;
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`,
                           `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (100, '芋道源码', 0, 0, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103',
        '2022-01-14 01:04:05', b'0', 1);
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`,
                           `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (101, '深圳总公司', 100, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1',
        '2022-05-16 20:25:23', b'0', 1);
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`,
                           `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (102, '长沙分公司', 100, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '',
        '2021-12-15 05:01:40', b'0', 1);
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`,
                           `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (103, '研发部门', 101, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103',
        '2022-01-14 01:04:14', b'0', 1);
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`,
                           `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (104, '市场部门', 101, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '',
        '2021-12-15 05:01:38', b'0', 1);
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`,
                           `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (105, '测试部门', 101, 3, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1',
        '2022-05-16 20:25:15', b'0', 1);
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`,
                           `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (106, '财务部门', 101, 4, 103, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103',
        '2022-01-15 21:32:22', b'0', 1);
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`,
                           `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (107, '运维部门', 101, 5, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '',
        '2021-12-15 05:01:33', b'0', 1);
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`,
                           `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (108, '市场部门', 102, 1, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1',
        '2022-02-16 08:35:45', b'0', 1);
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`,
                           `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (109, '财务部门', 102, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '',
        '2021-12-15 05:01:29', b'0', 1);
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`,
                           `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (110, '新部门', 0, 1, NULL, NULL, NULL, 0, '110', '2022-02-23 20:46:30', '110', '2022-02-23 20:46:30', b'0', 121);
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`,
                           `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (111, '顶级部门', 0, 1, NULL, NULL, NULL, 0, '113', '2022-03-07 21:44:50', '113', '2022-03-07 21:44:50', b'0', 122);
COMMIT;

-- ----------------------------
-- Table structure for system_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_data`;
CREATE TABLE `system_dict_data`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    `sort`        int                                                           NOT NULL DEFAULT '0' COMMENT '字典排序',
    `label`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典标签',
    `value`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典键值',
    `dict_type`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
    `status`      tinyint                                                       NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `color_type`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '颜色类型',
    `css_class`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT 'css 样式',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1162
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='字典数据表';

-- ----------------------------
-- Records of system_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1, 1, '男', '1', 'system_user_sex', 0, 'default', 'A', '性别男', 'admin', '2021-01-05 17:03:48', '1',
        '2022-03-29 00:14:39', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (2, 2, '女', '2', 'system_user_sex', 1, 'success', '', '性别女', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 01:30:51', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (8, 1, '正常', '1', 'infra_job_status', 0, 'success', '', '正常状态', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 19:33:38', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (9, 2, '暂停', '2', 'infra_job_status', 0, 'danger', '', '停用状态', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 19:33:45', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (12, 1, '系统内置', '1', 'infra_config_type', 0, 'danger', '', '参数类型 - 系统内置', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 19:06:02', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (13, 2, '自定义', '2', 'infra_config_type', 0, 'primary', '', '参数类型 - 自定义', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 19:06:07', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (14, 1, '通知', '1', 'system_notice_type', 0, 'success', '', '通知', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 13:05:57', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (15, 2, '公告', '2', 'system_notice_type', 0, 'info', '', '公告', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 13:06:01', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (16, 0, '其它', '0', 'system_operate_type', 0, 'default', '', '其它操作', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:32:46', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (17, 1, '查询', '1', 'system_operate_type', 0, 'info', '', '查询操作', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:33:16', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (18, 2, '新增', '2', 'system_operate_type', 0, 'primary', '', '新增操作', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:33:13', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (19, 3, '修改', '3', 'system_operate_type', 0, 'warning', '', '修改操作', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:33:22', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (20, 4, '删除', '4', 'system_operate_type', 0, 'danger', '', '删除操作', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:33:27', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (22, 5, '导出', '5', 'system_operate_type', 0, 'default', '', '导出操作', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:33:32', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (23, 6, '导入', '6', 'system_operate_type', 0, 'default', '', '导入操作', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:33:35', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (27, 1, '开启', '0', 'common_status', 0, 'primary', '', '开启状态', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 08:00:39', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (28, 2, '关闭', '1', 'common_status', 0, 'info', '', '关闭状态', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 08:00:44', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (29, 1, '目录', '1', 'system_menu_type', 0, '', '', '目录', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-01 16:43:45', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (30, 2, '菜单', '2', 'system_menu_type', 0, '', '', '菜单', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-01 16:43:41', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (31, 3, '按钮', '3', 'system_menu_type', 0, '', '', '按钮', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-01 16:43:39', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (32, 1, '内置', '1', 'system_role_type', 0, 'danger', '', '内置角色', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 13:02:08', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (33, 2, '自定义', '2', 'system_role_type', 0, 'primary', '', '自定义角色', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 13:02:12', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (34, 1, '全部数据权限', '1', 'system_data_scope', 0, '', '', '全部数据权限', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-01 16:47:17', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (35, 2, '指定部门数据权限', '2', 'system_data_scope', 0, '', '', '指定部门数据权限', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-01 16:47:18', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (36, 3, '本部门数据权限', '3', 'system_data_scope', 0, '', '', '本部门数据权限', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-01 16:47:16', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (37, 4, '本部门及以下数据权限', '4', 'system_data_scope', 0, '', '', '本部门及以下数据权限', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-01 16:47:21', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (38, 5, '仅本人数据权限', '5', 'system_data_scope', 0, '', '', '仅本人数据权限', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-01 16:47:23', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (39, 0, '成功', '0', 'system_login_result', 0, 'success', '', '登陆结果 - 成功', '', '2021-01-18 06:17:36', '1',
        '2022-02-16 13:23:49', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (40, 10, '账号或密码不正确', '10', 'system_login_result', 0, 'primary', '', '登陆结果 - 账号或密码不正确', '', '2021-01-18 06:17:54',
        '1', '2022-02-16 13:24:27', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (41, 20, '用户被禁用', '20', 'system_login_result', 0, 'warning', '', '登陆结果 - 用户被禁用', '', '2021-01-18 06:17:54', '1',
        '2022-02-16 13:23:57', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (42, 30, '验证码不存在', '30', 'system_login_result', 0, 'info', '', '登陆结果 - 验证码不存在', '', '2021-01-18 06:17:54', '1',
        '2022-02-16 13:24:07', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (43, 31, '验证码不正确', '31', 'system_login_result', 0, 'info', '', '登陆结果 - 验证码不正确', '', '2021-01-18 06:17:54', '1',
        '2022-02-16 13:24:11', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (44, 100, '未知异常', '100', 'system_login_result', 0, 'danger', '', '登陆结果 - 未知异常', '', '2021-01-18 06:17:54', '1',
        '2022-02-16 13:24:23', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (45, 1, '是', 'true', 'infra_boolean_string', 0, 'danger', '', 'Boolean 是否类型 - 是', '', '2021-01-19 03:20:55', '1',
        '2022-03-15 23:01:45', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (46, 1, '否', 'false', 'infra_boolean_string', 0, 'info', '', 'Boolean 是否类型 - 否', '', '2021-01-19 03:20:55', '1',
        '2022-03-15 23:09:45', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (47, 1, '永不超时', '1', 'infra_redis_timeout_type', 0, 'primary', '', 'Redis 未设置超时的情况', '', '2021-01-26 00:53:17',
        '1', '2022-02-16 19:03:35', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (48, 1, '动态超时', '2', 'infra_redis_timeout_type', 0, 'info', '', '程序里动态传入超时时间，无法固定', '', '2021-01-26 00:55:00',
        '1', '2022-02-16 19:03:41', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (49, 3, '固定超时', '3', 'infra_redis_timeout_type', 0, 'success', '', 'Redis 设置了过期时间', '', '2021-01-26 00:55:26',
        '1', '2022-02-16 19:03:45', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (50, 1, '单表（增删改查）', '1', 'infra_codegen_template_type', 0, '', '', NULL, '', '2021-02-05 07:09:06', '',
        '2022-03-10 16:33:15', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (51, 2, '树表（增删改查）', '2', 'infra_codegen_template_type', 0, '', '', NULL, '', '2021-02-05 07:14:46', '',
        '2022-03-10 16:33:19', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (53, 0, '初始化中', '0', 'infra_job_status', 0, 'primary', '', NULL, '', '2021-02-07 07:46:49', '1',
        '2022-02-16 19:33:29', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (57, 0, '运行中', '0', 'infra_job_log_status', 0, 'primary', '', 'RUNNING', '', '2021-02-08 10:04:24', '1',
        '2022-02-16 19:07:48', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (58, 1, '成功', '1', 'infra_job_log_status', 0, 'success', '', NULL, '', '2021-02-08 10:06:57', '1',
        '2022-02-16 19:07:52', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (59, 2, '失败', '2', 'infra_job_log_status', 0, 'warning', '', '失败', '', '2021-02-08 10:07:38', '1',
        '2022-02-16 19:07:56', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (60, 1, '会员', '1', 'user_type', 0, 'primary', '', NULL, '', '2021-02-26 00:16:27', '1', '2022-02-16 10:22:19',
        b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (61, 2, '管理员', '2', 'user_type', 0, 'success', '', NULL, '', '2021-02-26 00:16:34', '1', '2022-02-16 10:22:22',
        b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (62, 0, '未处理', '0', 'infra_api_error_log_process_status', 0, 'primary', '', NULL, '', '2021-02-26 07:07:19', '1',
        '2022-02-16 20:14:17', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (63, 1, '已处理', '1', 'infra_api_error_log_process_status', 0, 'success', '', NULL, '', '2021-02-26 07:07:26', '1',
        '2022-02-16 20:14:08', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (64, 2, '已忽略', '2', 'infra_api_error_log_process_status', 0, 'danger', '', NULL, '', '2021-02-26 07:07:34', '1',
        '2022-02-16 20:14:14', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (65, 1, '云片', 'YUN_PIAN', 'system_sms_channel_code', 0, 'success', '', NULL, '1', '2021-04-05 01:05:14', '1',
        '2022-02-16 10:09:55', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (66, 2, '阿里云', 'ALIYUN', 'system_sms_channel_code', 0, 'primary', '', NULL, '1', '2021-04-05 01:05:26', '1',
        '2022-02-16 10:09:52', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (67, 1, '验证码', '1', 'system_sms_template_type', 0, 'warning', '', NULL, '1', '2021-04-05 21:50:57', '1',
        '2022-02-16 12:48:30', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (68, 2, '通知', '2', 'system_sms_template_type', 0, 'primary', '', NULL, '1', '2021-04-05 21:51:08', '1',
        '2022-02-16 12:48:27', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (69, 0, '营销', '3', 'system_sms_template_type', 0, 'danger', '', NULL, '1', '2021-04-05 21:51:15', '1',
        '2022-02-16 12:48:22', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (70, 0, '初始化', '0', 'system_sms_send_status', 0, 'primary', '', NULL, '1', '2021-04-11 20:18:33', '1',
        '2022-02-16 10:26:07', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (71, 1, '发送成功', '10', 'system_sms_send_status', 0, 'success', '', NULL, '1', '2021-04-11 20:18:43', '1',
        '2022-02-16 10:25:56', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (72, 2, '发送失败', '20', 'system_sms_send_status', 0, 'danger', '', NULL, '1', '2021-04-11 20:18:49', '1',
        '2022-02-16 10:26:03', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (73, 3, '不发送', '30', 'system_sms_send_status', 0, 'info', '', NULL, '1', '2021-04-11 20:19:44', '1',
        '2022-02-16 10:26:10', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (74, 0, '等待结果', '0', 'system_sms_receive_status', 0, 'primary', '', NULL, '1', '2021-04-11 20:27:43', '1',
        '2022-02-16 10:28:24', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (75, 1, '接收成功', '10', 'system_sms_receive_status', 0, 'success', '', NULL, '1', '2021-04-11 20:29:25', '1',
        '2022-02-16 10:28:28', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (76, 2, '接收失败', '20', 'system_sms_receive_status', 0, 'danger', '', NULL, '1', '2021-04-11 20:29:31', '1',
        '2022-02-16 10:28:32', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (77, 0, '调试(钉钉)', 'DEBUG_DING_TALK', 'system_sms_channel_code', 0, 'info', '', NULL, '1', '2021-04-13 00:20:37',
        '1', '2022-02-16 10:10:00', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (78, 1, '自动生成', '1', 'system_error_code_type', 0, 'warning', '', NULL, '1', '2021-04-21 00:06:48', '1',
        '2022-02-16 13:57:20', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (79, 2, '手动编辑', '2', 'system_error_code_type', 0, 'primary', '', NULL, '1', '2021-04-21 00:07:14', '1',
        '2022-02-16 13:57:24', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (80, 100, '账号登录', '100', 'system_login_type', 0, 'primary', '', '账号登录', '1', '2021-10-06 00:52:02', '1',
        '2022-02-16 13:11:34', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (81, 101, '社交登录', '101', 'system_login_type', 0, 'info', '', '社交登录', '1', '2021-10-06 00:52:17', '1',
        '2022-02-16 13:11:40', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (83, 200, '主动登出', '200', 'system_login_type', 0, 'primary', '', '主动登出', '1', '2021-10-06 00:52:58', '1',
        '2022-02-16 13:11:49', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (85, 202, '强制登出', '202', 'system_login_type', 0, 'danger', '', '强制退出', '1', '2021-10-06 00:53:41', '1',
        '2022-02-16 13:11:57', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1145, 1, '管理后台', '1', 'infra_codegen_scene', 0, '', '', '代码生成的场景枚举 - 管理后台', '1', '2022-02-02 13:15:06', '1',
        '2022-03-10 16:32:59', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1146, 2, '用户 APP', '2', 'infra_codegen_scene', 0, '', '', '代码生成的场景枚举 - 用户 APP', '1', '2022-02-02 13:15:19', '1',
        '2022-03-10 16:33:03', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1150, 1, '数据库', '1', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:25:28', '1',
        '2022-03-15 00:25:28', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1151, 10, '本地磁盘', '10', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:25:41', '1',
        '2022-03-15 00:25:56', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1152, 11, 'FTP 服务器', '11', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:06', '1',
        '2022-03-15 00:26:10', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1153, 12, 'SFTP 服务器', '12', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:22', '1',
        '2022-03-15 00:26:22', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1154, 20, 'S3 对象存储', '20', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:31', '1',
        '2022-03-15 00:26:45', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1155, 103, '短信登录', '103', 'system_login_type', 0, 'default', '', NULL, '1', '2022-05-09 23:57:58', '1',
        '2022-05-09 23:58:09', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1156, 1, 'password', 'password', 'system_oauth2_grant_type', 0, 'default', '', '密码模式', '1',
        '2022-05-12 00:22:05', '1', '2022-05-11 16:26:01', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1157, 2, 'authorization_code', 'authorization_code', 'system_oauth2_grant_type', 0, 'primary', '', '授权码模式', '1',
        '2022-05-12 00:22:59', '1', '2022-05-11 16:26:02', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1158, 3, 'implicit', 'implicit', 'system_oauth2_grant_type', 0, 'success', '', '简化模式', '1',
        '2022-05-12 00:23:40', '1', '2022-05-11 16:26:05', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1159, 4, 'client_credentials', 'client_credentials', 'system_oauth2_grant_type', 0, 'default', '', '客户端模式', '1',
        '2022-05-12 00:23:51', '1', '2022-05-11 16:26:08', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1160, 5, 'refresh_token', 'refresh_token', 'system_oauth2_grant_type', 0, 'info', '', '刷新模式', '1',
        '2022-05-12 00:24:02', '1', '2022-05-11 16:26:11', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_type`;
CREATE TABLE `system_dict_type`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典名称',
    `type`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
    `status`      tinyint                                                       NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `dict_type` (`type`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 149
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='字典类型表';

-- ----------------------------
-- Records of system_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (1, '用户性别', 'system_user_sex', 0, NULL, 'admin', '2021-01-05 17:03:48', '1', '2022-05-16 20:29:32', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (6, '参数类型', 'infra_config_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:36:54', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (7, '通知类型', 'system_notice_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:35:26', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (9, '操作类型', 'system_operate_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:32:21', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (10, '系统状态', 'common_status', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:21:28', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (11, 'Boolean 是否类型', 'infra_boolean_string', 0, 'boolean 转是否', '', '2021-01-19 03:20:08', '',
        '2022-02-01 16:37:10', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (104, '登陆结果', 'system_login_result', 0, '登陆结果', '', '2021-01-18 06:17:11', '', '2022-02-01 16:36:00', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (105, 'Redis 超时类型', 'infra_redis_timeout_type', 0, 'RedisKeyDefine.TimeoutTypeEnum', '', '2021-01-26 00:52:50',
        '', '2022-02-01 16:50:29', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (106, '代码生成模板类型', 'infra_codegen_template_type', 0, NULL, '', '2021-02-05 07:08:06', '1', '2022-05-16 20:26:50',
        b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (107, '定时任务状态', 'infra_job_status', 0, NULL, '', '2021-02-07 07:44:16', '', '2022-02-01 16:51:11', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (108, '定时任务日志状态', 'infra_job_log_status', 0, NULL, '', '2021-02-08 10:03:51', '', '2022-02-01 16:50:43', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (109, '用户类型', 'user_type', 0, NULL, '', '2021-02-26 00:15:51', '', '2021-02-26 00:15:51', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (110, 'API 异常数据的处理状态', 'infra_api_error_log_process_status', 0, NULL, '', '2021-02-26 07:07:01', '',
        '2022-02-01 16:50:53', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (111, '短信渠道编码', 'system_sms_channel_code', 0, NULL, '1', '2021-04-05 01:04:50', '1', '2022-02-16 02:09:08',
        b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (112, '短信模板的类型', 'system_sms_template_type', 0, NULL, '1', '2021-04-05 21:50:43', '1', '2022-02-01 16:35:06',
        b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (113, '短信发送状态', 'system_sms_send_status', 0, NULL, '1', '2021-04-11 20:18:03', '1', '2022-02-01 16:35:09', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (114, '短信接收状态', 'system_sms_receive_status', 0, NULL, '1', '2021-04-11 20:27:14', '1', '2022-02-01 16:35:14',
        b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (115, '错误码的类型', 'system_error_code_type', 0, NULL, '1', '2021-04-21 00:06:30', '1', '2022-02-01 16:36:49', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (116, '登陆日志的类型', 'system_login_type', 0, '登陆日志的类型', '1', '2021-10-06 00:50:46', '1', '2022-02-01 16:35:56',
        b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (144, '代码生成的场景枚举', 'infra_codegen_scene', 0, '代码生成的场景枚举', '1', '2022-02-02 13:14:45', '1', '2022-03-10 16:33:46',
        b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (145, '角色类型', 'system_role_type', 0, '角色类型', '1', '2022-02-16 13:01:46', '1', '2022-02-16 13:01:46', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (146, '文件存储器', 'infra_file_storage', 0, '文件存储器', '1', '2022-03-15 00:24:38', '1', '2022-03-15 00:24:38', b'0');
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`,
                                `update_time`, `deleted`)
VALUES (147, 'OAuth 2.0 授权类型', 'system_oauth2_grant_type', 0, 'OAuth 2.0 授权类型（模式）', '1', '2022-05-12 00:20:52', '1',
        '2022-05-11 16:25:49', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_error_code
-- ----------------------------
DROP TABLE IF EXISTS `system_error_code`;
CREATE TABLE `system_error_code`
(
    `id`               bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '错误码编号',
    `type`             tinyint                                                       NOT NULL DEFAULT '0' COMMENT '错误码类型',
    `application_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '应用名',
    `code`             int                                                           NOT NULL DEFAULT '0' COMMENT '错误码编码',
    `message`          varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '错误码错误提示',
    `memo`             varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '备注',
    `creator`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`      datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`      datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5832
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='错误码表';

-- ----------------------------
-- Records of system_error_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_login_log
-- ----------------------------
DROP TABLE IF EXISTS `system_login_log`;
CREATE TABLE `system_login_log`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '访问ID',
    `log_type`    bigint                                                        NOT NULL COMMENT '日志类型',
    `trace_id`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '链路追踪编号',
    `user_id`     bigint                                                        NOT NULL DEFAULT '0' COMMENT '用户编号',
    `user_type`   tinyint                                                       NOT NULL DEFAULT '0' COMMENT '用户类型',
    `username`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '用户账号',
    `result`      tinyint                                                       NOT NULL COMMENT '登陆结果',
    `user_ip`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户 IP',
    `user_agent`  varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint                                                        NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统访问记录';

-- ----------------------------
-- Records of system_login_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '菜单名称',
    `permission`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限标识',
    `type`        tinyint                                                       NOT NULL COMMENT '菜单类型',
    `sort`        int                                                           NOT NULL DEFAULT '0' COMMENT '显示顺序',
    `parent_id`   bigint                                                        NOT NULL DEFAULT '0' COMMENT '父菜单ID',
    `path`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '路由地址',
    `icon`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '#' COMMENT '菜单图标',
    `component`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '组件路径',
    `status`      tinyint                                                       NOT NULL DEFAULT '0' COMMENT '菜单状态',
    `visible`     bit(1)                                                        NOT NULL DEFAULT b'1' COMMENT '是否可见',
    `keep_alive`  bit(1)                                                        NOT NULL DEFAULT b'1' COMMENT '是否缓存',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1268
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='菜单权限表';

-- ----------------------------
-- Records of system_menu
-- ----------------------------
BEGIN;
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1, '系统管理', '', 1, 10, 0, '/system', 'system', NULL, 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-06-30 11:46:49', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (2, '基础设施', '', 1, 20, 0, '/infra', 'monitor', NULL, 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (100, '用户管理', 'system:user:list', 2, 1, 1, 'user', 'user', 'system/user/index', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (101, '角色管理', '', 2, 2, 1, 'role', 'peoples', 'system/role/index', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (102, '菜单管理', '', 2, 3, 1, 'menu', 'tree-table', 'system/menu/index', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (103, '部门管理', '', 2, 4, 1, 'dept', 'tree', 'system/dept/index', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (104, '岗位管理', '', 2, 5, 1, 'post', 'post', 'system/post/index', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (105, '字典管理', '', 2, 6, 1, 'dict', 'dict', 'system/dict/index', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (106, '配置管理', '', 2, 6, 2, 'config', 'edit', 'infra/config/index', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (107, '通知公告', '', 2, 8, 1, 'notice', 'message', 'system/notice/index', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (108, '审计日志', '', 1, 9, 1, 'log', 'log', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (109, '令牌管理', '', 2, 2, 1261, 'token', 'online', 'system/oauth2/token/index', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-05-11 23:31:42', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (110, '定时任务', '', 2, 12, 2, 'job', 'job', 'infra/job/index', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (111, 'MySQL 监控', '', 2, 9, 2, 'druid', 'druid', 'infra/druid/index', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (112, 'Java 监控', '', 2, 11, 2, 'admin-server', 'server', 'infra/server/index', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (113, 'Redis 监控', '', 2, 10, 2, 'redis', 'redis', 'infra/redis/index', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (114, '表单构建', 'infra:build:list', 2, 2, 2, 'build', 'build', 'infra/build/index', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (115, '代码生成', 'infra:codegen:query', 2, 1, 2, 'codegen', 'code', 'infra/codegen/index', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (116, '系统接口', 'infra:swagger:list', 2, 3, 2, 'swagger', 'swagger', 'infra/swagger/index', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (500, '操作日志', '', 2, 1, 108, 'operate-log', 'form', 'system/operatelog/index', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (501, '登录日志', '', 2, 2, 108, 'login-log', 'logininfor', 'system/loginlog/index', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1001, '用户查询', 'system:user:query', 3, 1, 100, '', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1002, '用户新增', 'system:user:create', 3, 2, 100, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1003, '用户修改', 'system:user:update', 3, 3, 100, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1004, '用户删除', 'system:user:delete', 3, 4, 100, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1005, '用户导出', 'system:user:export', 3, 5, 100, '', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1006, '用户导入', 'system:user:import', 3, 6, 100, '', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1007, '重置密码', 'system:user:update-password', 3, 7, 100, '', '', '', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1008, '角色查询', 'system:role:query', 3, 1, 101, '', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1009, '角色新增', 'system:role:create', 3, 2, 101, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1010, '角色修改', 'system:role:update', 3, 3, 101, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1011, '角色删除', 'system:role:delete', 3, 4, 101, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1012, '角色导出', 'system:role:export', 3, 5, 101, '', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1013, '菜单查询', 'system:menu:query', 3, 1, 102, '', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1014, '菜单新增', 'system:menu:create', 3, 2, 102, '', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1015, '菜单修改', 'system:menu:update', 3, 3, 102, '', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1016, '菜单删除', 'system:menu:delete', 3, 4, 102, '', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1017, '部门查询', 'system:dept:query', 3, 1, 103, '', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1018, '部门新增', 'system:dept:create', 3, 2, 103, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1019, '部门修改', 'system:dept:update', 3, 3, 103, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1020, '部门删除', 'system:dept:delete', 3, 4, 103, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1021, '岗位查询', 'system:post:query', 3, 1, 104, '', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1022, '岗位新增', 'system:post:create', 3, 2, 104, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1023, '岗位修改', 'system:post:update', 3, 3, 104, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1024, '岗位删除', 'system:post:delete', 3, 4, 104, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1025, '岗位导出', 'system:post:export', 3, 5, 104, '', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1026, '字典查询', 'system:dict:query', 3, 1, 105, '#', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1027, '字典新增', 'system:dict:create', 3, 2, 105, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1028, '字典修改', 'system:dict:update', 3, 3, 105, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1029, '字典删除', 'system:dict:delete', 3, 4, 105, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1030, '字典导出', 'system:dict:export', 3, 5, 105, '#', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1031, '配置查询', 'infra:config:query', 3, 1, 106, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1032, '配置新增', 'infra:config:create', 3, 2, 106, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1033, '配置修改', 'infra:config:update', 3, 3, 106, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1034, '配置删除', 'infra:config:delete', 3, 4, 106, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1035, '配置导出', 'infra:config:export', 3, 5, 106, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1036, '公告查询', 'system:notice:query', 3, 1, 107, '#', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1037, '公告新增', 'system:notice:create', 3, 2, 107, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1038, '公告修改', 'system:notice:update', 3, 3, 107, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1039, '公告删除', 'system:notice:delete', 3, 4, 107, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1040, '操作查询', 'system:operate-log:query', 3, 1, 500, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1042, '日志导出', 'system:operate-log:export', 3, 2, 500, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1043, '登录查询', 'system:login-log:query', 3, 1, 501, '#', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1045, '日志导出', 'system:login-log:export', 3, 3, 501, '#', '#', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1046, '令牌列表', 'system:oauth2-token:page', 3, 1, 109, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48',
        '1', '2022-05-09 23:54:42', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1048, '令牌删除', 'system:oauth2-token:delete', 3, 2, 109, '', '', '', 0, b'1', b'1', 'admin',
        '2021-01-05 17:03:48', '1', '2022-05-09 23:54:53', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1050, '任务新增', 'infra:job:create', 3, 2, 110, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1051, '任务修改', 'infra:job:update', 3, 3, 110, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1052, '任务删除', 'infra:job:delete', 3, 4, 110, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1053, '状态修改', 'infra:job:update', 3, 5, 110, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1054, '任务导出', 'infra:job:export', 3, 7, 110, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1056, '生成修改', 'infra:codegen:update', 3, 2, 115, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1057, '生成删除', 'infra:codegen:delete', 3, 3, 115, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1058, '导入代码', 'infra:codegen:create', 3, 2, 115, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1059, '预览代码', 'infra:codegen:preview', 3, 4, 115, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1060, '生成代码', 'infra:codegen:download', 3, 5, 115, '', '', '', 0, b'1', b'1', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1063, '设置角色菜单权限', 'system:permission:assign-role-menu', 3, 6, 101, '', '', '', 0, b'1', b'1', '',
        '2021-01-06 17:53:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1064, '设置角色数据权限', 'system:permission:assign-role-data-scope', 3, 7, 101, '', '', '', 0, b'1', b'1', '',
        '2021-01-06 17:56:31', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1065, '设置用户角色', 'system:permission:assign-user-role', 3, 8, 101, '', '', '', 0, b'1', b'1', '',
        '2021-01-07 10:23:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1066, '获得 Redis 监控信息', 'infra:redis:get-monitor-info', 3, 1, 113, '', '', '', 0, b'1', b'1', '',
        '2021-01-26 01:02:31', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1067, '获得 Redis Key 列表', 'infra:redis:get-key-list', 3, 2, 113, '', '', '', 0, b'1', b'1', '',
        '2021-01-26 01:02:52', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1070, '代码生成示例', 'infra:test-demo:query', 2, 1, 2, 'test-demo', 'validCode', 'infra/testDemo/index', 0, b'1',
        b'1', '', '2021-02-06 12:42:49', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1071, '测试示例表创建', 'infra:test-demo:create', 3, 1, 1070, '', '', '', 0, b'1', b'1', '', '2021-02-06 12:42:49',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1072, '测试示例表更新', 'infra:test-demo:update', 3, 2, 1070, '', '', '', 0, b'1', b'1', '', '2021-02-06 12:42:49',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1073, '测试示例表删除', 'infra:test-demo:delete', 3, 3, 1070, '', '', '', 0, b'1', b'1', '', '2021-02-06 12:42:49',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1074, '测试示例表导出', 'infra:test-demo:export', 3, 4, 1070, '', '', '', 0, b'1', b'1', '', '2021-02-06 12:42:49',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1075, '任务触发', 'infra:job:trigger', 3, 8, 110, '', '', '', 0, b'1', b'1', '', '2021-02-07 13:03:10', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1076, '数据库文档', '', 2, 4, 2, 'db-doc', 'table', 'infra/dbDoc/index', 0, b'1', b'1', '', '2021-02-08 01:41:47',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1077, '监控平台', '', 2, 13, 2, 'skywalking', 'eye-open', 'infra/skywalking/index', 0, b'1', b'1', '',
        '2021-02-08 20:41:31', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1078, '访问日志', '', 2, 1, 1083, 'api-access-log', 'log', 'infra/apiAccessLog/index', 0, b'1', b'1', '',
        '2021-02-26 01:32:59', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1082, '日志导出', 'infra:api-access-log:export', 3, 2, 1078, '', '', '', 0, b'1', b'1', '', '2021-02-26 01:32:59',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1083, 'API 日志', '', 2, 8, 2, 'log', 'log', NULL, 0, b'1', b'1', '', '2021-02-26 02:18:24', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1084, '错误日志', 'infra:api-error-log:query', 2, 2, 1083, 'api-error-log', 'log', 'infra/apiErrorLog/index', 0,
        b'1', b'1', '', '2021-02-26 07:53:20', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1085, '日志处理', 'infra:api-error-log:update-status', 3, 2, 1084, '', '', '', 0, b'1', b'1', '',
        '2021-02-26 07:53:20', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1086, '日志导出', 'infra:api-error-log:export', 3, 3, 1084, '', '', '', 0, b'1', b'1', '', '2021-02-26 07:53:20',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1087, '任务查询', 'infra:job:query', 3, 1, 110, '', '', '', 0, b'1', b'1', '1', '2021-03-10 01:26:19', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1088, '日志查询', 'infra:api-access-log:query', 3, 1, 1078, '', '', '', 0, b'1', b'1', '1', '2021-03-10 01:28:04',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1089, '日志查询', 'infra:api-error-log:query', 3, 1, 1084, '', '', '', 0, b'1', b'1', '1', '2021-03-10 01:29:09',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1090, '文件列表', '', 2, 5, 1243, 'file', 'upload', 'infra/file/index', 0, b'1', b'1', '', '2021-03-12 20:16:20',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1091, '文件查询', 'infra:file:query', 3, 1, 1090, '', '', '', 0, b'1', b'1', '', '2021-03-12 20:16:20', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1092, '文件删除', 'infra:file:delete', 3, 4, 1090, '', '', '', 0, b'1', b'1', '', '2021-03-12 20:16:20', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1093, '短信管理', '', 1, 11, 1, 'sms', 'validCode', NULL, 0, b'1', b'1', '1', '2021-04-05 01:10:16', '1',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1094, '短信渠道', '', 2, 0, 1093, 'sms-channel', 'phone', 'system/sms/smsChannel', 0, b'1', b'1', '',
        '2021-04-01 11:07:15', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1095, '短信渠道查询', 'system:sms-channel:query', 3, 1, 1094, '', '', '', 0, b'1', b'1', '', '2021-04-01 11:07:15',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1096, '短信渠道创建', 'system:sms-channel:create', 3, 2, 1094, '', '', '', 0, b'1', b'1', '', '2021-04-01 11:07:15',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1097, '短信渠道更新', 'system:sms-channel:update', 3, 3, 1094, '', '', '', 0, b'1', b'1', '', '2021-04-01 11:07:15',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1098, '短信渠道删除', 'system:sms-channel:delete', 3, 4, 1094, '', '', '', 0, b'1', b'1', '', '2021-04-01 11:07:15',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1100, '短信模板', '', 2, 1, 1093, 'sms-template', 'phone', 'system/sms/smsTemplate', 0, b'1', b'1', '',
        '2021-04-01 17:35:17', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1101, '短信模板查询', 'system:sms-template:query', 3, 1, 1100, '', '', '', 0, b'1', b'1', '', '2021-04-01 17:35:17',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1102, '短信模板创建', 'system:sms-template:create', 3, 2, 1100, '', '', '', 0, b'1', b'1', '', '2021-04-01 17:35:17',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1103, '短信模板更新', 'system:sms-template:update', 3, 3, 1100, '', '', '', 0, b'1', b'1', '', '2021-04-01 17:35:17',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1104, '短信模板删除', 'system:sms-template:delete', 3, 4, 1100, '', '', '', 0, b'1', b'1', '', '2021-04-01 17:35:17',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1105, '短信模板导出', 'system:sms-template:export', 3, 5, 1100, '', '', '', 0, b'1', b'1', '', '2021-04-01 17:35:17',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1106, '发送测试短信', 'system:sms-template:send-sms', 3, 6, 1100, '', '', '', 0, b'1', b'1', '1',
        '2021-04-11 00:26:40', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1107, '短信日志', '', 2, 2, 1093, 'sms-log', 'phone', 'system/sms/smsLog', 0, b'1', b'1', '', '2021-04-11 08:37:05',
        '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1108, '短信日志查询', 'system:sms-log:query', 3, 1, 1107, '', '', '', 0, b'1', b'1', '', '2021-04-11 08:37:05', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1109, '短信日志导出', 'system:sms-log:export', 3, 5, 1107, '', '', '', 0, b'1', b'1', '', '2021-04-11 08:37:05', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1110, '错误码管理', '', 2, 12, 1, 'error-code', 'code', 'system/errorCode/index', 0, b'1', b'1', '',
        '2021-04-13 21:46:42', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1111, '错误码查询', 'system:error-code:query', 3, 1, 1110, '', '', '', 0, b'1', b'1', '', '2021-04-13 21:46:42', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1112, '错误码创建', 'system:error-code:create', 3, 2, 1110, '', '', '', 0, b'1', b'1', '', '2021-04-13 21:46:42', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1113, '错误码更新', 'system:error-code:update', 3, 3, 1110, '', '', '', 0, b'1', b'1', '', '2021-04-13 21:46:42', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1114, '错误码删除', 'system:error-code:delete', 3, 4, 1110, '', '', '', 0, b'1', b'1', '', '2021-04-13 21:46:42', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1115, '错误码导出', 'system:error-code:export', 3, 5, 1110, '', '', '', 0, b'1', b'1', '', '2021-04-13 21:46:42', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1237, '文件配置', '', 2, 0, 1243, 'file-config', 'config', 'infra/fileConfig/index', 0, b'1', b'1', '',
        '2022-03-15 14:35:28', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1238, '文件配置查询', 'infra:file-config:query', 3, 1, 1237, '', '', '', 0, b'1', b'1', '', '2022-03-15 14:35:28', '',
        '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1239, '文件配置创建', 'infra:file-config:create', 3, 2, 1237, '', '', '', 0, b'1', b'1', '', '2022-03-15 14:35:28',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1240, '文件配置更新', 'infra:file-config:update', 3, 3, 1237, '', '', '', 0, b'1', b'1', '', '2022-03-15 14:35:28',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1241, '文件配置删除', 'infra:file-config:delete', 3, 4, 1237, '', '', '', 0, b'1', b'1', '', '2022-03-15 14:35:28',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1242, '文件配置导出', 'infra:file-config:export', 3, 5, 1237, '', '', '', 0, b'1', b'1', '', '2022-03-15 14:35:28',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1243, '文件管理', '', 2, 5, 2, 'file', 'documentation', NULL, 0, b'1', b'1', '1', '2022-03-16 23:47:40', '1',
        '2022-06-30 11:18:28', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1247, '敏感词管理', '', 2, 13, 1, 'sensitive-word', 'education', 'system/sensitiveWord/index', 0, b'1', b'1', '',
        '2022-04-07 16:55:03', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1248, '敏感词查询', 'system:sensitive-word:query', 3, 1, 1247, '', '', '', 0, b'1', b'1', '', '2022-04-07 16:55:03',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1249, '敏感词创建', 'system:sensitive-word:create', 3, 2, 1247, '', '', '', 0, b'1', b'1', '', '2022-04-07 16:55:03',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1250, '敏感词更新', 'system:sensitive-word:update', 3, 3, 1247, '', '', '', 0, b'1', b'1', '', '2022-04-07 16:55:03',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1251, '敏感词删除', 'system:sensitive-word:delete', 3, 4, 1247, '', '', '', 0, b'1', b'1', '', '2022-04-07 16:55:03',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1252, '敏感词导出', 'system:sensitive-word:export', 3, 5, 1247, '', '', '', 0, b'1', b'1', '', '2022-04-07 16:55:03',
        '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1255, '数据源配置', '', 2, 1, 2, 'data-source-config', 'rate', 'infra/dataSourceConfig/index', 0, b'1', b'1', '',
        '2022-04-27 14:37:32', '1', '2022-04-27 22:42:06', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1256, '数据源配置查询', 'infra:data-source-config:query', 3, 1, 1255, '', '', '', 0, b'1', b'1', '',
        '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1257, '数据源配置创建', 'infra:data-source-config:create', 3, 2, 1255, '', '', '', 0, b'1', b'1', '',
        '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1258, '数据源配置更新', 'infra:data-source-config:update', 3, 3, 1255, '', '', '', 0, b'1', b'1', '',
        '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1259, '数据源配置删除', 'infra:data-source-config:delete', 3, 4, 1255, '', '', '', 0, b'1', b'1', '',
        '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1260, '数据源配置导出', 'infra:data-source-config:export', 3, 5, 1255, '', '', '', 0, b'1', b'1', '',
        '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1261, 'OAuth 2.0', '', 1, 10, 1, 'oauth2', 'people', NULL, 0, b'1', b'1', '1', '2022-05-09 23:38:17', '1',
        '2022-05-11 23:51:46', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1263, '应用管理', '', 2, 0, 1261, 'oauth2/application', 'tool', 'system/oauth2/client/index', 0, b'1', b'1', '',
        '2022-05-10 16:26:33', '1', '2022-05-11 23:31:36', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1264, '客户端查询', 'system:oauth2-client:query', 3, 1, 1263, '', '', '', 0, b'1', b'1', '', '2022-05-10 16:26:33',
        '1', '2022-05-11 00:31:06', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1265, '客户端创建', 'system:oauth2-client:create', 3, 2, 1263, '', '', '', 0, b'1', b'1', '', '2022-05-10 16:26:33',
        '1', '2022-05-11 00:31:23', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1266, '客户端更新', 'system:oauth2-client:update', 3, 3, 1263, '', '', '', 0, b'1', b'1', '', '2022-05-10 16:26:33',
        '1', '2022-05-11 00:31:28', b'0');
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`,
                           `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`,
                           `deleted`)
VALUES (1267, '客户端删除', 'system:oauth2-client:delete', 3, 4, 1263, '', '', '', 0, b'1', b'1', '', '2022-05-10 16:26:33',
        '1', '2022-05-11 00:31:33', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_notice
-- ----------------------------
DROP TABLE IF EXISTS `system_notice`;
CREATE TABLE `system_notice`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '公告ID',
    `title`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告标题',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci        NOT NULL COMMENT '公告内容',
    `type`        tinyint                                                      NOT NULL COMMENT '公告类型（1通知 2公告）',
    `status`      tinyint                                                      NOT NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint                                                       NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='通知公告表';

-- ----------------------------
-- Records of system_notice
-- ----------------------------
BEGIN;
INSERT INTO `system_notice` (`id`, `title`, `content`, `type`, `status`, `creator`, `create_time`, `updater`,
                             `update_time`, `deleted`, `tenant_id`)
VALUES (1, '芋道的公众', '<p>新版本内容133</p>', 1, 0, 'admin', '2021-01-05 17:03:48', '1', '2022-05-04 21:00:20', b'0', 1);
INSERT INTO `system_notice` (`id`, `title`, `content`, `type`, `status`, `creator`, `create_time`, `updater`,
                             `update_time`, `deleted`, `tenant_id`)
VALUES (2, '维护通知：2018-07-01 若依系统凌晨维护',
        '<p><img src=\"http://test.kugga.iocoder.cn/b7cb3cf49b4b3258bf7309a09dd2f4e5.jpg\">维护内容</p>', 2, 1, 'admin',
        '2021-01-05 17:03:48', '1', '2022-05-11 12:34:24', b'0', 1);
INSERT INTO `system_notice` (`id`, `title`, `content`, `type`, `status`, `creator`, `create_time`, `updater`,
                             `update_time`, `deleted`, `tenant_id`)
VALUES (4, '我是测试标题', '<p>哈哈哈哈123</p>', 1, 0, '110', '2022-02-22 01:01:25', '110', '2022-02-22 01:01:46', b'0', 121);
COMMIT;

-- ----------------------------
-- Table structure for system_oauth2_access_token
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_access_token`;
CREATE TABLE `system_oauth2_access_token`
(
    `id`            bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`       bigint                                                        NOT NULL COMMENT '用户编号',
    `user_type`     tinyint                                                       NOT NULL COMMENT '用户类型',
    `access_token`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '访问令牌',
    `refresh_token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '刷新令牌',
    `client_id`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
    `scopes`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '授权范围',
    `expires_time`  datetime                                                      NOT NULL COMMENT '过期时间',
    `creator`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`   datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`   datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`     bigint                                                        NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 235
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='OAuth2 访问令牌';

-- ----------------------------
-- Records of system_oauth2_access_token
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_oauth2_approve
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_approve`;
CREATE TABLE `system_oauth2_approve`
(
    `id`           bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`      bigint                                                        NOT NULL COMMENT '用户编号',
    `user_type`    tinyint                                                       NOT NULL COMMENT '用户类型',
    `client_id`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
    `scope`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '授权范围',
    `approved`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否接受',
    `expires_time` datetime                                                      NOT NULL COMMENT '过期时间',
    `creator`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`    bigint                                                        NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 80
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='OAuth2 批准表';

-- ----------------------------
-- Records of system_oauth2_approve
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_oauth2_client
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_client`;
CREATE TABLE `system_oauth2_client`
(
    `id`                             bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `client_id`                      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
    `secret`                         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端密钥',
    `name`                           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
    `logo`                           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用图标',
    `description`                    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '应用描述',
    `status`                         tinyint                                                       NOT NULL COMMENT '状态',
    `access_token_validity_seconds`  int                                                           NOT NULL COMMENT '访问令牌的有效期',
    `refresh_token_validity_seconds` int                                                           NOT NULL COMMENT '刷新令牌的有效期',
    `redirect_uris`                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '可重定向的 URI 地址',
    `authorized_grant_types`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '授权类型',
    `scopes`                         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '授权范围',
    `auto_approve_scopes`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '自动通过的授权范围',
    `authorities`                    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '权限',
    `resource_ids`                   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '资源',
    `additional_information`         varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '附加信息',
    `creator`                        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`                    datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`                        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`                    datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`                        bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 41
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='OAuth2 客户端表';

-- ----------------------------
-- Records of system_oauth2_client
-- ----------------------------
BEGIN;
INSERT INTO `system_oauth2_client` (`id`, `client_id`, `secret`, `name`, `logo`, `description`, `status`,
                                    `access_token_validity_seconds`, `refresh_token_validity_seconds`, `redirect_uris`,
                                    `authorized_grant_types`, `scopes`, `auto_approve_scopes`, `authorities`,
                                    `resource_ids`, `additional_information`, `creator`, `create_time`, `updater`,
                                    `update_time`, `deleted`)
VALUES (1, 'default', 'admin123', '芋道源码', 'http://test.kugga.iocoder.cn/a5e2e244368878a366b516805a4aabf1.png', '我是描述',
        0, 999999999, 8640, '[\"https://www.iocoder.cn\",\"https://doc.iocoder.cn\"]',
        '[\"password\",\"authorization_code\",\"implicit\",\"refresh_token\"]', '[\"user.read\",\"user.write\"]', '[]',
        '[\"user.read\",\"user.write\"]', '[]', '{}', '1', '2022-05-11 21:47:12', '1', '2022-05-23 13:33:11', b'0');
INSERT INTO `system_oauth2_client` (`id`, `client_id`, `secret`, `name`, `logo`, `description`, `status`,
                                    `access_token_validity_seconds`, `refresh_token_validity_seconds`, `redirect_uris`,
                                    `authorized_grant_types`, `scopes`, `auto_approve_scopes`, `authorities`,
                                    `resource_ids`, `additional_information`, `creator`, `create_time`, `updater`,
                                    `update_time`, `deleted`)
VALUES (40, 'test', 'test2', 'biubiu', 'http://test.kugga.iocoder.cn/277a899d573723f1fcdfb57340f00379.png', NULL, 0,
        1800, 43200, '[\"https://www.iocoder.cn\"]', '[\"password\",\"authorization_code\",\"implicit\"]',
        '[\"user_info\",\"projects\"]', '[\"user_info\"]', '[]', '[]', '{}', '1', '2022-05-12 00:28:20', '1',
        '2022-05-14 15:11:31', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_oauth2_code
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_code`;
CREATE TABLE `system_oauth2_code`
(
    `id`           bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`      bigint                                                        NOT NULL COMMENT '用户编号',
    `user_type`    tinyint                                                       NOT NULL COMMENT '用户类型',
    `code`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '授权码',
    `client_id`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
    `scopes`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '授权范围',
    `expires_time` datetime                                                      NOT NULL COMMENT '过期时间',
    `redirect_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '可重定向的 URI 地址',
    `state`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '状态',
    `creator`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`    bigint                                                        NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='OAuth2 授权码表';

-- ----------------------------
-- Records of system_oauth2_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_oauth2_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_refresh_token`;
CREATE TABLE `system_oauth2_refresh_token`
(
    `id`            bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`       bigint                                                        NOT NULL COMMENT '用户编号',
    `refresh_token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '刷新令牌',
    `user_type`     tinyint                                                       NOT NULL COMMENT '用户类型',
    `client_id`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
    `scopes`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '授权范围',
    `expires_time`  datetime                                                      NOT NULL COMMENT '过期时间',
    `creator`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`   datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`   datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`     bigint                                                        NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 169
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='刷新令牌';

-- ----------------------------
-- Records of system_oauth2_refresh_token
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `system_operate_log`;
CREATE TABLE `system_operate_log`
(
    `id`               bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `trace_id`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '' COMMENT '链路追踪编号',
    `user_id`          bigint                                                         NOT NULL COMMENT '用户编号',
    `user_type`        tinyint                                                        NOT NULL DEFAULT '0' COMMENT '用户类型',
    `module`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '模块标题',
    `name`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '操作名',
    `type`             bigint                                                         NOT NULL DEFAULT '0' COMMENT '操作分类',
    `content`          varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作内容',
    `exts`             varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '拓展字段',
    `request_method`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '请求方法名',
    `request_url`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '请求地址',
    `user_ip`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT NULL COMMENT '用户 IP',
    `user_agent`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '浏览器 UA',
    `java_method`      varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT 'Java 方法名',
    `java_method_args` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT 'Java 方法的参数',
    `start_time`       datetime                                                       NOT NULL COMMENT '操作时间',
    `duration`         int                                                            NOT NULL COMMENT '执行时长',
    `result_code`      int                                                            NOT NULL DEFAULT '0' COMMENT '结果码',
    `result_msg`       varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '结果提示',
    `result_data`      varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '结果数据',
    `creator`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time`      datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time`      datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`        bigint                                                         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='操作日志记录';

-- ----------------------------
-- Records of system_operate_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_post
-- ----------------------------
DROP TABLE IF EXISTS `system_post`;
CREATE TABLE `system_post`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `code`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位编码',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位名称',
    `sort`        int                                                          NOT NULL COMMENT '显示顺序',
    `status`      tinyint                                                      NOT NULL COMMENT '状态（0正常 1停用）',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint                                                       NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='岗位信息表';

-- ----------------------------
-- Records of system_post
-- ----------------------------
BEGIN;
INSERT INTO `system_post` (`id`, `code`, `name`, `sort`, `status`, `remark`, `creator`, `create_time`, `updater`,
                           `update_time`, `deleted`, `tenant_id`)
VALUES (1, 'ceo', '董事长', 1, 0, '', 'admin', '2021-01-06 17:03:48', '1', '2022-04-19 16:53:39', b'0', 1);
INSERT INTO `system_post` (`id`, `code`, `name`, `sort`, `status`, `remark`, `creator`, `create_time`, `updater`,
                           `update_time`, `deleted`, `tenant_id`)
VALUES (2, 'se', '项目经理', 2, 0, '', 'admin', '2021-01-05 17:03:48', '1', '2021-12-12 10:47:47', b'0', 1);
INSERT INTO `system_post` (`id`, `code`, `name`, `sort`, `status`, `remark`, `creator`, `create_time`, `updater`,
                           `update_time`, `deleted`, `tenant_id`)
VALUES (4, 'user', '普通员工', 4, 0, '111', 'admin', '2021-01-05 17:03:48', '1', '2022-05-04 22:46:35', b'0', 1);
COMMIT;

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`
(
    `id`                  bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `name`                varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '角色名称',
    `code`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
    `sort`                int                                                           NOT NULL COMMENT '显示顺序',
    `data_scope`          tinyint                                                       NOT NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `data_scope_dept_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据范围(指定部门数组)',
    `status`              tinyint                                                       NOT NULL COMMENT '角色状态（0正常 1停用）',
    `type`                tinyint                                                       NOT NULL COMMENT '角色类型',
    `remark`              varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '备注',
    `creator`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint                                                        NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 114
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='角色信息表';

-- ----------------------------
-- Records of system_role
-- ----------------------------
BEGIN;
INSERT INTO `system_role` (`id`, `name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`,
                           `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (1, '超级管理员', 'super_admin', 1, 1, '', 0, 1, '超级管理员', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:21',
        b'0', 1);
INSERT INTO `system_role` (`id`, `name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`,
                           `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (2, '普通角色', 'common', 2, 2, '', 0, 1, '普通角色', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:20', b'0',
        1);
INSERT INTO `system_role` (`id`, `name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`,
                           `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (109, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2022-02-22 00:56:14', '1', '2022-02-22 00:56:14',
        b'0', 121);
INSERT INTO `system_role` (`id`, `name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`,
                           `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (111, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58',
        b'0', 122);
INSERT INTO `system_role` (`id`, `name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`,
                           `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (113, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10',
        b'0', 124);
COMMIT;

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '自增编号',
    `role_id`     bigint   NOT NULL COMMENT '角色ID',
    `menu_id`     bigint   NOT NULL COMMENT '菜单ID',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)   NOT NULL                                            DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint   NOT NULL                                            DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1879
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='角色和菜单关联表';

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (263, 109, 1, '1', '2022-02-22 00:56:14', '1', '2022-02-22 00:56:14', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (434, 2, 1, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (454, 2, 1093, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (455, 2, 1094, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (460, 2, 1100, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (467, 2, 1107, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (470, 2, 1110, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (477, 2, 100, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (478, 2, 101, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (479, 2, 102, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (481, 2, 103, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (483, 2, 104, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (485, 2, 105, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (488, 2, 107, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (490, 2, 108, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (492, 2, 109, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (541, 2, 500, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (543, 2, 501, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (675, 2, 2, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (689, 2, 1077, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (690, 2, 1078, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (692, 2, 1083, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (693, 2, 1084, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (699, 2, 1090, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (703, 2, 106, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (704, 2, 110, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (705, 2, 111, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (706, 2, 112, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (707, 2, 113, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1486, 109, 103, '1', '2022-02-23 19:32:14', '1', '2022-02-23 19:32:14', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1487, 109, 104, '1', '2022-02-23 19:32:14', '1', '2022-02-23 19:32:14', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1489, 1, 1, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1490, 1, 2, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1494, 1, 1077, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1495, 1, 1078, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1496, 1, 1083, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1497, 1, 1084, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1498, 1, 1090, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1499, 1, 1093, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1500, 1, 1094, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1501, 1, 1100, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1502, 1, 1107, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1503, 1, 1110, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1506, 1, 100, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1507, 1, 101, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1508, 1, 102, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1510, 1, 103, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1511, 1, 104, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1512, 1, 105, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1513, 1, 106, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1514, 1, 107, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1515, 1, 108, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1516, 1, 109, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1517, 1, 110, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1518, 1, 111, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1519, 1, 112, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1520, 1, 113, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1527, 1, 500, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1528, 1, 501, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1529, 109, 1024, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1530, 109, 1025, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1536, 109, 1017, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1537, 109, 1018, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1538, 109, 1019, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1539, 109, 1020, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1540, 109, 1021, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1541, 109, 1022, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1542, 109, 1023, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1576, 111, 1024, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1577, 111, 1025, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1578, 111, 1, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1584, 111, 103, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1585, 111, 104, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1587, 111, 1017, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1588, 111, 1018, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1589, 111, 1019, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1590, 111, 1020, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1591, 111, 1021, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1592, 111, 1022, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1593, 111, 1023, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1594, 109, 102, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1595, 109, 1013, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1596, 109, 1014, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1597, 109, 1015, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1598, 109, 1016, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', b'0', 121);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1599, 111, 102, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1600, 111, 1013, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1601, 111, 1014, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1602, 111, 1015, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1603, 111, 1016, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', b'0', 122);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1712, 113, 1024, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1713, 113, 1025, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1714, 113, 1, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1715, 113, 102, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1716, 113, 103, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1717, 113, 104, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1718, 113, 1013, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1719, 113, 1014, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1720, 113, 1015, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1721, 113, 1016, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1722, 113, 1017, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1723, 113, 1018, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1724, 113, 1019, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1725, 113, 1020, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1726, 113, 1021, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1727, 113, 1022, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1728, 113, 1023, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1729, 1, 1024, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1730, 1, 1025, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1731, 1, 1026, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1732, 1, 1027, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1733, 1, 1028, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1734, 1, 1029, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1735, 1, 1030, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1736, 1, 1031, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1737, 1, 1032, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1738, 1, 1033, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1739, 1, 1034, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1740, 1, 1035, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1741, 1, 1036, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1742, 1, 1037, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1743, 1, 1038, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1744, 1, 1039, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1745, 1, 1040, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1746, 1, 1042, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1747, 1, 1043, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1748, 1, 1045, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1749, 1, 1046, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1750, 1, 1048, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1751, 1, 1050, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1752, 1, 1051, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1753, 1, 1052, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1754, 1, 1053, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1755, 1, 1054, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1756, 1, 1056, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1757, 1, 1057, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1758, 1, 1058, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1759, 1, 1059, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1760, 1, 1060, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1761, 1, 1063, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1762, 1, 1064, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1763, 1, 1065, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1764, 1, 1066, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1765, 1, 1067, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1766, 1, 1070, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1767, 1, 1071, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1768, 1, 1072, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1769, 1, 1073, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1770, 1, 1074, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1771, 1, 1075, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1772, 1, 1076, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1773, 1, 1082, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1774, 1, 1085, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1775, 1, 1086, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1776, 1, 1087, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1777, 1, 1088, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1778, 1, 1089, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1779, 1, 1091, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1780, 1, 1092, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1781, 1, 1095, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1782, 1, 1096, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1783, 1, 1097, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1784, 1, 1098, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1785, 1, 1101, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1786, 1, 1102, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1787, 1, 1103, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1788, 1, 1104, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1789, 1, 1105, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1790, 1, 1106, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1791, 1, 1108, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1792, 1, 1109, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1793, 1, 1111, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1794, 1, 1112, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1795, 1, 1113, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1796, 1, 1114, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1797, 1, 1115, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1809, 1, 114, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1810, 1, 115, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1811, 1, 116, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1831, 1, 1237, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1832, 1, 1238, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1833, 1, 1239, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1834, 1, 1240, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1835, 1, 1241, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1836, 1, 1242, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1837, 1, 1243, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1838, 1, 1247, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1839, 1, 1248, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1840, 1, 1249, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1841, 1, 1250, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1842, 1, 1251, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1843, 1, 1252, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1844, 1, 1255, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1845, 1, 1256, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1846, 1, 1001, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1847, 1, 1257, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1848, 1, 1002, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1849, 1, 1258, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1850, 1, 1003, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1851, 1, 1259, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1852, 1, 1004, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1853, 1, 1260, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1854, 1, 1005, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1855, 1, 1261, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1856, 1, 1006, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1857, 1, 1007, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1858, 1, 1263, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1859, 1, 1008, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1860, 1, 1264, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1861, 1, 1009, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1862, 1, 1265, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1863, 1, 1010, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1864, 1, 1266, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1865, 1, 1011, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1866, 1, 1267, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1867, 1, 1012, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1868, 1, 1013, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1869, 1, 1014, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1870, 1, 1015, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1871, 1, 1016, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1872, 1, 1017, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1873, 1, 1018, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1874, 1, 1019, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1875, 1, 1020, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1876, 1, 1021, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1877, 1, 1022, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1878, 1, 1023, '1', '2022-06-30 09:46:31', '1', '2022-06-30 09:46:31', b'0', 0);
COMMIT;

-- ----------------------------
-- Table structure for system_sensitive_word
-- ----------------------------
DROP TABLE IF EXISTS `system_sensitive_word`;
CREATE TABLE `system_sensitive_word`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '敏感词',
    `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '描述',
    `tags`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '标签数组',
    `status`      tinyint                                                       NOT NULL COMMENT '状态',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='敏感词';

-- ----------------------------
-- Records of system_sensitive_word
-- ----------------------------
BEGIN;
INSERT INTO `system_sensitive_word` (`id`, `name`, `description`, `tags`, `status`, `creator`, `create_time`, `updater`,
                                     `update_time`, `deleted`)
VALUES (3, '土豆', '好呀', '蔬菜,短信', 0, '1', '2022-04-08 21:07:12', '1', '2022-04-09 10:28:14', b'0');
INSERT INTO `system_sensitive_word` (`id`, `name`, `description`, `tags`, `status`, `creator`, `create_time`, `updater`,
                                     `update_time`, `deleted`)
VALUES (4, 'XXX', NULL, '短信', 0, '1', '2022-04-08 21:27:49', '1', '2022-04-08 21:27:49', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_sms_channel
-- ----------------------------
DROP TABLE IF EXISTS `system_sms_channel`;
CREATE TABLE `system_sms_channel`
(
    `id`           bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `signature`    varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '短信签名',
    `code`         varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '渠道编码',
    `status`       tinyint                                                       NOT NULL COMMENT '开启状态',
    `remark`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '备注',
    `api_key`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信 API 的账号',
    `api_secret`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '短信 API 的秘钥',
    `callback_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '短信发送回调 URL',
    `creator`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='短信渠道';

-- ----------------------------
-- Records of system_sms_channel
-- ----------------------------
BEGIN;
INSERT INTO `system_sms_channel` (`id`, `signature`, `code`, `status`, `remark`, `api_key`, `api_secret`,
                                  `callback_url`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1, '芋道', 'YUN_PIAN', 0, '呵呵呵哒', '1555a14277cb8a608cf45a9e6a80d510', NULL,
        'http://vdwapu.natappfree.cc/admin-api/system/sms/callback/yunpian', '', '2021-03-31 06:12:20', '1',
        '2022-02-23 16:48:44', b'0');
INSERT INTO `system_sms_channel` (`id`, `signature`, `code`, `status`, `remark`, `api_key`, `api_secret`,
                                  `callback_url`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (2, 'Ballcat', 'ALIYUN', 0, '啦啦啦', 'LTAI5tCnKso2uG3kJ5gRav88', 'fGJ5SNXL7P1NHNRmJ7DJaMJGPyE55C', NULL, '',
        '2021-03-31 11:53:10', '1', '2021-04-14 00:08:37', b'0');
INSERT INTO `system_sms_channel` (`id`, `signature`, `code`, `status`, `remark`, `api_key`, `api_secret`,
                                  `callback_url`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (4, '测试渠道', 'DEBUG_DING_TALK', 0, '123', '696b5d8ead48071237e4aa5861ff08dbadb2b4ded1c688a7b7c9afc615579859',
        'SEC5c4e5ff888bc8a9923ae47f59e7ccd30af1f14d93c55b4e2c9cb094e35aeed67', NULL, '1', '2021-04-13 00:23:14', '1',
        '2022-03-27 20:29:49', b'0');
INSERT INTO `system_sms_channel` (`id`, `signature`, `code`, `status`, `remark`, `api_key`, `api_secret`,
                                  `callback_url`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (6, '测试演示', 'DEBUG_DING_TALK', 0, NULL, '696b5d8ead48071237e4aa5861ff08dbadb2b4ded1c688a7b7c9afc615579859',
        'SEC5c4e5ff888bc8a9923ae47f59e7ccd30af1f14d93c55b4e2c9cb094e35aeed67', NULL, '1', '2022-04-10 23:07:59', '1',
        '2022-05-16 20:34:49', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_sms_code
-- ----------------------------
DROP TABLE IF EXISTS `system_sms_code`;
CREATE TABLE `system_sms_code`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '编号',
    `mobile`      varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
    `code`        varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '验证码',
    `create_ip`   varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建 IP',
    `scene`       tinyint                                                      NOT NULL COMMENT '发送场景',
    `today_index` tinyint                                                      NOT NULL COMMENT '今日发送的第几条',
    `used`        tinyint                                                      NOT NULL COMMENT '是否使用',
    `used_time`   datetime                                                              DEFAULT NULL COMMENT '使用时间',
    `used_ip`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '使用 IP',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint                                                       NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_mobile` (`mobile`) USING BTREE COMMENT '手机号'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='手机验证码';

-- ----------------------------
-- Records of system_sms_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `system_sms_log`;
CREATE TABLE `system_sms_log`
(
    `id`               bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `channel_id`       bigint                                                        NOT NULL COMMENT '短信渠道编号',
    `channel_code`     varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '短信渠道编码',
    `template_id`      bigint                                                        NOT NULL COMMENT '模板编号',
    `template_code`    varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '模板编码',
    `template_type`    tinyint                                                       NOT NULL COMMENT '短信类型',
    `template_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信内容',
    `template_params`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信参数',
    `api_template_id`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '短信 API 的模板编号',
    `mobile`           varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '手机号',
    `user_id`          bigint                                                                 DEFAULT NULL COMMENT '用户编号',
    `user_type`        tinyint                                                                DEFAULT NULL COMMENT '用户类型',
    `send_status`      tinyint                                                       NOT NULL DEFAULT '0' COMMENT '发送状态',
    `send_time`        datetime                                                               DEFAULT NULL COMMENT '发送时间',
    `send_code`        int                                                                    DEFAULT NULL COMMENT '发送结果的编码',
    `send_msg`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '发送结果的提示',
    `api_send_code`    varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '短信 API 发送结果的编码',
    `api_send_msg`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '短信 API 发送失败的提示',
    `api_request_id`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '短信 API 发送返回的唯一请求 ID',
    `api_serial_no`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '短信 API 发送返回的序号',
    `receive_status`   tinyint                                                       NOT NULL DEFAULT '0' COMMENT '接收状态',
    `receive_time`     datetime                                                               DEFAULT NULL COMMENT '接收时间',
    `api_receive_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT 'API 接收结果的编码',
    `api_receive_msg`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT 'API 接收结果的说明',
    `creator`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`      datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`      datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='短信日志';

-- ----------------------------
-- Records of system_sms_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `system_sms_template`;
CREATE TABLE `system_sms_template`
(
    `id`              bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `type`            tinyint                                                       NOT NULL COMMENT '短信签名',
    `status`          tinyint                                                       NOT NULL COMMENT '开启状态',
    `code`            varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '模板编码',
    `name`            varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '模板名称',
    `content`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板内容',
    `params`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数数组',
    `remark`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '备注',
    `api_template_id` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '短信 API 的模板编号',
    `channel_id`      bigint                                                        NOT NULL COMMENT '短信渠道编号',
    `channel_code`    varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '短信渠道编码',
    `creator`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`     datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`     datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='短信模板';

-- ----------------------------
-- Records of system_sms_template
-- ----------------------------
BEGIN;
INSERT INTO `system_sms_template` (`id`, `type`, `status`, `code`, `name`, `content`, `params`, `remark`,
                                   `api_template_id`, `channel_id`, `channel_code`, `creator`, `create_time`, `updater`,
                                   `update_time`, `deleted`)
VALUES (2, 1, 0, 'test_01', '测试验证码短信', '正在进行登录操作{operation}，您的验证码是{code}', '[\"operation\",\"code\"]', NULL, '4383920',
        1, 'YUN_PIAN', '', '2021-03-31 10:49:38', '1', '2021-04-10 01:22:00', b'0');
INSERT INTO `system_sms_template` (`id`, `type`, `status`, `code`, `name`, `content`, `params`, `remark`,
                                   `api_template_id`, `channel_id`, `channel_code`, `creator`, `create_time`, `updater`,
                                   `update_time`, `deleted`)
VALUES (3, 1, 0, 'test_02', '公告通知', '您的验证码{code}，该验证码5分钟内有效，请勿泄漏于他人！', '[\"code\"]', NULL, 'SMS_207945135', 2, 'ALIYUN',
        '', '2021-03-31 11:56:30', '1', '2021-04-10 01:22:02', b'0');
INSERT INTO `system_sms_template` (`id`, `type`, `status`, `code`, `name`, `content`, `params`, `remark`,
                                   `api_template_id`, `channel_id`, `channel_code`, `creator`, `create_time`, `updater`,
                                   `update_time`, `deleted`)
VALUES (6, 3, 0, 'test-01', '测试模板', '哈哈哈 {name}', '[\"name\"]', 'f哈哈哈', '4383920', 1, 'YUN_PIAN', '1',
        '2021-04-10 01:07:21', '1', '2021-04-10 01:22:05', b'0');
INSERT INTO `system_sms_template` (`id`, `type`, `status`, `code`, `name`, `content`, `params`, `remark`,
                                   `api_template_id`, `channel_id`, `channel_code`, `creator`, `create_time`, `updater`,
                                   `update_time`, `deleted`)
VALUES (7, 3, 0, 'test-04', '测试下', '老鸡{name}，牛逼{code}', '[\"name\",\"code\"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK',
        '1', '2021-04-13 00:29:53', '1', '2021-04-14 00:30:38', b'0');
INSERT INTO `system_sms_template` (`id`, `type`, `status`, `code`, `name`, `content`, `params`, `remark`,
                                   `api_template_id`, `channel_id`, `channel_code`, `creator`, `create_time`, `updater`,
                                   `update_time`, `deleted`)
VALUES (8, 1, 0, 'user-sms-login', '前台用户短信登录', '您的验证码是{code}', '[\"code\"]', NULL, '4372216', 1, 'YUN_PIAN', '1',
        '2021-10-11 08:10:00', '1', '2021-10-11 08:10:00', b'0');
INSERT INTO `system_sms_template` (`id`, `type`, `status`, `code`, `name`, `content`, `params`, `remark`,
                                   `api_template_id`, `channel_id`, `channel_code`, `creator`, `create_time`, `updater`,
                                   `update_time`, `deleted`)
VALUES (9, 2, 0, 'bpm_task_assigned', '【工作流】任务被分配',
        '您收到了一条新的待办任务：{processInstanceName}-{taskName}，申请人：{startUserNickname}，处理链接：{detailUrl}',
        '[\"processInstanceName\",\"taskName\",\"startUserNickname\",\"detailUrl\"]', NULL, 'suibian', 4,
        'DEBUG_DING_TALK', '1', '2022-01-21 22:31:19', '1', '2022-01-22 00:03:36', b'0');
INSERT INTO `system_sms_template` (`id`, `type`, `status`, `code`, `name`, `content`, `params`, `remark`,
                                   `api_template_id`, `channel_id`, `channel_code`, `creator`, `create_time`, `updater`,
                                   `update_time`, `deleted`)
VALUES (10, 2, 0, 'bpm_process_instance_reject', '【工作流】流程被不通过',
        '您的流程被审批不通过：{processInstanceName}，原因：{reason}，查看链接：{detailUrl}',
        '[\"processInstanceName\",\"reason\",\"detailUrl\"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK', '1',
        '2022-01-22 00:03:31', '1', '2022-05-01 12:33:14', b'0');
INSERT INTO `system_sms_template` (`id`, `type`, `status`, `code`, `name`, `content`, `params`, `remark`,
                                   `api_template_id`, `channel_id`, `channel_code`, `creator`, `create_time`, `updater`,
                                   `update_time`, `deleted`)
VALUES (11, 2, 0, 'bpm_process_instance_approve', '【工作流】流程被通过', '您的流程被审批通过：{processInstanceName}，查看链接：{detailUrl}',
        '[\"processInstanceName\",\"detailUrl\"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK', '1', '2022-01-22 00:04:31',
        '1', '2022-03-27 20:32:21', b'0');
INSERT INTO `system_sms_template` (`id`, `type`, `status`, `code`, `name`, `content`, `params`, `remark`,
                                   `api_template_id`, `channel_id`, `channel_code`, `creator`, `create_time`, `updater`,
                                   `update_time`, `deleted`)
VALUES (12, 2, 0, 'demo', '演示模板', '我就是测试一下下', '[]', NULL, 'biubiubiu', 6, 'DEBUG_DING_TALK', '1', '2022-04-10 23:22:49',
        '1', '2022-04-10 23:22:49', b'0');
INSERT INTO `system_sms_template` (`id`, `type`, `status`, `code`, `name`, `content`, `params`, `remark`,
                                   `api_template_id`, `channel_id`, `channel_code`, `creator`, `create_time`, `updater`,
                                   `update_time`, `deleted`)
VALUES (13, 1, 0, 'admin-sms-login', '后台用户短信登录', '您的验证码是{code}', '[\"code\"]', '', '4372216', 1, 'YUN_PIAN', '1',
        '2021-10-11 08:10:00', '1', '2021-10-11 08:10:00', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_social_user
-- ----------------------------
DROP TABLE IF EXISTS `system_social_user`;
CREATE TABLE `system_social_user`
(
    `id`             bigint unsigned                                                NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
    `type`           tinyint                                                        NOT NULL COMMENT '社交平台的类型',
    `openid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '社交 openid',
    `token`          varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '社交 token',
    `raw_token_info` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始 Token 数据，一般是 JSON 格式',
    `nickname`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '用户昵称',
    `avatar`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '用户头像',
    `raw_user_info`  varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始用户数据，一般是 JSON 格式',
    `code`           varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '最后一次的认证 code',
    `state`          varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '最后一次的认证 state',
    `creator`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '创建者',
    `create_time`    datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time`    datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`      bigint                                                         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='社交用户表';

-- ----------------------------
-- Records of system_social_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_social_user_bind
-- ----------------------------
DROP TABLE IF EXISTS `system_social_user_bind`;
CREATE TABLE `system_social_user_bind`
(
    `id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
    `user_id`        bigint          NOT NULL COMMENT '用户编号',
    `user_type`      tinyint         NOT NULL COMMENT '用户类型',
    `social_type`    tinyint         NOT NULL COMMENT '社交平台的类型',
    `social_user_id` bigint          NOT NULL COMMENT '社交用户的编号',
    `creator`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time`    datetime        NOT NULL                                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time`    datetime        NOT NULL                                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)          NOT NULL                                     DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`      bigint          NOT NULL                                     DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='社交绑定表';

-- ----------------------------
-- Records of system_social_user_bind
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_tenant
-- ----------------------------
DROP TABLE IF EXISTS `system_tenant`;
CREATE TABLE `system_tenant`
(
    `id`              bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '租户编号',
    `name`            varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户名',
    `contact_user_id` bigint                                                                DEFAULT NULL COMMENT '联系人的用户编号',
    `contact_name`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系人',
    `contact_mobile`  varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '联系手机',
    `status`          tinyint                                                      NOT NULL DEFAULT '0' COMMENT '租户状态（0正常 1停用）',
    `domain`          varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         DEFAULT '' COMMENT '绑定域名',
    `package_id`      bigint                                                       NOT NULL COMMENT '租户套餐编号',
    `expire_time`     datetime                                                     NOT NULL COMMENT '过期时间',
    `account_count`   int                                                          NOT NULL COMMENT '账号数量',
    `creator`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
    `create_time`     datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '更新者',
    `update_time`     datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 125
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='租户表';

-- ----------------------------
-- Records of system_tenant
-- ----------------------------
BEGIN;
INSERT INTO `system_tenant` (`id`, `name`, `contact_user_id`, `contact_name`, `contact_mobile`, `status`, `domain`,
                             `package_id`, `expire_time`, `account_count`, `creator`, `create_time`, `updater`,
                             `update_time`, `deleted`)
VALUES (1, '芋道源码', NULL, '芋艿', '17321315478', 0, 'https://www.iocoder.cn', 0, '2099-02-19 17:14:16', 9999, '1',
        '2021-01-05 17:03:47', '1', '2022-02-23 12:15:11', b'0');
INSERT INTO `system_tenant` (`id`, `name`, `contact_user_id`, `contact_name`, `contact_mobile`, `status`, `domain`,
                             `package_id`, `expire_time`, `account_count`, `creator`, `create_time`, `updater`,
                             `update_time`, `deleted`)
VALUES (121, '小租户', 110, '小王2', '15601691300', 0, 'http://www.iocoder.cn', 111, '2024-03-11 00:00:00', 20, '1',
        '2022-02-22 00:56:14', '1', '2022-05-17 10:03:59', b'0');
INSERT INTO `system_tenant` (`id`, `name`, `contact_user_id`, `contact_name`, `contact_mobile`, `status`, `domain`,
                             `package_id`, `expire_time`, `account_count`, `creator`, `create_time`, `updater`,
                             `update_time`, `deleted`)
VALUES (122, '测试租户', 113, '芋道', '15601691300', 0, 'https://www.iocoder.cn', 111, '2022-04-30 00:00:00', 50, '1',
        '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS `system_tenant_package`;
CREATE TABLE `system_tenant_package`
(
    `id`          bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '套餐编号',
    `name`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '套餐名',
    `status`      tinyint                                                        NOT NULL DEFAULT '0' COMMENT '租户状态（0正常 1停用）',
    `remark`      varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '备注',
    `menu_ids`    varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联的菜单编号',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci            DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 112
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='租户套餐表';

-- ----------------------------
-- Records of system_tenant_package
-- ----------------------------
BEGIN;
INSERT INTO `system_tenant_package` (`id`, `name`, `status`, `remark`, `menu_ids`, `creator`, `create_time`, `updater`,
                                     `update_time`, `deleted`)
VALUES (111, '普通套餐', 0, '小功能', '[1024,1025,1,102,103,104,1013,1014,1015,1016,1017,1018,1019,1020,1021,1022,1023]', '1',
        '2022-02-22 00:54:00', '1', '2022-03-19 18:39:13', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_user_post
-- ----------------------------
DROP TABLE IF EXISTS `system_user_post`;
CREATE TABLE `system_user_post`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`     bigint   NOT NULL                                            DEFAULT '0' COMMENT '用户ID',
    `post_id`     bigint   NOT NULL                                            DEFAULT '0' COMMENT '岗位ID',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)   NOT NULL                                            DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint   NOT NULL                                            DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 116
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户岗位表';

-- ----------------------------
-- Records of system_user_post
-- ----------------------------
BEGIN;
INSERT INTO `system_user_post` (`id`, `user_id`, `post_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (112, 1, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0', 1);
INSERT INTO `system_user_post` (`id`, `user_id`, `post_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (113, 100, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0', 1);
INSERT INTO `system_user_post` (`id`, `user_id`, `post_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (114, 114, 3, 'admin', '2022-05-02 07:25:24', 'admin', '2022-06-30 02:12:15', b'1', 1);
INSERT INTO `system_user_post` (`id`, `user_id`, `post_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (115, 104, 1, '1', '2022-05-16 19:36:28', '1', '2022-05-16 19:36:28', b'0', 1);
COMMIT;

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '自增编号',
    `user_id`     bigint NOT NULL COMMENT '用户ID',
    `role_id`     bigint NOT NULL COMMENT '角色ID',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                       DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint NOT NULL                                              DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户和角色关联表';

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
BEGIN;
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (1, 1, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:17', b'0', 1);
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (5, 100, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:12', b'0', 1);
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (6, 100, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:11', b'0', 1);
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`,
                                `deleted`, `tenant_id`)
VALUES (18, 1, 2, '1', '2022-05-12 20:39:29', '1', '2022-05-12 20:39:29', b'0', 1);
COMMIT;

-- ----------------------------
-- Table structure for system_user_session
-- ----------------------------
DROP TABLE IF EXISTS `system_user_session`;
CREATE TABLE `system_user_session`
(
    `id`              bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `token`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '会话编号',
    `user_id`         bigint                                                        NOT NULL COMMENT '用户编号',
    `user_type`       tinyint                                                       NOT NULL DEFAULT '0' COMMENT '用户类型',
    `session_timeout` datetime                                                      NOT NULL COMMENT '会话超时时间',
    `username`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户账号',
    `user_ip`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户 IP',
    `user_agent`      varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
    `creator`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`     datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`     datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`       bigint                                                        NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 17
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户在线 Session';

-- ----------------------------
-- Records of system_user_session
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_users
-- ----------------------------
DROP TABLE IF EXISTS `system_users`;
CREATE TABLE `system_users`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户账号',
    `password`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
    `nickname`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户昵称',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '备注',
    `dept_id`     bigint                                                                 DEFAULT NULL COMMENT '部门ID',
    `post_ids`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '岗位编号数组',
    `email`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '用户邮箱',
    `mobile`      varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '手机号码',
    `sex`         tinyint                                                                DEFAULT '0' COMMENT '用户性别',
    `avatar`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '头像地址',
    `status`      tinyint                                                       NOT NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `login_ip`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '最后登录IP',
    `login_date`  datetime                                                               DEFAULT NULL COMMENT '最后登录时间',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint                                                        NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_username` (`username`, `update_time`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 117
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户信息表';

-- ----------------------------
-- Records of system_users
-- ----------------------------
BEGIN;
INSERT INTO `system_users` (`id`, `username`, `password`, `nickname`, `remark`, `dept_id`, `post_ids`, `email`,
                            `mobile`, `sex`, `avatar`, `status`, `login_ip`, `login_date`, `creator`, `create_time`,
                            `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (1, 'admin', '$2a$10$0acJOIk2D25/oC87nyclE..0lzeu9DtQ/n3geP4fkun/zIVRhHJIO', 'KuggaAdmin', '管理员', 103, '[1]',
        'aoteman@126.com', '13975260095', 1, 'http://test.kugga.iocoder.cn/48934f2f-92d4-4250-b917-d10d2b262c6a', 0,
        '0:0:0:0:0:0:0:1', '2022-06-30 11:57:01', 'admin', '2021-01-05 17:03:47', '1', '2022-06-30 11:57:25', b'0', 1);
INSERT INTO `system_users` (`id`, `username`, `password`, `nickname`, `remark`, `dept_id`, `post_ids`, `email`,
                            `mobile`, `sex`, `avatar`, `status`, `login_ip`, `login_date`, `creator`, `create_time`,
                            `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (100, 'kugga', '$2a$10$11U48RhyJ5pSBYWSn12AD./ld671.ycSzJHbyrtpeoMeYiw31eo8a', '芋道', '不要吓我', 104, '[1]',
        'kugga@iocoder.cn', '15601691300', 1, '', 1, '127.0.0.1', '2022-05-22 19:35:33', '', '2021-01-07 09:07:17',
        NULL, '2022-05-22 19:35:33', b'0', 1);
INSERT INTO `system_users` (`id`, `username`, `password`, `nickname`, `remark`, `dept_id`, `post_ids`, `email`,
                            `mobile`, `sex`, `avatar`, `status`, `login_ip`, `login_date`, `creator`, `create_time`,
                            `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (104, 'test', '$2a$10$GP8zvqHB//TekuzYZSBYAuBQJiNq1.fxQVDYJ.uBCOnWCtDVKE4H6', '测试号', NULL, 107, '[1]',
        '111@qq.com', '15601691200', 1, '', 0, '127.0.0.1', '2022-05-28 15:43:17', '', '2021-01-21 02:13:53', NULL,
        '2022-05-28 15:43:17', b'0', 1);
INSERT INTO `system_users` (`id`, `username`, `password`, `nickname`, `remark`, `dept_id`, `post_ids`, `email`,
                            `mobile`, `sex`, `avatar`, `status`, `login_ip`, `login_date`, `creator`, `create_time`,
                            `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES (107, 'admin107', '$2a$10$dYOOBKMO93v/.ReCqzyFg.o67Tqk.bbc2bhrpyBGkIw9aypCtr2pm', '芋艿', NULL, NULL, NULL, '',
        '15601691300', 0, '', 0, '', NULL, '1', '2022-02-20 22:59:33', '1', '2022-02-27 08:26:51', b'0', 118);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
