package com.hisun.kugga.duke.system.api.email.dto;

import com.hisun.kugga.duke.enums.email.EmailScene;
import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class SendCodeReqDTO {
    /**
     * 发邮件场景
     */
    private EmailScene emailScene;
    /**
     * 接收人
     */
    private String to;
    /**
     * 国际化标识
     */
    private String locale = "zh_cn";

}
