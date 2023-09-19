package com.hisun.kugga.duke.league.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueDO;
import com.hisun.kugga.duke.league.properties.ForumProperties;
import com.hisun.kugga.duke.league.service.ForumService;
import com.hisun.kugga.duke.league.service.LeagueMemberService;
import com.hisun.kugga.duke.league.service.LeagueService;
import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
import com.hisun.kugga.duke.user.api.oauth2.dto.UserInfoRespDTO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: zhou_xiong
 */
@Service
@Slf4j
public class ForumServiceImpl implements ForumService {
    private static final int OK = 200;

    @Resource
    private ForumProperties forumProperties;
    @Resource
    private LeagueService leagueService;
    @Resource
    private LeagueMemberService leagueMemberService;
    @Resource
    private DukeUserApi dukeUserApi;

    /**
     * {
     * "clientId": "string",
     * "isUserAdmin": true,
     * "organizationId": 0,
     * "organizationName": "string",
     * "partnerNickName": "string",
     * "partnerUserId": 0,
     * "partnerUsername": "string",
     * "secret": "string"
     * }
     *
     * @param leagueId
     * @return
     */
    @Override
    public String forumIndex(Long leagueId) {
        // 当前登录用户
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        UserInfoRespDTO user = dukeUserApi.getUserById(userId);
        // 查询公会信息
        LeagueDO leagueDO = leagueService.getById(leagueId);
        // 查询用户是否是该公会的管理员
        boolean adminFlag = leagueMemberService.isAdmin(userId, leagueId);
        // 生成100万以内随机正整数盐值
        int code = RandomUtil.randomInt(1000000);
        // 请求KuggaMax登录
        KuggaMaxLoginReq loginData = KuggaMaxLoginReq.builder().clientId(forumProperties.getClientId())
                .secret(SecureUtil.md5(forumProperties.getSecret() + code))
                .code(code)
                .isUserAdmin(adminFlag)
                .organizationId(leagueId)
                .organizationName(leagueDO.getLeagueName())
                .partnerUserId(user.getId())
                .partnerNickName(user.getFirstName() + user.getLastName())
                .partnerUsername(user.getUsername()).build();
        String body = HttpRequest.post(forumProperties.getLoginUrl())
                .body(JSONUtil.toJsonStr(loginData))
                .execute().body();
        KuggaMaxLoginRsp kuggaMaxLoginRsp = JSONUtil.toBean(body, KuggaMaxLoginRsp.class);
        if (kuggaMaxLoginRsp.getCode() != OK) {
            log.error("登录KuggaMax失败，username:{},返回的内容:{}", user.getUsername(), kuggaMaxLoginRsp);
            throw new ServiceException(kuggaMaxLoginRsp.getCode(), kuggaMaxLoginRsp.getMsg());
        }
        return kuggaMaxLoginRsp.getData().getUrl();
    }

    @Data
    @Builder
    public static class KuggaMaxLoginReq {
        private String clientId;
        private String secret;
        private Integer code;
        private Boolean isUserAdmin;
        private Long organizationId;
        private String organizationName;
        private Long partnerUserId;
        private String partnerUsername;
        private String partnerNickName;
    }

    @Data
    public class KuggaMaxLoginRsp {
        private Integer code;
        private String msg;
        private String requestId;
        private String status;
        private BizData data;

        @Data
        public class BizData {
            private String url;
        }
    }
}
