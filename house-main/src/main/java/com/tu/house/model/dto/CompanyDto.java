package com.tu.house.model.dto;

/**
 * @author Tu enum@foxmail.com.
 */
public class CompanyDto {

  /**
   * companyId : 77979
   * companyFullName : 重庆网宏科技有限公司
   * companyShortName : 网宏科技
   * companyLogo : i/image/M00/94/95/Cgp3O1iavEWAHDCVAADebILG_r8137.jpg
   * city : 重庆
   * industryField : 移动互联网,O2O
   * companyFeatures : 中国领先的旅游业网络营销服务商
   * financeStage : 不需要融资
   */

  private String companyFullName;
  private String companyShortName;
  private String companyLogo;
  private String city;
  private String industryField;
  private String companyFeatures;
  private String financeStage;
  private String url;

  public String getCompanyFullName() {
    return companyFullName;
  }

  public void setCompanyFullName(String companyFullName) {
    this.companyFullName = companyFullName;
  }

  public String getCompanyShortName() {
    return companyShortName;
  }

  public void setCompanyShortName(String companyShortName) {
    this.companyShortName = companyShortName;
  }

  public String getCompanyLogo() {
    return companyLogo;
  }

  public void setCompanyLogo(String companyLogo) {
    this.companyLogo = companyLogo;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getIndustryField() {
    return industryField;
  }

  public void setIndustryField(String industryField) {
    this.industryField = industryField;
  }

  public String getCompanyFeatures() {
    return companyFeatures;
  }

  public void setCompanyFeatures(String companyFeatures) {
    this.companyFeatures = companyFeatures;
  }

  public String getFinanceStage() {
    return financeStage;
  }

  public void setFinanceStage(String financeStage) {
    this.financeStage = financeStage;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
