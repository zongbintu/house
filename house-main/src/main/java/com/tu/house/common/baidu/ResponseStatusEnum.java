package com.tu.house.common.baidu;

/**
 * 服务状态码
 *
 * @author Tu enum@foxmail.com.
 */
public enum ResponseStatusEnum {
  /**
   * 正常
   */
  OK(0, "ok"),
  /**
   * 请求参数非法- 必要参数拼写错误或漏传（如query和tag请求中均未传入）
   */
  PARAMETER_INVALID(2, "Parameter Invalid"),
  /**
   * 权限校验失败
   */
  VERIFY_FAILURE(3, "Verify Failure"),
  /**
   * 配额校验失败-服务当日调用次数已超限
   */
  QUOTA_FAILURE(4, "Quota Failure"),
  /**
   * ak不存在或者非法-未传入ak参数；ak已被删除
   */
  AK_FAILURE(5, "AK Failure");
  private final int code;
  private final String message;

  ResponseStatusEnum(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public static ResponseStatusEnum getByCode(int type) {
    for (ResponseStatusEnum typeEnum : values()) {
      if (typeEnum.code == type) {
        return typeEnum;
      }
    }
    return null;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
