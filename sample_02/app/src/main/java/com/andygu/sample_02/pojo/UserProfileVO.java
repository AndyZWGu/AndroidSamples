package com.andygu.sample_02.pojo;

public class UserProfileVO {

  private String statusCode;
  private String uId;
  private String ucId;
  private String uPhone;
  private String uName;
  private String uEmail;
  private String errorMessage;

  public UserProfileVO(String statusCode, String uId, String ucId, String uPhone, String uName, String uEmail,
      String errorMessage) {
    this.statusCode = statusCode;
    this.uId = uId;
    this.ucId = ucId;
    this.uPhone = uPhone;
    this.uName = uName;
    this.uEmail = uEmail;
    this.errorMessage = errorMessage;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public String getuId() {
    return uId;
  }

  public void setuId(String uId) {
    this.uId = uId;
  }

  public String getUcId() {
    return ucId;
  }

  public void setUcId(String ucId) {
    this.ucId = ucId;
  }

  public String getuPhone() {
    return uPhone;
  }

  public void setuPhone(String uPhone) {
    this.uPhone = uPhone;
  }

  public String getuName() {
    return uName;
  }

  public void setuName(String uName) {
    this.uName = uName;
  }

  public String getuEmail() {
    return uEmail;
  }

  public void setuEmail(String uEmail) {
    this.uEmail = uEmail;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
