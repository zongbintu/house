package com.tu.house.service;

/**
 * @author Tu enum@foxmail.com.
 */
public interface IHouseService {

  /**
   * 获取链家房子信息
   */
  String getHouse(String... args);

  /**
   * 国家统计数据  销售面积
   */
  String getSaleArea(String startDate, String endDate);

  /**
   * 获取链家小区信息
   */
  String getVillage();
}
