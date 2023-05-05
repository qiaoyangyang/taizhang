package com.meiling.oms.bean;

public class BindMeituanShopBean {
    private int type = 1;//类型
    private String name;//名称
    private String description;//描述
    private boolean isselect;

    public boolean isIsselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
    }

    public BindMeituanShopBean(int type, String name, String description, boolean isselect) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.isselect = isselect;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
