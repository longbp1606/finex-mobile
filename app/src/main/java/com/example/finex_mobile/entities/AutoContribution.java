package com.example.finex_mobile.entities;

public class AutoContribution {
    private long amount;
    private String frequency; // "none", "weekly", "biweekly", "monthly"

    public AutoContribution() {}

    public AutoContribution(long amount, String frequency) {
        this.amount = amount;
        this.frequency = frequency;
    }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
}