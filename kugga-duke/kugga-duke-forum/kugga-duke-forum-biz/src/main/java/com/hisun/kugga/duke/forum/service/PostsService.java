package com.hisun.kugga.duke.forum.service;

import com.hisun.kugga.duke.forum.vo.BatchCancelCollectionReqVO;
import com.hisun.kugga.duke.forum.vo.CommentVO;
import com.hisun.kugga.duke.forum.vo.CommentsReqVO;
import com.hisun.kugga.duke.forum.vo.CreatePostsReqVO;
import com.hisun.kugga.duke.forum.vo.HotPostsVO;
import com.hisun.kugga.duke.forum.vo.HotScanSwitchReqVO;
import com.hisun.kugga.duke.forum.vo.LabelPostsReqVO;
import com.hisun.kugga.duke.forum.vo.MessageDetailsVO;
import com.hisun.kugga.duke.forum.vo.PagePostsCollectionReqVO;
import com.hisun.kugga.duke.forum.vo.PostsCollectionReqVO;
import com.hisun.kugga.duke.forum.vo.PostsDetailsReqVO;
import com.hisun.kugga.duke.forum.vo.PostsFloorReqVO;
import com.hisun.kugga.duke.forum.vo.PostsFloorVO;
import com.hisun.kugga.duke.forum.vo.PostsReqVO;
import com.hisun.kugga.duke.forum.vo.PostsVO;
import com.hisun.kugga.duke.forum.vo.PraiseReqVO;
import com.hisun.kugga.duke.forum.vo.ReplyPostsReqVO;
import com.hisun.kugga.duke.forum.vo.ReplyReqVO;
import com.hisun.kugga.duke.forum.vo.TrampleReqVO;
import com.hisun.kugga.framework.common.pojo.PageResult;

import java.util.List;

/**
 * 论坛贴子服务
 *
 * @author zuocheng
 */
public interface PostsService {
    /**
     * 分页查询贴子列表
     *
     * @param reqVO
     * @return
     */
    PageResult<PostsVO> pageList(PostsReqVO reqVO);

    /**
     * 创建贴子
     *
     * @param reqVO
     */
    void createPosts(CreatePostsReqVO reqVO);

    /**
     * 贴子详情
     *
     * @param reqVO
     * @return
     */
    PostsVO postsDetails(PostsDetailsReqVO reqVO);

    /**
     * 贴子回复列表
     *
     * @param reqVO
     * @return
     */
    PageResult<PostsFloorVO> pageFloor(PostsFloorReqVO reqVO);


    /**
     * 讨论列表（每一个楼层的讨论列表）
     *
     * @param reqVO
     * @return
     */
    PageResult<CommentVO> pageComments(CommentsReqVO reqVO);

    /**
     * 回复贴子、楼层、讨论
     *
     * @param reqVO
     */
    CommentVO reply(ReplyReqVO reqVO);

    /**
     * 回复贴子
     *
     * @param reqVO
     */
    PostsFloorVO replyPosts(ReplyPostsReqVO reqVO);

    /**
     * 删除贴子、楼层、楼层讨论
     *
     * @param msgId
     */
    void delete(String msgId);

    /**
     * 实时热贴
     *
     * @return
     */
    List<HotPostsVO> realTimeHostPosts();

    /**
     * 上升热帖
     *
     * @return
     */
    List<HotPostsVO> riseHotPosts();

    /**
     * 匿名热贴
     *
     * @return
     */
    List<HotPostsVO> anonymousHotPosts();

    /**
     * 查询楼层信息
     *
     * @param postsId
     * @param floorId
     * @return
     */
    MessageDetailsVO floor(Long postsId, Long floorId);

    /**
     * 修改贴子热贴扫描开关
     * @param reqVO
     */
    void hotScanSwitch(HotScanSwitchReqVO reqVO);

    /**
     * 点赞/取消点赞
     * @param reqVO
     */
    void praise(PraiseReqVO reqVO);

    /**
     * 点踩/取消点踩
     * @param reqVO
     */
    void trample(TrampleReqVO reqVO);

    /**
     * 根据标签查询贴子列表
     * @param reqVO
     * @return
     */
    PageResult<PostsVO> labelPosts(LabelPostsReqVO reqVO);

    /**
     * 收藏/取消收藏
     * @param reqVO
     */
    Long collection(PostsCollectionReqVO reqVO);

    /**
     * 批量取消收藏
     * @param reqVO
     */
    void batchCancelCollection(BatchCancelCollectionReqVO reqVO);

    /**
     * 分布查询分组列表
     * @param reqVO
     * @return
     */
    PageResult<PostsVO> pagePostsCollection(PagePostsCollectionReqVO reqVO);
}
