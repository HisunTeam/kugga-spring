package com.hisun.kugga.duke.system.api.rsa;


import com.hisun.kugga.duke.system.api.rsa.dto.SecretKeyReqDTO;
import com.hisun.kugga.duke.system.api.rsa.dto.SecretKeyRespDTO;

import java.security.KeyPair;

/**
 * 加密接口用于获取公私钥对
 */
public interface KeyPairApi {

//    /**
//     * 获取公私钥对
//     * @return
//     */
//    KeyPair getKeyPair();

    /**
     * 获取公钥
     *
     * @return
     */
    SecretKeyRespDTO getPublicKey();

    /**
     * 加密  原文密码转密文
     *
     * @param secretKeyReqDTO 原文信息
     * @return 密文
     */
    SecretKeyRespDTO encrypt(SecretKeyReqDTO secretKeyReqDTO);

    /**
     * 解密  密文转原文
     *
     * @param secretKeyReqDTO 密文信息
     * @return 明文
     */
    SecretKeyRespDTO decrypt(SecretKeyReqDTO secretKeyReqDTO);

    /**
     * 解密  密文转原文
     *
     * @param secretKeyReqDTO 密文信息
     * @param keyPair         公私钥对
     * @return
     */
    SecretKeyRespDTO decrypt(SecretKeyReqDTO secretKeyReqDTO, KeyPair keyPair);

    /**
     * 业务实际解密接口
     * 当解密失败时会抛 解密失败,此时业务放需要捕获异常
     *
     * @param secretKeyReqDTO 密文、公钥
     * @return 原文密码
     */
    SecretKeyRespDTO decryptByBusiness(SecretKeyReqDTO secretKeyReqDTO);


}
