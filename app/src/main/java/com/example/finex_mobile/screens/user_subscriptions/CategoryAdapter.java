package com.example.finex_mobile.screens.user_subscriptions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VH> {
    public interface OnCategoryActionListener {
        void onEdit(Category category);
        void onDelete(Category category);
    }

    private final List<Category> data;
    private final OnCategoryActionListener listener;

    public CategoryAdapter(List<Category> data, OnCategoryActionListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int i) {
        Category c = data.get(i);
        h.txtIndex.setText(String.valueOf(i+1));
        h.txtName.setText(c.getName());
        h.txtLang.setText(c.getLanguage());
        h.btnEdit.setOnClickListener(v -> listener.onEdit(c));
        h.btnDelete.setOnClickListener(v -> listener.onDelete(c));
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtIndex, txtName, txtLang;
        ImageButton btnEdit, btnDelete;
        VH(@NonNull View v) {
            super(v);
            txtIndex = v.findViewById(R.id.text_index);
            txtName = v.findViewById(R.id.text_name);
            txtLang = v.findViewById(R.id.text_language);
            btnEdit = v.findViewById(R.id.btn_edit);
            btnDelete = v.findViewById(R.id.btn_delete);
        }
    }
}
