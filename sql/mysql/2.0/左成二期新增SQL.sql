-- noinspection SqlNoDataSourceInspectionForFile

-- noinspection SqlDialectInspectionForFile

create table duke_forum_plate
(
    id           bigint auto_increment comment '板块id'
        primary key,
    plate_value  varchar(24)                 not null comment '板块值',
    language     varchar(10) default 'zh-CN' not null comment '语言',
    plate_name   varchar(512)                not null comment '板块名称',
    plate_desc   longtext                    null comment '板块简介',
    plate_avatar varchar(512)                null comment '板块头像',
    anon_flag    bit         default b'0'    not null comment '匿名标识 true: 是 false: 否',
    create_time  datetime                    not null comment '创建时间',
    update_time  datetime                    not null comment '修改时间',
    creator      varchar(64)                 null comment '创建者',
    updater      varchar(64)                 null comment '修改者',
    rmk          varchar(256)                null comment '备注信息',
    deleted      bit         default b'0'    not null comment '删除状态 true:已删除 false:未删除'
)
    comment '论坛板块表';

create table duke_posts
(
    id                bigint auto_increment comment '贴子id'
        primary key,
    msg_id            varchar(64)      not null comment '信息编号',
    sort_id           int default 99   null comment '排序编号(用于自定义排序规则)',
    user_id           bigint           not null comment '发贴人',
    group_id          bigint           null comment '贴子所属组(目前只支持所属公会,即此id关联公会id)',
    plate             varchar(32)      not null comment '板块 0:公会 根据 duke_forum_plate 表关联',
    posts_title       varchar(256)     not null comment '贴子标题',
    user_ip           varchar(64)      null comment '发贴人ip',
    create_time       datetime         not null comment '创建时间',
    update_time       datetime         not null comment '修改时间',
    new_reply_user_id bigint           null comment '最新回复人',
    reply_time        datetime         not null comment '最新回复时间',
    praise_num        int default 0    null comment '点赞人数',
    trample_num       int default 0    null comment '点踩人数',
    click_num         int default 0    null comment '点击次数',
    collect_num       int default 0    null comment '收藏数',
    share_num         int default 0    null comment '分享数',
    hot_num           int default 0    null comment '热度',
    comment_num       int default 0    null comment '热度',
    floor_count       int default 0    null comment '楼层计数(直接回复数)',
    hot_search_switch bit default b'1' not null comment '是否允许被热贴检索  false:不允许 true:允许',
    updater           varchar(64)      null comment '更新者(管理员处理贴子时存在值)',
    rmk               varchar(256)     null comment '备注信息',
    deleted           bit default b'0' not null comment '删除状态 true:已删除 false:未删除',
    constraint msg_id
        unique (msg_id)
)
    comment '贴子表';

-- 增加索引
alter table duke_posts
    add unique (msg_id);

create table duke_posts_content
(
    id            bigint auto_increment comment 'id'
        primary key,
    posts_id      bigint           null comment '贴子id',
    content       json             null comment '贴子一段内容',
    original_text text             null comment '原始文本--方便做搜索',
    create_time   datetime         null comment '创建时间',
    update_time   datetime         null comment '更新时间',
    deleted       bit default b'0' null comment '是否删除'
)
    comment '贴子内容表';

create table duke_posts_floor
(
    id            bigint auto_increment comment '楼层ID'
        primary key,
    msg_id        varchar(64)      not null comment '信息编号',
    posts_id      bigint           not null comment '贴子ID 与duke_posts id一致',
    floor_num     int              not null comment '楼层数',
    user_id       bigint           not null comment '评论者id',
    user_ip       varchar(64)      null comment '回复人ip',
    landlord_flag bit default b'0' not null comment '是否为楼主回复 true:是 false:否',
    create_time   datetime         not null comment '创建时间',
    update_time   datetime         not null comment '修改时间',
    praise_num    int default 0    null comment '点赞人数',
    trample_num   int default 0    null comment '点踩人数',
    hot_num       int default 0    null comment '热度',
    rmk           varchar(256)     null comment '备注信息',
    updater       varchar(64)      null comment '更新者(管理员处理贴子时存在值)',
    deleted       bit default b'0' not null comment '删除状态 true:已删除 false:未删除'
)
    comment '贴子回复楼层表';

