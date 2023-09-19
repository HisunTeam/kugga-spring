package com.hisun.kugga.duke.user.dal.dataobject.favoritegroup;

import lombok.*;

import com.baomidou.mybatisplus.annotation.*;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;

/**
 * 收藏分组 DO
 *
 * @author lzt
 */
@TableName("duke_favorite_group")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteGroupDO extends BaseDO {

    /**
     * 分组id
     */
    @TableId
    private Long id;
    /**
     * 分区名称
     */
    private String groupName;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * G-公会 T-推荐信
     */
    private String type;

}
