package com.hisun.kugga.duke.batch.dal.mysql.message;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessagesDO;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Message Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MessagesMapper extends BaseMapperX<MessagesDO> {
    /**
     * Query messages to be processed by task ID
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
     * Handle expired messages, for guild authentication, chat, and recommendation reports.
     * The business_id is taskId for authentication and noticeId for recommendation reports.
     *
     * @param messagesDO
     * @param businessId
     */
    default void updateExpireByBusinessId(MessagesDO messagesDO, Long businessId) {
        update(messagesDO, new LambdaQueryWrapper<MessagesDO>()
                .eq(MessagesDO::getBusinessId, businessId)
                .eq(MessagesDO::getDealFlag, MessageDealStatusEnum.DEAL.getCode()));
    }
}
