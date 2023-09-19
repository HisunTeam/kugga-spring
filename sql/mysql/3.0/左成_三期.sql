-- 论坛标签信息表
create table duke_forum_label
(
    id            bigint auto_increment comment '标签ID'
        primary key,
    label_name varchar(512) not null comment '标签名称',
    hot_num bigint default 0    not null comment '热度数',
    create_time   datetime  not null comment '创建时间'
)
    comment '论坛标签标签信息表';

-- 贴子与论坛标签关联表
create table duke_posts_label_relation
(
    id            bigint auto_increment comment 'id'
        primary key,
    posts_id bigint not null comment '贴子id',
    label_id bigint not null comment '标签id',
    create_time  datetime  not null comment '创建时间'
)
    comment '贴子与论坛标签关联表';

create table duke_posts_praise_record
(
    id  bigint auto_increment comment '标签ID'
        primary key,
    posts_id bigint not null comment '贴子ID',
    user_id bigint  not null comment '用户ID',
    create_time datetime  not null comment '创建时间'
)
    comment '贴子点赞记录';

create table duke_posts_trample_record
(
    id  bigint auto_increment comment '标签ID'
        primary key,
    posts_id bigint not null comment '贴子ID',
    user_id bigint  not null comment '用户ID',
    create_time datetime  not null comment '创建时间'
)
    comment '贴子点踩记录';

create table duke_posts_floor_praise_record
(
    id  bigint auto_increment comment '标签ID'
        primary key,
    posts_id bigint not null comment '贴子ID',
    floor_id bigint not null comment '楼层ID',
    user_id bigint  not null comment '用户ID',
    create_time datetime  not null comment '创建时间'
)
    comment '楼层点赞记录';

create table duke_posts_floor_trample_record
(
    id  bigint auto_increment comment '标签ID'
        primary key,
    posts_id bigint not null comment '贴子ID',
    floor_id bigint not null comment '楼层ID',
    user_id bigint  not null comment '用户ID',
    create_time datetime  not null comment '创建时间'
)
    comment '楼层点踩记录';


create table duke_posts_comment_praise_record
(
    id  bigint auto_increment comment '标签ID'
        primary key,
    posts_id bigint not null comment '贴子ID',
    comment_id bigint not null comment '讨论ID',
    user_id bigint  not null comment '用户ID',
    create_time datetime  not null comment '创建时间'
)
    comment '讨论点赞记录';

create table duke_posts_comment_trample_record
(
    id  bigint auto_increment comment '标签ID'
        primary key,
    posts_id bigint not null comment '贴子ID',
    comment_id bigint not null comment '讨论ID',
    user_id bigint  not null comment '用户ID',
    create_time datetime  not null comment '创建时间'
)
    comment '讨论点踩记录';

create table duke_posts_collection
(
    id          bigint auto_increment comment '收藏ID'
        primary key,
    posts_id    bigint   not null comment '被收藏的贴子ID',
    user_id     bigint   not null comment '用户ID',
    create_time datetime not null comment '创建时间'
)
    comment '贴子收藏表';

INSERT INTO duke_system_params (type, param_key, value, description)
VALUES ('forum', 'hot_label_list_num', '5', '热度标签列表展示数量(只能整数)');

INSERT INTO duke_system_params (type, param_key, value, description)
VALUES ('forum', 'hot_label_dimension', '7', '热度标签列表展示维度(单位:天,只能整数)');