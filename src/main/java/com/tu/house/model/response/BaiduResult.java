package com.tu.house.model.response;

/**
 * @author Tu enum@foxmail.com.
 */
public class BaiduResult<T> {

  private int status;
  private String message;
  private int total;
  private T results;

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getResults() {
    return results;
  }

  public void setResults(T results) {
    this.results = results;
  }
}
