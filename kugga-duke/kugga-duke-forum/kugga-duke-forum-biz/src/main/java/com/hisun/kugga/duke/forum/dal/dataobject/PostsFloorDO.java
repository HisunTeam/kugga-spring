package com.hisun.kugga.duke.forum.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 贴子回复楼层表
 * </p>
 *
 * @author zuocheng
 * @since 2022-08-29 09:33:27
 */
@TableName("duke_posts_floor")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostsFloorDO {

    /**
     * 楼层ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 信息ID
     */
    private String msgId;

    /**
     * 贴子ID 与duke_posts id一致
     */
    private Long postsId;

    /**
     * 楼层数
     */
    private Integer floorNum;

    /**
     * 评论者id
     */
    private Long userId;

    /**
     * 回复人ip
     */
    private String userIp;

    /**
     * 是否为楼主回复 true:是 false:否
     */
    private Boolean landlordFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 点赞人数
     */
    private Integer praiseNum;

    /**
     * 点踩人数
     */
    private Integer trampleNum;

    /**
     * 热度
     */
    private Integer hotNum;

    /**
     * 备注信息
     */
    private String rmk;

    /**
     * 更新者(管理员处理贴子时存在值)
     */
    private String updater;

    /**
     * 删除状态 true:已删除 false:未删除
     */
    @TableLogic
    private Boolean deleted;
}
