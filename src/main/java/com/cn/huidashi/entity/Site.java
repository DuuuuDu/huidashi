package com.cn.huidashi.entity;

import java.util.Date;

public class Site {

    private Integer id;

    private String siteName;

    private String location;

    private String startingPrice;

    private String previewImageUrl;

    private String pdfBriefUrl;

    private String siteBriefIntroduction;

    private String siteServices;

    private String sitePolicy;

    private Date createTime;

    private Date updateTime;

    private Date lastDecorationDate;

    private Date openingDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    public String getPdfBriefUrl() {
        return pdfBriefUrl;
    }

    public void setPdfBriefUrl(String pdfBriefUrl) {
        this.pdfBriefUrl = pdfBriefUrl;
    }

    public String getSiteBriefIntroduction() {
        return siteBriefIntroduction;
    }

    public void setSiteBriefIntroduction(String siteBriefIntroduction) {
        this.siteBriefIntroduction = siteBriefIntroduction;
    }

    public String getSiteServices() {
        return siteServices;
    }

    public void setSiteServices(String siteServices) {
        this.siteServices = siteServices;
    }

    public String getSitePolicy() {
        return sitePolicy;
    }

    public void setSitePolicy(String sitePolicy) {
        this.sitePolicy = sitePolicy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastDecorationDate() {
        return lastDecorationDate;
    }

    public void setLastDecorationDate(Date lastDecorationDate) {
        this.lastDecorationDate = lastDecorationDate;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", siteName='" + siteName + '\'' +
                ", location='" + location + '\'' +
                ", startingPrice='" + startingPrice + '\'' +
                ", previewImageUrl='" + previewImageUrl + '\'' +
                ", pdfBriefUrl='" + pdfBriefUrl + '\'' +
                ", siteBriefIntroduction='" + siteBriefIntroduction + '\'' +
                ", siteServices='" + siteServices + '\'' +
                ", sitePolicy='" + sitePolicy + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", lastDecorationDate=" + lastDecorationDate +
                ", openingDate=" + openingDate +
                '}';
    }
}