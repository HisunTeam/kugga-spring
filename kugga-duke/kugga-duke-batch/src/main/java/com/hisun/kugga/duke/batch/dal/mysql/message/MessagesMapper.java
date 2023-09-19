package com.hisun.kugga.duke.batch.dal.mysql.message;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessagesDO;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 消息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MessagesMapper extends BaseMapperX<MessagesDO> {
    /**
     * 根据任务号查询需处理的消息
     *
     * @param businessId
     * @param dealStatus
     * @return
     */
    default List<MessagesDO> selectByBusinessIdAndDealStatus(Long businessId, String dealStatus) {
        return selectList(new LambdaQueryWrapper<MessagesDO>()
                .eq(MessagesDO::getBusinessId, businessId)
                .eq(MessagesDO::getDealFlag, dealStatus));
    }

    /**
     * 消息过期处理， 公会认证 聊天 business_id是taskId、推荐报告business_id是noticeId
     *
     * @param messagesDO
     * @param businessId
     */
    //update duke_message set deal_flag = 'EX' where business_id = '1' and deal_flag = 'D'
    default void updateExpireByBusinessId(MessagesDO messagesDO, Long businessId) {
        update(messagesDO, new LambdaQueryWrapper<MessagesDO>()
                .eq(MessagesDO::getBusinessId, businessId)
                .eq(MessagesDO::getDealFlag, MessageDealStatusEnum.DEAL.getCode()));
    }
}
