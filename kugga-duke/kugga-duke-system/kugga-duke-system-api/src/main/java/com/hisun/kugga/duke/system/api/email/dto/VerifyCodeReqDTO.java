package com.hisun.kugga.duke.system.api.email.dto;

import com.hisun.kugga.duke.enums.email.EmailScene;
import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class VerifyCodeReqDTO {
    /**
     * 发邮件场景
     */
    private EmailScene emailScene;
    /**
     * 接收验证码的邮箱
     */
    private String email;
    /**
     * 验证码
     */
    private String verifyCode;

}
