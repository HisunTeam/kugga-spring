package com.hisun.kugga.duke.pay.api.useraccount;

import com.hisun.kugga.duke.enums.SecretTypeEnum;
import com.hisun.kugga.duke.pay.api.useraccount.dto.AccountPwdVerifyDTO;
import com.hisun.kugga.duke.pay.service.useraccount.UserAccountService;
import com.hisun.kugga.duke.system.api.rsa.PasswordDecryptApi;
import com.hisun.kugga.duke.system.api.rsa.dto.DecryptDTO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.web.core.util.WebFrameworkUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.ILLEGAL_PARAMS;

/**
 * @author: zhou_xiong
 */
@Service
public class UserAccountApiImpl implements UserAccountApi {

    @Resource
    private UserAccountService userAccountService;
    @Resource
    private PasswordDecryptApi decryptApi;


    @Override
    public boolean getPayPasswordFlag(Long userId) {
        Optional.ofNullable(userId).orElseThrow(() -> new ServiceException(ILLEGAL_PARAMS, "userId"));
        return userAccountService.getPwdFlag(userId);
    }

    @Override
    public boolean verifyPayPassword(Long userId, String payPassword) {
        Optional.ofNullable(userId).orElseThrow(() -> new ServiceException(ILLEGAL_PARAMS, "userId"));
        Optional.ofNullable(payPassword).orElseThrow(() -> new ServiceException(ILLEGAL_PARAMS, "payPassword"));
        return userAccountService.verifyPayPassword(userId, payPassword);
    }

    @Override
    public boolean verifyPayPassword(AccountPwdVerifyDTO pwdVerifyDTO) {
        DecryptDTO decryptDTO = new DecryptDTO()
                .setPublicKey(pwdVerifyDTO.getPublicKey())
                .setPassword(pwdVerifyDTO.getPassword())
                .setType(SecretTypeEnum.PAY);
        String passwordDecrypt = decryptApi.decrypt(decryptDTO);

        return verifyPayPassword(WebFrameworkUtils.getLoginUserId(), passwordDecrypt);
    }
}
