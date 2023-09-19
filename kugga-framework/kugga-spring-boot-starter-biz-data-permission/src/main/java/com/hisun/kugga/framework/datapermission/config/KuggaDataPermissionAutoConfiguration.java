package com.hisun.kugga.framework.datapermission.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.hisun.kugga.framework.datapermission.core.aop.DataPermissionAnnotationAdvisor;
import com.hisun.kugga.framework.datapermission.core.db.DataPermissionDatabaseInterceptor;
import com.hisun.kugga.framework.datapermission.core.rule.DataPermissionRule;
import com.hisun.kugga.framework.datapermission.core.rule.DataPermissionRuleFactory;
import com.hisun.kugga.framework.datapermission.core.rule.DataPermissionRuleFactoryImpl;
import com.hisun.kugga.framework.mybatis.core.util.MyBatisUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 数据权限的自动配置类
 *
 * @author 芋道源码
 */
@Configuration
public class KuggaDataPermissionAutoConfiguration {

    @Bean
    public DataPermissionRuleFactory dataPermissionRuleFactory(List<DataPermissionRule> rules) {
        return new DataPermissionRuleFactoryImpl(rules);
    }

    @Bean
    public DataPermissionDatabaseInterceptor dataPermissionDatabaseInterceptor(MybatisPlusInterceptor interceptor,
                                                                               List<DataPermissionRule> rules) {
        // 创建 DataPermissionDatabaseInterceptor 拦截器
        DataPermissionRuleFactory ruleFactory = dataPermissionRuleFactory(rules);
        DataPermissionDatabaseInterceptor inner = new DataPermissionDatabaseInterceptor(ruleFactory);
        // 添加到 interceptor 中
        // 需要加在首个，主要是为了在分页插件前面。这个是 MyBatis Plus 的规定
        MyBatisUtils.addInterceptor(interceptor, inner, 0);
        return inner;
    }

    @Bean
    public DataPermissionAnnotationAdvisor dataPermissionAnnotationAdvisor() {
        return new DataPermissionAnnotationAdvisor();
    }

}
