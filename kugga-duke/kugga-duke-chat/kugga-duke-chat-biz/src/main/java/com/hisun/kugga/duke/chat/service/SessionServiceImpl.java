package com.hisun.kugga.duke.chat.service;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.chat.controller.app.vo.session.SessionPageReqVO;
import com.hisun.kugga.duke.chat.controller.app.vo.session.SessionRespVO;
import com.hisun.kugga.duke.chat.dal.dataobject.SessionDO;
import com.hisun.kugga.duke.chat.dal.mysql.SessionMapper;
import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import com.hisun.kugga.duke.chat.socket.utils.MessageUtils;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 聊天会话列 Service 实现类
 *
 * @author toi
 */
@Service
@Validated
public class SessionServiceImpl implements SessionService {

    @Resource
    private SessionMapper sessionMapper;

    @Override
    public PageResult<SessionRespVO> getSessionPage(SessionPageReqVO pageReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        Page<SessionDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        IPage<SessionRespVO> sessionRespVOIPage = sessionMapper.selectPageWithUserName(page, loginUserId);

        PageResult<SessionRespVO> pageResult = new PageResult<>(sessionRespVOIPage.getRecords(), sessionRespVOIPage.getTotal());
        pageResult.getList().forEach(i -> {
            i.setData(MessageUtils.convertMessage(i.getMessageType(), StrUtil.toString(i.getData())));
            i.setIsExpire(isExpire(i));
        });
        return pageResult;
    }

    private boolean isExpire(SessionRespVO vo) {
        if (vo.getPayType() == PayTypeEnum.FREE_CHAT) {
            return false;
        }
        LocalDateTime expire = LocalDateTimeUtil.of(vo.getExpireTime());
        return LocalDateTime.now().isAfter(expire);
    }

    @Override
    public void read(Long id, Long userId) {
        int read = sessionMapper.read(id, userId);
    }

    @Override
    public Boolean messageRetDot(Long userId) {
        Integer integer = sessionMapper.redDot(userId);
        return Integer.valueOf(1).equals(Optional.ofNullable(integer).orElse(0));
    }

}
