package com.hisun.kugga.duke.league.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.*;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
import com.hisun.kugga.duke.league.LeagueSubscribeType;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueJoinApprovalDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueJoinDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueMemberDO;
import com.hisun.kugga.duke.league.dal.mysql.LeagueJoinApprovalMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueJoinMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueMemberMapper;
import com.hisun.kugga.duke.league.enums.LeagueJoinTypeEnums;
import com.hisun.kugga.duke.league.enums.LeagueMemberLevelEnums;
import com.hisun.kugga.duke.league.service.LeagueJoinApprovalService;
import com.hisun.kugga.duke.league.service.LeagueMemberService;
import com.hisun.kugga.duke.league.service.LeagueSubscribeService;
import com.hisun.kugga.duke.league.vo.LeagueMemberJoinVO;
import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalPageReqVO;
import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalRespVO;
import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalUpdateReqVO;
import com.hisun.kugga.duke.league.vo.subscribe.SubscribeVo;
import com.hisun.kugga.duke.pay.api.order.OrderApi;
import com.hisun.kugga.duke.pay.api.order.dto.ReceiverInfo;
import com.hisun.kugga.duke.pay.api.order.dto.RefundReqDTO;
import com.hisun.kugga.duke.pay.api.order.dto.SplitAccountReqDTO;
import com.hisun.kugga.duke.system.api.message.SendMessageApi;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.utils.SplitAccountUtil;
import com.hisun.kugga.duke.utils.vo.SplitAccountVo;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.web.core.util.WebFrameworkUtils.getLoginUserId;


/**
 * @Description:加入公会审批 Service 实现类
 * @author： Lin
 * @Date 2022/9/13 17:18
 */
@Service
@Validated
public class LeagueJoinApprovalServiceImpl implements LeagueJoinApprovalService {

    @Resource
    private LeagueJoinApprovalMapper approvalMapper;
    @Resource
    private LeagueJoinMapper joinMapper;
    @Resource
    private LeagueMemberMapper memberMapper;
    @Resource
    private LeagueMemberService memberService;
    @Resource
    private OrderApi orderApi;
    @Resource
    private SendMessageApi sendMessageApi;
    @Resource
    private LeagueSubscribeService subscribeService;

    @Override
    public PageResult<LeagueJoinApprovalRespVO> getLeagueJoinApprovalPage(LeagueJoinApprovalPageReqVO pageVO) {
        Page<LeagueJoinApprovalRespVO> pageParam = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());

        //根据用户id 查询我加入且是管理员的公会ids，然后根据ids获取审批记录
        List<Long> adminLeagueIds = memberMapper.getLeagueAdminIds(getLoginUserId(), LeagueMemberLevelEnums.MEMBER.getType());
        if (ObjectUtil.isEmpty(adminLeagueIds)) {
            return new PageResult<>();
        }
        Page<LeagueJoinApprovalRespVO> resPage = approvalMapper.selectApprovalPage(pageParam, pageVO, adminLeagueIds);

        return new PageResult<LeagueJoinApprovalRespVO>().setList(resPage.getRecords()).setTotal(resPage.getTotal());
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateLeagueJoinApproval(LeagueJoinApprovalUpdateReqVO reqVO) {
        //前置检查
        LeagueJoinApprovalDO approvalDO = preCheck(reqVO);

        LeagueJoinDO joinDO = joinMapper.selectById(approvalDO.getBusinessId());
        if (ObjectUtil.isNull(joinDO)) {
            throw exception(LEAGUE_JOIN_RECORD_NOT_EXIST);
        }

        //加入公会订单状态
        LeagueJoinDO joinUpdateDO = new LeagueJoinDO();
        joinUpdateDO.setId(approvalDO.getBusinessId());
        joinUpdateDO.setBusinessStatus(reqVO.getApprovalType().getValue());

        LeagueJoinApprovalDO updateApprovalDo = new LeagueJoinApprovalDO();
        updateApprovalDo.setId(reqVO.getId());
        updateApprovalDo.setStatus(reqVO.getApprovalType().getValue());

        //是否同意审批
        boolean isApproval = reqVO.getApprovalType() == LeagueJoinApprovalTypeEnum.AGREE;

        // 付费的 根据是否同意修改金额状态,同意分账；拒绝退款
        if (joinDO.getPayFlag()) {
            int amountStatus = isApproval ? PayStatusEnum.SPLIT_ACCOUNT.getValue() : PayStatusEnum.REFUND.getValue();
            joinUpdateDO.setAmountStatus(amountStatus);
        }

        //同意
        if (isApproval) {
            //把申请用户加公会
            insertLeagueMember(joinDO);
            // 同意就增加订阅记录 不管免费付费
            subscribePackage(joinDO);
        }

        //修改审批记录 及加入公会的业务状态
        approvalMapper.updateById(updateApprovalDo);
        joinMapper.updateById(joinUpdateDO);

        //同意/拒绝后发送消息通知
        sendMessage(reqVO, joinDO);

        //最后来分账 付费的 同意就分账，拒绝就退款
        if (joinDO.getPayFlag()) {
            if (isApproval) {
                splitAccount(joinDO);
            } else {
                refund(joinDO);
            }
        }
    }


