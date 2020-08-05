import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JedisTester {
  public static void main(String[] args) {
    // 实例化 （若端口为默认的6379,则第二个参数可以省略）
    Jedis jedis = new Jedis("localhost", 6379);
    try {
      // 授权密码
      jedis.auth("root");
      // 使用第 2 个数据库
      jedis.select(2);
      System.out.println("Redis连接成功");

      // 字符串
      jedis.set("num", "100");
      String num = jedis.get("num");
      System.out.println(num); // 100
      jedis.mset("myStr1", "foo1", "myStr2", "foo2", "myStr3", "foo3");
      List<String> myStrList = jedis.mget("myStr1", "myStr2", "myStr3");
      System.out.println(myStrList);  // [foo1, foo2, foo3]
      Long num2 = jedis.incr("num");
      System.out.println(num2);  // 101

      // hash
      jedis.hset("student:666", "name", "zhangsan");
      String studName = jedis.hget("student:666", "name");
      System.out.println(studName); // zhangsan
      Map<String, String> studMap = new HashMap();
      studMap.put("name", "lisi");
      studMap.put("age", "12");
      jedis.hmset("student:999", studMap);
      Map<String, String> studMapGet = jedis.hgetAll("student:999");
      System.out.println(studMapGet); // {age=12, name=lisi}

      // list
      jedis.del("letter");
      jedis.rpush("letter", "A", "B", "C");
      String popStr = jedis.rpop("letter"); // C
      System.out.println(popStr);
      List<String> list = jedis.lrange("letter", 0, -1);
      System.out.println(list);  // [A, B]

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jedis.close();
    }
  }
}
