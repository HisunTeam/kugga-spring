package com.hisun.kugga.duke.user.dal.mysql.oauth2;

import com.hisun.kugga.duke.user.dal.dataobject.oauth2.OAuth2CodeDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OAuth2CodeMapper extends BaseMapperX<OAuth2CodeDO> {

    default OAuth2CodeDO selectByCode(String code) {
        return selectOne(OAuth2CodeDO::getCode, code);
    }

}
