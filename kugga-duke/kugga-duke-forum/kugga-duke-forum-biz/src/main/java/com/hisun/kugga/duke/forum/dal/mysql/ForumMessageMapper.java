package com.hisun.kugga.duke.forum.dal.mysql;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.forum.dal.dataobject.ForumMessageDO;
import com.hisun.kugga.duke.forum.vo.MessageVO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 论坛消息 Mapper 接口
 * </p>
 *
 * @author zuocheng
 * @since 2022-09-02 10:02:09
 */
@Mapper
public interface ForumMessageMapper extends BaseMapperX<ForumMessageDO> {

    /**
     * 根据用户ID查询用户的消息列表
     *
     * @param page
     * @param userId
     * @return
     */
    IPage<MessageVO> pageListByUserId(Page page, @Param("userId") Long userId);

    /**
     * 根据用户ID查询用户所有未读消息条数
     *
     * @param userId
     * @return
     */
    default Long selectUnreadNum(Long userId) {
        return selectCount(new LambdaQueryWrapperX<ForumMessageDO>()
                .eq(ForumMessageDO::getUserId, userId)
                .eq(ForumMessageDO::getReadFlag, false));
    }

    /**
     * 根据用户ID，将用户所有的未读消息修改成已读
     *
     * @param userId
     * @return
     */
    default int updateRead(Long userId) {
        return update(null, new LambdaUpdateWrapper<ForumMessageDO>()
                .eq(ForumMessageDO::getUserId, userId)
                .eq(ForumMessageDO::getReadFlag, false)
                .set(ForumMessageDO::getReadFlag, true));
    }

    /**
     * 根据消息ID 单条已读处理
     *
     * @return
     */
    default int updateSingleRead(Long id, Long userId) {
        return update(null, new LambdaUpdateWrapper<ForumMessageDO>()
                .eq(ForumMessageDO::getId, id)
                .eq(ForumMessageDO::getUserId, userId)
                .eq(ForumMessageDO::getReadFlag, false)
                .set(ForumMessageDO::getReadFlag, true)
        );
    }
}
