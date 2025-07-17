package com.example.finex_mobile.entities;

public class Bill {
    private int id;
    private String name;
    private String category;
    private double amount;
    private String dueDate;
    private String status; // paid, upcoming, overdue

    public Bill(int id, String name, String category, double amount, String dueDate, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public String getDueDate() { return dueDate; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
