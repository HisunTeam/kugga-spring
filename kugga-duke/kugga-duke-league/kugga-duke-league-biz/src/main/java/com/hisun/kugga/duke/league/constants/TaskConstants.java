package com.hisun.kugga.duke.league.constants;

import com.hisun.kugga.framework.common.exception.ErrorCode;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-27 19:38
 */
public interface TaskConstants {
    ErrorCode TASK_PARAM_ERROR = new ErrorCode(120000, "The task type parameters error", "任务类型参数错误，没有此类型任务");

    ErrorCode NOT_EXPECT_METHOD = new ErrorCode(120020, "Not the expected method, exception method has been executed", "不是预期方法 执行到异常方法");
    ErrorCode LEAGUE_INFO_EMPTY = new ErrorCode(120021, "Guild parameters missing", "公会参数缺失");
    ErrorCode TASK_CREATE_FAILED = new ErrorCode(120022, "Failed to add new item", "新增任务失败");
    ErrorCode TASK_AMOUNT_ERROR = new ErrorCode(120023, "The total amount was calculated incorrectly, please recalculate", "任务总计金额计算错误，请重新计算");
    ErrorCode TASK_NOT_COMPLETE = new ErrorCode(120024, "Incomplete mission parameters", "任务参数不全");
    ErrorCode NOT_NULL_LEAGUE_ID_AMOUNT = new ErrorCode(120025, "Guild's ID and payment amount cannot be empty", "公会ID和金额不能为空");
    ErrorCode LEAGUE_NOT_EXIST = new ErrorCode(120026, "The guild does not exist", "公会不存在");
    ErrorCode NOT_EXPECT_PRICE = new ErrorCode(120027, "Realtime prices have changed, please refresh the page", "实时价格已变动，请刷新");
    ErrorCode WAIT_TARGET_AGREE_CHAT = new ErrorCode(120028, "This order already exists. Please wait for the user to confirm", "该订单已存在，请等待用户同意聊天");
    ErrorCode NOT_ACCEPT = new ErrorCode(120029, "Can't write a recommendation for yourself", "不能给自己写推荐报告");

    ErrorCode ONE_MONTH_REPETITION_WRITE_REPORT = new ErrorCode(120030, "You can only take this order once within one month", "一个月内不能重复接单 oneMonthRepetitionWriteReport");
    ErrorCode LEAGUE_RULE_ERROR = new ErrorCode(120031, "Guild and guild rules mapping anomalies", "公会和公会规则映射异常");
    ErrorCode JUDGE_PRICE_ERROR = new ErrorCode(120032, "Price anomalies", "判断价格异常");
    ErrorCode NOT_ONESELF_CHAT = new ErrorCode(120033, "Not allowed to send message to yourself", "不能与自己聊天");
    ErrorCode HAS_CONSENT_CHAT = new ErrorCode(120034, "Invitation was already accepted", "已经同意聊天");
    ErrorCode CHAT_APPLY_PAST = new ErrorCode(120035, "Communication request expired", "聊天申请已过期");
    ErrorCode NOT_COMMIT_REPORT_FINISH = new ErrorCode(120036, "This recommendation has been completed, please submit only once", "该推荐报告已完成，请勿重复提交");
    ErrorCode LEAGUE_IS_NULL = new ErrorCode(120037, "The guild doesn’t exist", "该公会不存在");
    ErrorCode LEAGUE_HAS_AUTH = new ErrorCode(120038, "The guild is endorsed", "该公会已认证");
    ErrorCode NOTICE_EXIST_USER = new ErrorCode(120039, "This recommendation has been written", "该推荐报告已有人接单");

