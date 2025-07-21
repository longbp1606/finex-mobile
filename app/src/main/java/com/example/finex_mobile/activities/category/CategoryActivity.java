package com.example.finex_mobile.activities.category;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finex_mobile.R;
import com.example.finex_mobile.database.CategoryDAO;
import com.example.finex_mobile.entities.category.Category;
import com.example.finex_mobile.adapters.CategoryAdapter;

import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryActionListener {
    private RecyclerView recycler;
    private ImageButton btnAdd;
    private CategoryDAO dao;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        dao = new CategoryDAO(this);
        recycler = findViewById(R.id.recycler_category);
        btnAdd = findViewById(R.id.btn_add_category);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        loadData();
//        progressBar = findViewById(R.id.progress_bar);

        btnAdd.setOnClickListener(v -> showDialog(null));
    }

    private void loadData() {
        List<Category> list = dao.getAllCategories();
        adapter = new CategoryAdapter(list, this);
        recycler.setAdapter(adapter);
    }

    private void showDialog(Category cat) {
        boolean isEdit = cat != null;
        EditText input = new EditText(this);
        input.setHint("Name");
        if (isEdit) input.setText(cat.getName());

        new AlertDialog.Builder(this)
                .setTitle(isEdit ? "Edit Category" : "Add Category")
                .setView(input)
                .setPositiveButton("Save", (d, w) -> {
                    String name = input.getText().toString().trim();
                    if (name.isEmpty()) return;
                    if (isEdit) {
                        cat.setName(name);
                        dao.updateCategory(cat);
                    } else {
                        dao.insertCategory(new Category(null, name, "EN"));
                    }
                    loadData();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onEdit(Category category) {
        showDialog(category);
    }

    @Override
    public void onDelete(Category category) {
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Delete this category?")
                .setPositiveButton("Yes", (d, w) -> {
                    dao.deleteCategory(category.getId());
                    loadData();
                })
                .setNegativeButton("No", null)
                .show();
    }
}

