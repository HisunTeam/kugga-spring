-- 删除历史模板数据
delete
from duke_message_template
where language = 'en-US'
  and deleted = false;
-- 插入新数据
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0000', '公会相关 00', '00', 'INVITE', 'en-US', '公会认证-邀请', '{} requested an endorsement for [{}].',
        '张三邀请为[后端开发工程师]做公会认证', 'D', null, null, null, '2022-08-11 16:20:52', null, '2022-09-20 09:28:34', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0001', 'LEAGUE_AUTH_INVITE', 'LEAGUE_AUTHENTICATION', 'INVITE', 'en-US', '公会认证-邀请',
        '{} requested an endorsement for [{}].', '张三邀请为[后端开发工程师]做公会认证', 'D', null, null, null, '2022-08-11 16:20:52',
        null, '2022-09-20 09:28:34', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0002', 'LEAGUE_AUTH_CALLBACK', 'LEAGUE_AUTHENTICATION', 'CALLBACK', 'en-US', '公会认证-回调',
        '{} has endorsed [{}].', '李四为[后端开发工程师]做公会认证', 'D', null, null, null, '2022-08-11 16:20:52', null,
        '2022-09-20 09:28:34', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0003', 'LEAGUE_JOIN_INVITE', 'JOIN_LEAGUE', 'INVITE', 'en-US', '加入公会',
        'You are invited to join [{}]  by [{}] .', '张三邀请您加入[后端开发工程师]', 'D', null, null, null, '2022-08-11 16:20:53',
        null, '2022-09-20 09:28:34', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0004', 'LEAGUE_CREATE_CALLBACK', 'JOIN_LEAGUE', 'CALLBACK', 'en-US', '加入公会-通知邀请方',
        'The guild has been formally founded after {} accepting your invitation. Go establishing the rules of the guild.',
        '在张三接受您的邀请后，公会正式成立。去建立公会的规则。', 'D', null, null, null, '2022-08-11 16:20:53', null, '2022-09-20 09:28:34',
        false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0006', 'LEAGUE_FIRST_JOIN_INVITE', 'JOIN_LEAGUE_FIRST', 'INVITE', 'en-US', '第一个加入公会通知',
        'You are the first member to join guild [{}], so you can get a bonus of ${}.',
        '您是第一个受邀加入公会后端开发工程师的会员，并获得10美元的奖金。', 'UD', null, null, null, '2022-08-11 16:20:53', null, '2022-09-26 14:45:01',
        false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0007', 'LEAGUE_FIRST_JOIN_INVITE_FREE', 'JOIN_LEAGUE_FIRST', 'INVITE', 'en-US', '第一个加入公会通知-免费',
        'You are the first member to join guild [{}] by invitation.', '您是第一个受邀加入公会后端开发工程师的会员', 'UD', null, null, null,
        '2022-08-11 16:20:53', null, '2022-09-20 09:33:45', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0008', 'LEAGUE_CREATE_AUTH_INVITE', 'LEAGUE_CREATE_AUTH', 'INVITE', 'en-US', '创建公会-通知进行公会认证',
        '{} accepted my invitation to join the guild, so the guild has been officially established. Go and request an endorsement for your guild.',
        '张三接受了我创建公会的邀请，公会已正式成立，快去完成公会认证吧', 'D', null, null, null, '2022-08-11 16:20:53', null, '2022-09-22 16:18:58',
        false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0009', 'LEAGUE_CREATE_RULE_INVITE', 'LEAGUE_CREATE_RULE', 'INVITE', 'en-US', '创建公会-通知进行规则设置',
        '[{}]has been endorsed. Add rules for your guild''s members to follow.', '【后端开发工程师】公会已完成公会认证，快去设置公会规则吧', 'D',
        null, null, null, '2022-08-11 16:20:53', null, '2022-09-22 16:18:59', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0010', 'LEAGUE_AUTHENTICATION_CALLBACK_RECEIVER', 'LEAGUE_AUTHENTICATION', 'CALLBACK_RECEIVER', 'en-US',
        '公会认证-回调接收方', '[{}] Guild {} has completed the guild certification', '[产品经理]公会张三已完成公会认证', 'D', null, null, null,
        '2022-08-11 16:20:52', null, '2022-09-22 16:18:58', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0100', '推荐信相关 01', '01', 'INVITE', 'en-US', '推荐报告-邀请', '', '', 'D', null, null, null, '2022-08-11 16:20:52',
        null, '2022-09-20 13:13:35', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0101', 'RECOMMENDATION_INVITE', 'RECOMMENDATION', 'INVITE', 'en-US', '推荐报告-邀请',
        'Write a recommendation for {} and earn a ${} bonus.', '张三邀请写推荐报告，您将获得$50的收益', 'D', null, null, null,
        '2022-08-11 16:20:52', null, '2022-09-26 14:57:23', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0102', 'RECOMMENDATION_INVITE_FREE', 'RECOMMENDATION', 'INVITE2', 'en-US', '推荐报告-邀请-免费',
        'You are invited to write a recommendation for {}.', '张三邀请写推荐报告', 'D', null, null, null, '2022-08-11 16:20:52',
        null, '2022-09-20 10:03:58', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0103', 'RECOMMENDATION_CALLBACK', 'RECOMMENDATION', 'CALLBACK', 'en-US', '推荐报告-回调',
        '{} wrote a recommendation for you.', '李四为我写了推荐报告', 'UD', null, null, null, '2022-08-11 16:20:53', null,
        '2022-09-20 13:16:33', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0105', 'RECOMMENDATION_CALLBACK_RECEIVER2', 'RECOMMENDATION', 'CALLBACK_RECEIVER2', 'en-US', '推荐报告-回调接收方',
        'I wrote a recommendation report for {}', '我为张三写了推荐报告', 'UD', null, null, null, '2022-08-11 16:20:53', null,
        '2022-09-20 13:17:57', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0200', '聊天相关 02', '02', 'INVITE', 'en-US', '聊天邀请通知', '', '张三邀请与我聊天，您将获得$50的收益', 'D', null, null, null,
        '2022-08-11 16:20:53', null, '2022-09-26 14:59:02', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0201', 'CHAT_INVITE', 'CHAT', 'INVITE', 'en-US', '聊天邀请通知',
        'Accept communication invitation from {} and earna ${} bonus.', '张三邀请与我聊天，您将获得$50的收益', 'D', null, null, null,
        '2022-08-11 16:20:53', null, '2022-09-26 14:59:02', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0202', 'CHAT_INVITE_FREE', 'CHAT', 'INVITE2', 'en-US', '聊天邀请通知-免费',
        '{} initiated a communication invitation to you. ', '张三邀请与我聊天', 'D', null, null, null, '2022-08-11 16:20:53',
        null, '2022-09-20 10:19:19', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0203', 'CHAT_CALLBACK', 'CHAT', 'CALLBACK', 'en-US', '聊天回调通知', '{} accept my communication invitation.',
        '李四同意与我聊天', 'D', null, null, null, '2022-08-11 16:20:53', null, '2022-09-26 15:04:42', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0205', 'CHAT_CALLBACK_RECEIVER2', 'CHAT', 'CALLBACK_RECEIVER2', 'en-US', '聊天回调通知-回调接收方-免费',
        'You agree to chat with others.', '您同意与他人聊天', 'UD', null, null, null, '2022-08-11 16:20:53', null,
        '2022-09-20 10:22:37', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0300', '03 系统通知', '03', 'CALLBACK_RECEIVER2', 'en-US', '推荐报告-回调接收方-付费',
        'You wrote a recommendation report and received ${}', '您撰写推荐报告，到账$50(我为张三写了推荐报告)', 'UD', null, null, null,
        '2022-08-11 16:20:53', null, '2022-09-20 09:56:53', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0301', 'SYSTEM_NOTICE_PLATFORM_UPDATE', 'SYSTEM_NOTICE', 'INVITE', 'en-US', '系统通知',
        'Duke will update the system from {} - {}.', 'Duke全平台明晚24:00至次日8:00进行系统升级', 'UD', null, null, null,
        '2022-08-11 16:20:54', null, '2022-09-20 10:08:12', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0400', '04 到账通知', '04', 'CALLBACK_RECEIVER2', 'en-US', '推荐报告-回调接收方-付费',
        'You wrote a recommendation report and received ${}', '您撰写推荐报告，到账$50(我为张三写了推荐报告)', 'UD', null, null, null,
        '2022-08-11 16:20:53', null, '2022-09-20 10:08:49', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0401', 'REACH_ACCOUNT_AUTH', 'RECOMMENDATION', '12', 'en-US', '到账通知-公会认证到账',
        'You do guild certification for the guild and received ${}.', '您为公会做公会认证，到账${50}', 'UD', null, null, null,
        '2022-08-11 16:20:53', null, '2022-09-20 13:25:08', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0402', 'REACH_ACCOUNT_RECOMMENDATION', 'RECOMMENDATION', 'CALLBACK_RECEIVER', 'en-US', '推荐报告-回调接收方-付费',
        'You have written a recommendation and earned a ${} bonus.', '您撰写推荐报告，到账$50', 'UD', null, null, null,
        '2022-08-11 16:20:53', null, '2022-09-26 14:45:58', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0403', 'REACH_ACCOUNT_CHAT', 'CHAT', 'CALLBACK_RECEIVER', 'en-US', '聊天-回调给接受人-付费',
        'You agree to chat with others and received ${}', '您同意与他人聊天，到账$50', 'UD', null, null, null,
        '2022-08-11 16:20:53', null, '2022-09-20 13:23:20', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0500', '05 加入公会审批', '11', 'CALLBACK', 'en-US', '主动加入-审批同意申请', '', '[后端开发工程师]同意你加入公会', 'UD', null, null, null,
        '2022-08-11 16:20:53', null, '2022-09-22 15:23:53', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0501', 'JOIN_LEAGUE_ACTIVE_AGREE', 'JOIN_LEAGUE_ACTIVE', 'CALLBACK', 'en-US', '主动加入-审批同意申请',
        'Your request to join [{}] is approved.', '[后端开发工程师]同意你加入公会', 'UD', null, null, null, '2022-08-11 16:20:53',
        null, '2022-09-26 14:49:40', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0502', 'JOIN_LEAGUE_ACTIVE_REJECT', 'JOIN_LEAGUE_ACTIVE', 'CALLBACK2', 'en-US', '主动加入-审批拒绝申请',
        '[{}] rejected your request to join.', '[后端开发工程师]拒绝你加入公会', 'UD', null, null, null, '2022-08-11 16:20:53', null,
        '2022-09-22 15:25:30', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0503', 'JOIN_LEAGUE_ACTIVE_EXPIRE', 'JOIN_LEAGUE_ACTIVE', 'CALLBACK3', 'en-US', '主动加入-过期拒绝申请',
        'Your application to join [{}] has expired', '因为本次加入申请过期，[{后端开发工程师}]公会已经拒绝您的申请', 'UD', null, null, null,
        '2022-08-11 16:20:53', null, '2022-09-20 10:09:45', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0600', '06 其他', '06', 'INVITE', 'en-US', '邀请链接失效通知', 'The link to request endorsement has expired. ',
        '创建公会的邀请链接已失效 ', 'UD', null, null, null, '2022-08-11 16:20:54', null, '2022-09-20 09:47:58', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0601', 'INVITE_LINK_EXPIRE_CREATE_LEAGUE', 'INVITE_LINK_EXPIRE', 'INVITE', 'en-US', '邀请链接失效通知',
        'The link to request endorsement has expired. ', '创建公会的邀请链接已失效 ', 'UD', null, null, null, '2022-08-11 16:20:54',
        null, '2022-09-20 09:47:58', false);


-- 新增系统配置参数
select *
from duke_system_params;

INSERT INTO duke_system_params (type, param_key, value, description)
VALUES ('task', 'auth', '72', '任务-公会认证 订单有效期');
INSERT INTO duke_system_params (type, param_key, value, description)
VALUES ('task', 'report', '72', '任务-写推荐报告 订单有效期');
INSERT INTO duke_system_params (type, param_key, value, description)
VALUES ('task', 'chat', '72', '任务-公会认证订单有效期');
INSERT INTO duke_system_params (type, param_key, value, description)
VALUES ('task', 'join_league', '72', '主动加入公会 订单有效期');
