package demo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessHistoryInterceptor implements HandlerInterceptor {
  // 日志记录器 （类都属于org.slf4j包下）
  private Logger logger = LoggerFactory.getLogger(AccessHistoryInterceptor.class);

  // 只用到了前置拦截器
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    StringBuffer log = new StringBuffer();
    // 获取特定信息
    log.append(request.getRemoteAddr() + "|");       // IP地址
    log.append(request.getRequestURL() + "|");       // 被访问的地址
    log.append(request.getHeader("user-agent"));  // 用户使用环境
    // 日志追加记录
    logger.info(log.toString());
    return true;
  }
}
