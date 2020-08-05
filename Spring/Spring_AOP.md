
# Spring AOP

AOP(Aspect Oriented Programing, 面向**切面**编程) 采用**横向抽取**机制，它取代了传统**纵向继承**体系重复性代码（性能监视、事务管理、安全检查、缓存）。

AOP 是 OOP 的延申，为了解决 OOP 开发中的一些问题。

此部分的示例代码，都放在 [spring_aop_demo](./spring_aop_demo) 中。

</br>

## 理论简介

假设有 `UserService` 类：

``` java
class UserService {
  public void save() {}
  public void update() {}
  public void delete() {}
  public void search() {}
}
```

假如我们在保存数据前，需要对数据校验，在传统开发中，可以通过**纵向继承**，由父类提供校验方法：

``` java
class ServiceBase {
  public boolean checkPrivilege() {
    // ....
    return true;
  }
}
class UserService extends ServiceBase {
  public void save() {
    boolean verity = checkPrivilege();
    // 保存或不保存
  }
  // ....
}
```

现在，我们可以通过**代理**原来的 `ServiceBase` 类，代理类与原来的 `ServiceBase` 是平级的，所以被称为"横向"。

在代理对象中，可以针对某个方法的"增强"。

</br>

## AOP 相关术语

| 术语                  | 说明                                                   | 例子（以上面例子说明）                               |
|:----------------------|:-------------------------------------------------------|:-----------------------------------------------------|
| **JoinPoint(连接点)** | 是指那些被拦截到的点。                                 | `save`/`update`/`delete`/`search`等可以被代理的方法  |
|                       | 在 Spring 中，点指的是方法。（ Spring 也仅支持此方式） |                                                      |
| Pointcut(切入点)      | 是指我们需要对哪些 JoinPoint 进行拦截的定义。          | 若需要对`save`增强，那`save`就是切入点               |
| **Advice(通知/增强)** | 是指拦截到 JoinPoint 之后要做的事。                    | 拦截`save`方法后，需要鉴权，那"鉴权"是"通知/增强"    |
|                       |                                                        | 在方法前执行的，叫前置通知，例如身份校验；           |
|                       |                                                        | 在方法后执行的，叫后置通知，例如记录日志；           |
|                       |                                                        | 在方法前后都需要执行的，叫环绕通知，例如操作日志     |
| Introduction(引介)    | 是一种特殊的通知，在不修改类代码的前提下，             | （Spring 不提供）                                    |
|                       | 可以在运行期间为*类*动态添加一些方法或 Field 。        |                                                      |
| **Target(目标对象)**  | 被代理的对象。                                         | `UserService`就是"Target"                            |
| Weaving(织入)         | 是指把增强应用到目标对象来闯将的新的代理对象的过程。   | 简单说就是，方法被增强的这个过程叫织入               |
|                       | Spring 采用动态代理织入，                              |                                                      |
|                       | 而 AspectJ 采用编译期间织入和类装载期织入。            |                                                      |
| **Proxy(代理)**       | 一个类被 AOP 织入增强后，就会产生一个结果代理类。      |                                                      |
| Aspect(切面)          | 是切入点和通知（引介）的结合。                         | 简单说就是，多个方法都被增强了，它们的一个整体叫切面 |

</br>

## AOP 底层原理

Spring 是在运行期间，动态生成代理对象，不需要特殊的编译器。

Spring AOP 的底层就是通过 **JDK 动态代理** 或 **CGLib 动态代理技术** ，为目标 Bean 执行横向织入：

- 若目标对象实现了若干接口， Spring 就会使用 JDK 的 `Java.lang.reflect.Proxy` 类代理。

- 若目标对象没有实现任何接口， Spring 会使用 CGLib 库生成目标对象的子类。

JDK 的动态代理实现，请看 **demo_1** 包。这部分内容只需要了解即可。

CGLIB 生成代理的示例，请看 **demo_2** 包。

注意事项：

- 程序中应优先对接口创建代理，便于程序解耦维护

- 标记为 `final` 的方法，不能被代理，因为无法被覆盖
  - JDK 动态代理，是针对接口生成自类，接口中方法不能使用 `final` 修饰
  - CGLib 是针对目标类生成自类，因此类或方法不能使用 `final` 修饰，否则无法被继承、

</br>

## Spring AOP 基础

AOP 联盟为 Advice 定义了 `org.aopalliance.aop.Interface.Advice` 。

Spring 按照通知 Advice 在目标类的连接点位置，可以分为 5 类：

- 前置通知(`org.springframework.aop.MethodBeforeAdvice`): 在目标方法执行前实施增强。

- 后置通知(`org.springframework.aop.AfterReturningAdvice`): 在目标方法执行后实施增强。

- 环绕通知(`org.springframework.aop.MethodInterceptor`): 在目标方法执行前后都实施增强。

- 异常抛出通知(`org.springframework.aop.ThrowsAdvice`): 在方法抛出异常后实施增强。

- 引介通知(`org.springframework.aop.IntroductionInterceptor`): 在目标类中添加新的属性和方法（不要求掌握）。

Spring AOP 切面类型：

- `Advisor`: 代表一般切面， Advice 本身就是一个切面，对目标类**所有方法**进行拦截。

- `PointcutAdvisor`: 代表具有切点的切面，可以**指定**拦截目标类的哪些**方法**。

- `IntroductionAdvisor`: 代表引介切面，针对引介通知使用的切面（不要求掌握）。

首先需要在 `pom.xml` 添加依赖：

