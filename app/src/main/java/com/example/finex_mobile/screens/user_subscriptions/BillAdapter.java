package com.example.finex_mobile.screens.user_subscriptions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.Bill;

import java.util.List;
public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    public interface OnBillActionListener {
        void onEdit(Bill bill);
        void onDelete(int id);
        void onMarkPaid(int id);
    }

    private List<Bill> bills;
    private OnBillActionListener listener;

    public BillAdapter(List<Bill> bills, OnBillActionListener listener) {
        this.bills = bills;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill = bills.get(position);
        holder.name.setText(bill.getName());
        holder.category.setText(bill.getCategory());
        holder.amount.setText("$" + String.format("%.2f", bill.getAmount()));
        holder.dueDate.setText(bill.getDueDate());
        holder.status.setText(bill.getStatus().toUpperCase());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(bill));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(bill.getId()));
        holder.btnPay.setEnabled(!"paid".equals(bill.getStatus()));
        holder.btnPay.setOnClickListener(v -> listener.onMarkPaid(bill.getId()));
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView name, category, amount, dueDate, status;
        Button btnEdit, btnDelete, btnPay;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            category = itemView.findViewById(R.id.text_category);
            amount = itemView.findViewById(R.id.text_amount);
            dueDate = itemView.findViewById(R.id.text_due_date);
            status = itemView.findViewById(R.id.text_status);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnPay = itemView.findViewById(R.id.btn_pay);
        }
    }
}
