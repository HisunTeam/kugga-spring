package com.hisun.kugga.duke.forum.api;

import com.hisun.kugga.duke.forum.service.MessageService;
import com.hisun.kugga.duke.league.api.ForumApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/20 17:42
 */
@Service
public class ForumApiImpl implements ForumApi {

    @Resource
    private MessageService messageService;

    @Override
    public Long unreadNum() {
        return messageService.unreadNum();
    }
}
