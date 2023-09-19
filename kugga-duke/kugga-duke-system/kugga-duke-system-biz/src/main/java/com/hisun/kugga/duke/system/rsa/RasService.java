package com.hisun.kugga.duke.system.rsa;

import com.hisun.kugga.duke.system.api.rsa.PasswordDecryptApi;
import com.hisun.kugga.duke.system.controller.app.ras.vo.SecretReqVo;
import com.hisun.kugga.duke.system.controller.app.ras.vo.SecretRespVo;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/6 17:12
 */
public interface RasService extends PasswordDecryptApi {

    /**
     * 输入密码前获取公钥等信息
     *
     * @param secretReqVo
     * @return
     */
    SecretRespVo getPrefixSecretInfo(SecretReqVo secretReqVo);
}
