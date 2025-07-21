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

public class AdminManageSubscriptionsAdapter extends BaseAdapter {
    private Context context;
    private List<Subscription> subscriptionList;

    public AdminManageSubscriptionsAdapter(Context context, List<Subscription> subscriptionList) {
        this.context = context;
        this.subscriptionList = subscriptionList;
    }

    @Override
    public int getCount() {
        return subscriptionList.size();
    }

    @Override
    public Object getItem(int position) {
        return subscriptionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.subscription_item, parent, false);
            convertView.setLongClickable(true);
        }

        Subscription subscription = subscriptionList.get(position);

        TextView subscriptionName = convertView.findViewById(R.id.subscriptionName);
        subscriptionName.setText(subscription.getName());
        TextView subscriptionPrice = convertView.findViewById(R.id.subscriptionPrice);
        subscriptionPrice.setText(subscription.getPrice() + " USD / Month");
        TextView subscriptionDescription = convertView.findViewById(R.id.subscriptionDescription);
        subscriptionDescription.setText(subscription.getDescription());

        return convertView;
    }
}
