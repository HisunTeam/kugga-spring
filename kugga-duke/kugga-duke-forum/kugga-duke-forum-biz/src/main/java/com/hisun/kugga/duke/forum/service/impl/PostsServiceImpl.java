package com.hisun.kugga.duke.forum.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.entity.Content;
import com.hisun.kugga.duke.enums.GrowthType;
import com.hisun.kugga.duke.enums.RichTextTypeEnum;
import com.hisun.kugga.duke.forum.bo.ReceiveBO;
import com.hisun.kugga.duke.forum.dal.dataobject.ForumLabelDO;
import com.hisun.kugga.duke.forum.dal.dataobject.ForumMessageDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsCollectionDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsCommentDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsCommentPraiseRecordDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsCommentTrampleRecordDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsContentDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsFloorContentDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsFloorDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsFloorPraiseRecordDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsFloorTrampleRecordDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsImageDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsLabelRelationDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsPraiseRecordDO;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsTrampleRecordDO;
import com.hisun.kugga.duke.forum.dal.mysql.ForumLabelMapper;
import com.hisun.kugga.duke.forum.dal.mysql.ForumMessageMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsCollectionMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsCommentMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsCommentPraiseRecordMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsCommentTrampleRecordMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsContentMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsFloorContentMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsFloorMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsFloorPraiseRecordMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsFloorTrampleRecordMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsImageMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsLabelRelationMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsPraiseRecordMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsRiseCountMapper;
import com.hisun.kugga.duke.forum.dal.mysql.PostsTrampleRecordMapper;
import com.hisun.kugga.duke.forum.enums.MsgTypeEnums;
import com.hisun.kugga.duke.forum.enums.PlateEnums;
import com.hisun.kugga.duke.forum.enums.SortTypeEnums;
import com.hisun.kugga.duke.forum.service.PostsService;
import com.hisun.kugga.duke.forum.utils.AnonymousUtil;
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
import com.hisun.kugga.duke.growthrule.factory.GrowthRuleFactory;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.duke.league.api.dto.UserGrowthLevelDTO;
import com.hisun.kugga.duke.user.api.oauth2.FavoriteGroupApi;
import com.hisun.kugga.duke.user.api.oauth2.dto.GroupRelationUpdateReqDTO;
import com.hisun.kugga.duke.utils.EditorUtils;
import com.hisun.kugga.duke.utils.RedisUtils;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.hisun.kugga.duke.common.CommonConstants.SNOWFLAKE;
import static com.hisun.kugga.duke.common.RedisKeyPrefixConstants.FORUM_COMMENT_PRAISE;
import static com.hisun.kugga.duke.common.RedisKeyPrefixConstants.FORUM_COMMENT_TRAMPLE;
import static com.hisun.kugga.duke.common.RedisKeyPrefixConstants.FORUM_FLOOR_PRAISE;
import static com.hisun.kugga.duke.common.RedisKeyPrefixConstants.FORUM_FLOOR_TRAMPLE;
import static com.hisun.kugga.duke.common.RedisKeyPrefixConstants.FORUM_POSTS_COLLECTION;
import static com.hisun.kugga.duke.common.RedisKeyPrefixConstants.FORUM_POSTS_PRAISE;
import static com.hisun.kugga.duke.common.RedisKeyPrefixConstants.FORUM_POSTS_TRAMPLE;
import static com.hisun.kugga.duke.forum.enums.PlateEnums.PLATE_0;
import static com.hisun.kugga.duke.forum.enums.PlateEnums.PLATE_1;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.common.util.servlet.ServletUtils.getClientIP;
import static com.hisun.kugga.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

/**
 * @author zuocheng
 */
@Service
@Slf4j
public class PostsServiceImpl implements PostsService {
    @Resource
    private LeagueApi leagueApi;

    @Resource
    private PostsMapper postsMapper;

    @Resource
    private PostsContentMapper postsContentMapper;

    @Resource
    private PostsFloorMapper postsFloorMapper;

    @Resource
    private PostsCommentMapper postsCommentMapper;

    @Resource
    private PostsFloorContentMapper postsFloorContentMapper;

    @Resource
    private ForumMessageMapper forumMessageMapper;

    @Resource
    private PostsRiseCountMapper postsRiseCountMapper;

    @Resource
    private PostsImageMapper postsImageMapper;

    @Resource
    private GrowthRuleFactory growthRuleFactory;

    @Resource
    private ForumLabelMapper forumLabelMapper;

    @Resource
    private PostsLabelRelationMapper postsLabelRelationMapper;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private PostsPraiseRecordMapper postsPraiseRecordMapper;

    @Resource
    private PostsTrampleRecordMapper postsTrampleRecordMapper;

    @Resource
    private PostsFloorPraiseRecordMapper postsFloorPraiseRecordMapper;

    @Resource
    private PostsFloorTrampleRecordMapper postsFloorTrampleRecordMapper;

    @Resource
    private PostsCommentPraiseRecordMapper postsCommentPraiseRecordMapper;

    @Resource
    private PostsCommentTrampleRecordMapper postsCommentTrampleRecordMapper;

    @Resource
    private PostsCollectionMapper postsCollectionMapper;

    @Resource
    private FavoriteGroupApi favoriteGroupApi;

