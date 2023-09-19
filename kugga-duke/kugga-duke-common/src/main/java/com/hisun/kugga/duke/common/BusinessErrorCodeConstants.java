package com.hisun.kugga.duke.common;

import com.hisun.kugga.framework.common.exception.ErrorCode;

/**
 * @Description: 业务错误码
 * @author： Lin
 * @Date 2022/7/21 11:21
 */
public interface BusinessErrorCodeConstants {
    /*
    kugga-duke-chat  10
     kugga-duke-common  11
    kugga-duke-league  12
     kugga-duke-pay  13
    kugga-duke-system  14
    kugga-duke-user 15
     kugga-duke-user-cv 16
     kugga-duke-bos 18

     */
    // ========== 系统DB 1001000000 ==========
    ErrorCode DB_UPDATE_FAILED = new ErrorCode(149000, "Failed to update data", "数据更新失败");
    ErrorCode DB_DELETE_FAILED = new ErrorCode(149001, "Failed to delete data", "数据删除失败");
    ErrorCode DB_INSERT_FAILED = new ErrorCode(149002, "Failed to add data", "数据新增失败");
    ErrorCode DB_GET_FAILED = new ErrorCode(149003, "Failed to query data", "数据查询失败");


    ErrorCode SYSTEM_ERROR = new ErrorCode(149004, "Sorry, the system is busy", "系统错误");
    ErrorCode PARAM_ERROR = new ErrorCode(149005, "Request parameter error", "请求参数错误");
    ErrorCode SPLICT_AMOUNT_NOT_ZERO = new ErrorCode(149006, "The dimension amount cannot be 0", "分账金额不能为0");

    // ========== AUTH 模块 1002000000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(150100, "Login failed. Please enter correct password. ", "登录失败，账号密码不正确");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(150101, "Login failed because account is not active", "登录失败，账号被禁用");
    ErrorCode AUTH_LOGIN_CAPTCHA_NOT_FOUND = new ErrorCode(150102, "Verification Not Found", "验证码不存在");
    ErrorCode AUTH_LOGIN_CAPTCHA_CODE_ERROR = new ErrorCode(150103, "Incorrect verification", "验证码不正确");
    ErrorCode AUTH_TOKEN_EXPIRED = new ErrorCode(150104, "Token expired", "Token 已经过期");
    ErrorCode AUTH_MOBILE_NOT_EXISTS = new ErrorCode(150105, "Phone number not found", "手机号不存在");

    // ========== 用户模块 1002003000 ==========
    ErrorCode USER_USERNAME_EXISTS = new ErrorCode(150200, "Account exists", "用户账号已经存在");
    ErrorCode USER_MOBILE_EXISTS = new ErrorCode(150201, "Phone number exists", "手机号已经存在");
    ErrorCode USER_EMAIL_EXISTS = new ErrorCode(150202, "Email exists", "邮箱已经存在");
    ErrorCode USER_NOT_EXISTS = new ErrorCode(150203, "User does not exist", "用户不存在");
    ErrorCode USER_IMPORT_LIST_IS_EMPTY = new ErrorCode(150204, "The value of user can't be empty", "导入用户数据不能为空！");
    ErrorCode USER_PASSWORD_FAILED = new ErrorCode(150205, "Incorrect current password", "原密码不正确");
    ErrorCode USER_IS_DISABLE = new ErrorCode(150206, "[{}]is disabled", "名字为【{}】的用户已被禁用");
    ErrorCode NEW_OLD_PASSWORD_NOT_EQUALS = new ErrorCode(150207, "Your new password cannot be the same as your old password.", "新老密码不能一致");
    ErrorCode USER_COUNT_MAX = new ErrorCode(150208,
            "Account creation failed, because the tenant's maximum tenant quota was exceeded ({})!",
            "创建用户失败，原因：超过租户最大租户配额({})！");
    ErrorCode PASSWORD_ENSURE_NOT_MATCH = new ErrorCode(150209, "Password confirmation doesn't match Password", "密码确认与密码不匹配");
    ErrorCode USER_REGISTER_COUNT_MAX = new ErrorCode(150210, "User registration failed, please try again later", "用户注册失败,请稍后重试");
    ErrorCode USER_ACCOUNT_NOT_EXIST = new ErrorCode(150211, "User account does not exist", "用户账户不存在");
    ErrorCode USER_NUMBER_COUNT_MAX = new ErrorCode(150212, "User name num exceeds the limit", "用户名num超标");
    ErrorCode USER_FORGET_PWD_TOKEN_ERROR = new ErrorCode(150213, "Password reset link is invalid or expired,Please try again to get a new link", "忘记密码token已失效");
    ErrorCode EMAIL_NOT_NULL = new ErrorCode(150202, "Please enter a valid email address", "邮箱不能为空");

