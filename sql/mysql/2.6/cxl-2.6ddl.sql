-- 10.19
-- 公会规则 订阅价格ddl
alter table duke_league_rule
    modify user_join_price decimal(10, 2) null comment '用户加入价格 (永久加入)';
alter table duke_league_rule
    add subscribe_month_price decimal(10, 2) null comment '按月订阅价格' after user_join_price;
alter table duke_league_rule
    add subscribe_quarter_price decimal(10, 2) null comment '按季度订阅价格' after subscribe_month_price;
alter table duke_league_rule
    add subscribe_year_price decimal(10, 2) null comment '按年订阅价格' after subscribe_quarter_price;
alter table duke_league_rule
    add subscribe_forever_price decimal(10, 2) null comment '永久订阅' after subscribe_year_price;

alter table duke_league_rule
    modify user_join_price decimal(10, 2) null comment '用户加入价格 (Deprecated)';
alter table duke_league_rule
    alter column subscribe_month_price set default '0.00';
alter table duke_league_rule
    alter column subscribe_quarter_price set default '0.00';
alter table duke_league_rule
    alter column subscribe_year_price set default '0.00';
alter table duke_league_rule
    alter column subscribe_forever_price set default '0.00';

alter table duke_task_league_join
    add subscribe_type varchar(16) null comment '订阅类型' after app_order_no;

create table duke_league_subscribe
(
    id               bigint auto_increment comment '订阅id',
    league_member_id bigint                                   not null comment '公会成员id',
    user_id          bigint                                   null comment '用户id',
    league_id        bigint                                   null comment '公会id',
    subscribe_type   varchar(16)                              not null comment '订阅类型',
    price            decimal(15, 2) default '0.00'            null comment '订阅价格',
    expire_time      datetime                                 null comment '过期时间日期',
    status           bit            default b'0'              not null comment '订阅状态 true 订阅中 false取消订阅',
    creator          varchar(64)    default ''                null comment '创建者',
    create_time      datetime       default CURRENT_TIMESTAMP not null comment '创建时间',
    updater          varchar(64)    default ''                null comment '更新者',
    update_time      datetime       default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted          bit            default b'0'              not null comment '是否删除',
    constraint duke_league_member_subscribe_pk primary key (id)
)
    comment '公会成员订阅表';
create index idx_expireTime on duke_league_subscribe (expire_time);

-- 公会订阅流水表
create table duke_league_subscribe_flow
(
    id              bigint auto_increment comment '订阅流水id',
    subscribe_id    bigint                                   not null comment '订阅id',
    user_id         bigint                                   null comment '用户id',
    league_id       bigint                                   null comment '公会id',
    subscribe_type  varchar(16)                              not null comment '订阅类型',
    price           decimal(15, 2) default '0.00'            null comment '订阅价格',
    app_order_no    varchar(32)                              null comment '订单号',
    subscribe_time  datetime                                 null comment '订阅日期',
    business_status int(2)         default '0'               null comment '业务状态 (0初始化、1已下单、2已支付、3已分账、6失败)',
    remark          varchar(512)                             null comment '备注',
    creator         varchar(64)    default ''                null comment '创建者',
    create_time     datetime       default CURRENT_TIMESTAMP not null comment '创建时间',
    updater         varchar(64)    default ''                null comment '更新者',
    update_time     datetime       default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted         bit            default b'0'              not null comment '是否删除',
    constraint duke_league_subscribe_flow primary key (id)
)
    comment '公会订阅流水表';


--  10.31 消息模板
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0700', '07 公会订阅', '07', 'INVITE', 'en-US', '', '', ' ', 'UD', null, null, null, '2022-08-11 16:20:54', null,
        '2022-10-25 14:44:56', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0701', 'LEAGUE_SUBSCRIBE_RENEWAL', 'LEAGUE_SUBSCRIBE_RENEWAL', 'INVITE', 'en-US', '公会订阅续期',
        'Your subscription of [{}] guid will be automatically extended on {}. Please make sure you have sufficient funds in your wallet ${}.',
        '您在{后端开发工程师公会}的订阅将于 2022年11月11日 以 10$/月的价格自动续期，清保持账户余额充足', 'UD', null, null, null, '2022-08-11 16:20:54', null,
        '2022-09-20 09:47:58', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0702', 'LEAGUE_SUBSCRIBE_NO_BALANCE_QUIT', 'LEAGUE_SUBSCRIBE_NO_BALANCE_QUIT', 'INVITE', 'en-US',
        '订阅续期余额不足消息通知', 'Your subscription of [{}] guild is suspended due to insufficient account balance. You will lose access to the [{}] guild and its benefits.
', '由于帐户余额不足，您对｛xxx｝公会的订阅已暂停。您将无法访问｛xxx｝公会及其福利。', 'UD', null, null, null, '2022-08-11 16:20:54', null,
        '2022-10-27 15:19:16', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0800', '08 公会发红包', '08', 'INVITE', 'en-US', '', '', ' ', 'UD', null, null, null, '2022-08-11 16:20:54', null,
        '2022-10-25 14:44:56', false);
INSERT INTO duke_message_template (message_code, message_key, message_scene, message_type, language, subject, template,
                                   original_template, deal_flag, send_interval, send_limit, creator, create_time,
                                   updater, update_time, deleted)
VALUES ('0801', 'LEAGUE_RED_PACKAGE', 'LEAGUE_RED_PACKAGE', 'INVITE', 'en-US', '公会发红包',
        'You have received ${} from the [{}] Guild.', '获得后端开发工程师公会100$的红包', 'UD', null, null, null,
        '2022-08-11 16:20:54', null, '2022-10-26 13:54:02', false);

