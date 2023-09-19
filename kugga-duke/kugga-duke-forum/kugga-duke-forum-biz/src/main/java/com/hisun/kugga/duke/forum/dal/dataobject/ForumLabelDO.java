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
 * 论坛标签标签信息表
 * </p>
 *
 * @author zuoCheng
 * @since 2022-11-08
 */
@TableName("duke_forum_label")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForumLabelDO {
    /**
     * 标签ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 热度数
     */
    private Long hotNum;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
