package my.demo.entity;

import java.util.List;

public class Goods {
  // Java 的变量命名必须是驼峰式命名
  private Integer goodsId;
  private String title;
  private Float discount;

  // 一对多的关系
  private List<GoodsDetail> goodsDetails;


  @Override
  public String toString() {
    return this.goodsId + "_" + this.title + "_" + this.discount;
  }
  // 自动生成 Getter 和 Setter ....

  public Integer getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Float getDiscount() {
    return discount;
  }

  public void setDiscount(Float discount) {
    this.discount = discount;
  }


  public List<GoodsDetail> getGoodsDetails() {
    return goodsDetails;
  }

  public void setGoodsDetails(List<GoodsDetail> goodsDetails) {
    this.goodsDetails = goodsDetails;
  }
}
