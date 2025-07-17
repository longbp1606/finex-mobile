package com.example.finex_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finex_mobile.screens.user_subscriptions.SavingsGoalsActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // layout splash

        sharedPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE);

        new Handler().postDelayed(() -> {
            boolean isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false);
            Intent intent;
            intent = new Intent(MainActivity.this, SavingsGoalsActivity.class);
//            if (isLoggedIn) {
//                intent = new Intent(MainActivity.this, AnalysisActivity.class);
//            } else {
//                intent = new Intent(MainActivity.this, LoginActivity.class);
//            }
            startActivity(intent);
            finish();
        }, 2000); // delay 2s để hiện splash
    }
}