-- 增加索引
alter table duke_posts_floor
    add unique (msg_id);

create table duke_posts_floor_content
(
    id            bigint auto_increment comment 'id'
        primary key,
    floor_id      bigint           null comment '楼层id',
    content       json             null comment '楼层的一段内容',
    original_text text             null comment '原始文本--方便做搜索',
    create_time   datetime         null comment '创建时间',
    update_time   datetime         null comment '更新时间',
    deleted       bit default b'0' null comment '是否删除'
)
    comment '楼层内容';

create table duke_posts_comment
(
    id              bigint auto_increment comment '评论ID'
        primary key,
    msg_id          varchar(64)      not null comment '信息编号',
    msg_type        varchar(4)       not null comment '消息类型 1:回复楼层信息,2:回复指定信息',
    posts_id        bigint           not null comment '贴子ID 与表duke_posts id匹配',
    floor_id        bigint           not null comment '楼层ID 与duke_posts_floor id匹配',
    receive_id      bigint           not null comment '被回复信息的ID(comment_type为0时 存duke_posts_floor表的id,为1时存duke_posts_comment的id)',
    user_id         bigint           not null comment '评论者id',
    landlord_flag   bit default b'0' not null comment '是否为楼主回复 true:是 false:否',
    content         longtext         not null comment '评论内容',
    user_ip         varchar(64)      null comment '评论人ip',
    receive_user_id bigint           not null comment '被评论人ID',
    create_time     datetime         not null comment '创建时间',
    update_time     datetime         not null comment '修改时间',
    praise_num      int default 0    null comment '点赞人数',
    trample_num     int default 0    null comment '点踩人数',
    rmk             varchar(256)     null comment '备注信息',
    updater         varchar(64)      null comment '更新者(管理员处理贴子时存在值)',
    deleted         bit default b'0' not null comment '删除状态 true:已删除 false:未删除'
)
    comment '贴子讨论表';

-- 增加索引
alter table duke_posts_comment
    add unique (msg_id);

create table duke_league_label
(
    id                    bigint auto_increment comment '公会ID'
        primary key,
    sort_id               int         default 99      null comment '排序编号(用于自定义排序规则)',
    display_flag          bit         default b'0'    not null comment '显示标识 true:显示 false:隐藏',
    label_value           varchar(10)                 not null comment '标签值',
    language              varchar(10) default 'zh-CN' not null comment '语言',
    label_name            varchar(512)                not null comment '标签名',
    label_background      varchar(512)                null comment '标签背图',
    label_linear_gradient varchar(512)                null comment '背景渐变样式',
    label_desc            longtext                    not null comment '标签描述',
    create_time           datetime                    not null comment '创建时间',
    update_time           datetime                    not null comment '更新时间',
    creator               varchar(64)                 null comment '创建者',
    updater               varchar(64)                 null comment '更新者',
    deleted               bit         default b'0'    not null comment '删除状态 true:已删除 false:未删除'
)
    comment '公会标签配枚举配置表';

create table duke_forum_message
(
    id               bigint auto_increment comment 'id'
        primary key,
    user_id          bigint           not null comment '消息所属人',
    posts_id         bigint           not null comment '原贴id',
    floor_id         bigint           not null comment '楼层id',
    reply_id         bigint           not null comment '回复的消息ID',
    reply_msg_id     varchar(64)      not null comment '回复的信息ID',
    reply_user_id    bigint           not null comment '回复信息用户ID',
    receive_id       bigint           not null comment '被回复的消息ID',
    receive_msg_type varchar(4)       not null comment '回复类型 0:回复的贴子,1:回复楼层信息,2:回复指定信息',
    create_time      datetime         not null comment '创建时间',
    update_time      datetime         not null comment '创建时间',
    read_flag        bit default b'0' not null comment '已读未读标识 true:已读 false:未读',
    deleted          bit default b'0' not null comment '删除状态 true:已删除 false:未删除'
)
    comment '论坛消息表';
