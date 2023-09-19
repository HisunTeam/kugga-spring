package com.hisun.kugga.duke.system.rsa;

import java.security.KeyPair;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/6 17:12
 */
public interface KeyPairHandleService {

    /**
     * 初始化公私钥对
     * 加密算法，默认RAS
     *
     * @return
     */
    KeyPair initKeyPair();
}
