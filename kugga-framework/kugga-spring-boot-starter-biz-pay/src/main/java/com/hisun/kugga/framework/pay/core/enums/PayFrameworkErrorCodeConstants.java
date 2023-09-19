package com.hisun.kugga.framework.pay.core.enums;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.framework.common.exception.ErrorCode;
import com.hisun.kugga.framework.common.exception.ExternalErrorCode;

import java.util.stream.Stream;

/**
 * 支付框架的错误码枚举
 * <p>
 * 短信框架，使用 2-002-000-000 段
 *
 * @author 芋道源码
 */
public interface PayFrameworkErrorCodeConstants {

    ErrorCode PAY_UNKNOWN = new ErrorCode(2002000000, "未知错误，需要解析");

    // ========== 配置相关相关 2002000100 ==========
    ErrorCode PAY_CONFIG_APP_ID_ERROR = new ErrorCode(2002000100, "支付渠道 AppId 不正确");
    ErrorCode PAY_CONFIG_SIGN_ERROR = new ErrorCode(2002000100, "签名错误"); // 例如说，微信支付，配置错了 mchId 或者 mchKey


    // ========== 其它相关 2002000900 开头 ==========
    ErrorCode PAY_OPENID_ERROR = new ErrorCode(2002000900, "无效的 openid"); // 例如说，微信 openid 未授权过
    ErrorCode PAY_PARAM_MISSING = new ErrorCode(2002000901, "请求参数缺失"); // 例如说，支付少传了金额

    ErrorCode EXCEPTION = new ErrorCode(2002000999, "调用异常");

    ExternalErrorCode CALL_WALLET_FAILED = new ExternalErrorCode("131111", 131111, "Failed to invoke the interface of wallet", "调用钱包API失败");
    ErrorCode URL_NOT_EXISTS = new ErrorCode(131112, "Invalid request address", "钱包URL错误，请配置kugga.pay.wallet.urls");
    ErrorCode SIGN_FAILED = new ErrorCode(131113, "Failed to generate a signature", "生成签名失败");
    ErrorCode SIGN_VERIFY_FAILED = new ErrorCode(131114, "Signature verification failed", "验签失败");

