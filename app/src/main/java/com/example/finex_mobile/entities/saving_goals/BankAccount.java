package com.example.finex_mobile.entities.saving_goals;

public class BankAccount {
    private String id;
    private String accountName;
    private String bankName;
    private String type;
    private long balance;
    private String currency;
    private boolean isActive;
    private String updatedAt;

    public BankAccount() {}

    public BankAccount(String id, String accountName, String bankName, String type,
                       long balance, String currency, boolean isActive, String updatedAt) {
        this.id = id;
        this.accountName = accountName;
        this.bankName = bankName;
        this.type = type;
        this.balance = balance;
        this.currency = currency;
        this.isActive = isActive;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public long getBalance() { return balance; }
    public void setBalance(long balance) { this.balance = balance; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getDisplayName() {
        return accountName + " (" + bankName + ") - " +
                String.format("%,d", balance) + " " + currency;
    }
}