package com.tatsinktech.echat.enums;

public enum LoginTypeEnum {
    LOGIN(1, "Online"),
    LOGOUT(2, "Offline");
    private int code;
    private String desc;

    LoginTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
