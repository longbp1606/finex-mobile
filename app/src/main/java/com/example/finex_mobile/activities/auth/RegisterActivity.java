package com.example.finex_mobile.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finex_mobile.R;
import com.example.finex_mobile.activities.budget.BudgetActivity;
import com.example.finex_mobile.entities.user.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etconfirmPassword;
    private Button btnRegister;
    private Button btnRedirect;
    private boolean isExisted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Hidden menu
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etconfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnRedirect = findViewById(R.id.btnRedirect);

        btnRegister.setOnClickListener((v) -> onRegister(v));
        btnRedirect.setOnClickListener((v) -> onRedirect(v));
    }

    public void onRegister(View view) {
        String name = etName.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etconfirmPassword.getText().toString();

        if (name.isEmpty()) {
            etName.setError("Name is required!");
            return;
        }

        if (username.isEmpty()) {
            etUsername.setError("Username is required!");
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            etconfirmPassword.setError("Passwords do not match!");
            return;
        }

        isExisted = false;
        for (User user : User.getAccountList()) {
            if (user.getUsername().equals(username)) {
                isExisted = true;
                break;
            }
        }

        if (isExisted) {
            Toast.makeText(this, "This account already exists!", Toast.LENGTH_SHORT).show();
        } else {
            User newUser = new User(name, username, password);
            User.getAccountList().add(newUser);
            Toast.makeText(this, "Register successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, BudgetActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onRedirect(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}