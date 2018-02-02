package com.tu.house.model.response;

import java.util.List;

/**
 * @author Tu enum@foxmail.com.
 */
public class StatsResult {

  private String dbcode;
  private String htmltable;
  private String title;
  private List<StatsExcel> exceltable;

  public String getDbcode() {
    return dbcode;
  }

  public void setDbcode(String dbcode) {
    this.dbcode = dbcode;
  }

  public String getHtmltable() {
    return htmltable;
  }

  public void setHtmltable(String htmltable) {
    this.htmltable = htmltable;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<StatsExcel> getExceltable() {
    return exceltable;
  }

  public void setExceltable(List<StatsExcel> exceltable) {
    this.exceltable = exceltable;
  }
}
