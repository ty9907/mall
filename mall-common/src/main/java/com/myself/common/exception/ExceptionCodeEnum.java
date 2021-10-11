package com.myself.common.exception;

public enum ExceptionCodeEnum {
    UNKONW_ERROE(10000,"系统未知错误"),
    DATA_VALID_EXCEPTION(10001,"数据校验错误")
    ;

    private Integer code;
    private String msg;

    ExceptionCodeEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
