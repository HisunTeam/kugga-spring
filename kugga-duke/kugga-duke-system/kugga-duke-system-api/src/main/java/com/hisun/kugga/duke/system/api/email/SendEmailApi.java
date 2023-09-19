package com.hisun.kugga.duke.system.api.email;

import com.hisun.kugga.duke.system.api.email.dto.GeneralEmailReqDTO;
import com.hisun.kugga.duke.system.api.email.dto.SendCodeReqDTO;
import com.hisun.kugga.duke.system.api.email.dto.VerifyCodeReqDTO;

/**
 * @author: zhou_xiong
 */
public interface SendEmailApi {
    /**
     * 根据不同场景发送邮件验证码
     *
     * @param sendCodeReqDTO
     */
    void sendCode(SendCodeReqDTO sendCodeReqDTO);

    /**
     * 校验邮件验证码
     *
     * @param verifyCodeReqDTO
     * @return
     */
    boolean verifyCode(VerifyCodeReqDTO verifyCodeReqDTO);

    /**
     * 除验证码场景以外的其他通用发邮件场景，需提供模板替换值
     *
     * @param generalEmailReqDTO
     */
    void sendEmail(GeneralEmailReqDTO generalEmailReqDTO);
}
