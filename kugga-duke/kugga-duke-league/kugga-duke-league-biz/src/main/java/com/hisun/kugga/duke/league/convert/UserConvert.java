package com.hisun.kugga.duke.league.convert;

import com.hisun.kugga.duke.league.api.dto.leaguemember.BonusUserDTO;
import com.hisun.kugga.duke.league.dal.dataobject.BonusUserDO;
import com.hisun.kugga.duke.league.vo.user.UserBO;
import com.hisun.kugga.duke.user.api.oauth2.dto.UserInfoRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserBO convert(UserInfoRespDTO bean);

    List<BonusUserDTO> convert(List<BonusUserDO> bonusUserDOList);
}
