package com.hisun.kugga.duke.pay.service.userbill;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillPageReqVO;
import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillPageRspVO;
import com.hisun.kugga.duke.pay.convert.userbill.UserBillConvert;
import com.hisun.kugga.duke.pay.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.duke.pay.dal.mysql.userbill.UserBillMapper;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.lock.DistributedLocked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 用户账单 Service 实现类
 *
 * @author zhou_xiong
 */
@Service
@Validated
public class UserBillServiceImpl implements UserBillService {

    @Resource
    private UserBillMapper userBillMapper;

    @Override
    public PageResult<UserBillPageRspVO> getUserBillPage(UserBillPageReqVO pageReqVO) {
        PageResult<UserBillDO> billDOPageResult = userBillMapper.selectPage(pageReqVO);
        return UserBillConvert.INSTANCE.convertPage1(billDOPageResult);
    }

    @Override
    @DistributedLocked(lockName = "'createUserBill:'+#userBillDO.getWalletOrderNo()+#userBillDO.getUserId()", leaseTime = 5, waitTime = 0)
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insertIfNotExist(UserBillDO userBillDO) {
        Long count = userBillMapper.selectCount(new LambdaQueryWrapper<UserBillDO>()
                .eq(UserBillDO::getId, userBillDO.getId())
                .eq(UserBillDO::getWalletOrderNo, userBillDO.getWalletOrderNo()));
        if (count > 0) {
            return;
        } else {
            userBillMapper.insert(userBillDO);
        }
    }
}
