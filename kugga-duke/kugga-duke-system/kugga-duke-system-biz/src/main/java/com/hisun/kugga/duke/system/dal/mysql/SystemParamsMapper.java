package com.hisun.kugga.duke.system.dal.mysql;

import com.hisun.kugga.duke.system.dal.dataobject.SystemParamsDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统参数表 Mapper 接口
 * </p>
 *
 * @author zuocheng
 * @since 2022-07-29 09:15:00
 */
@Mapper
public interface SystemParamsMapper extends BaseMapperX<SystemParamsDO> {
    /**
     * 根据类型与KEY查询参数
     *
     * @param type
     * @param paramKey
     * @return
     */
    default SystemParamsDO selectByParamKey(String type, String paramKey) {
        return selectOne(new LambdaQueryWrapperX<SystemParamsDO>()
                .eq(SystemParamsDO::getType, type)
                .eq(SystemParamsDO::getParamKey, paramKey)
        );
    }
}
