package com.tu.house;

import com.tu.house.service.HouseServiceImpl;
import com.tu.house.service.IHouseService;

/**
 * @author Tu enum@foxmail.com.
 */
public class JavaApplication {

  public static void main(String[] args) {
    IHouseService houseService = new HouseServiceImpl();
    houseService.getHouse();

  }
}
