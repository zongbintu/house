package com.tu.house.model.dto;

import java.io.Serializable;

/**
 * @author Tu enum@foxmail.com.
 */
public class PoiDto implements Serializable {

  private static final long serialVersionUID = 7934556743590779228L;
  private String name;
  private String province;
  private String city;
  private String area;
  private String address;
  private String street_id;
  private String telephone;
  private int detail;
  private String uid;
  private String lat;
  private String lng;
  /**
   * 品牌
   */
  private String brandMark;

  public String getLat() {
    return lat;
  }

  public void setLat(String lat) {
    this.lat = lat;
  }

  public String getLng() {
    return lng;
  }

  public void setLng(String lng) {
    this.lng = lng;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getStreet_id() {
    return street_id;
  }

  public void setStreet_id(String street_id) {
    this.street_id = street_id;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public int getDetail() {
    return detail;
  }

  public void setDetail(int detail) {
    this.detail = detail;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getBrandMark() {
    return brandMark;
  }

  public void setBrandMark(String brandMark) {
    this.brandMark = brandMark;
  }
}
