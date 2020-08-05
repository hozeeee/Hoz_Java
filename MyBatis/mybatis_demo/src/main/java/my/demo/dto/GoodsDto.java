package my.demo.dto;

import my.demo.entity.Goods;

// 这种类是针对实体类的扩展
public class GoodsDto {
  // 创建实体类的对象
  private Goods goods = new Goods();
  // 其他扩展的属性
  private Boolean isFreedDelivery;
  private String categoryName;  // 从 t_category 表中获取

  @Override
  public String toString() {
    return goods.toString() + "_" + isFreedDelivery + "_" + categoryName;
  }

  // 快捷生成 Getter 和 Setter ....

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public Goods getGoods() {
    return goods;
  }

  public void setGoods(Goods goods) {
    this.goods = goods;
  }

  public Boolean getIsFreedDelivery() {
    return isFreedDelivery;
  }

  public void setIsFreedDelivery(Integer isFreedDelivery) {
    if (isFreedDelivery == 1) this.isFreedDelivery = true;
    else this.isFreedDelivery = false;
  }
}