``` xml
<!-- AOP 相关依赖 -->
<dependency>
  <groupId>aopalliance</groupId>
  <artifactId>aopalliance</artifactId>
  <version>1.0</version>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-aop</artifactId>
  <version>4.2.5.RELEASE</version>
</dependency>
```

### Advisor 的传统实现

具体代码请看 **demo_3** 包。下面的思路：

1. 先创建接口、类。

2. 创建实现于 `MethodBeforeAdvice` 接口的前置增强实现类。

    ``` java
    public class MyBeforeAdvice implements MethodBeforeAdvice {
      // 方法内部就是"增强"的内容
      public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("前置增强的内容......(" + method.getName() + "|" + objects.getClass() + ")");
      }
    }
    ```

3. 在 `applictionContext.xml` 配置 `<bean>` 标签：

    - 配置配置目标类：

      ``` xml
      <bean id="target_obj" class="demo_3.UserService"/>
      ```

    - 配置**前置**增强类：

      ``` xml
      <bean id="my_before_advice" class="demo_3.MyBeforeAdvice"/>
      ```

    - 配置代理对象：

      ``` xml
      <bean id="user_service_proxy" class="org.springframework.aop.framework.ProxyFactoryBean">
        <!-- 被代理的目标类 -->
        <property name="target" ref="target_obj"/>
        <!-- 实现的接口 -->
        <property name="proxyInterfaces" value="demo_3.IUserService"/>
        <!-- 若代理的对象没有实现接口，则配置 proxyTargetClass 属性为 true -->
        <!-- <property name="proxyTargetClass" value="true"/> -->
        <!-- 拦截器的名称 -->
        <property name="interceptorNames" value="my_before_advice"/>
        <!-- 是否返回单例  (默认 true ) -->
        <property name="singleton" value="true"/>
        <!-- 是否强制使用 CGLib  (默认 false ) -->
        <property name="optimize" value="false"/>
      </bean>
      ```

### PointcutAdvisor

像上面这种，使用普通 Advice 作为切面，将对目标类的所有方法进行拦截，不够灵活。

在实际开发中，一般使用带有切点的切面。

常用的 PointcutAdvisor 实现类：

- `DefaultPointcoutAdvice`: 它可以通过任意 Pointcut 和 Advice 组合定义切面。
- **`JdkRegexpMethodPointcut`**: 构造正则表达式切点。

具体代码请看 **demo_4** 包。

``` xml
<!-- 目标类 -->
<bean id="target_obj" class="demo_4.UserService"/>

<!-- 配置通知类 -->
<bean id="my_interceptor" class="demo_4.MyInterceptor"/>

<!-- 一般的切面使用通知作为切面。现在需要对目标类的某个方法增强，则需要配置一个带有切入点的切面 -->
<bean id="my_advice" class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
  <!-- 指定正则表达式 （包.方法） （注意把"."转义） （若name="patterns",可以指定多个规则,用逗号分隔） -->
  <property name="pattern" value="demo_4\.UserService\.save.*"/>
  <!-- 指定通知类 -->
  <property name="advice" ref="my_interceptor"/>
</bean>

<!-- 配置代理对象 -->
<bean id="my_proxy_obj" class="org.springframework.aop.framework.ProxyFactoryBean">
  <property name="target" ref="target_obj"/>
  <property name="proxyTargetClass" value="true"/>
  <property name="interceptorNames" value="my_advice"/>
</bean>
```

</br>

## AOP 的自动代理

在上面，无论是 Advisor 还是 PointcutAdvisor 都需要很多的配置，假如我们有很多的类和方法需要代理，这工作量是很大的，且难以维护。

### 基于 beanName 的自动代理

具体代码请看 **demo_5** 包。

``` xml
<!-- 配置目标类 -->
<bean id="car_service" class="demo_5.CarService"/>
<bean id="user_service" class="demo_5.UserService"/>

<!-- 配置增强实现类 -->
<bean id="my_after_advice" class="demo_5.MyAfterAdvice"/>
<bean id="my_before_advice" class="demo_5.MyBeforeAdvice"/>

<!-- 配置基于 beanName 的自动代理 （不需要id是因为别的地方不需要引用它） -->
<bean class="org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator">
  <!-- 配置 beanName 的匹配规则 -->
  <property name="beanNames" value="*_service"/>
  <!-- 配置增强类的名字（多个用逗号分隔） -->
  <property name="interceptorNames" value="my_after_advice, my_before_advice"/>
</bean>
```

缺点是，会对所有类的所有方法产生代理。

### 基于切面信息的自动代理

具体代码请看 **demo_6** 包。

``` xml
<!-- 配置目标类 -->
<bean id="car_service" class="demo_6.CarService"/>
<bean id="user_service" class="demo_6.UserService"/>

<!-- 配置增强实现类 -->
<bean id="my_after_advice" class="demo_6.MyAfterAdvice"/>

<!-- 配置切面 -->
<bean id="my_advisor" class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
  <!-- 配置方法的匹配规则 （包.方法） （注意把"."转义） （若name="patterns",可以指定多个规则,用逗号分隔） -->
  <property name="pattern" value="demo_6\.CarService\.save.*"/>
  <property name="advice" ref="my_after_advice"/>
</bean>

<!-- 自动根据上面的切面配置 -->
<!-- 配置基于切面信息的自动代理 （同样不需要id） -->
<bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"/>
```

</br>

## 其他

Spring2.0 后增加了对 AspectJ 的支持。[AspectJ 的介绍](./AspectJ.md)。
