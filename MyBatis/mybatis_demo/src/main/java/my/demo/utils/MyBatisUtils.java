package my.demo.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtils {
  // 静态工厂模型
  public static SqlSessionFactory ssf = null;

  // 静态代码块，在初始化类时实例化 SqlSessionFactory
  static {
    try {
      Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
      ssf = new SqlSessionFactoryBuilder().build(reader);
    } catch (IOException e) {
      e.printStackTrace();
      // 初始化错误是，通过抛出异常 ExceptionInInitializerError 通知调用者
      throw new ExceptionInInitializerError(e);
    }
  }

  // 注意使用 static 修饰符，因为此工具类无需实例化

  /**
   * 创建一个新的 SqlSession 对象
   * @return SqlSession
   */
  public static SqlSession openSession() {
    return ssf.openSession();
  }

  /**
   * 释放一个有效的 SqlSession 对象
   * @param ss
   */
  public static void closeSession(SqlSession ss) {
    if (ss != null) {
      ss.close();
    }
  }
}
