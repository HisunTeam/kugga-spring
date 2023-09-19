package com.hisun.kugga.duke.innercall;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.utils.RedisUtils;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.util.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.function.Predicate;

/**
 * @author: zhou_xiong
 */
@Slf4j
@Component
public class InnerCallHelper {
    private static final String PREFIX = "innercall:";
    @Resource
    private RedisUtils redisUtils;

    public String genCert(Object bizValue) {
        String uuid = IdUtil.simpleUUID();
        // 随机的10~15分钟失效时间，避免大量key同一时间失效
        int time = RandomUtil.randomInt(600, 900);
        bizValue = ObjectUtil.isNull(bizValue) ? "1" : bizValue;
        redisUtils.setForTimeSec(PREFIX + uuid, bizValue, time);
        return uuid;
    }

    public <RedisValue> void verifyCert(InnerCallReqDTO reqDTO, Class<RedisValue> valueClazz, Predicate<RedisValue> predicate) {
        String key = PREFIX + reqDTO.getUuid();
        String jsonObj = redisUtils.get(key);
        if (StrUtil.isEmpty(jsonObj)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.ILLEGAL_OPERATOR);
        }
        RedisValue redisValue = JsonUtils.parseObject(jsonObj, valueClazz);
        if (!predicate.test(redisValue)) {
            log.error("凭证校验失败，uuid：{}", reqDTO.getUuid());
            ServiceException.throwServiceException(BusinessErrorCodeConstants.ILLEGAL_OPERATOR);
        } else {
            redisUtils.delete(key);
        }
    }

    public <ReqBody extends InnerCallReqDTO, RspBody> RspBody post(String url, ReqBody reqBody, Class<RspBody> rspBodyClazz) {
        String body = HttpRequest.post(url).body(JSONUtil.toJsonStr(reqBody)).execute().body();
        CommonResult<RspBody> commonResult = JSONUtil.toBean(body, new TypeReference<CommonResult<RspBody>>() {
        }, false);
        if (!commonResult.getCode().equals(GlobalErrorCodeConstants.SUCCESS.getCode())) {
            log.error("请求url:[{}]失败，请求参数：{}，返回结果为：{}", url, reqBody, commonResult);
            throw new ServiceException(commonResult.getCode(), commonResult.getMsg());
        }
        return commonResult.getData();
    }
}
