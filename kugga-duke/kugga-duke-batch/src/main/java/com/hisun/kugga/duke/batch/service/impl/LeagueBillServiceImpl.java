package com.hisun.kugga.duke.batch.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguebill.LeagueBillDO;
import com.hisun.kugga.duke.batch.dal.mysql.leaguebill.LeagueBillMapper;
import com.hisun.kugga.duke.batch.service.LeagueBillService;
import com.hisun.kugga.framework.lock.DistributedLocked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;


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

}
