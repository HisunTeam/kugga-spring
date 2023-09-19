package com.hisun.kugga.duke.forum.vo;

import com.hisun.kugga.duke.entity.Content;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zuocheng
 */
@ApiModel("更新贴子 request VO")
@Data
public class UpdatePostsReqVO {
    @ApiModelProperty(value = "贴子ID", required = true, example = "1")
    @NotNull(message = "贴子ID不能为空")
    private Long postsId;

    @ApiModelProperty(value = "贴子标题", example = "四月是你的谎言")
    private String postsTitle;

    @ApiModelProperty(value = "贴子内容", example = "和他相遇的瞬间，我的人生就改变了...")
    private List<Content> postsContent;

    @ApiModelProperty(value = "是否允许被热贴检索", example = "false:不允许 true:允许")
    private Boolean hotSearchSwitch;

    @ApiModelProperty(value = "分区ID", example = "1")
    private Long districtId;
}
