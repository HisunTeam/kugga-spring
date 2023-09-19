-- 聊天室表
DROP TABLE IF EXISTS `chat_room`;
CREATE TABLE IF NOT EXISTS chat_room
(
    id              bigint UNSIGNED                      NOT NULL AUTO_INCREMENT PRIMARY KEY comment 'id',
    name            VARCHAR(60)                          NULL COMMENT '聊天室名字',
    description     VARCHAR(600)                         NULL COMMENT '聊天室描述',
    avatar          VARCHAR(256)                         NULL COMMENT '聊天室头像URL',
    room_type       TINYINT(1) UNSIGNED                  NOT NULL COMMENT '聊天室的类型 1: 群聊，0：私聊，2：单聊',
    pay_type        tinyint(1) default 0                 null comment '聊天室支付类型 工会内部免费聊天:0,工会外部付费聊天:1',
    people_limit    INT UNSIGNED                         NOT NULL COMMENT '聊天室最大人数',
    expire_time     datetime   default CURRENT_TIMESTAMP null comment '聊天有效期时间',
    create_time     datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    league_id       bigint                               not null comment '公会ID',
    inviter_user_id bigint unsigned                      not null comment '聊天发起方用户ID'
) ENGINE = InnoDB
  DEFAULT collate = utf8mb4_unicode_ci comment '聊天室表';

-- 聊天室成员表
DROP TABLE IF EXISTS `chat_room_member`;
CREATE TABLE IF NOT EXISTS chat_room_member
(
    id          bigint UNSIGNED                    NOT NULL AUTO_INCREMENT PRIMARY KEY comment 'id',
    room_id     bigint UNSIGNED                    NOT NULL COMMENT '聊天室ID',
    user_id     bigint UNSIGNED                    NOT NULL COMMENT '用户ID',
    nickname    VARCHAR(30)                        NOT NULL COMMENT '室友昵称',
    role        TINYINT(1) UNSIGNED                NOT NULL DEFAULT 0 COMMENT '室友角色',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint chat_room_member_room_id_user_id_uindex
        unique (room_id, user_id)
) ENGINE = InnoDB
  DEFAULT collate = utf8mb4_unicode_ci comment '聊天室成员表';

create index chat_room_member_user_id_index
    on chat_room_member (user_id);


-- 聊天记录表
DROP TABLE IF EXISTS `chat_room_record`;
CREATE TABLE IF NOT EXISTS chat_room_record
(
    id           bigint UNSIGNED                    NOT NULL AUTO_INCREMENT PRIMARY KEY comment 'id',
    room_id      bigint UNSIGNED                    NOT NULL COMMENT '聊天室ID',
    user_id      bigint UNSIGNED                    NOT NULL COMMENT '消息发送者ID',
    message_type TINYINT(1) UNSIGNED                NOT NULL COMMENT '消息类型. 文字，图片，视频，语音，文件，撤回消息',
    data         JSON                               NOT NULL COMMENT '消息数据体',
    reply_id     bigint UNSIGNED                    NULL COMMENT '回复消息的消息记录ID',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间'
) ENGINE = InnoDB
  DEFAULT collate = utf8mb4_unicode_ci comment '聊天记录表';

create index chat_room_record_create_time_index
    on chat_room_record (create_time);

create index chat_room_record_room_id_user_id_index
    on chat_room_record (room_id, user_id);


-- 聊天会话列表
DROP TABLE IF EXISTS `chat_session`;
CREATE TABLE IF NOT EXISTS chat_session
(
    id              bigint UNSIGNED     NOT NULL AUTO_INCREMENT PRIMARY KEY comment 'id',
    user_id         bigint UNSIGNED     NOT NULL COMMENT '用户ID',
    receive_user_id bigint unsigned     null comment '被接受消息者用户id',
    session_type    TINYINT(1) UNSIGNED NOT NULL COMMENT '会话类型。聊天室会话：0，聊天室通知会话：1',
    room_id         bigint UNSIGNED     NOT NULL COMMENT '聊天室ID',
    room_type       TINYINT(1) UNSIGNED NOT NULL default 0 COMMENT '聊天室的类型 1: 群聊，0：私聊，2：单聊',
    unread          TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '未读消息数',
    visible         BOOLEAN             NOT NULL DEFAULT TRUE COMMENT '是否显示',
    sticky          BOOLEAN             NOT NULL DEFAULT FALSE COMMENT '是否置顶',
    create_time     datetime                     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime                     default CURRENT_TIMESTAMP not null comment '更新时间',
    record_id       bigint unsigned     null comment '消息id',
    constraint chat_session_user_id_receive_user_id_uindex
        unique (user_id, receive_user_id)
) ENGINE = InnoDB
  DEFAULT collate = utf8mb4_unicode_ci comment '聊天会话列表';

create index chat_session_user_id_index
    on chat_session (user_id);

create index chat_session_room_id_index
    on chat_session (room_id);
