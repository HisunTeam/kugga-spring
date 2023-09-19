package com.hisun.kugga.duke.pay.service.leaguebill;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.duke.pay.controller.app.leaguebill.vo.LeagueBillPageReqVO;
import com.hisun.kugga.duke.pay.controller.app.leaguebill.vo.LeagueBillPageRspVO;
import com.hisun.kugga.duke.pay.controller.app.redpacket.vo.RedPacketInfo;
import com.hisun.kugga.duke.pay.convert.leaguebill.LeagueBillConvert;
import com.hisun.kugga.duke.pay.dal.dataobject.leaguebill.LeagueBillDO;
import com.hisun.kugga.duke.pay.dal.mysql.leaguebill.LeagueBillMapper;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.lock.DistributedLocked;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;


/**
 * 公会账单 Service 实现类
 *
 * @author zhou_xiong
 */
@Service
@Validated
public class LeagueBillServiceImpl implements LeagueBillService {

    @Resource
    private LeagueBillMapper leagueBillMapper;
    @Resource
    private LeagueApi leagueApi;

    @Override
    public PageResult<LeagueBillPageRspVO> pageQuery(LeagueBillPageReqVO pageVO) {
        // 判断当前登录人是否在该公会下
        isLeagueMember(pageVO.getLeagueId());
        PageResult<LeagueBillDO> billDOPageResult = leagueBillMapper.selectPage(pageVO);
        return LeagueBillConvert.INSTANCE.convertPage1(billDOPageResult);
    }

    private void isLeagueMember(Long leagueId) {
        Boolean leagueMember = leagueApi.isLeagueMember(leagueId, SecurityFrameworkUtils.getLoginUserId());
        if (!leagueMember) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.IS_NOT_LEAGUE_MEMBER);
        }
    }

    @Override
    @DistributedLocked(lockName = "'createLeagueBill:'+#leagueBillDO.getWalletOrderNo()+#leagueBillDO.getLeagueId()", leaseTime = 5, waitTime = 0)
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insertIfNotExist(LeagueBillDO leagueBillDO) {
        Long count = leagueBillMapper.selectCount(new LambdaQueryWrapper<LeagueBillDO>()
                .eq(LeagueBillDO::getWalletOrderNo, leagueBillDO.getWalletOrderNo()));
        if (count > 0) {
            return;
        } else {
            leagueBillMapper.insert(leagueBillDO);
        }
    }

    @Override
    public List<RedPacketInfo> redPacketDetail(Long leagueId, Long billId) {
        isLeagueMember(leagueId);
        List<RedPacketInfo> redPacketInfos = leagueBillMapper.redPacketDetail(billId);
        return redPacketInfos;
    }

}
