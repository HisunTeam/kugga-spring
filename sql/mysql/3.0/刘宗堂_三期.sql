DROP TABLE IF EXISTS `duke_favorite_group`;
create table duke_favorite_group
(
    id          bigint auto_increment comment '分组id'
        primary key,
    group_name  varchar(128)                          not null comment '分区名称',
    user_id     bigint                                not null comment '用户id',
    `type`        varchar(2)                            not null comment 'G-公会 T-推荐信',
    creator     varchar(64) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(64) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit         default b'0'              not null comment '是否删除'
) comment '收藏分组表' collate = utf8mb4_unicode_ci;

create index duke_favorite_group_ni1
    on duke_favorite_group (`type`);
create index duke_favorite_group_ni2
    on duke_favorite_group (user_id);


DROP TABLE IF EXISTS `duke_favorite_group_relation`;
create table duke_favorite_group_relation
(
    id          bigint auto_increment comment 'id'
        primary key,
    group_id    bigint                                not null comment '分组id',
    favorite_id bigint                                not null comment '收藏id',
    `type`        varchar(2)                            not null comment 'G-公会 T-推荐信',
    creator     varchar(64) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(64) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit         default b'0'              not null comment '是否删除'
) comment '收藏分组关联表' collate = utf8mb4_unicode_ci;

create index duke_group_relation_ni1
    on duke_favorite_group_relation (group_id);
create index duke_group_relation_ni2
    on duke_favorite_group_relation (favorite_id);
create index duke_group_relation_ni3
    on duke_favorite_group (`type`);

-- 增加分组数量上限参数配置
INSERT INTO duke_system_params (type, param_key, value, description)
VALUES ('group', 'group_upper_limit', '100', '收藏分组数量上限值(只能整数)');

-- 运管菜单配置
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                                  keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2044, '充值提现订单查询', '', 2, 2, 2039, 'chargeWithdraw', '#', 'operation/chargeWithdraw/index.vue', 0, true,
        true, '1', '2022-11-16 10:23:45', '1', '2022-11-16 10:24:23', false);

-- 菜单与运营角色关联
INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted,
                                       tenant_id)
VALUES (114, 2044, '1', '2022-11-16 14:13:08', '1', '2022-11-16 14:13:08', false, 0);