    @Override
    public PageResult<PostsVO> pageList(PostsReqVO reqVO) {
        Page<PostsVO> page = new Page<>(reqVO.getPageNo(),
                reqVO.getPageSize());

        //检查是否为匿名板块
        boolean anonymousBoo = isAnonymous(reqVO.getPlate());

        //非匿名板块时，组ID必须传(目前非匿名贴，必然为公会贴，查询公会贴时必需传公会ID)
        if (!anonymousBoo) {
            if (ObjectUtil.isNull(reqVO.getGroupId())) {
                throw exception(BusinessErrorCodeConstants.PLATE_ERR);
            }
        }

        IPage<PostsVO> iPage;
        switch (SortTypeEnums.valueOfType(reqVO.getSortType())) {
            case REPLY_TIME:
                iPage = postsMapper.pagePostsSortReplyTime(page, reqVO);
                break;
            case HOT:
                iPage = postsMapper.pagePostsSortHot(page, reqVO);
                break;
            case CREATE_TIME:
            default:
                iPage = postsMapper.pagePostsSortCreateTime(page, reqVO);
        }
        //组装贴子列表数据
        buildPostsList(iPage);
        return new PageResult(iPage.getRecords(), iPage.getTotal());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createPosts(CreatePostsReqVO reqVO) {
        //检查用户是否有发贴权利
        checkPlate(reqVO.getPlate(), reqVO.getGroupId());

        //标题不能为空
        if (StrUtil.isBlank(reqVO.getPostsTitle())) {
            throw exception(BusinessErrorCodeConstants.POSTS_TITLE_EMPTY);
        }

        //内容不能为空
        if (CollUtil.isEmpty(reqVO.getContent())) {
            throw exception(BusinessErrorCodeConstants.POSTS_CONTENT_EMPTY);
        }

        //保存贴子信息
        savePosts(reqVO);

        // 公会发帖，加成长值
        if (PLATE_0.getType().equals(reqVO.getPlate())) {
            GrowthDTO growthDTO = new GrowthDTO(reqVO.getGroupId(), getLoginUserId());
            growthRuleFactory.getBy(GrowthType.POST).growthValue(growthDTO, leagueApi::growthThenLevel);
        }
    }

    @Override
    public PostsVO postsDetails(PostsDetailsReqVO reqVO) {
        //查询贴子信息
        PostsVO rspVO = postsMapper.selectPostsDetails(reqVO.getPostsId());
        if (ObjectUtil.isNull(rspVO)) {
            throw exception(BusinessErrorCodeConstants.POSTS_NOT_EXIST);
        }

        boolean anonymousBoo = isAnonymous(rspVO.getPlate());
        //非匿名贴时需要检查用户是否公会公会成员
        boolean memberBoo = false;
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (!anonymousBoo && ObjectUtil.isNotNull(userId)) {
            memberBoo = leagueApi.isLeagueMember(rspVO.getGroupId(), userId);
        }

        //匿名贴或公会成员可以直接查看贴子的全部内容
        List<PostsContentDO> contentList = postsContentMapper.selectByPostsId(rspVO.getPostsId());
        boolean hideBoo = anonymousBoo || memberBoo;
        //设置报文内容
        rspVO.setPostsContent(buildContent(contentList, !hideBoo));

        //设置贴子归属(登录用户查看贴子时，需要设置贴子归属)
        if (ObjectUtil.isNotNull(userId)) {
            rspVO.setBelongToFlag(rspVO.getUserId().equals(getLoginUserId()));
        }

        //匿名处理
        if (anonymousBoo) {
            String anon = AnonymousUtil.md5Hex("" + rspVO.getPostsId() + rspVO.getUserId());
            //匿名时不返回用户ID 与 头像 也不需要 姓
            rspVO.setUserId(Long.valueOf(anon));
            rspVO.setAvatar(null);
            rspVO.setUserName(anon);
            rspVO.setNickName(anon);
            rspVO.setFirstName(anon);
            rspVO.setLastName(null);

            if (ObjectUtil.isNotNull(rspVO.getNewReplyUserId())) {
                String reAnon = AnonymousUtil.md5Hex("" + rspVO.getPostsId() + rspVO.getNewReplyUserId());
                //匿名时不返回用户ID 与 头像 也不需要 姓
                rspVO.setNewReplyUserId(null);
                rspVO.setNewReplyAvatar(null);
                rspVO.setNewReplyUserName(reAnon);
                rspVO.setNewReplyNickName(reAnon);
                rspVO.setNewReplyFirstName(reAnon);
                rspVO.setNewReplyLastName(null);
            }
        }

        //检查用户是否对当前贴子做过点赞
        rspVO.setUserPraiseFlag(redisUtils.isMember(FORUM_POSTS_PRAISE + rspVO.getPostsId(), userId));

        //检查用户是否对当前贴子做过点踩
        rspVO.setUserTrampleFlag(redisUtils.isMember(FORUM_POSTS_TRAMPLE + rspVO.getPostsId(), userId));

        //检查用户是否对当前贴子做过点踩
        rspVO.setUserCollectionFlag(redisUtils.isMember(FORUM_POSTS_COLLECTION + rspVO.getPostsId(), userId));

        //非匿名贴,获取发贴人在当前贴,当前公会的等级与等级名称
        if(!anonymousBoo && ObjectUtil.isNotNull(userId)){
            UserGrowthLevelDTO userGrowthLeve = leagueApi.getUserGrowthInfo(rspVO.getGroupId(), rspVO.getUserId());
            if(ObjectUtil.isNotNull(userGrowthLeve)){
                rspVO.setUserLevelName(userGrowthLeve.getLevelName());
                rspVO.setUserGrowthLevel(userGrowthLeve.getGrowthLevel());
            }
        }

        //设置贴子拥用的标签
        rspVO.setLabels(postsMapper.selectPostsLabel(rspVO.getPostsId()));

        //更新贴子点击数
        postsMapper.updatePostsClick(rspVO.getPostsId());
        return rspVO;
    }

    @Override
    public PageResult<PostsFloorVO> pageFloor(PostsFloorReqVO reqVO) {
        PostsDO posts = queryPosts(reqVO.getPostsId());

        boolean anonymousBoo = isAnonymous(posts.getPlate());
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        //非匿名贴检查用户是否为公会成员
        if (!anonymousBoo && ObjectUtil.isNotNull(userId)) {
            //非公会成员不可获取楼层内容 直接返回空
            if (!leagueApi.isLeagueMember(posts.getGroupId(), userId)) {
                return new PageResult(new ArrayList<PostsFloorVO>(), 0L);
            }
        }

        Page<PostsFloorVO> page = new Page<>(reqVO.getPageNo(),
                reqVO.getPageSize());

        IPage<PostsFloorVO> iPage;
        //未带排序规则时默认成按回复时间正序
        if (StrUtil.isBlank(reqVO.getSortType())) {
            reqVO.setSortType("0");
        }
        switch (SortTypeEnums.valueOfType(reqVO.getSortType())) {
            case REPLY_TIME:
                iPage = postsFloorMapper.pageFloorDesc(page, reqVO.getPostsId());
                break;
            case CREATE_TIME:
            default:
                iPage = postsFloorMapper.pageFloorAsc(page, reqVO.getPostsId());
        }

        //设置楼层内容
        buildFloorContents(iPage.getRecords(), anonymousBoo, posts.getGroupId());

        return new PageResult(iPage.getRecords(), iPage.getTotal());
    }

    @Override
    public PageResult<CommentVO> pageComments(CommentsReqVO reqVO) {
        PostsFloorDO floor = postsFloorMapper.selectById(reqVO.getFloorId());
        if (ObjectUtil.isNull(floor)) {
            log.info("根据ID[{}],未匹配到对应的楼层信息", reqVO.getFloorId());
            throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
        }

        PostsDO posts = queryPosts(floor.getPostsId());
        if (ObjectUtil.isNull(posts)) {
            log.info("根据ID[{}],未匹配到贴子信息", floor.getPostsId());
            throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
        }

        boolean anonymousBoo = isAnonymous(posts.getPlate());
        //非匿名贴检查用户是否为公会成员
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (!anonymousBoo && ObjectUtil.isNotNull(userId)) {
            //非公会成员不可获取楼层内容 直接返回空
            if (!leagueApi.isLeagueMember(posts.getGroupId(), userId)) {
                return new PageResult(new ArrayList<CommentVO>(), 0L);
            }
        }

        return buildComments(reqVO, anonymousBoo, posts.getGroupId());
    }

    @Override
    public PostsFloorVO replyPosts(ReplyPostsReqVO reqVO) {
        PostsDO posts = queryPosts(reqVO.getPostsId());

        //检查用户是否有权限
        checkPlate(posts.getPlate(), posts.getGroupId());

        PostsFloorDO floor = new PostsFloorDO();
        floor.setMsgId(MsgTypeEnums.MSG_TYPE_1.getPrefix() + SNOWFLAKE.nextIdStr());
        floor.setPostsId(posts.getId());
        floor.setFloorNum(posts.getFloorCount() + 1);
        floor.setUserId(SecurityFrameworkUtils.getLoginUserId());
        floor.setUserIp(getClientIP());
        floor.setLandlordFlag(posts.getUserId().equals(SecurityFrameworkUtils.getLoginUserId()));
        floor.setCreateTime(LocalDateTime.now());
        floor.setUpdateTime(LocalDateTime.now());
        //插入一条回复
        postsFloorMapper.insert(floor);

        reqVO.getContent().stream().forEach(ctn -> {
            PostsFloorContentDO floorContentDO = new PostsFloorContentDO();
            floorContentDO.setFloorId(floor.getId());
            floorContentDO.setContent(JSONUtil.toJsonStr(ctn));
            floorContentDO.setOriginalText(EditorUtils.parseContent(ctn));
            postsFloorContentMapper.insert(floorContentDO);
        });

        //更新贴楼层
        posts.setNewReplyUserId(SecurityFrameworkUtils.getLoginUserId());
        postsMapper.updatePostsFloor(posts);

        //登记一条回复消息通知
        ForumMessageDO insertMsg = new ForumMessageDO()
                .setUserId(posts.getUserId())
                .setPostsId(posts.getId())
                .setFloorId(floor.getId())
                .setReplyId(floor.getId())
                .setReplyMsgId(floor.getMsgId())
                .setReceiveMsgType(MsgTypeEnums.MSG_TYPE_0)
                .setReceiveId(posts.getId())
                .setReplyUserId(SecurityFrameworkUtils.getLoginUserId())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        //插入消息
        forumMessageMapper.insert(insertMsg);

        //回帖时 回帖人和被回帖人加成长值
        replyPostAddGrowth(posts);
        return floor(posts.getPlate(), floor.getId(), posts.getGroupId());
    }

    /**
     * 回帖时 回帖人和被回帖人加成长值
     * @param posts
     */
    private void replyPostAddGrowth(PostsDO posts) {
        //板块是0公会板块  被回帖 发帖人和评论人都加积分(自己评论自己帖子不加)
        // 回帖和被回帖 时 如果是同一个帖子，多次评论回复只加一次积分
        if(ObjectUtil.equal(PLATE_0.getType(), posts.getPlate()) &&
                ObjectUtil.notEqual(posts.getUserId(),getLoginUserId())){
            //prefix+帖子id+回帖人id+被回帖人id
            String key = "GROWTH_VALUE_COUNT:REPLY_POST_COUNT:"+posts.getId()+"_"+ +getLoginUserId()+"_"+ posts.getUserId();
            if(ObjectUtil.isNotEmpty(redisUtils.get(key))){
                return;
            }
            GrowthDTO growthDTO = new GrowthDTO(posts.getGroupId(), posts.getUserId());
            growthRuleFactory.getBy(GrowthType.BY_REPLY_POST).growthValue(growthDTO, leagueApi::growthThenLevel);

            GrowthDTO growthDTO2 = new GrowthDTO(posts.getGroupId(), getLoginUserId());
            growthRuleFactory.getBy(GrowthType.REPLY_POST).growthValue(growthDTO2, leagueApi::growthThenLevel);

            redisUtils.setForTimeCustom(key, 1, 24, TimeUnit.HOURS);
        }
    }



    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommentVO reply(ReplyReqVO reqVO) {
        //根据回复消息的ID获取,回复类型
        MsgTypeEnums replyTypeEnum = obtainReplyType(reqVO.getMsgId());

        ReceiveBO receiveBO;
        switch (replyTypeEnum) {
            case MSG_TYPE_1:
                receiveBO = queryFloorSomeId(reqVO.getMsgId());
                break;
            case MSG_TYPE_2:
                receiveBO = queryCommentSomeId(reqVO.getMsgId());
                break;
            default:
                throw exception(BusinessErrorCodeConstants.MSG_TYPE_ERROR);
        }

        PostsDO posts = queryPosts(receiveBO.getPostsId());

        //检查用户是否有权限
        checkPlate(posts.getPlate(), posts.getGroupId());

        Long userId = SecurityFrameworkUtils.getLoginUserId();
        PostsCommentDO comment = new PostsCommentDO();
        comment.setMsgId(MsgTypeEnums.MSG_TYPE_2.getPrefix() + SNOWFLAKE.nextIdStr());
        comment.setMsgType(replyTypeEnum.getType());
        comment.setPostsId(receiveBO.getPostsId());
        comment.setFloorId(receiveBO.getFloorId());
        comment.setReceiveId(receiveBO.getId());
        comment.setUserId(userId);
        comment.setLandlordFlag(posts.getUserId().equals(userId));
        comment.setContent(reqVO.getContent());
        comment.setUserIp(getClientIP());
        comment.setReceiveUserId(receiveBO.getUserId());
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());

        postsCommentMapper.insert(comment);

        //更新贴热度
        postsMapper.updatePostsHot(posts);

        //登记一条回复消息通知
        ForumMessageDO insertMsg = new ForumMessageDO()
                .setUserId(receiveBO.getUserId())
                .setPostsId(posts.getId())
                .setFloorId(receiveBO.getFloorId())
                .setReplyId(comment.getId())
                .setReplyMsgId(comment.getMsgId())
                .setReceiveMsgType(replyTypeEnum)
                .setReceiveId(receiveBO.getId())
                .setReplyUserId(SecurityFrameworkUtils.getLoginUserId())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        //插入消息
        forumMessageMapper.insert(insertMsg);

        return comment(posts.getPlate(), comment.getId(), posts.getGroupId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(String msgId) {
        //根据回复消息的ID获取,回复类型
        MsgTypeEnums replyTypeEnum = obtainReplyType(msgId);

        switch (replyTypeEnum) {
            case MSG_TYPE_0:
                deletePosts(msgId);
                break;
            case MSG_TYPE_1:
                deleteFloor(msgId);
                break;
            case MSG_TYPE_2:
                deleteComment(msgId);
                break;
            default:
                throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
        }
    }

    @Override
    public List<HotPostsVO> realTimeHostPosts() {
        List<HotPostsVO> hotPosts = postsMapper.hotPosts(LocalDateTime.now().minusMonths(1L));
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        hotPosts.stream().forEach(item -> {
            //设置贴子内容(产品说显示100个字)
            List<PostsContentDO> contentData = postsContentMapper.selectByPostsId(item.getPostsId());
            //设置省略后的内容
            item.setPostsContent(buildContent(contentData, true));

            //设置贴子图片列表
            List<PostsImageDO> imageList = postsImageMapper.selectByPostsId(item.getPostsId());
            item.setImagesNum(imageList.size());
            item.setImages(buildImages(imageList));
            //设置贴子标签列表
            item.setLabels(postsMapper.selectPostsLabel(item.getPostsId()));

            //检查用户是否对当前贴子做过点赞
            item.setUserPraiseFlag(redisUtils.isMember(FORUM_POSTS_PRAISE + item.getPostsId(), userId));

            //检查用户是否对当前贴子做过点踩
            item.setUserTrampleFlag(redisUtils.isMember(FORUM_POSTS_TRAMPLE + item.getPostsId(), userId));

            //检查用户是否对当前贴子做过点踩
            item.setUserCollectionFlag(redisUtils.isMember(FORUM_POSTS_COLLECTION + item.getPostsId(), userId));

        });
        return hotPosts;
    }

    @Override
    public List<HotPostsVO> riseHotPosts() {
        //获取开始与结束时间
        LocalDateTime entTime = LocalDateTime.now().withNano(0);
        LocalDateTime startTime = entTime.minusMinutes(30L);

        List<HotPostsVO> hotPosts = postsRiseCountMapper.riseHotPosts(startTime, entTime);
        //检查30分钟内是否存在5条上升热贴,如果没有,则查询7天内的上升热点做补充
        int dif = 5 - hotPosts.size();
        if (dif > 0) {
            entTime = startTime;
            startTime = entTime.minusDays(7L);
            List<Long> postsIds = hotPosts.stream().map(HotPostsVO::getPostsId).collect(Collectors.toList());
            List<HotPostsVO> supplement = postsRiseCountMapper.riseHotPostsComplex(startTime, entTime, postsIds, dif);
            hotPosts.addAll(supplement);
        }
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        hotPosts.stream().forEach(item -> {
            //设置贴子内容(产品说显示100个字)
            List<PostsContentDO> contentData = postsContentMapper.selectByPostsId(item.getPostsId());
            //设置省略后的内容
            item.setPostsContent(buildContent(contentData, true));

            //设置贴子图片列表
            List<PostsImageDO> imageList = postsImageMapper.selectByPostsId(item.getPostsId());
            item.setImagesNum(imageList.size());
            item.setImages(buildImages(imageList));
            //设置贴子标签列表
            item.setLabels(postsMapper.selectPostsLabel(item.getPostsId()));

            //检查用户是否对当前贴子做过点赞
            item.setUserPraiseFlag(redisUtils.isMember(FORUM_POSTS_PRAISE + item.getPostsId(), userId));

            //检查用户是否对当前贴子做过点踩
            item.setUserTrampleFlag(redisUtils.isMember(FORUM_POSTS_TRAMPLE + item.getPostsId(), userId));

            //检查用户是否对当前贴子做过点踩
            item.setUserCollectionFlag(redisUtils.isMember(FORUM_POSTS_COLLECTION + item.getPostsId(), userId));
        });
        return hotPosts;
    }

    @Override
    public List<HotPostsVO> anonymousHotPosts() {
        List<HotPostsVO> hotPosts = postsMapper.anonymousHotPosts(LocalDateTime.now().minusMonths(1L));
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        hotPosts.stream().forEach(item -> {
            //设置贴子内容
            List<PostsContentDO> contentData = postsContentMapper.selectByPostsId(item.getPostsId());
            //设置省略后的内容
            item.setPostsContent(buildContent(contentData, true));

            //设置贴子图片列表
            List<PostsImageDO> imageList = postsImageMapper.selectByPostsId(item.getPostsId());
            item.setImagesNum(imageList.size());
            item.setImages(buildImages(imageList));
            //设置贴子标签列表
            item.setLabels(postsMapper.selectPostsLabel(item.getPostsId()));

            //匿名时不返回用户ID、头像、姓
            String anon = AnonymousUtil.md5Hex("" + item.getPostsId() + item.getUserId());
            item.setUserId(Long.valueOf(anon));
            item.setAvatar(null);
            item.setUserName(anon);
            item.setNickName(anon);
            item.setFirstName(anon);
            item.setLastName(null);

            //检查用户是否对当前贴子做过点赞
            item.setUserPraiseFlag(redisUtils.isMember(FORUM_POSTS_PRAISE + item.getPostsId(), userId));

            //检查用户是否对当前贴子做过点踩
            item.setUserTrampleFlag(redisUtils.isMember(FORUM_POSTS_TRAMPLE + item.getPostsId(), userId));

            //检查用户是否对当前贴子做过点踩
            item.setUserCollectionFlag(redisUtils.isMember(FORUM_POSTS_COLLECTION + item.getPostsId(), userId));

        });
        return hotPosts;
    }

    @Override
    public MessageDetailsVO floor(Long postsId, Long floorId) {
        //查询贴子信息
        PostsDO posts = postsMapper.selectById(postsId);
        if (ObjectUtil.isNull(posts)) {
            log.info("贴子[{}]不存在,或已删除", postsId);
            throw exception(BusinessErrorCodeConstants.POST_DELETED);
        }

        return BeanUtil.copyProperties(floor(posts.getPlate(), floorId,posts.getGroupId()), MessageDetailsVO.class)
                .setPlate(posts.getPlate())
                .setGroupId(posts.getGroupId());
    }

    @Override
    public void hotScanSwitch(HotScanSwitchReqVO reqVO) {
        PostsDO posts = postsMapper.selectById(reqVO.getPostsId());
        if (ObjectUtil.isNull(posts)) {
            return;
        }
        //检查归属
        checkBelongTo(posts.getUserId());
        //更新热贴推荐开/关,每次开关取反（每次用户点击不是刚好是触发相反的状态）
        postsMapper.updatePostsHotSwitch(posts.getId(), !posts.getHotSearchSwitch());
    }

    @Override
    public void praise(PraiseReqVO reqVO) {
        //根据回复消息的ID获取被点赞/取消点赞的消息类型(贴子、楼层、讨论)
        MsgTypeEnums msgTypeEnums = obtainReplyType(reqVO.getMsgId());
        switch (msgTypeEnums) {
            case MSG_TYPE_0:
                praisePosts(reqVO.getMsgId());
                break;
            case MSG_TYPE_1:
                praiseFloor(reqVO.getMsgId());
                break;
            case MSG_TYPE_2:
                praiseComment(reqVO.getMsgId());
                break;
            default:
                throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
        }
    }

    @Override
    public void trample(TrampleReqVO reqVO) {
        //根据回复消息的ID获取被点踩/取消点踩的消息类型(贴子、楼层、讨论)
        MsgTypeEnums msgTypeEnums = obtainReplyType(reqVO.getMsgId());
        switch (msgTypeEnums) {
            case MSG_TYPE_0:
                tramplePosts(reqVO.getMsgId());
                break;
            case MSG_TYPE_1:
                trampleFloor(reqVO.getMsgId());
                break;
            case MSG_TYPE_2:
                trampleComment(reqVO.getMsgId());
                break;
            default:
                throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
        }
    }

    @Override
    public PageResult<PostsVO> labelPosts(LabelPostsReqVO reqVO) {
        Page<PostsVO> page = new Page<>(reqVO.getPageNo(),
                reqVO.getPageSize());
        IPage<PostsVO> iPage = postsMapper.pageLabelPosts(page, reqVO.getLabelId());
        //组装贴子列表数据
        buildPostsList(iPage);
        return new PageResult(iPage.getRecords(), iPage.getTotal());
    }

    @Override
    public Long collection(PostsCollectionReqVO reqVO) {
        String collectionKey = FORUM_POSTS_COLLECTION + reqVO.getPostsId();

        Long userId = SecurityFrameworkUtils.getLoginUserId();
        //如果存在收藏记录,则说明此次是做取消收藏操作
        if(redisUtils.isMember(collectionKey, userId)){
            //删除收藏分组关系
            PostsCollectionDO collection = postsCollectionMapper.selectByUser(userId, reqVO.getPostsId());
            if(ObjectUtil.isNull(collection)){
                return collection.getId();
            }
            //减少一次收藏记录
            postsMapper.collectSubtractOne(reqVO.getPostsId());
            //删除收藏分组关系
            favoriteGroupApi.deleteFavoriteRelation(collection.getId(),"P");
            //删除收藏信息
            postsCollectionMapper.deleteByUser(userId, reqVO.getPostsId());
            //取消收藏
            redisUtils.remove(collectionKey, userId);
            return collection.getId();
        }else{
            //增加一次收藏记录
            postsMapper.collectPlusOne(reqVO.getPostsId());
            //记录一次收藏信息
            PostsCollectionDO collection =  new PostsCollectionDO()
                    .setUserId(userId)
                    .setPostsId(reqVO.getPostsId())
                    .setCreateTime(LocalDateTime.now());
            postsCollectionMapper.insert(collection);
            //增加分组关系
            favoriteGroupApi.updateFavoriteGroupRelation(new GroupRelationUpdateReqDTO()
                    .setFavoriteId(collection.getId())
                    .setGroupIds(reqVO.getGroupIds())
                    .setContentId(reqVO.getPostsId())
                    .setType("P")
            );
            //点赞(记录redis)
            redisUtils.add(collectionKey, userId);
            return collection.getId();
        }
    }

    @Override
    public void batchCancelCollection(BatchCancelCollectionReqVO reqVO) {
        if(CollUtil.isEmpty(reqVO.getPostsIds())){
            log.info("无取消收藏的数据列表!");
            return;
        }

        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if(ObjectUtil.isNull(userId)){
            log.info("获取登录用户信息失败");
            return;
        }
        reqVO.getPostsIds().stream().forEach(item->{
            //获取redis收藏key
            String collectionKey = FORUM_POSTS_COLLECTION + item;
            //根据贴子ID 与 用户ID 查询收藏信息
            PostsCollectionDO collection = postsCollectionMapper.selectByUser(userId, item);
            if(ObjectUtil.isNull(collection)){
                return;
            }
            //减少一次收藏记录
            postsMapper.collectSubtractOne(item);
            //删除收藏分组关系
            favoriteGroupApi.deleteFavoriteRelation(collection.getId(),"P");
            //删除收藏信息
            postsCollectionMapper.deleteByUser(userId, item);
            //取消收藏
            redisUtils.remove(collectionKey, userId);
        });

    }

    @Override
    public PageResult<PostsVO> pagePostsCollection(PagePostsCollectionReqVO reqVO) {
        //前端（产品）说他的组件查全部组ID会是0,吖的，对于我ID有值，我就认为他是查分组数据，所以前端传了0我就去掉0这个分组直接查全部
        if(reqVO.getGroupId() != null && reqVO.getGroupId().equals(0L)){
            reqVO.setGroupId(null);
        }
        Page<PostsVO> page = new Page<>(reqVO.getPageNo(),
                reqVO.getPageSize());
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        IPage<PostsVO> iPage = postsMapper.pagePostsCollection(page, userId,reqVO.getGroupId());
        //组装贴子列表数据
        buildPostsList(iPage);
        return new PageResult(iPage.getRecords(), iPage.getTotal());
    }

    /**
     * 贴子点赞/取消点赞
     * @param msgId
     */
    private void praisePosts(String msgId){
        //根据msgId查询贴子信息
        PostsDO posts = postsMapper.selectByMsgId(msgId);
        if(ObjectUtil.isNull(posts)){
            throw exception(BusinessErrorCodeConstants.POSTS_NOT_EXIST);
        }

        Long userId = SecurityFrameworkUtils.getLoginUserId();

        String postsPraiseKey = FORUM_POSTS_PRAISE + posts.getId();
        //如果存在点赞记录,则说明此次是做取消点点赞操作
        if(redisUtils.isMember(postsPraiseKey, userId)){
            //减少一次点赞记录
            postsMapper.praiseSubtractOne(posts.getId());
            //取消点赞
            redisUtils.remove(postsPraiseKey, userId);
        }else{
            //增加一次点赞记录
            postsMapper.praisePlusOne(posts.getId());
            //记录一次点赞流水
            PostsPraiseRecordDO postsPraiseRecord = new PostsPraiseRecordDO()
                    .setPostsId(posts.getId())
                    .setUserId(userId)
                    .setCreateTime(LocalDateTime.now());
            postsPraiseRecordMapper.insert(postsPraiseRecord);
            //点赞(记录redis)
            redisUtils.add(postsPraiseKey, userId);
            //点赞的话,需要检查用户有没有做过点踩,如果有,还需要取想点踩
            String postsTrampleKey = FORUM_POSTS_TRAMPLE + posts.getId();
            if(redisUtils.isMember(postsTrampleKey, userId)){
                //点踩-1
                postsMapper.trampleSubtractOne(posts.getId());
                //取消点踩
                redisUtils.remove(postsTrampleKey, userId);
            }
        }
    }

    /**
     * 楼层点赞/取消点赞
     * @param msgId
     */
    private void praiseFloor(String msgId){
        //根据msgId查询楼层信息
        PostsFloorDO floor = postsFloorMapper.selectByMsgId(msgId);
        if(ObjectUtil.isNull(floor)){
            throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
        }

        Long userId = SecurityFrameworkUtils.getLoginUserId();

        String floorPraiseKey = FORUM_FLOOR_PRAISE + floor.getId();
        //如果已存在点赞记录则此次为取消点赞,否则为点赞
        if(redisUtils.isMember(floorPraiseKey, userId)){
            //点赞记录-1
            postsFloorMapper.praiseSubtractOne(floor.getId());
            //取消点赞(删除redis)
            redisUtils.remove(floorPraiseKey, userId);
        }else{
            //点赞记录+1
            postsFloorMapper.praisePlusOne(floor.getId());
            //插入一条点赞记录
            PostsFloorPraiseRecordDO insert = new PostsFloorPraiseRecordDO()
                    .setPostsId(floor.getPostsId())
                    .setFloorId(floor.getId())
                    .setUserId(userId)
                    .setCreateTime(LocalDateTime.now());
            postsFloorPraiseRecordMapper.insert(insert);
            //点赞(记录redis)
            redisUtils.add(floorPraiseKey, userId);
            //点赞的话,需要检查用户有没有做过点踩,如果有,还需要取想点踩
            String floorTrampleKey = FORUM_FLOOR_TRAMPLE + floor.getId();
            if(redisUtils.isMember(floorTrampleKey, userId)){
                //点踩-1
                postsFloorMapper.trampleSubtractOne(floor.getId());
                //取消点踩
                redisUtils.remove(floorTrampleKey, userId);
            }
        }
    }

    /**
     * 讨论点赞/取消点赞
     * @param msgId
     */
    private void praiseComment(String msgId){
        //根据msgId查询讨论信息
        PostsCommentDO comment = postsCommentMapper.selectByMsgId(msgId);
        if(ObjectUtil.isNull(comment)){
            throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
        }

        //用户ID
        Long userId = SecurityFrameworkUtils.getLoginUserId();

        String commentPraiseKey = FORUM_COMMENT_PRAISE + comment.getId();
        //如果已存在点赞记录则此次为取消点赞,否则为点赞
        if(redisUtils.isMember(commentPraiseKey, userId)){
            //点赞-1
            postsCommentMapper.praisePlusOne(comment.getId());
            //取消点赞
            redisUtils.remove(commentPraiseKey, userId);
        }else{
            //点赞+1
            postsCommentMapper.praisePlusOne(comment.getId());
            //插入一条点赞记录
            PostsCommentPraiseRecordDO insert = new PostsCommentPraiseRecordDO()
                    .setPostsId(comment.getPostsId())
                    .setCommentId(comment.getId())
                    .setUserId(userId)
                    .setCreateTime(LocalDateTime.now());
            postsCommentPraiseRecordMapper.insert(insert);
            //点赞(记录redis)
            redisUtils.add(commentPraiseKey, userId);
            //点赞的话,需要检查用户有没有做过点踩,如果有,还需要取想点踩
            String commentTrampleKey = FORUM_COMMENT_TRAMPLE + comment.getId();
            if(redisUtils.isMember(commentTrampleKey, userId)){
                //点踩-1
                postsCommentMapper.trampleSubtractOne(comment.getId());
                //取消点踩
                redisUtils.remove(commentTrampleKey, userId);
            }
        }
    }

    /**
     * 贴子点踩/取消点踩
     * @param msgId
     */
    private void tramplePosts(String msgId){
        //根据msgId查询贴子信息
        PostsDO posts = postsMapper.selectByMsgId(msgId);
        if(ObjectUtil.isNull(posts)){
            throw exception(BusinessErrorCodeConstants.POSTS_NOT_EXIST);
        }

        Long userId = SecurityFrameworkUtils.getLoginUserId();

        String postsTrampleKey = FORUM_POSTS_TRAMPLE + posts.getId();
        //如果已存在点踩记录则此次为取消点踩,否则为点踩
        if(redisUtils.isMember(postsTrampleKey, userId)){
            //点踩-1
            postsMapper.trampleSubtractOne(posts.getId());
            //取消点踩
            redisUtils.remove(postsTrampleKey, userId);
        }else{
            //点踩+1
            postsMapper.tramplePlusOne(posts.getId());
            //插入一条点赞记录
            PostsTrampleRecordDO insert = new PostsTrampleRecordDO()
                    .setPostsId(posts.getId())
                    .setUserId(userId)
                    .setCreateTime(LocalDateTime.now());

            postsTrampleRecordMapper.insert(insert);
            //点踩
            redisUtils.add(postsTrampleKey, userId);
            //点踩时如果用户有点赞需要取消掉用户原来的点赞
            String postsPraiseKey = FORUM_POSTS_PRAISE + posts.getId();
            if(redisUtils.isMember(postsPraiseKey, userId)){
                //点赞-1
                postsMapper.praiseSubtractOne(posts.getId());
                //取消点赞
                redisUtils.remove(postsPraiseKey, userId);
            }
        }
    }

    /**
     * 贴子点踩/取消点踩
     * @param msgId
     */
    private void trampleFloor(String msgId){
        //根据msgId查询楼层信息
        PostsFloorDO floor = postsFloorMapper.selectByMsgId(msgId);
        if(ObjectUtil.isNull(floor)){
            throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
        }

        Long userId = SecurityFrameworkUtils.getLoginUserId();

        String floorTrampleKey = FORUM_FLOOR_TRAMPLE + floor.getId();
        //如果已存在点踩记录则此次为取消点踩,否则为点踩
        if(redisUtils.isMember(floorTrampleKey, userId)){
            //点踩-1
            postsFloorMapper.trampleSubtractOne(floor.getId());
            //取消点踩
            redisUtils.remove(floorTrampleKey, userId);
        }else{
            //点踩+1
            postsFloorMapper.tramplePlusOne(floor.getId());
            //插入一条点赞记录
            PostsFloorTrampleRecordDO insert = new PostsFloorTrampleRecordDO()
                    .setPostsId(floor.getPostsId())
                    .setFloorId(floor.getId())
                    .setUserId(userId)
                    .setCreateTime(LocalDateTime.now());
            postsFloorTrampleRecordMapper.insert(insert);
            //点踩
            redisUtils.add(floorTrampleKey, userId);
            //检查用户是否有过点赞,有则取消点赞
            String floorPraiseKey = FORUM_FLOOR_PRAISE + floor.getId();
            if(redisUtils.isMember(floorPraiseKey, userId)){
                //点赞-1
                postsFloorMapper.praiseSubtractOne(floor.getId());
                //取消点赞
                redisUtils.remove(floorPraiseKey, userId);
            }
        }
    }

    /**
     * 讨论点踩/取消点踩
     * @param msgId
     */
    private void trampleComment(String msgId){
        //根据msgId查询讨论信息
        PostsCommentDO comment = postsCommentMapper.selectByMsgId(msgId);
        if(ObjectUtil.isNull(comment)){
            throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
        }

        Long userId = SecurityFrameworkUtils.getLoginUserId();

        String commentTrampleKey = FORUM_COMMENT_TRAMPLE + comment.getId();
        //如果已存在点踩记录则此次为取消点踩,否则为点踩
        if(redisUtils.isMember(commentTrampleKey, userId)){
            //点踩-1
            postsCommentMapper.trampleSubtractOne(comment.getId());
            //取消点踩
            redisUtils.remove(commentTrampleKey, userId);
        }else{
            //点踩+1
            postsCommentMapper.tramplePlusOne(comment.getId());
            //插入一条点赞记录
            PostsCommentTrampleRecordDO insert = new PostsCommentTrampleRecordDO()
                    .setPostsId(comment.getPostsId())
                    .setCommentId(comment.getId())
                    .setUserId(userId)
                    .setCreateTime(LocalDateTime.now());
            postsCommentTrampleRecordMapper.insert(insert);
            //点踩
            redisUtils.add(commentTrampleKey, userId);
            //检查用户是否有过点赞,有则取消点赞
            String commentPraiseKey = FORUM_COMMENT_PRAISE + comment.getId();
            if(redisUtils.isMember(commentPraiseKey, userId)){
                //点赞-1
                postsCommentMapper.praiseSubtractOne(comment.getId());
                //取消点赞
                redisUtils.remove(commentPraiseKey, userId);
            }
        }
    }

    /**
     * 删除贴子
     *
     * @param msgId
     */
    private void deletePosts(String msgId) {
        PostsDO posts = postsMapper.selectByMsgId(msgId);
        if (ObjectUtil.isNull(posts)) {
            return;
        }

        //检查归属
        checkBelongTo(posts.getUserId());
        //删除贴子
        postsMapper.deleteById(posts.getId());
        postsContentMapper.deleteByPostsId(posts.getId());
        //检查贴子是否有设置热点,若有则热点减1,且删除热点信息
        List<PostsLabelRelationDO> relations = postsLabelRelationMapper.selectByPostsId(posts.getId());
        if(CollUtil.isNotEmpty(relations)){
            //标签热度-1
            relations.stream().forEach(item->forumLabelMapper.hotSubtractOne(item.getId()));
            //删除贴子与标签的关联关联
            postsLabelRelationMapper.deleteByPostsId(posts.getId());
        }
    }

    /**
     * 删除楼层
     *
     * @param msgId
     */
    private void deleteFloor(String msgId) {
        PostsFloorDO floor = postsFloorMapper.selectByMsgId(msgId);
        if (ObjectUtil.isNull(floor)) {
            return;
        }

        //检查归属
        checkBelongTo(floor.getUserId());

        postsFloorMapper.deleteById(floor.getId());
        postsFloorContentMapper.deleteByFloorIdId(floor.getId());

        //删除楼层时，把楼层里的回复也一并删除，避免以后产品要实时统计贴子回复量时，有异常
        int res = postsCommentMapper.deleteByFloorId(floor.getId());

        //扣减贴子回复数
        subCommentNum(floor.getPostsId(), 1 + res);

    }

    /**
     * 删除讨论
     *
     * @param msgId
     */
    private void deleteComment(String msgId) {
        PostsCommentDO comment = postsCommentMapper.selectByMsgId(msgId);
        if (ObjectUtil.isNull(comment)) {
            return;
        }

        //检查归属
        checkBelongTo(comment.getUserId());

        postsCommentMapper.deleteById(comment.getId());

        //扣减贴子回复数
        subCommentNum(comment.getPostsId(), 1);
    }


    /**
     * 扣减贴子回复数
     *
     * @param postsId
     * @param num
     */
    private void subCommentNum(Long postsId, int num) {
        postsMapper.subCommentNum(postsId, num);
    }

    /**
     * 根据消息ID获取回复类型
     *
     * @param msgId
     * @return
     */
    private MsgTypeEnums obtainReplyType(String msgId) {
        return MsgTypeEnums.valueOfPrefix(msgId.substring(0, 6));
    }

    /**
     * 检查归属权（贴子、楼层、讨论）是否为当前登录用户所有
     *
     * @param userId
     */
    private void checkBelongTo(Long userId) {
        if (!ObjectUtil.equals(userId, SecurityFrameworkUtils.getLoginUserId())) {
            throw exception(BusinessErrorCodeConstants.NO_ACCESS);
        }
    }

    /**
     * 查询楼层的一些相关ID,贴子ID 楼层ID 用户ID
     *
     * @param msgId
     * @return
     */
    private ReceiveBO queryFloorSomeId(String msgId) {
        PostsFloorDO floor = postsFloorMapper.selectByMsgId(msgId);
        if (ObjectUtil.isNull(floor)) {
            throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
        }

        return new ReceiveBO().setId(floor.getId())
                .setPostsId(floor.getPostsId())
                .setFloorId(floor.getId())
                .setUserId(floor.getUserId());
    }

    /**
     * 查询讨论的一些相关ID 贴子ID 楼层ID 用户ID
     *
     * @param msgId
     * @return
     */
    private ReceiveBO queryCommentSomeId(String msgId) {
        PostsCommentDO comment = postsCommentMapper.selectByMsgId(msgId);
        if (ObjectUtil.isNull(comment)) {
            throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
        }

        return new ReceiveBO().setId(comment.getId())
                .setPostsId(comment.getPostsId())
                .setFloorId(comment.getFloorId())
                .setUserId(comment.getUserId());
    }


    /**
     * 根据ID查询贴子信息（不查询贴内容）
     *
     * @param postsId
     * @return
     */
    private PostsDO queryPosts(Long postsId) {
        //查询贴子信息
        PostsDO posts = postsMapper.selectById(postsId);
        if (ObjectUtil.isNull(posts)) {
            log.info("贴子[{}]不存在", postsId);
            throw exception(BusinessErrorCodeConstants.POSTS_NOT_EXIST);
        }

        return posts;
    }

    /**
     * 设置楼层内容
     *
     * @param list
     * @param <T>
     */
    private <T extends PostsFloorVO> void buildFloorContents(List<T> list, Boolean boo, Long leagueId) {
        list.stream().forEach(item -> buildFloorContent(item, boo, leagueId));
    }

    /**
     * 组织楼层数据
     *
     * @param floor
     * @param boo
     * @param <T>
     */
    private <T extends PostsFloorVO> void buildFloorContent(PostsFloorVO floor, Boolean boo, Long leagueId) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();

        //设置楼层内容
        List<PostsFloorContentDO> contentList = postsFloorContentMapper.selectByFloorId(floor.getFloorId());
        List<Content> contents = contentList.stream()
                .map(PostsFloorContentDO::getContent)
                .map(ctn -> JSONUtil.toBean(ctn, Content.class))
                .collect(Collectors.toList());
        floor.setFloorContent(contents);

        //设置每层的讨论信息
        PageResult<CommentVO> pageComments = pageComments(new CommentsReqVO().setFloorId(floor.getFloorId()), boo, leagueId);
        floor.setPageComments(pageComments);
        floor.setCommentNum(pageComments.getTotal().intValue());

        if (ObjectUtil.isNotNull(userId)) {
            //设置楼层归属
            floor.setBelongToFlag(floor.getUserId().equals(userId));
        }

        //检查用户是否对当前贴子做过点赞
        floor.setUserPraiseFlag(redisUtils.isMember(FORUM_FLOOR_PRAISE + floor.getFloorId(), userId));

        //检查用户是否对当前贴子做过点踩
        floor.setUserTrampleFlag(redisUtils.isMember(FORUM_FLOOR_TRAMPLE + floor.getFloorId(), userId));

        //匿名处理
        if (boo) {
            String anon = AnonymousUtil.md5Hex("" + floor.getPostsId() + floor.getUserId());
            //匿名时不返回用户ID、头像、姓
            floor.setUserId(Long.valueOf(anon));
            floor.setAvatar(null);
            floor.setUserName(anon);
            floor.setNickName(anon);
            floor.setFirstName(anon);
            floor.setLastName(null);
        }

        //非匿名贴,获取发贴人在当前贴,当前公会的等级与等级名称
        if(!boo && ObjectUtil.isNotNull(userId)){
            UserGrowthLevelDTO userGrowthLeve = leagueApi.getUserGrowthInfo(leagueId, floor.getUserId());
            if(ObjectUtil.isNotNull(userGrowthLeve)){
                floor.setUserLevelName(userGrowthLeve.getLevelName());
                floor.setUserGrowthLevel(userGrowthLeve.getGrowthLevel());
            }
        }
    }

    /**
     * 查询用户访问权限
     */
    private Boolean checkPlate(String plate, Long groupId) {

        if (StrUtil.isBlank(plate)) {
            log.info("板块[{}]错误", plate);
            throw exception(BusinessErrorCodeConstants.PLATE_ERR);
        }

        boolean boo = false;
        switch (PlateEnums.valueOfType(plate)) {
            case PLATE_0:
                checkAccessRights(groupId);
                break;
            case PLATE_1:
                boo = true;
                break;
            default:
                throw exception(BusinessErrorCodeConstants.PLATE_ERR);
        }

        return boo;
    }

    /**
     * 是否为匿名贴
     *
     * @param type
     * @return
     */
    private Boolean isAnonymous(String type) {
        return PLATE_1.equals(PlateEnums.valueOfType(type));
    }

    /**
     * 公会贴需要检查用户是否具有公会权限
     *
     * @param groupId
     */
    private void checkAccessRights(Long groupId) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        //非公会成员不允许获取,公会贴子列表
        if (!leagueApi.isLeagueMember(groupId, userId)) {
            log.info("用户[{}],非公会[{}]成员,拒绝获取公会贴子信息", userId, groupId);
            throw exception(BusinessErrorCodeConstants.IS_NOT_LEAGUE_MEMBER);
        }
    }

