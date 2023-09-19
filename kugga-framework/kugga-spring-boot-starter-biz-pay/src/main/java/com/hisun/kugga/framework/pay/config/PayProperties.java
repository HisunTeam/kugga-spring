package com.hisun.kugga.framework.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "kugga.pay")
@Validated
@Data
public class PayProperties {

    /**
     * 支付回调地址
     * 注意，支付渠道统一回调到 payNotifyUrl 地址，由支付模块统一处理；然后，自己的支付模块，在回调 PayAppDO.payNotifyUrl 地址
     */
    private String payNotifyUrl;
    /**
     * 退款回调地址
     * 注意点，同 {@link #payNotifyUrl} 属性
     */
    private String refundNotifyUrl;


    /**
     * 支付完成的返回地址
     */
    private String payReturnUrl;

}
