package com.example.finex_mobile.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finex_mobile.R;
import com.example.finex_mobile.activities.budget.BudgetActivity;
import com.example.finex_mobile.entities.user.User;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRedirect;
    private boolean isValid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Hidden menu
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnRegister);
        btnRedirect = findViewById(R.id.btnRedirect);

        btnLogin.setOnClickListener((v) -> onLogin(v));
        btnRedirect.setOnClickListener((v) -> onRedirect(v));
    }

    public void onLogin(View view) {
        if (etUsername.getText().toString().isEmpty()) {
            etUsername.setError("Username is required!");
            return;
        }

        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password is required!");
            return;
        }

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        isValid = false;
        for (User user : User.getAccountList()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                isValid = true;
                break;
            }
        }

        if (isValid) {
            Toast.makeText(this, "Login successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, BudgetActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Username or password is incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRedirect(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
