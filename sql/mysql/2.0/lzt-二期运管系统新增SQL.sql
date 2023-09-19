-- 刘宗堂新增sql
-- 角色 菜单配置
DROP TABLE IF EXISTS `duke_serial_password`;
create table duke_serial_password
(
    id              bigint auto_increment comment 'id'
        primary key,
    sort_id         int                                   null comment '推荐人编号',
    group_name      varchar(32)                           null comment '密码组编号',
    username        varchar(30)                           null comment '用户账号',
    user_id         bigint                                null comment '用户id',
    serial_password varchar(64)                           null comment '序列密码',
    interval_time   bigint                                null comment '密码输入间隔时间 分',
    effective_time  bigint                                null comment '授权生效时间 分',
    input_flag      varchar(2)                            null comment '0-未输入、1-已输入',
    input_time      datetime                              null comment '输入密码时间',
    creator         varchar(64) default ''                null comment '创建者',
    create_time     datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater         varchar(64) default ''                null comment '更新者',
    update_time     datetime    default CURRENT_TIMESTAMP not null comment '更新时间',
    deleted         bit         default b'0'              not null comment '是否删除'
)
    comment '序列密码信息表' collate = utf8mb4_unicode_ci;

create index duke_serial_password_ni1 on duke_serial_password (group_name);

INSERT INTO duke_serial_password (user_id, username, input_flag, sort_id, group_name, serial_password, interval_time,
                                  effective_time, input_time, creator, create_time, updater, update_time, deleted)
VALUES (117, 'operation001', 0, 1, 'create_recommendation_log', 'duke123123', 480, 1440, null, DEFAULT, DEFAULT,
        DEFAULT, DEFAULT, DEFAULT);

INSERT INTO duke_serial_password (user_id, username, input_flag, sort_id, group_name, serial_password, interval_time,
                                  effective_time, input_time, creator, create_time, updater, update_time, deleted)
VALUES (118, 'operation002', 0, 2, 'create_recommendation_log', 'duke123', 480, 1440, null, DEFAULT, DEFAULT, DEFAULT,
        DEFAULT, DEFAULT);

INSERT INTO system_users (id, username, password, nickname, remark, dept_id, post_ids, email, mobile, sex, avatar,
                          status, login_ip, login_date, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (117, 'operation001', '$2a$10$T7Dr96/CCAQ40tTT1Rbd..eezvqOzrI9kfK8kyfRtTNk67iU8ZBJq', '运营账号1', null, 110, '[]',
        '', '', 0, '', 0, '0:0:0:0:0:0:0:1', '2022-09-20 15:46:59', '1', '2022-09-15 10:00:07', null,
        '2022-09-20 15:46:59', false, 0);
INSERT INTO system_users (id, username, password, nickname, remark, dept_id, post_ids, email, mobile, sex, avatar,
                          status, login_ip, login_date, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (118, 'operation002', '$2a$10$AM50IzgYvJ5fT2dKe6EPRuR14NVWmcTzhZQYuxLZVthKOAeggEGzi', '运营账号2', null, null, '[]',
        '', '', 0, '', 0, '0:0:0:0:0:0:0:1', '2022-09-20 15:47:25', '1', '2022-09-15 10:04:29', null,
        '2022-09-20 15:47:25', false, 0);

INSERT INTO system_role (id, name, code, sort, data_scope, data_scope_dept_ids, status, type, remark, creator,
                         create_time, updater, update_time, deleted, tenant_id)
VALUES (114, '运营人员', 'operator', 3, 1, '', 0, 2, null, '1', '2022-08-31 10:27:45', '1', '2022-08-31 10:27:45', false,
        0);
INSERT INTO system_role (id, name, code, sort, data_scope, data_scope_dept_ids, status, type, remark, creator,
                         create_time, updater, update_time, deleted, tenant_id)
VALUES (115, '推荐信查询授权', 'operation_admin', 1, 1, '', 0, 2, null, '1', '2022-09-15 10:01:37', '1', '2022-09-15 10:01:37',
        false, 0);

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2030, '管理后台', '', 1, 1, 0, '/operation', 'chart', null, 0, true, true, '1', '2022-08-31 15:20:29', '1',
        '2022-09-08 10:27:56', true);
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2031, '首页推荐管理', '', 2, 1, 2032, 'recommend', 'edit', 'operation/recommend/index.vue', 0, true, true, '1',
        '2022-09-01 09:31:04', '1', '2022-09-08 10:28:57', false);
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2032, '首页配置', '', 1, 2, 0, '/homepage', 'edit', null, 0, true, true, '1', '2022-09-07 13:44:45', '1',
        '2022-09-08 10:27:41', false);
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2033, '公会查询展示', '', 2, 3, 2034, 'leaguedisplay', 'edit', 'operation/leaguedisplay/index.vue', 0, true, true,
        '1', '2022-09-08 10:26:19', '1', '2022-09-08 13:31:13', false);
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2034, '公会配置', '', 1, 2, 0, '/league', '#', null, 0, true, true, '1', '2022-09-08 13:30:46', '1',
        '2022-09-08 13:30:46', false);
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2035, '推荐信查询', '', 1, 3, 0, '/recommendation', '#', null, 0, true, true, '1', '2022-09-13 18:00:00', '1',
        '2022-09-13 18:00:00', false);
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2036, '用户推荐报告编写记录查询', '', 2, 1, 2035, 'createlog', 'build', 'operation/recommendation/index.vue', 0, true, true,
        '1', '2022-09-13 18:10:54', '1', '2022-09-15 10:43:51', false);
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2037, '查询授权', 'duke:recommendation:authSelect', 3, 1, 2036, '', '', '', 0, true, true, '1',
        '2022-09-15 09:57:38', '1', '2022-09-15 09:57:38', false);
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2038, '推荐信编写记录查询', 'duke:recommendation:select', 3, 2, 2036, '', '', '', 0, true, true, '1',
        '2022-09-15 10:06:49', '1', '2022-09-15 10:13:20', false);
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2039, '订单查询', '', 1, 2, 0, '/order', 'pay', null, 0, true, true, '1', '2022-09-19 09:38:59', '1',
        '2022-09-19 09:38:59', false);
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2040, '交易订单查询', '', 2, 1, 2039, 'selectOrder', 'search', 'operation/payOrder/index.vue', 0, true, true, '1',
        '2022-09-19 09:43:25', '1', '2022-09-19 09:43:47', false);

INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 2032, '1', '2022-09-15 10:02:47', '1', '2022-09-15 10:02:47', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 2033, '1', '2022-09-15 10:02:47', '1', '2022-09-15 10:02:47', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 2034, '1', '2022-09-15 10:02:47', '1', '2022-09-15 10:02:47', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 2035, '1', '2022-09-15 10:02:47', '1', '2022-09-15 10:02:47', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 2036, '1', '2022-09-15 10:02:47', '1', '2022-09-15 10:02:47', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 2037, '1', '2022-09-15 10:02:47', '1', '2022-09-15 10:02:47', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 2031, '1', '2022-09-15 10:02:47', '1', '2022-09-15 10:02:47', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (114, 2032, '1', '2022-09-15 10:05:04', '1', '2022-09-15 10:05:04', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (114, 2033, '1', '2022-09-15 10:05:04', '1', '2022-09-15 10:05:04', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (114, 2034, '1', '2022-09-15 10:05:04', '1', '2022-09-15 10:05:04', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (114, 2031, '1', '2022-09-15 10:05:04', '1', '2022-09-15 10:05:04', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (114, 2035, '1', '2022-09-15 10:07:29', '1', '2022-09-15 10:07:29', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (114, 2036, '1', '2022-09-15 10:07:29', '1', '2022-09-15 10:07:29', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (114, 2038, '1', '2022-09-15 10:07:29', '1', '2022-09-15 10:07:29', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 101, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 102, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1063, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1064, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1065, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1008, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1009, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1010, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1011, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1012, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1013, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1014, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1015, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1016, '1', '2022-09-15 10:44:45', '1', '2022-09-15 10:44:45', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 2039, '1', '2022-09-21 10:53:56', '1', '2022-09-21 10:53:56', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 2040, '1', '2022-09-21 10:53:56', '1', '2022-09-21 10:53:56', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (114, 2039, '1', '2022-09-21 10:54:21', '1', '2022-09-21 10:54:21', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (114, 2040, '1', '2022-09-21 10:54:21', '1', '2022-09-21 10:54:21', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 100, '1', '2022-09-21 10:55:12', '1', '2022-09-21 10:55:12', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1001, '1', '2022-09-21 10:55:12', '1', '2022-09-21 10:55:12', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1002, '1', '2022-09-21 10:55:12', '1', '2022-09-21 10:55:12', false, 0);
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (115, 1003, '1', '2022-09-21 10:55:12', '1', '2022-09-21 10:55:12', false, 0);

INSERT INTO system_user_role (id, user_id, role_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (21, 117, 115, '1', '2022-09-15 10:03:08', '1', '2022-09-15 10:03:08', false, 0);
INSERT INTO system_user_role (id, user_id, role_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (22, 118, 114, '1', '2022-09-15 10:04:42', '1', '2022-09-15 10:04:42', false, 0);
INSERT INTO system_user_role (id, user_id, role_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (23, 117, 114, '1', '2022-09-16 10:15:33', '1', '2022-09-16 10:15:33', false, 0);
INSERT INTO system_user_role (id, user_id, role_id, creator, create_time, updater, update_time, deleted, tenant_id)
VALUES (24, 118, 115, '1', '2022-09-16 10:15:39', '1', '2022-09-16 10:15:39', false, 0);
