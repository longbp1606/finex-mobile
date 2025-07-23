package com.example.finex_mobile.entities.subcription;

import com.example.finex_mobile.entities.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserSubscription {
    private static List<UserSubscription> userSubscriptionList;
    public static List<UserSubscription> getUserSubscriptionList() {
        if (userSubscriptionList == null) {
            User userLong = User.getUserList().get(0);
            List<Subscription> subscriptionList = Subscription.getSupscriptionList();

            userSubscriptionList = new ArrayList<>();
        }
        return userSubscriptionList;
    }

    private Subscription subscription;
    private String startTime;
    private String endTime;
    private User user;

    public UserSubscription(Subscription subscription, String startTime, String endTime, User user) {
        this.subscription = subscription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
