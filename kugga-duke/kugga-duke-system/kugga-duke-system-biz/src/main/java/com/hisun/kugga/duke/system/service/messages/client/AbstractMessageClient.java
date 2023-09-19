package com.hisun.kugga.duke.system.service.messages.client;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.enums.message.MessageReadStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.system.service.messages.MessagesService;
import com.hisun.kugga.duke.system.service.messages.bo.MessageBo;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.ILLEGAL_PARAM;
import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.MESSAGES_RECEIVE_NOT_EMPTY;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/7/30 14:43
 */
public class AbstractMessageClient implements MessageClient {

    private static final String MESSAGE_TEMPLATE = "message:template";

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private MessagesService messagesService;


    @Override
    public void sendMessage(SendMessageReqDTO reqDto) {
        //前置参数校验
        paramValidate(reqDto);

        // 消息模板初始化了直接使用,如果过期了重新初始化
        String templateStr = redisTemplate.opsForValue().get(MESSAGE_TEMPLATE);
        if (ObjectUtil.isEmpty(templateStr)) {
            messagesService.initMessageTemplate();
            templateStr = redisTemplate.opsForValue().get(MESSAGE_TEMPLATE);
        }
        Map<String, String> templateMap = JSONUtil.toBean(templateStr, Map.class);
        // String content = templateMap.get(reqDto.getMessageScene().name() + "_" + reqDto.getMessageType().name());
        String content = templateMap.get(reqDto.getMessageTemplate().name());

        String messageParam = JSONUtil.toJsonPrettyStr(reqDto.getMessageParam());
        List<MessageBo> messageBos = new ArrayList<>();
        for (Long receiver : reqDto.getReceivers()) {
            MessageBo messageBo = new MessageBo();
            messageBo.setMessageKey(reqDto.getMessageTemplate().name());
            messageBo.setScene(reqDto.getMessageScene().getScene());
            messageBo.setType(reqDto.getMessageType().getCode());
            messageBo.setBusinessLink(reqDto.getBusinessLink());
            messageBo.setBusinessId(reqDto.getBusinessId());

            messageBo.setInitiatorId(reqDto.getMessageParam().getInitiatorId());
            messageBo.setInitiatorLeagueId(reqDto.getMessageParam().getInitiatorLeagueId());
            messageBo.setReceiverId(receiver);
            messageBo.setReceiverLeagueId(reqDto.getMessageParam().getReceiverLeagueId());
            messageBo.setContent(content);
            messageBo.setMessageParam(messageParam);

            messageBo.setReadFlag(MessageReadStatusEnum.UNREAD.getCode());
            messageBo.setDealFlag(reqDto.getDealStatus().getCode());

            messageBos.add(messageBo);
        }
        doSendMessage(messageBos);
    }

    /**
     * 消息处理
     * 模板方法 {@link AbstractQueuedSynchronizer#tryAcquire(int)}
     */
    protected void doSendMessage(List<MessageBo> messageBos) {
        throw exception(BusinessErrorCodeConstants.SYSTEM_ERROR);
    }

    /**
     * 参数校验
     *
     * @param reqDto
     */
    private void paramValidate(SendMessageReqDTO reqDto) {
        if (ObjectUtil.isNull(reqDto.getMessageScene()) ||
                ObjectUtil.isNull(reqDto.getMessageType()) ||
                ObjectUtil.isNull(reqDto.getMessageTemplate()) ||
                ObjectUtil.isNull(reqDto.getLanguage()) ||
//                ObjectUtil.isNull(reqDto.getMessageTemplate()) ||
                //发起方用户id不能为空
                ObjectUtil.isNull(reqDto.getMessageParam().getInitiatorId())
        ) {
            throw exception(ILLEGAL_PARAM);
        }

        /*
        公会认证时 发送方公会id不能为null
        张三邀请为[后端开发工程师]做公会认证，此时发起方公会为后端，正常
        李四为[后端开发工程师]做公会认证  此时是认证回调，对于认证用户来说不能确定所属公会是哪个，这里特殊设置公会发起方还是原始后端公会，但是发起方用户是李四了
         */
        if ((ObjectUtil.equal(MessageSceneEnum.LEAGUE_AUTHENTICATION, reqDto.getMessageScene()) && ObjectUtil.isNull(reqDto.getMessageParam().getInitiatorLeagueId())) ||
                (ObjectUtil.equal(MessageSceneEnum.JOIN_LEAGUE_FIRST, reqDto.getMessageScene()) && ObjectUtil.isNull(reqDto.getMessageParam().getInitiatorLeagueId()))) {
            throw exception(ILLEGAL_PARAM);
        }
        //用户接受列表不能为null
        if (ObjectUtil.isEmpty(reqDto.getReceivers())) {
            throw exception(MESSAGES_RECEIVE_NOT_EMPTY);
        }
    }


}
