<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020/8/4
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%-- "${}" 放入变量，其中 "u" 由 ModelAndView 提供 --%>
<h1>My name is ${u.name}.I'm ${u.age} year ago.</h1>
</body>
</html>
