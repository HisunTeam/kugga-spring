package com.hisun.kugga.duke.league.dal.dataobject.growthinfo;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.GrowthType;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 公会成员 DO
 *
 * @author 芋道源码
 */
@TableName("duke_growth_info")
@KeySequence("duke_growth_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrowthInfoDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 公会成员表ID  duke_league_member.id
     */
    private Long memberId;
    /**
     * 成长类型
     */
    private GrowthType growthType;
    /**
     * 成长值
     */
    private Integer growthValue;

}
