package com.hisun.kugga.duke.system.service.messages;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageReadStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
import com.hisun.kugga.duke.league.api.ForumApi;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.duke.system.api.message.dto.MessagesUpdateReqDTO;
import com.hisun.kugga.duke.system.api.message.dto.RedDotReqDTO;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesPageReqVO;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesRespVO;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesUpdateReqVO;
import com.hisun.kugga.duke.system.convert.messages.MessagesConvert;
import com.hisun.kugga.duke.system.dal.dataobject.MessageTemplateDO;
import com.hisun.kugga.duke.system.dal.dataobject.MessagesDO;
import com.hisun.kugga.duke.system.dal.mysql.MessagesMapper;
import com.hisun.kugga.duke.system.utils.MessageUtil;
import com.hisun.kugga.duke.utils.UserUtil;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.MESSAGES_ALREADY_DEAL;
import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.MESSAGES_NOT_EXISTS;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.web.core.util.WebFrameworkUtils.getLoginUserId;


/**
 * 消息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class MessagesServiceImpl implements MessagesService {

    private static final String MESSAGE_TEMPLATE = "message:template";

    @Resource
    private MessagesMapper messagesMapper;
    @Resource
    private MessageTemplateService templateService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private ForumApi forumApi;
    @Resource
    private LeagueApi leagueApi;
    @Resource
    private RedDotService redDotService;

    @Value("${duck.kugga.language:en-US}")
    private String language;


    @Override
    public List<MessageTemplateDO> initMessageTemplate() {
        List<MessageTemplateDO> messageTemplateDos = templateService.selectListByLanguage(language);

        HashMap<String, String> map = new HashMap<>();
        if (ObjectUtil.isNotEmpty(messageTemplateDos)) {
            for (MessageTemplateDO templateDo : messageTemplateDos) {
                // RECOMMENDATION_INVITE->张三邀请写推荐报告
                // map.put(templateDo.getMessageScene()+"_"+templateDo.getMessageType(),templateDo.getTemplate());

                //使用唯一key
                map.put(templateDo.getMessageKey(), templateDo.getTemplate());
            }
        }
        redisTemplate.opsForValue().set(MESSAGE_TEMPLATE, JSONUtil.toJsonPrettyStr(map), 24, TimeUnit.HOURS);
        return messageTemplateDos;
    }

    @Override
    public PageResult<MessagesRespVO> getPageMessages(MessagesPageReqVO reqVO) {
        Page<MessagesRespVO> pageParam = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        Page<MessagesRespVO> resPage = new Page<>();
        resPage = messagesMapper.selectMessagePage(pageParam, reqVO);

        PageResult<MessagesRespVO> result = new PageResult<>();
        if (ObjectUtil.isEmpty(resPage.getRecords())) {
            return result.setList(resPage.getRecords()).setTotal(resPage.getTotal());
        }

        for (MessagesRespVO message : resPage.getRecords()) {
            //设置参数
            ContentParamVo contentParamVo = JSONUtil.toBean(message.getMessageParamStr(), ContentParamVo.class);
            message.setMessageParam(contentParamVo);
            message.setMessageParamStr(null);
            // 构建消息文案信息
            // buildMessageInfo(message);
            MessageUtil.buildMessageInfo(message);
        }
        return result.setList(resPage.getRecords()).setTotal(resPage.getTotal());
    }


    @Override
    public void updateMessages(MessagesUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateMessagesExists(updateReqVO.getId());
        // 更新
        MessagesDO updateObj = MessagesConvert.INSTANCE.convert(updateReqVO);
        messagesMapper.updateById(updateObj);
    }

    private void validateMessagesExists(Long id) {
        if (messagesMapper.selectById(id) == null) {
            throw exception(MESSAGES_NOT_EXISTS);
        }
    }

    @Override
    public MessagesDO getMessages(Long id) {
        return messagesMapper.selectById(id);
    }


    /**
     * {@link SendMessageReqDTO}
     *
     * @param pageReqVO 分页查询
     * @return
     */
    @Override
    public PageResult<MessagesDO> getMessagesPage(MessagesPageReqVO pageReqVO) {
        return messagesMapper.selectPage(pageReqVO);
    }


    /**
     * 把消息信息补全
     * {} 邀请为[{}]公会做认证   张三 后端开发工程师
     * 张三邀请为[后端开发工程师]公会做认证
     *
     * @param messagesRespVO
     */
    private void buildMessageInfo(MessagesRespVO messagesRespVO) {
        //从缓存map里获取消息模板
        String messageStr = "";
        List<String> params = new ArrayList<>();
        //构建原始消息所需要的参数
        packageMessageInfo(messagesRespVO, params);
        messageStr = StrUtil.format(messagesRespVO.getContent(), ArrayUtil.toArray(params, String.class));
        messagesRespVO.setContent(messageStr);
    }

    /**
     * 封装消息格式化参数
     *
     * @param messagesRespVO
     * @param params
     */
    private void packageMessageInfo(MessagesRespVO messagesRespVO, List<String> params) {
        MessageSceneEnum sceneEnum = MessageSceneEnum.getByScene(messagesRespVO.getScene());
        MessageTypeEnum typeEnum = MessageTypeEnum.getByCode(messagesRespVO.getType());

        if (ObjectUtil.equal(MessageSceneEnum.LEAGUE_AUTHENTICATION, sceneEnum)) {
            /* 公会认证
            张三邀请为[后端开发工程师]做公会认证    张三发起给李四接受
            李四为[后端开发工程师]做公会认证       李四发给张三收
             */
            if (ObjectUtil.equal(MessageTypeEnum.INVITE, typeEnum)) {
                params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
                params.add(messagesRespVO.getInitiatorLeagueName());
            } else if (ObjectUtil.equal(MessageTypeEnum.CALLBACK, typeEnum)) {
                //李四为[后端开发工程师]做公会认证  此时是认证回调，对于认证用户李四来说不能确定所属公会是哪个，这里特殊设置公会发起方还是原始后端公会
                // 但是发起方用户是李四了
                params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
                params.add(messagesRespVO.getInitiatorLeagueName());
            } else if (ObjectUtil.equal(MessageTypeEnum.CALLBACK_RECEIVER, typeEnum)) {
                params.add(messagesRespVO.getInitiatorLeagueName());
                params.add(UserUtil.getUserName(messagesRespVO.getReceiverFirstName(), messagesRespVO.getReceiverLastName()));
            }
        } else if (ObjectUtil.equal(MessageSceneEnum.RECOMMENDATION, sceneEnum)) {
            /* 推荐信
            张三邀请写推荐报告，您将获得$50的收益  去处理>    to 李四
            李四为我写了推荐报告  to 张三
            您撰写推荐报告，到账$50   to李四    (我为张三写了推荐报告，获得50收益)
            我为张三写了推荐报告
             */
            if (ObjectUtil.equal(MessageTypeEnum.INVITE, typeEnum)) {
                params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
                BigDecimal amount = Optional.ofNullable(messagesRespVO.getMessageParam().getTaskWriteReportCreate().getAmount()).orElse(BigDecimal.ZERO);
                params.add(amount.toString());
            } else if (ObjectUtil.equal(MessageTypeEnum.CALLBACK, typeEnum)) {
                //此时回调的发起方是 李四了
                params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
            } else if (ObjectUtil.equal(MessageTypeEnum.CALLBACK_RECEIVER, typeEnum)) {
                //回调给接收方，此时回调的发起方是李四，因此取接收方name
                //params.add(UserUtil.getUserName(messagesRespVO.getReceiverFirstName(), messagesRespVO.getReceiverLastName()));
                BigDecimal amount = Optional.ofNullable(messagesRespVO.getMessageParam().getTaskWriteReportCreate().getAmount()).orElse(BigDecimal.ZERO);
                params.add(amount.toString());
            }
        } else if (ObjectUtil.equal(MessageSceneEnum.JOIN_LEAGUE, sceneEnum)) {
            /* 加入公会
            张三邀请您加入[后端开发工程师]
            -- 在李四接受您的邀请后，公会正式成立。去建立公会的规则。
            张三接受了我创建公会的邀请，公会已正式成立，快去完成公会认证吧
            【后端开发工程师】公会已完成公会认证，快去设置公会规则吧
             */
            if (ObjectUtil.equal(MessageTypeEnum.INVITE, typeEnum)) {
                params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
                params.add(messagesRespVO.getInitiatorLeagueName());
            } else if (ObjectUtil.equal(MessageTypeEnum.CALLBACK, typeEnum)) {
                //todo 认证&规则
                params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
            }
        } else if (ObjectUtil.equal(MessageSceneEnum.LEAGUE_CREATE_AUTH, sceneEnum)) {
            /* 加入公会
            张三接受了我创建公会的邀请，公会已正式成立，快去完成公会认证吧
             */
            if (ObjectUtil.equal(MessageTypeEnum.INVITE, typeEnum)) {
                params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
            }
        } else if (ObjectUtil.equal(MessageSceneEnum.LEAGUE_CREATE_RULE, sceneEnum)) {
            /* 加入公会
            【后端开发工程师】公会已完成公会认证，快去设置公会规则吧
             */
            if (ObjectUtil.equal(MessageTypeEnum.INVITE, typeEnum)) {
                params.add(messagesRespVO.getReceiverLeagueName());
            }
        } else if (ObjectUtil.equal(MessageSceneEnum.JOIN_LEAGUE_FIRST, sceneEnum)) {
            //第一次加入公会  您是第一个受邀加入公会后端开发工程师的会员，并获得10美元的奖金。
            params.add(messagesRespVO.getInitiatorLeagueName());
            BigDecimal amount = Optional.ofNullable(messagesRespVO.getMessageParam().getJoinLeagueMessageVO().getAmount()).orElse(BigDecimal.ZERO);
            params.add(amount.toString());
        } else if (ObjectUtil.equal(MessageSceneEnum.CHAT, sceneEnum)) {
            /*聊天
            张三邀请与我聊天，您将获得$50的收益 to 李四
            张三邀请与我聊天 免费
            李四同意与我聊天 to 张三
            您同意与他人聊天，到账$50  to李四    (同意与张三聊天， 获得50收益)
             */
            if (ObjectUtil.equal(MessageTypeEnum.INVITE, typeEnum)) {
                params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
                BigDecimal amount = Optional.ofNullable(messagesRespVO.getMessageParam().getTaskChat().getAmount()).orElse(BigDecimal.ZERO);
                params.add(amount.toString());
            } else if (ObjectUtil.equal(MessageTypeEnum.CALLBACK, typeEnum)) {
                params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
            } else if (ObjectUtil.equal(MessageTypeEnum.CALLBACK_RECEIVER, typeEnum)) {
                BigDecimal amount = Optional.ofNullable(messagesRespVO.getMessageParam().getTaskChat().getAmount()).orElse(BigDecimal.ZERO);
                params.add(amount.toString());
            }
        }/*else if(ObjectUtil.equal(MessageSceneEnum.INVITE_LINK_EXPIRE,sceneEnum)){
            //邀请连接过期  纯文案不用格式化
        }*/ else if (ObjectUtil.equal(MessageSceneEnum.SYSTEM_NOTICE, sceneEnum)) {
            //系统通知
            params.add(messagesRespVO.getMessageParam().getSystemNoticeMessageVO().getStartTime());
            params.add(messagesRespVO.getMessageParam().getSystemNoticeMessageVO().getEndTime());
        }
    }

    /**
     * @param messageStr
     * @param message
     * @return
     */
    private String getPackageMessage(String messageStr, MessagesRespVO message) {

        return "";
    }


    @Override
    public void updateReadMessages(MessagesUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateMessagesExists(updateReqVO.getId());
        // 更新
        MessagesDO updateObj = new MessagesDO();
        updateObj.setId(updateReqVO.getId());
        updateObj.setReadFlag(MessageReadStatusEnum.READ.getCode());
        messagesMapper.updateById(updateObj);
        // 红点处理
        if (!getAllUnRead(SecurityFrameworkUtils.getLoginUserId())) {
            redDotService.publish(new RedDotReqDTO()
                    .setUserId(SecurityFrameworkUtils.getLoginUserId())
                    .setMessageRedDot(false));
        }
    }

    @Override
    public void dealMessagesInner(MessagesUpdateReqDTO reqDTO) {

        List<MessagesDO> messagesDOS = messagesMapper.selectByBusinessId(reqDTO.getTaskId());
        //消息不能为空且不能是已处理
        if (ObjectUtil.isEmpty(messagesDOS)) {
            return;
        }
        /*if (ObjectUtil.equal(messagesDOS.get(0).getDealFlag(), MessageDealStatusEnum.ALREADY_DEAL.getCode()) ||
                ObjectUtil.equal(messagesDOS.get(0).getDealFlag(), MessageDealStatusEnum.EXPIRE.getCode())) {
            throw exception(MESSAGES_ALREADY_DEAL);
        }*/
        //通过业务(任务)id把所有消息已处理
        MessagesDO updateObj = new MessagesDO();
        updateObj.setDealFlag(reqDTO.getStatus().getCode());

        LambdaQueryWrapper<MessagesDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessagesDO::getBusinessId, reqDTO.getTaskId());
        messagesMapper.update(updateObj, wrapper);
    }

    @Override
    public void updateDealMessages(MessagesUpdateReqVO updateReqVO) {
        MessagesDO messagesDO = messagesMapper.selectById(updateReqVO.getId());
        if (ObjectUtil.equal(messagesDO.getDealFlag(), MessageDealStatusEnum.ALREADY_DEAL.getCode())) {
            throw exception(MESSAGES_ALREADY_DEAL);
        }

        // 更新
        MessagesDO updateObj = new MessagesDO();
        updateObj.setId(updateReqVO.getId());
        updateObj.setDealFlag(MessageDealStatusEnum.ALREADY_DEAL.getCode());
        messagesMapper.updateById(updateObj);
    }

    @Override
    public void cleanRedSpot(Long userId) {
        MessagesDO updateObj = new MessagesDO();
        updateObj.setReadFlag(MessageReadStatusEnum.READ.getCode());
        LambdaQueryWrapper<MessagesDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessagesDO::getReceiverId, userId);
        messagesMapper.update(updateObj, wrapper);
//        messagesMapper.updateReadFlagByUserId(userId);
    }

    @Override
    public Long getUserUnReadCount(Long userId) {
        return messagesMapper.getUserUnReadCount(userId);
    }

    @Override
    public boolean getIsUserUnRead(Long userId) {
        return this.getUserUnReadCount(userId) > 0;
    }

    @Override
    public boolean getAllUnRead(Long userId) {
        // 短路机制
        boolean isRead = false;
        isRead = getIsUserUnRead(userId);
        if (isRead) {
            return true;
        }
        Long count = 0L;
        count = forumApi.unreadNum();
        if (count > 0) {
            return true;
        }
        count = leagueApi.getApprovalCount();
        return count > 0;
    }
}
