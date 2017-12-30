package com.cn.huidashi.entity;

public class Menu {
    private Integer id;

    private String name;

    private Boolean hasSubmenu;

    private Integer parentMenuId;

    private String link;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasSubmenu() {
        return hasSubmenu;
    }

    public void setHasSubmenu(Boolean hasSubmenu) {
        this.hasSubmenu = hasSubmenu;
    }

    public Integer getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Integer parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}