package com.tu.house.service;

/**
 * @author Tu enum@foxmail.com.
 */
public interface IHouseService {

  String getHouse(String... args);

  /**
   * 销售面积
   */
  String getSaleArea(String startDate, String endDate);
}
