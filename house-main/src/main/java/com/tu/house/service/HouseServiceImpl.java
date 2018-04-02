package com.tu.house.service;

import com.google.gson.GsonBuilder;
import com.tu.house.api.SaleAreaApi;
import com.tu.house.common.Constants;
import com.tu.house.model.House;
import com.tu.house.model.SaleArea;
import com.tu.house.model.Village;
import com.tu.house.model.request.SaleAreaRequest;
import com.tu.house.model.response.StatsExcel;
import com.tu.house.model.response.StatsResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
public class HouseServiceImpl implements IHouseService {

  private static final Logger logger = LoggerFactory.getLogger(HouseServiceImpl.class);

  @Override
  public String getHouse(String... args) {
    for (int i = 1; i <= 22; i++) {
      String fileName = saveHtml(Constants.URL_LIANJIA, "/ershoufang/renhe/pg" + i + "/", "house");
      List<House> houses = parseHtml(fileName);
      writeExcel("src/main/resources/static/lianjia.xls", houses);
    }

    return null;
  }

  public String getXiaoqu() {
    String dir = String.valueOf(System.currentTimeMillis());
    for (int i = 1; i <= 16; i++) {
      String fileName = saveHtml(Constants.URL_LIANJIA, "/xiaoqu/shapingba/pg" + i + "/", dir);
//      List<Village> villages = parseVillageHtml(fileName);
//      writeExcel("src/main/resources/static/lianjia.xls", villages);
    }

    return null;
  }

  public List<Village> paseXiaoqu(String dir) {
    File villageDir = new File(dir);
    List<String> urls = new ArrayList<>();
    if (villageDir.exists()) {
      for (File file : villageDir.listFiles()
          ) {
        urls.addAll(parseVillageHtml(file.getPath()));
      }

    }
    return getVillageDetail(urls);
  }

  public List<Village> getVillageDetail(List<String> urls) {
    List<Village> details = new ArrayList<>(urls.size());
    for (String url : urls
        ) {
      details.add(parseVillageDetail(url));
    }
    return details;
  }

  @Override
  public String getSaleArea(String startDate, String endDate) {
    DateTime current = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMM"));
    final DateTime endTime = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyyMM"));

    while (!current.isAfter(endTime)) {
      List<SaleArea> list = new ArrayList<>();
      list.addAll(getSaleArea(current));
      writeExcelSaleArea("src/main/resources/static/sale_area.xls", list);
      current = current.plusMonths(1);
    }
    return null;
  }

