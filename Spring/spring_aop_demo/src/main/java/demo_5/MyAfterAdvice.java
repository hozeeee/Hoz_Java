package demo_5;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class MyAfterAdvice implements AfterReturningAdvice {
  public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
    System.out.println("后置增强代码....");
  }
}
