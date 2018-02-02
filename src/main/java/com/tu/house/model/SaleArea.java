package com.tu.house.model;

import java.io.Serializable;

/**
 * 销售面积
 *
 * @author Tu enum@foxmail.com.
 * @since 0.1.0
 */
public class SaleArea implements Serializable{

  /**
   * 地区
   */
  private String region;

  /**
   * 商品房销售面积(万平方米)
   */
  private double acreage;

  /**
   * 现房
   */
  private double exist;

  /**
   * 期房
   */
  private double forwardDelivery;

  /**
   * 日期 201801
   */
  private String date;

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public double getAcreage() {
    return acreage;
  }

  public void setAcreage(double acreage) {
    this.acreage = acreage;
  }

  public double getExist() {
    return exist;
  }

  public void setExist(double exist) {
    this.exist = exist;
  }

  public double getForwardDelivery() {
    return forwardDelivery;
  }

  public void setForwardDelivery(double forwardDelivery) {
    this.forwardDelivery = forwardDelivery;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "SaleArea{" +
        "region='" + region + '\'' +
        ", acreage=" + acreage +
        ", exist=" + exist +
        ", forwardDelivery=" + forwardDelivery +
        ", date='" + date + '\'' +
        '}';
  }
}