    ErrorCode NOTICE_FINISH = new ErrorCode(120040, " This announcement has been completed, please submit only once", "该公告已完成，请勿重复提交");
    ErrorCode QUERY_NOTICE_NOT_IN_LEAGUE = new ErrorCode(120041, "Only members of the guild can view the guild announcements", "非本公会成员不可查询公会公告");
    ErrorCode QUERY_RULE_NOT_IN_LEAGUE = new ErrorCode(120042, "Only members of the guild can view the rules of the guild", "非本公会管理员不可查询公会规则");
    ErrorCode NOT_ACCEPT_TIME_OUT_WRITE_REPORT = new ErrorCode(120043, "The invitation to write a recommendation has expired", "不能接单推荐报告已超时");
    ErrorCode NOT_ACCEPT_TIME_OUT_LEAGUE_AUTH = new ErrorCode(120044, "The invitation to provide an endorsement for the guild has expired", "不能接单推荐报告已超时");
    ErrorCode LEAGUE_CLOSE_AUTH = new ErrorCode(120045, "Guilds can't provide endorsement for other guilds with the endorsing function turned off", "公会关闭了认证功能无法帮其他公会认证");
    ErrorCode BY_CHAT_USER_ID_NULL = new ErrorCode(120046, "You cannot initiate a chat invitation with an empty ID", "被聊天用户ID不能为空");
    ErrorCode LEAGUE_RULE_NO_EXIST = new ErrorCode(120047, "The guild does not exist or guild rules do not exist", "公会不存在或公会规则不存在");
    ErrorCode REPORT_INVITE_LEAGUE_LIST_EMPTY = new ErrorCode(120048, "", "写推荐报告邀请选择的公会列表不能为空");
    ErrorCode LEAGUE_AUTH_INVITE_LIST_EMPTY = new ErrorCode(120049, "", "公会认证邀请选择的公会列表不能为空");

    ErrorCode LEAGUE_NOT_AUTH = new ErrorCode(120050, "The guild has not been endorsed", "公会没有被认证");
    ErrorCode temp_zay0002 = new ErrorCode(12051, "You can't invite members to provide recommendations, because the guild has not been endorsed", "公会没有认证无法被邀请写推荐报告");
    ErrorCode temp_zay0003 = new ErrorCode(120052, "You can't invite members to provide an endorsement, because the guild has not been endorsed", "公会没有认证无法被邀请公会认证");
    ErrorCode LEAGUE_NOT_AUTH_NO_CHAT = new ErrorCode(120053, "You can't initiate a chat in this guild, because the guild has not been endorsed", "公会没有被认证无法通过此公会聊天");
    ErrorCode REQUEST_PARAMETERS_MISSING = new ErrorCode(120054, "Request parameters are missing", "请求参数缺失");
    ErrorCode NOTICE_IS_DELETE = new ErrorCode(120055, "This notice has been removed", "该公告已被删除");
    ErrorCode LEAGUE_AUTH_IS_DELETE = new ErrorCode(120056, "发出的公会邀请都已被删除", "发出的公会邀请都已被删除");
    ErrorCode REQUEST_PARAMETER_ILLEGAL = new ErrorCode(120057, "request parameter illegal :[%s]", "请求参数非法");
    ErrorCode REQUIRED_PARAMETER_IS_EMPTY = new ErrorCode(120058, "The required parameter cannot be empty", "必填参数不能为空");
    ErrorCode GUILD_NAME_NOT_UNIQUE = new ErrorCode(120059, "The guild name must be unique", "公会名称不能重复");

    ErrorCode USER_NOT_NOW = new ErrorCode(120060, "The task initiator must be the current log-in", "任务发起者必须是当前登录人");
    ErrorCode ORDER_NOT_EXISTS = new ErrorCode(120061, "This order not exists:[%s]", "订单不存在");
    ErrorCode ORDER_HAS_BEEN_PAID = new ErrorCode(120062, "Don't pay again, This order has already been paid", "该订单已支付，请勿重复支付");
    ErrorCode TASK_UPDATE_EXCEPTION = new ErrorCode(120063, "Task update exception", "任务更新异常");
    ErrorCode TASK_LEAGUE_AUTH_LOCK = new ErrorCode(120064, "Guild can't be endorsed right now because system is busy. Please try again later", "公会认证忙 请稍后再试");
    ErrorCode ORDER_NUMBER_IS_EMPTY = new ErrorCode(120065, "The order number is empty", "订单号为空！");

    ErrorCode LEAGUE_CREATE_ACCOUNT_FAIL = new ErrorCode(120065, "The league create account fail", "公会账户创建失败");
    ErrorCode chat_order_update_exception = new ErrorCode(120065, "The chat order update exception", "聊天订单更新异常");
}
