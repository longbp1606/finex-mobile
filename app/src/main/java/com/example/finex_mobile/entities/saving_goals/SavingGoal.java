package com.example.finex_mobile.entities.saving_goals;

import java.util.List;

public class SavingGoal {
    private int id;
    private String name;
    private String icon;
    private long targetAmount;
    private long currentAmount;
    private String deadline;
    private String category;
    private String notes;
    private List<Contribution> contributions;
    private AutoContribution autoContribution;
    private String status; // "active" or "completed"
    private String bankAccountId;

    public SavingGoal() {}

    public SavingGoal(int id, String name, String icon, long targetAmount, long currentAmount,
                      String deadline, String category, String notes, List<Contribution> contributions,
                      AutoContribution autoContribution, String status, String bankAccountId) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
        this.category = category;
        this.notes = notes;
        this.contributions = contributions;
        this.autoContribution = autoContribution;
        this.status = status;
        this.bankAccountId = bankAccountId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public long getTargetAmount() { return targetAmount; }
    public void setTargetAmount(long targetAmount) { this.targetAmount = targetAmount; }

    public long getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(long currentAmount) { this.currentAmount = currentAmount; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public List<Contribution> getContributions() { return contributions; }
    public void setContributions(List<Contribution> contributions) { this.contributions = contributions; }

    public AutoContribution getAutoContribution() { return autoContribution; }
    public void setAutoContribution(AutoContribution autoContribution) { this.autoContribution = autoContribution; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getBankAccountId() { return bankAccountId; }
    public void setBankAccountId(String bankAccountId) { this.bankAccountId = bankAccountId; }

    public int getProgressPercent() {
        if (targetAmount == 0) return 0;
        return Math.min(100, (int) Math.round((currentAmount * 100.0) / targetAmount));
    }

    public boolean isCompleted() {
        return "completed".equals(status) || currentAmount >= targetAmount;
    }
}