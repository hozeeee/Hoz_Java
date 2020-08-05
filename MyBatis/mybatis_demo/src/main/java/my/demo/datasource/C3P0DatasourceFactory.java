package my.demo.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

// 这个类是 C3P0 与 MyBatis 兼容使用的数据源工厂类
// 需要继承 UnpooledDataSourceFactory ，实现 C3P0 的迁入工作
public class C3P0DatasourceFactory extends UnpooledDataSourceFactory {
  // 构造函数
  public C3P0DatasourceFactory() {
    // this.dataSource 是由 UnpooledDataSourceFactory 提供
    // ComboPooledDataSource 由 C3P0 提供
    // 表示数据源由 C3P0 创建。如果之后需要使用其他数据源，只需要修改等号右边的内容即可。
    this.dataSource = new ComboPooledDataSource();
  }
}
