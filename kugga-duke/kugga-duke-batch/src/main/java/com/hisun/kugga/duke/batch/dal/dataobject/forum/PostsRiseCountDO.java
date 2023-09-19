package com.hisun.kugga.duke.batch.dal.dataobject.forum;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 贴子上升计数表
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
     * 楼层ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 贴子ID 与duke_posts id一致
     */
    private Long postsId;

    /**
     * 计数
     */
    private Integer num;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
