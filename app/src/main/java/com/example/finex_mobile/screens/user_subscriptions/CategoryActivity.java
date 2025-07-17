package com.example.finex_mobile.screens.user_subscriptions;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.Category;

import java.util.ArrayList;
import java.util.List;
public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView = findViewById(R.id.recycler_category);
        progressBar = findViewById(R.id.progress_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        fetchCategories();
    }

    private void fetchCategories() {
        progressBar.setVisibility(View.VISIBLE);

        // TODO: Replace with real API call using Retrofit or similar
        recyclerView.postDelayed(() -> {
            List<Category> dummyData = new ArrayList<>();
            dummyData.add(new Category("1", "Groceries", "EN"));
            dummyData.add(new Category("2", "Transport", "EN"));
            dummyData.add(new Category("3", "Ăn uống", "VI"));

            adapter = new CategoryAdapter(dummyData);
            recyclerView.setAdapter(adapter);

            progressBar.setVisibility(View.GONE);
        }, 1000);
    }
}
