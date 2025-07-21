package com.example.finex_mobile.screens.user_subscriptions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.Transaction;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    public interface OnTransactionActionListener {
        void onEdit(Transaction transaction);
        void onDelete(Transaction transaction);
    }

    private List<Transaction> transactions;
    private OnTransactionActionListener listener;

    public TransactionAdapter(List<Transaction> transactions, OnTransactionActionListener listener) {
        this.transactions = transactions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.textNo.setText(String.valueOf(position + 1));
        holder.textContent.setText(transaction.getContent());
        holder.textCreatedAt.setText(transaction.getCreatedAt());
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(transaction));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(transaction));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void updateTransactions(List<Transaction> newTransactions) {
        this.transactions = newTransactions;
        notifyDataSetChanged();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView textNo, textContent, textCreatedAt, btnEdit, btnDelete;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            textNo = itemView.findViewById(R.id.text_no);
            textContent = itemView.findViewById(R.id.text_content);
            textCreatedAt = itemView.findViewById(R.id.text_created_at);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
} 