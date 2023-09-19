/*
package com.hisun.kugga.duke.system.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesRespVO;
import com.hisun.kugga.duke.utils.UserUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hisun.kugga.duke.common.IdentifierConstants.dd_MM_yyyy;

*/
/**
 * @Description: 构建出完整消息
 * @author： Lin
 * @Date 2022/9/20 15:18
 * <p>
 * 把消息信息补全
 * {} 邀请为[{}]公会做认证   张三 后端开发工程师
 * 张三邀请为[后端开发工程师]公会做认证
 * @param messagesRespVO
 * <p>
 * 封装消息格式化参数
 * {@link MessageTemplateEnum}
 * @param messagesRespVO
 * @param params
 *//*

public class MessageUtil2 {
    */
/**
 * 把消息信息补全
 * {} 邀请为[{}]公会做认证   张三 后端开发工程师
 * 张三邀请为[后端开发工程师]公会做认证
 *
 * @param messagesRespVO
 *//*

    public static void buildMessageInfo(MessagesRespVO messagesRespVO) {
        //从缓存map里获取消息模板
        String messageStr = "";
        List<String> params = new ArrayList<>();
        //构建原始消息所需要的参数
        packageMessageInfo(messagesRespVO, params);
        messageStr = StrUtil.format(messagesRespVO.getContent(), ArrayUtil.toArray(params, String.class));
        messagesRespVO.setContent(messageStr);
    }


    */
/**
 * 封装消息格式化参数
 * {@link MessageTemplateEnum}
 *
 * @param messagesRespVO
 * @param params
 *//*

    private static void packageMessageInfo(MessagesRespVO messagesRespVO, List<String> params) {
        // 得到唯一消息key
        MessageTemplateEnum template = MessageTemplateEnum.valueOf(messagesRespVO.getMessageKey());

//        switch (template){
//            case LEAGUE_AUTH_INVITE:
//                System.out.println("");
//                break;
//            default:
//                throw exception(BusinessErrorCodeConstants.SUBSCRIBE_TYPE_ERROR);
//        }

        if (ObjectUtil.equal(MessageTemplateEnum.LEAGUE_AUTH_INVITE, template)) {
            */
/* 公会认证
            张三邀请为[后端开发工程师]做公会认证    张三发起给李四接受
            李四为[后端开发工程师]做公会认证       李四发给张三收
             *//*

            params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
            params.add(messagesRespVO.getInitiatorLeagueName());
        } else if (ObjectUtil.equal(MessageTemplateEnum.LEAGUE_AUTH_CALLBACK, template)) {
            //李四为[后端开发工程师]做公会认证  此时是认证回调，对于认证用户李四来说不能确定所属公会是哪个，这里特殊设置公会发起方还是原始后端公会
            // 但是发起方用户是李四了
            params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
            params.add(messagesRespVO.getInitiatorLeagueName());
        } else if (ObjectUtil.equal(MessageTemplateEnum.RECOMMENDATION_INVITE, template)) {
            */
/* 推荐信
            张三邀请写推荐报告，您将获得$50的收益  去处理>    to 李四
            张三邀请写推荐报告 免费                        to 李四
            李四为我写了推荐报告  to 张三

            您撰写推荐报告，到账$50   to李四    (我为张三写了推荐报告，获得50收益)
            我为张三写了推荐报告
             *//*

            params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
            BigDecimal amount = Optional.ofNullable(messagesRespVO.getMessageParam().getTaskWriteReportCreate().getAmount()).orElse(BigDecimal.ZERO);
            params.add(amount.toString());
        } else if (ObjectUtil.equal(MessageTemplateEnum.RECOMMENDATION_INVITE_FREE, template)) {
            // 推荐信 张三邀请写推荐报告 免费
            params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
        } else if (ObjectUtil.equal(MessageTemplateEnum.RECOMMENDATION_CALLBACK, template)) {
            // 推荐信 李四为我写了推荐报告  to 张三
            params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
        } else if (ObjectUtil.equal(MessageTemplateEnum.REACH_ACCOUNT_RECOMMENDATION, template)) {
            // 您撰写推荐报告，到账$50   to李四
            BigDecimal amount = Optional.ofNullable(messagesRespVO.getMessageParam().getTaskWriteReportCreate().getAmount()).orElse(BigDecimal.ZERO);
            params.add(amount.toString());
        } else if (ObjectUtil.equal(MessageTemplateEnum.RECOMMENDATION_CALLBACK_RECEIVER2, template)) {
            // 我为张三写了推荐报告
            //回调给接收方，此时回调的发起方是李四，因此取接收张三name
            params.add(UserUtil.getUserName(messagesRespVO.getReceiverFirstName(), messagesRespVO.getReceiverLastName()));
        } else if (ObjectUtil.equal(MessageTemplateEnum.CHAT_INVITE, template)) {
             */
