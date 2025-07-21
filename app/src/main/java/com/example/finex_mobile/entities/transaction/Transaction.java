package com.example.finex_mobile.entities.transaction;

public class Transaction {
    private String id;
    private String budgetId;
    private String content;
    private String createdAt;

    public Transaction() {}

    public Transaction(String id, String budgetId, String content, String createdAt) {
        this.id = id;
        this.budgetId = budgetId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBudgetId() { return budgetId; }
    public void setBudgetId(String budgetId) { this.budgetId = budgetId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
} 