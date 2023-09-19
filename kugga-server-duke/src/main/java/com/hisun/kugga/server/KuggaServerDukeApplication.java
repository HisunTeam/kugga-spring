package com.hisun.kugga.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 项目的启动类
 * <p>
 * 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
 *
 * @author 芋道源码
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${kugga.info.base-package}
@SpringBootApplication(scanBasePackages = {"${kugga.info.base-package}.server", "${kugga.info.base-package}.module", "${kugga.info.base-package}.duke"
        , "${kugga.info.base-package}.framework.utils"})
public class KuggaServerDukeApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(KuggaServerDukeApplication.class, args);
        printApiPath(run);
    }

    /**
     * 打印swagger 文档地址 （启动完成）
     *
     * @param application
     */
    private static void printApiPath(ConfigurableApplicationContext application) {
        Environment env = application.getEnvironment();
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            String port = env.getProperty("server.port");
            String path = env.getProperty("server.servlet.context-path");
            if (path == null || "".equals(path)) {
                path = "";
            }
            System.out.println("\n----------------------------------------------------------\n\t" +
                    "Application Kugga Duke is running! Access URLs:\n\t" +
                    "Swagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n\t" +
                    "Swagger文档: \thttp://localhost:" + port + path + "/doc.html\n" +
                    "----------------------------------------------------------");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
