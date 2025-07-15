package com.example.finex_mobile.entities;

public class Contribution {
    private String date;
    private long amount;
    private String bankAccountId;

    public Contribution() {}

    public Contribution(String date, long amount, String bankAccountId) {
        this.date = date;
        this.amount = amount;
        this.bankAccountId = bankAccountId;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    public String getBankAccountId() { return bankAccountId; }
    public void setBankAccountId(String bankAccountId) { this.bankAccountId = bankAccountId; }
}