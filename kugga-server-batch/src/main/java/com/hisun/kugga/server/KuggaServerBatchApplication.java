package com.hisun.kugga.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类
 * <p>
 * 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
 *
 * @author 芋道源码
 */
@SuppressWarnings("SpringComponentScan")
@SpringBootApplication(scanBasePackages = {"${kugga.info.base-package}.server", "${kugga.info.base-package}.module", "${kugga.info.base-package}.duke", "${kugga.info.base-package}.framework.utils"})
public class KuggaServerBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuggaServerBatchApplication.class, args);
    }
}
