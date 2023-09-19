package com.hisun.kugga.duke.forum.vo;

import com.hisun.kugga.duke.entity.Content;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 热贴信息
 *
 * @author zuocheng
 */
@ApiModel("热贴信息 VO")
@Data
public class HotPostsVO {
    @ApiModelProperty(value = "贴子ID", required = true, example = "1")
    private Long postsId;

    @ApiModelProperty(value = "信息ID", required = true, example = "POSTS123124125152")
    private String msgId;

    @ApiModelProperty(value = "板块", required = true, example = "0:公会贴 其它待定")
    private String plate;

    @ApiModelProperty(value = "贴子所属组ID", example = "plate=0时,为公会ID")
    private Long groupId;

    @ApiModelProperty(value = "贴子标题", required = true, example = "四月是你的谎言")
    private String postsTitle;

    @ApiModelProperty(value = "贴子内容(缩减版)", required = true, example = "和他相遇的瞬间，我的人生就改变了...")
    private List<Content> postsContent;

    @ApiModelProperty(value = "创建时间", required = true, example = "1661915853000")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", required = true, example = "1661915853000")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "最新回复时间", required = true, example = "1661915853000")
    private LocalDateTime replyTime;

    @ApiModelProperty(value = "楼层数", example = "0")
    private Integer floorCount;

    @ApiModelProperty(value = "点赞人数", example = "0")
    private Integer praiseNum;

    @ApiModelProperty(value = "点踩人数", example = "0")
    private Integer trampleNum;

    @ApiModelProperty(value = "收藏数", example = "0")
    private Integer collectNum;

    @ApiModelProperty(value = "分享数", example = "0")
    private Integer shareNum;

    @ApiModelProperty(value = "热度", example = "0")
    private Integer hotNum;

    @ApiModelProperty(value = "评论数(包含楼层与楼层中的讨论)", example = "0")
    private Integer commentNum;

    @ApiModelProperty(value = "访问量", example = "0")
    private Integer clickNum;

    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;

    @ApiModelProperty(value = "用户头像", example = "/infra/file/get/35a12e57-4297-4faa-bf7d-7ed2f211c952")
    private String avatar;

    @ApiModelProperty(value = "用户名", example = "99duke****")
    private String userName;

    @ApiModelProperty(value = "昵称", example = "橘温旧茶")
    private String nickName;

    @ApiModelProperty(value = "姓", example = "LiLi")
    private String lastName;

    @ApiModelProperty(value = "名", example = "ku")
    private String firstName;

    @ApiModelProperty(value = "贴子图片列表(用于帖子列表展示,至多展示3个)")
    private List<String> images;

    @ApiModelProperty(value = "贴子接所函图片总数量")
    private Integer imagesNum;

    @ApiModelProperty(value = "标签列表")
    private List<LabelVO> labels;

    @ApiModelProperty(value = "当前用户对当前贴子点赞状态",  example = "true:已点,false:未点")
    private Boolean userPraiseFlag;

    @ApiModelProperty(value = "当前用户对当前贴子点踩状态",  example = "true:已点,false:未点")
    private Boolean userTrampleFlag;

    @ApiModelProperty(value = "当前用户对当前贴子收藏状态",  example = "true:已点,false:未点")
    private Boolean userCollectionFlag;
}
