package com.hisun.kugga.duke.forum.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.entity.Content;
import com.hisun.kugga.duke.enums.RichTextTypeEnum;
import com.hisun.kugga.duke.forum.dal.dataobject.*;
import com.hisun.kugga.duke.forum.dal.mysql.*;
import com.hisun.kugga.duke.forum.enums.MsgTypeEnums;
import com.hisun.kugga.duke.forum.enums.PlateEnums;
import com.hisun.kugga.duke.forum.service.MessageService;
import com.hisun.kugga.duke.forum.service.PostsService;
import com.hisun.kugga.duke.forum.utils.AnonymousUtil;
import com.hisun.kugga.duke.forum.vo.MessageDetailsReqVO;
import com.hisun.kugga.duke.forum.vo.MessageDetailsVO;
import com.hisun.kugga.duke.forum.vo.MessageVO;
import com.hisun.kugga.duke.forum.vo.PageMessageReqVO;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.duke.league.api.dto.LeagueDTO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

/**
 * @author zuocheng
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    /**
     * 内容删除说明
     */
    public static final String CONTENT_DELETE_EXPLAIN_EG = "Content deleted";

    @Resource
    private ForumMessageMapper forumMessageMapper;

    @Resource
    private PostsMapper postsMapper;

    @Resource
    private PostsFloorContentMapper postsFloorContentMapper;

    @Resource
    private PostsCommentMapper postsCommentMapper;

    @Resource
    private ForumPlateMapper forumPlateMapper;

    @Resource
    private LeagueApi leagueApi;

    @Resource
    private PostsFloorMapper floorMapper;

    @Resource
    private PostsService postsService;

    @Override
    public PageResult<MessageVO> pageList(PageMessageReqVO reqVo) {
        //设置分页参数
        Page<MessageVO> page = new Page<>(reqVo.getPageNo(), reqVo.getPageSize());
        //分页查询用户的论坛消息列表
        IPage<MessageVO> iPage = forumMessageMapper.pageListByUserId(page, getLoginUserId());
        //设置回复内容 与 被回复内容
        iPage.getRecords().stream().forEach(item -> {
            //获取贴子内容
            PostsDO posts = postsMapper.selectById(item.getReplyPostsId());
            //设置内容
            switch (MsgTypeEnums.valueOfType(item.getReceiveMsgType())) {
                case MSG_TYPE_0:
                    setReplyPostsContent(item, posts);
                    break;
                case MSG_TYPE_1:
                    setReplyFloorContent(item);
                    break;
                case MSG_TYPE_2:
                    setReplyCommentContent(item);
                    break;
                default:
                    throw exception(BusinessErrorCodeConstants.MSG_TYPE_ERROR);
            }

            if (ObjectUtil.isNull(posts)) {
                return;
            }
            //贴子还存在的情况下 去取板块名称
            item.setPlateId(posts.getPlate());
            item.setGroupId(posts.getGroupId());
            if (PlateEnums.PLATE_0.getType().equals(posts.getPlate())) {
                //板块类型为公会时,板块名称为公会
                LeagueDTO league = leagueApi.queryLeagueById(posts.getGroupId());
                if (ObjectUtil.isNotNull(league)) {
                    item.setPlateName(league.getLeagueName());
                    item.setGroupId(league.getId());
                    item.setPlateAvatar(league.getLeagueAvatar());
                }
            } else {
                //查询匿名贴的板块名称，预设将来支持多语言
                ForumPlateDO plate = forumPlateMapper.selectOneByValue(posts.getPlate(), "en-US");
                item.setPlateName(plate.getPlateName());
                item.setPlateAvatar(plate.getPlateAvatar());

                //匿名时不返回用户ID、头像、姓
                String str = AnonymousUtil.md5Hex("" + item.getReplyPostsId() + item.getReplyUserId());
                item.setReplyUserId(Long.valueOf(str));
                item.setReplyAvatar(null);
                item.setReplyLastName(null);
                item.setReplyFirstName(str);
                item.setReplyUserName(str);
            }
        });

        return new PageResult<>(iPage.getRecords(), iPage.getTotal());
    }

    @Override
    public Long unreadNum() {
        return forumMessageMapper.selectUnreadNum(getLoginUserId());
    }

    @Override
    public void read() {
        forumMessageMapper.updateRead(SecurityFrameworkUtils.getLoginUserId());
    }

    @Override
    public void singleRead(Long id) {
        forumMessageMapper.updateSingleRead(id, SecurityFrameworkUtils.getLoginUserId());
    }

    @Override
    public MessageDetailsVO details(MessageDetailsReqVO reqVO) {
        ForumMessageDO messageDO = forumMessageMapper.selectById(reqVO.getMessageId());
        if (ObjectUtil.isNull(messageDO)) {
            //消息已不存在
            throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
        }

        if (!messageDO.getUserId().equals(getLoginUserId())) {
            //避免用户刷信息，消息非当前登录用户的信息时报错
            throw exception(BusinessErrorCodeConstants.NO_ACCESS);
        }

        return postsService.floor(messageDO.getPostsId(), messageDO.getFloorId());
    }

    /**
     * 设置贴子类型的内容
     *
     * @param messageVO
     */
    private void setReplyPostsContent(MessageVO messageVO, PostsDO posts) {
        //添加被回复内容,贴不存在时设置被回复的内容为“贴子删除”,如果存在则设置被回复的内容为贴子标题
        if (ObjectUtil.isNull(posts)) {
            //贴子删除的情况下，直接默认成内容已被删除
            messageVO.setReceiveContent(CONTENT_DELETE_EXPLAIN_EG);
        } else {
            messageVO.setReceiveContent(posts.getPostsTitle());
        }

        //添加回复的内容,被回复的消息为贴子时，回复内容必为楼层，直接查询楼层内容表(需要将富文本转成String)
        List<PostsFloorContentDO> contents = postsFloorContentMapper.selectByFloorId(messageVO.getReplyId());
        if (CollUtil.isEmpty(contents)) {
            //内层内容被删除时，直接默认成内容已被删除
            messageVO.setReplyContent(CONTENT_DELETE_EXPLAIN_EG);
        } else {
            //查询的内容，富文本转下String
            StringBuilder sb = new StringBuilder();
            contents.stream().forEach(content -> {
                Content ct = JSONUtil.toBean(content.getContent(), Content.class);
                if (ct.getType().equals(RichTextTypeEnum.IMAGE.getType())) {
                    sb.append("[image]");
                } else {
                    sb.append(content.getOriginalText());
                }
            });
            //去除最后一行的换行
            messageVO.setReplyContent(sb.toString());
            PostsFloorDO floor = floorMapper.selectById(messageVO.getReplyId());
            if (ObjectUtil.isNotNull(floor)) {
                messageVO.setReplyMsgId(floor.getMsgId());
            }
        }
    }


    /**
     * 设置回复楼层的内容
     *
     * @param messageVO
     */
    private void setReplyFloorContent(MessageVO messageVO) {
        //设置被回复的内容，楼层被回复的内容直接取楼层内容
        List<PostsFloorContentDO> contents = postsFloorContentMapper.selectByFloorId(messageVO.getReceiveId());
        if (CollUtil.isEmpty(contents)) {
            //楼层内容不存在时,直接设置成“内容已删除”
            messageVO.setReceiveContent(CONTENT_DELETE_EXPLAIN_EG);
        } else {
            //富文本转下String
            StringBuilder sb = new StringBuilder();
            contents.stream().forEach(content -> {
                Content ct = JSONUtil.toBean(content.getContent(), Content.class);
                if (ct.getType().equals(RichTextTypeEnum.IMAGE.getType())) {
                    sb.append("[image]");
                } else {
                    sb.append(content.getOriginalText());
                }
            });
            //去除最后一行的换行
            messageVO.setReceiveContent(sb.toString());
        }

        //设置回复内容,楼层里的所有回复均在讨论表，直接根据ID去去讨论内容
        PostsCommentDO comment = postsCommentMapper.selectById(messageVO.getReplyId());
        if (ObjectUtil.isNull(comment)) {
            //讨论内容不存在时,直接设置成“内容已删除”
            messageVO.setReplyContent(CONTENT_DELETE_EXPLAIN_EG);
        } else {
            messageVO.setReplyContent(comment.getContent());
            messageVO.setReplyMsgId(comment.getMsgId());
        }
    }

    /**
     * 设置回复讨论的内容
     *
     * @param messageVO
     */
    private void setReplyCommentContent(MessageVO messageVO) {
        //设置被回复的内容
        PostsCommentDO receive = postsCommentMapper.selectById(messageVO.getReceiveId());
        if (ObjectUtil.isNull(receive)) {
            //讨论内容不存在时,直接设置成“内容已删除”
            messageVO.setReceiveContent(CONTENT_DELETE_EXPLAIN_EG);
        } else {
            messageVO.setReceiveContent(receive.getContent());
        }

        //设置回复内容
        PostsCommentDO reply = postsCommentMapper.selectById(messageVO.getReplyId());
        if (ObjectUtil.isNull(reply)) {
            //讨论内容不存在时,直接设置成“内容已删除”
            messageVO.setReplyContent(CONTENT_DELETE_EXPLAIN_EG);
        } else {
            messageVO.setReplyContent(reply.getContent());
            messageVO.setReplyMsgId(reply.getMsgId());
        }
    }
}
