package com.example.finex_mobile.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.transaction.Transaction;
import com.example.finex_mobile.database.TransactionDatabaseHelper;
import com.example.finex_mobile.adapters.TransactionAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class TransactionActivity extends AppCompatActivity implements TransactionAdapter.OnTransactionActionListener {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<Transaction> transactions;
    private TransactionDatabaseHelper dbHelper;
    private String budgetId;
    private String budgetTitle;
    private EditText edtSearch, edtDate;
    private LinearLayout emptyState;
    private Button btnAddTransaction;
    private FloatingActionButton fabAddTransaction;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        recyclerView = findViewById(R.id.recycler_transactions);
        edtSearch = findViewById(R.id.edt_search);
        edtDate = findViewById(R.id.edt_date);
        emptyState = findViewById(R.id.empty_state);
        btnAddTransaction = findViewById(R.id.btn_add_transaction);
        fabAddTransaction = findViewById(R.id.fab_add_transaction);
        tvTitle = findViewById(R.id.tv_title);
        dbHelper = new TransactionDatabaseHelper(this);

        Intent intent = getIntent();
        budgetId = intent.getStringExtra("budgetId");
        budgetTitle = intent.getStringExtra("budgetTitle");
        if (!TextUtils.isEmpty(budgetTitle)) {
            tvTitle.setText(budgetTitle + " Transactions");
        }

        transactions = new ArrayList<>();
        adapter = new TransactionAdapter(transactions, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadTransactions();

        btnAddTransaction.setOnClickListener(v -> showTransactionDialog(null));
        fabAddTransaction.setOnClickListener(v -> showTransactionDialog(null));
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_search).setOnClickListener(v -> searchTransactions());
        edtDate.setOnClickListener(v -> pickDate());
    }

    private void loadTransactions() {
        transactions.clear();
        String dateFilter = edtDate.getText().toString().trim();
        String searchQuery = edtSearch.getText().toString().trim();
        List<Transaction> result;
        if (!TextUtils.isEmpty(searchQuery)) {
            result = dbHelper.searchTransactionsForBudget(budgetId, searchQuery);
        } else {
            result = dbHelper.getTransactionsForBudget(budgetId);
        }
        if (!TextUtils.isEmpty(dateFilter)) {
            List<Transaction> filtered = new ArrayList<>();
            for (Transaction t : result) {
                if (t.getCreatedAt().startsWith(dateFilter)) {
                    filtered.add(t);
                }
            }
            result = filtered;
        }
        transactions.addAll(result);
        adapter.notifyDataSetChanged();
        emptyState.setVisibility(transactions.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(transactions.isEmpty() ? View.GONE : View.VISIBLE);
        Toast.makeText(this, "Loaded " + transactions.size() + " transactions for budgetId: " + budgetId, Toast.LENGTH_SHORT).show();
    }

    private void searchTransactions() {
        loadTransactions();
    }

    @Override
    public void onEdit(Transaction transaction) {
        showTransactionDialog(transaction);
    }

    @Override
    public void onDelete(Transaction transaction) {
        new AlertDialog.Builder(this)
            .setTitle("Delete Transaction")
            .setMessage("Are you sure you want to delete this transaction?")
            .setPositiveButton("Delete", (dialog, which) -> {
                dbHelper.deleteTransaction(transaction.getId());
                loadTransactions();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void showTransactionDialog(@Nullable Transaction transactionToEdit) {
        boolean isEdit = transactionToEdit != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_transaction, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
        EditText edtContent = dialogView.findViewById(R.id.edt_content);
        TextView tvCreatedAt = dialogView.findViewById(R.id.tv_created_at);
        ImageButton btnPickDate = dialogView.findViewById(R.id.btn_pick_date);
        Button btnSave = dialogView.findViewById(R.id.btn_save);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        final Calendar calendar = Calendar.getInstance();
        String initialDate;
        if (isEdit) {
            dialogTitle.setText("Edit Transaction");
            edtContent.setText(transactionToEdit.getContent());
            initialDate = transactionToEdit.getCreatedAt();
        } else {
            dialogTitle.setText("Add Transaction");
            initialDate = getNowDateTime();
        }
        tvCreatedAt.setText(initialDate);

        btnPickDate.setOnClickListener(v -> {
            String[] parts = tvCreatedAt.getText().toString().split(" ");
            String[] dateParts = parts[0].split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]) - 1;
            int day = Integer.parseInt(dateParts[2]);
            new DatePickerDialog(this, (view, y, m, d) -> {
                String newDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", y, m + 1, d);
                String[] timeParts = parts.length > 1 ? parts[1].split(":") : new String[]{"00","00","00"};
                String newDateTime = newDate + (parts.length > 1 ? " " + parts[1] : " 00:00:00");
                tvCreatedAt.setText(newDateTime);
            }, year, month, day).show();
        });

        btnSave.setOnClickListener(v -> {
            String content = edtContent.getText().toString().trim();
            String createdAt = tvCreatedAt.getText().toString().trim();
            if (content.isEmpty()) {
                edtContent.setError("Required");
                return;
            }
            if (isEdit) {
                Transaction updated = new Transaction(transactionToEdit.getId(), budgetId, content, createdAt);
                dbHelper.updateTransaction(updated);
            } else {
                String id = UUID.randomUUID().toString();
                Transaction newTransaction = new Transaction(id, budgetId, content, createdAt);
                dbHelper.addTransaction(newTransaction);
                Toast.makeText(this, "Added transaction for budgetId: " + budgetId, Toast.LENGTH_SHORT).show();
            }
            loadTransactions();
            dialog.dismiss();
        });
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void pickDate() {
        final Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String date = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            edtDate.setText(date);
            loadTransactions();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private String getNowDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }
} 