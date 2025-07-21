package com.example.finex_mobile.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finex_mobile.R;
import com.example.finex_mobile.screens.goals.SavingsGoalsFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BottomNavigationActivity extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        bottomAppBar = findViewById(R.id.bottom_app_bar);
        fab = findViewById(R.id.fab_add_budget);

        setSupportActionBar(bottomAppBar);

        // Default fragment
        loadFragment(new SavingsGoalsFragment());

        bottomAppBar.setOnMenuItemClickListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_saving) {
                selectedFragment = new SavingsGoalsFragment();
//            } else if (id == R.id.nav_analysis) {
//                selectedFragment = new AnalysisFragment();
//            } else if (id == R.id.nav_advice) {
//                selectedFragment = new AdviceFragment();
//            } else if (id == R.id.nav_profile) {
//                selectedFragment = new ProfileFragment();
            }

            return loadFragment(selectedFragment);
        });

//        fab.setOnClickListener(v -> {
//            loadFragment(new BudgetFragment());
//        });
        fab.setOnClickListener(v -> {
            Toast.makeText(this, "Budget feature coming soon!", Toast.LENGTH_SHORT).show();
        });

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
