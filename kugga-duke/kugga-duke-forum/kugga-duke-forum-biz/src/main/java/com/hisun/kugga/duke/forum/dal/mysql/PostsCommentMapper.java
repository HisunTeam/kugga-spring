package com.hisun.kugga.duke.forum.dal.mysql;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsCommentDO;
import com.hisun.kugga.duke.forum.vo.CommentVO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 贴子讨论表 Mapper 接口
 * </p>
 *
 * @author zuocheng
 * @since 2022-08-29 09:35:00
 */
@Mapper
public interface PostsCommentMapper extends BaseMapperX<PostsCommentDO> {
    /**
     * 查询讨论列表
     *
     * @param page
     * @param floorId
     * @return
     */
    IPage<CommentVO> pageComments(Page page, @Param("floorId") Long floorId);

    /**
     * 根据msgId查询回复信息
     *
     * @param msgId
     * @return
     */
    default PostsCommentDO selectByMsgId(String msgId) {
        return selectOne(new LambdaQueryWrapperX<PostsCommentDO>().eq(PostsCommentDO::getMsgId, msgId));
    }

    /**
     * 查询消息
     *
     * @param floorId
     * @return
     */
    CommentVO comment(@Param("id") Long floorId);

    /**
     * 根据楼层删除回复
     *
     * @param floorId
     * @return
     */
    default int deleteByFloorId(Long floorId) {
        return delete(new LambdaQueryWrapperX<PostsCommentDO>().eq(PostsCommentDO::getFloorId, floorId));
    }

    /**
     * 点赞加1
     * @param id
     * @return
     */
    default int praisePlusOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsCommentDO>()
                .setSql("praise_num = praise_num + 1").eq(PostsCommentDO::getId, id));
    }

    /**
     * 点赞减1
     * @param id
     * @return
     */
    default int praiseSubtractOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsCommentDO>()
                .setSql("praise_num = praise_num - 1").eq(PostsCommentDO::getId, id));
    }

    /**
     * 点踩加1
     * @param id
     * @return
     */
    default int tramplePlusOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsCommentDO>()
                .setSql("trample_num = trample_num + 1").eq(PostsCommentDO::getId, id));
    }

    /**
     * 点踩减1
     * @param id
     * @return
     */
    default int trampleSubtractOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsCommentDO>()
                .setSql("trample_num = trample_num - 1").eq(PostsCommentDO::getId, id));
    }
}