    /**
     * 设置贴子图片列表
     *
     * @param imageList
     * @return
     */
    private List<String> buildImages(List<PostsImageDO> imageList) {
        int i = 0;
        List<String> images = new ArrayList<>(3);
        for (PostsImageDO image : imageList) {
            i++;
            images.add(image.getImageUrl());
            if (i >= 3) {
                break;
            }
        }
        return images;
    }

    /**
     * 组织贴子内容
     *
     * @param contentList
     * @param hideBoo
     * @return
     */
    private List<Content> buildContent(List<PostsContentDO> contentList, boolean hideBoo) {
        //隐藏内容处理
        if (hideBoo) {
            //就跟产品说了，公会贴非公会成员可以看到第一行，产品硬要只可以展示100个字，查个列表还要逐行解析内容
            //计算出第100个字符的行数
            int len = 0;
            Content content = new Content();
            //设置ID(缩略内容默认ID为0)
            content.setId("0");
            //设置内容类型(缩略内容默认为text)
            content.setType(RichTextTypeEnum.PARAGRAPH.getType());
            StringBuffer textSb = new StringBuffer();
            for (PostsContentDO contentDO : contentList) {
                String text = contentDO.getOriginalText();
                if (StrUtil.isBlank(text)) {
                    continue;
                }
                int nowLen = text.length();
                //每行长度相加，直到超过100
                len = len + nowLen;
                if (len > 100) {
                    //计算出总长度超过100多少
                    int distance = len - 100;
                    //对当前行进行截取，剔除超过100个字的内容
                    String strSub = text.substring(0, nowLen - distance);
                    textSb.append(strSub);
                    textSb.append("...");
                    break;
                } else {
                    textSb.append(text);
                }
            }
            Content.Data data = new Content.Data();
            data.setText(textSb.toString());
            content.setData(data);
            //隐藏内容,设置处理后的数据
            List<Content> contents = new ArrayList<>(1);
            contents.add(content);
            return contents;
        } else {
            //不隐藏内容的,直接设置响应内容
            List<Content> contents = contentList.stream()
                    .map(PostsContentDO::getContent)
                    .map(ctn -> JSONUtil.toBean(ctn, Content.class))
                    .collect(Collectors.toList());

            return contents;
        }
    }

