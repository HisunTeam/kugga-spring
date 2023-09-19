create table duke_forum_district
(
    id            bigint auto_increment comment '贴子id'
        primary key,
    forum_id      bigint(64)   not null comment '论坛ID',
    district_name varchar(128) not null comment '分区名称',
    create_time   datetime     not null comment '创建时间',
    update_time   datetime     not null comment '修改时间'
)
    comment '论坛分区表';

create table duke_posts_image
(
    id          bigint auto_increment comment 'id'
        primary key,
    posts_id    bigint           null comment '贴子id',
    image_url   varchar(512)     null comment '图片地址',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间',
    deleted     bit default b'0' null comment '是否删除'
)
    comment '贴子图片存储表';

INSERT INTO duke_system_params (type, param_key, value, description)
VALUES ('forum', 'district_upper_limit', '8', '论坛分区上限值(只能整数)');
-- 贴子表新增分区字段
alter table duke_posts
    add district bigint default 0 comment '所属分区0默认无分区,其它关联duke_forum_district表的id' after plate;
