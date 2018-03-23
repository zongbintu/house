package com.tu.poi.transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

/**
 * @author Tu enum@foxmail.com.
 */
public class BeanWrapperFieldExtractor<T> implements FieldExtractor<T> {

  private String[] names;

  /**
   * @param names field names to be extracted by the {@link #extract(Object)} method.
   */
  public void setNames(String[] names) {
    Assert.notNull(names, "Names must be non-null");
    this.names = Arrays.asList(names).toArray(new String[names.length]);
  }

  @Override
  public Object[] extract(T item) {
    Assert.notNull(names, "Names must be non-null");
    Assert.notNull(item, "item must be non-null");
    List<Object> values = new ArrayList<Object>();

    BeanWrapper bw = new BeanWrapperImpl(item);
    for (String propertyName : this.names) {
      values.add(bw.getPropertyValue(propertyName));
    }
    return values.toArray();
  }
}
