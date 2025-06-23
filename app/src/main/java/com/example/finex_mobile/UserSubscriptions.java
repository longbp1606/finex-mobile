package com.example.finex_mobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserSubscriptions extends AppCompatActivity {
    private LinearLayout scrollContainer;
    private AllSubscriptionsAdapter allSubscriptionsAdapter;
    private ListView allSubscriptionsListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_subscriptions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        scrollContainer = findViewById(R.id.scrollContainer);
        allSubscriptionsAdapter = new AllSubscriptionsAdapter(this, Subscription.getSupscriptionList());

        changeToAllSubscriptionsTab();
    }

    private void changeTab(Integer tab) {
        View v = LayoutInflater.from(this).inflate(tab, scrollContainer, false);
        scrollContainer.removeAllViews();
        scrollContainer.addView(v);
    }

    private void changeToAllSubscriptionsTab() {
        changeTab(R.layout.all_subscriptions);
        allSubscriptionsListview = findViewById(R.id.allSubscriptionsListview);
        allSubscriptionsListview.setAdapter(allSubscriptionsAdapter);
    }

    private void changeToMySubscriptionsTab() {
        changeTab(R.layout.my_subscriptions);
    }

    public void onAllSubscriptionsClick(View view) {
        changeToAllSubscriptionsTab();
    }

    public void onMySubscriptionsClick(View view) {
        changeTab(R.layout.my_subscriptions);
    }

}