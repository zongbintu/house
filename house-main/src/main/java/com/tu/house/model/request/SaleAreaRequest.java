package com.tu.house.model.request;

/**
 * @author Tu enum@foxmail.com.
 */
public class SaleAreaRequest {

  private String wdcode;
  private String valuecode;

  public SaleAreaRequest() {
  }

  public SaleAreaRequest(String wdcode, String valuecode) {
    this.wdcode = wdcode;
    this.valuecode = valuecode;
  }

  public String getWdcode() {
    return wdcode;
  }

  public void setWdcode(String wdcode) {
    this.wdcode = wdcode;
  }

  public String getValuecode() {
    return valuecode;
  }

  public void setValuecode(String valuecode) {
    this.valuecode = valuecode;
  }
}
