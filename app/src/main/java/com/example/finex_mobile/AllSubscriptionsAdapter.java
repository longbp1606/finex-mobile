package com.example.finex_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AllSubscriptionsAdapter extends BaseAdapter {
    private Context context;
    private List<Subscription> subscriptionList;

    public AllSubscriptionsAdapter(Context context, List<Subscription> subscriptionList) {
        this.context = context;
        this.subscriptionList = subscriptionList;
    }

    @Override
    public int getCount() {
        return subscriptionList.size();
    }

    @Override
    public Object getItem(int i) {
        return subscriptionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.subscription_item, viewGroup, false);
        }

        Subscription subscription = Subscription.getSupscriptionList().get(i);

        TextView subscriptionName = view.findViewById(R.id.subscriptionName);
        subscriptionName.setText(subscription.getName());
        TextView subscriptionPrice = view.findViewById(R.id.subscriptionPrice);
        subscriptionPrice.setText(subscription.getPrice() + " USD / Month");
        TextView subscriptionTime = view.findViewById(R.id.subscriptionTime);
        subscriptionTime.setText(subscription.getDescription());

        return view;
    }
}