    @Override
    public Long getApprovalCount() {
        //根据用户id 查询我加入且是管理员的公会ids，然后根据ids获取未审批
        List<Long> adminLeagueIds = memberMapper.getLeagueAdminIds(getLoginUserId(), LeagueMemberLevelEnums.MEMBER.getType());
        if (ObjectUtil.isEmpty(adminLeagueIds)) {
            return 0L;
        }
        List<LeagueJoinApprovalDO> approvalDos = approvalMapper.selectListUnApproval(adminLeagueIds, LocalDateTime.now());
        int size = approvalDos.size();
        Long count = Long.valueOf(size + "");
        return count;
    }

    /**
     * 公会加入订阅
     *
     * @param joinDO
     */
    private void subscribePackage(LeagueJoinDO joinDO) {
        SubscribeVo build = SubscribeVo.builder()
                .leagueId(joinDO.getLeagueId())
                .userId(joinDO.getUserId())
                .amount(joinDO.getAmount())
                .subscribeType(LeagueSubscribeType.getByCode(joinDO.getSubscribeType()))
                .build();
        subscribeService.subscribePackage(build);
    }

    /**
     * 发送消息
     *
     * @param reqVO
     * @param joinDO
     */
    private void sendMessage(LeagueJoinApprovalUpdateReqVO reqVO, LeagueJoinDO joinDO) {
        /*
        [后端开发工程师]同意你加入公会
        [后端开发工程师]拒绝你加入公会
         */
        boolean isAgree = ObjectUtil.equal(reqVO.getApprovalType(), LeagueJoinApprovalTypeEnum.AGREE);
        MessageTypeEnum typeEnum = isAgree ? MessageTypeEnum.CALLBACK : MessageTypeEnum.CALLBACK2;

        MessageTemplateEnum template = isAgree ? MessageTemplateEnum.JOIN_LEAGUE_ACTIVE_AGREE : MessageTemplateEnum.JOIN_LEAGUE_ACTIVE_REJECT;

        SendMessageReqDTO message = SendMessageReqDTO.builder()
                .messageTemplate(template)
                .messageScene(MessageSceneEnum.JOIN_LEAGUE_ACTIVE)
                .messageType(typeEnum)
                .businessId(joinDO.getId())
                //给申请加入公会的人发消息
                .receivers(Arrays.asList(joinDO.getUserId()))
                .messageParam(
                        new ContentParamVo()
                                .setInitiatorId(getLoginUserId())
                                .setInitiatorLeagueId(joinDO.getLeagueId())
                )
                .language(LanguageEnum.en_US)
                .dealStatus(MessageDealStatusEnum.NO_DEAL)
                .build();
        sendMessageApi.sendMessage(message);
    }

    /**
     * 用户加入公会
     *
     * @param joinDO
     */
    private void insertLeagueMember(LeagueJoinDO joinDO) {
        LeagueMemberJoinVO joinVO = new LeagueMemberJoinVO();
        joinVO.setLeagueId(joinDO.getLeagueId());
        joinVO.setUserId(joinDO.getUserId());
        joinVO.setLevel(LeagueMemberLevelEnums.MEMBER.getType());
        joinVO.setJoinType(LeagueJoinTypeEnums.APPLY.getType());
        joinVO.setRelationUserId(getLoginUserId());
        joinVO.setJoinTime(LocalDateTime.now());
        joinVO.setUpdateTime(LocalDateTime.now());
        memberService.joinLeague(joinVO);
    }

