package com.hisun.kugga.module.system.dal.mysql.sms;

import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.QueryWrapperX;
import com.hisun.kugga.module.system.controller.admin.sms.vo.log.SmsLogExportReqVO;
import com.hisun.kugga.module.system.controller.admin.sms.vo.log.SmsLogPageReqVO;
import com.hisun.kugga.module.system.dal.dataobject.sms.SmsLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SmsLogMapper extends BaseMapperX<SmsLogDO> {

    default PageResult<SmsLogDO> selectPage(SmsLogPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<SmsLogDO>()
                .eqIfPresent("channel_id", reqVO.getChannelId())
                .eqIfPresent("template_id", reqVO.getTemplateId())
                .likeIfPresent("mobile", reqVO.getMobile())
                .eqIfPresent("send_status", reqVO.getSendStatus())
                .betweenIfPresent("send_time", reqVO.getBeginSendTime(), reqVO.getEndSendTime())
                .eqIfPresent("receive_status", reqVO.getReceiveStatus())
                .betweenIfPresent("receive_time", reqVO.getBeginReceiveTime(), reqVO.getEndReceiveTime())
                .orderByDesc("id"));
    }

    default List<SmsLogDO> selectList(SmsLogExportReqVO reqVO) {
        return selectList(new QueryWrapperX<SmsLogDO>()
                .eqIfPresent("channel_id", reqVO.getChannelId())
                .eqIfPresent("template_id", reqVO.getTemplateId())
                .likeIfPresent("mobile", reqVO.getMobile())
                .eqIfPresent("send_status", reqVO.getSendStatus())
                .betweenIfPresent("send_time", reqVO.getBeginSendTime(), reqVO.getEndSendTime())
                .eqIfPresent("receive_status", reqVO.getReceiveStatus())
                .betweenIfPresent("receive_time", reqVO.getBeginReceiveTime(), reqVO.getEndReceiveTime())
                .orderByDesc("id"));
    }

}
