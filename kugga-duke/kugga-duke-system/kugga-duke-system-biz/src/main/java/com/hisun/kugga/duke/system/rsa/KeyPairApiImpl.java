package com.hisun.kugga.duke.system.rsa;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.system.api.rsa.KeyPairApi;
import com.hisun.kugga.duke.system.api.rsa.dto.SecretKeyReqDTO;
import com.hisun.kugga.duke.system.api.rsa.dto.SecretKeyRespDTO;
import com.hisun.kugga.duke.system.rsa.bo.KeyPairBo;
import com.hisun.kugga.duke.system.rsa.redis.KeyPairRedisRepository;
import com.hisun.kugga.duke.system.rsa.util.RasUtils;
import com.hisun.kugga.framework.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import java.security.KeyPair;
import java.util.Optional;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;


/**
 * @Description:
 * @author： admin
 * @Date 2022/9/7 9:36
 */
@Slf4j
@Service
public class KeyPairApiImpl implements KeyPairApi {

    @Resource
    private KeyPairRedisRepository redisRepository;
    @Resource
    private KeyPairHandleService handleService;

    @Override
    public SecretKeyRespDTO getPublicKey() {
        String publicKey = redisRepository.getKeyPairKeyId();
        if (ObjectUtil.isNotEmpty(publicKey)) {
            return new SecretKeyRespDTO().setPublicKey(publicKey);
        }
        //密钥不存在时生成密钥
        handleService.initKeyPair();
        publicKey = redisRepository.getKeyPairKeyId();
        return new SecretKeyRespDTO().setPublicKey(publicKey);
    }


    @Override
    public SecretKeyRespDTO encrypt(SecretKeyReqDTO secretKeyReqDTO) {
        //只需要传明文,明文通过公钥转密文
        Optional.ofNullable(secretKeyReqDTO.getPassword()).orElseThrow(() -> new ServiceException(SECRET_ILLEGAL_PARAMS));
        KeyPair keyPair = this.getKeyPair();
        String encodePwd = null;
        try {
            Cipher cipher = Cipher.getInstance(RasUtils.Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            byte[] bytes = cipher.doFinal(secretKeyReqDTO.getPassword().getBytes());
            encodePwd = Base64.encode(bytes);
        } catch (Exception e) {
            log.error("encrypt faire", e);
            throw exception(ENCRYPT_FAIL);
        }
        return new SecretKeyRespDTO().setEncodePwd(encodePwd);
    }

    @Override
    public SecretKeyRespDTO decrypt(SecretKeyReqDTO secretKeyReqDTO) {
        KeyPair keyPair = this.getKeyPair();
        return decrypt(secretKeyReqDTO, keyPair);
    }

    @Override
    public SecretKeyRespDTO decrypt(SecretKeyReqDTO secretKeyReqDTO, KeyPair keyPair) {
        //只需要传密文,密文通过私钥转明文
        Optional.ofNullable(secretKeyReqDTO.getEncodePwd()).orElseThrow(() -> new ServiceException(SECRET_ILLEGAL_PARAMS));

        String password = null;
        try {
            Cipher cipher = Cipher.getInstance(RasUtils.Algorithm);
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            byte[] bytes = cipher.doFinal(Base64.decode(secretKeyReqDTO.getEncodePwd()));
            password = StrUtil.str(bytes, CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            log.error("decrypt faire", e);
            throw exception(ENCRYPT_FAIL);
        }
        return new SecretKeyRespDTO().setPassword(password);
    }

    @Override
    public SecretKeyRespDTO decryptByBusiness(SecretKeyReqDTO secretKeyReqDTO) {
        //必传公钥和密文
        Optional.ofNullable(secretKeyReqDTO.getPublicKey()).orElseThrow(() -> new ServiceException(SECRET_ILLEGAL_PARAMS));
        Optional.ofNullable(secretKeyReqDTO.getEncodePwd()).orElseThrow(() -> new ServiceException(SECRET_ILLEGAL_PARAMS));

        //获取当前使用公钥
        String publicKey = redisRepository.getKeyPairKeyId();
        if (ObjectUtil.notEqual(secretKeyReqDTO.getPublicKey(), publicKey)) {
            //当前使用公钥和参数公钥不等，说明参数公钥过期 设置使用公钥为过期公钥
            publicKey = secretKeyReqDTO.getPublicKey();
        }
        //根据公钥获取密钥对 解密
        KeyPair keyPair = getKeyPairByPublicKey(publicKey);
        return decrypt(secretKeyReqDTO, keyPair);
    }


    /**
     * 获取密钥对
     *
     * @return
     */
    private KeyPair getKeyPair() {
        String publicRedisKey = redisRepository.getKeyPairKeyId();
        // 不存在时初始化密钥对
        if (ObjectUtil.isEmpty(publicRedisKey)) {
            return handleService.initKeyPair();
        }

        KeyPairBo keyPairKeys = redisRepository.getKeyPairKeys(publicRedisKey);
        return RasUtils.convertKeyPairBoToKeyPair(keyPairKeys);
    }

    /**
     * 通过公钥获取公私钥对，主要用于获取过期的公私钥
     *
     * @param publicKey 公钥
     * @return 密钥对
     */
    private KeyPair getKeyPairByPublicKey(String publicKey) {
        KeyPairBo keyPairBo = redisRepository.getKeyPairKeys(publicKey);
        if (ObjectUtil.isNull(keyPairBo)) {
            throw exception(KeyPairBo_EXPIRE);
        }
        return RasUtils.convertKeyPairBoToKeyPair(keyPairBo);
    }

    /**
     * 通过公钥获取公私钥字符对
     *
     * @param publicKey 公钥
     * @return 密钥对
     */
    private KeyPairBo getKeyPairBoByPublicKey(String publicKey) {
        KeyPairBo keyPairBo = redisRepository.getKeyPairKeys(publicKey);
        if (ObjectUtil.isNull(keyPairBo)) {
            throw exception(KeyPairBo_EXPIRE);
        }
        return keyPairBo;
    }
}
