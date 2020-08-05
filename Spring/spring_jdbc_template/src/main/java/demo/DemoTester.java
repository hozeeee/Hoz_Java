package demo;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DemoTester {
  @Test
  public void doTest() {
    // 获取 JDBC Template 实例对象
    ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("springConfig.xml");
    final JdbcTemplate jdbcTemplate = (JdbcTemplate) cpxac.getBean("jdbcTemplate");

    // 创建数据库
    // execute 方法可以执行任何 SQL 语句，一般用于建表
    jdbcTemplate.execute("DROP TABLE IF EXISTS t_student;");
    jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS t_student(id INT UNSIGNED AUTO_INCREMENT KEY, name VARCHAR(20) NOT NULL, sex ENUM('男','女','保密'), age INT);");

    // JDBC Template 对数据库操作分为两类： "增删改" 和 "查"

    // 修改单条记录
    int insertCount1 = jdbcTemplate.update("INSERT t_student VALUES(null, ?, ?, ?)", "张三", "男", 16);
    System.out.println("update-1 成功插入" + insertCount1 + "条");
    int insertCount2 = jdbcTemplate.update("UPDATE t_student SET sex=?, age=? WHERE name='张三';", new Object[]{"女", 28});
    System.out.println("update-2 成功修改" + insertCount2 + "条");
    System.out.println("------------------------------------------------------------------------");

    // 同时修改多条数据
    int[] insertCounts = jdbcTemplate.batchUpdate(new String[]{
            "INSERT t_student VALUES(null,'小乔', '女', '18');",
            "INSERT t_student VALUES(null,'大乔', '女', '20');"
    });
    System.out.println("batchUpdate-1 插入成功" + getSum(insertCounts) + "条");
    // （常用）执行同构 SQL ，使用一个模板，插入多条数据
    int[] insertCounts2 = jdbcTemplate.batchUpdate(
            "INSERT t_student VALUES(null, ?, ?, ?);",
            new ArrayList<Object[]>() {{
              this.add(new Object[]{"张飞", "男", 22});
              this.add(new Object[]{"关羽", "男", 23});
              this.add(new Object[]{"刘备", "男", 24});
            }}
    );
    System.out.println("batchUpdate-2 插入成功" + getSum(insertCounts2) + "条");
    System.out.println("------------------------------------------------------------------------");

    // 查询单行单列 （如果行或列不为1都会异常,分别是IncorrectResultSizeDataAccessException和IncorrectResultSetColumnCountException）
    Integer recordCount = jdbcTemplate.queryForObject("SELECT count(*) FROM t_student;", Integer.class);
    System.out.println("queryForObject 查询总记录数：" + recordCount);
    System.out.println("------------------------------------------------------------------------");

    // 查询多行单列 （如果列不为1，会报IncorrectResultSetColumnCountException异常）
    List<String> names = jdbcTemplate.queryForList("SELECT name FROM t_student;", String.class);
    System.out.println("queryForList 查询所有名字： " + concatString(names));
    System.out.println("------------------------------------------------------------------------");

    // （常用）查询单行多列
    Map liubei = jdbcTemplate.queryForMap("SELECT * FROM t_student WHERE name=?;", "刘备");
    System.out.println("queryForMap 查询刘备的信息： " + liubei);
    System.out.println("------------------------------------------------------------------------");
    // （常用）查询多行多列
    List<Map<String, Object>> taoyuan = jdbcTemplate.queryForList("SELECT * FROM t_student WHERE name=? OR name=? OR name=?;", new Object[]{"刘备", "关羽", "张飞"});
    System.out.println("queryForList 查询刘关张的信息： " + taoyuan);
    System.out.println("------------------------------------------------------------------------");

    // （重要！）查询单条记录并封装到对象中
    // 需要提前创建用于存放数据的类，然后实现 JDBC Template 提供的 RowMapper 接口
    RowMapper<Student> myRowMapper = new RowMapper<Student>() {
      // 第一个参数是结果集，第二个参数是当前处理的行号
      public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();
        // 指定映射关系
        student.setId(resultSet.getInt("id"));
        student.setName(resultSet.getString("name"));
        student.setSex(resultSet.getString("sex"));
        student.setAge(resultSet.getInt("age"));
        return student;
      }
    };
    Student student1 = (Student) jdbcTemplate.queryForObject("SELECT * FROM t_student WHERE name=?;", myRowMapper, "小乔");
    System.out.println("queryForList 查询单条记录并封装到 Student 对象： " + student1);
    System.out.println("------------------------------------------------------------------------");

    // （重要！）查询多条记录并封装到对象列表中
    List<Student> allStudent2 = jdbcTemplate.query("SELECT * FROM t_student;", myRowMapper);
    System.out.println("query 查询所有记录并都封装到 Student 对象： " + concatString(allStudent2));
    System.out.println("------------------------------------------------------------------------");


  }


  private int getSum(int[] nums) {
    int sum = 0;
    for (int num : nums) {
      sum += num;
    }
    return sum;
  }

  private String concatString(List<? extends Object> strings) {
    StringBuilder res = new StringBuilder("[");
    for (Object tmp : strings) {
      res.append(tmp + " ");
    }
    res.append("]");
    return res.toString();
  }
}
