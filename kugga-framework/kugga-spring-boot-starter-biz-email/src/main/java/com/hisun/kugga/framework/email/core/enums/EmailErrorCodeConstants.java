package com.hisun.kugga.framework.email.core.enums;

import com.hisun.kugga.framework.common.exception.ErrorCode;

/**
 * @author: zhou_xiong
 */
public interface EmailErrorCodeConstants {
    // ========== 邮件相关 1002004000 ==========
    ErrorCode MAIL_SEND_FAILED = new ErrorCode(149001, "e-mail sending failed");
}
