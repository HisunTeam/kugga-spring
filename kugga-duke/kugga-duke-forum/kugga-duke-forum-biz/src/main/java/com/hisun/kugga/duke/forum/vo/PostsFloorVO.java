package com.hisun.kugga.duke.forum.vo;

import com.hisun.kugga.duke.entity.Content;
import com.hisun.kugga.framework.common.pojo.PageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zuocheng
 */
@ApiModel("贴子回复列表  Response VO")
@Data
public class PostsFloorVO {
    @ApiModelProperty(value = "楼层Id", required = true, example = "1")
    private Long floorId;

    @ApiModelProperty(value = "信息ID", required = true, example = "FLOOR123124125152")
    private String msgId;

    @ApiModelProperty(value = "楼层内容", required = true, example = "评论的真好,下次别评论了")
    private List<Content> floorContent;

    @ApiModelProperty(value = "楼层", required = true, example = "1")
    private String floorNum;

    @ApiModelProperty(value = "贴子ID", required = true, example = "1")
    private Long postsId;

    @ApiModelProperty(value = "创建时间", required = true, example = "1661915853000")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", required = true, example = "1661915853000")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "点赞人数", example = "0")
    private Integer praiseNum;

    @ApiModelProperty(value = "点踩人数", example = "0")
    private Integer trampleNum;

    @ApiModelProperty(value = "楼层评论数", example = "0")
    private Integer commentNum;

    @ApiModelProperty(value = "回贴用户ID", example = "1")
    private Long userId;

    @ApiModelProperty(value = "回贴用户头像", example = "/infra/file/get/35a12e57-4297-4faa-bf7d-7ed2f211c952")
    private String avatar;

    @ApiModelProperty(value = "回贴用户名", example = "99duke****")
    private String userName;

    @ApiModelProperty(value = "回贴用户昵称", example = "橘温旧茶")
    private String nickName;

    @ApiModelProperty(value = "回贴用户姓", example = "LiLi")
    private String lastName;

    @ApiModelProperty(value = "回贴用户名", example = "ku")
    private String firstName;

    @ApiModelProperty(value = "用户在当前公会的等级", example = "1")
    private Integer userGrowthLevel;

    @ApiModelProperty(value = "用户在当前公会的等级名称", example = "天使长")
    private String userLevelName;

    @ApiModelProperty(value = "楼主标识(此条回复是否为楼主回复) true, false", example = "false")
    private Boolean landlordFlag;

    @ApiModelProperty(value = "楼层归属标识(是否归属当前登录用户) true, false", example = "false")
    private Boolean belongToFlag;

    @ApiModelProperty(value = "楼层讨论列表")
    private PageResult<CommentVO> pageComments;

    @ApiModelProperty(value = "当前用户对当前楼层点赞状态",  example = "true:已点,false:未点")
    private Boolean userPraiseFlag;

    @ApiModelProperty(value = "当前用户对当前楼层点踩状态",  example = "true:已点,false:未点")
    private Boolean userTrampleFlag;
}