    ErrorCode FAVORITE_NOT_EXISTS = new ErrorCode(150300, "Records in favorites not found", "收藏记录不存在");
    ErrorCode FAVORITE_EXISTS = new ErrorCode(150301, "Records in favorites exist", "收藏记录已存在");
    ErrorCode FAVORITE_RECOMMENDATION_LEAGUE_NOT_NULL = new ErrorCode(150302, "Guild ID cannot be empty when writing recommendation letter guild ID and collecting recommendation letter", "写推荐信公会id和收藏推荐信时公会id不能为空");
    ErrorCode GROUP_NUM_UPPER_LIMIT = new ErrorCode(150303, "The number of group count reaches the upper limit", "分组数达到上限");
    ErrorCode FAVORITE_GROUP_NOT_EXISTS = new ErrorCode(150304, "Group not found", "收藏分组不存在");
    // ========= 文件相关 1001003000=================
    ErrorCode FILE_PATH_EXISTS = new ErrorCode(1001003000, "File exists", "文件路径已存在");
    ErrorCode FILE_NOT_EXISTS = new ErrorCode(1001003001, "File not found", "文件不存在");
    ErrorCode FILE_IS_EMPTY = new ErrorCode(1001003002, "File is empty", "文件为空");
    ErrorCode FILE_IMAGE_NOT_SUPPORT = new ErrorCode(1001003003, "image file only support jpg png gif", "文件格式不支持");
    ErrorCode FILE_IMAGE_SIZE_LIMIT = new ErrorCode(1001003004, "image file limit for 1M", "文件大小限制");
    ErrorCode FILE_MODULE_ILLEGAL = new ErrorCode(1001003005, "file module contains unsupported characters", "fileModule包含不支持的字符");
    // ========== 公会模块 1002004000 ==========
    ErrorCode LEAGUE_NAME_NOT_EXISTS = new ErrorCode(125001, "Details of the guild not found", "公会信息不存在");
    ErrorCode LEAGUE_INSERT_ERR = new ErrorCode(125002, "Failed to update guild's information", "公会信息更新失败");
    ErrorCode LEAGUE_INVITE_BIND_ERR = new ErrorCode(125003, "Failed to bind invitation link", "公会邀请链接绑定失败");
    ErrorCode LEAGUE_INVITE_URL_NOT = new ErrorCode(125004, "Guild invitation link not found", "未查询到公会邀请链接");
    ErrorCode LEAGUE_CREATE_DEDUCT_FAIL = new ErrorCode(125005, "Charge failed", "扣款失败");
    ErrorCode NOT_REPEAT_JOIN_LEAGUE = new ErrorCode(125006, "You can only join the guild once", "不能重复加入公会");
    ErrorCode LEAGUE_USER_NOT_EXISTS = new ErrorCode(125007, "The guild user does not exist", "公会用户不存在");
    ErrorCode LEAGUE_CREATE_COST_ERR = new ErrorCode(125008, "Incorrect amount", "公会创建金额错误");
    ErrorCode LEAGUE_ID_IS_NULL = new ErrorCode(125009, "The guild ID is empty", "公会ID为空");
    ErrorCode LEAGUE_MEMBER_IS_NULL = new ErrorCode(125010, "The member's ID is empty", "公会成员ID为空");
    ErrorCode LEAGUE_NOT_ADMIN = new ErrorCode(125011, "Unauthorised to operate", "非公会管理员,拒绝操作");
    ErrorCode LEAGUE_NAME_EXISTS = new ErrorCode(125012, "Guild name already exists", "公会名已存在");
    ErrorCode LEAGUE_NOT_EXISTS = new ErrorCode(125013, "Guild does not exist", "公会不存在");
    ErrorCode LEAGUE_NOT_JOIN = new ErrorCode(125014, "Guild is not allowed to join", "公会不允许加入");
    ErrorCode LEAGUE_AMOUNT_CHANGE = new ErrorCode(125014, "Guild joining amount has changed", "公会加入金额已变动");
    ErrorCode LEAGUE_JOIN_ORDER_PAY = new ErrorCode(125015, "Join guild order paid", "加入公会订单已支付");
    ErrorCode LEAGUE_SUBSCRIBE_TYPE_OFF = new ErrorCode(125014, "This guild subscription type cannot be added", "该公会订阅类型不可加入");
    ErrorCode ABNORMAL_AMOUNT = new ErrorCode(125015, "Abnormal amount", "金额价格异常");
    ErrorCode ADMIN_AUTH = new ErrorCode(125016, "You are not the guild administrator", "你不是公会管理员");


