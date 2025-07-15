package com.example.finex_mobile.screens.user_subscriptions;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.SavingGoal;
import com.example.finex_mobile.entities.BankAccount;
import com.example.finex_mobile.entities.Contribution;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddContributionDialog extends Dialog {

    public interface OnContributionAddedListener {
        void onContributionAdded(Contribution contribution);
    }

    private SavingGoal goal;
    private List<BankAccount> bankAccounts;
    private OnContributionAddedListener listener;
    private TextView tvGoalName;
    private EditText etAmount;
    private Spinner spinnerBankAccount;
    private Button btnAdd, btnCancel;

    public AddContributionDialog(@NonNull Context context, SavingGoal goal, List<BankAccount> bankAccounts) {
        super(context);
        this.goal = goal;
        this.bankAccounts = bankAccounts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_contribution);

        initViews();
        setupSpinner();
        setupClickListeners();

        tvGoalName.setText(goal.getName());
    }

    private void initViews() {
        tvGoalName = findViewById(R.id.tv_goal_name);
        etAmount = findViewById(R.id.et_amount);
        spinnerBankAccount = findViewById(R.id.spinner_bank_account);
        btnAdd = findViewById(R.id.btn_add);
        btnCancel = findViewById(R.id.btn_cancel);
    }

    private void setupSpinner() {
        List<String> bankDisplayNames = new ArrayList<>();
        for (BankAccount account : bankAccounts) {
            bankDisplayNames.add(account.getDisplayName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, bankDisplayNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBankAccount.setAdapter(adapter);
    }

    private void setupClickListeners() {
        btnAdd.setOnClickListener(v -> addContribution());
        btnCancel.setOnClickListener(v -> dismiss());
    }

    private void addContribution() {
        String amountStr = etAmount.getText().toString().trim();

        if (amountStr.isEmpty()) {
            Toast.makeText(getContext(), "Please enter amount", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            long amount = Long.parseLong(amountStr);
            String selectedBankAccountId = bankAccounts.get(spinnerBankAccount.getSelectedItemPosition()).getId();
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            Contribution contribution = new Contribution(currentDate, amount, selectedBankAccountId);

            if (listener != null) {
                listener.onContributionAdded(contribution);
            }
            dismiss();
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please enter valid amount", Toast.LENGTH_SHORT).show();
        }
    }

    public void setOnContributionAddedListener(OnContributionAddedListener listener) {
        this.listener = listener;
    }
}