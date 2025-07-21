package com.example.finex_mobile.entities.budget;

public class Budget {
    private String id;
    private String title;
    private String currencyUnit;
    private String language;
    private String createdAt;
    private String updatedAt;
    private boolean isAnalyzed;
    private boolean isDeleted;
    private double money;

    public Budget() {}

    public Budget(String id, String title, String currencyUnit, String language, String createdAt, String updatedAt, boolean isAnalyzed, boolean isDeleted, double money) {
        this.id = id;
        this.title = title;
        this.currencyUnit = currencyUnit;
        this.language = language;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isAnalyzed = isAnalyzed;
        this.isDeleted = isDeleted;
        this.money = money;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCurrencyUnit() { return currencyUnit; }
    public void setCurrencyUnit(String currencyUnit) { this.currencyUnit = currencyUnit; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public boolean isAnalyzed() { return isAnalyzed; }
    public void setAnalyzed(boolean analyzed) { isAnalyzed = analyzed; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    public double getMoney() { return money; }
    public void setMoney(double money) { this.money = money; }
}