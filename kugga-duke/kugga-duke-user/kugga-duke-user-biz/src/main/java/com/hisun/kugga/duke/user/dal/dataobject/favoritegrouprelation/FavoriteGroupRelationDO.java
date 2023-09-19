package com.hisun.kugga.duke.user.dal.dataobject.favoritegrouprelation;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;

/**
 * 收藏分组关联 DO
 *
 * @author 芋道源码
 */
@TableName("duke_favorite_group_relation")
@KeySequence("duke_favorite_group_relation_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteGroupRelationDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 分组id
     */
    private Long groupId;
    /**
     * 收藏id
     */
    private Long favoriteId;
    /**
     * G-公会 T-推荐信
     */
    private String type;

    private Boolean deleted;


}
