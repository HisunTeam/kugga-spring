package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 公会成长等级 DO
 *
 * @author 芋道源码
 */
@TableName("duke_league_growth_level")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueGrowthLevelDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 所加入公会id
     */
    private Long leagueId;
    /**
     * 成长等级
     */
    private Integer growthLevel;
    /**
     * 等级名称
     */
    private String levelName;
    /**
     * 该等级的最小值 [1,10]闭区间 0<=x<=10
     */
    private Integer levelMin;
    /**
     * 该等级的最大值 []区间
     */
    private Integer levelMax;
}
