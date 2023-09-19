-- 聊天群组申请表
-- status: 0->等待验证，1->同意，2->拒绝，3->删除
CREATE TABLE IF NOT EXISTS chat_request_room
(
    id                INT UNSIGNED        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    room_id           INT UNSIGNED        NOT NULL COMMENT '聊天室ID',
    requester_user_id INT UNSIGNED        NOT NULL COMMENT '申请人ID',
    handler_user_id   INT UNSIGNED        NULL COMMENT '处理人ID',
    status            TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '申请状态',
    request_reason    VARCHAR(50)         NULL COMMENT '申请原因',
    reject_reason     VARCHAR(50)         NULL COMMENT '拒绝理由',
    readed_list       JSON                NOT NULL COMMENT '已读列表',
    create_time       BIGINT UNSIGNED     NOT NULL,
    update_time       BIGINT UNSIGNED     NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
