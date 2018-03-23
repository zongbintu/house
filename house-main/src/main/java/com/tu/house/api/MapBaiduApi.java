package com.tu.house.api;

import com.tu.house.model.Poi;
import com.tu.house.model.response.BaiduResult;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Tu enum@foxmail.com.
 */
public interface MapBaiduApi {

  /**
   * poi
   *
   * @param query 检索关键字
   * @param tag 检索分类 可选
   * @param region 检索行政区划区域
   * @param city_limit 区域数据召回限制，为true时，仅召回region对应区域内数据
   * @param output 输出格式为json或者xml
   * @param page_num 页码，0开始
   * @param page_size 页数，最大20
   * @param timestamp 时间戳
   * @param ak 开发者的访问密钥
   * @param sn 开发者的权限签名
   */
  @GET("place/v2/search")
  Call<BaiduResult<List<Poi>>> poi(@Query("query") String query, @Query("tag") String tag, @Query("region") String region,
      @Query("city_limit") String city_limit,
      @Query("output") String output, @Query("page_num") int page_num, @Query("page_size") int page_size, @Query("timestamp") String timestamp,
      @Query("ak") String ak, @Query("sn") String sn);
}
