package com.tu.house;

import com.tu.house.common.Constants;
import com.tu.house.common.baidu.ResponseStatusEnum;
import com.tu.house.model.Poi;
import com.tu.house.model.response.BaiduResult;
import com.tu.house.service.HouseServiceImpl;
import com.tu.house.service.IHouseService;
import com.tu.house.service.MapBaiduServiceImpl;
import com.tu.poi.transform.BeanWrapperFieldExtractor;
import com.tu.poi.transform.PoiWriter;
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

    try {
      File templateFile = new File("src/main/resources/static/baby_map.xls");
      File targetFile = new File("src/main/resources/static/baby_map" + System.currentTimeMillis() + ".xls");
      Path targetFilePath = Files.copy(templateFile.toPath(), targetFile.toPath());

      BeanWrapperFieldExtractor extractor = new BeanWrapperFieldExtractor();
      extractor.setNames(new String[]{"name", "brandMark", "province", "city", "area", "address", "lng", "lat", "telephone", "detail", "uid"});
      PoiWriter writer = new PoiWriter(targetFilePath.toString(), extractor);

      MapBaiduServiceImpl baiduService = new MapBaiduServiceImpl();

      String[] areas = new String[]{"沙坪坝区", "渝北区", "重庆市江北区", "渝中区", "九龙坡区", "南岸区", "北碚区", "大渡口区"};

      for (String area : areas) {

        List<Poi> poiList = new ArrayList<>();

        int pageNum = 0;
        BaiduResult<List<Poi>> result = baiduService.poi(Constants.MAP_AK, Constants.MAP_SK, "母婴", area, 0);
        while (true) {
          if (result.getStatus() == ResponseStatusEnum.OK.getCode()) {
            if (!ObjectUtils.isEmpty(result.getResults())) {
              poiList.addAll(result.getResults());
            }
            if (result.getTotal() > 0) {
              result = baiduService.poi(Constants.MAP_AK, Constants.MAP_SK, "母婴", area, ++pageNum);
            } else {
              break;
            }
          }
        }
        logger.info("result:{}", poiList);

        if (!ObjectUtils.isEmpty(poiList)) {
          writer.write(baiduService.convertPoi(poiList, area));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
