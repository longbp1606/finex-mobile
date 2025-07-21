package com.example.finex_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.subcription.Subscription;

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

        Subscription subscription = subscriptionList.get(i);

        TextView subscriptionName = view.findViewById(R.id.subscriptionName);
        subscriptionName.setText(subscription.getName());
        TextView subscriptionPrice = view.findViewById(R.id.subscriptionPrice);
        subscriptionPrice.setText(subscription.getPrice() + " USD / Month");
        TextView subscriptionDescription = view.findViewById(R.id.subscriptionDescription);
        subscriptionDescription.setText(subscription.getDescription());

        return view;
    }
}
