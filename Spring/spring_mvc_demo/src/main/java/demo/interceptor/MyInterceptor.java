package demo.interceptor;

import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// HandlerInterceptor 接口有三个方法可以实现，但并非必须要实现
public class MyInterceptor implements HandlerInterceptor {

  // 在"执行请求方法"之前。
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    System.out.println("preHandle ....");
    System.out.println(response.getHeader("Content-Type")); // null
    System.out.println("------------------------------------------------");
    // 返回值表示是否可以继续执行。若返回 false ，请求就被终止。
    return true;
  }

  // 在此阶段，目标资源已被 Spring MVC 框架处理。
  // 执行时间：在"当请求方法有返回值"之后；在"产生响应体"之前。
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    System.out.println("postHandle ....");
    System.out.println(response.getHeader("Content-Type")); // 有内容
    System.out.println("------------------------------------------------");
  }

  // 在此阶段，响应文本已经产生。
  // 执行时间：在"产生响应体"之后。
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    System.out.println("afterCompletion ....");
    System.out.println(response.getHeader("Content-Type")); // 有内容
    System.out.println("------------------------------------------------");
  }

}
