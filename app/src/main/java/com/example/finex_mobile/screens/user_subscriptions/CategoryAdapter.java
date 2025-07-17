package com.example.finex_mobile.screens.user_subscriptions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.Category;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>  {
    private final List<Category> categories;
    private final Set<String> selectedIds = new HashSet<>();

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    public Set<String> getSelectedIds() {
        return selectedIds;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.indexText.setText(String.valueOf(position + 1));
        holder.nameText.setText(category.getName());
        holder.languageText.setText(category.getLanguage());
        holder.checkBox.setChecked(selectedIds.contains(category.getId()));

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) selectedIds.add(category.getId());
            else selectedIds.remove(category.getId());
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView indexText, nameText, languageText;
        CheckBox checkBox;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            indexText = itemView.findViewById(R.id.text_index);
            nameText = itemView.findViewById(R.id.text_name);
            languageText = itemView.findViewById(R.id.text_language);
            checkBox = itemView.findViewById(R.id.checkbox_select);
        }
    }
}
