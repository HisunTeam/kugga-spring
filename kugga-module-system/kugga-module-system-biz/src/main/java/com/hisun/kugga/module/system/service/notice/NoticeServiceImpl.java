package com.hisun.kugga.module.system.service.notice;

import com.google.common.annotations.VisibleForTesting;
import com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.module.system.controller.admin.notice.vo.NoticeCreateReqVO;
import com.hisun.kugga.module.system.controller.admin.notice.vo.NoticePageReqVO;
import com.hisun.kugga.module.system.controller.admin.notice.vo.NoticeUpdateReqVO;
import com.hisun.kugga.module.system.convert.notice.NoticeConvert;
import com.hisun.kugga.module.system.dal.dataobject.notice.NoticeDO;
import com.hisun.kugga.module.system.dal.mysql.notice.NoticeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hisun.kugga.module.system.enums.ErrorCodeConstants.NOTICE_NOT_FOUND;

/**
 * 通知公告 Service 实现类
 *
 * @author 芋道源码
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public Long createNotice(NoticeCreateReqVO reqVO) {
        NoticeDO notice = NoticeConvert.INSTANCE.convert(reqVO);
        noticeMapper.insert(notice);
        return notice.getId();
    }

    @Override
    public void updateNotice(NoticeUpdateReqVO reqVO) {
        // 校验是否存在
        this.checkNoticeExists(reqVO.getId());
        // 更新通知公告
        NoticeDO updateObj = NoticeConvert.INSTANCE.convert(reqVO);
        noticeMapper.updateById(updateObj);
    }

    @Override
    public void deleteNotice(Long id) {
        // 校验是否存在
        this.checkNoticeExists(id);
        // 删除通知公告
        noticeMapper.deleteById(id);
    }

    @Override
    public PageResult<NoticeDO> pageNotices(NoticePageReqVO reqVO) {
        return noticeMapper.selectPage(reqVO);
    }

    @Override
    public NoticeDO getNotice(Long id) {
        return noticeMapper.selectById(id);
    }

    @VisibleForTesting
    public void checkNoticeExists(Long id) {
        if (id == null) {
            return;
        }
        NoticeDO notice = noticeMapper.selectById(id);
        if (notice == null) {
            throw ServiceExceptionUtil.exception(NOTICE_NOT_FOUND);
        }
    }

}
