package com.hisun.kugga.duke.bos.controller.admin.recommendation.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecommendationReqVO extends PageParam {
    @ApiModelProperty(value = "用户名", required = true, example = "1")
    private String username;

    @ApiModelProperty(value = "邮箱", example = "123@qq.com")
    private String email;

    @ApiModelProperty(value = "姓", example = "99")
    private String lastName;

    @ApiModelProperty(value = "名", example = "100")
    private String firstName;

}
