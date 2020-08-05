import my.demo.dao.GoodsDAO;
import my.demo.entity.Goods;
import my.demo.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MyBatisTester {
  @Test
  public void testSelectByPriceRange() throws Exception {
    SqlSession ss = null;
    try {
      ss = MyBatisUtils.openSession();
      // 获取 Mapper
      GoodsDAO goodsDAO = ss.getMapper(GoodsDAO.class);
      // 使用 DAO 的查询方法
      List<Goods> result = goodsDAO.selectByPriceRange(100f, 500f, 20);
      for (Goods goods : result) {
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
