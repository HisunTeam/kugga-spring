package com.hisun.kugga.duke.system.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.system.api.shorturl.dto.CreateShortUrlReqDTO;
import com.hisun.kugga.duke.system.api.shorturl.dto.CreateShortUrlRspDTO;
import com.hisun.kugga.duke.system.api.shorturl.dto.QueryShortUrlRspDTO;
import com.hisun.kugga.duke.system.constants.ShortUrlConstants;
import com.hisun.kugga.duke.system.controller.app.shorturl.vo.ShortCodeRespVO;
import com.hisun.kugga.duke.system.dal.dataobject.ShortUrlDO;
import com.hisun.kugga.duke.system.dal.mysql.ShortUrlMapper;
import com.hisun.kugga.duke.system.enums.EffectiveUnitEnum;
import com.hisun.kugga.duke.system.enums.ShortUrlTypeEnum;
import com.hisun.kugga.duke.system.service.ShortUrlService;
import com.hisun.kugga.duke.system.utils.ShortUrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @author zuocheng
 */
@Service
@Slf4j
public class ShortUrlServiceImpl implements ShortUrlService {
    @Resource
    private ShortUrlMapper shortUrlMapper;

    @Override
    public CreateShortUrlRspDTO createShortUrl(CreateShortUrlReqDTO reqDTO) {
        //获取域名及URI
        URL url = null;
        try {
            url = new URL(reqDTO.getUrl());
        } catch (MalformedURLException e) {
            log.info("地址[{}]错误,无法生成连接", reqDTO.getUrl());
            throw exception(BusinessErrorCodeConstants.SHORT_URL_CREATE_ERR);
        }

        String domain = url.getProtocol() + "://" + url.getAuthority();
        String uri = url.getFile();

        CreateShortUrlRspDTO rspDTO = new CreateShortUrlRspDTO();
        //检查当前长链是否已绑定了,有效的短链,存在有效的短链则直接返回
        ShortUrlDO longExist = shortUrlMapper.selectLongEffect(domain, uri);

        if (ObjectUtil.isNotNull(longExist)) {
            log.info("当前链接[{}],存在短链信息,直接各应结果,短链信息为[{}]", reqDTO.getUrl() + uri, longExist);
            rspDTO = Convert.convert(CreateShortUrlRspDTO.class, longExist);
            //获取短链前缀
            String prefix = getUriPrefix(ShortUrlTypeEnum.getByCode(longExist.getLinkType()));
            rspDTO.setShortUrl(domain + prefix + longExist.getShotCode());
            return rspDTO;
        }

        //无短链信息时，生成短连接
        String[] shortCodes = ShortUrlUtils.shortUrl(reqDTO.getUrl() + uri);
        String shortCode = "";
        //检查生成的短链是否已经被占用/绑定,未被绑定时,则取当前code绑定长链接
        for (String str : shortCodes) {
            if (checkShortCodeExist(str)) {
                shortCode = str;
                break;
            }
        }

        //将短链信息保存到,数据库
        ShortUrlDO insert = new ShortUrlDO();
        insert.setCreateTime(LocalDateTime.now().withNano(0));
        insert.setUpdateTime(LocalDateTime.now().withNano(0));
        insert.setUrl(domain);
        insert.setUri(uri);
        insert.setShotCode(shortCode);
        insert.setExpireFlag(false);
        if (ObjectUtil.isNotNull(reqDTO.getType())) {
            insert.setLinkType(reqDTO.getType().getCode());
        }
        if (ObjectUtil.isNotNull(reqDTO.getEffectiveUnit()) && ObjectUtil.isNotNull(reqDTO.getEffectivePeriod())) {
            //生成短链超时时间
            insert.setExpireTime(calculationEffectiveTime(reqDTO.getEffectiveUnit(), reqDTO.getEffectivePeriod()));
        } else {
            //默认永久不超时
            insert.setExpireTime(LocalDateTime.of(9999, 12, 31, 23, 59, 59));
        }
        int res = shortUrlMapper.insert(insert);
        if (res != 1) {
            throw exception(BusinessErrorCodeConstants.SHORT_URL_WRITE_DATA_ERROR);
        }

        rspDTO = Convert.convert(CreateShortUrlRspDTO.class, insert);

        //获取短链前缀
        String prefix = getUriPrefix(reqDTO.getType());

        rspDTO.setShortUrl(insert.getUrl() + prefix + insert.getShotCode());
        return rspDTO;
    }

    @Override
    public String getLongUrl(String shortCode) {
        ShortUrlDO shortUrl = getShortUrlByCode(shortCode);
        //访问次数+1
        recordAccess(shortUrl);
        return shortUrl.getUrl() + shortUrl.getUri();
    }

    @Override
    public QueryShortUrlRspDTO queryShortUrlById(Long id) {
        QueryShortUrlRspDTO rspDTO = new QueryShortUrlRspDTO();
        ShortUrlDO shortUrl = shortUrlMapper.selectById(id);

        //检查链接信息
        checkShortUrl(shortUrl);

        rspDTO = Convert.convert(QueryShortUrlRspDTO.class, shortUrl);

        String prefix = getUriPrefix(ShortUrlTypeEnum.getByCode(shortUrl.getLinkType()));

        rspDTO.setShortUrl(shortUrl.getUrl() + prefix + shortUrl.getShotCode());
        return rspDTO;
    }