    /**
     * 分页查询讨论内容（已明确匿名）
     *
     * @param reqVO
     * @param boo
     * @return
     */
    public PageResult<CommentVO> pageComments(CommentsReqVO reqVO, Boolean boo, Long leagueId) {
        return buildComments(reqVO, boo, leagueId);
    }

    /**
     * 设置讨论内容
     *
     * @param reqVO
     * @param boo
     * @return
     */
    private PageResult<CommentVO> buildComments(CommentsReqVO reqVO, Boolean boo, Long leagueId) {
        Page<CommentVO> page = new Page<>(reqVO.getPageNo(),
                reqVO.getPageSize());
        IPage<CommentVO> iPage = postsCommentMapper.pageComments(page, reqVO.getFloorId());

        iPage.getRecords().stream().forEach(item -> buildComment(boo, item, leagueId));
        return new PageResult(iPage.getRecords(), iPage.getTotal());
    }

    /**
     * 查询楼层信息
     *
     * @param floorId
     * @return
     */
    private PostsFloorVO floor(String plate, Long floorId, Long leagueId) {
        //获取匿名标识
        boolean anonymousBoo = isAnonymous(plate);

        //查询楼层数据
        PostsFloorVO floor = postsFloorMapper.floor(floorId);
        if (ObjectUtil.isNull(floor)) {
            log.info("楼层[{}]不存在,或已删除", floorId);
            throw exception(BusinessErrorCodeConstants.CONTENT_DELETED);
        }
        //设置楼层内容
        buildFloorContent(floor, anonymousBoo, leagueId);

        return floor;
    }

