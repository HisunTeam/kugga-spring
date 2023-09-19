package com.hisun.kugga.duke.forum.vo;

import com.hisun.kugga.duke.entity.Content;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author zuocheng
 */
@ApiModel("创建贴子 request VO")
@Data
public class CreatePostsReqVO {
    @ApiModelProperty(value = "贴子标题", required = true, example = "四月是你的谎言")
    @NotBlank(message = "postsTitle field cannot be empty")
    private String postsTitle;

    @ApiModelProperty(value = "贴子内容", required = true, example = "和他相遇的瞬间，我的人生就改变了...")
    @NotEmpty(message = "content field cannot be empty")
    private List<Content> content;

    @ApiModelProperty(value = "板块", required = true, example = "0:公会贴 1:匿名贴")
    @NotBlank(message = "plate field cannot be empty")
    private String plate;

    @ApiModelProperty(value = "组ID plate=0时,对应公会ID", example = "1")
    private Long groupId;

    @ApiModelProperty(value = "热帖扫描开关", example = "true or false")
    private Boolean hotSwitch;

    @ApiModelProperty(value = "分区ID", example = "1")
    private Long districtId;

    @ApiModelProperty(value = "标签信息", example = "1")
    private List<String> labels;
}
