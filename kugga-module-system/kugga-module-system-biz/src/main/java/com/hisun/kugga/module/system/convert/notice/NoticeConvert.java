package com.hisun.kugga.module.system.convert.notice;

import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.module.system.controller.admin.notice.vo.NoticeCreateReqVO;
import com.hisun.kugga.module.system.controller.admin.notice.vo.NoticeRespVO;
import com.hisun.kugga.module.system.controller.admin.notice.vo.NoticeUpdateReqVO;
import com.hisun.kugga.module.system.dal.dataobject.notice.NoticeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);

    PageResult<NoticeRespVO> convertPage(PageResult<NoticeDO> page);

    NoticeRespVO convert(NoticeDO bean);

    NoticeDO convert(NoticeUpdateReqVO bean);

    NoticeDO convert(NoticeCreateReqVO bean);

}
