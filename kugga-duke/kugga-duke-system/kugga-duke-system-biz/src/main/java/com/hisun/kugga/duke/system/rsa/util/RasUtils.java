package com.hisun.kugga.duke.system.rsa.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import com.hisun.kugga.duke.system.rsa.bo.KeyPairBo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.ACQUIRE_KEY_PARIS_FAIL;
import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.KeyPairBo_EXPIRE;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/7 9:36
 */
@Slf4j
public class RasUtils {

    public static String Algorithm = AsymmetricAlgorithm.RSA.getValue();


    /**
     * 把KeyPairBo转为KeyPair
     *
     * @param keyPairKeys 密钥字符对
     * @return 密钥对
     */
    @NotNull
    public static KeyPair convertKeyPairBoToKeyPair(KeyPairBo keyPairKeys) {
        if (ObjectUtil.isNull(keyPairKeys)) {
            throw exception(KeyPairBo_EXPIRE);
        }
        PublicKey publicKey = null;
        PrivateKey privateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(Algorithm);
            publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(keyPairKeys.getPublicKey())));
            privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(keyPairKeys.getPrivateKey())));
        } catch (Exception e) {
            log.error("get KeyPair is faire", e);
            throw exception(ACQUIRE_KEY_PARIS_FAIL);
        }
        return new KeyPair(publicKey, privateKey);
    }
}
