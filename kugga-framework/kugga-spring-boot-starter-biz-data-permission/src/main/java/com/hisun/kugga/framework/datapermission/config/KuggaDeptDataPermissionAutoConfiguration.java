package com.hisun.kugga.framework.datapermission.config;

import com.hisun.kugga.framework.datapermission.core.rule.dept.DeptDataPermissionRule;
import com.hisun.kugga.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import com.hisun.kugga.framework.security.core.LoginUser;
import com.hisun.kugga.module.system.api.permission.PermissionApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 基于部门的数据权限 AutoConfiguration
 *
 * @author 芋道源码
 */
@Configuration
@ConditionalOnClass(LoginUser.class)
@ConditionalOnBean(value = {PermissionApi.class, DeptDataPermissionRuleCustomizer.class})
public class KuggaDeptDataPermissionAutoConfiguration {

    @Bean
    public DeptDataPermissionRule deptDataPermissionRule(PermissionApi permissionApi,
                                                         List<DeptDataPermissionRuleCustomizer> customizers) {
        // 创建 DeptDataPermissionRule 对象
        DeptDataPermissionRule rule = new DeptDataPermissionRule(permissionApi);
        // 补全表配置
        customizers.forEach(customizer -> customizer.customize(rule));
        return rule;
    }

}
