package com.example.finex_mobile.screens.user_subscriptions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finex_mobile.R;
import com.example.finex_mobile.adapters.AllSubscriptionsAdapter;
import com.example.finex_mobile.adapters.MySubscriptionsAdapter;
import com.example.finex_mobile.entities.subcription.Subscription;
import com.example.finex_mobile.entities.user.User;
import com.example.finex_mobile.entities.subcription.UserSubscription;

public class UserSubscriptions extends AppCompatActivity {
    private ConstraintLayout mainLayout;
    private AllSubscriptionsAdapter allSubscriptionsAdapter;
    private ListView allSubscriptionsListview;
    private MySubscriptionsAdapter userSubscriptionsAdapter;
    private ListView userSubscriptionsListView;
    private User currentUser;
    private ActivityResultLauncher<Intent> subscriptionDetailLauncher;

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

        mainLayout = findViewById(R.id.mainLayout);
        allSubscriptionsAdapter = new AllSubscriptionsAdapter(this, Subscription.getSupscriptionList());
        userSubscriptionsAdapter = new MySubscriptionsAdapter(this, UserSubscription.getUserSubscriptionList());

        currentUser = User.getUserList().get(0);
        subscriptionDetailLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::handleSubscriptionDetailActivityResult);

        changeToAllSubscriptionsTab();
    }

    private void changeTab(Integer tab) {
        View v = LayoutInflater.from(this).inflate(tab, mainLayout, false);
        mainLayout.removeAllViews();
        mainLayout.addView(v);
    }

    private void handleSubscriptionItemClick(AdapterView adapterView, View view, int position, long id) {
        Intent intent = new Intent(this, SubscriptionDetail.class);
        intent.putExtra("subscriptionPosition", position);
        subscriptionDetailLauncher.launch(intent);
    }

    private void handleSubscriptionDetailActivityResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_CANCELED) {
            return;
        }

        if (result.getResultCode() != RESULT_OK) {
            Toast.makeText(this, "Buy Subscription Failed!", Toast.LENGTH_SHORT).show();
            return;
        }

        int position = result.getData().getExtras().getInt("subscriptionPosition");
        Subscription subscription = Subscription.getSupscriptionList().get(position);
        UserSubscription.getUserSubscriptionList().add(new UserSubscription(subscription, "10:00 24/07/2025", "10:00 24/07/2025", currentUser));

        Toast.makeText(this, "Buy Subscription Success!", Toast.LENGTH_SHORT).show();
    }

    private void changeToAllSubscriptionsTab() {
        changeTab(R.layout.all_subscriptions);
        allSubscriptionsListview = findViewById(R.id.allSubscriptionsListview);
        allSubscriptionsListview.setAdapter(allSubscriptionsAdapter);
        allSubscriptionsListview.setOnItemClickListener(this::handleSubscriptionItemClick);
    }

    private void changeToMySubscriptionsTab() {
        changeTab(R.layout.my_subscriptions);
        userSubscriptionsListView = findViewById(R.id.userSubscriptionsListView);
        userSubscriptionsListView.setAdapter(userSubscriptionsAdapter);
    }

    public void onAllSubscriptionsClick(View view) {
        changeToAllSubscriptionsTab();
    }

    public void onMySubscriptionsClick(View view) {
        changeToMySubscriptionsTab();
    }

}