package com.hisun.kugga.duke.chat.service;

import com.hisun.kugga.duke.chat.controller.app.vo.roomrecord.RoomRecordPageReqVO;
import com.hisun.kugga.duke.chat.controller.app.vo.roomrecord.RoomRecordReadReqVO;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomRecordDO;
import com.hisun.kugga.duke.chat.dal.dataobject.SessionDO;
import com.hisun.kugga.duke.chat.dal.mysql.RoomRecordMapper;
import com.hisun.kugga.duke.chat.dal.mysql.SessionMapper;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Optional;

import static com.hisun.kugga.duke.chat.enums.ErrorCodeConstantsChat.RoomNotExists;

/**
 * 聊天记录 Service 实现类
 *
 * @author toi
 */
@Service
@Validated
public class RoomRecordServiceImpl implements RoomRecordService {

    @Resource
    private SessionMapper sessionMapper;

    @Resource
    private RoomRecordMapper roomRecordMapper;

    @Resource
    private SessionService sessionService;

    @Override
    public PageResult<RoomRecordDO> getRoomRecordPage(RoomRecordPageReqVO pageReqVO) {
        // 首先通过用户和会话id查询房间号，这样保证只能查询自己的聊天消息记录
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();

        SessionDO userSelfSession = sessionMapper.getUserSelfSession(pageReqVO.getRoomId(), loginUserId);
        Optional.ofNullable(userSelfSession).orElseThrow(() -> new ServiceException(RoomNotExists));

        PageResult<RoomRecordDO> roomRecordDOPageResult = roomRecordMapper.selectPage(userSelfSession.getRoomId(),
                pageReqVO.getRecordId(),
                pageReqVO);

        // 如果是第一页的情况，用户可能刚好收到消息，同时过来查，前端传值 pageNum = 1 && recordId != null
        // 剔除掉第一个重复的消息
        if (Integer.valueOf(1).equals(pageReqVO.getPageNo()) && pageReqVO.getRecordId() != null) {
            roomRecordDOPageResult.getList().remove(0);
        }

        return roomRecordDOPageResult;
    }

    public void read(RoomRecordReadReqVO reqVO) {
        if (reqVO.getUnread() != null &&
                reqVO.getUnread() > 0) {
            Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
            SessionDO userSelfSession = sessionMapper.getUserSelfSession(reqVO.getRoomId(), loginUserId);
            Optional.ofNullable(userSelfSession).orElseThrow(() -> new ServiceException(RoomNotExists));
            sessionService.read(userSelfSession.getId(), loginUserId);
        }
    }

}
