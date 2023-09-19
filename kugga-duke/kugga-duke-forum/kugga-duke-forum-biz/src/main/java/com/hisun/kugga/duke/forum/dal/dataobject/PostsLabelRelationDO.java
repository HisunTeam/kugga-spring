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
 * 贴子与论坛标签关联表
 * </p>
 *
 * @author zuoCheng
 * @since 2022-11-08
 */
@TableName("duke_posts_label_relation")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostsLabelRelationDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 贴子id
     */
    private Long postsId;

    /**
     * 标签id
     */
    private Long labelId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
