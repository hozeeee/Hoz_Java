package demo_6;

import java.util.*;

// 复杂类型的属性注入
public class ExampleObj5 {
  // 数组
  private String[] arrs;
  // 列表
  private List<String> list;
  // Set 集合
  private Set<String> set;
  // Map 集合
  private Map<String, String> map;

  @Override
  public String toString() {
    return "ExampleObj5{" +
            "arrs=" + Arrays.toString(arrs) +
            ", list=" + list +
            ", set=" + set +
            ", map=" + map +
            ", properties=" + properties +
            '}';
  }

  public void setArrs(String[] arrs) {
    this.arrs = arrs;
  }

  public void setList(List<String> list) {
    this.list = list;
  }

  public void setSet(Set<String> set) {
    this.set = set;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  // 属性类型
  private Properties properties;

}
