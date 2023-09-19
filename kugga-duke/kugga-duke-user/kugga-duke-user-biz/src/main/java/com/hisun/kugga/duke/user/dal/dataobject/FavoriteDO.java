package com.hisun.kugga.duke.user.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 收藏 DO
 *
 * @author 芋道源码
 */
@TableName("duke_favorite")
@KeySequence("duke_favorite_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDO extends BaseDO {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 内容id 工会、推荐报告id..
     */
    private Long contentId;
    /**
     * 收藏类型 G-工会 T-推荐报告
     */
    private String type;
    /**
     * 写推荐报告所在公会id
     */
    private Long recommendationLeagueId;
    /**
     * 收藏推荐报告时所在公会id
     */
    private Long favoriteLeagueId;
    /**
     * 不逻辑删除
     */
    private Boolean deleted;

}
