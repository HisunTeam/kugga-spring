-- ------------简历模板相关---------------------

-- 增加学位字段长度
BEGIN;
alter table duke_resume
    modify degree varchar(128) null comment '学历';
alter table duke_resume_experience
    modify degree varchar(128) null comment '学历';
COMMIT;
-- 20221014 ddl 修改手机号长度 增加original_text字段
alter table duke_resume
    modify phone_num varchar(20) null comment '电话号码';
alter table duke_resume
    add original_text longtext null comment '纯文本';


-- 个人简历表增加字段
alter table duke_resume
    add resume_name varchar(128) null comment '姓名';
alter table duke_resume
    add birthday datetime null comment '出生年月';
alter table duke_resume
    add `degree` varchar(32) null comment '学历';
alter table duke_resume
    add phone_num varchar(11) null comment '电话号码';
alter table duke_resume
    add email varchar(64) null comment '邮箱';
alter table duke_resume
    add skills text null comment '职业技能';
alter table duke_resume
    add resume_avatar varchar(500) null comment '简历头像';
alter table duke_resume
    add sex varchar(2) null comment '性别  男 MALE(1),女 FEMALE(2),未知 UNKNOWN(3)';
alter table duke_resume
    add hide varchar(2) null comment '是否隐藏';


alter table duke_resume
    change original_text introduce longtext null comment '自我介绍';

-- 个人简历 经历表
create table duke_resume_experience
(
    id            bigint auto_increment comment 'id'
        primary key,
    resume_id     bigint                                not null comment '所属简历id',
    begin_time    datetime                              null comment '开始时间',
    end_time      datetime                              null comment '结束时间',
    school        varchar(128)                          null comment '学校',
    degree        varchar(32)                           null comment '学历',
    major         varchar(128)                          null comment '主修',
    company       varchar(128)                          null comment '公司',
    position      varchar(128)                          null comment '职位',
    `description` text                                  null comment '经历描述',
    original_text text                                  null comment '原始文本--方便做搜索',
    type          varchar(2)                            null comment '0-教育经历 1-工作经历',
    creator       varchar(64) default ''                null comment '创建者',
    create_time   datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(64) default ''                null comment '更新者',
    update_time   datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted       bit         default b'0'              not null comment '是否删除'
) comment '个人简历经历表' collate = utf8mb4_unicode_ci;

create index duke_resume_experience_ni1
    on duke_resume_experience (resume_id);

create index duke_resume_experience_ni2
    on duke_resume_experience (id);

create index duke_resume_experience_ni3
    on duke_resume_experience (type);
-- --------------------------------------------------
