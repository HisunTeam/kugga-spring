DROP TABLE IF EXISTS `duke_charge_order`;
CREATE TABLE `duke_charge_order`
(
    `id`              bigint(20)     NOT NULL AUTO_INCREMENT COMMENT '订单id',
    `app_order_no`    varchar(64)    NULL DEFAULT NULL COMMENT '内部订单号',
    `wallet_order_no` varchar(64)    NULL DEFAULT NULL COMMENT '钱包订单号',
    `user_id`         bigint(20)     NULL DEFAULT NULL COMMENT '用户id',
    `account_id`      varchar(64)    NULL DEFAULT NULL COMMENT '钱包账号',
    `amount`          decimal(11, 2) NULL DEFAULT NULL COMMENT '充值金额，单位：元',
    `fee`             decimal(10, 2) NULL DEFAULT NULL COMMENT '手续费，单位：元',
    `charge_channel`  varchar(255)   NULL DEFAULT 'paypal' COMMENT '充值方式 paypal',
    `currency`        varchar(64)    NULL DEFAULT NULL COMMENT '交易币种',
    `status`          varchar(64)    NULL DEFAULT NULL COMMENT '充值状态 prepay 预支付；processing 处理中；success 已成功；failed 交易失败；closed 已关闭',
    `creator`         varchar(64)    NULL DEFAULT NULL COMMENT '创建者',
    `received_time`   datetime       NULL DEFAULT NULL COMMENT '到账时间',
    `create_time`     datetime       NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         varchar(64)    NULL DEFAULT NULL COMMENT '更新者',
    `update_time`     datetime       NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)         NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `app_order_no_index` (`app_order_no`) USING BTREE,
    UNIQUE INDEX `wallet_order_no` (`wallet_order_no`) USING BTREE
) ENGINE = InnoDB COMMENT = '充值订单'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `duke_pay_order`;
CREATE TABLE `duke_pay_order`
(
    `id`              bigint(20)     NOT NULL AUTO_INCREMENT COMMENT '订单id',
    `app_order_no`    varchar(64)    NULL     DEFAULT NULL COMMENT '内部订单号',
    `wallet_order_no` varchar(64)    NULL     DEFAULT NULL COMMENT '钱包订单号',
    `order_type`      varchar(2)     NULL     DEFAULT NULL COMMENT '订单类型【1.创建公会；2.公会认证；3.编写推荐报告；4.加入公会；5.发起聊天；6.成员聊天；7.退款；8.充值；9.获得红包/发红包】',
    `payer_id`        bigint(20)     NOT NULL COMMENT '付款方id',
    `account_type`    varchar(24)    NULL     DEFAULT NULL COMMENT '付款方账户类型',
    `account_id`      varchar(64)    NOT NULL COMMENT '钱包账户',
    `pay_channel`     varchar(24)    NULL     DEFAULT 'balance' COMMENT '支付方式【balance：余额支付，paypal：paypal支付】',
    `pay_amount`      decimal(13, 2) NULL     DEFAULT NULL COMMENT '交易金额',
    `fee`             decimal(10, 2) NULL     DEFAULT NULL COMMENT '手续费',
    `split_amount`    decimal(10, 2) NULL     DEFAULT 0.00 COMMENT '已分账金额',
    `refund_amount`   decimal(10, 2) NULL     DEFAULT 0.00 COMMENT '已退款金额',
    `currency`        varchar(24)    NULL     DEFAULT NULL COMMENT '交易币种',
    `status`          varchar(24)    NOT NULL DEFAULT 'prepay' COMMENT '交易状态： prepay 预支付；closed 支付超时关闭；paySuccess 支付成功；payFailed 支付失败；splitSuccess 分账成功；splitFailed 分账失败；refundSuccess 退款成功；refundFailed 退款失败；partRefund 部分退款',
    `remark`          varchar(256)   NULL     DEFAULT NULL COMMENT '备注',
    `creator`         varchar(64)    NULL     DEFAULT NULL COMMENT '创建者',
    `create_time`     datetime       NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         varchar(64)    NULL     DEFAULT NULL COMMENT '更新者',
    `update_time`     datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)         NULL     DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_order_no` (`app_order_no`) USING BTREE
) ENGINE = InnoDB COMMENT = '支付订单表'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `duke_pay_order_sub`;
CREATE TABLE `duke_pay_order_sub`
(
    `id`           bigint(20)     NOT NULL AUTO_INCREMENT COMMENT '订单子集id',
    `app_order_no` varchar(64)    NULL     DEFAULT NULL COMMENT '内部订单号',
    `split_no`     varchar(64)    NULL     DEFAULT NULL COMMENT '钱包分账记录编号',
    `order_type`   varchar(2)     NULL     DEFAULT NULL COMMENT '订单子集交易类型：1.创建公会；2.公会认证；3.编写推荐报告；4.加入公会；5.发起聊天；6.成员聊天；7.退款；8.充值；9.获得红包/发红包',
    `receiver_id`  bigint(20)     NULL     DEFAULT NULL COMMENT '收款方id',
    `account_type` varchar(64)    NULL     DEFAULT NULL COMMENT '收款方账户类型',
    `account_id`   varchar(64)    NULL     DEFAULT NULL COMMENT '收款方钱包账户',
    `amount`       decimal(13, 2) NULL     DEFAULT NULL COMMENT '收款金额',
    `status`       varchar(24)    NOT NULL DEFAULT 'draft' COMMENT '状态： preSplit 待分账；splitSuccess 分账成功；splitFailed 分账失败',
    `remark`       varchar(64)    NULL     DEFAULT NULL COMMENT '备注',
    `creator`      varchar(64)    NULL     DEFAULT NULL COMMENT '创建者',
    `create_time`  datetime       NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64)    NULL     DEFAULT NULL COMMENT '更新者',
    `update_time`  datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)         NULL     DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '支付订单分账表'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `duke_withdraw_order`;
CREATE TABLE `duke_withdraw_order`
(
    `id`               bigint(20)     NOT NULL AUTO_INCREMENT COMMENT '订单id',
    `app_order_no`     varchar(64)    NULL DEFAULT NULL COMMENT '内部订单号',
    `wallet_order_no`  varchar(64)    NULL DEFAULT NULL COMMENT '钱包订单号',
    `user_id`          bigint(20)     NULL DEFAULT NULL COMMENT '用户id',
    `account_id`       varchar(64)    NULL DEFAULT NULL COMMENT '钱包账号',
    `amount`           decimal(11, 2) NULL DEFAULT NULL COMMENT '提现金额，单位：元',
    `fee`              decimal(10, 2) NULL DEFAULT NULL COMMENT '手续费，单位：元',
    `actual_amount`    decimal(10, 2) NULL DEFAULT NULL COMMENT '实际到账金额，单位：元',
    `withdraw_channel` varchar(255)   NULL DEFAULT 'paypal' COMMENT '提现方式 paypal',
    `card_no`          varchar(255)   NULL DEFAULT NULL COMMENT '提现卡号',
    `currency`         varchar(64)    NULL DEFAULT NULL COMMENT '交易币种',
    `status`           varchar(64)    NULL DEFAULT NULL COMMENT '提现状态 draft 等待到账；success 已成功；failed 交易失败；',
    `creator`          varchar(64)    NULL DEFAULT NULL COMMENT '创建者',
    `received_time`    datetime       NULL DEFAULT NULL COMMENT '到账时间',
    `create_time`      datetime       NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          varchar(64)    NULL DEFAULT NULL COMMENT '更新者',
    `update_time`      datetime       NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)         NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `app_order_no_index` (`app_order_no`) USING BTREE,
    UNIQUE INDEX `wallet_order_no` (`wallet_order_no`) USING BTREE
) ENGINE = InnoDB COMMENT = '提现订单'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `duke_platform_account`;
CREATE TABLE `duke_platform_account`
(
    `id`           bigint(20)     NOT NULL AUTO_INCREMENT COMMENT '账户ID',
    `pay_password` varchar(255)   NULL     DEFAULT NULL COMMENT '支付密码',
    `account_id`   varchar(255)   NULL     DEFAULT NULL COMMENT '外部钱包账户编号',
    `balance`      decimal(13, 2) NULL     DEFAULT 0.00 COMMENT '账户金额',
    `creator`      varchar(64)    NULL     DEFAULT NULL COMMENT '创建者',
    `create_time`  datetime       NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater`      varchar(64)    NULL     DEFAULT NULL COMMENT '更新者',
    `deleted`      bit(1)         NULL     DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '平台账户表';

