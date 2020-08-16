package demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// 必须继承 HttpServlet 类
public class SimpleServlet extends HttpServlet {

  // 重写 service 方法。方法中的两个参数分别是 请求 和 响应 。
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // 获取请求的参数
    String name = req.getParameter("name");
    System.out.println("name: " + name);
    // 返回响应内容
    String html = "<h1>hi, " + name + "</h1>";
    PrintWriter printWriter = resp.getWriter();
    printWriter.println(html);
  }
}
