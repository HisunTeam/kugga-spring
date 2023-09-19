-- dev 表修改
-- 9.9
alter table duke_message_template
    add code varchar(8) null comment '消息code' after id;
alter table duke_message_template
    change code message_code varchar(8) null comment '消息code';
alter table duke_message
    add message_code varchar(8) null comment '消息code';

-- 9.14
alter table duke_league_rule
    modify enabled_user_join bit default b'0' null comment '是否允许用户加入 true:是 false:否' after posts_search_switch;

alter table duke_league_rule
    add enabled_admin_approval bit default b'0' null comment '主动加入公会是否需要管理员审核 true:是 false:否' after enabled_user_join;



create table duke_league_join
(
    id              bigint                                   not null auto_increment comment 'id',
    user_id         bigint                                   null comment '用户id',
    league_id       bigint                                   null comment '所加入公会id',
    business_status int(2)                                   null default 0 comment '业务状态 0初始化、1已同意、2已拒绝、3已过期',
    app_order_no    varchar(32)                              null comment '订单号',
    amount          decimal(15, 2) default '0.00' comment '金额',
    pay_status      int(2)                                   null comment '支付状态 0未支付 1已支付',
    amount_status   int(2)         default 0 comment '金额状态 0默认值 2已分账 3已退款',
    expire_time     datetime                                 null comment '过期时间',
    join_reason     varchar(1024)                            null comment '加入理由',
    creator         varchar(64)    default ''                null comment '创建者',
    create_time     datetime       default CURRENT_TIMESTAMP not null comment '创建时间',
    updater         varchar(64)    default ''                null comment '更新者',
    update_time     datetime       default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted         bit            default b'0'              not null comment '是否删除',
    constraint duke_league_join_pk primary key (id)
)
    comment '公会加入表';

alter table duke_league_join
    add league_create_id bigint null comment '公会创建者id' after join_reason;
alter table duke_league_join
    add enabled_admin_approval bit default b'0' null comment '是否需要审批' after league_create_id;
alter table duke_league_join
    add pay_flag bit default b'0' null comment '是否付费 1付费，0免费' after enabled_admin_approval;
-- 过期时间定时任务扫描加索引
create index idx_expireTime on duke_league_join (expire_time);



create table duke_league_join_approval
(
    id          bigint                                not null auto_increment comment 'id',
    business_id bigint                                null comment '公会加入表id',
    user_id     bigint                                null comment '用户id',
    league_id   bigint                                null comment '所加入公会id',
    join_reason varchar(1024)                         null comment '加入理由',
    status      int(2)                                null default 0 comment '审批状态 0未审批、1已同意、2已拒绝、3已过期',
    creator     varchar(64) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(64) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit         default b'0'              not null comment '是否删除',
    constraint duke_league_join_pk primary key (id)
)
    comment '加入公会审批表';

alter table duke_league_join_approval
    add expire_time datetime null comment '过期时间';
alter table duke_league_join_approval
    modify expire_time datetime null comment '过期时间' after status;



alter table duke_league_account
    add account_id varchar(255) null comment '外部钱包账户编号' after league_id;
alter table duke_league_notice
    modify task_type int not null comment '任务类型  1 推荐报告 2 认证 3 聊天';


alter table duke_task_report
    modify status varchar(32) charset utf8 not null comment '状态 0未支付 1已支付 2已分账 3已退款';
alter table duke_task_report
    modify app_order_no varchar(64) null comment '内部订单号';
alter table duke_message_template
    add message_key varchar(124) null comment '消息key 唯一标识' after message_code;
alter table duke_message_template
    modify message_code varchar(32) null comment '消息code';
alter table duke_message
    change message_code message_key varchar(64) null comment '消息code' after id;


rename table duke_league_join to duke_task_league_join;
rename table duke_league_join_approval to duke_task_league_join_approval;
-- 新增表存在
-- drop table duke_league_join;
-- drop table duke_league_join_approval;


-- 9.21
alter table duke_task_league_join
    modify pay_status int(2) null comment '支付状态  1未支付,2已支付 ';

alter table duke_task_league_join
    modify pay_status int null comment '支付状态  0免费 1未支付,2已支付 ';

-- 9.22
drop index udx_index on duke_message_template;

create unique index udx_index
    on duke_message_template (message_key, message_scene, message_type, deleted, language);

-- 历史消息数据需要删除
update duke_message
set deleted = true
where create_time <= '2022-10-10';

-- 9.23
-- 任务表、公告栏表、订单表 status备注统一
alter table duke_task
    modify status int not null comment '任务状态 0待支付 1未接单 已支付、2已结单、3、已完成 任务完成、4带退款、5、已退款';
alter table duke_league_notice
    modify status int not null comment '状态 1未接单 2已接单 3已完成 4已失效';
alter table duke_task_league_auth
    modify status varchar(32) charset utf8 not null comment '状态 0默认值  1未支付 2已支付 3已分账 4待退款 5已退款';
alter table duke_task_report
    modify status varchar(32) charset utf8 not null comment '状态 0默认值  1未支付 2已支付 3已分账 4待退款 5已退款';
alter table duke_task_chat
    modify status varchar(32) charset utf8 not null comment '状态 0默认值  1未支付 2已支付 3已分账 4待退款 5已退款';