    ErrorCode WLET0001 = new ExternalErrorCode("WLET0001", 13115, "appid cannot be empty", "合作方应用id不能为空");
    ErrorCode WLET0002 = new ExternalErrorCode("WLET0002", 13115, "Callback url cannot be empty", "回调通知地址不能为空");
    ErrorCode WLET0003 = new ExternalErrorCode("WLET0003", 13115, "Insert failed", "插入失败");
    ErrorCode WLET0004 = new ExternalErrorCode("WLET0004", 13115, "Signature verification failed", "验签失败");
    ErrorCode WLET0005 = new ExternalErrorCode("WLET0005", 13115, "Update failed", "更新失败");
    ErrorCode WLET0011 = new ExternalErrorCode("WLET0011", 13115, "Account does not exist", "账户不存在");
    ErrorCode WLET0012 = new ExternalErrorCode("WLET0012", 13115, "Reprocessing", "重复处理");
    ErrorCode WLET0013 = new ExternalErrorCode("WLET0013", 13115, "Abnormal state", "状态异常");
    ErrorCode WLET0014 = new ExternalErrorCode("WLET0014", 13115, "Insufficient account balance", "账户余额不足");
    ErrorCode WLET0015 = new ExternalErrorCode("WLET0015", 13115, "The transaction amount must be greater than 0", "交易金额必须大于0");
    ErrorCode WLET0016 = new ExternalErrorCode("WLET0016", 13115, "The amount cannot be empty", "金额不能为空");
    ErrorCode WLET0017 = new ExternalErrorCode("WLET0017", 13115, "The body parameter cannot be empty", "参数body不能为空");
    ErrorCode WLET0018 = new ExternalErrorCode("WLET0018", 13115, "Failed to withdraw money to PayPal", "执行paypal提现失败");
    ErrorCode WLET0019 = new ExternalErrorCode("WLET0019", 13115, "The Email address of the paypal account for cash withdrawal cannot be empty", "提现的paypal账户的Email不能为空");
    ErrorCode WLET0020 = new ExternalErrorCode("WLET0020", 13115, "The transaction amount must not be too small and must meet the minimum amount limit", "交易金额不能太小, 必须满足最低额限制");
    ErrorCode WLET0051 = new ExternalErrorCode("WLET0051", 13115, "The allotment amount cannot be empty", "分账金额不能为空");
    ErrorCode WLET0052 = new ExternalErrorCode("WLET0052", 13115, "The account of the ledger recipient cannot be empty", "分账接收方账号不能为空");
    ErrorCode WLET0053 = new ExternalErrorCode("WLET0053", 13115, "The ledger record number cannot be blank", "分账记录编号不能为空");
    ErrorCode WLET0054 = new ExternalErrorCode("WLET0054", 13115, "The order is already in the ledger for processing", "该订单已存在处理中的分账记录");
    ErrorCode WLET0055 = new ExternalErrorCode("WLET0055", 13115, "The divisible balance of the order is insufficient", "该订单可分账余额不足");
    ErrorCode WLET0056 = new ExternalErrorCode("WLET0056", 13115, "Recipient account does not exist", "分账接收人账户不存在");
    ErrorCode WLET0057 = new ExternalErrorCode("WLET0057", 13115, "The order is not separable", "该订单不可分账");
    ErrorCode WLET0058 = new ExternalErrorCode("WLET0058", 13115, "The ledger receiver list cannot be empty", "分账接收方列表不能为空");
    ErrorCode WLET0101 = new ExternalErrorCode("WLET0101", 13115, "The account cannot be empty", "账号不能为空");
    ErrorCode WLET0102 = new ExternalErrorCode("WLET0102", 13115, "Unable to complete payment. Please check your account balance.", "动账失败");
    ErrorCode WLET0151 = new ExternalErrorCode("WLET0151", 13115, "The order number cannot be empty", "订单号不能为空");
    ErrorCode WLET0152 = new ExternalErrorCode("WLET0152", 13115, "The order type cannot be empty", "订单类型不能为空");
    ErrorCode WLET0153 = new ExternalErrorCode("WLET0153", 13115, "Order does not exist", "订单不存在");
    ErrorCode WLET0201 = new ExternalErrorCode("WLET0201", 13115, "The refund amount cannot be empty", "退款金额不能为空");
    ErrorCode WLET0202 = new ExternalErrorCode("WLET0202", 13115, "The refund record number cannot be empty", "退款记录编号不能为空");
    ErrorCode WLET0203 = new ExternalErrorCode("WLET0203", 13115, "Splitting has started, partial refund is not supported", "已开始分账, 不支持部分退款");
    ErrorCode WLET0204 = new ExternalErrorCode("WLET0204", 13115, "In the process of distribution, refund is not supported", "正在分账中, 不支持退款");
    ErrorCode WLET0205 = new ExternalErrorCode("WLET0205", 13115, "Refunds are being processed. Repeated refunds are not supported", "正在处理退款之中, 不支持重复发起退款");
    ErrorCode WLET0206 = new ExternalErrorCode("WLET0206", 13115, "The refundable amount is not enough", "可退款金额不够");
    ErrorCode WLET0252 = new ExternalErrorCode("WLET0252", 13115, "The start date of the reconciliation application cannot be later than the end date", "对账申请开始日期不能晚于结束日期");
    ErrorCode WLET0253 = new ExternalErrorCode("WLET0253", 13115, "The start date cannot be empty", "开始日期不能为空");
    ErrorCode WLET0254 = new ExternalErrorCode("WLET0254", 13115, "The end date cannot be empty", "结束日期不能为空");
    ErrorCode WLET0255 = new ExternalErrorCode("WLET0255", 13115, "The query account cannot be empty", "查询账号不能为空");
    ErrorCode WLET0256 = new ExternalErrorCode("WLET0256", 13115, "The statement request cannot be repeated", "对账单申请不能重复");
    ErrorCode WLET0401 = new ExternalErrorCode("WLET0401", 13115, "Red envelopes don't exist", "红包不存在");
    ErrorCode WLET0402 = new ExternalErrorCode("WLET0402", 13115, "The recipient of the red envelope does not exist", "红包发放对象不存在");
    ErrorCode WLET0403 = new ExternalErrorCode("WLET0403", 13115, "The amount in the red envelope cannot be empty", "红包金额不能为空");
    ErrorCode WLET0404 = new ExternalErrorCode("WLET0404", 13115, "The amount of red packets does not match", "红包金额不匹配");


    static ErrorCode codeEscape(String externalCode) {
        Object[] fieldsValue = ReflectUtil.getFieldsValue(PayFrameworkErrorCodeConstants.class);
        return Stream.of(fieldsValue).filter(obj -> obj instanceof ExternalErrorCode)
                .map(ExternalErrorCode.class::cast)
                .filter(externalErrorCode -> StrUtil.equals(externalErrorCode.getExternalCode(), externalCode))
                .findFirst().orElse(CALL_WALLET_FAILED);
    }
}
