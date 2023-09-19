package com.hisun.kugga.duke.user.cv.convert.resume;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * 个人简历信息 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ResumeConvert {

    ResumeConvert INSTANCE = Mappers.getMapper(ResumeConvert.class);

//    ResumeDO convert(ResumeCreateReqVO bean);

//    ResumeDO convert(ResumeUpdateReqVO bean);

//    ResumeRespVO convert(ResumeDO bean);

//    List<ResumeRespVO> convertList(List<ResumeDO> list);
//
//    PageResult<ResumeRespVO> convertPage(PageResult<ResumeDO> page);
//
//    List<ResumeExcelVO> convertList02(List<ResumeDO> list);

}
