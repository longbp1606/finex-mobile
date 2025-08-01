package com.example.finex_mobile.entities.subcription;

import java.util.ArrayList;
import java.util.List;

public class Subscription {
    private static List<Subscription> supscriptionList;
    public static List<Subscription> getSupscriptionList() {
        if (supscriptionList == null) {
            supscriptionList = new ArrayList<>();
            supscriptionList.add(new Subscription("Basic Plan", 8, "Basic functions"));
            supscriptionList.add(new Subscription("Pro Plan", 15, "Provide you with more advanced features"));
            supscriptionList.add(new Subscription("Ultimate Plan", 30, "Unlock unlimited features"));
        }
        return supscriptionList;
    }

    private String name;
    private double price;
    private String description;

    public Subscription(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
