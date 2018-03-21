package com.tu.house.service;

import com.tu.house.api.MapBaiduApi;
import com.tu.house.common.baidu.ResponseStatusEnum;
import com.tu.house.map.baidu.SnCal;
import com.tu.house.model.Poi;
import com.tu.house.model.response.BaiduResult;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        BaiduResult<List<Poi>> body = response.body();
        if (ResponseStatusEnum.OK.getCode() == body.getStatus() && !ObjectUtils.isEmpty(body.getResults())) {
          writeExcel("src/main/resources/static/baby_map.xls", body.getResults());
        }
        return body;
      }
    } catch (IOException e) {
      logger.error("请求失败", e);
    }
    return result;
  }


  public String writeExcel(String fileName, List<Poi> poiList) {
    logger.info("文件{}开始写入", fileName);
    try (POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName))) {
      Workbook workbook = new HSSFWorkbook(fs);
      HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
      final int preLastRowNum = sheet.getLastRowNum();
      logger.info("文件{},上次行数为{}", fileName, preLastRowNum);
      int index = 0;
      for (int i = 0; i < poiList.size(); i++) {
        Poi poi = poiList.get(i);
        if ("母婴室".equals(poi.getName())) {
          continue;
        }
        index++;
        Row row = sheet.createRow(preLastRowNum + index);
        row.createCell(0).setCellValue(preLastRowNum + index);
        row.createCell(1).setCellValue(poi.getName());
        row.createCell(2).setCellValue(poi.getProvince());
        row.createCell(3).setCellValue(poi.getCity());
        row.createCell(4).setCellValue(poi.getArea());
        row.createCell(5).setCellValue(poi.getAddress());
        row.createCell(6).setCellValue(poi.getTelephone());
        row.createCell(7).setCellValue(poi.getLocation().getLng());
        row.createCell(8).setCellValue(poi.getLocation().getLat());
        row.createCell(9).setCellValue(poi.getDetail());
        row.createCell(10).setCellValue(poi.getUid());
        row.createCell(11).setCellValue(trueName(poi.getName()));
      }
      FileOutputStream fileOut = new FileOutputStream(fileName);
      workbook.write(fileOut);
      IOUtils.closeQuietly(fileOut);
      logger.info("文件{}写入完成,{}-{}行", fileName, preLastRowNum + 1, preLastRowNum + index + 1);
    } catch (IOException e) {
      logger.error("excel-{}处理出错", fileName, e);
    }

    return fileName;
  }

  /**
   * 数据清洗
   */
  private String trueName(String name) {
    String[] keywords = {"爱亲", "孕婴世界", "中亿孕婴", "孩子王"
        , "孕婴坊", "圣婴孕婴", "可爱可亲", "宝妈时光"
        , "欧比母婴", "宝贝爱", "妈咪爱", "贝乐母婴", "孕婴房", "母婴坊", "囝囡孕婴", "孕婴之家", "喜来宝", "浩贝母婴", "天乐孕婴", "婴点母婴", "好孩子孕婴"
        , "贝乐家", "爱贝尚", "宝贝计划", "爱婴堂", "天乐孕婴", "婴点"};
    for (String keyword : keywords) {
      if (name.contains(keyword)) {
        return keyword;
      }
    }
    return name;
  }
}
