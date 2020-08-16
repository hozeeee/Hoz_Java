package demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// Servlet 注解开发
@WebServlet(value = "/bye", loadOnStartup = 2)
public class AnnotationServlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // 获取请求的参数
    String name = req.getParameter("name");
    System.out.println("name: " + name);
    // 返回响应内容
    String html = "<h1>bye, " + name + "</h1>";
    PrintWriter printWriter = resp.getWriter();
    printWriter.println(html);
  }
}
