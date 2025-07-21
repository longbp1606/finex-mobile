package com.example.finex_mobile.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.bill.Bill;
import com.example.finex_mobile.adapters.BillAdapter;
import com.example.finex_mobile.screens.user_subscriptions.UserSubscriptions;

import java.util.ArrayList;
import java.util.List;
public class BillActivity extends AppCompatActivity implements BillAdapter.OnBillActionListener {
    private List<Bill> bills = new ArrayList<>();
    private BillAdapter adapter;
    private RecyclerView recyclerView;
    private TextView totalText, unpaidText;
    private int editingId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill);
        setTitle("My Bills");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bill_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        totalText = findViewById(R.id.text_total);
        unpaidText = findViewById(R.id.text_unpaid);
        recyclerView = findViewById(R.id.recycler_bills);

        adapter = new BillAdapter(bills, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btn_add).setOnClickListener(v -> showBillDialog(null));

        loadDummyData();
        updateSummary();
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

    private void loadDummyData() {
        bills.add(new Bill(1, "Electricity", "Utilities", 145.80, "2025-03-20", "upcoming"));
        bills.add(new Bill(2, "Internet", "Utilities", 79.99, "2025-03-15", "upcoming"));
        bills.add(new Bill(3, "Water", "Utilities", 68.25, "2025-03-05", "overdue"));
        bills.add(new Bill(4, "Rent", "Housing", 1500.00, "2025-03-01", "paid"));
        bills.add(new Bill(5, "Mobile Phone", "Utilities", 95.50, "2025-03-22", "upcoming"));
        adapter.notifyDataSetChanged();
    }

    private void updateSummary() {
        double total = 0, unpaid = 0;
        for (Bill bill : bills) {
            total += bill.getAmount();
            if (!"paid".equals(bill.getStatus())) unpaid += bill.getAmount();
        }
        totalText.setText("Total: $" + String.format("%.2f", total));
        unpaidText.setText("Unpaid: $" + String.format("%.2f", unpaid));
    }

    private void showBillDialog(Bill bill) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_bill, null);
        EditText edtName = dialogView.findViewById(R.id.edt_name);
        EditText edtCategory = dialogView.findViewById(R.id.edt_category);
        EditText edtAmount = dialogView.findViewById(R.id.edt_amount);
        EditText edtDueDate = dialogView.findViewById(R.id.edt_due_date);

        if (bill != null) {
            edtName.setText(bill.getName());
            edtCategory.setText(bill.getCategory());
            edtAmount.setText(String.valueOf(bill.getAmount()));
            edtDueDate.setText(bill.getDueDate());
            editingId = bill.getId();
        } else {
            editingId = -1;
        }

        new AlertDialog.Builder(this)
                .setTitle(bill == null ? "Add Bill" : "Edit Bill")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = edtName.getText().toString();
                    String cat = edtCategory.getText().toString();
                    double amt = Double.parseDouble(edtAmount.getText().toString());
                    String due = edtDueDate.getText().toString();

                    if (editingId == -1) {
                        int newId = bills.isEmpty() ? 1 : bills.get(bills.size() - 1).getId() + 1;
                        bills.add(new Bill(newId, name, cat, amt, due, "upcoming"));
                    } else {
                        for (int i = 0; i < bills.size(); i++) {
                            if (bills.get(i).getId() == editingId) {
                                bills.set(i, new Bill(editingId, name, cat, amt, due, "upcoming"));
                                break;
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    updateSummary();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onEdit(Bill bill) {
        showBillDialog(bill);
    }

    @Override
    public void onDelete(int id) {
        bills.removeIf(b -> b.getId() == id);
        adapter.notifyDataSetChanged();
        updateSummary();
    }

    @Override
    public void onMarkPaid(int id) {
        for (Bill b : bills) {
            if (b.getId() == id) {
                b.setStatus("paid");
                break;
            }
        }
        adapter.notifyDataSetChanged();
        updateSummary();
    }
}
