package com.hisun.kugga.duke.forum.dal.mysql;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsDO;
import com.hisun.kugga.duke.forum.vo.HotPostsVO;
import com.hisun.kugga.duke.forum.vo.LabelVO;
import com.hisun.kugga.duke.forum.vo.PostsReqVO;
import com.hisun.kugga.duke.forum.vo.PostsVO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 贴子表 Mapper 接口
 * </p>
 *
 * @author zuocheng
 * @since 2022-08-29 09:29:57
 */
@Mapper
public interface PostsMapper extends BaseMapperX<PostsDO> {

    /**
     * 查询贴子详情
     *
     * @param id
     * @return
     */
    PostsVO selectPostsDetails(@Param("id") Long id);

    /**
     * 查询贴子列表根据创建时间排序
     *
     * @param page
     * @param param
     * @return
     */
    IPage<PostsVO> pagePostsSortCreateTime(Page page, @Param("param") PostsReqVO param);

    /**
     * 查询贴子列表根据最后回复时间
     *
     * @param page
     * @param param
     * @return
     */
    IPage<PostsVO> pagePostsSortReplyTime(Page page, @Param("param") PostsReqVO param);

    /**
     * 查询贴子列表根据热度
     *
     * @param page
     * @param param
     * @return
     */
    IPage<PostsVO> pagePostsSortHot(Page page, @Param("param") PostsReqVO param);

    /**
     * 更新贴子热度、楼层数
     *
     * @param postsDO
     * @return
     */
    default int updatePostsFloor(PostsDO postsDO) {
        return update(null, new LambdaUpdateWrapper<PostsDO>()
                .eq(PostsDO::getId, postsDO.getId())
                .set(PostsDO::getNewReplyUserId, postsDO.getNewReplyUserId())
                .set(PostsDO::getUpdateTime, LocalDateTime.now())
                .set(PostsDO::getReplyTime, LocalDateTime.now())
                .setSql("hot_num = hot_num + 1, comment_num = comment_num + 1, floor_count = floor_count + 1")
        );
    }

    /**
     * 更新贴子热度
     *
     * @param postsDO
     * @return
     */
    default int updatePostsHot(PostsDO postsDO) {
        return update(null, new LambdaUpdateWrapper<PostsDO>()
                .eq(PostsDO::getId, postsDO.getId())
                .set(PostsDO::getUpdateTime, LocalDateTime.now())
                .setSql("hot_num = hot_num + 1, comment_num = comment_num + 1")
        );
    }

    /**
     * 更新贴子点击数
     *
     * @param id
     * @return
     */
    default int updatePostsClick(Long id) {
        return update(null, new LambdaUpdateWrapper<PostsDO>()
                .eq(PostsDO::getId, id)
                .set(PostsDO::getUpdateTime, LocalDateTime.now())
                .setSql("click_num = click_num + 1")
        );
    }

    /**
     * 根据msgId查询贴子信息
     *
     * @param msgId
     * @return
     */
    default PostsDO selectByMsgId(String msgId) {
        return selectOne(new LambdaQueryWrapperX<PostsDO>().eq(PostsDO::getMsgId, msgId));
    }

    /**
     * 查询非匿名（公会）热贴
     *
     * @param createTime
     * @return
     */
    List<HotPostsVO> hotPosts(@Param("createTime") LocalDateTime createTime);

    /**
     * 查询匿名热贴
     *
     * @param createTime
     * @return
     */
    List<HotPostsVO> anonymousHotPosts(@Param("createTime") LocalDateTime createTime);

    /**
     * 修改贴子热贴扫描开关
     *
     * @param id
     * @param boo
     * @return
     */
    default int updatePostsHotSwitch(Long id, Boolean boo) {
        return update(null, new LambdaUpdateWrapper<PostsDO>()
                .eq(PostsDO::getId, id)
                .set(PostsDO::getHotSearchSwitch, boo)
                .set(PostsDO::getUpdateTime, LocalDateTime.now())
        );
    }

    /**
     * 减去回复数（有删楼层 或 讨论时调用下）
     *
     * @return
     */
    default int subCommentNum(long postsId, int num) {
        return update(null, new LambdaUpdateWrapper<PostsDO>()
                .eq(PostsDO::getId, postsId)
                .set(PostsDO::getUpdateTime, LocalDateTime.now())
                .setSql("comment_num = comment_num -" + num)
        );
    }

    /**
     * 根据标签ID查询贴子列表
     * @param labelId
     * @return
     */
    IPage<PostsVO> pageLabelPosts(Page page, @Param("labelId") long labelId);

    /**
     * 点赞加1
     * @param id
     * @return
     */
    default int praisePlusOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsDO>()
                .setSql("praise_num = praise_num + 1").eq(PostsDO::getId, id));
    }

    /**
     * 点赞减1
     * @param id
     * @return
     */
    default int praiseSubtractOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsDO>()
                .setSql("praise_num = praise_num - 1").eq(PostsDO::getId, id));
    }

    /**
     * 点踩加1
     * @param id
     * @return
     */
    default int tramplePlusOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsDO>()
                .setSql("trample_num = trample_num + 1").eq(PostsDO::getId, id));
    }

    /**
     * 点踩减1
     * @param id
     * @return
     */
    default int trampleSubtractOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsDO>()
                .setSql("trample_num = trample_num - 1").eq(PostsDO::getId, id));
    }

    /**
     * 收藏加1
     * @param id
     * @return
     */
    default int collectPlusOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsDO>()
                .setSql("collect_num = collect_num + 1").eq(PostsDO::getId, id));
    }

    /**
     * 收藏减1
     * @param id
     * @return
     */
    default int collectSubtractOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsDO>()
                .setSql("collect_num = collect_num - 1").eq(PostsDO::getId, id));
    }

    /**
     * 分页查询用户的收藏列表(支持收藏分组查询)
     * @param page
     * @param userId
     * @param groupId
     * @return
     */
    IPage<PostsVO> pagePostsCollection(Page page, @Param("userId") Long userId, @Param("groupId") Long groupId);

    /**
     * 查询贴子拥有标签
     * @return
     */
    List<LabelVO> selectPostsLabel(@Param("postsId") Long postsId);
}
