package my.demo.dao;

import my.demo.dto.GoodsDto;
import my.demo.entity.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 注意！这里是接口类型！
public interface GoodsDAO {

  // @Select() 注解参数内，与我们之前在 XML 文件写的查询语句是一摸一样的
  // 查询方法的参数中，需要用注解 @Param() 的参数中，说明与查询语句中的哪个字段对应。例子中用 "_" 区分，实际开发中使用同名即可。
  // 方法中也已经能说明查询的返回类型，例子中就是 List<Goods> 。
  @Select("SELECT * FROM t_goods WHERE current_price BETWEEN #{min_} AND #{max_} ORDER BY current_price LIMIT 0,#{limit_}")
  public List<Goods> selectByPriceRange(
          @Param("min_") Float min,
          @Param("max_") Float max,
          @Param("limit_") Integer limit
  );


  // 新增的例子
  @Insert("INSERT t_goods VALUES(null, #{title}, #{subTitle}, #{originalCost}, #{currentPrice}, #{discount}, #{isFreeDelivery}, #{categoryId})")
  // 等同于 XML 中内嵌的 <selectKey>
  @SelectKey(statement = "SELECT last_insert_id()", before = false, keyProperty = "goodId", resultType = Integer.class)
  public int insertOneGoods(Goods goods);

  /**
   * 上述接口等效于如下 XML ：
   * <insert id="????" parameterType="my.demo.entity.Goods">
   *  INSERT t_goods VALUES(null, #{title}, #{subTitle}, #{originalCost}, #{currentPrice}, #{discount}, #{isFreeDelivery}, #{categoryId})
   *  <selectKey resultType="Integer" keyProperty="categoryId" order="AFTER">
   *    SELECT last_insert_id();
   *  </selectKey>
   * </insert>
   */


  // 映射的例子
  @Select("SELECT * FROM t_goods")
  @Results({
          @Result(column = "goods_id", property = "goodsId", id = true),
          @Result(column = "title", property = "title"),
          @Result(column = "sub_title", property = "subTitle")
  })
  public List<GoodsDto> selectGoodsDtoAll();
  /**  上面接口等效于如下 XML :
   * <resultMap id="******" type="my.demo.entity.GoodsDto">
   *   <id property="goodsId" column="goods_id"/>
   *   <result property="title" column="title"/>
   *   <result property="subTitle" column="sub_title"/>
   * </resultMap>
   * <select id="????" resultMap="******">
   *   SELECT * FROM t_goods
   * </select>
   */

}
