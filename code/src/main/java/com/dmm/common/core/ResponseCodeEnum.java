package com.dmm.common.core;

/**
 * Desc: 返回码枚举<br/>
 *
 */
public enum ResponseCodeEnum {

    BACK_CODE_SUCCESS("code", "6000", "处理数据成功"),
    BACK_CODE_FAIL("code", "7000", "处理数据失败"),
    BACK_CODE_ILLEGAL("code", "7001", "未经验证客户端"),
    BACK_CODE_CHECKERROR("code", "7002", "客户端与服务端验证失败--唯一码验证错误"),
    BACK_CODE_EXCEPTION("code", "7003", "未知错误"),
    BACK_CODE_ILLEGAL_PARAMS("code", "7004", "参数错误");

    public String name;
    public String value;
    public String desc;

    ResponseCodeEnum(String name, String code, String desc) {
        this.name = name;
        this.value = code;
        this.desc = desc;
    }
}
