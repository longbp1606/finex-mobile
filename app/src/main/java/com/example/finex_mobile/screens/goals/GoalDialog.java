package com.example.finex_mobile.screens.goals;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.saving_goals.SavingGoal;
import com.example.finex_mobile.entities.saving_goals.BankAccount;
import com.example.finex_mobile.entities.saving_goals.AutoContribution;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GoalDialog extends Dialog {

    public interface OnGoalActionListener {
        void onGoalCreated(SavingGoal goal);
        void onGoalUpdated(SavingGoal goal);
    }

    private List<BankAccount> bankAccounts;
    private OnGoalActionListener listener;
    private SavingGoal existingGoal;
    private boolean isEditMode;

    private EditText etGoalName, etCategory, etTargetAmount, etDeadline, etInitialAmount;
    private Spinner spinnerIcon, spinnerBankAccount;
    private Button btnSave, btnCancel;
    private TextView tvDialogTitle;
    private static int goalIdCounter = 3;

    public GoalDialog(@NonNull Context context, List<BankAccount> bankAccounts) {
        super(context);
        this.bankAccounts = bankAccounts;
        this.existingGoal = null;
        this.isEditMode = false;
    }
    public GoalDialog(@NonNull Context context, List<BankAccount> bankAccounts, SavingGoal goalToEdit) {
        super(context);
        this.bankAccounts = bankAccounts;
        this.existingGoal = goalToEdit;
        this.isEditMode = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_goal);

        initViews();
        setupSpinners();
        setupClickListeners();

        if (isEditMode) {
            setupEditMode();
        } else {
            setupCreateMode();
        }
    }

    private void initViews() {
        tvDialogTitle = findViewById(R.id.tv_dialog_title);
        etGoalName = findViewById(R.id.et_goal_name);
        etCategory = findViewById(R.id.et_category);
        etTargetAmount = findViewById(R.id.et_target_amount);
        etDeadline = findViewById(R.id.et_deadline);
        etDeadline.setOnClickListener(v -> showDatePicker());
        etInitialAmount = findViewById(R.id.et_initial_amount);
        spinnerIcon = findViewById(R.id.spinner_icon);
        spinnerBankAccount = findViewById(R.id.spinner_bank_account);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
    }

    private void setupSpinners() {
        String[] icons = {"rocket", "bank", "car", "home", "global", "heart", "book", "trophy", "gift"};
        ArrayAdapter<String> iconAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, icons);
        iconAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIcon.setAdapter(iconAdapter);

        List<String> bankDisplayNames = new ArrayList<>();
        for (BankAccount account : bankAccounts) {
            bankDisplayNames.add(account.getDisplayName());
        }
        ArrayAdapter<String> bankAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, bankDisplayNames);
        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBankAccount.setAdapter(bankAdapter);
    }

    private void setupCreateMode() {
        tvDialogTitle.setText("Create New Goal");
        btnSave.setText("Create");

        etGoalName.setText("");
        etCategory.setText("");
        etTargetAmount.setText("");
        etDeadline.setText("");
        etInitialAmount.setText("");
        spinnerIcon.setSelection(0);
        spinnerBankAccount.setSelection(0);
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();

        if (!etDeadline.getText().toString().isEmpty()) {
            String[] parts = etDeadline.getText().toString().split("-");
            if (parts.length == 3) {
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]) - 1; // Calendar month is 0-based
                int day = Integer.parseInt(parts[2]);
                calendar.set(year, month, day);
            }
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, selectedYear, selectedMonth, selectedDay) -> {
            // Format YYYY-MM-DD
            String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
            etDeadline.setText(formattedDate);
        }, year, month, day);

        datePickerDialog.show();
    }


    private void setupEditMode() {
        tvDialogTitle.setText("Edit Goal");
        btnSave.setText("Update");

        if (existingGoal != null) {
            etGoalName.setText(existingGoal.getName());
            etCategory.setText(existingGoal.getCategory());
            etTargetAmount.setText(String.valueOf(existingGoal.getTargetAmount()));
            etDeadline.setText(existingGoal.getDeadline());
            etInitialAmount.setText(String.valueOf(existingGoal.getCurrentAmount()));

            String[] icons = {"rocket", "bank", "car", "home", "global", "heart", "book", "trophy", "gift"};
            for (int i = 0; i < icons.length; i++) {
                if (icons[i].equals(existingGoal.getIcon())) {
                    spinnerIcon.setSelection(i);
                    break;
                }
            }

            for (int i = 0; i < bankAccounts.size(); i++) {
                if (bankAccounts.get(i).getId().equals(existingGoal.getBankAccountId())) {
                    spinnerBankAccount.setSelection(i);
                    break;
                }
            }
        }
    }

    private void setupClickListeners() {
        btnSave.setOnClickListener(v -> {
            if (isEditMode) {
                updateGoal();
            } else {
                createGoal();
            }
        });
        btnCancel.setOnClickListener(v -> dismiss());
    }

    private void createGoal() {
        String name = etGoalName.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String targetAmountStr = etTargetAmount.getText().toString().trim();
        String deadline = etDeadline.getText().toString().trim();
        String initialAmountStr = etInitialAmount.getText().toString().trim();

        if (name.isEmpty() || targetAmountStr.isEmpty() || deadline.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            long targetAmount = Long.parseLong(targetAmountStr);
            long initialAmount = initialAmountStr.isEmpty() ? 0 : Long.parseLong(initialAmountStr);
            String selectedIcon = spinnerIcon.getSelectedItem().toString();
            String selectedBankAccountId = bankAccounts.get(spinnerBankAccount.getSelectedItemPosition()).getId();

            SavingGoal newGoal = new SavingGoal(
                    goalIdCounter++, name, selectedIcon, targetAmount, initialAmount,
                    deadline, category, "", new ArrayList<>(),
                    new AutoContribution(0, "none"), "active", selectedBankAccountId
            );

            if (listener != null) {
                listener.onGoalCreated(newGoal);
            }
            dismiss();
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please enter valid amounts", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateGoal() {
        String name = etGoalName.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String targetAmountStr = etTargetAmount.getText().toString().trim();
        String deadline = etDeadline.getText().toString().trim();
        String initialAmountStr = etInitialAmount.getText().toString().trim();

        if (name.isEmpty() || targetAmountStr.isEmpty() || deadline.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            long targetAmount = Long.parseLong(targetAmountStr);
            long initialAmount = initialAmountStr.isEmpty() ? 0 : Long.parseLong(initialAmountStr);
            String selectedIcon = spinnerIcon.getSelectedItem().toString();
            String selectedBankAccountId = bankAccounts.get(spinnerBankAccount.getSelectedItemPosition()).getId();

            existingGoal.setName(name);
            existingGoal.setCategory(category);
            existingGoal.setTargetAmount(targetAmount);
            existingGoal.setCurrentAmount(initialAmount);
            existingGoal.setDeadline(deadline);
            existingGoal.setIcon(selectedIcon);
            existingGoal.setBankAccountId(selectedBankAccountId);

            if (listener != null) {
                listener.onGoalUpdated(existingGoal);
            }
            dismiss();
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please enter valid amounts", Toast.LENGTH_SHORT).show();
        }
    }

    public void setOnGoalActionListener(OnGoalActionListener listener) {
        this.listener = listener;
    }

    public void setOnGoalCreatedListener(OnGoalCreatedListener listener) {
        this.listener = new OnGoalActionListener() {
            @Override
            public void onGoalCreated(SavingGoal goal) {
                listener.onGoalCreated(goal);
            }

            @Override
            public void onGoalUpdated(SavingGoal goal) {
            }
        };
    }

    public void setOnGoalUpdatedListener(OnGoalUpdatedListener listener) {
        this.listener = new OnGoalActionListener() {
            @Override
            public void onGoalCreated(SavingGoal goal) {
            }

            @Override
            public void onGoalUpdated(SavingGoal goal) {
                listener.onGoalUpdated(goal);
            }
        };
    }

    public interface OnGoalCreatedListener {
        void onGoalCreated(SavingGoal goal);
    }

    public interface OnGoalUpdatedListener {
        void onGoalUpdated(SavingGoal goal);
    }
}