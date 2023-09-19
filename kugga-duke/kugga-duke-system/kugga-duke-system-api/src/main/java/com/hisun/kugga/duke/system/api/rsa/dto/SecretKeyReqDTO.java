package com.hisun.kugga.duke.system.api.rsa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/6 9:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecretKeyReqDTO {
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 原文密码
     */
    private String password;

    /**
     * 加密密文
     */
    private String encodePwd;

    private String flag;

}
