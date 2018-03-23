package com.tu.house.api;

import com.tu.house.model.response.StatsResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Tu enum@foxmail.com.
 */
public interface SaleAreaApi {

  //http://data.stats.gov.cn/tablequery.htm?m=QueryData&code=AA130Q&wds=[{"wdcode":"sj","valuecode":"201701"}]
  @GET("tablequery.htm")
  Call<StatsResult> listSaleAreas(@Query("m") String m, @Query("code") String code, @Query("wds") String wds);
}
