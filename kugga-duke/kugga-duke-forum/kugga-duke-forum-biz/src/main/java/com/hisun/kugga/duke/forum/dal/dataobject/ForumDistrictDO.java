package com.hisun.kugga.duke.forum.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 论坛分区表
 * </p>
 *
 * @author zuoCheng
 * @since 2022-10-17
 */
@TableName("duke_forum_district")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForumDistrictDO {

    /**
     * 贴子id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 论坛ID
     */
    private Long forumId;

    /**
     * 分区名称
     */
    private String districtName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
