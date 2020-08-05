
# Maven

Maven 是项目管理工具，对软件项目提供构建与依赖管理，是 Apache 下的 Java 开源项目，为 Java 项目提供了统一的管理方式，已成为业界标准。

核心特性：

- 项目设置遵循统一的规则，保证不同开发环境的兼容性。
- 强大的依赖管理，项目依赖组件自动下载、自动更新。
- 可扩展的插件机制，使用简单，功能丰富。

## 安装与配置

首先需要在系统变量添加一项 `JAVA_HOME` ，指定到 jdk1.8 的安装目录；同时在环境变量添加 `jdk1.8\bin` 。

到[官网下载地址](http://maven.apache.org/download.cgi)，选择 `apache-maven-*.*.*-bin.zip` 下载。

把压缩包解压到需要安装的目录下，然后把它的 `bin` 目录添加到环境变量，重点是 `mvn.cmd` 文件。

一般 IED 都会集成，也可以首选改成自己下载的最新版本。

## Maven 坐标

GroupId: 机构或者团体的英文，采用 "逆向域名" 的形式书写

ArtifactId: 项目名称，说明其用途，例如 cms、oa...

Version: 版本号，一般采用 "版本+单词" 的形式，例如 1.0.0.RELEASE

## Maven 项目标准目录结构

| 目录                            | 用途                                                     |
|:--------------------------------|:---------------------------------------------------------|
| `${basedir}`                    | 根目录，用于保存 pom.xml                                 |
| `${basedir}/src/main/java`      | Java 源代码目录                                          |
| `${basedir}/src/main/resources` | 资源目录，保存配置文件、静态图片等                       |
| `${basedir}/src/test/java`      | 测试类的源代码                                           |
| `${basedir}/src/test/resources` | 测试时需要使用的资源文件                                 |
| `${basedir}/target`             | 项目输出的目录，用于存储 jar 、 war 文件                 |
| `${basedir}/target/classes`     | 字节码(.class)文件的输出目录                             |
| `${basedir}/pom.xml`            | 项目(Project)对象(Object)模型(Model)的简写，项目配置文件 |

## Maven 依赖管理

Maven 会根据 `pom.xml` 的 `<project>` 标签下的 `<Dependencies>` 标签下的所有 `<Dependency>` 的内容加载 jar 包。

Maven 会自动将依赖从**远程仓库**下载到**本地仓库**，并在工程中引用。

其中上面提到的本地仓库，是指当前计算机的 `.m2/repository` 目录，若需要的依赖包在本地仓库存在，就不会去远程啊仓库下载，以减少服务器的压力。

jar 包可以在 [search.maven.org](https://search.maven.org) 搜索特定关键字，然后把对应的 `<Dependency>` 标签内容添加即可，其他交给 Maven 自动下载。

如果当前项目使用国内的服务器：

1. 可以选择[阿里的](https://maven.aliyun.com)，找到 `public` 的地址。
2. 在 `pom.xml` 文件中的 `<project>` 标签下添加如下代码：

    ``` xml
    <repositories>
      <repository>
        <id>aliyun</id>
        <name>aliyun</name>
        <url>https://maven.aliyun.com/repository/public</url>
      </repository>
    </repositories>
    ```

3. 只有当 aliyun 的地址不能用的情况下，才会去 apache 下载。

## Maven 常用命令

| 命令                     | 用途                |
|:-------------------------|:--------------------|
| `mvn archetype:generate` | 创建 Maven 工程结构 |
| `mvn compile`            | 编译源代码          |
| `mvn test`               | 执行测试用例        |
| `mvn clean`              | 清除产生的项目      |
| `mvn package`            | 项目打包            |
| `mvn install`            | 安装至本地仓库      |

.....


## 修改本地仓库地址

1. 找到 Maven 的目录，打开 `conf` 目录下的 `settings.xml` 文件。
2. 在 `<settings>` 标签下，找到被注释的 `<localRepository>` 标签，包含了默认配置。
3. 按照示例，在该注释下添加一行，例如，要修改成 `D:/maven-repo` :

    ``` xml
    <localRepository>D:/maven-repo</localRepository>
    ```
