package com.hisun.kugga.duke.league.dal.mysql;


import com.hisun.kugga.duke.league.dal.dataobject.LeagueLabelDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zuocheng
 * 公会标签枚举配置 mapper
 */
@Mapper
public interface LeagueLabelMapper extends BaseMapperX<LeagueLabelDO> {

    /**
     * 查询标签简单列表,可自定义查询条数,预留显示 与 语言 方便以后新增配置,及支持多语言
     *
     * @return
     */
    default List<LeagueLabelDO> simpleLabels() {
        return selectList(new LambdaQueryWrapperX<LeagueLabelDO>()
                .select(LeagueLabelDO::getLabelName,
                        LeagueLabelDO::getLabelValue)
                .eq(LeagueLabelDO::getLanguage, "en-US")
                .eq(LeagueLabelDO::getDisplayFlag, true)
                .last("limit 5")
        );
    }

    /**
     * 根据语言查询公会标签列表（现在只支持英语,直接写死）
     *
     * @return
     */
    default List<LeagueLabelDO> selectListByLanguage() {
        return selectList(new LambdaQueryWrapperX<LeagueLabelDO>()
                .eq(LeagueLabelDO::getLanguage, "en-US")
                .eq(LeagueLabelDO::getDisplayFlag, true)
                .orderByAsc(LeagueLabelDO::getSortId)
                .orderByDesc(LeagueLabelDO::getUpdateTime)
        );
    }

    /**
     * 查询单个标签详情,预留语言，避免支付多语言
     *
     * @param labelValue
     * @return
     */
    default LeagueLabelDO selectOneByLabelValue(String labelValue) {
        return selectOne(new LambdaQueryWrapperX<LeagueLabelDO>()
                .eq(LeagueLabelDO::getLabelValue, labelValue)
                .eq(LeagueLabelDO::getLanguage, "en-US"));
    }
}