    ErrorCode LEAGUE_JOIN_APPROVAL_ID_IS_NULL = new ErrorCode(125100, "Approval record ID is empty", "审批记录ID为空");
    ErrorCode LEAGUE_JOIN_APPROVAL_NOT_EXISTS = new ErrorCode(125101, "The approval record of joining the guild does not exist", "加入公会审批记录不存在");
    ErrorCode LEAGUE_JOIN_APPROVAL_PROCESSED = new ErrorCode(125102, "Approval record has been processed", "审批记录已处理");
    ErrorCode LEAGUE_JOIN_APPROVAL_EXPIRE = new ErrorCode(125103, "The approval record has expired", "审批记录已过期");
    ErrorCode LEAGUE_JOIN_RECORD_EXIST = new ErrorCode(125104, "Please do not submit duplicate requests to join the guild", "请不要提交重复的加入帮会请求");
    ErrorCode EXIST_JOIN_LEAGUE_RECORD_NOT_UPDATE_RULE = new ErrorCode(125105, "There are approval records for joining the guild, and the approval status cannot be modified", "存在加入公会审批记录，不能修改审批状态");
    ErrorCode LEAGUE_JOIN_RECORD_NOT_EXIST = new ErrorCode(125106, "The guild joining record does not exists", "加入公会记录不存在");
    ErrorCode LEAGUE_ADMIN_CAN_APPROVAL = new ErrorCode(125107, "Only guild administrators can approve", "公会管理员才能审批");
    ErrorCode USER_ALREADY_JOIN_LEAGUE = new ErrorCode(125108, "You've already joined this guild.", "用户已加入公会");
    ErrorCode APPLICANT_ALREADY_JOIN = new ErrorCode(125108, "Applicant has already joined this guild.", "申请人已加入该公会。");
    ErrorCode APPROVAL_TYPE_EXCEPTION = new ErrorCode(125109, "Approval type exception", "审批状态异常");

