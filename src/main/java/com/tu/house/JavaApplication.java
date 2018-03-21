package com.tu.house;

import com.tu.house.common.baidu.ResponseStatusEnum;
import com.tu.house.model.Poi;
import com.tu.house.model.response.BaiduResult;
import com.tu.house.service.HouseServiceImpl;
import com.tu.house.service.IHouseService;
import com.tu.house.service.MapBaiduServiceImpl;
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

    MapBaiduServiceImpl baiduService = new MapBaiduServiceImpl();

    List<Poi> poiList = new ArrayList<>();

    int pageNum = 0;
    BaiduResult<List<Poi>> result = baiduService.poi("yourak","yoursk","母婴", "沙坪坝区", 0);
    while (true) {
      if (result.getStatus() == ResponseStatusEnum.OK.getCode()) {
        if(!ObjectUtils.isEmpty(result.getResults())) {
          poiList.addAll(result.getResults());
        }
        if (result.getTotal() > 0) {
          result = baiduService.poi("yourak","yoursk","母婴", "沙坪坝区", ++pageNum);
        } else {
          break;
        }
      }
    }
    logger.info("result:{}",poiList);
  }
}
