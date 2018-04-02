package com.tu.house.model;

import java.io.Serializable;

/**
 * 小区信息
 *
 * @author Tu enum@foxmail.com.
 */
public class Village implements Serializable {

  private static final long serialVersionUID = 5293498194078308939L;
  private String url;
  /**
   * 标题
   */
  private String title;
  /**
   * 地址
   */
  private String address;
  /**
   * 年代
   */
  private int age;
  /**
   * 均价
   */
  private String price;
  /**
   * 建筑类型 塔楼/板楼
   */
  private String buildType;
  /**
   * 物业费
   */
  private String propertyCost;
  /**
   * 物业公司
   */
  private String property;
  /**
   * 楼栋数
   */
  private int buildCount;
  /**
   * 户数
   */
  private int doorCount;

  /**
   * 幼儿园
   */
  private String kindergarten;
  /**
   * 地区
   */
  private String district;
  /**
   * 市
   */
  private String city;
  /**
   * 街道
   */

  private String street;
  /**
   * 开发商
   */
  private String builder;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getBuildType() {
    return buildType;
  }

  public void setBuildType(String buildType) {
    this.buildType = buildType;
  }

  public String getPropertyCost() {
    return propertyCost;
  }

  public void setPropertyCost(String propertyCost) {
    this.propertyCost = propertyCost;
  }

  public int getBuildCount() {
    return buildCount;
  }

  public void setBuildCount(int buildCount) {
    this.buildCount = buildCount;
  }

  public int getDoorCount() {
    return doorCount;
  }

  public void setDoorCount(int doorCount) {
    this.doorCount = doorCount;
  }

  public String getKindergarten() {
    return kindergarten;
  }

  public void setKindergarten(String kindergarten) {
    this.kindergarten = kindergarten;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getBuilder() {
    return builder;
  }

  public void setBuilder(String builder) {
    this.builder = builder;
  }

  @Override
  public String toString() {
    return "Village{" +
        "url='" + url + '\'' +
        ", title='" + title + '\'' +
        ", address='" + address + '\'' +
        ", age=" + age +
        ", price='" + price + '\'' +
        ", buildType='" + buildType + '\'' +
        ", propertyCost='" + propertyCost + '\'' +
        ", property='" + property + '\'' +
        ", buildCount=" + buildCount +
        ", doorCount=" + doorCount +
        ", kindergarten='" + kindergarten + '\'' +
        ", district='" + district + '\'' +
        ", city='" + city + '\'' +
        ", street='" + street + '\'' +
        ", builder='" + builder + '\'' +
        '}';
  }
}
