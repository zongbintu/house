package com.tu.network.converter;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author Tu enum@foxmail.com.
 */
public class StringResponseBodyConverter implements Converter<ResponseBody, String> {

  @Override
  public String convert(ResponseBody value) throws IOException {
    try {
      return value.toString();
    } finally {
      value.close();
    }
  }
}
