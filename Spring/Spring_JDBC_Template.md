
# Spring JDBC Template

JDBC Template 是 Spring 框架对 JDBC 操作的封装，简单灵活但不够强大。实际应用中还需要和其他 ORM 框架混合使用。

它的缺点是， SQL 与 Java 代码参杂；功能不够丰富。

示例代码放在 [spring_jdbc_template](./spring_jdbc_template) 中。

## 配置

1. `pom.xml` 依赖配置。包含三大部分，MySQL驱动、Spring组件（core，beans，context，aop）、JDBC Template（jdbc、tx）。：

    ``` xml
    <properties>
      <spring.version>4.2.5.RELEASE</spring.version>
    </properties>
    <dependencies>
      <!-- mysql 驱动 -->
      <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.38</version>
      </dependency>
      <!-- spring 4 件套 -->
      <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>${spring.version}</version>
      </dependency>
      <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-beans</artifactId>
        <version>${spring.version}</version>
      </dependency>
      <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>${spring.version}</version>
      </dependency>
      <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aop</artifactId>
        <version>${spring.version}</version>
      </dependency>
      <!-- JDBC Template -->
      <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>${spring.version}</version>
      </dependency>
      <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-tx</artifactId>
        <version>${spring.version}</version>
      </dependency>
    </dependencies>
    ```

2. 配置 `springConfig.xml` 文件：

    注意 `<beans>` 的属性配置，需要包含 aop、context、atx 。

    ``` xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
            http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
          ">

      <!-- 配置数据源 -->
      <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <!-- 请提前创建数据库： CREATE DATABASE IF NOT EXISTS test_jdbc_template; -->
        <property name="url" value="jdbc:mysql://localhost:3306/test_jdbc_template?serverTimezone=UTC"/>
        <property name="username" value="root"/>
        <property name="password" value="root"/>
      </bean>

      <!-- 配置 JDBC Template -->
      <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <!-- 指定数据源 -->
        <property name="dataSource" ref="dataSource"/>
      </bean>

    </beans>
    ```

3. 获取 JDBC Template 后，执行 SQL 语句。简单的示例如下：

    ``` java
    ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("springConfig.xml");
    JdbcTemplate jdbcTemplate = (JdbcTemplate) cpxac.getBean("jdbcTemplate");
    jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS test_teable(id int, name varchar(20));");
    ```

</br>

## 基本使用

这里只简单列出常用的方法及其重构方法，具体代码看 demo 。

``` java
// 单条增删改操作
int update(String sql, Object[] args);
int update(String sql, Object[] args);

// 批量增删改操作
int[] batchUpdate(String[] sql);
int[] batchUpdate(String[] sql, List<Object[]> args);

// 查询"单行单列"
T queryForObject(String sql, Class<T> type);
T queryForObject(String sql, Object[] args, Class<T> type);
T queryForObject(String sql, Class<T> type, Object... arg);

// 查询"多行单列"
List<T> queryForList(String sql, Class<T> type);
List<T> queryForList(String sql, Object[] args, Class<T> type);
List<T> queryForList(String sql, Class<T> type, Object... arg);

// 查询"单行多列"，并封装到 Map 中
Map queryForMap(String sql);
Map queryForMap(String sql, Object[] args);
Map queryForMap(String sql, Object... arg);

// 查询"多行多列"，并封装到 List<Map> 中
List<Map<String, Object>> queryForList(String sql)
List<Map<String, Object>> queryForList(String sql, Object[] args);
List<Map<String, Object>> queryForList(String sql, Object... arg);

// 查询"单行多列"，并封装到 RowMapper 接口所指定的对象中
T queryForObject(String sql, RowMapper<T> mapper);
T queryForObject(String sql, Object[] args, RowMapper<T> mapper);
T queryForObject(String sql, RowMapper<T> mapper, Object... arg);

// 查询"多行多列"，并封装到 RowMapper 接口所指定的对象中，结果为 List<T>
List<T> query(String sql, RowMapper<T> mapper);
List<T> query(String sql, Object[] args, RowMapper<T> mapper);
List<T> query(String sql, RowMapper<T> mapper, Object... arg);
```
