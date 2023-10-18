# 一、 KuggaDuke是什么

## 1、简介

KuggaDuke作为一个基于WEB3.0理念打造的去中心化社交平台，其核心在于支持用户自主创建和加入代表个人兴趣或技能的公会组织。在KuggaDuke中，每个公会都是一个自治的群落，公会成员可以根据兴趣制定属于自己的管理规则和规范。

用户可以在KuggaDuke中发挥所长，通过加入不同公会来实现自我价值。公会内部实现资源和收益的平等分配，用户通过在公会内部互动贡献来获得收益。同时，公会也可以通过认证开启更多高级功能。

相较传统中心化社交平台，KuggaDuke更加注重公平和用户主权。加入KuggaDuke的公会组织，用户可以享有开放透明的社交环境、平等自治的组织形式、丰厚的互动收益。

## 2、设计思路及优势

KuggaDuke的设计思路是通过兴趣社群聚集志同道合、能力相近的用户，提供一个让他们自由交流的内容平台。基于WEB3.0理念，KuggaDuke采用去中心化社交模式，支持公会内部实现高度自治和自主管理。在KuggaDuke中，用户可以无限制加入和创建代表兴趣爱好的公会组织。公会成员可以制定协作规则，平等参与公会事务。平台仅提供基础支持，不对公会内部进行干预。

这种去中心化运营的模式，可以给予公会和用户更大的自主权。公会可以根据兴趣设定治理规则和规范，实现资源分配的公平和合理。用户也可以自由选择感兴趣的公会进行交流。同时，兴趣社群的聚集也自然形成高质量的用户粘性。公会内产出的内容更加贴合成员兴趣，平台活跃度得到有效提升。公会成员也更乐于在感兴趣的领域做出贡献。

## 3、应用范围

KuggaDuke作为一个依托兴趣爱好和能力的公会社交平台，其应用范围集中在联结志同道合的用户群体，支持他们在共享的兴趣和技能领域进行深度交流和价值实现。

KuggaDuke可广泛应用于:

- 兴趣社群 - 用户基于爱好建立专属群落，获得属于自己的交流空间。
- 个人社交 - 用户搭建个人中心，发布动态和推荐展示自我。
- 社群商业 - 用户通过公会互动获得丰厚收益和认可。
- 去中心化自治 - 公会实现高度自治和自主管理。
- WEB3.0社交 - 平台打造开放透明的用户管理和价值传输。

KuggaDuke通过支持兴趣社群、鼓励个人社交、开启社群商业、打造去中心化自治、实现WEB3.0社交等应用，致力于构建一个公平高效的社交价值网络。

# 二、 能力有什么