-- 增加索引
alter table duke_forum_message
    add index d_fm_1 (user_id);


create table duke_posts_rise_count
(
    id          bigint auto_increment comment 'id'
        primary key,
    posts_id    bigint          not null comment '贴子ID',
    num         int default '0' not null comment '计数量',
    create_time datetime        not null comment '创建时间'
)
    comment '贴子上升计数表';


create table duke_task_create_league
(
    id              bigint auto_increment comment 'id'
        primary key,
    user_id         bigint                                   null comment '用户id',
    league_id       bigint                                   null comment '公会id',
    business_status int(2)         default 0                 null comment '业务状态 0初始化、1已完成、2已拒绝、3已过期',
    app_order_no    varchar(32)                              null comment '订单号',
    amount          decimal(15, 2) default 0.00              null comment '金额',
    pay_status      int(2)                                   null comment '支付状态 0未支付 1已支付',
    amount_status   int(2)         default 0                 null comment '金额状态 0默认值 1分账中 2已分账 3已退款',
    ledger_user_id  bigint                                   null comment '被分账的用户',
    ledger_time     datetime                                 null comment '分账时间',
    pay_flag        bit            default b'0'              null comment '是否付费 1付费，0免费',
    create_time     datetime       default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime       default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted         bit            default b'0'              not null comment '是否删除'
)
    comment '公会创建订单表';

create index idx_order_no
    on duke_task_create_league (app_order_no);

-- 公会表与待创建公会表新增公会名唯一索引
create unique index uk_league1 on duke_league (league_name);
create unique index uk_league_un1 on duke_league_unfinished (league_name);
-- 公会规则表新增字段
alter table duke_league_rule
    add posts_search_switch bit default b'1' not null comment '是否允许热贴检索开关  false:不允许 true:允许' after enabled_user_join;
-- 公会待创建表新增字段
alter table duke_league_unfinished
    add deleted bit default b'0' not null comment '删除状态 true:已删除 false:未删除';
alter table duke_league_unfinished
    add pre_status bit default b'0' not null comment '预创建状态 false:未完成 true:已完成';

alter table duke_league_unfinished
    add league_label varchar(32) default '0' not null comment '公会标签 0:无标签 牛人、熟人、同行、公会 ' after id;

alter table duke_league
    add league_label varchar(32) default '0' not null comment '公会标签 0:无标签 牛人、熟人、同行、公会 ' after id;

-- 配置精选公会
INSERT INTO duke_system_params (id, type, param_key, value, description)
VALUES (8, 'league', 'selected_league', '76,77,78,79,80,81', '精选公会');

-- 配置公会标签信息
INSERT INTO duke_league_label (id, sort_id, display_flag, label_value, language, label_name, label_background,
                               label_linear_gradient, label_desc, create_time, update_time, creator, updater, deleted)
VALUES (1, 99, true, '1', 'en-US', 'Influencers',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F08%2F20210908214758_3d481.jpg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665802521&t=f6bc2acfd8bae901493f63a4672ef6e7',
        'linear-gradient(180deg, rgba(171, 138, 113, 0) 0%, #AB8A71 20.83%, rgba(171, 138, 113, 0) 100%)',
        'Hear what industry-excellences say,earn your perks through info-sharing guild engagement.',
        '2022-09-07 14:42:41', '2022-09-07 14:42:43', null, null, false);
INSERT INTO duke_league_label (id, sort_id, display_flag, label_value, language, label_name, label_background,
                               label_linear_gradient, label_desc, create_time, update_time, creator, updater, deleted)
