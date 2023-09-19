package com.hisun.kugga.duke.batch.service.impl;

import com.hisun.kugga.duke.batch.bo.BillBO;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguebill.LeagueBillDO;
import com.hisun.kugga.duke.batch.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.duke.batch.service.BillService;
import com.hisun.kugga.duke.batch.service.LeagueBillService;
import com.hisun.kugga.duke.batch.service.UserBillService;
import com.hisun.kugga.duke.common.CommonConstants;
import com.hisun.kugga.duke.enums.AccountType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hisun.kugga.duke.common.CommonConstants.SNOWFLAKE;

/**
 * @author: zhou_xiong
 */
@Service
public class BillServiceImpl implements BillService {
    @Resource
    private UserBillService userBillService;
    @Resource
    private LeagueBillService leagueBillService;

    @Override
    public void saveBillByAccountType(BillBO billBO) {
        AccountType accountType = billBO.getAccountType();
        String billNo = SNOWFLAKE.nextIdStr();
        switch (accountType) {
            case USER:
                UserBillDO userBillDO = new UserBillDO();
                userBillDO.setBillNo(billNo);
                userBillDO.setWalletOrderNo(billBO.getWalletOrderNo());
                userBillDO.setUserId(billBO.getObjectId());
                userBillDO.setAmount(billBO.getAmount());
                userBillDO.setFee(billBO.getFee());
                userBillDO.setStatus(CommonConstants.BillStatus.SUCCESS);
                userBillDO.setRemark(billBO.getRemark());
                userBillService.insertIfNotExist(userBillDO);
                break;
            case LEAGUE:
                LeagueBillDO leagueBillDO = new LeagueBillDO();
                leagueBillDO.setBillNo(billNo);
                leagueBillDO.setWalletOrderNo(billBO.getWalletOrderNo());
                leagueBillDO.setLeagueId(billBO.getObjectId());
                leagueBillDO.setAmount(billBO.getAmount());
                leagueBillDO.setStatus(CommonConstants.BillStatus.SUCCESS);
                leagueBillDO.setRemark(billBO.getRemark());
                leagueBillService.insertIfNotExist(leagueBillDO);
                break;
            case PLATFORM:
                // todo 是否要保存平台账单
                return;
        }
    }
}