- 前端采用 [vue-element-admin](https://github.com/PanJiaChen/vue-element-admin) ，正在支持 Vue 3 + ElementUI Plus 最新方案。
- 后端采用 Spring Boot、MySQL + MyBatis Plus、Redis + Redisson。
- 数据库可使用 MySQL、Oracle、PostgreSQL、SQL Server、MariaDB、国产达梦 DM、TiDB 等
- 权限认证使用 Spring Security & Token & Redis，支持多终端、多种用户的认证系统。
- 支持加载动态权限菜单，按钮级别权限控制，本地缓存提升性能。
- 高效率开发，使用代码生成器可以一键生成前后端代码 + 单元测试 + Swagger 接口文档 + Validator 参数校验。
- 集成阿里云、腾讯云、云片等短信渠道，集成 MinIO、阿里云、腾讯云、七牛云等云存储服务。

### 系统功能

| 功能       | 描述                                                     |
| ---------- | -------------------------------------------------------- |
| 用户管理   | 用户是系统操作者，该功能主要完成系统用户配置             |
| 角色管理   | 角色菜单权限分配、设置角色按机构进行数据范围权限划分     |
| 菜单管理   | 配置系统菜单、操作权限、按钮权限标识等，本地缓存提供性能 |
| 字典管理   | 对系统中经常使用的一些较为固定的数据进行维护             |
| 操作日志   | 系统正常操作日志记录和查询，集成 Swagger 生成日志内容    |
| 登录日志   | 系统登录日志记录查询，包含登录异常                       |
| 错误码管理 | 系统所有错误码的管理，可在线修改错误提示，无需重启服务   |

### 基础设施

| 功能       | 描述                                                         |
| ---------- | ------------------------------------------------------------ |
| 代码生成   | 前后端代码的生成（Java、Vue、SQL、单元测试），支持 CRUD 下载 |
| 系统接口   | 基于 Swagger 自动生成相关的 RESTful API 接口文档             |
| 数据库文档 | 基于 Screw 自动生成数据库文档，支持导出 Word、HTML、MD 格式  |
| 配置管理   | 对系统动态配置常用参数，支持 SpringBoot 加载                 |
| 定时任务   | 在线（添加、修改、删除)任务调度包含执行结果日志              |
| 文件服务   | 支持将文件存储到 S3（MinIO、阿里云、腾讯云、七牛云）、本地、FTP、数据库等 |
| API 日志   | 包括 RESTful API 访问日志、异常日志两部分，方便排查 API 相关的问题 |
| MySQL 监控 | 监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈  |
| Redis 监控 | 监控 Redis 数据库的使用情况，使用的 Redis Key 管理           |
| 消息队列   | 基于 Redis 实现消息队列，Stream 提供集群消费，Pub/Sub 提供广播消费 |
| Java 监控  | 基于 Spring Boot Admin 实现 Java 应用的监控                  |
| 链路追踪   | 接入 SkyWalking 组件，实现链路追踪                           |
| 日志中心   | 接入 SkyWalking 组件，实现日志中心                           |
| 分布式锁   | 基于 Redis 实现分布式锁，满足并发场景                        |
| 幂等组件   | 基于 Redis 实现幂等组件，解决重复请求问题                    |
| 服务保障   | 基于 Resilience4j 实现服务的稳定性，包括限流、熔断等功能     |
| 日志服务   | 轻量级日志中心，查看远程服务器的日志                         |
| 单元测试   | 基于 JUnit + Mockito 实现单元测试，保证功能的正确性、代码的质量等 |

# 三、系统架构

## 1、技术架构图

![image-20230925150651965](img/kugga-architecture.png)

## 2、技术栈

| 项目                  | 说明                         |
| --------------------- | ---------------------------- |
| `kugga-dependencies`  | Maven 依赖版本管理           |
| `kugga-framework`     | Java 框架拓展                |
| `kugga-server`        | 管理后台 + 用户 APP 的服务端 |
| `kugga-admin-ui`      | 管理后台的 UI 界面           |
| `kugga-user-ui`       | 用户 APP 的 UI 界面          |
| `kugga-module-system` | 系统功能的 Module 模块       |
| `kugga-module-member` | 会员中心的 Module 模块       |
| `kugga-module-infra`  | 基础设施的 Module 模块       |
| `kugga-module-tool`   | 研发工具的 Module 模块       |
| `kugga-module-bpm`    | 工作流程的 Module 模块       |
| `kugga-module-pay`    | 支付系统的 Module 模块       |

### 后端