    ErrorCode SUBSCRIBE_TYPE_ERROR = new ErrorCode(125200, "subscribe type error", "订阅类型异常");
    ErrorCode SUBSCRIBE_RECORD_NOT_EXIST = new ErrorCode(125201, "Subscription record does not exist", "订阅记录不存在");
    ErrorCode SUBSCRIBE_FLOW_RECORD_NOT_EXIST = new ErrorCode(125201, "Subscription flow record does not exist", "订阅流水记录不存在");
    ErrorCode SUBSCRIBE_EXPIRE = new ErrorCode(125201, "Subscription expired", "订阅已过期");
    ErrorCode BONUS_AMOUNT_NOT_ENOUGH = new ErrorCode(126000,"Guild fails to distribute bonus because of insufficient balance", "公会红包发放金额不足，不满足发放规则");
    ErrorCode LEAGUE_BALANCE_NOT_ENOUGH = new ErrorCode(126002,"The guild has insufficient available balance", "公会可用余额不足");
    ErrorCode NO_USER_MET_CONDITION = new ErrorCode(126003,"No users satisfy the requirements to receive the bonus", "没有用户满足红包发放条件");
    ErrorCode ILLEGAL_OPERATOR = new ErrorCode(126001, "Illegal operator", "非法操作");
    // ========== 邮件模块 1002006000 ==========
    ErrorCode ILLEGAL_PARAMS = new ErrorCode(140000, "parameters cannot be empty:[%s]", "必输参数不能为空");
    ErrorCode ILLEGAL_PARAM = new ErrorCode(140001, "Mandatory parameters cannot be empty", "必输参数不能为空");
    ErrorCode EMAIL_TEMPLATE_NOT_FOUND = new ErrorCode(140002, "Mail template configuration does not exist", "邮件模板配置不存在");
    ErrorCode UNSUPPORTED_EMAIL_TYPE = new ErrorCode(140003, "Not supported for this mail type", "不支持的邮件类型");
    ErrorCode EMAIL_SEND_COUNT_LIMIT = new ErrorCode(140004, "The number of times this mailbox has been sent exceeds the limit for the day", "该邮箱发送次数超过当日限制数");
    ErrorCode EMAIL_IS_LOCK = new ErrorCode(140005, "Too many emails being sent. please try again later", "发送邮件太频繁，请稍后再试");
    ErrorCode SEND_JOURNAL_NOT_EXISTS = new ErrorCode(140006, "Mail delivery record does not exist", "邮件发送流水不存在");
    ErrorCode CODE_EXPIRED = new ErrorCode(140007, "The verification code has expired, please get it again", "未生成邮件验证码或验证码已失效，请重新获取！");
    ErrorCode CODE_WRONG = new ErrorCode(140008, "Incorrect verification. Please try again later", "邮件验证码输入有误，请重新输入！");
    // ========== 短链接模块 1002005000 ==========
    ErrorCode SHORT_URL_CREATE_ERR = new ErrorCode(140009, "Failed to create a link", "创建短链失败");
    ErrorCode SHORT_URL_NOT_EXIST = new ErrorCode(140010, "The link does not exist", "链接不存在");
    ErrorCode SHORT_URL_WRITE_DATA_ERROR = new ErrorCode(140011, "Failed to update the link", "短链接更新失败");
    ErrorCode SHORT_URL_INVALID = new ErrorCode(140012, "The link expires", "链接已失效");


    // ========== 消息模块  ==========
    ErrorCode MESSAGES_NOT_EXISTS = new ErrorCode(149100, "Message not found", "消息不存在");
    ErrorCode MESSAGES_PARAM_ERROR = new ErrorCode(149101, "Message parameter error", "消息参数错误");
    ErrorCode MESSAGES_RECEIVE_NOT_EMPTY = new ErrorCode(149102, "The recipient of the message cannot be empty", "消息接收者不能为空");
    ErrorCode RECOMMENDATION_MYSELF_ILLEGAL = new ErrorCode(149103, "You can't write a recommendation for yourself.", "您不能给自己写推荐报告");
    ErrorCode MESSAGES_TEMPLATE_NOT_FOUND = new ErrorCode(149104, "Message template configuration not available", "消息模板配置不存在");
    ErrorCode MESSAGES_ALREADY_DEAL = new ErrorCode(149105, "Message processed or expired", "消息已处理或已失效");
    ErrorCode MESSAGES_TYPE_ERROR = new ErrorCode(149106, "Message type exception", "消息类型异常");

