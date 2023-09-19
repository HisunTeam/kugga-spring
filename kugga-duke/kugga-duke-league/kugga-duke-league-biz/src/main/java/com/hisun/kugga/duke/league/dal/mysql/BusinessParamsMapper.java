package com.hisun.kugga.duke.league.dal.mysql;

import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.league.dal.dataobject.BusinessParamsDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-09-08 15:14
 */
@Mapper
public interface BusinessParamsMapper extends BaseMapperX<BusinessParamsDO> {

    default void insert(String businessId, Object... params) {
        BusinessParamsDO businessParamsDO = new BusinessParamsDO()
                .setBusinessId(businessId)
                .setParams(JSONUtil.toJsonStr(params));
        this.insert(businessParamsDO);
    }
}
