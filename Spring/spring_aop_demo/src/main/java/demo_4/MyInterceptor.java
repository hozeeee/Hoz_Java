package demo_4;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

// 环绕通知类
public class MyInterceptor implements MethodInterceptor {
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    System.out.println("环绕前的增强.....");
    // 执行原来的方法
    Object obj = methodInvocation.proceed();
    System.out.println("环绕后的增强.....");
    return obj;
  }
}
