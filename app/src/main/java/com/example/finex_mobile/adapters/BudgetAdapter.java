package com.example.finex_mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.budget.Budget;
import com.example.finex_mobile.activities.transactions.TransactionActivity;

import java.util.List;
import android.graphics.Paint;
import android.app.AlertDialog;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {
    public interface OnBudgetActionListener {
        void onDetails(Budget budget);
        void onEdit(Budget budget);
        void onDelete(Budget budget);
    }

    private List<Budget> budgets;
    private OnBudgetActionListener listener;

    public BudgetAdapter(List<Budget> budgets, OnBudgetActionListener listener) {
        this.budgets = budgets;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_budget, parent, false);
        return new BudgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        Budget budget = budgets.get(position);
        holder.title.setText(budget.getTitle());
        holder.currencyUnit.setText(budget.getCurrencyUnit());
        holder.money.setText(String.format("$%.2f", budget.getMoney()));
        holder.createdAt.setText(budget.getCreatedAt());

        // Set underline for Details and Edit
        holder.btnDetails.setPaintFlags(holder.btnDetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.btnEdit.setPaintFlags(holder.btnEdit.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        holder.btnDetails.setOnClickListener(v -> {
            android.content.Context context = holder.itemView.getContext();
            android.content.Intent intent = new android.content.Intent(context, TransactionActivity.class);
            intent.putExtra("budgetId", budget.getId());
            intent.putExtra("budgetTitle", budget.getTitle());
            context.startActivity(intent);
        });
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(budget));
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                .setTitle("Delete Budget")
                .setMessage("Are you sure you want to delete this budget?")
                .setPositiveButton("Delete", (dialog, which) -> listener.onDelete(budget))
                .setNegativeButton("Cancel", null)
                .show();
        });
    }

    @Override
    public int getItemCount() {
        return budgets.size();
    }

    public static class BudgetViewHolder extends RecyclerView.ViewHolder {
        TextView title, currencyUnit, money, createdAt;
        TextView btnDetails, btnEdit;
        ImageView btnDelete;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            currencyUnit = itemView.findViewById(R.id.text_currency_unit);
            money = itemView.findViewById(R.id.text_money);
            createdAt = itemView.findViewById(R.id.text_created_at);
            btnDetails = itemView.findViewById(R.id.btn_details);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
} 