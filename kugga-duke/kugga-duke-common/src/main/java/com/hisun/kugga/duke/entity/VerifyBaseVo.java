package com.hisun.kugga.duke.entity;

import lombok.Data;

/**
 * @Description: 密码校验的公共接口
 * @author： Lin
 * @Date 2022/9/7 16:48
 */
@Data
public class VerifyBaseVo {
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 密码
     */
//    private String password;
}
