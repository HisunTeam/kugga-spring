package com.hisun.kugga.framework.pay.core.client.impl.wallet.config;

import com.hisun.kugga.framework.pay.core.enums.SignType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author: zhou_xiong
 */
@Data
@ConfigurationProperties(prefix = "kugga.pay.wallet")
public class WalletProperties {
    private String appid;
    private String appSecret;
    private Boolean shouldSign;
    private SignType signType;
    private Map<String, String> urls;
}
