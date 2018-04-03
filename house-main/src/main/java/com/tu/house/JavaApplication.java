package com.tu.house;

import com.tu.house.common.Constants;
import com.tu.house.common.baidu.ResponseStatusEnum;
import com.tu.house.model.Poi;
import com.tu.house.model.Village;
import com.tu.house.model.response.BaiduResult;
import com.tu.house.service.HouseServiceImpl;
import com.tu.house.service.IHouseService;
import com.tu.house.service.LagouService;
import com.tu.house.service.LagouServiceImpl;
import com.tu.house.service.MapBaiduServiceImpl;
import com.tu.poi.PoiWriter;
import com.tu.poi.transform.BeanWrapperFieldExtractor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

/**
 * @author Tu enum@foxmail.com.
 */
public class JavaApplication {

  private static final Logger logger = LoggerFactory.getLogger(JavaApplication.class);


  public static void main(String[] args) {
    IHouseService houseService = new HouseServiceImpl();
//    houseService.getHouse();

//    houseService.getSaleArea("201702","201712");

//    try {
//      File templateFile = new File("templates/baby_map.xls");
//      File targetFile = new File("templates/baby_map" + System.currentTimeMillis() + ".xls");
//      Path targetFilePath = Files.copy(templateFile.toPath(), targetFile.toPath());
//
//      BeanWrapperFieldExtractor extractor = new BeanWrapperFieldExtractor();
//      extractor.setNames(new String[]{"name", "brandMark", "province", "city", "area", "address", "lng", "lat", "telephone", "detail", "uid"});
//      PoiWriter writer = new PoiWriter(targetFilePath.toString(), extractor);
//
//      MapBaiduServiceImpl baiduService = new MapBaiduServiceImpl();
//
//      String[] areas = new String[]{"沙坪坝区", "渝北区", "重庆市江北区", "渝中区", "九龙坡区", "南岸区", "北碚区", "大渡口区"};
//
//      String keyword = "母婴";
//
//      for (String area : areas) {
//
//        List<Poi> poiList = new ArrayList<>();
//
//        int pageNum = 0;
//        BaiduResult<List<Poi>> result = baiduService.poi(Constants.MAP_AK, Constants.MAP_SK, keyword, area, 0);
//        while (true) {
//          if (result.getStatus() == ResponseStatusEnum.OK.getCode()) {
//            if (!ObjectUtils.isEmpty(result.getResults())) {
//              poiList.addAll(result.getResults());
//            }
//            if (result.getTotal() > 0) {
//              result = baiduService.poi(Constants.MAP_AK, Constants.MAP_SK, keyword, area, ++pageNum);
//            } else {
//              break;
//            }
//          }
//        }
//        logger.info("result:{}", poiList);
//
//        if (!ObjectUtils.isEmpty(poiList)) {
//          writer.writeExcel(baiduService.convertPoi(poiList, area));
//        }
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

    LagouService lagouService = new LagouServiceImpl();
    lagouService.companies("user_trace_token=20171103153545-9ac04ec7-c069-11e7-9704-5254005c3644; LGUID=20171103153545-9ac05222-c069-11e7-9704-5254005c3644; _ga=GA1.2.1192990198.1509694546; index_location_city=%E5%85%A8%E5%9B%BD; JSESSIONID=ABAAABAABEEAAJAB0D71992925D3D7F0A9DEFA68F0948D9; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1520560137,1521186320,1522050245,1522636160; _gid=GA1.2.1709160403.1522749141; TG-TRACK-CODE=index_company; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1522749148; LGRID=20180403175227-b7b08b1f-3724-11e8-b275-525400f775ce");

  }
}