    /**
     * 查询回复信息
     *
     * @param plate
     * @param commentId
     * @return
     */
    private CommentVO comment(String plate, Long commentId, Long league) {
        //获取匿名标识
        boolean anonymousBoo = isAnonymous(plate);
        //查询单条回复内容
        CommentVO comment = postsCommentMapper.comment(commentId);
        //组织回复内容
        buildComment(anonymousBoo, comment,league);
        return comment;
    }

    /**
     * 组装回复信息
     *
     * @param anonymousBoo
     * @param comment
     */
    private void buildComment(Boolean anonymousBoo, CommentVO comment, Long leagueId) {
        //获取用户ID
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        //设置归属
        if (ObjectUtil.isNotNull(userId)) {
            comment.setBelongToFlag(comment.getUserId().equals(userId));
        }
        //匿名处理
        if (anonymousBoo) {
            String anon = AnonymousUtil.md5Hex("" + comment.getPostsId() + comment.getUserId());
            //匿名时不返回用户ID 与 头像 也不需要 姓
            comment.setUserId(Long.valueOf(anon));
            comment.setAvatar(null);
            comment.setUserName(anon);
            comment.setNickName(anon);
            comment.setFirstName(anon);
            comment.setLastName(null);

            String recAnon = AnonymousUtil.md5Hex("" + comment.getPostsId() + comment.getReceiveUserId());
            comment.setReceiveUserId(Long.valueOf(recAnon));
            comment.setReceiveAvatar(null);
            comment.setReceiveUserName(recAnon);
            comment.setReceiveNickName(recAnon);
            comment.setReceiveFirstName(recAnon);
            comment.setReceiveLastName(null);
        }

        //检查用户是否对当前贴子做过点赞
        comment.setUserPraiseFlag(redisUtils.isMember(FORUM_COMMENT_PRAISE + comment.getCommentId(), userId));

        //检查用户是否对当前贴子做过点踩
        comment.setUserTrampleFlag(redisUtils.isMember(FORUM_COMMENT_TRAMPLE + comment.getCommentId(), userId));

        //非匿名贴,获取发贴人在当前贴,当前公会的等级与等级名称
        if(!anonymousBoo && ObjectUtil.isNotNull(userId)){
            UserGrowthLevelDTO userGrowthLeve = leagueApi.getUserGrowthInfo(leagueId, comment.getUserId());
            if(ObjectUtil.isNotNull(userGrowthLeve)){
                comment.setUserLevelName(userGrowthLeve.getLevelName());
                comment.setUserGrowthLevel(userGrowthLeve.getGrowthLevel());
            }
        }
    }

