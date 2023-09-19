package com.hisun.kugga.duke.system.controller.app.ras;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.system.api.rsa.KeyPairApi;
import com.hisun.kugga.duke.system.api.rsa.dto.DecryptDTO;
import com.hisun.kugga.duke.system.api.rsa.dto.SecretKeyReqDTO;
import com.hisun.kugga.duke.system.api.rsa.dto.SecretKeyRespDTO;
import com.hisun.kugga.duke.system.controller.app.ras.vo.SecretReqVo;
import com.hisun.kugga.duke.system.controller.app.ras.vo.SecretRespVo;
import com.hisun.kugga.duke.system.rsa.RasService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/6 13:59
 */
@Api(tags = "A6-密钥")
@RestController
@RequestMapping("/system/secret")
@Validated
public class RasController {

    @Resource
    private KeyPairApi keyPairApi;
    @Resource
    private RasService rasService;


    @PostMapping("/testSecret")
    @ApiOperation("1.testSecret")
    public CommonResult<SecretKeyRespDTO> testSecret(@Valid @RequestBody SecretKeyReqDTO reqDTO) {
        long startTime = System.currentTimeMillis();
        SecretKeyRespDTO respDTO = null;
        if (ObjectUtil.equal("3", reqDTO.getFlag())) {
            respDTO = keyPairApi.getPublicKey();
        } else if (ObjectUtil.equal("4", reqDTO.getFlag())) {
            respDTO = keyPairApi.encrypt(reqDTO);
        } else if (ObjectUtil.equal("5", reqDTO.getFlag())) {
            respDTO = keyPairApi.decryptByBusiness(reqDTO);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(reqDTO.getFlag() + ".执行耗时：" + (endTime - startTime));
        return success(respDTO);
    }

    @PostMapping("/getSecretInfo")
    @ApiOperation("1.获取公钥和随机数")
    public CommonResult<SecretRespVo> getSecretInfo(@Valid @RequestBody SecretReqVo secretReqVo) {
        /*
        1、先要调用 getSecretInfo 获取公钥和random 111111值
        2、拿着公钥、random+密码  去加密 得到加密密文   111111admin123
        3、公钥、加密密文调用此接口 test-pwd-verify，
         */
        SecretRespVo secretRespVo = rasService.getPrefixSecretInfo(secretReqVo);
        return success(secretRespVo);
    }

    @PostMapping("/testPasswordAll")
    @ApiOperation("1.测试加密解密得到原密码的全程")
    public CommonResult<String> testPasswordAll(@Valid @RequestBody SecretReqVo secretReqVo) {

        // 1、获取公钥和随机数
        SecretRespVo secretRespVo = rasService.getPrefixSecretInfo(secretReqVo);
        //2、随机数密码加密 得到加密密文
        String newPwd = secretRespVo.getRandom() + secretReqVo.getPassword();
        SecretKeyRespDTO encrypt = keyPairApi.encrypt(new SecretKeyReqDTO().setPassword(newPwd));
        String encodePwd = encrypt.getEncodePwd();

        //3、根据密文和类型type解密  这里只测试LOGIN、PAY两个场景
        DecryptDTO decryptDTO = new DecryptDTO()
                .setPublicKey(secretRespVo.getPublicKey())
                .setPassword(encodePwd)
                .setType(secretReqVo.getType());
        String passwordDecrypt = rasService.decrypt(decryptDTO);

        return success(passwordDecrypt);
    }
}
