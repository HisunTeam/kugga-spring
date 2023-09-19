package com.hisun.kugga.server.module;

import com.hisun.kugga.framework.common.api.permission.BasePermissionApi;
import org.springframework.stereotype.Service;

/**
 * 用于权限判断
 */
@Service
public class PermissionApiImpl implements BasePermissionApi {

    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
        return Boolean.TRUE;
    }

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        return Boolean.TRUE;
    }

}
