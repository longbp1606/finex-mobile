package com.example.finex_mobile.screens.user_subscriptions;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.SavingGoal;
import com.example.finex_mobile.entities.Contribution;
import com.example.finex_mobile.entities.AutoContribution;
import com.example.finex_mobile.entities.BankAccount;
import com.example.finex_mobile.entities.RecentContribution;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class SavingsGoalsActivity extends AppCompatActivity
        implements SavingsGoalAdapter.OnGoalClickListener {

    private RecyclerView recyclerView;
    private SavingsGoalAdapter adapter;
    private RecentContributionAdapter recentAdapter;
    private List<SavingGoal> savingGoals;
    private List<SavingGoal> allGoals; // Backup for filtering
    private List<BankAccount> bankAccounts;
    private List<RecentContribution> recentContributions;
    private FloatingActionButton fabAddGoal;
    private LinearLayout emptyState;

    // Header views
    private ImageView ivBack, ivFilter, ivSearch;

    // Summary cards
    private TextView tvTotalSaved, tvOverallProgress, tvActiveGoals;

    // Filter tabs
    private TextView tabAll, tabActive, tabCompleted, tabRecent;
    private String currentFilter = "all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_goals);

        initViews();
        initData();
        setupRecyclerView();
        setupClickListeners();
        setupTabListeners();
        updateSummaryCards();
        checkEmptyState();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv_savings_goals);
        fabAddGoal = findViewById(R.id.fab_add_goal);
        emptyState = findViewById(R.id.empty_state);

        // Header views
        ivBack = findViewById(R.id.ic_back);
        ivSearch = findViewById(R.id.ic_search);

        // Summary cards
        tvTotalSaved = findViewById(R.id.tv_total_saved);
        tvOverallProgress = findViewById(R.id.tv_overall_progress);
        tvActiveGoals = findViewById(R.id.tv_active_goals);

        // Filter tabs
        tabAll = findViewById(R.id.tab_all);
        tabActive = findViewById(R.id.tab_active);
        tabCompleted = findViewById(R.id.tab_completed);
        tabRecent = findViewById(R.id.tab_recent);
    }

    private void initData() {
        savingGoals = new ArrayList<>();
        allGoals = new ArrayList<>();
        bankAccounts = new ArrayList<>();
        recentContributions = new ArrayList<>();

        bankAccounts.add(new BankAccount("acc1", "Checking", "VCB", "checking",
                5000000, "VND", true, "2024-01-15"));
        bankAccounts.add(new BankAccount("acc2", "Savings", "TCB", "savings",
                10000000, "VND", true, "2024-01-15"));

        loadSampleGoals();

        loadRecentContributions();
    }

    private void loadSampleGoals() {
        List<Contribution> contributions1 = new ArrayList<>();
        contributions1.add(new Contribution("2024-01-01", 2000000, "acc1"));
        contributions1.add(new Contribution("2024-01-15", 500000, "acc1"));

        SavingGoal emergencyGoal = new SavingGoal(
                1, "Emergency Fund", "bank", 10000000, 2500000,
                "2024-12-31", "Emergency", "Emergency fund for unexpected expenses",
                contributions1, new AutoContribution(500000, "monthly"),
                "active", "acc1"
        );

        List<Contribution> contributions2 = new ArrayList<>();
        contributions2.add(new Contribution("2024-01-01", 15000000, "acc2"));

        SavingGoal carGoal = new SavingGoal(
                2, "New Car", "car", 50000000, 15000000,
                "2025-06-30", "Transportation", "Save for a new car",
                contributions2, new AutoContribution(2000000, "monthly"),
                "active", "acc2"
        );

        List<Contribution> contributions3 = new ArrayList<>();
        contributions3.add(new Contribution("2024-01-01", 8000000, "acc1"));
        contributions3.add(new Contribution("2024-02-01", 2000000, "acc1"));

        SavingGoal vacationGoal = new SavingGoal(
                3, "Vacation Fund", "global", 10000000, 10000000,
                "2024-06-30", "Travel", "Summer vacation to Japan",
                contributions3, new AutoContribution(0, "none"),
                "completed", "acc1"
        );

        allGoals.add(emergencyGoal);
        allGoals.add(carGoal);
        allGoals.add(vacationGoal);

        savingGoals.addAll(allGoals);
    }

    private void loadRecentContributions() {
        recentContributions.clear();

        for (SavingGoal goal : allGoals) {
            if (goal.getContributions() != null) {
                for (Contribution contribution : goal.getContributions()) {
                    RecentContribution recentContribution = new RecentContribution(
                            contribution.getDate(),
                            contribution.getAmount(),
                            contribution.getBankAccountId(),
                            goal.getName(),
                            goal.getIcon(),
                            goal.getId()
                    );

                    BankAccount bankAccount = getBankAccountById(contribution.getBankAccountId());
                    if (bankAccount != null) {
                        recentContribution.setBankAccountName(bankAccount.getAccountName());
                        recentContribution.setBankName(bankAccount.getBankName());
                    }

                    recentContributions.add(recentContribution);
                }
            }
        }

        Collections.sort(recentContributions, new Comparator<RecentContribution>() {
            @Override
            public int compare(RecentContribution o1, RecentContribution o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
    }

    private BankAccount getBankAccountById(String bankAccountId) {
        for (BankAccount account : bankAccounts) {
            if (account.getId().equals(bankAccountId)) {
                return account;
            }
        }
        return null;
    }

    private void setupRecyclerView() {
        adapter = new SavingsGoalAdapter(this, savingGoals);
        adapter.setOnGoalClickListener(this);

        recentAdapter = new RecentContributionAdapter(this, recentContributions);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupClickListeners() {
        fabAddGoal.setOnClickListener(v -> showCreateGoalDialog());
        ivBack.setOnClickListener(v -> onBackPressed());
        ivSearch.setOnClickListener(v -> showSearchDialog());
    }

    private void setupTabListeners() {
        tabAll.setOnClickListener(v -> selectTab("all"));
        tabActive.setOnClickListener(v -> selectTab("active"));
        tabCompleted.setOnClickListener(v -> selectTab("completed"));
        tabRecent.setOnClickListener(v -> selectTab("recent"));
    }

    private void selectTab(String filter) {
        currentFilter = filter;

        resetTabStyles();

        switch (filter) {
            case "all":
                tabAll.setTextColor(getResources().getColor(R.color.color_selector_tab_text));
                tabAll.setTextSize(14);
                tabAll.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
            case "active":
                tabActive.setTextColor(getResources().getColor(R.color.color_selector_tab_text));
                tabActive.setTextSize(14);
                tabActive.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
            case "completed":
                tabCompleted.setTextColor(getResources().getColor(R.color.color_selector_tab_text));
                tabCompleted.setTextSize(14);
                tabCompleted.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
            case "recent":
                tabRecent.setTextColor(getResources().getColor(R.color.color_selector_tab_text));
                tabRecent.setTextSize(14);
                tabRecent.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
        }

        if (filter.equals("recent")) {
            showRecentContributions();
        } else {
            showGoals();
            filterGoals(filter);
        }
    }

    private void resetTabStyles() {
        int defaultColor = getResources().getColor(android.R.color.darker_gray);

        tabAll.setTextColor(defaultColor);
        tabAll.setTypeface(null, android.graphics.Typeface.NORMAL);

        tabActive.setTextColor(defaultColor);
        tabActive.setTypeface(null, android.graphics.Typeface.NORMAL);

        tabCompleted.setTextColor(defaultColor);
        tabCompleted.setTypeface(null, android.graphics.Typeface.NORMAL);

        tabRecent.setTextColor(defaultColor);
        tabRecent.setTypeface(null, android.graphics.Typeface.NORMAL);
    }

    private void showGoals() {
        recyclerView.setAdapter(adapter);
        fabAddGoal.setVisibility(View.VISIBLE);
    }

    private void showRecentContributions() {
        loadRecentContributions();
        recentAdapter.updateContributions(recentContributions);
        recyclerView.setAdapter(recentAdapter);
        fabAddGoal.setVisibility(View.GONE);
        checkEmptyState();
    }

    private void filterGoals(String filter) {
        List<SavingGoal> filteredGoals = new ArrayList<>();

        switch (filter) {
            case "all":
                filteredGoals.addAll(allGoals);
                break;
            case "active":
                for (SavingGoal goal : allGoals) {
                    if ("active".equals(goal.getStatus())) {
                        filteredGoals.add(goal);
                    }
                }
                break;
            case "completed":
                for (SavingGoal goal : allGoals) {
                    if ("completed".equals(goal.getStatus())) {
                        filteredGoals.add(goal);
                    }
                }
                break;
        }

        savingGoals.clear();
        savingGoals.addAll(filteredGoals);
        adapter.updateGoals(savingGoals);
        checkEmptyState();
    }

    private void updateSummaryCards() {
        long totalSaved = 0;
        long totalTarget = 0;
        int activeCount = 0;

        for (SavingGoal goal : allGoals) {
            totalSaved += goal.getCurrentAmount();
            totalTarget += goal.getTargetAmount();
            if ("active".equals(goal.getStatus())) {
                activeCount++;
            }
        }

        int overallProgress = totalTarget > 0 ? (int) ((totalSaved * 100) / totalTarget) : 0;

        tvTotalSaved.setText(formatCurrency(totalSaved));
        tvOverallProgress.setText(overallProgress + "%");
        tvActiveGoals.setText(activeCount + "/" + allGoals.size());
    }

    private void checkEmptyState() {
        boolean isEmpty = false;

        if (currentFilter.equals("recent")) {
            isEmpty = recentContributions.isEmpty();
        } else {
            isEmpty = savingGoals.isEmpty();
        }

        if (isEmpty) {
            emptyState.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private String formatCurrency(long amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(amount);
    }

    private void showCategoryFilter() {
        Toast.makeText(this, "Category filter - Coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void showAmountFilter() {
        Toast.makeText(this, "Amount filter - Coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void showSearchDialog() {
        Toast.makeText(this, "Search functionality - Coming soon!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddContribution(SavingGoal goal) {
        showAddContributionDialog(goal);
    }

    @Override
    public void onEditGoal(SavingGoal goal) {
        showEditGoalDialog(goal);
    }

    @Override
    public void onDeleteGoal(SavingGoal goal) {
        showDeleteConfirmDialog(goal);
    }

    private void showCreateGoalDialog() {
        GoalDialog dialog = new GoalDialog(this, bankAccounts);
        dialog.setOnGoalCreatedListener(new GoalDialog.OnGoalCreatedListener() {
            @Override
            public void onGoalCreated(SavingGoal newGoal) {
                allGoals.add(newGoal);

                if ("all".equals(currentFilter) || "active".equals(newGoal.getStatus())) {
                    savingGoals.add(newGoal);
                }

                adapter.updateGoals(savingGoals);
                updateSummaryCards();
                checkEmptyState();
                Toast.makeText(SavingsGoalsActivity.this, "Goal created successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void showEditGoalDialog(SavingGoal goal) {
        GoalDialog dialog = new GoalDialog(this, bankAccounts, goal);
        dialog.setOnGoalUpdatedListener(new GoalDialog.OnGoalUpdatedListener() {
            @Override
            public void onGoalUpdated(SavingGoal updatedGoal) {
                for (int i = 0; i < allGoals.size(); i++) {
                    if (allGoals.get(i).getId() == updatedGoal.getId()) {
                        allGoals.set(i, updatedGoal);
                        break;
                    }
                }

                filterGoals(currentFilter);
                updateSummaryCards();
                Toast.makeText(SavingsGoalsActivity.this, "Goal updated successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void showAddContributionDialog(SavingGoal goal) {
        AddContributionDialog dialog = new AddContributionDialog(this, goal, bankAccounts);

        dialog.setOnContributionAddedListener(contribution -> {
            if (goal.getContributions() == null) {
                goal.setContributions(new ArrayList<>());
            }

            goal.getContributions().add(contribution);

            long updatedAmount = goal.getCurrentAmount() + contribution.getAmount();
            goal.setCurrentAmount(updatedAmount);

            if (updatedAmount >= goal.getTargetAmount()) {
                goal.setStatus("completed");
            }

            for (int i = 0; i < allGoals.size(); i++) {
                if (allGoals.get(i).getId() == goal.getId()) {
                    allGoals.set(i, goal);
                    break;
                }
            }

            adapter.updateGoals(savingGoals);
            updateSummaryCards();

            if (currentFilter.equals("recent")) {
                loadRecentContributions();
                recentAdapter.updateContributions(recentContributions);
            }

            Toast.makeText(this, "Added ₫" + contribution.getAmount() + " to " + goal.getName(), Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }

    private void showDeleteConfirmDialog(SavingGoal goal) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Goal")
                .setMessage("Are you sure you want to delete \"" + goal.getName() + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    allGoals.remove(goal);
                    savingGoals.remove(goal);
                    adapter.updateGoals(savingGoals);
                    updateSummaryCards();
                    checkEmptyState();

                    if (currentFilter.equals("recent")) {
                        loadRecentContributions();
                        recentAdapter.updateContributions(recentContributions);
                    }

                    Toast.makeText(this, "Goal deleted successfully!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}