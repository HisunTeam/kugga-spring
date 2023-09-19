package com.hisun.kugga.module.system.dal.mysql.social;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.module.system.dal.dataobject.social.SocialUserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SocialUserMapper extends BaseMapperX<SocialUserDO> {

    default SocialUserDO selectByTypeAndCodeAnState(Integer type, String code, String state) {
        return selectOne(new LambdaQueryWrapper<SocialUserDO>()
                .eq(SocialUserDO::getType, type)
                .eq(SocialUserDO::getCode, code)
                .eq(SocialUserDO::getState, state));
    }

    default SocialUserDO selectByTypeAndOpenid(Integer type, String openid) {
        return selectOne(new LambdaQueryWrapper<SocialUserDO>()
                .eq(SocialUserDO::getType, type)
                .eq(SocialUserDO::getOpenid, openid));
    }

}
