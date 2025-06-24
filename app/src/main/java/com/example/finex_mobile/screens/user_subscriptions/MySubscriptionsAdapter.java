package com.example.finex_mobile.screens.user_subscriptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.UserSubscription;

import java.util.List;

public class MySubscriptionsAdapter extends BaseAdapter {
    private Context context;
    private List<UserSubscription> userSubscriptionList;
    public MySubscriptionsAdapter(Context context, List<UserSubscription> userSubscriptionList) {
        this.context = context;
        this.userSubscriptionList = userSubscriptionList;
    }

    @Override
    public int getCount() {
        return userSubscriptionList.size();
    }

    @Override
    public Object getItem(int position) {
        return userSubscriptionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_subscription_item, parent, false);
        }

        TextView userSubscriptionName = convertView.findViewById(R.id.userSubscriptionName);
        TextView userSubscriptionStartTime = convertView.findViewById(R.id.userSupscriptionStartTime);
        TextView userSubscriptionEndTime = convertView.findViewById(R.id.userSubscriptionEndTime);
        UserSubscription item = userSubscriptionList.get(position);

        userSubscriptionName.setText(item.getSubscription().getName());
        userSubscriptionStartTime.setText("From: " + item.getStartTime());
        userSubscriptionEndTime.setText("To: " + item.getEndTime());

        return convertView;
    }
}
