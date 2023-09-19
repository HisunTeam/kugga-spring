-- 工会表新增工会头像
alter table duke_league_unfinished
    change league_icon league_avatar varchar(512) null comment '工会头像';
alter table duke_league
    change league_icon league_avatar varchar(512) null comment '工会头像';

-- 消息表
alter table duke_message
    add initiator_league_id bigint null comment '发起者公会id';
alter table duke_message
    add receiver_league_id bigint null comment '接收方公会id';
alter table duke_message
    modify initiator_league_id bigint null comment '发起者公会id' after message_param;
alter table duke_message
    modify receiver_league_id bigint null comment '接收方公会id' after initiator_league_id;