    /**
     * 分账
     *
     * @param joinDO
     */
    private void splitAccount(LeagueJoinDO joinDO) {
        SplitAccountVo accountVo = SplitAccountUtil.splitByThree(joinDO.getAmount());
        //加入公会 公会创建者、公会、平台 532分账
        ReceiverInfo leagueAdmin = new ReceiverInfo().setReceiverId(joinDO.getLeagueCreateId()).setAccountType(AccountType.USER).setAmount(accountVo.getPersonAmount());
        ReceiverInfo league = new ReceiverInfo().setReceiverId(joinDO.getLeagueId()).setAccountType(AccountType.LEAGUE).setAmount(accountVo.getLeagueAmount());
        ReceiverInfo platform = new ReceiverInfo().setReceiverId(1L).setAccountType(AccountType.PLATFORM).setAmount(accountVo.getPlatformAmount());
        SplitAccountReqDTO splitAccountReqDTO = new SplitAccountReqDTO()
                .setOrderType(OrderType.ACTIVE_JOIN_LEAGUE_SPLIT)
                .setAppOrderNo(joinDO.getAppOrderNo())
                .setReceiverList(Arrays.asList(leagueAdmin, league, platform));
        orderApi.splitAccount(splitAccountReqDTO);
    }

    /**
     * 退款
     *
     * @param joinDO
     */
    private void refund(LeagueJoinDO joinDO) {
        orderApi.refund(new RefundReqDTO().setAppOrderNo(joinDO.getAppOrderNo()).setRefundAmount(joinDO.getAmount()));
    }

    /**
     * 前置校验
     *
     * @param reqVO
     * @return
     */
    @NotNull
    private LeagueJoinApprovalDO preCheck(LeagueJoinApprovalUpdateReqVO reqVO) {
        // id不能为null，审批记录存在，审批状态不能是已过期 已处理
        if (ObjectUtil.isNull(reqVO.getId())) {
            throw exception(LEAGUE_JOIN_APPROVAL_ID_IS_NULL);
        }

        LeagueJoinApprovalDO approvalDO = approvalMapper.selectById(reqVO.getId());
        if (ObjectUtil.isNull(approvalDO)) {
            throw exception(LEAGUE_JOIN_APPROVAL_NOT_EXISTS);
        }
        //已过期
        if (LocalDateTime.now().isAfter(approvalDO.getExpireTime())) {
            throw exception(LEAGUE_JOIN_APPROVAL_EXPIRE);
        }

        //审批记录状态必须是 初始化状态
        if (ObjectUtil.notEqual(approvalDO.getStatus(), LeagueJoinApprovalTypeEnum.INIT.getValue())) {
            throw exception(LEAGUE_JOIN_APPROVAL_PROCESSED);
        }
        // 管理员审批状态只能是 同意or拒绝
        if (ObjectUtil.notEqual(reqVO.getApprovalType(), LeagueJoinApprovalTypeEnum.AGREE) &&
                ObjectUtil.notEqual(reqVO.getApprovalType(), LeagueJoinApprovalTypeEnum.REJECT)) {
            throw exception(APPROVAL_TYPE_EXCEPTION);
        }

        // 管理员才能审批
        LeagueMemberDO leagueAdmin = memberMapper.isLeagueAdmin(approvalDO.getLeagueId(), getLoginUserId());
        if (ObjectUtil.isNotNull(leagueAdmin) && ObjectUtil.equal(leagueAdmin.getLevel(), LeagueMemberLevelEnums.MEMBER.getType())) {
            throw exception(LEAGUE_ADMIN_CAN_APPROVAL);
        }

        // 已加入后审批同意报错
        LeagueMemberDO leagueUser = memberMapper.getByLeagueIdAndUserId(approvalDO.getLeagueId(), approvalDO.getUserId());
        if (ObjectUtil.isNotNull(leagueUser) && ObjectUtil.equal(reqVO.getApprovalType(), LeagueJoinApprovalTypeEnum.AGREE)) {
            throw exception(APPLICANT_ALREADY_JOIN);
        }
        return approvalDO;
    }


}
