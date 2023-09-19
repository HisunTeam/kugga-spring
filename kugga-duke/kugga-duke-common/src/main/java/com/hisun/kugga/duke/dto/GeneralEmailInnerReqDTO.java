package com.hisun.kugga.duke.dto;

import com.hisun.kugga.duke.enums.email.EmailScene;
import com.hisun.kugga.duke.innercall.InnerCallReqDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.hisun.kugga.duke.common.CommonConstants.EN_US;

/**
 * @Description: 发送邮件通用dto (内部使用)
 * @author： Lin
 * @Date 2022/10/25 9:55
 */
@ApiModel("发送邮件通用dto")
@Data
@ToString
public class GeneralEmailInnerReqDTO extends InnerCallReqDTO {
    @ApiModelProperty(value = "业务流水id", required = true)
    @NotNull(message = "业务流水id不能为空")
    private Long businessId;

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
