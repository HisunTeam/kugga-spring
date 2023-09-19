package com.hisun.kugga.duke.system.rsa;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.KeyUtil;
import com.hisun.kugga.duke.system.rsa.bo.KeyPairBo;
import com.hisun.kugga.duke.system.rsa.redis.KeyPairRedisRepository;
import com.hisun.kugga.duke.system.rsa.util.RasUtils;
import com.hisun.kugga.framework.lock.DistributedLocked;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/6 17:13
 */
@Slf4j
@Service
public class KeyPairHandleServiceImpl implements KeyPairHandleService {

//    public static String DUKE_KEYPAIR_LOCK = "duke_keypair_lock";

    @Resource
    private KeyPairRedisRepository redisRepository;

    @DistributedLocked(lockName = "'duke_keypair_lock'", waitTime = 4, leaseTime = 8)
    @Override
    public KeyPair initKeyPair() {
        //先尝试获取 已有直接返回
        String publicRedisKey = redisRepository.getKeyPairKeyId();
        if (ObjectUtil.isNotEmpty(publicRedisKey)) {
            KeyPairBo keyPairKeys = redisRepository.getKeyPairKeys(publicRedisKey);
            return RasUtils.convertKeyPairBoToKeyPair(keyPairKeys);
        }

        //生成一对密钥
        KeyPair keyPair = KeyUtil.generateKeyPair(RasUtils.Algorithm);
        //获取私钥
        PrivateKey privateKey = keyPair.getPrivate();
        //获取公钥
        PublicKey publicKey = keyPair.getPublic();

        String publicKeyCode = Base64.encode(publicKey.getEncoded());
        String privateKeyCode = Base64.encode(privateKey.getEncoded());
        KeyPairBo keyPairBo = new KeyPairBo(publicKeyCode, privateKeyCode);

        //设置当前使用公钥及公司钥信息
        redisRepository.setKeyPairKeyId(publicKeyCode);
        redisRepository.setKeyPairKeys(keyPairBo);
        return keyPair;
    }
}
