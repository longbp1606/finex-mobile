package com.example.finex_mobile.activities.subscription.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.subcription.Subscription;

public class SubscriptionDetail extends AppCompatActivity {
    private Subscription currentSubscription;
    private TextView subscriptionDetailName;
    private TextView subscriptionDetailPrice;
    private TextView subscriptionDetailDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subscription_detail);
        getSupportActionBar().hide(); // Hide the action bar for a more modern look
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int position = getIntent().getExtras().getInt("subscriptionPosition");
        currentSubscription = Subscription.getSupscriptionList().get(position);

        subscriptionDetailName = findViewById(R.id.subscriptionDetailName);
        subscriptionDetailPrice = findViewById(R.id.subscriptionDetailPrice);
        subscriptionDetailDescription = findViewById(R.id.subscriptionDetailDescription);

        subscriptionDetailName.setText(currentSubscription.getName());
        subscriptionDetailPrice.setText(currentSubscription.getPrice() + " USD / Month");
        subscriptionDetailDescription.setText(currentSubscription.getDescription());
        
        // Set up back button click listener
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());
    }

    public void handleBuySubscriptionClick(View view) {
        setResult(RESULT_OK, getIntent());
        finish();
    }
}