/*聊天
            张三邀请与我聊天，您将获得$50的收益 to 李四
            张三邀请与我聊天 免费
            李四同意与我聊天 to 张三
            您同意与他人聊天，到账$50  to李四    (同意与张三聊天， 获得50收益)
            您同意与他人聊天 免费
             *//*

            params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
            BigDecimal amount = Optional.ofNullable(messagesRespVO.getMessageParam().getTaskChat().getAmount()).orElse(BigDecimal.ZERO);
            params.add(amount.toString());
        } else if (ObjectUtil.equal(MessageTemplateEnum.CHAT_INVITE_FREE, template)) {
            //张三邀请与我聊天 免费
            params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
        } else if (ObjectUtil.equal(MessageTemplateEnum.CHAT_CALLBACK, template)) {
            //李四同意与我聊天
            params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
        } else if (ObjectUtil.equal(MessageTemplateEnum.REACH_ACCOUNT_CHAT, template)) {
            //您同意与他人聊天，到账$50
            BigDecimal amount = Optional.ofNullable(messagesRespVO.getMessageParam().getTaskChat().getAmount()).orElse(BigDecimal.ZERO);
            params.add(amount.toString());
        } else if (ObjectUtil.equal(MessageTemplateEnum.CHAT_CALLBACK_RECEIVER2, template)) {
            //您同意与他人聊天  纯文案注释
            // params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
        } else if (ObjectUtil.equal(MessageTemplateEnum.JOIN_LEAGUE_ACTIVE_AGREE, template)) {
            // 加入公会 后端开发工程师同意-拒绝你加入公会
            params.add(messagesRespVO.getInitiatorLeagueName());
        } else if (ObjectUtil.equal(MessageTemplateEnum.JOIN_LEAGUE_ACTIVE_REJECT, template)) {
            //后端开发工程师拒绝你加入公会
            params.add(messagesRespVO.getInitiatorLeagueName());
        }else if (ObjectUtil.equal(MessageTemplateEnum.JOIN_LEAGUE_ACTIVE_EXPIRE, template)) {
            //因为本次加入申请过期，[{后端开发工程师}]公会已经拒绝您的申请
            params.add(messagesRespVO.getInitiatorLeagueName());
        }else if (ObjectUtil.equal(MessageTemplateEnum.LEAGUE_CREATE_AUTH_INVITE, template)) {
            //{张三}接受了我创建公会的邀请，公会已正式成立，快去完成公会认证吧
            params.add(UserUtil.getUserName(messagesRespVO.getInitiatorFirstName(), messagesRespVO.getInitiatorLastName()));
        }else if (ObjectUtil.equal(MessageTemplateEnum.LEAGUE_CREATE_RULE_INVITE, template)) {
            // {后端开发工程师}公会已完成公会认证，快去设置公会规则吧
            params.add(messagesRespVO.getInitiatorLeagueName());
        }else if (ObjectUtil.equal(MessageTemplateEnum.LEAGUE_FIRST_JOIN_INVITE, template)) {
            // 第一次加入公会,您是第一个受邀加入公会[{后端开发工程师}]的会员，并获得{10}美元的奖金。
            params.add(messagesRespVO.getInitiatorLeagueName());
            BigDecimal amount = Optional.ofNullable(messagesRespVO.getMessageParam().getJoinLeagueMessageVO().getAmount()).orElse(BigDecimal.ZERO);
            params.add(amount.toString());
        }else if (ObjectUtil.equal(MessageTemplateEnum.LEAGUE_FIRST_JOIN_INVITE_FREE, template)) {
            // 您是第一个受邀加入公会[后端开发工程师]的会员
            params.add(messagesRespVO.getInitiatorLeagueName());
        }else if(ObjectUtil.equal(MessageTemplateEnum.LEAGUE_JOIN_INVITE, template)){
            // You're invited to join [后端开发] guild.
            if(StrUtil.isBlank(messagesRespVO.getInitiatorLeagueName())){
                //如果“initiatorLeagueName”字段为空,则说明发出邀请的公会属于创建中的公会,直接取邀请对象中的公会名
                params.add(messagesRespVO.getMessageParam().getInviteJoinLeagueVO().getLeagueName());
            }else {
                params.add(messagesRespVO.getInitiatorLeagueName());
            }
        }else if (ObjectUtil.equal(MessageTemplateEnum.LEAGUE_SUBSCRIBE_RENEWAL, template)) {
            // 您在{后端开发工程师公会}的订阅将于 2022年11月11日 以 10$/月的价格自动续期，清保持账户余额充足  10/20/2022
            params.add(messagesRespVO.getInitiatorLeagueName());
            LocalDateTime expireTime = messagesRespVO.getMessageParam().getLeagueSubscribeVO().getExpireTime();

            String format = DateUtil.format(expireTime, dd_MM_yyyy);
            params.add(format);

            BigDecimal amount = Optional.ofNullable(messagesRespVO.getMessageParam().getLeagueSubscribeVO().getAmount()).orElse(BigDecimal.ZERO);
            params.add(amount.toString());
        }
    }

}
*/
