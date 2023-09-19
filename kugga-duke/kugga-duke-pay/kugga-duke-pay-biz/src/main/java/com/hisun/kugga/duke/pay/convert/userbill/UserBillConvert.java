package com.hisun.kugga.duke.pay.convert.userbill;

import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillCreateReqVO;
import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillPageRspVO;
import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillRespVO;
import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillUpdateReqVO;
import com.hisun.kugga.duke.pay.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户账单 Convert
 *
 * @author zhou_xiong
 */
@Mapper
public interface UserBillConvert {

    UserBillConvert INSTANCE = Mappers.getMapper(UserBillConvert.class);

    UserBillDO convert(UserBillCreateReqVO bean);

    UserBillDO convert(UserBillUpdateReqVO bean);

    UserBillRespVO convert(UserBillDO bean);

    List<UserBillRespVO> convertList(List<UserBillDO> list);

    PageResult<UserBillRespVO> convertPage(PageResult<UserBillDO> page);

    PageResult<UserBillPageRspVO> convertPage1(PageResult<UserBillDO> page);

}
