package com.hisun.kugga.duke.system.rsa;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.hisun.kugga.duke.enums.SecretTypeEnum;
import com.hisun.kugga.duke.system.api.rsa.KeyPairApi;
import com.hisun.kugga.duke.system.api.rsa.dto.DecryptDTO;
import com.hisun.kugga.duke.system.api.rsa.dto.SecretKeyReqDTO;
import com.hisun.kugga.duke.system.api.rsa.dto.SecretKeyRespDTO;
import com.hisun.kugga.duke.system.controller.app.ras.vo.SecretReqVo;
import com.hisun.kugga.duke.system.controller.app.ras.vo.SecretRespVo;
import com.hisun.kugga.duke.system.rsa.redis.SecretRedisRepository;
import com.hisun.kugga.framework.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/6 17:13
 */
@Slf4j
@Service
public class RasServiceImpl implements RasService/*, PasswordDecryptApi*/ {

    private static final Integer RANDOM_SIZE = 10;
    @Resource
    private KeyPairApi keyPairApi;

    @Resource
    private SecretRedisRepository redisRepository;

    @Override
    public SecretRespVo getPrefixSecretInfo(SecretReqVo secretReqVo) {
        SecretKeyRespDTO keyPairBo = keyPairApi.getPublicKey();

        String random = RandomUtil.randomNumbers(RANDOM_SIZE);

        SecretRespVo respVo = new SecretRespVo();
        respVo.setPublicKey(keyPairBo.getPublicKey());
        respVo.setRandom(random);
        redisRepository.setRandom(secretReqVo.getType(), random, random);

        if (ObjectUtil.equal(SecretTypeEnum.LOGIN_UPDATE, secretReqVo.getType()) ||
                ObjectUtil.equal(SecretTypeEnum.PAY_UPDATE, secretReqVo.getType())) {
            //如果是修改的场景，设置两个随机数 ，第一个随机数是老密码的随机数，第二个是新密码的
            String random2 = RandomUtil.randomNumbers(RANDOM_SIZE);
            respVo.setRandom2(random2);
            redisRepository.setRandom(secretReqVo.getType(), random2, random2);
        }
        return respVo;
    }

    @Override
    public String decrypt(DecryptDTO decryptDTO) {
        Optional.ofNullable(decryptDTO.getPublicKey()).orElseThrow(() -> new ServiceException(ILLEGAL_PARAMS, "publicKey"));
        Optional.ofNullable(decryptDTO.getPassword()).orElseThrow(() -> new ServiceException(ILLEGAL_PARAMS, "password"));
        Optional.ofNullable(decryptDTO.getType()).orElseThrow(() -> new ServiceException(ILLEGAL_PARAMS, "type"));

        SecretKeyReqDTO reqDTO = new SecretKeyReqDTO()
                .setPublicKey(decryptDTO.getPublicKey())
                .setEncodePwd(decryptDTO.getPassword());
        SecretKeyRespDTO respDTO = keyPairApi.decryptByBusiness(reqDTO);

        //解密后的密码是由 RANDOM_SIZE 10位的随机数+密码组成
        String password = respDTO.getPassword();
        if (ObjectUtil.isNotEmpty(password) && password.length() <= RANDOM_SIZE) {
            throw exception(PASSWORD_VERIFY_ERROR);
        }
        String random = password.substring(0, RANDOM_SIZE);
        String redisRandom = redisRepository.getRandom(decryptDTO.getType(), random);
        if (ObjectUtil.notEqual(random, redisRandom)) {
            throw exception(RANDOM_ERROR);
        }
        //lua删除，防止并发解密
        redisRepository.deleteLuaRandom(decryptDTO.getType(), random, redisRandom);

        return password.substring(RANDOM_SIZE);
    }
}