ALTER TABLE `duke_user_bill`
    DROP `order_group_no`;
ALTER TABLE `duke_user_bill`
    ADD COLUMN `wallet_order_no` VARCHAR(64) NULL DEFAULT NULL COMMENT '钱包订单号' AFTER `bill_no`;
ALTER TABLE `duke_user_bill`
    ADD COLUMN `fee` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '手续费' AFTER `wallet_order_no`;

ALTER TABLE `duke_league_bill`
    DROP `order_group_no`;
ALTER TABLE `duke_league_bill`
    ADD COLUMN `wallet_order_no` VARCHAR(64) NULL DEFAULT NULL COMMENT '钱包订单号' AFTER `bill_no`;
ALTER TABLE `duke_league_bill`
    ADD COLUMN `fee` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '手续费' AFTER `wallet_order_no`;


INSERT INTO `email_template` (`email_scene`, `email_type`, `locale`, `subject`, `template`, `send_interval`,
                              `send_limit`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('UPDATE_PAY_PASSWORD', 'SIMPLE_HTML_MAIL', 'en-US', 'Email verification request from KuggaDuke',
        '<!doctype html>\n<html>\n  <head>\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n    <title></title>\n    <style>\n      img{border:none;-ms-interpolation-mode:bicubic;max-width:100%}\n      body{background-color:#f2f3f4;font-family:sans-serif;-webkit-font-smoothing:antialiased;font-size:14px;line-height:1.4;margin:0;padding:0;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}\n      table{border-collapse:separate;mso-table-lspace:0pt;mso-table-rspace:0pt;width:100%}\n      table td{font-family:sans-serif;font-size:14px;vertical-align:top}\n      .body{background-color:#f2f3f4;width:100%}\n      .container{display:block;margin:0 auto !important;max-width:620px;padding:10px;width:620px}\n      .content{box-sizing:border-box;display:block;margin:0 auto;max-width:620px;padding:10px}\n      .main{background:#ffffff;width:100%;box-shadow:0px 0px 20px rgba(0,0,0,0.1);border-radius:18px}\n      .wrapper{box-sizing:border-box;padding:40px}\n      .content-block{padding-bottom:10px;padding-top:10px}\n      h1,h2,h3,h4{color:#000000;font-family:sans-serif;font-weight:400;line-height:1.4;margin:0;margin-bottom:30px}\n      h1{font-size:35px;font-weight:300;text-align:center;text-transform:capitalize}\n      p,ul,ol{font-family:sans-serif;font-size:18px;font-weight:normal;color:#222;margin:0;margin-bottom:15px}\n      p li,ul li,ol li{list-style-position:inside;margin-left:5px}\n      a{color:#EB4141;text-decoration:underline}\n    </style>\n  </head>\n  <body>\n    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n      <tr>\n        <td>&nbsp;</td>\n        <td class=\"container\">\n          <div class=\"content\">\n            <table role=\"presentation\" class=\"main\">\n              <tr>\n                <td class=\"wrapper\">\n                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                    <tr>\n                      <td>\n                        <p style=\"text-align: center\">Hi, We received a request to change the password on your Duke Account.</p>\n                        <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"number\">\n                          <tbody>\n                            <tr>\n                              <td align=\"center\">\n                                <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                                  <tbody>\n                                    <tr>\n                                      <td>\n                                        <p style=\"font-size: 32px;color: #EB4141;text-align: center;\">{}</p>\n                                      </td>\n                                    </tr>\n                                    <tr>\n                                      <td>\n                                        <p style=\"font-size: 16px;color: #999;text-align: center;\">Enter this code to complete the change.</p>\n                                      </td>\n                                    </tr>\n                                    <tr>\n                                      <td>\n                                        <p style=\"font-size: 16px;color: #999;text-align: center;\">Please don\'t share this verification code with anyone else in order to protect the security of your account. It is only valid for 30 minutes. Thanks for helping us keep your account secure.</p>\n                                      </td>\n                                    </tr>\n                                  </tbody>\n                                </table>\n                              </td>\n                            </tr>\n                          </tbody>\n                        </table>\n                      </td>\n                    </tr>\n                    <tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr>\n                    <tr>\n                        <td>\n                            <p>Best wishes,<br/>The KuggaDuke</p>\n                        </td>\n                    </tr>\n                  </table>\n                </td>\n              </tr>\n            </table>\n          </div>\n        </td>\n        <td>&nbsp;</td>\n      </tr>\n    </table>\n  </body>\n</html>',
        60, 100, NULL, NULL, NULL, NULL, 0);

INSERT INTO `email_template` (`email_scene`, `email_type`, `locale`, `subject`, `template`, `send_interval`,
                              `send_limit`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('RESET_PAY_PASSWORD', 'SIMPLE_HTML_MAIL', 'en-US', 'Email verification request from KuggaDuke',
        '<!doctype html>\n<html>\n  <head>\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n    <title></title>\n    <style>\n      img{border:none;-ms-interpolation-mode:bicubic;max-width:100%}\n      body{background-color:#f2f3f4;font-family:sans-serif;-webkit-font-smoothing:antialiased;font-size:14px;line-height:1.4;margin:0;padding:0;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}\n      table{border-collapse:separate;mso-table-lspace:0pt;mso-table-rspace:0pt;width:100%}\n      table td{font-family:sans-serif;font-size:14px;vertical-align:top}\n      .body{background-color:#f2f3f4;width:100%}\n      .container{display:block;margin:0 auto !important;max-width:620px;padding:10px;width:620px}\n      .content{box-sizing:border-box;display:block;margin:0 auto;max-width:620px;padding:10px}\n      .main{background:#ffffff;width:100%;box-shadow:0px 0px 20px rgba(0,0,0,0.1);border-radius:18px}\n      .wrapper{box-sizing:border-box;padding:40px}\n      .content-block{padding-bottom:10px;padding-top:10px}\n      h1,h2,h3,h4{color:#000000;font-family:sans-serif;font-weight:400;line-height:1.4;margin:0;margin-bottom:30px}\n      h1{font-size:35px;font-weight:300;text-align:center;text-transform:capitalize}\n      p,ul,ol{font-family:sans-serif;font-size:18px;font-weight:normal;color:#222;margin:0;margin-bottom:15px}\n      p li,ul li,ol li{list-style-position:inside;margin-left:5px}\n      a{color:#EB4141;text-decoration:underline}\n    </style>\n  </head>\n  <body>\n    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n      <tr>\n        <td>&nbsp;</td>\n        <td class=\"container\">\n          <div class=\"content\">\n            <table role=\"presentation\" class=\"main\">\n              <tr>\n                <td class=\"wrapper\">\n                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                    <tr>\n                      <td>\n                        <p style=\"text-align: center\">Hi, We received a request to reset the password on your Duke Account.</p>\n                        <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"number\">\n                          <tbody>\n                            <tr>\n                              <td align=\"center\">\n                                <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                                  <tbody>\n                                    <tr>\n                                      <td>\n                                        <p style=\"font-size: 32px;color: #EB4141;text-align: center;\">{}</p>\n                                      </td>\n                                    </tr>\n                                    <tr>\n                                      <td>\n                                        <p style=\"font-size: 16px;color: #999;text-align: center;\">Enter this code to complete the reset.</p>\n                                      </td>\n                                    </tr>\n                                    <tr>\n                                      <td>\n                                        <p style=\"font-size: 16px;color: #999;text-align: center;\">Please don\'t share this verification code with anyone else in order to protect the security of your account. It is only valid for 30 minutes. Thanks for helping us keep your account secure.</p>\n                                      </td>\n                                    </tr>\n                                  </tbody>\n                                </table>\n                              </td>\n                            </tr>\n                          </tbody>\n                        </table>\n                      </td>\n                    </tr>\n                    <tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr>\n                    <tr>\n                        <td>\n                            <p>Best wishes,<br/>The KuggaDuke</p>\n                        </td>\n                    </tr>\n                  </table>\n                </td>\n              </tr>\n            </table>\n          </div>\n        </td>\n        <td>&nbsp;</td>\n      </tr>\n    </table>\n  </body>\n</html>',
        60, 100, NULL, NULL, NULL, NULL, 0);

INSERT INTO `email_template` (`email_scene`, `email_type`, `locale`, `subject`, `template`, `send_interval`,
                              `send_limit`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('SET_PAY_PASSWORD', 'SIMPLE_HTML_MAIL', 'en-US', 'Email verification request from KuggaDuke',
        '<!doctype html>\n<html>\n  <head>\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n    <title></title>\n    <style>\n      img{border:none;-ms-interpolation-mode:bicubic;max-width:100%}\n      body{background-color:#f2f3f4;font-family:sans-serif;-webkit-font-smoothing:antialiased;font-size:14px;line-height:1.4;margin:0;padding:0;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}\n      table{border-collapse:separate;mso-table-lspace:0pt;mso-table-rspace:0pt;width:100%}\n      table td{font-family:sans-serif;font-size:14px;vertical-align:top}\n      .body{background-color:#f2f3f4;width:100%}\n      .container{display:block;margin:0 auto !important;max-width:620px;padding:10px;width:620px}\n      .content{box-sizing:border-box;display:block;margin:0 auto;max-width:620px;padding:10px}\n      .main{background:#ffffff;width:100%;box-shadow:0px 0px 20px rgba(0,0,0,0.1);border-radius:18px}\n      .wrapper{box-sizing:border-box;padding:40px}\n      .content-block{padding-bottom:10px;padding-top:10px}\n      h1,h2,h3,h4{color:#000000;font-family:sans-serif;font-weight:400;line-height:1.4;margin:0;margin-bottom:30px}\n      h1{font-size:35px;font-weight:300;text-align:center;text-transform:capitalize}\n      p,ul,ol{font-family:sans-serif;font-size:18px;font-weight:normal;color:#222;margin:0;margin-bottom:15px}\n      p li,ul li,ol li{list-style-position:inside;margin-left:5px}\n      a{color:#EB4141;text-decoration:underline}\n    </style>\n  </head>\n  <body>\n    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n      <tr>\n        <td>&nbsp;</td>\n        <td class=\"container\">\n          <div class=\"content\">\n            <table role=\"presentation\" class=\"main\">\n              <tr>\n                <td class=\"wrapper\">\n                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                    <tr>\n                      <td>\n                        <p style=\"text-align: center\">Hi, We received a request to set the password on your Duke Account.</p>\n                        <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"number\">\n                          <tbody>\n                            <tr>\n                              <td align=\"center\">\n                                <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                                  <tbody>\n                                    <tr>\n                                      <td>\n                                        <p style=\"font-size: 32px;color: #EB4141;text-align: center;\">{}</p>\n                                      </td>\n                                    </tr>\n                                    <tr>\n                                      <td>\n                                        <p style=\"font-size: 16px;color: #999;text-align: center;\">Enter this code to complete the set.</p>\n                                      </td>\n                                    </tr>\n                                    <tr>\n                                      <td>\n                                        <p style=\"font-size: 16px;color: #999;text-align: center;\">Please don\'t share this verification code with anyone else in order to protect the security of your account. It is only valid for 30 minutes. Thanks for helping us keep your account secure.</p>\n                                      </td>\n                                    </tr>\n                                  </tbody>\n                                </table>\n                              </td>\n                            </tr>\n                          </tbody>\n                        </table>\n                      </td>\n                    </tr>\n                    <tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr>\n                    <tr>\n                        <td>\n                            <p>Best wishes,<br/>The KuggaDuke</p>\n                        </td>\n                    </tr>\n                  </table>\n                </td>\n              </tr>\n            </table>\n          </div>\n        </td>\n        <td>&nbsp;</td>\n      </tr>\n    </table>\n  </body>\n</html>',
        60, 100, NULL, NULL, NULL, NULL, 0);


