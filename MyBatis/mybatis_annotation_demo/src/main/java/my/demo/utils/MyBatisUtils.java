package my.demo.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtils {
  public static SqlSessionFactory ssf = null;

  static {
    try {
      Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
      ssf = new SqlSessionFactoryBuilder().build(reader);
    } catch (IOException e) {
      e.printStackTrace();
      throw new ExceptionInInitializerError(e);
    }
  }

  public static SqlSession openSession() {
    return ssf.openSession();
  }

  public static void closeSession(SqlSession ss) {
    if (ss != null) {
      ss.close();
    }
  }
}
