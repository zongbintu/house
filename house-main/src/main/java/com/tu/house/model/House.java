package com.tu.house.model;

import java.io.Serializable;

/**
 * @author Tu enum@foxmail.com.
 */
public class House implements Serializable {

  private static final long serialVersionUID = 2174443390114018171L;
  private String url;
  /**
   * 标题
   */
  private String title;
  /**
   * 小区
   */
  private String village;
  /**
   * 户型-3室2厅/车位
   */
  private String structure;

  /**
   * 面积
   */
  private String acreage;
  /**
   * 朝向
   */
  private String orientation;
  /**
   * 装修情况
   */
  private String decoration;
  /**
   * 是否有电梯
   */
  private String elevator;
  /**
   * 总价
   */
  private String totalPrice;
  /**
   * 单价
   */
  private String unitPrice;
  /**
   * 楼层位置
   */
  private String position;
  /**
   * 区域
   */
  private String region;

  private String tag;

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

  public String getVillage() {
    return village;
  }

  public void setVillage(String village) {
    this.village = village;
  }

  public String getStructure() {
    return structure;
  }

  public void setStructure(String structure) {
    this.structure = structure;
  }

  public String getAcreage() {
    return acreage;
  }

  public void setAcreage(String acreage) {
    this.acreage = acreage;
  }

  public String getOrientation() {
    return orientation;
  }

  public void setOrientation(String orientation) {
    this.orientation = orientation;
  }

  public String getDecoration() {
    return decoration;
  }

  public void setDecoration(String decoration) {
    this.decoration = decoration;
  }

  public String getElevator() {
    return elevator;
  }

  public void setElevator(String elevator) {
    this.elevator = elevator;
  }

  public String getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(String totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(String unitPrice) {
    this.unitPrice = unitPrice;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  @Override
  public String toString() {
    return "House{" +
        "url='" + url + '\'' +
        ", title='" + title + '\'' +
        ", village='" + village + '\'' +
        ", structure='" + structure + '\'' +
        ", acreage='" + acreage + '\'' +
        ", orientation='" + orientation + '\'' +
        ", decoration='" + decoration + '\'' +
        ", elevator='" + elevator + '\'' +
        ", totalPrice='" + totalPrice + '\'' +
        ", unitPrice='" + unitPrice + '\'' +
        ", position='" + position + '\'' +
        ", region='" + region + '\'' +
        ", tag='" + tag + '\'' +
        '}';
  }
}