-- 公会规则订阅老数据设置默认值
update duke_league_rule
set subscribe_month_price   = '0.00',
    subscribe_quarter_price = '0.00',
    subscribe_year_price    = '0.00',
    subscribe_forever_price = '0.00'
where subscribe_month_price is null;

-- 10.31 订阅选择规则
alter table duke_league_rule
    add subscribe_select varchar(8) default '0_0_0_0' null comment '订阅选择状况 1生效，0不生效
按月、季、年、永久的顺序排列，下划线分割' after user_join_price;


-- 订阅邮件通知
INSERT INTO email_template (email_scene, email_type, locale, subject, template, send_interval, send_limit, creator,
                            create_time, updater, update_time, deleted)
VALUES ('LEAGUE_SUBSCRIBE_RENEWAL', 'SIMPLE_HTML_MAIL', 'en-US', 'KuggaDuke Renewal Reminder Email', '<!doctype html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title></title>
    <style>
img{border:none;-ms-interpolation-mode:bicubic;max-width:100%}
body{background-color:#f2f3f4;font-family:sans-serif;-webkit-font-smoothing:antialiased;font-size:14px;line-height:1.4;margin:0;padding:0;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}
table{border-collapse:separate;mso-table-lspace:0pt;mso-table-rspace:0pt;width:100%}
table td{font-family:sans-serif;font-size:14px;vertical-align:top}
.body{background-color:#f2f3f4;width:100%}
.container{display:block;margin:0 auto !important;max-width:620px;padding:10px;width:620px}
.content{box-sizing:border-box;display:block;margin:0 auto;max-width:620px;padding:10px}
.main{background:#ffffff;width:100%;box-shadow:0px 0px 20px rgba(0,0,0,0.1);border-radius:18px}
.wrapper{box-sizing:border-box;padding:40px}
.content-block{padding-bottom:10px;padding-top:10px}
.btn{box-sizing:border-box;width:100%}
.btn > tbody > tr > td{padding-bottom:15px}
.btn table{width:auto}
.btn table td{background-color:#ffffff;border-radius:40px;text-align:center}
.btn a{background-color:#ffffff;border:solid 1px #182034;border-radius:40px;box-sizing:border-box;color:#182034;cursor:pointer;display:inline-block;font-size:24px;font-weight:700;margin:0;padding:18px 85px;text-decoration:none;text-transform:capitalize}
.btn-primary table td{background-color:#182034}
.btn-primary a{background-color:#182034;border-color:#182034;color:#ffffff}
h1,h2,h3,h4{color:#000000;font-family:sans-serif;font-weight:400;line-height:1.4;margin:0;margin-bottom:30px}
h1{font-size:35px;font-weight:300;text-align:center;text-transform:capitalize}
p,ul,ol{font-family:sans-serif;font-size:18px;font-weight:normal;color:#222;margin:0;margin-bottom:15px}
p li,ul li,ol li{list-style-position:inside;margin-left:5px}
a{color:#EB4141;text-decoration:underline}
    </style>
  </head>
  <body>
    <table role="presentation" border="0" cellpadding="0" cellspacing="0" class="body">
      <tr>
        <td>&nbsp;</td>
        <td class="container">
          <div class="content">
            <table role="presentation" class="main">
              <tr>
                <td class="wrapper">
                  <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <p style="text-align: left">Hi,Thank you for being a part of our [{}] guild.</p>
                      </td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr>
                      <td>
                        <p style="text-align: left">We wanted to remind you that you''ve chosen the automatic renewal option.Your subscription will be renewed on [{}].Please make sure you have sufficient funds in your wallet (${}). You can change your current plan in subscription.</p>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <table border="0" cellpadding="0" cellspacing="0" class="btn btn-primary">
                          <tbody>
                          <tr>
                            <td align="center">
                              <table border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                <tr>
                                  <td><a href="{}" target="_blank">Subscription</a> </td>
                                </tr>
                                </tbody>
                              </table>
                            </td>
                          </tr>
                          </tbody>
                        </table>
                      </td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
					<tr>
						<td>
						 <table role="presentation" border="0" cellpadding="0" cellspacing="0" class="number">
                          <tbody>
                            <tr>
                              <td align="left">
                                <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                                  <tbody>
                                    <tr>
                                      <td>
                                        <p style="font-size: 16px;color: #999;text-align: left;">Our entire team is dedicated to ensuring your experience in KuggaDuke. If you have any questions, concerns, or suggestions, simply reply to this email and we''ll get back to you with a prompt response.</p>
                                      </td>
                                    </tr>
                                  </tbody>
                                </table>
                              </td>
                            </tr>
                          </tbody>
                        </table>
						</td>
					</tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr>
                        <td>
                            <p>Best wishes,<br/>The KuggaDuke</p>
                        </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </div>
        </td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </body>
</html>
', 0, 100, null, null, null, null, false);

-- 生产公会全部关闭主动加入 和类型选择默认值
update duke_league_rule
set enabled_user_join = false
where create_time >= '2021-10-25 00:00:00';
update duke_league_rule
set subscribe_select = '0_0_0_0'
where create_time >= '2021-10-25 00:00:00';


-- 11.3 订阅实际状态
alter table duke_league_subscribe
    modify status bit default b'0' not null comment '订阅状态 true 订阅中 false取消订阅 (手动开关)';

alter table duke_league_subscribe
    add expire_status bit default b'0' null comment '订阅过期状态 true订阅中，false取消订阅
(可能存在status关闭但是还未过期，此时状态为0)' after status;

-- 删除league_member_id
drop index udx_leagueMemberId on duke_league_subscribe;
alter table duke_league_subscribe
    drop column league_member_id;

