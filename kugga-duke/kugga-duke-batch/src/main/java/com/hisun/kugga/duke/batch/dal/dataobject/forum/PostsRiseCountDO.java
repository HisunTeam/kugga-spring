package com.hisun.kugga.duke.batch.dal.dataobject.forum;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * Post Rise Count Table
 * </p>
 *
 * @author zuocheng
 * @since 2022-08-29 09:33:27
 */
@TableName("duke_posts_rise_count")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostsRiseCountDO {
    /**
     * Floor ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Post ID, consistent with duke_posts id
     */
    private Long postsId;

    /**
     * Count
     */
    private Integer num;

    /**
     * Creation time
     */
    private LocalDateTime createTime;
}
