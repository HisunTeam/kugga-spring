package com.hisun.kugga.duke.user.convert;

import com.hisun.kugga.duke.user.controller.vo.user.UserCreateReqVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserRespVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserUpdateReqVO;
import com.hisun.kugga.duke.user.dal.dataobject.UserDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户信息 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserDO convert(UserCreateReqVO bean);

    UserDO convert(UserUpdateReqVO bean);

    UserRespVO convert(UserDO bean);

    List<UserRespVO> convertList(List<UserDO> list);

    PageResult<UserRespVO> convertPage(PageResult<UserDO> page);

//    List<UserExcelVO> convertList02(List<UserDO> list);

}