    // ========== 密钥  ==========
    ErrorCode ACQUIRE_KEY_PARIS_FAIL = new ErrorCode(149200, "Failed to get key", "密钥获取失败");
    ErrorCode SECRET_ILLEGAL_PARAMS = new ErrorCode(149201, "Encryption and decryption required parameters cannot be empty", "加解密必输参数不能为空");
    ErrorCode ENCRYPT_FAIL = new ErrorCode(149202, "Encryption failed", "加密失败");
    ErrorCode DECRYPT_FAIL = new ErrorCode(149203, "Decryption failed", "解密失败");
    ErrorCode PUBLIC_KEY_NOT_EXIST = new ErrorCode(149204, "Public key does not exist", "公钥不存在");
    ErrorCode KeyPairBo_EXPIRE = new ErrorCode(149205, "Key pair has expired", "密钥对已过期");

    ErrorCode RANDOM_EXPIRE = new ErrorCode(149300, "Random has expired", "随机数已过期");
    ErrorCode RANDOM_ERROR = new ErrorCode(149301, "Random error", "随机数错误");
    ErrorCode PASSWORD_VERIFY_ERROR = new ErrorCode(149302, "Password error", "密码错误");


    // ========== 个人简历模块 1002009000 ==========
    ErrorCode RESUME_NOT_EXISTS = new ErrorCode(161001, "Personal profile not found", "个人简历信息不存在");
    ErrorCode RESUME_EXISTS = new ErrorCode(161002, "Personal profile exists", "个人简历信息已存在");
    ErrorCode CONTENT_NOT_EXISTS = new ErrorCode(160003, "The content can't be empty", "内容不能为空");
    ErrorCode RECOMMENDATION_NOT_EXISTS = new ErrorCode(160004, "The recommendation not found", "推荐报告不存在");
    ErrorCode RESUME_INFO_NOT_EXISTS = new ErrorCode(161005, "Personal information not found", "信息不存在");


