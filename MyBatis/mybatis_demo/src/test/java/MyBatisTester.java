import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import my.demo.dto.GoodsDto;
import my.demo.entity.Category;
import my.demo.entity.Goods;
import my.demo.utils.MyBatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyBatisTester {
  // 添加了 "@Test" 注解后， Junit 才能对其执行
  @Test
  public void testSqlSessionFactory() throws IOException {
    // 利用 Reader 加载 classpath 下的 mybatis-config.xml 核心配置文件
    Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
    // 初始化 SqlSessionFactory 对象，同时解析 mybatis-config.xml 文件
    SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(reader);
    System.out.println("SqlSessionFactory 加载成功");
    SqlSession ss = null;
    try {
      // 创建 SqlSession 对象， SqlSession 是 JDBC 的扩展类，用于与数据库交互
      ss = ssf.openSession();
      // 这不是必须的，只是用来展示，正常开发时，创建连接是 MyBatis 自动完成的。正常也不会有 "java.sql" 包的导入。
      Connection conn = ss.getConnection();
      System.out.println(conn);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (ss != null) {
        // 针对配置文件 mybatis-config.xml 的 <dataSource> 标签的 type
        // 如果 type="POOLED" ，代表使用连接池， close 则是将连接回收到连接池中
        // 如果 type="UNPOOLED" ，代表直连， close 则会调用 JDBC 的 Connection.close() 方法关闭连接
        ss.close();
      }
      System.out.println("结束");
    }
  }

  @Test
  public void testMyBatisUtils() {
    System.out.println("测试开始");
    // 创建 SqlSession
    SqlSession ss = MyBatisUtils.openSession();
    Connection conn = ss.getConnection();
    System.out.println(conn);
    // 关闭 SqlSession
    MyBatisUtils.closeSession(ss);
    System.out.println("测试结束");
  }


  @Test
  public void testSelectAll() throws Exception {
    SqlSession ss = null;
    try {
      ss = MyBatisUtils.openSession();
      // 将查询的每条结果都放入 Goods 类中， selectList 参数是"命名空间.查询语句Id" ，返回结果是 List<?>
      List<Goods> goodsList = ss.selectList("goods.selectAll");
      // 输出结果
      for (Goods goods : goodsList) {
        System.out.println(goods);
      }
    } catch (Exception e) {
      throw e;
    } finally {
      if (ss != null) {
        MyBatisUtils.closeSession(ss);
      }
    }
  }

  @Test
  public void testSelectById() throws Exception {
    SqlSession ss = null;
    try {
      ss = MyBatisUtils.openSession();
      Goods goods = ss.selectOne("goods.selectById", 1111);
      System.out.println(goods);
    } catch (Exception e) {
      throw e;
    } finally {
      if (ss != null) {
        MyBatisUtils.closeSession(ss);
      }
    }
  }

  @Test
  public void testSelectByPriceRange() throws Exception {
    SqlSession ss = null;
    try {
      ss = MyBatisUtils.openSession();
      Map param = new HashMap();
      param.put("min", 0);
      param.put("max", 1000);
      param.put("limit", 10);
      List<Goods> goodsList = ss.selectList("goods.selectByPriceRange", param);
      for (Goods goods : goodsList) {
        System.out.println(goods);
      }
    } catch (Exception e) {
      throw e;
    } finally {
      if (ss != null) {
        MyBatisUtils.closeSession(ss);
      }
    }
  }


  @Test
  public void testSelectGoodsMap() throws Exception {
    SqlSession ss = null;
    try {
      ss = MyBatisUtils.openSession();
      List<Map> list = ss.selectList("goods.selectGoodsMap");
      for (Map map : list) {
        System.out.println(map);
        System.out.println();
      }
    } catch (Exception e) {
      throw e;
    } finally {
      if (ss != null) {
        MyBatisUtils.closeSession(ss);
      }
    }
  }


  @Test
  public void testSelectGoodsDto() throws Exception {
    SqlSession ss = null;
    try {
      ss = MyBatisUtils.openSession();
      List<GoodsDto> list = ss.selectList("goods.selectGoodsDto");
      for (GoodsDto item : list) {
        System.out.println(item);
      }
    } catch (Exception e) {
      throw e;
    } finally {
      if (ss != null) {
        MyBatisUtils.closeSession(ss);
      }
    }
  }


  @Test
  public void testInsertCategory() throws Exception {
    SqlSession ss = null;
    try {
      ss = MyBatisUtils.openSession();
      // 使用 insert 方法
      // 创建实体类对象
      Category cg = new Category();
      cg.setCategoryLevel(1);
      cg.setCategoryName("我的自定义分类");
      cg.setCategoryOrder(0);
      int sucCount = ss.insert("category.categoryInsert", cg);
      // 提交事务
      ss.commit();
      System.out.println("成功插入：" + sucCount + "条");
      // categoryId 的值由 <resultKey> 标签提供
      System.out.println("最后的 AUTO_INCREMENT 的值：" + cg.getCategoryId());
    } catch (Exception e) {
      // 若发生错误，回滚事务
      if (ss != null) {
        ss.rollback();
      }
      throw e;
    } finally {
      if (ss != null) {
        MyBatisUtils.closeSession(ss);
      }
    }
  }

  @Test
  public void testUpdateCategory() throws Exception {
    SqlSession ss = null;
    try {
      ss = MyBatisUtils.openSession();
      // 首先我们需要把原有的值查询，放入到实体类对象中
      Category cg = ss.selectOne("category.categorySearchById", 61);
      if (cg == null) {
        System.out.println("没有查询结果，不需要更新！");
        return;
      }
      // 修改实体类对象
      cg.setCategoryName("被修改的分类");
      // 将实体类对象的值更新到数据库
      int updateCount = ss.update("category.categoryUpdate", cg);
      System.out.println("成功更新" + updateCount + "条数据");
      // 提交事务
      ss.commit();
    } catch (Exception e) {
      // 若发生错误，回滚事务
      if (ss != null) {
        ss.rollback();
      }
      throw e;
    } finally {
      if (ss != null) {
        MyBatisUtils.closeSession(ss);
      }
    }
  }

  @Test
  public void testDeleteCategory() throws Exception {
    SqlSession ss = null;
    try {
      ss = MyBatisUtils.openSession();
      int delCount = ss.update("category.categoryUpdate", 59);
      System.out.println("");
      System.out.println("成功删除" + delCount + "条数据");
      // 提交事务
      ss.commit();
    } catch (Exception e) {
      // 若发生错误，回滚事务
      if (ss != null) {
        ss.rollback();
      }
      throw e;
    } finally {
      if (ss != null) {
        MyBatisUtils.closeSession(ss);
      }
    }
  }

  @Test
  public void testSelectOneToMany() throws Exception {
    SqlSession ss = null;
    try {
      ss = MyBatisUtils.openSession();
      List<Goods> goodsList = ss.selectList("goods.selectOneToMany");
      System.out.println(goodsList);
    } catch (Exception e) {
      throw e;
    } finally {
      if (ss != null) {
        MyBatisUtils.closeSession(ss);
      }
    }
  }


  @Test
  public void testPageHelper() throws Exception {
    SqlSession ss = null;
    try {
      ss = MyBatisUtils.openSession();
      // 配置分页的起始页(由1开始)和大小。
      PageHelper.startPage(1, 10);
      // 与之前不同，经过 PageHelper 处理后，返回的是一个 Page 对象。
      Page<Goods> page = (Page) ss.selectList("goods.selectAll");
      // 读取数据
      System.out.println("总页数：" + page.getPages());
      System.out.println("总记录数：" + page.getTotal());
      System.out.println("开始行号：" + page.getStartRow());
      System.out.println("结束行号：" + page.getEndRow());
      System.out.println("当前页码：" + page.getPageNum());
      // 读取查询到的数据
      List<Goods> goodsList = page.getResult();
      for (Goods goods : goodsList) {
        System.out.println(goods);
      }
    } catch (Exception e) {
      throw e;
    } finally {
      if (ss != null) {
        MyBatisUtils.closeSession(ss);
      }
    }
  }
}
