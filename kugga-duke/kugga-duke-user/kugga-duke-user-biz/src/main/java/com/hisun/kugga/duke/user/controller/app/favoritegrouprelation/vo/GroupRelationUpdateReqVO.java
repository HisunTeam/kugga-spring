package com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("分组关联更新 Request VO")
@Data
@ToString(callSuper = true)
public class GroupRelationUpdateReqVO  {


    @ApiModelProperty(value = "收藏id", required = true)
    private Long favoriteId;

    @ApiModelProperty(value = "G-公会 T-推荐信 P-帖子", required = true)
    @NotNull(message = "G-公会 T-推荐信 P-帖子 不能为空")
    private String type;

    @ApiModelProperty(value = "内容id 工会、推荐报告id..", required = true)
    @NotNull(message = "Content ID cannot be empty")
    private Long contentId;

    @ApiModelProperty(value = "分组id列表")
    private List<Long> groupIds;

    @ApiModelProperty(value = "收藏推荐报告时所在公会id", required = false)
    private Long favoriteLeagueId;
}
