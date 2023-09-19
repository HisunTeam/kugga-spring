package com.hisun.kugga.module.system.api.tenant;

import com.hisun.kugga.module.system.service.tenant.TenantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 多租户的 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class TenantApiImpl implements TenantApi {

    @Resource
    private TenantService tenantService;

    @Override
    public List<Long> getTenantIds() {
        return tenantService.getTenantIds();
    }

    @Override
    public void validTenant(Long id) {
        tenantService.validTenant(id);
    }

}
