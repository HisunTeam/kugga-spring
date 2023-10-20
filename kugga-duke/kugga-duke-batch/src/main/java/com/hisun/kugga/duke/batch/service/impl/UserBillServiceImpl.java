package com.hisun.kugga.duke.batch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.duke.batch.dal.mysql.userbill.UserBillMapper;
import com.hisun.kugga.duke.batch.service.UserBillService;
import com.hisun.kugga.framework.lock.DistributedLocked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * User Bill Service Implementation
 *
 * Author: Zhou Xiong
 */
@Service
@Validated
public class UserBillServiceImpl implements UserBillService {

    @Resource
    private UserBillMapper userBillMapper;

    @Override
    @DistributedLocked(lockName = "'createUserBill:'+#userBillDO.getWalletOrderNo()+#userBillDO.getUserId()", leaseTime = 5, waitTime = 0)
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insertIfNotExist(UserBillDO userBillDO) {
        Long count = userBillMapper.selectCount(new LambdaQueryWrapper<UserBillDO>()
                .eq(UserBillDO::getWalletOrderNo, userBillDO.getWalletOrderNo()));
        if (count > 0) {
            return;
        } else {
            userBillMapper.insert(userBillDO);
        }
    }
}
