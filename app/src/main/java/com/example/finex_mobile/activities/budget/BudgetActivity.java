package com.example.finex_mobile.activities.budget;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finex_mobile.R;
import com.example.finex_mobile.activities.saving.SavingsGoalsActivity;
import com.example.finex_mobile.activities.bill.BillActivity;
import com.example.finex_mobile.entities.budget.Budget;
import com.example.finex_mobile.database.BudgetDatabaseHelper;
import com.example.finex_mobile.adapters.BudgetAdapter;
import com.example.finex_mobile.activities.subscription.user.UserSubscriptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

public class BudgetActivity extends AppCompatActivity implements BudgetAdapter.OnBudgetActionListener {
    private RecyclerView recyclerView;
    private BudgetAdapter adapter;
    private List<Budget> budgets;
    private FloatingActionButton fabAddBudget;
    private BudgetDatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget);
        setTitle("Budget");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.budget_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycler_budgets);
        fabAddBudget = findViewById(R.id.fab_add_budget);
        dbHelper = new BudgetDatabaseHelper(this);

        budgets = new ArrayList<>();
        loadBudgetsFromDb();

        adapter = new BudgetAdapter(budgets, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fabAddBudget.setOnClickListener(v -> {
            showBudgetDialog(null);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finex_mobile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.finex_menu_subscription) {
            Intent intent = new Intent(this, UserSubscriptions.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.finex_menu_budget) {
            Intent intent = new Intent(this, BudgetActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.finex_menu_saving_goal) {
            Intent intent = new Intent(this, SavingsGoalsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.finex_menu_bill) {
            Intent intent = new Intent(this, BillActivity.class);
            startActivity(intent);
        }
        return false;
    }

    private void loadBudgetsFromDb() {
        budgets.clear();
        budgets.addAll(dbHelper.getAllBudgets());
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    @Override
    public void onDetails(Budget budget) {
        Toast.makeText(this, "Details: " + budget.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEdit(Budget budget) {
        showBudgetDialog(budget);
    }

    @Override
    public void onDelete(Budget budget) {
        dbHelper.deleteBudget(budget.getId());
        loadBudgetsFromDb();
        Toast.makeText(this, "Deleted: " + budget.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void showBudgetDialog(Budget budgetToEdit) {
        boolean isEdit = budgetToEdit != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_budget, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
        EditText edtTitle = dialogView.findViewById(R.id.edt_title);
        EditText edtCurrency = dialogView.findViewById(R.id.edt_currency_unit);
        EditText edtMoney = dialogView.findViewById(R.id.edt_money);
        EditText edtLanguage = dialogView.findViewById(R.id.edt_language);
        TextView tvCreatedAt = dialogView.findViewById(R.id.tv_created_at);
        Button btnSave = dialogView.findViewById(R.id.btn_save);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        if (isEdit) {
            dialogTitle.setText("Edit Budget");
            edtTitle.setText(budgetToEdit.getTitle());
            edtCurrency.setText(budgetToEdit.getCurrencyUnit());
            edtMoney.setText(String.valueOf(budgetToEdit.getMoney()));
            edtLanguage.setText(budgetToEdit.getLanguage());
            tvCreatedAt.setText("Created: " + budgetToEdit.getCreatedAt());
        } else {
            dialogTitle.setText("Add Budget");
            tvCreatedAt.setText("Created: " + getTodayDate());
        }

        btnSave.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String currency = edtCurrency.getText().toString().trim();
            String moneyStr = edtMoney.getText().toString().trim();
            String language = edtLanguage.getText().toString().trim();
            String createdAt = isEdit ? budgetToEdit.getCreatedAt() : getTodayDate();
            String updatedAt = getTodayDate();
            double money = 0;
            try { money = Double.parseDouble(moneyStr); } catch (Exception ignored) {}
            if (title.isEmpty() || currency.isEmpty() || language.isEmpty()) {
                edtTitle.setError(title.isEmpty() ? "Required" : null);
                edtCurrency.setError(currency.isEmpty() ? "Required" : null);
                edtLanguage.setError(language.isEmpty() ? "Required" : null);
                return;
            }
            if (isEdit) {
                Budget updated = new Budget(budgetToEdit.getId(), title, currency, language, createdAt, updatedAt, false, false, money);
                dbHelper.updateBudget(updated);
            } else {
                String id = UUID.randomUUID().toString();
                Budget newBudget = new Budget(id, title, currency, language, createdAt, updatedAt, false, false, money);
                dbHelper.addBudget(newBudget);
            }
            loadBudgetsFromDb();
            dialog.dismiss();
        });
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private String getTodayDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }
} 