package com.hisun.kugga.duke.forum.dal.mysql;

import com.hisun.kugga.duke.forum.dal.dataobject.ForumPlateDO;
import com.hisun.kugga.duke.forum.vo.SimplePlateVO;
import com.hisun.kugga.framework.common.pojo.PageParam;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 论坛板块表 Mapper 接口
 * </p>
 *
 * @author zuocheng
 * @since 2022-09-02 10:02:09
 */
@Mapper
public interface ForumPlateMapper extends BaseMapperX<ForumPlateDO> {
    /**
     * 分页查询板块列表
     *
     * @param pageParam
     * @param language
     * @return
     */
    default PageResult<ForumPlateDO> selectPlates(PageParam pageParam, String language) {
        return selectPage(pageParam, new LambdaQueryWrapperX<ForumPlateDO>()
                .eq(ForumPlateDO::getLanguage, language));
    }

    /**
     * 未了将来支持多语言
     *
     * @param plateValue
     * @param language
     * @return
     */
    default ForumPlateDO selectOneByValue(String plateValue, String language) {
        return selectOne(new LambdaQueryWrapperX<ForumPlateDO>()
                .eq(ForumPlateDO::getLanguage, language)
                .eq(ForumPlateDO::getPlateValue, plateValue));
    }

    /**
     * 查询公会信息做为板块信息
     *
     * @param leagueId
     * @return
     */
    SimplePlateVO selectLeague(@Param("leagueId") Long leagueId);
}