    /**
     * 保存贴子信息
     * @param reqVO
     */
    private void savePosts(CreatePostsReqVO reqVO){
        PostsDO posts = new PostsDO();
        posts.setMsgId(MsgTypeEnums.MSG_TYPE_0.getPrefix() + SNOWFLAKE.nextIdStr());
        posts.setUserId(getLoginUserId());
        posts.setGroupId(reqVO.getGroupId());
        posts.setPlate(reqVO.getPlate());
        posts.setDistrict(reqVO.getDistrictId());
        posts.setPostsTitle(reqVO.getPostsTitle());
        posts.setUserIp(getClientIP());
        posts.setCreateTime(LocalDateTime.now());
        posts.setUpdateTime(LocalDateTime.now());
        posts.setReplyTime(LocalDateTime.now());
        posts.setHotSearchSwitch(reqVO.getHotSwitch());
        //产品说的，贴子没人回复的时候，回复人就是发贴人
        posts.setNewReplyUserId(SecurityFrameworkUtils.getLoginUserId());

        postsMapper.insert(posts);

        reqVO.getContent().stream().forEach(ctn -> {
            //内容如果是图片在单独存一张表,方便之后查询使用
            if (ctn.getType().equals(RichTextTypeEnum.IMAGE.getType())) {
                String imageUrl = ctn.getData().getFile().getUrl();
                PostsImageDO postsImageDO = new PostsImageDO();
                postsImageDO.setPostsId(posts.getId());
                postsImageDO.setCreateTime(LocalDateTime.now());
                postsImageDO.setUpdateTime(LocalDateTime.now());
                postsImageDO.setImageUrl(imageUrl);
                postsImageMapper.insert(postsImageDO);
            }

            //将贴子内容存入内容表
            PostsContentDO postsContentDO = new PostsContentDO();
            postsContentDO.setPostsId(posts.getId());
            postsContentDO.setContent(JSONUtil.toJsonStr(ctn));
            postsContentDO.setOriginalText(EditorUtils.parseContent(ctn));
            postsContentMapper.insert(postsContentDO);
        });

        //标签为空时,贴子保存处理完成
        if(CollUtil.isEmpty(reqVO.getLabels())){
            return;
        }
        //添加贴子与标签关系
        List<String> labels = reqVO.getLabels().stream().distinct().collect(Collectors.toList());
        labels.stream().forEach(item->{
            ForumLabelDO labelDO = forumLabelMapper.selectByName(item);
            //未匹配到标签时,新增一条标签记录
            if(ObjectUtil.isNull(labelDO)){
                labelDO = new ForumLabelDO()
                        .setLabelName(item)
                        .setHotNum(0L)
                        .setCreateTime(LocalDateTime.now());
                forumLabelMapper.insert(labelDO);
            }

            //已存在，或已新增标签后，插入标签与贴子关系
            PostsLabelRelationDO relationDO = new PostsLabelRelationDO()
                    .setLabelId(labelDO.getId())
                    .setPostsId(posts.getId())
                    .setCreateTime(LocalDateTime.now());
            postsLabelRelationMapper.insert(relationDO);

            //标签热度+1
            forumLabelMapper.hotPlusOne(labelDO.getId());
        });
    }

