package com.example.finex_mobile.screens.goals;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finex_mobile.R;

public class SavingsGoalsFragment extends Fragment {

    public SavingsGoalsFragment() {
        // Bắt buộc phải có constructor trống để Fragment hoạt động đúng
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_savings_goals, container, false);
    }
}
