package my.demo.entity;

public class GoodsDetail {
  private Integer gdId;
  private Integer goodsId;
  private String gdPicUrl;
  private Integer gdOrder;

  // 快速生成 Getter 和 Setter ....

  public Integer getGdId() {
    return gdId;
  }

  public void setGdId(Integer gdId) {
    this.gdId = gdId;
  }

  public Integer getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
  }

  public String getGdPicUrl() {
    return gdPicUrl;
  }

  public void setGdPicUrl(String gdPicUrl) {
    this.gdPicUrl = gdPicUrl;
  }

  public Integer getGdOrder() {
    return gdOrder;
  }

  public void setGdOrder(Integer gdOrder) {
    this.gdOrder = gdOrder;
  }
}