  public String writeExcelSaleArea(String fileName, List<SaleArea> saleAreas) {
    logger.info("文件{}开始写入", fileName);
    try (POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName))) {
      Workbook workbook = new HSSFWorkbook(fs);
      HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
      final int preLastRowNum = sheet.getLastRowNum();
      logger.info("文件{},上次行数为{}", fileName, preLastRowNum);
      for (int i = 0; i < saleAreas.size(); i++) {
        Row row = sheet.createRow(preLastRowNum + i + 1);
        SaleArea data = saleAreas.get(i);
        row.createCell(0).setCellValue(preLastRowNum + i + 1);
        row.createCell(1).setCellValue(data.getRegion());
        row.createCell(2).setCellValue(data.getAcreage());
        row.createCell(3).setCellValue(data.getExist());
        row.createCell(4).setCellValue(data.getForwardDelivery());
        row.createCell(5).setCellValue(data.getDate());
      }
      FileOutputStream fileOut = new FileOutputStream(fileName);
      workbook.write(fileOut);
      IOUtils.closeQuietly(fileOut);
      logger.info("文件{}写入完成,{}-{}行", fileName, preLastRowNum + 1, preLastRowNum + saleAreas.size());
    } catch (IOException e) {
      logger.error("excel-{}处理出错", fileName, e);
    }

    return fileName;
  }

  public List<SaleArea> getSaleArea(DateTime date) {
    List<SaleArea> list = new ArrayList<>();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://data.stats.gov.cn/").addConverterFactory(GsonConverterFactory.create())
        .build();

    SaleAreaApi api = retrofit.create(SaleAreaApi.class);
    final String dateTime = date.toString("yyyyMM");
    List<SaleAreaRequest> saleAreaRequests = new ArrayList<>(1);
    saleAreaRequests.add(new SaleAreaRequest("sj", dateTime));
    Call<StatsResult> call = api.listSaleAreas("QueryData", "AA130Q", new GsonBuilder().create().toJson(saleAreaRequests));
    try {
      Response<StatsResult> response = call.execute();
      if (response.isSuccessful()) {
        List<StatsExcel> statsExcels = response.body().getExceltable();
        list.addAll(convert(statsExcels, dateTime));
      }
    } catch (IOException e) {
      logger.error("请求失败", e);
    }

    return list;
  }

  private List<SaleArea> convert(List<StatsExcel> statsExcels, String date) {
    statsExcels = statsExcels.subList(5, statsExcels.size());
    List<SaleArea> saleAreas = new ArrayList<>();
    for (int i = 0; i < statsExcels.size() / 4; i++) {
      SaleArea tempSaleArea = new SaleArea();
      tempSaleArea.setDate(date);
      tempSaleArea.setRegion(statsExcels.get(i * 4).getData());
      tempSaleArea.setAcreage(Double.valueOf(statsExcels.get(i * 4 + 1).getData()));
      tempSaleArea.setExist(Double.valueOf(statsExcels.get(i * 4 + 2).getData()));
      tempSaleArea.setForwardDelivery(Double.valueOf(statsExcels.get(i * 4 + 3).getData()));
      saleAreas.add(tempSaleArea);
    }
    return saleAreas;
  }


  public String saveHtml(String domain, String uri, String dir) {
    FileOutputStream out = null;
    File dest = new File("src/temp_html/" + dir + "/" + (uri.endsWith("/") ? uri.substring(0, uri.length() - 1) : uri) + ".html");
    try {
      Document document = Jsoup.connect(domain + uri)
          .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
          .timeout(30000).get();

      if (!dest.getParentFile().exists()) {
        dest.getParentFile().mkdirs();
      }

      if (!dest.exists()) {
        dest.createNewFile();
      }
      out = new FileOutputStream(dest, false);
      out.write(document.toString().getBytes("UTF-8"));
      out.close();


    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
      IOUtils.closeQuietly(out);
    }

    return dest.getPath();
  }

  public List<String> parseVillageHtml(String fileName) {
    List<String> urls = new ArrayList<>();
    //读取本地html的路径
    File file = new File(fileName);
    //写个循环读取这些文件的名字
    try {
      if (file.isFile()) {
        logger.info("文件{}开始解析", fileName);

        //下面开始解析本地的html
        Document doc = Jsoup.parse(file, "UTF-8");
        Elements listContents = doc.select("ul.listContent");
        if (!ObjectUtils.isEmpty(listContents)) {
          Element listContent = listContents.first();
          Elements infoContents = listContent.select("div.info");
          if (!ObjectUtils.isEmpty(infoContents)) {
            for (int i = 0; i < infoContents.size(); i++) {

              Element infoElement = infoContents.get(i);
              Element titleElement = infoElement.select("div.title").first().select("a[href]").first();
              String url = titleElement.attr("href");
              urls.add(url);
              logger.info("解析第{}个元素，结果为：{}", i, url);
            }
          }
        }
      }
    } catch (Exception e) {
      logger.error("文件{}解析错误", fileName, e);
    }
    return urls;
  }

  public Village parseVillageDetail(String url) {
    Village village = new Village();
    //写个循环读取这些文件的名字
    try {
      logger.info("开始解析{}", url);

      Document doc = Jsoup.connect(url)
          .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
          .timeout(30000).get();

      village.setUrl(url);

      Element bredElement = doc.selectFirst("div.xiaoquDetailbreadCrumbs").selectFirst("div.fl");
      Elements districtElements = bredElement.select("a[href]");
      if (null != districtElements && districtElements.size() >= 4) {
        village.setCity(districtElements.get(1).text().replace("小区", ""));
        village.setDistrict(districtElements.get(2).text().replace("小区", ""));
        village.setStreet(districtElements.get(3).text().replace("小区", ""));
      }

      Element headerElement = doc.selectFirst("div.detailHeader");
      village.setTitle(headerElement.selectFirst("h1.detailTitle").text());
      village.setAddress(headerElement.selectFirst("div.detailDesc").text());

      Element desElement = doc.select("div.xiaoquDescribe").first();

      Element priceElement = desElement.selectFirst("span.xiaoquUnitPrice");
      village.setPrice(null == priceElement ? "0" : desElement.selectFirst("span.xiaoquUnitPrice").text());
      Elements infoElements = desElement.select("div.xiaoquInfoItem");
      if (null != infoElements && infoElements.size() >= 7) {
        String age = "";
        try {
          age = infoElements.get(0).selectFirst("span.xiaoquInfoContent").text().replace("年建成", "");
          village.setAge(Integer.parseInt(age));
        } catch (NumberFormatException e) {
          logger.info("建筑年代\"{}\"解析错误", age);
        }
        village.setBuildType(infoElements.get(1).selectFirst("span.xiaoquInfoContent").text());
        village.setPropertyCost(infoElements.get(2).selectFirst("span.xiaoquInfoContent").text());
        village.setProperty(infoElements.get(3).selectFirst("span.xiaoquInfoContent").text());
        village.setBuilder(infoElements.get(4).selectFirst("span.xiaoquInfoContent").text());
        village.setBuildCount(Integer.valueOf(infoElements.get(5).selectFirst("span.xiaoquInfoContent").text().replace("栋", "")));
        village.setDoorCount(Integer.valueOf(infoElements.get(6).selectFirst("span.xiaoquInfoContent").text().replace("户", "")));

      }

      return village;
    } catch (Exception e) {
      logger.error("解析{}错误", url, e);
    }
    return null;
  }

  public List<House> parseHtml(String fileName) {
    List<House> houses = null;
    //读取本地html的路径
    File file = new File(fileName);
    //写个循环读取这些文件的名字
    try {
      if (file.isFile()) {
        logger.info("文件{}开始解析", fileName);

        //下面开始解析本地的html
        Document doc = Jsoup.parse(file, "UTF-8");
        Elements sellListContents = doc.select("ul.sellListContent");
        if (!ObjectUtils.isEmpty(sellListContents)) {
          Element sellListContent = sellListContents.first();
          Elements sellContents = sellListContent.select("li.clear");
          if (!ObjectUtils.isEmpty(sellListContents)) {
            houses = new ArrayList<>(sellContents.size());
            for (int i = 0; i < sellContents.size(); i++) {
              Element sellList = sellContents.get(i);
              Elements sellElements = sellList.select("div.clear");
              if (null != sellElements && sellElements.size() == 1) {
                House house = new House();
                Element infoElement = sellElements.first();

                Element titleElement = infoElement.select("div.title").first().select("a[href]").first();
                house.setTitle(titleElement.text());
                house.setUrl(titleElement.attr("href"));

                Element houseInfoElement = infoElement.selectFirst("div.houseInfo");
                house.setVillage(houseInfoElement.select("a").first().text());
                String houseInfo = houseInfoElement.text();
                String[] houseInfos = houseInfo.split("\\|");

                for (int j = 1; j < houseInfos.length; j++
                    ) {
                  switch (j) {
                    case 1:
                      house.setStructure(houseInfos[j].trim());
                      break;
                    case 2:
                      house.setAcreage(houseInfos[j].replace("平米", "").trim());
                      break;
                    case 3:
                      house.setOrientation(houseInfos[j].trim());
                      break;
                    case 4:
                      house.setDecoration(houseInfos[j].trim());
                      break;
                    case 5:
                      house.setElevator(houseInfos[j].trim());
                      break;
                  }
                }
                Element positionInfoElement = infoElement.selectFirst("div.positionInfo");
                house.setRegion(positionInfoElement.select("a").first().text());
                String position = positionInfoElement.text().split(house.getRegion())[0].trim();

                house
                    .setPosition(position.lastIndexOf("-") == position.length() - 1 ? position.substring(0, position.length() - 1).trim() : position);

                house.setTag(infoElement.selectFirst("div.tag").text());

                Element priceInfoElement = infoElement.selectFirst("div.priceInfo");
                house.setTotalPrice(priceInfoElement.selectFirst("div.totalPrice").text().replace("万", ""));

                house.setUnitPrice(priceInfoElement.selectFirst("div.unitPrice").text().replace("单价", "").replace("元/平米", "").trim());

                logger.info("解析第{}个元素，结果为：{}", i, house.toString());
                houses.add(house);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      logger.error("文件{}解析错误", fileName, e);
    }
    return houses;
  }

  public String writeExcel(String fileName, List<House> houses) {
    logger.info("文件{}开始写入", fileName);
    try (POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName))) {
      Workbook workbook = new HSSFWorkbook(fs);
      HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
      final int preLastRowNum = sheet.getLastRowNum();
      logger.info("文件{},上次行数为{}", fileName, preLastRowNum);
      for (int i = 0; i < houses.size(); i++) {
        Row row = sheet.createRow(preLastRowNum + i + 1);
        House house = houses.get(i);
        row.createCell(0).setCellValue(preLastRowNum + i + 1);
        row.createCell(1).setCellValue(house.getTitle());
        row.createCell(2).setCellValue(house.getTotalPrice());
        row.createCell(3).setCellValue(house.getUnitPrice());
        row.createCell(4).setCellValue(house.getVillage());
        row.createCell(5).setCellValue(house.getStructure());
        row.createCell(6).setCellValue(house.getAcreage());
        row.createCell(7).setCellValue(house.getOrientation());
        row.createCell(8).setCellValue(house.getDecoration());
        row.createCell(9).setCellValue(house.getElevator());
        row.createCell(10).setCellValue(house.getPosition());
        row.createCell(11).setCellValue(house.getRegion());
        row.createCell(12).setCellValue(house.getTag());
        row.createCell(13).setCellValue(house.getUrl());
      }
      FileOutputStream fileOut = new FileOutputStream(fileName);
      workbook.write(fileOut);
      IOUtils.closeQuietly(fileOut);
      logger.info("文件{}写入完成,{}-{}行", fileName, preLastRowNum + 1, preLastRowNum + houses.size());
    } catch (IOException e) {
      logger.error("excel-{}处理出错", fileName, e);
    }

    return fileName;
  }


}
