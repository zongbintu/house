package com.tu.house.service;

import com.tu.house.api.LagouApi;
import com.tu.house.model.dto.CompanyDto;
import com.tu.house.model.lagou.GongSi;
import com.tu.house.model.response.LagouResult;
import com.tu.poi.PoiWriter;
import com.tu.poi.transform.BeanWrapperFieldExtractor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Tu enum@foxmail.com.
 */
public class LagouServiceImpl implements LagouService {

  private static final Logger logger = LoggerFactory.getLogger(LagouServiceImpl.class);

  @Override
  public List<CompanyDto> companies(String cookie) {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).addInterceptor(new Interceptor() {
      @Override
      public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
            .newBuilder()
            .addHeader("Cookie", cookie)
            .build();
        return chain.proceed(request);
      }
    }).build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://www.lagou.com/").addConverterFactory(GsonConverterFactory.create()).client(httpClient)
        .build();

    LagouApi api = retrofit.create(LagouApi.class);

    int sortField = 0;
    int havemark = 0;
    int pageSize = 16;
    Integer totalCount;
    int pageCount = 1;

    List<GongSi> companies = new ArrayList<>();
    for (int pageNo = 1; pageNo <= pageCount; pageNo++) {
      Call<LagouResult<GongSi>> call = api.gongsiList("false", pageNo, sortField, havemark);
      try {
        Response<LagouResult<GongSi>> response = call.execute();
        if (response.isSuccessful()) {
          try {
            totalCount = Integer.parseInt(response.body().getTotalCount());
            pageCount = totalCount % 16 == 0 ? totalCount / 16 : totalCount / 16 + 1;
          } catch (NumberFormatException e) {
            logger.error("total count error", e);
          }
          if (null != response.body().getResult() && !response.body().getResult().isEmpty()) {
            companies.addAll(response.body().getResult());
          } else {
            logger.info("第{}页数据未返回", pageNo);
            break;
          }
        }
      } catch (IOException e) {
        logger.error("请求失败", e);
      }
    }
    logger.info("-----companies----{}", companies);

    File templateFile = new File("templates/lagou_company_template.xlsx");
    File targetFile = new File("templates/lagou_company_" + System.currentTimeMillis() + ".xlsx");
    Path targetFilePath = null;
    try {
      targetFilePath = Files.copy(templateFile.toPath(), targetFile.toPath());
      BeanWrapperFieldExtractor extractor = new BeanWrapperFieldExtractor();
      extractor.setNames(new String[]{"companyId", "companyFullName", "companyShortName", "companyLogo", "city", "industryField", "companyFeatures",
          "financeStage"});
      PoiWriter writer = new PoiWriter(targetFilePath.toString(), extractor);
      writer.write(companies);
    } catch (IOException e) {
      logger.error(MessageFormat.format("copy file error,source:{0},target:{1}", templateFile.getPath(), targetFile.getPath()), e);
    }

    return null;
  }
}
