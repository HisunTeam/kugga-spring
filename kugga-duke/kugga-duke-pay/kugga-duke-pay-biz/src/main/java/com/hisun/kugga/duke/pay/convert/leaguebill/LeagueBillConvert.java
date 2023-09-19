package com.hisun.kugga.duke.pay.convert.leaguebill;

import com.hisun.kugga.duke.pay.controller.app.leaguebill.vo.LeagueBillCreateReqVO;
import com.hisun.kugga.duke.pay.controller.app.leaguebill.vo.LeagueBillPageRspVO;
import com.hisun.kugga.duke.pay.controller.app.leaguebill.vo.LeagueBillRespVO;
import com.hisun.kugga.duke.pay.controller.app.leaguebill.vo.LeagueBillUpdateReqVO;
import com.hisun.kugga.duke.pay.dal.dataobject.leaguebill.LeagueBillDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 公会账单 Convert
 *
 * @author zhou_xiong
 */
@Mapper
public interface LeagueBillConvert {

    LeagueBillConvert INSTANCE = Mappers.getMapper(LeagueBillConvert.class);

    LeagueBillDO convert(LeagueBillCreateReqVO bean);

    LeagueBillDO convert(LeagueBillUpdateReqVO bean);

    LeagueBillRespVO convert(LeagueBillDO bean);

    List<LeagueBillRespVO> convertList(List<LeagueBillDO> list);

    PageResult<LeagueBillRespVO> convertPage(PageResult<LeagueBillDO> page);

    PageResult<LeagueBillPageRspVO> convertPage1(PageResult<LeagueBillDO> page);


}
