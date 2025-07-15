package com.example.finex_mobile.entities;

public class RecentContribution {
    private String date;
    private long amount;
    private String bankAccountId;
    private String goalName;
    private String goalIcon;
    private int goalId;
    private String bankAccountName;
    private String bankName;

    public RecentContribution() {}

    public RecentContribution(String date, long amount, String bankAccountId,
                              String goalName, String goalIcon, int goalId) {
        this.date = date;
        this.amount = amount;
        this.bankAccountId = bankAccountId;
        this.goalName = goalName;
        this.goalIcon = goalIcon;
        this.goalId = goalId;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    public String getBankAccountId() { return bankAccountId; }
    public void setBankAccountId(String bankAccountId) { this.bankAccountId = bankAccountId; }

    public String getGoalName() { return goalName; }
    public void setGoalName(String goalName) { this.goalName = goalName; }

    public String getGoalIcon() { return goalIcon; }
    public void setGoalIcon(String goalIcon) { this.goalIcon = goalIcon; }

    public int getGoalId() { return goalId; }
    public void setGoalId(int goalId) { this.goalId = goalId; }

    public String getBankAccountName() { return bankAccountName; }
    public void setBankAccountName(String bankAccountName) { this.bankAccountName = bankAccountName; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
}