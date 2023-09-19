package com.hisun.kugga.duke.batch.job.league;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguejoin.LeagueJoinApprovalDO;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguejoin.LeagueJoinDO;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessagesDO;
import com.hisun.kugga.duke.batch.dal.mysql.leaguejoin.LeagueJoinApprovalMapper;
import com.hisun.kugga.duke.batch.dal.mysql.leaguejoin.LeagueJoinMapper;
import com.hisun.kugga.duke.batch.dal.mysql.message.MessagesMapper;
import com.hisun.kugga.duke.batch.dto.RefundReqDTO;
import com.hisun.kugga.duke.batch.service.MessageService;
import com.hisun.kugga.duke.batch.service.PayOrderService;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.LanguageEnum;
import com.hisun.kugga.duke.enums.LeagueJoinApprovalTypeEnum;
import com.hisun.kugga.duke.enums.PayStatusEnum;
import com.hisun.kugga.duke.enums.message.*;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 加入公会过期退款
 * @author： Lin
 * @Date 2022/9/15 15:09
 */
@Slf4j
@Component
public class LeagueJoinExpireJob implements JobHandler {

    @Resource
    private LeagueJoinMapper joinMapper;
    @Resource
    private LeagueJoinApprovalMapper approvalMapper;
    @Resource
    private PayOrderService payOrderService;
    @Resource
    private MessageService messageService;
    @Resource
    private MessagesMapper messagesMapper;

    @Override
    public String execute(String param) throws Exception {
        log.info(" LeagueJoinExpireJob 定时处理加入公会过期退款数据 start:{}", param);
        //加入公会的申请记录数据是  业务状态是初始化状态0的，支付状态是0免费或者2已支付的(不等于 未支付1) 过期时间<当前时间的

        List<LeagueJoinDO> expireRecords = joinMapper.selectExpireRecords();
        if (ObjectUtil.isEmpty(expireRecords)) {
            return GlobalErrorCodeConstants.SUCCESS.getMsg();
        }
        expireRecords.forEach(item -> {
            try {
                //修改加入记录 和审批记录为过期
                LeagueJoinDO joinDO = new LeagueJoinDO()
                        .setId(item.getId())
                        .setBusinessStatus(LeagueJoinApprovalTypeEnum.EXPIRE.getValue())
                        .setAmountStatus(item.getPayFlag() ? PayStatusEnum.REFUND.getValue() : null);
                joinMapper.updateById(joinDO);

                LeagueJoinApprovalDO approvalDO = new LeagueJoinApprovalDO()
                        .setBusinessId(joinDO.getId())
                        .setStatus(LeagueJoinApprovalTypeEnum.EXPIRE.getValue());
                LambdaQueryWrapper<LeagueJoinApprovalDO> wrapper = new LambdaQueryWrapper<LeagueJoinApprovalDO>().eq(LeagueJoinApprovalDO::getBusinessId, joinDO.getId());
                approvalMapper.update(approvalDO, wrapper);

                sendMessage(item);

                //付费才需要退款  免费只需要修改订单状态
                if (item.getPayFlag()) {
                    //每次请求前sleep 1秒钟(减小下压力),每次查询至多1000条数据,理论上16分钟可以发完
                    Thread.sleep(1000);
                    payOrderService.refund(new RefundReqDTO()
                            .setAppOrderNo(item.getAppOrderNo()).setRefundAmount(item.getAmount()));
                }
            } catch (InterruptedException e) {
                log.error("LeagueJoinExpireJob 数据处理失败(忽略此条错误)[{}]", item, e);
            }
        });
        log.info(" LeagueJoinExpireJob 定时处理加入公会过期退款数据 end:{}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * 给用户发 过期消息
     *
     * @param joinDO
     */
    private void sendMessage(LeagueJoinDO joinDO) {
//        String content = messageService.getContent(MessageTemplateEnum.JOIN_LEAGUE_ACTIVE_EXPIRE);
//        MessagesDO insert = new MessagesDO()
//                .setMessageKey(MessageTemplateEnum.JOIN_LEAGUE_ACTIVE_EXPIRE.name())
//                .setScene(MessageSceneEnum.JOIN_LEAGUE_ACTIVE.getScene())
//                .setType(MessageTypeEnum.CALLBACK2.getCode())
//                .setInitiatorLeagueId(joinDO.getLeagueId())
//                .setReceiverId(joinDO.getUserId())
//                .setContent(content)
//                .setReadFlag(MessageReadStatusEnum.UNREAD.getCode())
//                .setDealFlag(MessageDealStatusEnum.DEAL.getCode());
//        messagesMapper.insert(insert);

        ContentParamVo contentParamVo = new ContentParamVo()
                .setInitiatorLeagueId(joinDO.getLeagueId())
                .setInitiatorId(0L);
        // 因为本次加入申请过期，[{后端开发工程师}]公会已经拒绝您的申请
        SendMessageReqDTO message = new SendMessageReqDTO()
                .setMessageTemplate(MessageTemplateEnum.JOIN_LEAGUE_ACTIVE_EXPIRE)
                .setMessageScene(MessageSceneEnum.JOIN_LEAGUE_ACTIVE)
                .setMessageType(MessageTypeEnum.CALLBACK2)
                .setBusinessId(joinDO.getId())
                .setReceivers(Collections.singletonList(joinDO.getUserId()))
                .setMessageParam(contentParamVo)
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.NO_DEAL);
        messageService.sendMessage(message);
    }

}
