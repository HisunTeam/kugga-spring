package com.hisun.kugga.duke.system.api.rsa;

import com.hisun.kugga.duke.system.api.rsa.dto.DecryptDTO;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/8 9:34
 */
public interface PasswordDecryptApi {

    /**
     * 随机数+密码 通过公钥加密，得到密文
     * 传入公钥和密文，解密得到原文
     * 此时的原文就只是密码
     *
     * @param decryptDTO
     * @return
     */
    String decrypt(DecryptDTO decryptDTO);
}
