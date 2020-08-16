
# Servlet

这部分的示例代码都在 [servlet_demo](./servlet_demo) 中。

## 简单示例

以 Maven 工程为例，开发步骤如下：

1. 在 `pom.xml` 中添加依赖：

    ``` xml
    <!-- Servlet -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>4.0.1</version>
    </dependency>
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>jstl</artifactId>
      <version>1.2</version>
    </dependency>
    ```

2. 创建处理响应的类，必须继承 `HttpServlet` 抽象类：

    ``` java
    // 必须继承 HttpServlet 类
    public class SimpleServlet extends HttpServlet {
      // 重写 service 方法。方法中的两个参数分别是 请求 和 响应 。无论 GET 还是 POST 都是该方法处理。
      @Override
      protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求的参数
        String name = req.getParameter("name");
        System.out.println("name: " + name);
        // 返回响应内容
        String html = "<h1>hi, " + name + ".</h1>";
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(html);
      }
      // 如果只针对 GET 或 POST 处理，可以重写 doGet 或 doPost 。
    }
    ```

3. 在 `web.xml` 配置文件中，指定访问路径与处理响应的类之间的映射关系：

    ``` xml
    <!-- 指定 Servlet 实体类 -->
    <servlet>
      <servlet-name>my-first-servlet</servlet-name>
      <servlet-class>demo.SimpleServlet</servlet-class>
    </servlet>
    <!-- 指定路径映射 -->
    <servlet-mapping>
      <servlet-name>my-first-servlet</servlet-name>
      <url-pattern>/test</url-pattern>
    </servlet-mapping>
    ```

4. 上面例子已经配置了 `/test` 路径。
    - 在浏览器输入 `localhost:8080/test?name=zhangsan` 即可看到效果。
    - 注意端口号，根据你配置的 Tomcat 会有所不同。

</br>

## Servlet 的生命周期

1. 装载 `web.xml` ，加载继承 `HttpServlet` 的类。
2. 创建对象。
3. 调用 `init()` 方法初始化。
4. 以 `service()` 方法提供服务。
5. 在 Tomcat 关闭时，调用 `destory()` 方法销毁。

</br>

## 注解开发

在 Servlet3.x 之后，提供了注解开发。

使用方法很简单，只需要配置 `@WebServlet()` 即可。

``` java
// Servlet 注解开发
@WebServlet("/bye")
public class AnnotationServlet extends HttpServlet {
  // ...
}
```

</br>

## 启动服务时自动加载

默认情况下，只有当服务被用户访问时，对应的类才会被加载，这可能会影响到用户的使用。

我们可以在启动服务时，就配置对应的类预先加载。有两种方式：

1. 在 `web.xml` 中配置，在 `<servlet>` 标签中添加一个 `<load-on-startup>` 标签：

    ``` xml
    <servlet>
      <servlet-name>my-first-servlet</servlet-name>
      <servlet-class>demo.SimpleServlet</servlet-class>
      <load-on-startup>1</load-on-startup>
    </servlet>
    ```

    `<load-on-class>` 的值范围是 0~9999 ，越小优先级越高，不配置则不会自动加载。

2. 还可以在注解中配置：

    ``` java
    @WebServlet("/bye", loadOnStartup = 2)
    public class AnnotationServlet extends HttpServlet {
      // ...
    }
    ```

    `loadOnStartup` 的值范围与上面相同。

</br>

## ServletContext

`ServletContext` 一般用于给 JSP 设置变量。通过 `HttpServletRequest` 对象的 `getServletContext()` 方法可以获取 `ServletContext` 对象。

给 JSP 设置变量可以使用对象上的 `.setAttribute(key, value);` 方法。
