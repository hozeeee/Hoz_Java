package demo.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateConverter implements Converter<String, Date> {
  public Date convert(String s) {
    // 使用 SimpleDateFormat 将特定格式的字符串转化成日期对象
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = null;
    try {
      date = sdf.parse(s);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }
}
