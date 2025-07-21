package com.example.finex_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.saving_goals.SavingGoal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Date;

public class SavingsGoalAdapter extends RecyclerView.Adapter<SavingsGoalAdapter.ViewHolder> {

    private List<SavingGoal> goals;
    private Context context;
    private OnGoalClickListener listener;

    public interface OnGoalClickListener {
        void onAddContribution(SavingGoal goal);
        void onEditGoal(SavingGoal goal);
        void onDeleteGoal(SavingGoal goal);
    }

    public SavingsGoalAdapter(Context context, List<SavingGoal> goals) {
        this.context = context;
        this.goals = goals;
    }

    public void setOnGoalClickListener(OnGoalClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saving_goal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SavingGoal goal = goals.get(position);
        holder.bind(goal);
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public void updateGoals(List<SavingGoal> newGoals) {
        this.goals = newGoals;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconImageView;
        private TextView nameTextView;
        private TextView categoryTextView;
        private TextView currentAmountTextView;
        private TextView targetAmountTextView;
        private ProgressBar progressBar;
        private TextView progressTextView;
        private TextView deadlineTextView;
        private TextView notesTextView;
        private Button addContributionButton;
        private Button editButton;
        private Button deleteButton;
        private LinearLayout autoSavingLayout;
        private TextView autoSavingTextView;
        private LinearLayout completionBadgeLayout;
        private LinearLayout actionButtonsLayout;
        private View iconBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iv_goal_icon);
            iconBackground = itemView.findViewById(R.id.view_icon_background);
            nameTextView = itemView.findViewById(R.id.tv_goal_name);
            categoryTextView = itemView.findViewById(R.id.tv_goal_category);
            currentAmountTextView = itemView.findViewById(R.id.tv_current_amount);
            targetAmountTextView = itemView.findViewById(R.id.tv_target_amount);
            progressBar = itemView.findViewById(R.id.progress_goal);
            progressTextView = itemView.findViewById(R.id.tv_progress_percent);
            deadlineTextView = itemView.findViewById(R.id.tv_goal_deadline);
            notesTextView = itemView.findViewById(R.id.tv_goal_notes);
            addContributionButton = itemView.findViewById(R.id.btn_add_funds);
            editButton = itemView.findViewById(R.id.btn_edit_goal);
            deleteButton = itemView.findViewById(R.id.btn_delete_goal);
            autoSavingLayout = itemView.findViewById(R.id.layout_auto_saving);
            autoSavingTextView = itemView.findViewById(R.id.tv_auto_saving);
            completionBadgeLayout = itemView.findViewById(R.id.layout_completion_badge);
            actionButtonsLayout = itemView.findViewById(R.id.layout_action_buttons);
        }

        public void bind(SavingGoal goal) {
            nameTextView.setText(goal.getName());
            categoryTextView.setText(goal.getCategory());

            String formattedCurrent = formatCurrency(goal.getCurrentAmount());
            String formattedTarget = formatCurrency(goal.getTargetAmount());
            currentAmountTextView.setText(formattedCurrent);
            targetAmountTextView.setText("of " + formattedTarget);

            int progress = calculateProgress(goal.getCurrentAmount(), goal.getTargetAmount());
            progressBar.setProgress(progress);
            progressTextView.setText(progress + "%");

            setGoalIcon(goal.getIcon());
            setIconBackground(goal.getCategory());

            boolean isCompleted = goal.isCompleted() || progress >= 100;

            if (isCompleted) {
                completionBadgeLayout.setVisibility(View.VISIBLE);

                actionButtonsLayout.setVisibility(View.GONE);

                deadlineTextView.setText("Completed on: " + getCurrentDate());

                autoSavingLayout.setVisibility(View.GONE);

                progressBar.setProgressTintList(context.getResources().getColorStateList(R.color.black));
            } else {
                completionBadgeLayout.setVisibility(View.GONE);

                actionButtonsLayout.setVisibility(View.VISIBLE);

                if (goal.getDeadline() != null && !goal.getDeadline().isEmpty()) {
                    deadlineTextView.setText("Target date: " + goal.getDeadline());
                } else {
                    deadlineTextView.setText("No deadline set");
                }

                if (goal.getAutoContribution() != null && goal.getAutoContribution().getAmount() > 0) {
                    autoSavingLayout.setVisibility(View.VISIBLE);
                    autoSavingTextView.setText("Auto-saving " + formatCurrency(goal.getAutoContribution().getAmount()) + "/" + goal.getAutoContribution().getFrequency());
                } else {
                    autoSavingLayout.setVisibility(View.GONE);
                }

                progressBar.setProgressTintList(context.getResources().getColorStateList(R.color.black));
            }

            if (goal.getNotes() != null && !goal.getNotes().isEmpty()) {
                notesTextView.setVisibility(View.VISIBLE);
                notesTextView.setText(goal.getNotes());
            } else {
                notesTextView.setVisibility(View.GONE);
            }

            addContributionButton.setOnClickListener(v -> {
                if (listener != null) listener.onAddContribution(goal);
            });

            editButton.setOnClickListener(v -> {
                if (listener != null) listener.onEditGoal(goal);
            });

            deleteButton.setOnClickListener(v -> {
                if (listener != null) listener.onDeleteGoal(goal);
            });
        }

        private void setGoalIcon(String iconType) {
            int iconRes;
            switch (iconType != null ? iconType : "rocket") {
                case "bank":
                    iconRes = R.drawable.ic_bank;
                    break;
                case "car":
                    iconRes = R.drawable.ic_car;
                    break;
                case "home":
                    iconRes = R.drawable.ic_home;
                    break;
                case "global":
                    iconRes = R.drawable.ic_global;
                    break;
                case "heart":
                    iconRes = R.drawable.ic_heart;
                    break;
                case "book":
                    iconRes = R.drawable.ic_book;
                    break;
                case "trophy":
                    iconRes = R.drawable.ic_trophy;
                    break;
                case "gift":
                    iconRes = R.drawable.ic_gift;
                    break;
                default:
                    iconRes = R.drawable.ic_rocket;
                    break;
            }
            iconImageView.setImageResource(iconRes);
        }

        private void setIconBackground(String category) {
            if (iconBackground == null) return;

            int backgroundRes;
            switch (category != null ? category.toLowerCase() : "other") {
                case "emergency":
                    backgroundRes = R.drawable.shape_category_tag;
                    break;
                case "travel":
                    backgroundRes = R.drawable.shape_category_tag;
                    break;
                case "education":
                    backgroundRes = R.drawable.shape_category_tag;
                    break;
                case "investment":
                    backgroundRes = R.drawable.shape_category_tag;
                    break;
                case "house":
                    backgroundRes = R.drawable.shape_category_tag;
                    break;
                default:
                    backgroundRes = R.drawable.shape_category_tag;
                    break;
            }
            iconBackground.setBackgroundResource(backgroundRes);
        }

        private int calculateProgress(long current, long target) {
            if (target <= 0) return 0;
            return (int) ((current * 100) / target);
        }

        private String formatCurrency(long amount) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return formatter.format(amount);
        }

        private String getCurrentDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return sdf.format(new Date());
        }
    }
}