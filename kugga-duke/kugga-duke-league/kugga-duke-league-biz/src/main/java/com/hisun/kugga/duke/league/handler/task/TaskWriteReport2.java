//package com.hisun.kugga.duke.league.handler.task;
//
//import cn.hutool.core.util.ObjectUtil;
//import com.hisun.kugga.duke.league.bo.task.*;
//import com.hisun.kugga.duke.league.enums.LeagueNoticeTypeEnum;
//import com.hisun.kugga.duke.league.enums.TaskTypeEnum;
//import com.hisun.kugga.duke.league.factory.TaskFactory;
//import com.hisun.kugga.duke.league.service.LeagueNoticeService;
//import com.hisun.kugga.duke.league.utils.TaskUtils;
//import com.hisun.kugga.duke.league.vo.task.TaskCreateResultVO;
//import com.hisun.kugga.duke.league.vo.task.TaskInitResultVO;
//import com.hisun.kugga.duke.pay.api.order.OrderApi;
//import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
//import com.hisun.kugga.framework.common.exception.ServiceException;
//import com.hisun.kugga.framework.common.pojo.CommonResult;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//import static com.hisun.kugga.duke.league.constants.TaskConstants.REPORT_INVITE_LEAGUE_LIST_EMPTY;
//import static com.hisun.kugga.duke.league.constants.TaskConstants.REQUIRED_PARAMETER_IS_EMPTY;
//import static com.hisun.kugga.framework.common.pojo.CommonResult.success;
//
///**
// * @Author: ZhouAnYan 备份9.15代码 a270f6b19dc12c5c4954df5135ba39b3d14c9d13
// * @Email: yan_zay@163.com
// * @Date: 2022-07-25 10:37
// */
//@Slf4j
////@Component
//@AllArgsConstructor
//public class TaskWriteReport2 extends AbstractTaskHandler {
//
//    private static final TaskTypeEnum taskType = TaskTypeEnum.TASK_TYPE_1;
//    private static final LeagueNoticeTypeEnum noticeType = LeagueNoticeTypeEnum.NOTICE_TYPE_1;
//
//    LeagueNoticeService leagueNoticeService;
//    OrderApi orderApi;
//    DukeUserApi dukeUserApi;
//    TaskUtils taskUtils;
//
//    @Override
//    public TaskInitResultVO initTask(TaskInitBO bo) {
//        //前置校验
//        preCheck(bo);
//
//        return null;
//    }
//
//
//    /**
//     * 推荐报告
//     *
//     * 前置条件
//     * 选择公会列表不能为空，必须选一个及以上
//     * 所有选择的公会 是否认证
//     *
//     * 1    写推荐报告不能自己给自己写
//     * 2    公会ID和金额不能为空
//     * 3    任务总计金额计算错误，请重新计算    判断金钱相加是否相等  请勿用BigDecimal的equals方法！错误的比较方法！
//     * 4    去重（前端做了，后端再做一次去重？）
//     * 5    判断价格是否符合预期  价格是否实时变动
//     *
//     * 代码逻辑
//     * 1    创建任务，下单，成功后保存订单号，创建任务，调用订单模块，默认下单成功(费用大于0调用订单接口)
//     * 2    支付
//     * 3    创建公会公告
//     * 4    调用消息模块，带参数过去
//     * @param bo
//     * @return
//     */
//    @Override
//    public TaskCreateResultVO createTask(TaskCreateBO bo) {
///*        List<TaskLeagueBO> leagueList = bo.getTaskCreate1().getLeagueList();
//        if (Objects.isNull(bo.getAmount())) {
//            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
//        }
//        if (leagueList.isEmpty()) {
//            ServiceException.throwServiceException(REPORT_INVITE_LEAGUE_LIST_EMPTY);
//        }
//        //公会是否认证
//        taskUtils.leagueAuthFlagList(leagueList);
//        //去重
//        List<TaskLeagueBO> list = leagueList.stream().distinct().collect(Collectors.toList());
//        //判断金钱相加是否相等 总金额计算不能错误
//        TaskUtils.checkLeagueList(list, bo.getAmount());
//        //价格是否实时变动
//        taskUtils.judgeLeagueRealTimePrice(leagueList, bo.getType());
//
//        //         *  下订单 总订单
//        //         *  初始化任务   保存订单号
//        //         *  提交事务
//        String appOrderNo = callOrder(bo);
////        super.insertTask(bo, new TaskDO().setOrderRecord(appOrderNo).setExpiresTime(LocalDateTime.now()));
//        //         * 支付
//        //         * 修改任务状态
//
////        bo.setLeagueNoticeType(noticeType);
////        leagueNoticeService.create(bo);*/
//        return new TaskCreateResultVO();
//    }
//
//    private String callOrder(TaskCreateBO bo) {
///*        OrderCreateReqDTO req = new OrderCreateReqDTO()
//                .setOrderType(OrderType.CREATE_RECOMMENDATION)
//                .setPayerId(getLoginUserId())
//                .setAccountType(AccountType.USER)
//                .setAmount(bo.getAmount());
//        OrderCreateRspDTO order = orderApi.createOrder(req);
//        return order.getAppOrderNo();*/
//        return null;
//    }
//
//    @Override
//    public CommonResult accept(TaskAcceptBO bo) {
//        bo.setLeagueNoticeType(noticeType);
//        return leagueNoticeService.accept(bo);
//    }
//
//    /**
//     * 完成此公告栏   公会内抢单
//     * 分配钱
//     * 查询任务下所有公告完成状态 基于所有公告反驱动更新任务状态    暂时不做
//     * @param bo
//     * @return
//     */
//    @Override
//    public TaskFinishResultBO finish(TaskFinishBO bo) {
//        bo.setLeagueNoticeType(noticeType);
//        leagueNoticeService.finish(bo);
//        return new TaskFinishResultBO();
//    }
//
//    @Override
//    public void afterPropertiesSet() {
//        TaskFactory.register(taskType.getValue(),this);
//    }
//
//    private void preCheck(TaskInitBO bo) {
//        List<TaskLeagueBO> leagueList = bo.getTaskInit1().getLeagueList();
//        if (ObjectUtil.isNull(bo.getAmount())) {
//            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
//        }
//        if (leagueList.isEmpty()) {
//            ServiceException.throwServiceException(REPORT_INVITE_LEAGUE_LIST_EMPTY);
//        }
//        //判断金钱相加是否相等 总金额计算不能错误
//        TaskUtils.checkLeagueList(leagueList, bo.getAmount());
//        //公会是否认证
//        taskUtils.leagueAuthFlagList(leagueList);
//        //去重
////        List<TaskLeagueBO> list = leagueList.stream().distinct().collect(Collectors.toList());
//        //价格是否实时变动
//        taskUtils.judgeLeagueRealTimePrice(leagueList, bo.getType());
//    }
//}
