DROP TABLE IF EXISTS `duke_league_growth_level`;
create table duke_league_growth_level
(
    id           bigint auto_increment comment 'id'
        primary key,
    league_id    bigint                             not null comment '公会id',
    growth_level int                                null comment '成长等级',
    level_name   varchar(128)                       null comment '等级名称',
    level_min    int                                null comment '该等级的最小值 [1,10]闭区间 0<=x<=10',
    level_max    int                                null comment '该等级的最大值 []区间',
    create_time  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP null comment '更新时间',
    updater      varchar(64)                        null comment '更新者',
    deleted      bit      default b'0'              not null comment '是否已删除 0未删除 1已删除'
)
    comment '公会成长等级' collate = utf8mb4_unicode_ci;

create index league_id_growth_level_inx
    on duke_league_growth_level (league_id, growth_level);


-- 简历经验类型上移
alter table duke_resume_experience modify type varchar(2) null comment '0-教育经历 1-工作经历' after resume_id;
alter table duke_resume add all_experience bit default b'0' null comment '是否完善所有经验(学籍、工作)';