    @Override
    public ShortCodeRespVO queryShortUrlByCode(String shortCode) {
        ShortUrlDO shortUrl = getShortUrlByCode(shortCode);
        //访问次数+1
        recordAccess(shortUrl);

        return new ShortCodeRespVO().setUuid(shortUrl.getUri().substring(1));
    }

    /**
     * 获取短链URI前缀
     *
     * @return
     */
    private String getUriPrefix(ShortUrlTypeEnum TypeEnum) {
        String prefix = ShortUrlConstants.SHORT_URL_PREFIX;
        if (ShortUrlTypeEnum.TYPE_G.equals(TypeEnum)) {
            prefix = ShortUrlConstants.G_SHORT_URL_PREFIX;
        }
        return prefix;
    }

    /**
     * 根据短链编号查询短链信息
     *
     * @param shortCode
     * @return
     */
    private ShortUrlDO getShortUrlByCode(String shortCode) {
        ShortUrlDO shortUrl = shortUrlMapper.selectShortUrl(shortCode);
        //检查链接信息
        checkShortUrl(shortUrl);
        return shortUrl;
    }

    /**
     * 检查短链数据
     *
     * @param shortUrl
     */
    private void checkShortUrl(ShortUrlDO shortUrl) {
        if (ObjectUtil.isNull(shortUrl)) {
            log.info("链接信息不存在");
            throw exception(BusinessErrorCodeConstants.SHORT_URL_NOT_EXIST);
        }

        if (shortUrl.getExpireFlag()) {
            log.info("链接[{}]已失效", shortUrl);
            throw exception(BusinessErrorCodeConstants.SHORT_URL_INVALID);
        }

        LocalDateTime nowTime = LocalDateTime.now().withNano(0);
        if (ObjectUtil.isNotNull(shortUrl.getExpireTime()) && nowTime.isAfter(shortUrl.getExpireTime())) {
            updateExpire(shortUrl.getId());
            log.info("链接[{}]已失效,链接有效时间[{}-{}],当前时间[{}]", shortUrl.getShotCode(), shortUrl.getCreateTime(), shortUrl.getExpireTime(), nowTime);
            throw exception(BusinessErrorCodeConstants.SHORT_URL_INVALID);
        }
    }

    /**
     * 更新成失效
     *
     * @param id
     */
    private void updateExpire(Long id) {
        ShortUrlDO update = new ShortUrlDO();
        update.setId(id);
        update.setUpdateTime(LocalDateTime.now().withNano(0));
        update.setExpireFlag(true);
        //记录访问次数，忽视报错
        shortUrlMapper.updateById(update);
    }

    /**
     * 记录一次访问
     *
     * @param shortUrlDO
     */
    private void recordAccess(ShortUrlDO shortUrlDO) {
        ShortUrlDO update = new ShortUrlDO();
        update.setId(shortUrlDO.getId());
        update.setTotalClickCount(shortUrlDO.getTotalClickCount() + 1);
        //记录访问次数，忽视报错
        //todo 取消更新次数
//        shortUrlMapper.updateById(update);
    }

    /**
     * 检查短连是否存在,当短链已经存在时
     *
     * @param shortCode
     * @return
     */
    private boolean checkShortCodeExist(String shortCode) {
        LocalDateTime nowTime = LocalDateTime.now().withNano(0);
        ShortUrlDO queryDO = new ShortUrlDO();
        queryDO.setExpireFlag(false);
        queryDO.setCreateTime(nowTime);
        queryDO.setExpireTime(nowTime);
        queryDO.setShotCode(shortCode);
        Long count = shortUrlMapper.selectEffect(queryDO);
        if (count == 0) {
            return true;
        }
        return false;
    }

    /**
     * 计算出失效时间(未传时间单位时,默认无失效时间)
     *
     * @return
     */
    private LocalDateTime calculationEffectiveTime(EffectiveUnitEnum effectiveUnit, int effectivePeriod) {
        LocalDateTime effectiveTime = LocalDateTime.now().withNano(0);
        switch (effectiveUnit) {
            case s:
                effectiveTime = effectiveTime.plusSeconds(effectivePeriod);
                break;
            case m:
                effectiveTime = effectiveTime.plusMinutes(effectivePeriod);
                break;
            case H:
                effectiveTime = effectiveTime.plusHours(effectivePeriod);
                break;
            case D:
                effectiveTime = effectiveTime.plusDays(effectivePeriod);
                break;
            case W:
                effectiveTime = effectiveTime.plusWeeks(effectivePeriod);
                break;
            case M:
                effectiveTime = effectiveTime.plusMonths(effectivePeriod);
                break;
            case Y:
                effectiveTime = effectiveTime.plusYears(effectivePeriod);
                break;
            default:
                effectiveTime = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
        }
        return effectiveTime;
    }
}
