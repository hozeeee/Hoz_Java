
# Spring Boot

Spring Boot 可以简单理解为 Spring 的脚手架，类似于 Vue 的 Vue-cli 。

核心特性：

- 极低的学习成本
- 可独立运行的 Spring 项目
- "习惯优于配置"，极大的提高了开发效率
- 极简的组件依赖，自动发现与自动装配
- 提供运行时的应用监控
- 与分布式框架和云计算的天然集成

环境准备：

- JDK8 以上版本
- 建议使用 IntelliJ IDEA Ultimate

Spring Boot 目录结构：

| /src/main                         | 项目根目录            |
|:----------------------------------|:----------------------|
| /java                             | Java 源码目录         |
| /resources                        | 资源目录              |
| /resources/static                 | 静态资源目录          |
| /resources/templates              | 表示层页面目录(如jsp) |
| /resources/application.properties | Spring Boot 配置文件  |
| /test                             | 测试文件目录          |

</br>

## 使用 Maven 创建项目

1. IEDA 点击创建 Maven 项目。

2. 在 `pom.xml` 添加依赖。

    在 `<project>` 标签下加入如下内容：

    ``` xml
      <!-- 父标签包含了需要的依赖，不需要我们手动一个个添加 -->
      <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.1.RELEASE</version>
      </parent>
      <!-- 指定需要用到的 start 包 -->
      <!-- 不需要指定版本号，原因是上面已经包含了 -->
      <dependencies>
        <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
      </dependencies>
    <!-- 配置打包插件 -->
      <build>
        <plugins>
          <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
          </plugin>
        </plugins>
      </build>
    ```

3. 定义程序入口类。

    在包的根目录下，创建 `MySpringBootApplication` 类，它作为程序入口类，名字是约定俗成的：

    ``` java
    // @SpringBootApplication 说明这是程序的入口类，名字是约定俗成是 *Application
    @SpringBootApplication
    public class MySpringBootApplication {
      public static void main(String[] args) {
        // 启动 SpringBoot 应用（默认启动到本地的 8080 端口）
        SpringApplication.run(MySpringBootApplication.class);
      }
    }
    ```

4. 启动 `MySpringBootApplication` 的 `main` 方法即可。

</br>

## 使用 Spring Initilizr 创建项目 （推荐）

使用 IDEA 创建项目，但这次不使用 Maven 。

1. 在创建项目的左侧列表中，有 `Spring Initilizr` 选项，选择默认即可，会自动连接到 Spring 官网生成。点击下一步。

2. 下一步菜单中，这是项目和组织名，配置 Java 的版本。点击下一步。

3. 在此界面中，列举了所有 SpringBoot 的 starter ，勾选需要的即可。点击下一步。

4. 指定存储路径。点击完成。

</br>

## Spring Boot 启动流程

1. 加载配置文件： `application.properties`

2. 自动装配。例如：
    - `spring-boot-starter-web`: 增加 Web 的支持。
    - `spring-boot-starter-data-jpa`: 对 JPA 支持，继承 Hibernate 。
    - `spring-boot-starter-logging`: 增加 logback 日志的支持。
    - `spring-boot-starter-test`: 继承 JUnit 单元测试框架。

3. 加载组件。如 `@Respository`、`@Service`、`@Controller`、`@Component`、`@Entiry` 。

4. 应用初始化。

</br>

## Spring Boot 配置

### 常用配置

| 配置名称                    | 默认值 | 描述               | 示例                                                 |
|:----------------------------|:-------|:-------------------|:-----------------------------------------------------|
| server.port                 | 8080   | 端口号             |                                                      |
| server.servlet.context-path | /      | 设置应用上下文     |                                                      |
| logging.file                | 无     | 日志文件输出路径   |                                                      |
| logging.level.*             | INFO   | 最低日志输出级别   |                                                      |
| debug                       | false  | 开启/关闭调试模式  |                                                      |
| spring.datasource.*         |        | 与数据库相关的设置 | spring.datasource.driver-class-name=com.mysql.Driver |
|                             |        |                    | spring.datasource.url=jdbc:mysql:///test             |
|                             |        |                    | spring.datasource.username=root                      |
|                             |        |                    | spring.datasource.password=root                      |
| ...                         |        | ...                |                                                      |

### application.yml

除了使用 `application.properties` 作为配置文件，还可以使用 `application.yml` 。使用 `application.yml` 的好处是易于阅读维护。

YAML 是一种简介的非标记语言。YAML 以数据未中心，使用空白、缩进、分行来组织数据，从而使得数据更加简洁易读。

YAML 的语法格式：

- 标准格式： `${key}: ${value}`
- 使用空格代表层级关系，以 "`:`" 结束

示例：

``` yaml
debug: true
# 日志
logging:
  level:
    root: info
    file: C:/myspringboot.log
# 数据库
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql:///test
    username: root
    password: root
```

### 自定义配置项

Spring Boot 允许我们自定义应用程序配置项，在程序运行时允许动态加载，为程序提供了良好的可维护性。

在实际项目开发中，我们通常将项目的自定义信息放在配置文件中。

示例：

``` yaml
# 运行时动态提供的自定义变量
myDefaultValue:
  user:
    name: admin
    age: 18
    sex: 男
```

获取自定义变量（使用`@Value`注解）：

``` java
public class MyDemoClass() {
  // 获取自定义变量
  @Value("${myDefaultValue.user.name}")
  private String name;
  @Value("${myDefaultValue.user.age}")
  private Integer age;
  @Value("${myDefaultValue.user.sex}")
  private String sex;

  // .....
}
```

### 环境配置文件

Spring Boot 可针对不同的环境提供不同的 Profile 文件。

Profile 文件的默认命名格式为 `application-{env}.yml` 。

使用 `spring.profiles.active` 选项来指定不同的 profile 。

示例：

1. 创建三份(或更多)文件，分别是 `application.yml`、`application-prd.yml`、`application-dev.yml` 。

2. 在 `application-prd.yml` 内写生产环境的配置。如：

    ``` yaml
    debug: false
    ```

3. 在 `application-dev.yml` 内写开发环境的配置。如：

    ``` yaml
    debug: true
    ```

4. 在 `application.yml` 指定环境配置。如下：

    ``` yaml
    spring:
      profiles:
        # 现在是指定开发环境，如果是生产环境就改成 prd
        active: dev
    ```



