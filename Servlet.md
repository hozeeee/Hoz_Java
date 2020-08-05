
# Servlet


TODO:


## Tomcat

简介：

- Tomcat 是 Apache 软件基金会旗下的一款免费的开源 Web 应用服务器程序。
- Tomcat 是 J2EE Web(Servlet 与 JSP) 标准的实现者。
- J2SE 是 J2EE 运行的基石，运行 Tomcat 离不开 J2SE 。

安装：(须先安装 JDK8)

1. 在 [Tomcat官网](https://tomcat.apache.org/) 选择 8.*.* 的版本(与JDK对应)下载。
2. 下载 Windows 版本后，解压到合适的目录。
3. 在解压后的目录，找到 `/bin/startup.bat` ，这个就是启动文件。在控制台执行它即可。
4. 执行启动文件后，若提示 `Neither the JAVA_HOME nor the JRE_HOME environment variable is defined` ，则需要配置相应的环境变量。
5. 设置环境变量 `JAVA_HOME` 的值为 JDK7 的跟目录，如 `C:\Program Files\Java\jdk1.8.0_251` 。
6. 在浏览器输入 `localhost:8080` 若显示 Tomcat 的主页，证明 Tomcat 服务器启动成功。
7. 在 `~/apache-tomcat-8.5.57/webapps` 目录下，是存放需要发布的 Web 项目文件，这个一般由 IDE 关联发布。

Tomcat 在 **8.0** 之前默认使用**字符集 ISO-8859-1** ，属于西欧字符集，不支持中文。在 8.0 之后就是默认 UTF8 。

修改 URL 的字符集方法：

- 打开安装目录下的 `conf/server.xml`
- 找到 `<Connecter>` 标签，添加属性 `URIEncoding="UTF-8"` 。该设置表示对 URL 使用 UTF8 编码，解决 URL 传参的编码问题。















