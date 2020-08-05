import java.sql.*;
// 引入 MySQL 驱动
//import com.mysql.cj.jdbc.Driver;

public class TestJDBC {
  public static void main(String[] args) {
    System.out.println("程序开始");

    Connection conn = null;
    Statement stmt = null;
    ResultSet resultSet = null;
    // 需要捕获错误
    try {
      /*
        注意，进行下面操作前，需要
        先创建 数据库 jdbc_test 和 数据表 user
        数据表 user 包含两个字段 id 和 username
        需要插入一定数量的数据才能看到效果
       */

      // 1. 加载驱动（可能不需要，jdbc可能已经做了）
      // DriverManager.registerDriver(new Driver());  // 这句代码会导致驱动重复加载，因为 Driver 的静态代码块已经有这句代码。
      // Class.forName("com.mysql.cj.jdbc.Driver");   // 这句代码也不是必须的，只要依赖包含了即可。


      // 2. 获得连接 （记得带上参数 serverTimezone=UTC ，否则会报时区错误）
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_test?serverTimezone=UTC", "root", "root");

      // 3. 创建执行 SQL 的对象
      String sql = "SELECT * FROM user";
      stmt = conn.createStatement();

      // 4. 执行 SQL
      resultSet = stmt.executeQuery(sql);

      // 5. 从结果迭代器中读取数据
      while ((resultSet.next())) {
        Integer id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        System.out.println(id + "_" + username);
      }

      System.out.println("程序结束");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } finally {
      // 6. 释放资源
      if (resultSet != null) {
        try {
          resultSet.close();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
        resultSet = null;
      }
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
        stmt = null;
      }
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
        conn = null;
      }
    }

  }


}