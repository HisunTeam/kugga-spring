package com.hisun.kugga.duke.system.dal.mysql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesPageReqVO;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesRespVO;
import com.hisun.kugga.duke.system.dal.dataobject.MessagesDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import static com.hisun.kugga.duke.enums.message.MessageReadStatusEnum.UNREAD;

/**
 * 消息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MessagesMapper extends BaseMapperX<MessagesDO> {

    default int insertOne(MessagesDO entity) {
        return insert(entity);
    }

    default PageResult<MessagesDO> selectPage(MessagesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MessagesDO>()
                .eqIfPresent(MessagesDO::getScene, reqVO.getScene())
                .eqIfPresent(MessagesDO::getType, reqVO.getType())
                .eqIfPresent(MessagesDO::getReceiverId, reqVO.getReceiverId())
                .betweenIfPresent(MessagesDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(MessagesDO::getCreateTime));
    }

    default Long getUserUnReadCount(Long userId) {
        LambdaQueryWrapper<MessagesDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessagesDO::getReceiverId, userId);
        wrapper.eq(MessagesDO::getReadFlag, UNREAD.getCode());
        return selectCount(wrapper);
    }

    /**
     * 查询用户分页列表
     *
     * @param pageParam
     * @param reqVO
     * @return
     */
    Page<MessagesRespVO> selectMessagePage(@Param("pageParam") Page<MessagesRespVO> pageParam, @Param("reqVO") MessagesPageReqVO reqVO);

    default List<MessagesDO> selectByBusinessId(Long taskId) {
        return selectList(MessagesDO::getBusinessId, taskId);
    }
}
