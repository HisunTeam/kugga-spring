package com.hisun.kugga.duke.pay.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.ECKeyUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;


/**
 * @author: zhou_xiong
 */
@Slf4j
public class Sm2Util {

    public static final String ALGORITHM = "SM2";
    /**
     * 公私密钥对，不要改动
     */
    private static String PRIVATE_KEY = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgn3BMJ/Jf5a1+SVOxJ9LiUzOKk6cZzr1JL0a7+MbfjlOgCgYIKoEcz1UBgi2hRANCAARhTfhetj7TJ140gT0acXaybpqIIYWam5res1i1sbs8yug+qwc+FOMrvD3FEfqmSLAr0KJ+CzgMk1Lki2oW809X";
    private static String PUBLIC_KEY = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEYU34XrY+0ydeNIE9GnF2sm6aiCGFmpua3rNYtbG7PMroPqsHPhTjK7w9xRH6pkiwK9Cifgs4DJNS5ItqFvNPVw==";

    private Sm2Util() {
    }

    /**
     * 公钥加密
     *
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        SM2 sm2 = new SM2();
        byte[] publicKeyBytes = Base64.decode(PUBLIC_KEY);
        PublicKey publicKey;
        //尝试X.509
        try {
            publicKey = KeyUtil.generatePublicKey(ALGORITHM, publicKeyBytes);
        } catch (Exception ignore) {
            // 尝试PKCS#1
            publicKey = KeyUtil.generatePublicKey(ALGORITHM
                    , ECKeyUtil.createOpenSSHPublicKeySpec(publicKeyBytes));
        }
        sm2.setPublicKey(publicKey);
        byte[] encrypt = sm2.encrypt(data.getBytes(), KeyType.PublicKey);
        return Base64.encode(encrypt);
    }

    public static boolean matches(String data, String encryptStr) {
        if (StrUtil.hasEmpty(data, encryptStr)) {
            return false;
        }
        String decrypt = decrypt(encryptStr);
        return StrUtil.equals(decrypt, data);
    }

    /**
     * 私钥解密
     *
     * @param encryptStr
     * @return
     */
    public static String decrypt(String encryptStr) {
        SM2 sm2 = new SM2();
        byte[] privateKeyBytes = Base64.decode(PRIVATE_KEY);
        PrivateKey privateKey;
        //尝试PKCS#8
        try {
            privateKey = KeyUtil.generatePrivateKey(ALGORITHM, privateKeyBytes);
        } catch (Exception ignore) {
            // 尝试PKCS#1
            privateKey = KeyUtil.generatePrivateKey(ALGORITHM
                    , ECKeyUtil.createOpenSSHPrivateKeySpec(privateKeyBytes));
        }
        sm2.setPrivateKey(privateKey);
        byte[] decrypt = sm2.decrypt(Base64.decode(encryptStr), KeyType.PrivateKey);
        return new String(decrypt);
    }


    public static String encrypt(String data, KeyPairBase64 key) {
        SM2 sm2 = new SM2();
        sm2.setPublicKey(key.getPublicKeyPair());
        byte[] encrypt = sm2.encrypt(data.getBytes(), KeyType.PublicKey);
        return Base64.encode(encrypt);
    }

    public static String decrypt(String data, KeyPairBase64 key) {
        SM2 sm2 = new SM2();
        sm2.setPrivateKey(key.getPrivateKeyPair());
        byte[] decrypt = sm2.decrypt(Base64.decode(data), KeyType.PrivateKey);
        return new String(decrypt);
    }

    public static KeyPairBase64 generateKeyPair() {
        KeyPair keyPair = KeyUtil.generateKeyPair(ALGORITHM);
        return new KeyPairBase64(keyPair);
    }

    public static void main(String[] args) {
        KeyPairBase64 keyPairBase64 = generateKeyPair();
        String publicKey = keyPairBase64.getPublicKey();
        String privateKey = keyPairBase64.getPrivateKey();
        log.info("公钥：" + publicKey);
        log.info("私钥：" + privateKey);
    }
}
