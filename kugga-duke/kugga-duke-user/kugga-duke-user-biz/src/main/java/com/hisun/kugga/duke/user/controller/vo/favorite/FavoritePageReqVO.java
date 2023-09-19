package com.hisun.kugga.duke.user.controller.vo.favorite;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("收藏分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FavoritePageReqVO extends PageParam {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "内容id 工会、推荐报告id..")
    private String contentId;

    @ApiModelProperty(value = "收藏类型 G-工会 T-推荐报告")
    private String type;

    @ApiModelProperty(value = "认证类型 G-工会认证 T-推荐报告")
    private String authType;

    @ApiModelProperty(value = "分组Id")
    private Long groupId;
}
