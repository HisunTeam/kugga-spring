package com.hisun.kugga.duke.user.controller.vo.favorite;

import com.hisun.kugga.duke.entity.Content;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel("收藏 Response VO")
@Data
@ToString(callSuper = true)
public class FavoriteRespVO {

    @ApiModelProperty(value = "收藏id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "内容id 工会、推荐报告id..")
    private Long contentId;

    @ApiModelProperty(value = "收藏类型 G-工会 T-推荐报告")
    private String avatar;

    @ApiModelProperty(value = "公会名称")
    private String leagueName;

    @ApiModelProperty(value = "公会认证价格")
    private BigDecimal price;

    @ApiModelProperty(value = "姓", example = "史蒂夫")
    private String lastName;

    @ApiModelProperty(value = "名", example = "乔布斯")
    private String firstName;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "推荐信内容")
    private List<Content> contents;

    @ApiModelProperty(value = "推荐人id")
    private String recommenderId;

    @ApiModelProperty(value = "被推荐人id")
    private String recommendedId;

    @ApiModelProperty(value = "推荐人公会")
    private String recommenderLeagueId;

    @ApiModelProperty(value = "收藏推荐报告时所在公会id")
    private Long favoriteLeagueId;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}