    // ========== 支付模块 1002007000 ==========
    ErrorCode UNKNOW_HOST = new ErrorCode(130001, "Failed to obtain local IP", "获取本机IP失败");
    ErrorCode UPDATE_USER_ACCOUNT_FAILED = new ErrorCode(130002, "Failed to update the user's balance", "更新用户账户金额失败");
    ErrorCode USER_ACCOUNT_NOT_ENOUGH = new ErrorCode(130003, "Scheduled payment has been cancelled due to insufficient balance, you may top up the money to your wallet.", "您账户余额不足，请充值！");
    ErrorCode ORDER_NOT_EXISTS = new ErrorCode(130004, "The order does not exist, please check the order number", "订单不存在，请检查订单号");
    ErrorCode ENTER_ID_NOT_EXISTS = new ErrorCode(130005, "Failed to transfer because of invalid ID", "入账对象ID不存在，上账失败");
    ErrorCode UPDATE_LEAGUE_ACCOUNT_FAILED = new ErrorCode(130006, "Failed to update guild balance, please check guild's status", "更新公会账户金额失败，请检查公会状态");
    ErrorCode USER_ACCOUNT_NOT_EXISTS = new ErrorCode(130007, "The user not found", "用户账户不存在");
    ErrorCode LEAGUE_ACCOUNT_NOT_EXISTS = new ErrorCode(130008, "The guild not found", "公会账户不存在");
    ErrorCode ORDER_STATUS_ERROR = new ErrorCode(130009, "The order is not initialised, please check the order", "该订单子集不是待分账状态，请检查订单子集");
    ErrorCode IS_NOT_LEAGUE_MEMBER = new ErrorCode(130010, "You are not a member of this guild and unauthorised to operate", "您不是该公会成员，无法进行相关操作");
    ErrorCode RE_PASSWORD_WRONG = new ErrorCode(130011, "Confirm that the password is different from the password", "确认密码与密码不一致");
    ErrorCode OLD_PASSWORD_WRONG = new ErrorCode(130012, "The old password is incorrect", "旧密码不正确");
    ErrorCode EXIST_UNFINISHED_CHARGE_ORDER = new ErrorCode(130013, "You have an order that is being recharged. Please wait until the recharging is complete", "您存在正在充值的订单，请等待充值完成后再操作");
    ErrorCode ALLOCATE_PROCESSING = new ErrorCode(130014, "Separate accounting", "正在分账中");
    ErrorCode UNSUPPORT_PAYER_TYPE = new ErrorCode(130015, "unsuppoert payer type", "不支持的支付方类型");
    ErrorCode ILLEGAL_SPLIT_AMOUNT = new ErrorCode(130016, "The sub-account amount cannot be greater than the remaining sub-account amount of the order", "分账金额不能大于订单可分账金额");
    ErrorCode ILLEGAL_REFUND_AMOUNT = new ErrorCode(130017, "The refund amount cannot be greater than the refundable amount of the order", "退款金额不能大于订单可退款金额");
    ErrorCode MIN_VALUE_ERROR = new ErrorCode(130018, "The minimum amount of calculation is 1 cent", "最小计算金额为1美分");
    ErrorCode PAY_PASSWORD_SIX_NUMBER = new ErrorCode(130019, "Please enter a 6-digit password", "支付密码是六位数的数字");
    ErrorCode BALANCE_NOT_ENOUGH = new ErrorCode(130020, "Your withdrawable balance is insufficient", "您的可提现余额不足");
    ErrorCode CARD_DIFFERENT = new ErrorCode(130021, "The two withdrawal accounts don't match", "两个提现账户不一致");
    ErrorCode PAY_PASSWORD_WRONG = new ErrorCode(130022, "The payment password is incorrect", "支付密码不正确");
    ErrorCode WITHDRAW_MIN_ERROR = new ErrorCode(130023, "The minimum withdrawal amount is $1", "提现金额最少为1美元");
    ErrorCode PAY_PWD_ERROR_COUNT_OVER_LIMIT = new ErrorCode(130024, "Your account has been locked. Please reset your password tapping \"Reset Payment Password\"", "你的支付密码已经被锁定，请通过忘记密码进行重置");
    ErrorCode TEMP_ERROR = new ErrorCode(130025, "Withdrawal suspended due to system maintenance", "钱包提现功能暂时用不了");
    ErrorCode CREATE_ORDER_ERROR = new ErrorCode(130026, "Create order exception", "下单异常");

    // ========== 其他模块 1002003000 ==========

    // ========== 论坛模块 1002007000 ==========
    ErrorCode SORT_RULE_ERR = new ErrorCode(171001, "Collation error", "排序规则错误");
    ErrorCode PLATE_ERR = new ErrorCode(171002, "Plate error", "板块错误");
    ErrorCode POSTS_TITLE_EMPTY = new ErrorCode(171004, "Post title is empty", "贴子标题为空");
    ErrorCode POSTS_CONTENT_EMPTY = new ErrorCode(171005, "Post content is empty", "贴子内容为空");
    ErrorCode MSG_TYPE_ERROR = new ErrorCode(171006, "Message type error", "回复类型错误");
    ErrorCode INFO_NOT_EXIST = new ErrorCode(171007, "Information does not exist", "信息不存在");
    ErrorCode POSTS_NOT_EXIST = new ErrorCode(171010, "Article does not exist", "贴子不存在");
    ErrorCode NO_ACCESS = new ErrorCode(171011, "No access", "无权限");
    ErrorCode POST_DELETED = new ErrorCode(171012, "Post deleted", "贴子已删除");
    ErrorCode CONTENT_DELETED = new ErrorCode(171013, "Content has been deleted", "内容已被删除");
    ErrorCode DISTRICT_NUM_UPPER_LIMIT = new ErrorCode(171014, "The number of partitions reaches the upper limit", "分区数达到上限");


    // ========== 论坛模块 1002007000 ==========
    ErrorCode ONE_MONTH_DATA = new ErrorCode(180001, "单次支持导出一个月数据", "单次支持导出一个月数据");

}
