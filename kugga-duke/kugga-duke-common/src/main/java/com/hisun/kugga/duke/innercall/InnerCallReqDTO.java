package com.hisun.kugga.duke.innercall;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: zhou_xiong
 */
@Data
public class InnerCallReqDTO {
    /**
     * 请求凭证
     */
    @ApiModelProperty(value = "请求凭证", required = true)
    @NotNull(message = "uuid can not be empty")
    private String uuid;
}
