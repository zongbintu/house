package com.tu.house.model;

/**
 * @author Tu enum@foxmail.com.
 */
public class Poi {

  private String name;
  private String province;
  private String city;
  private String area;
  private String address;
  private String street_id;
  private String telephone;
  private int detail;
  private String uid;
  private Location location;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
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

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  @Override
  public String toString() {
    return "Poi{" +
        "name='" + name + '\'' +
        ", province='" + province + '\'' +
        ", city='" + city + '\'' +
        ", area='" + area + '\'' +
        ", address='" + address + '\'' +
        ", street_id='" + street_id + '\'' +
        ", telephone='" + telephone + '\'' +
        ", detail=" + detail +
        ", uid='" + uid + '\'' +
        ", location=" + location +
        '}';
  }
}