VALUES (2, 99, true, '2', 'en-US', 'Peers',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2Fceb15bd590493d0d2c2788c2341f95d4f62b580d.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665802580&t=2db76e1ca7c9401787be701389c6fd7e',
        'linear-gradient(180deg, rgba(171, 138, 113, 0) 0%, #AB8A71 20.83%, rgba(171, 138, 113, 0) 100%)',
        'Exchanging skills, experience and ideas with peers, become the possible candidate through 1-to-1 private chat.',
        '2022-09-07 14:42:41', '2022-09-07 14:42:43', null, null, false);
INSERT INTO duke_league_label (id, sort_id, display_flag, label_value, language, label_name, label_background,
                               label_linear_gradient, label_desc, create_time, update_time, creator, updater, deleted)
VALUES (3, 99, true, '3', 'en-US', 'Interests',
        'https://ts1.cn.mm.bing.net/th/id/R-C.d2d9a59e046cac8cb69b707e4351fc86?rik=I1v1gO438B4wAA&riu=http%3a%2f%2fn.sinaimg.cn%2fsinakd20210302ac%2f685%2fw1452h833%2f20210302%2f47fd-kksmnwv5358539.jpg&ehk=ETM%2fNqP%2bktJk3FqxushzlNVLQn240y%2bMs1fd1PBCWRk%3d&risl=&pid=ImgRaw&r=0',
        'linear-gradient(180deg, rgba(205, 135, 82, 0) 0%, #CD8752 20.83%, rgba(205, 135, 82, 0) 100%)',
        'Co-create a guild where friends or hobbies only,on one another, let recommendation letters happen.',
        '2022-09-07 14:42:41', '2022-09-07 14:42:43', null, null, false);
INSERT INTO duke_league_label (id, sort_id, display_flag, label_value, language, label_name, label_background,
                               label_linear_gradient, label_desc, create_time, update_time, creator, updater, deleted)
VALUES (4, 99, true, '4', 'en-US', 'Recruitment',
        'https://pic4.zhimg.com/v2-ed5bf44c8d4a6ab9783d52906e13e715_r.jpg?source=172ae18b',
        'linear-gradient(180deg, rgba(205, 135, 82, 0) 0%, #CD8752 20.83%, rgba(205, 135, 82, 0) 100%)',
        'Latest recruitments and Q&A from HR，join the guild to meet your future co-workers and peers here.',
        '2022-09-07 14:42:41', '2022-09-07 14:42:43', null, null, false);
INSERT INTO duke_league_label (id, sort_id, display_flag, label_value, language, label_name, label_background,
                               label_linear_gradient, label_desc, create_time, update_time, creator, updater, deleted)
VALUES (5, 1, true, '5', 'en-US', 'Popular',
        'https://upload-bbs.mihoyo.com/upload/2021/04/25/221596515/f55f78f4177854d2b6617fb6ad5a371b_8478508595496042966.png',
        'linear-gradient(180deg, rgba(205, 135, 82, 0) 0%, #CD8752 20.83%, rgba(205, 135, 82, 0) 100%)',
        'Accelerate your career,earn connections and rewards in guilds created by talents all over the world.',
        '2022-09-09 14:34:01', '2022-09-09 14:34:03', null, null, false);

-- 配置论坛板块信息
INSERT INTO duke_forum_plate (id, plate_value, language, plate_name, plate_desc, plate_avatar, anon_flag, create_time,
                              update_time, creator, updater, rmk, deleted)
VALUES (2, '1', 'en-US', 'Public', 'On the sly. No guns',
        'https://www.hisunpay66.com:8020/public/avatar/league/e05ebd93369ce542/20220909074359/8db5d10b-ef8b-488c-b681-391c935d8e88.jpg',
        true, '2022-09-09 13:39:31', '2022-09-09 13:39:33', null, null, null, false);
