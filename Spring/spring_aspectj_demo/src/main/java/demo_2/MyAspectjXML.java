package demo_2;

public class MyAspectjXML {

  public void before() {
    System.out.println("前置通知................");
  }

  public void afterReturning(Object obj) {
    System.out.println("后置通知............... (返回值:" + obj + ")");
  }
}