| 框架                                                         | 说明                  | 版本     | 学习指南                                                     |
| ------------------------------------------------------------ | --------------------- | -------- | ------------------------------------------------------------ |
| [Spring Boot](https://spring.io/projects/spring-boot)        | 应用开发框架          | 2.6.8    | [文档](https://github.com/YunaiV/SpringBoot-Labs)            |
| [MySQL](https://www.mysql.com/cn/)                           | 数据库服务器          | 5.7      |                                                              |
| [Druid](https://github.com/alibaba/druid)                    | JDBC 连接池、监控组件 | 1.2.8    | [文档](http://www.iocoder.cn/Spring-Boot/datasource-pool/?kugga) |
| [MyBatis Plus](https://mp.baomidou.com/)                     | MyBatis 增强工具包    | 3.5.2    | [文档](http://www.iocoder.cn/Spring-Boot/MyBatis/?kugga)     |
| [Dynamic Datasource](https://dynamic-datasource.com/)        | 动态数据源            | 3.5.0    | [文档](http://www.iocoder.cn/Spring-Boot/datasource-pool/?kugga) |
| [Redis](https://redis.io/)                                   | key-value 数据库      | 5.0      |                                                              |
| [Redisson](https://github.com/redisson/redisson)             | Redis 客户端          | 3.17.3   | [文档](http://www.iocoder.cn/Spring-Boot/Redis/?kugga)       |
| [Spring MVC](https://github.com/spring-projects/spring-framework/tree/master/spring-webmvc) | MVC 框架              | 5.3.20   | [文档](http://www.iocoder.cn/SpringMVC/MVC/?kugga)           |
| [Spring Security](https://github.com/spring-projects/spring-security) | Spring 安全框架       | 5.6.5    | [文档](http://www.iocoder.cn/Spring-Boot/Spring-Security/?kugga) |
| [Hibernate Validator](https://github.com/hibernate/hibernate-validator) | 参数校验组件          | 6.2.3    | [文档](http://www.iocoder.cn/Spring-Boot/Validation/?kugga)  |
| [Activiti](https://github.com/Activiti/Activiti)             | 工作流引擎            | 7.1.0.M6 | [文档](TODO)                                                 |
| [Quartz](https://github.com/quartz-scheduler)                | 任务调度组件          | 2.3.2    | [文档](http://www.iocoder.cn/Spring-Boot/Job/?kugga)         |
| [Knife4j](https://gitee.com/xiaoym/knife4j)                  | Swagger 增强 UI 实现  | 3.0.3    | [文档](http://www.iocoder.cn/Spring-Boot/Swagger/?kugga)     |
| [Resilience4j](https://github.com/resilience4j/resilience4j) | 服务保障组件          | 1.7.1    | [文档](http://www.iocoder.cn/Spring-Boot/Resilience4j/?kugga) |
| [SkyWalking](https://skywalking.apache.org/)                 | 分布式应用追踪系统    | 8.5.0    | [文档](http://www.iocoder.cn/Spring-Boot/SkyWalking/?kugga)  |
| [Spring Boot Admin](https://github.com/codecentric/spring-boot-admin) | Spring Boot 监控平台  | 2.6.7    | [文档](http://www.iocoder.cn/Spring-Boot/Admin/?kugga)       |
| [Jackson](https://github.com/FasterXML/jackson)              | JSON 工具库           | 2.13.3   |                                                              |
| [MapStruct](https://mapstruct.org/)                          | Java Bean 转换        | 1.4.1    | [文档](http://www.iocoder.cn/Spring-Boot/MapStruct/?kugga)   |
| [Lombok](https://projectlombok.org/)                         | 消除冗长的 Java 代码  | 1.16.14  | [文档](http://www.iocoder.cn/Spring-Boot/Lombok/?kugga)      |
| [JUnit](https://junit.org/junit5/)                           | Java 单元测试框架     | 5.8.2    | -                                                            |
| [Mockito](https://github.com/mockito/mockito)                | Java Mock 框架        | 4.0.0    | -                                                            |

### 前端

| 框架                                                         | 说明             | 版本   |
| ------------------------------------------------------------ | ---------------- | ------ |
| [Vue](https://cn.vuejs.org/index.html)                       | JavaScript 框架  | 2.6.12 |
| [Vue Element Admin](https://panjiachen.github.io/vue-element-admin-site/zh/) | 后台前端解决方案 | -      |

# 四、联系我们

## hisun_aiteam@outlook.com

