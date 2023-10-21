package com.liuc.server.api.common.enums;

public enum SmsTypeEnum {

    LOGIN("LOGIN", "登录"),
    REGISTER("REGISTER", "注册"),
    FORGET("FORGET", "忘记密码");

    private String id;
    private String name;

    SmsTypeEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
