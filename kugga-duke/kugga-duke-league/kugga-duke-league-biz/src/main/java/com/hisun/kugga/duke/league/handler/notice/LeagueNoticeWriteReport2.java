//package com.hisun.kugga.duke.league.handler.notice;
//
//import com.hisun.kugga.duke.enums.LanguageEnum;
//import com.hisun.kugga.duke.league.bo.task.TaskAcceptBO;
//import com.hisun.kugga.duke.league.bo.task.TaskFinishBO;
//import com.hisun.kugga.duke.league.bo.task.TaskInitBO;
//import com.hisun.kugga.duke.league.dal.dataobject.LeagueMemberDO;
//import com.hisun.kugga.duke.league.dal.dataobject.LeagueNoticeDO;
//import com.hisun.kugga.duke.league.dal.dataobject.TaskDO;
//import com.hisun.kugga.duke.league.dal.mysql.LeagueMapper;
//import com.hisun.kugga.duke.league.dal.mysql.LeagueMemberMapper;
//import com.hisun.kugga.duke.league.dal.mysql.LeagueNoticeMapper;
//import com.hisun.kugga.duke.league.dal.mysql.TaskMapper;
//import com.hisun.kugga.duke.league.enums.LeagueNoticeStatusEnum;
//import com.hisun.kugga.duke.league.enums.LeagueNoticeTypeEnum;
//import com.hisun.kugga.duke.league.factory.LeagueNoticeFactory;
//import com.hisun.kugga.duke.pay.api.order.OrderApi;
//import com.hisun.kugga.duke.system.api.message.SendMessageApi;
//import com.hisun.kugga.duke.entity.ContentParamVo;
//import com.hisun.kugga.duke.system.api.message.dot.MessagesUpdateReqDTO;
//import com.hisun.kugga.duke.system.api.message.dot.SendMessageReqDTO;
//import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
//import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
//import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
//import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
//import com.hisun.kugga.duke.user.api.oauth2.dto.UserInfoRespDTO;
//import com.hisun.kugga.framework.common.exception.ServiceException;
//import com.hisun.kugga.framework.common.pojo.CommonResult;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import static com.hisun.kugga.duke.league.constants.TaskConstants.*;
//import static com.hisun.kugga.duke.league.enums.LeagueNoticeStatusEnum.NOTICE_STATUS_2;
//import static com.hisun.kugga.framework.common.pojo.CommonResult.success;
//import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
//
///**
// * @Author: ZhouAnYan  推荐信备份 备份9.15代码 a270f6b19dc12c5c4954df5135ba39b3d14c9d13
// * @Email: yan_zay@163.com
// * @Date: 2022-07-25 10:37
// */
//@Slf4j
////@Component
//@AllArgsConstructor
//public class LeagueNoticeWriteReport2 extends AbstractLeagueNoticeHandler {
//
//    private static final LeagueNoticeTypeEnum noticeType = LeagueNoticeTypeEnum.NOTICE_TYPE_1;
//    private static final LeagueNoticeTypeEnum noticeFinishType = LeagueNoticeTypeEnum.NOTICE_TYPE_2;
//
//    private static final LeagueNoticeStatusEnum noticeStatus = LeagueNoticeStatusEnum.NOTICE_STATUS_1;
//    private static final LeagueNoticeStatusEnum noticeFinishStatus = LeagueNoticeStatusEnum.NOTICE_STATUS_3;
//
//    LeagueNoticeMapper leagueNoticeMapper;
//    LeagueMapper leagueMapper;
//    OrderApi orderApi;
//    SendMessageApi sendMessageApi;
//    LeagueMemberMapper leagueMemberMapper;
//    TaskMapper taskMapper;
//    DukeUserApi dukeUserApi;
//
//    /**
//     * 推荐报告
//     * @param bo
//     * @return
//     */
//    @Override
//    public void create(TaskInitBO bo) {
//        UserInfoRespDTO user = dukeUserApi.getUserById(getLoginUserId());
//        LocalDateTime now = LocalDateTime.now();
//        bo.getTaskInit1().getLeagueList().stream().forEach(obj -> {
//            LeagueNoticeDO noticeDO = new LeagueNoticeDO()
//                    .setTaskId(bo.getId())
//                    .setTaskType(bo.getType())
//                    .setLeagueId(obj.getId())
//                    .setType(noticeType)
//                    .setStatus(noticeStatus)
//
//                    .setUseUserId(user.getId())
//                    .setAmount(obj.getAmount());
//            leagueNoticeMapper.insert(noticeDO);
//
//            //发消息
//            List<Long> userIdList = leagueMemberMapper.selectList(LeagueMemberDO::getLeagueId, obj.getId())
//                    .stream().map(LeagueMemberDO::getUserId).collect(Collectors.toList());
//            SendMessageReqDTO message = new SendMessageReqDTO()
//                    .setMessageScene(MessageSceneEnum.RECOMMENDATION)
//                    .setMessageType(MessageTypeEnum.INVITE)
//                    .setBusinessId(noticeDO.getId())
//                    .setBusinessLink(null)
//                    .setReceivers(userIdList)
//                    .setMessageParam(new ContentParamVo()
//                            .setInitiatorId(user.getId())
//                            .setTaskWriteReportCreate(
//                                    new ContentParamVo.TaskWriteReportCreate()
//                                            .setId(bo.getId())
//                                            .setType(bo.getType().getValue())
//                                            .setLeagueNoticeId(noticeDO.getId())
//                                            .setByLeagueId(obj.getId())
//                                            .setUseUserId(user.getId())
//                            ))
//                    .setLanguage(LanguageEnum.en_US)
//                    .setDealStatus(MessageDealStatusEnum.DEAL);
//            sendMessageApi.sendMessage(message);
//        });
//    }
//
//    @Override
//    public CommonResult accept(TaskAcceptBO bo) {
//        LeagueNoticeDO notice = leagueNoticeMapper.selectById(bo.getLeagueNoticeId());
//        if(Objects.isNull(notice)) {
//            throw new ServiceException(NOTICE_IS_DELETE);
//        }
//        LocalDateTime now = LocalDateTime.now();
///*        if (now.isAfter(notice.getExpiresTime())) {
//            throw new ServiceException(NOT_ACCEPT_TIME_OUT_WRITE_REPORT);
//        }*/
//        if (Objects.isNull(bo.getId()) || Objects.isNull(bo.getType()) || Objects.isNull(bo.getLeagueNoticeId())) {
//            throw new ServiceException(TASK_NOT_COMPLETE);
//        }
//        Long userId = getLoginUserId();
//        TaskDO task = taskMapper.selectById(bo.getId());
//        if (userId.equals(task.getUserId())){
//            throw new ServiceException(NOT_ACCEPT);
//        }
//        if (noticeFinishStatus.equals(notice.getStatus())) {
//            throw new ServiceException(NOT_COMMIT_REPORT_FINISH);
//        }
//        //一个月内给这个人写推荐报告不能超过一次
///*        Long countWriteReport = leagueNoticeMapper.oneMonthRepetitionWriteReport(userId, task.getUserId());
//        if (countWriteReport >= 1) {
//            throw new ServiceException(ONE_MONTH_REPETITION_WRITE_REPORT);
//        }*/
//        UserInfoRespDTO user = dukeUserApi.getUserById(userId);
//        if (Objects.nonNull(notice.getByUserId()) && user.getId().equals(notice.getByUserId())) {
////            return success();
//        }
//        if (NOTICE_STATUS_2.equals(notice.getStatus())) {
//            throw new ServiceException(NOTICE_EXIST_USER);
//        }
//        LeagueNoticeDO update = new LeagueNoticeDO().setId(bo.getLeagueNoticeId()).setStatus(NOTICE_STATUS_2)
//                .setByUserId(user.getId());
//        leagueNoticeMapper.updateById(update);
//        return success();
//    }
//
//    /**
//     * 完成推荐报告
//     * 1    更新原公告状态
//     * 2    新增一条完成公告
//     * 3    调用支付 上账接口 资金到账 公会|个人
//     * @param bo    公告vo
//     * @return
//     */
//    public CommonResult finish(TaskFinishBO bo) {
//        //操作公告表 更新和插入
//        LeagueNoticeDO notice = leagueNoticeMapper.selectById(bo.getLeagueNoticeId());
//        log.info("写推荐报告完成任务，finish notice" , notice);
//        if (noticeFinishStatus.equals(notice.getStatus())) {
//            throw new ServiceException(NOT_COMMIT_REPORT_FINISH);
//        }
//        UserInfoRespDTO user = dukeUserApi.getUserById(getLoginUserId());
//        if (BigDecimal.ZERO.compareTo(notice.getAmount()) != 0) {
//            //orderApi.doAccount(new SplitAccountReqDTO().setEnterId(user.getId()).setOrderSubId(Long.valueOf(notice.getOrderNo())));
//        }
//        LocalDateTime now = LocalDateTime.now();
//        leagueNoticeMapper.updateById(new LeagueNoticeDO().setId(bo.getLeagueNoticeId()).setStatus(noticeFinishStatus));//.setExpiresTime(now)
//        LeagueNoticeDO insert = new LeagueNoticeDO()
//                .setId(null)
//                .setTaskId(notice.getTaskId())
//                .setTaskType(notice.getTaskType())
//                .setLeagueId(notice.getLeagueId())
//                .setType(noticeFinishType)
//                .setStatus(noticeFinishStatus)
//
//                .setUseLeagueId(notice.getLeagueId())
//                .setUseUserId(user.getId())
//
//                .setByUserId(notice.getUseUserId())
//
//                .setAmount(notice.getAmount());
//        leagueNoticeMapper.insert(insert);
//
//        //发消息
//        SendMessageReqDTO message = new SendMessageReqDTO()
//                .setMessageScene(MessageSceneEnum.RECOMMENDATION)
//                .setMessageType(MessageTypeEnum.CALLBACK)
//                .setBusinessId(bo.getId())
//                .setBusinessLink(null)
//                .setReceivers(Arrays.asList(notice.getUseUserId()))
//                .setMessageParam(new ContentParamVo().setInitiatorId(user.getId()))
//                .setLanguage(LanguageEnum.en_US)
//                .setDealStatus(MessageDealStatusEnum.NO_DEAL);
//        sendMessageApi.sendMessage(message);
//        sendMessageApi.messageDeal(new MessagesUpdateReqDTO().setTaskId(bo.getLeagueNoticeId()).setStatus(MessageDealStatusEnum.ALREADY_DEAL));
//        return success();
//    }
//
//    @Override
//    public void afterPropertiesSet() {
//        LeagueNoticeFactory.register(noticeType.getValue(),this);
//    }
//}
