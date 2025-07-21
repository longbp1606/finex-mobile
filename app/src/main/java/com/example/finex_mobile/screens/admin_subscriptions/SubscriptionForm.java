package com.example.finex_mobile.screens.admin_subscriptions;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.subcription.Subscription;
import com.google.android.material.textfield.TextInputLayout;

public class SubscriptionForm extends AppCompatActivity {
    private Subscription subscription;
    private EditText subscriptionNameInput;
    private EditText subscriptionDescriptionInput;
    private EditText subscriptionPriceInput;
    private TextInputLayout subscriptionNameInputLayout;
    private TextInputLayout subscriptionDescriptionInputLayout;
    private TextInputLayout subscriptionPriceInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subscription_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        subscriptionNameInput = findViewById(R.id.subscriptionNameInput);
        subscriptionDescriptionInput = findViewById(R.id.subscriptionDescriptionInput);
        subscriptionPriceInput = findViewById(R.id.subscriptionPriceInput);
        subscriptionNameInputLayout = findViewById(R.id.subscriptionNameInputLayout);
        subscriptionDescriptionInputLayout = findViewById(R.id.subscriptionDescriptionInputLayout);
        subscriptionPriceInputLayout = findViewById(R.id.subscriptionPriceInputLayout);

        int subscriptionPosition = getIntent().getExtras().getInt("subscriptionPosition", -1);
        if (subscriptionPosition >= 0) {
            subscription = Subscription.getSupscriptionList().get(subscriptionPosition);

            subscriptionNameInput.setText(subscription.getName());
            subscriptionDescriptionInput.setText(subscription.getDescription());
            subscriptionPriceInput.setText(String.valueOf(subscription.getPrice()));
        }
    }

    public void onSaveButtonClick(View view) {
        boolean isOk = true;

        String subscriptionName = subscriptionNameInput.getText().toString();
        String subscriptionDescription = subscriptionDescriptionInput.getText().toString();
        String subscriptionPriceStr = subscriptionPriceInput.getText().toString();
        double subscriptionPrice = 0;

        if (subscriptionName.isBlank()) {
            subscriptionNameInputLayout.setErrorEnabled(true);
            subscriptionNameInputLayout.setError("Subscription name must not be blank");
            isOk = false;
        }

        if (subscriptionDescription.isBlank()) {
            subscriptionDescriptionInputLayout.setErrorEnabled(true);
            subscriptionDescriptionInputLayout.setError("Subscription description must not be blank");
            isOk = false;
        }

        try {
            subscriptionPrice = Double.parseDouble(subscriptionPriceStr);
        } catch (Exception e) {
            subscriptionPriceInputLayout.setErrorEnabled(true);
            subscriptionPriceInputLayout.setError("Subscription price must be a number");
            isOk = false;
        }

        if (isOk) {
            subscriptionNameInputLayout.setErrorEnabled(false);
            subscriptionDescriptionInputLayout.setErrorEnabled(false);
            subscriptionPriceInputLayout.setErrorEnabled(false);

            if (subscription == null) {
                Subscription.getSupscriptionList().add(new Subscription(subscriptionName, subscriptionPrice, subscriptionDescription));
            } else {
                subscription.setName(subscriptionName);
                subscription.setDescription(subscriptionDescription);
                subscription.setPrice(subscriptionPrice);
            }

            setResult(RESULT_OK);
            finish();
        }
    }

    public void onCancelButtonClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}