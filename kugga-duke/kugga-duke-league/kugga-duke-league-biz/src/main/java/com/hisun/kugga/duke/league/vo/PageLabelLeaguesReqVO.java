package com.hisun.kugga.duke.league.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zuocheng
 */
@ApiModel("分页查询标签公会列表 Request VO")
@Data
public class PageLabelLeaguesReqVO extends PageParam {
    @ApiModelProperty(value = "标签值", required = true, example = "1")
    @NotBlank(message = "标签值不能为空")
    private String labelValue;
}
