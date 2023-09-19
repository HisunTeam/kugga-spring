package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zuocheng
 */
@ApiModel("修改（修改、新增、删除）分区 request VO")
@Data
public class UpdateDistrictReqVO {
    @ApiModelProperty(value = "论坛ID", required = true, example = "1(公会论坛时传公会ID,匿名论坛时传0)")
    @NotNull(message = "forumId not is null")
    private Long forumId;

    @ApiModelProperty(value = "分区列表", required = true)
    @NotEmpty(message = "districts not is empty")
    private List<ForumDistrictVO> districts;
}
