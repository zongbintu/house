package com.tu.house.api;

import com.tu.house.model.Poi;
import com.tu.house.model.response.BaiduResult;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author Tu enum@foxmail.com.
 */
public interface LagouApi {
//Referer: https://www.lagou.com/gongsi/
//Cookie: user_trace_token=20171103153545-9ac04ec7-c069-11e7-9704-5254005c3644; LGUID=20171103153545-9ac05222-c069-11e7-9704-5254005c3644; _ga=GA1.2.1192990198.1509694546; index_location_city=%E5%85%A8%E5%9B%BD; JSESSIONID=ABAAABAABEEAAJAC45FD51A13073CC46E4F42822968D2ED; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1520560137,1521186320,1522050245; TG-TRACK-CODE=hpage_code; _gid=GA1.2.1663466745.1522201685; LGSID=20180328094804-0e546a16-322a-11e8-b652-5254005c3644; PRE_UTM=; PRE_HOST=; PRE_SITE=; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2F; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1522201700; LGRID=20180328094818-16e13613-322a-11e8-b652-5254005c3644

  @POST("https://www.lagou.com/gongsi/5-0-0.json")
  @FormUrlEncoded
  @Headers({"Host: www.lagou.com",
      "Connection: keep-alive",
      "Pragma: no-cache",
      "Cache-Control: no-cache",
      "Origin: https://www.lagou.com",
      "X-Anit-Forge-Code: 0",
      "Upgrade-Insecure-Requests: 1",
      "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36",
      "Content-Type: application/x-www-form-urlencoded; charset=UTF-8",
      "Accept: application/json, text/javascript, */*; q=0.01",
      "X-Requested-With: XMLHttpRequest",
      "X-Anit-Forge-Token: None",
      "DNT: 1",
      "Referer: https://www.lagou.com/gongsi/5-0-0",
      "Accept-Encoding: gzip, deflate, br",
      "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8"
  })
  Call<BaiduResult<List<Poi>>> gongsiList(@Field("first") String first, @Field("pn") int pn, @Field("sortField") int sortField,
      @Field("havemark") int havemark);
}
