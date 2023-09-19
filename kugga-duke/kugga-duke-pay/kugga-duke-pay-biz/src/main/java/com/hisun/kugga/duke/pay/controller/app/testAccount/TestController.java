package com.hisun.kugga.duke.pay.controller.app.testAccount;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.pay.api.wallet.dto.AccountDetailReqBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.AccountDetailRspBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.CreateAccountReqBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.CreateAccountRspBody;
import com.hisun.kugga.duke.pay.dal.dataobject.leagueaccount.LeagueAccountDO;
import com.hisun.kugga.duke.pay.dal.dataobject.useraccount.UserAccountDO;
import com.hisun.kugga.duke.pay.dal.mysql.leagueaccount.LeagueAccountMapper;
import com.hisun.kugga.duke.pay.dal.mysql.useraccount.UserAccountMapper;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.util.amount.AmountUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;


@Api(tags = "A8-账户")
@RestController
@RequestMapping("/account")
@Validated
public class TestController {

    @Resource
    private WalletApi walletApi;
    @Resource
    private UserAccountMapper userAccountMapper;
    @Resource
    private LeagueAccountMapper leagueAccountMapper;
    @Value("${kugga.pay.wallet.platform-account}")
    private String platformAccount;


    @PostMapping("/testAccount")
    @ApiOperation("开通钱包账户-test")
    public CommonResult<CreateAccountRspBody> createUserAccount(@RequestBody @Valid AccountTestVO testVO) {
        //todo dev专用，生产del
        CreateAccountRspBody accountRspBody = null;
        if (ObjectUtil.equal("1", testVO.getFlag())) {
            //创建一个外部钱包账户
            accountRspBody = createAccount();
        } else if (ObjectUtil.equal("2", testVO.getFlag())) {
            //为某个user设置外部钱包账户
            openOneUserAccount(testVO);
        } else if (ObjectUtil.equal("3", testVO.getFlag())) {
            //为某个公会设置外部钱包账户
            openOneLeagueAccount(testVO);
        } else if (ObjectUtil.equal("4", testVO.getFlag())) {
            //为所有还未开通钱包账户的公会开通
            openLeagueAccount();
        } else if (ObjectUtil.equal("5", testVO.getFlag())) {
            //为所有还未开通钱包账户的用户开通
            openUserAccount();
        }
        return success(accountRspBody);
    }

    @PostMapping("/testAccountDetail")
    public CommonResult<BigDecimal> accountDetail(@RequestBody @Valid AccountDetailVO accountDetailVO) {
        switch (accountDetailVO.getAccountType()) {
            case PLATFORM:
                if (!platformAccount.equals(accountDetailVO.getWalletAccountId())) {
                    ServiceException.throwServiceException(BusinessErrorCodeConstants.ILLEGAL_OPERATOR);
                }
                break;
            case LEAGUE:
                LeagueAccountDO leagueAccountDO = leagueAccountMapper.selectOne("account_id", accountDetailVO.getWalletAccountId());
                if (ObjectUtil.isNull(leagueAccountDO)) {
                    ServiceException.throwServiceException(BusinessErrorCodeConstants.ILLEGAL_OPERATOR);
                }
                break;
            case USER:
                UserAccountDO userAccountDO = userAccountMapper.selectOne("account_id", accountDetailVO.getWalletAccountId());
                if (ObjectUtil.isNull(userAccountDO)) {
                    ServiceException.throwServiceException(BusinessErrorCodeConstants.ILLEGAL_OPERATOR);
                }
                break;
            default:
                break;
        }
        AccountDetailRspBody detail = walletApi.accountDetail(new AccountDetailReqBody().setAccount(accountDetailVO.getWalletAccountId()));
        return success(AmountUtil.fenToYuan(detail.getBalance()));
    }


    private void openOneLeagueAccount(AccountTestVO testVO) {
        LeagueAccountDO leagueAccountDO = leagueAccountMapper.selectOne(LeagueAccountDO::getLeagueId, testVO.getLeagueId());
        if (ObjectUtil.isNull(leagueAccountDO)) {
            return;
        }
        LeagueAccountDO updateDo = new LeagueAccountDO();
        updateDo.setId(leagueAccountDO.getId());
        updateDo.setAccountId(createAccount().getAccount());
        leagueAccountMapper.updateById(updateDo);
    }

    private void openOneUserAccount(AccountTestVO testVO) {
        UserAccountDO userAccountDO = userAccountMapper.selectOne(UserAccountDO::getUserId, testVO.getUserId());
        if (ObjectUtil.isNull(userAccountDO)) {
            return;
        }
        UserAccountDO updateDo = new UserAccountDO();
        updateDo.setId(userAccountDO.getId());
        updateDo.setAccountId(createAccount().getAccount());
        userAccountMapper.updateById(updateDo);
    }

    private void openUserAccount() {
        List<UserAccountDO> userAccountDOS = userAccountMapper.selectList();
        if (ObjectUtil.isEmpty(userAccountDOS)) {
            return;
        }
        for (UserAccountDO accountDO : userAccountDOS) {
            //未设置账户的才需要设置
            if (ObjectUtil.isEmpty(accountDO.getAccountId())) {
                UserAccountDO updateAccountDo = new UserAccountDO();
                updateAccountDo.setId(accountDO.getId());
                updateAccountDo.setAccountId(createAccount().getAccount());
                userAccountMapper.updateById(updateAccountDo);
            }
        }
    }

    private void openLeagueAccount() {
        List<LeagueAccountDO> leagueAccountDOS = leagueAccountMapper.selectList();
        if (ObjectUtil.isEmpty(leagueAccountDOS)) {
            return;
        }
        for (LeagueAccountDO leagueAccountDO : leagueAccountDOS) {
            //未设置账户的才需要设置
            if (ObjectUtil.isEmpty(leagueAccountDO.getAccountId())) {
                LeagueAccountDO updateAccountDo = new LeagueAccountDO();
                updateAccountDo.setId(leagueAccountDO.getId());
                updateAccountDo.setAccountId(createAccount().getAccount());
                leagueAccountMapper.updateById(updateAccountDo);
            }
        }
    }

    /**
     * 外部钱包账户开通
     *
     * @return
     */
    private CreateAccountRspBody createAccount() {
        return walletApi.createAccount(new CreateAccountReqBody());
    }
}
