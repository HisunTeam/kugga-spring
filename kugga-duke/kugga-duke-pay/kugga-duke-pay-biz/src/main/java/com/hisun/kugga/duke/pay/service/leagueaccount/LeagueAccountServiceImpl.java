package com.hisun.kugga.duke.pay.service.leagueaccount;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.pay.api.wallet.dto.AccountDetailReqBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.AccountDetailRspBody;
import com.hisun.kugga.duke.pay.dal.dataobject.leagueaccount.LeagueAccountDO;
import com.hisun.kugga.duke.pay.dal.mysql.leagueaccount.LeagueAccountMapper;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.util.amount.AmountUtil;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Service
public class LeagueAccountServiceImpl implements LeagueAccountService {
    @Resource
    private LeagueAccountMapper leagueAccountMapper;
    @Resource
    private WalletApi walletApi;

    @Override
    public void updateAccount(Long leagueId, BigDecimal amount) {
        int count = leagueAccountMapper.updateAccount(leagueId, amount);
        if (count <= 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.UPDATE_LEAGUE_ACCOUNT_FAILED);
        }
    }

    @Override
    public LeagueAccountDO getLeagueAccountByLeagueId(Long leagueId) {
        LeagueAccountDO leagueAccountDO = leagueAccountMapper.selectOne(new LambdaQueryWrapper<LeagueAccountDO>()
                .eq(LeagueAccountDO::getLeagueId, leagueId));
        if (ObjectUtil.isNull(leagueAccountDO) || StrUtil.isEmpty(leagueAccountDO.getAccountId())) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.LEAGUE_ACCOUNT_NOT_EXISTS);
        }
        return leagueAccountDO;
    }

    @Override
    public BigDecimal getLeagueBalance(Long leagueId) {
        LeagueAccountDO leagueAccountDO = getLeagueAccountByLeagueId(leagueId);
        AccountDetailReqBody accountDetailReqBody = new AccountDetailReqBody().setAccount(leagueAccountDO.getAccountId());
        AccountDetailRspBody detail = walletApi.accountDetail(accountDetailReqBody);
        return AmountUtil.fenToYuan(detail.getBalance());
    }

    @Override
    public int updateLeagueAccount(Long leagueId, String accountId) {
        LambdaQueryWrapperX<LeagueAccountDO> wrapperX = new LambdaQueryWrapperX<LeagueAccountDO>().eq(LeagueAccountDO::getLeagueId, leagueId);
        return leagueAccountMapper.update(new LeagueAccountDO().setAccountId(accountId), wrapperX);
    }
}
