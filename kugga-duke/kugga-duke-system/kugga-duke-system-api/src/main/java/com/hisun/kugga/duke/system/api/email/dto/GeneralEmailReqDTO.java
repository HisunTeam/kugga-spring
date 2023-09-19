package com.hisun.kugga.duke.system.api.email.dto;

import com.hisun.kugga.duke.enums.email.EmailScene;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.hisun.kugga.duke.common.CommonConstants.EN_US;

/**
 * @author: zhou_xiong
 */
@Data
public class GeneralEmailReqDTO {
    /**
     * 发送场景
     */
    private EmailScene emailScene;
    /**
     * 发送对象
     */
    private List<String> to;
    /**
     * 邮件模板替换值
     */
    private ArrayList<String> replaceValues;
    /**
     * 国际化标识
     */
    private String locale = EN_US;

}
