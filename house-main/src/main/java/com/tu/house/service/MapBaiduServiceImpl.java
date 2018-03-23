package com.tu.house.service;

import com.tu.house.api.MapBaiduApi;
import com.tu.house.common.baidu.ResponseStatusEnum;
import com.tu.house.map.baidu.SnCal;
import com.tu.house.model.Poi;
import com.tu.house.model.dto.PoiDto;
import com.tu.house.model.response.BaiduResult;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Tu enum@foxmail.com.
 */
public class MapBaiduServiceImpl {

  private static final Logger logger = LoggerFactory.getLogger(MapBaiduServiceImpl.class);

  public BaiduResult<List<Poi>> poi(String ak, String sk, String keyword, String region, int pageNum) {
    BaiduResult<List<Poi>> result = new BaiduResult<>();
    result.setStatus(ResponseStatusEnum.OK.getCode());

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://api.map.baidu.com/").addConverterFactory(GsonConverterFactory.create()).client(httpClient)
        .build();

    MapBaiduApi api = retrofit.create(MapBaiduApi.class);
    Map paramsMap = new LinkedHashMap<String, String>();
    paramsMap.put("query", keyword);
    paramsMap.put("tag", "");
    paramsMap.put("region", region);
    paramsMap.put("city_limit", "true");
    paramsMap.put("output", "json");
    paramsMap.put("page_num", String.valueOf(pageNum));
    paramsMap.put("page_size", String.valueOf(20));
    String timestamp = String.valueOf(System.currentTimeMillis());
    paramsMap.put("timestamp", timestamp);
    paramsMap.put("ak", ak);

    String sn = null;
    try {
      sn = SnCal.sn("/place/v2/search", sk, paramsMap);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    Call<BaiduResult<List<Poi>>> call = api.poi(keyword, "", region, "true", "json", pageNum, 20, timestamp, "1WGRy3qPjwL2cspBHT7VM8rsGANMrE03", sn);
    try {
      Response<BaiduResult<List<Poi>>> response = call.execute();
      if (response.isSuccessful()) {
        return response.body();
      }
    } catch (IOException e) {
      logger.error("请求失败", e);
    }
    return result;
  }

  public List<PoiDto> convertPoi(List<Poi> poiList, String area) {
    List<PoiDto> data = new ArrayList<>();
    if (!ObjectUtils.isEmpty(poiList)) {
      for (Poi poi : poiList) {
        PoiDto dto = new PoiDto();
        BeanUtils.copyProperties(poi, dto);
        String brandMark = brandMark(dto.getName());
        if (null == brandMark) {
          continue;
        }
        dto.setBrandMark(brandMark);
        dto.setLat(null != poi.getLocation() ? poi.getLocation().getLat() : null);
        dto.setLng(null != poi.getLocation() ? poi.getLocation().getLng() : null);

        dto.setArea(null == area ? dto.getArea() : area);
        data.add(dto);
      }
    }
    return data;
  }

  /**
   * 数据清洗
   */
  private String brandMark(String name) {
    List<String> notBaby = new ArrayList<>();
    notBaby.add("母婴室");
    notBaby.add("母婴哺乳室");
    notBaby.add("育婴哺乳室");

    if (notBaby.contains(name)) {
      return null;
    }
    String[] keywords = {"爱亲", "孕婴世界", "中亿孕婴", "孩子王"
        , "孕婴坊", "圣婴孕婴", "可爱可亲", "宝妈时光", "佳婴母婴"
        , "欧比母婴", "宝贝爱", "妈咪爱", "贝乐母婴", "孕婴房", "母婴坊", "囝囡孕婴", "孕婴之家", "喜来宝", "浩贝母婴", "天乐孕婴", "婴点母婴", "好孩子孕婴", "登康·好儿尚", "安妮宝贝", "宝宝乐母婴"
        , "贝乐家", "爱贝尚母婴", "宝贝计划", "爱婴堂", "天乐孕婴", "婴点", "妈咪宝贝", "全棉时代"};
    for (String keyword : keywords) {
      if (name.contains(keyword)) {
        return keyword;
      }
    }
    return name;
  }
}