    /**
     * 组装贴子列表数据
     * @param iPage
     */
    public void buildPostsList(IPage<PostsVO> iPage){
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        iPage.getRecords().forEach(item -> {
            //判断当前贴子是否为匿名贴
            boolean anonymousBoo = isAnonymous(item.getPlate());
            //设置贴子内容
            List<PostsContentDO> contentList = postsContentMapper.selectByPostsId(item.getPostsId());
            //列表内容直接做隐藏 设置true
            item.setPostsContent(buildContent(contentList, true));

            //设置贴子图片列表
            List<PostsImageDO> imageList = postsImageMapper.selectByPostsId(item.getPostsId());
            item.setImagesNum(imageList.size());
            item.setImages(buildImages(imageList));
            //设置贴子拥用的标签
            item.setLabels(postsMapper.selectPostsLabel(item.getPostsId()));

            //匿名处理
            if (anonymousBoo) {
                //匿名时不返回用户ID、头像、姓
                String anon = AnonymousUtil.md5Hex("" + item.getPostsId() + item.getUserId());
                item.setUserId(Long.valueOf(anon));
                item.setAvatar(null);
                item.setUserName(anon);
                item.setNickName(anon);
                item.setFirstName(anon);
                item.setLastName(null);

                String reAnon = AnonymousUtil.md5Hex("" + item.getPostsId() + item.getNewReplyUserId());
                //匿名时不返回用户ID、头像、姓
                item.setNewReplyUserId(Long.valueOf(reAnon));
                item.setNewReplyAvatar(null);
                item.setNewReplyUserName(reAnon);
                item.setNewReplyNickName(reAnon);
                item.setNewReplyFirstName(reAnon);
                item.setNewReplyLastName(null);
            }

            //检查用户是否对当前贴子做过点赞
            item.setUserPraiseFlag(redisUtils.isMember(FORUM_POSTS_PRAISE + item.getPostsId(), userId));

            //检查用户是否对当前贴子做过点踩
            item.setUserTrampleFlag(redisUtils.isMember(FORUM_POSTS_TRAMPLE + item.getPostsId(), userId));

            //检查用户是否对当前贴子做过收藏
            item.setUserCollectionFlag(redisUtils.isMember(FORUM_POSTS_COLLECTION + item.getPostsId(), userId));
            //用户做过收藏的贴子,返回收藏ID
            if(item.getUserCollectionFlag()){
                PostsCollectionDO postsCollection = postsCollectionMapper.selectByUser(userId, item.getPostsId());
                if(ObjectUtil.isNotNull(postsCollection)){
                    item.setUserCollectionId(postsCollection.getId());
                }
            }
        });
    }
}
