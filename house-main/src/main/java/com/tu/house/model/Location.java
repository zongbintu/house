package com.tu.house.model;

/**
 * @author Tu enum@foxmail.com.
 */
public class Location {

  private String lat;
  private String lng;

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

  @Override
  public String toString() {
    return "Location{" +
        "lat='" + lat + '\'' +
        ", lng='" + lng + '\'' +
        '}';
  }
}
