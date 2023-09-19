package com.hisun.kugga.duke.user.cv.dal.dataobject.recommendation;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 推荐报告 DO
 *
 * @author 芋道源码
 */
@TableName("duke_recommendation")
@KeySequence("duke_recommendation_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 推荐人编号
     */
    private Long recommenderId;
    /**
     * 被推荐人编号
     */
    private Long recommendedId;
    /**
     * 推荐人公会编号
     */
    private Long recommenderLeagueId;
    /**
     * 推荐信内容
     */
    private String content;
    /**
     * 原始文本
     */
    private String originalText;
    /**
     * 分享链接
     */
    private String shareLink;

}
