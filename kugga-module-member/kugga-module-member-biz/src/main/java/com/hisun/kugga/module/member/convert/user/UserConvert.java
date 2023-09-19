package com.hisun.kugga.module.member.convert.user;

import com.hisun.kugga.module.member.api.user.dto.UserRespDTO;
import com.hisun.kugga.module.member.controller.app.user.vo.AppUserInfoRespVO;
import com.hisun.kugga.module.member.dal.dataobject.user.MemberUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    AppUserInfoRespVO convert(MemberUserDO bean);

    UserRespDTO convert2(MemberUserDO bean);
}
