
# JSP

JSP(Java Server Pages, Java服务器页面) 是 J2EE 的功能模块，有 Web 服务器执行，其作用就是降低动态网页的开发难度。

JSP 的本质就是 Servlet ， JSP 代码会被转译成 Servlet 源代码。

## JSP的基本语法

下面按功能划分为四种。

### 执行代码块

用于在 JSP 中嵌入 Java 代码。语法是 `<%  Java代码块  %>` 。

示例：

``` jsp
<%
  for(int i = 0 ; i <= 50 ; i++){
    // out 就是一个 PrintWriter 对象，默认自带
    out.println("<h2>" + i + "</h2>");
  }
%>
```

### 声明构造块

用于声明变量或方法。语法是 `<%!  声明语句  %>` 。

示例：

``` jsp
<%!
  public int add(int a, int b) {
    return a + b;
  }
%>
```

### 输出指令

用于在 JSP 页面中显示 Java 代码的执行结果。语法是 `<&=  Java代码  &>` 。

示例：

``` jsp
<%!
  "<span>" + name + "</span>"
%>
```

### 处理指令

用于提供 JSP 执行过程中的辅助信息。语法是 `<%@  JSP指令  %>` 。

示例：

``` jsp
<!-- page 指令用于定义当前页面的全局设置，如导入那些Java包、响应头的字符集 -->
<%@ page import="java.util.*" contentType="text/html;charset=utf-8" %>

<!-- include 指令将 其他JSP页面 与 当前JSP页面 合并，就是将页面"模块化" -->
<%@ include file="foo/baz.jsp" %>

<!-- taglib 指令用于引入 JSP标签库 -->
<%@ taglib ... %>
```

## 注释

在 JSP 中可使用三种注释，分别有不同含义和作用：

- `<%-- 注释内容 --%>`: 表示 JSP 注释，被注释的内容不会被 JSP 解析。

- `/ 注释内容 /`、`/* 注释内容 */`: 只能用于 `<% %>` 中，用于注释 Java 代码。

- `<!-- 注释内容 -->`: 这是 HTML 的注释，会被包含在响应的内容中，但不会被浏览器显示。

</br>

## EL

EL(Expression Language, 表达式语言) 用于简化 JSP 的输出。语法是 `${表达式}` ，用于将该位置的内容替换。

略...

</br>

## JSTL

JSTL(JSP Standard Tag Library, JSP标准标签库) 也是用于简化 JSP 开发，提高代码的可读性与可维护性。

JSTL 由 SUN(Oracle) 定义规范，由 Apache Tomcat 团队实现。

略...

其中还有一个常用的标签库： fmt 格式化标签库，一般用于输出格式化日期。

略...
