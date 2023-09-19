package com.hisun.kugga.duke.pay.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.ECKeyUtil;
import cn.hutool.crypto.KeyUtil;
import lombok.Data;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import static com.hisun.kugga.duke.pay.utils.Sm2Util.ALGORITHM;

/**
 * KeyPair国密2密钥转base64实例
 *
 * @author zhou_xiong
 */
@Data
public class KeyPairBase64 {
    private String algorithm;

    private String publicKey;

    private String privateKey;

    public KeyPairBase64() {
    }

    public KeyPairBase64(KeyPair keyPair) {
        this.publicKey = Base64.encode(keyPair.getPublic().getEncoded());
        this.privateKey = Base64.encode(keyPair.getPrivate().getEncoded());
    }

    public PublicKey getPublicKeyPair() {
        if (StrUtil.isBlank(algorithm)) {
            algorithm = ALGORITHM;
        }
        byte[] publicKeyBytes = Base64.decode(this.publicKey);
        PublicKey publicKey;
        //尝试X.509
        try {
            publicKey = KeyUtil.generatePublicKey(algorithm, publicKeyBytes);
        } catch (Exception ignore) {
            // 尝试PKCS#1
            publicKey = KeyUtil.generatePublicKey(algorithm
                    , ECKeyUtil.createOpenSSHPublicKeySpec(publicKeyBytes));
        }
        return publicKey;
    }

    public PrivateKey getPrivateKeyPair() {
        if (StrUtil.isBlank(algorithm)) {
            algorithm = ALGORITHM;
        }
        byte[] privateKeyBytes = Base64.decode(this.privateKey);
        PrivateKey privateKey;
        //尝试PKCS#8
        try {
            privateKey = KeyUtil.generatePrivateKey(algorithm, privateKeyBytes);
        } catch (Exception ignore) {
            // 尝试PKCS#1
            privateKey = KeyUtil.generatePrivateKey(algorithm
                    , ECKeyUtil.createOpenSSHPrivateKeySpec(privateKeyBytes));
        }
        return privateKey;
    }
}
