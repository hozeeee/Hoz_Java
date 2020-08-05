import com.alibaba.fastjson.JSON;
import org.w3c.dom.ls.LSInput;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CacheSimple {
  public static void main(String[] args) {
    Jedis jedis = new Jedis("localhost");
    try {
      jedis.auth("root");
      jedis.select(3);

      // 先实例化几个对象作为数据
      Student stud1 = new Student(1, "zhangsan", 12, 65.32f);
      Student stud2 = new Student(2, "lisi", 11, 88.88f);
      Student stud3 = new Student(3, "zhaowu", 16, 55.65f);
      List<Student> studList = new ArrayList<Student>();
      studList.add(stud1);
      studList.add(stud2);
      studList.add(stud3);

      // 将数据格式化成 JSON ，并存到 Redis
      for (Student stud : studList) {
        String studJson = JSON.toJSONString(stud);
        System.out.println(studJson);
        // 存到 Redis
        String key = "student:" + stud.getId();
        jedis.set(key, studJson);
      }
      System.out.println("-----------------------------");

      // 获取 JSON ，并转成 对象
      jedis.select(3);  // 假设此处的jedis与前面不同,则需要重新选择数据
      String inputId = "2"; // 假设以id查询,输入id的步骤省略
      String key = "student:" + inputId;
      if (jedis.exists(key)) {
        String json = jedis.get(key);
        Student stud = JSON.parseObject(json, Student.class);
        System.out.println("查询到学生：" + stud.getName());
      } else {
        System.out.println("不存在该编号的学生");
      }


    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jedis.close();
    }
  }
}
