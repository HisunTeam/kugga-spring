package com.hisun.kugga.duke.forum.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 贴子收藏表
 * </p>
 *
 * @author zuoCheng
 * @since 2022-11-11
 */
@TableName("duke_posts_collection")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostsCollectionDO {

    /**
     * 收藏ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 被收藏的贴子ID
     */
    private Long postsId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
