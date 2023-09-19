package com.hisun.kugga.duke.system.api.rsa.dto;

import com.hisun.kugga.duke.enums.SecretTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/8 9:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecryptDTO {
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 密文密码
     */
    private String password;
    /**
     * 场景类型 LOGIN、PAY
     */
    private SecretTypeEnum type;
}
