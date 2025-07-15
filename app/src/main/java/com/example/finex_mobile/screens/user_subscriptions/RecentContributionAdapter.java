package com.example.finex_mobile.screens.user_subscriptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.RecentContribution;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecentContributionAdapter extends RecyclerView.Adapter<RecentContributionAdapter.ViewHolder> {

    private Context context;
    private List<RecentContribution> contributions;

    public RecentContributionAdapter(Context context, List<RecentContribution> contributions) {
        this.context = context;
        this.contributions = contributions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recent_contribute, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentContribution contribution = contributions.get(position);

        holder.tvGoalName.setText(contribution.getGoalName());

        holder.tvDateTime.setText(formatDateTime(contribution.getDate()));

        holder.tvBankAccount.setText("From " + contribution.getBankName() + " " + contribution.getBankAccountName());

        holder.tvAmount.setText("+" + formatCurrency(contribution.getAmount()));

        setGoalIcon(holder.ivGoalIcon, contribution.getGoalIcon());
    }

    @Override
    public int getItemCount() {
        return contributions.size();
    }

    private String formatDateTime(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = inputFormat.parse(dateStr);

            Calendar today = Calendar.getInstance();
            Calendar contributionDate = Calendar.getInstance();
            contributionDate.setTime(date);

            if (today.get(Calendar.YEAR) == contributionDate.get(Calendar.YEAR) &&
                    today.get(Calendar.DAY_OF_YEAR) == contributionDate.get(Calendar.DAY_OF_YEAR)) {
                return "Today, 2:30 PM";
            } else {
                SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                return outputFormat.format(date);
            }
        } catch (Exception e) {
            return dateStr;
        }
    }

    private String formatCurrency(long amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(amount);
    }

    private void setGoalIcon(ImageView imageView, String iconType) {
        int iconRes;
        int colorRes;

        switch (iconType) {
            case "bank":
                iconRes = R.drawable.ic_bank;
                colorRes = R.color.primary_color;
                break;
            case "car":
                iconRes = R.drawable.ic_car;
                colorRes = R.color.primary_color;
                break;
            case "global":
                iconRes = R.drawable.ic_global;
                colorRes = R.color.primary_color;
                break;
            default:
                iconRes = R.drawable.ic_bank;
                colorRes = R.color.primary_color;
                break;
        }

        imageView.setImageResource(iconRes);
        imageView.setColorFilter(context.getResources().getColor(colorRes));
    }

    public void updateContributions(List<RecentContribution> newContributions) {
        this.contributions = newContributions;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGoalIcon;
        TextView tvGoalName;
        TextView tvDateTime;
        TextView tvBankAccount;
        TextView tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGoalIcon = itemView.findViewById(R.id.iv_goal_icon);
            tvGoalName = itemView.findViewById(R.id.tv_goal_name);
            tvDateTime = itemView.findViewById(R.id.tv_date_time);
            tvBankAccount = itemView.findViewById(R.id.tv_bank_account);
            tvAmount = itemView.findViewById(R.id.tv_amount);
        }
    }
}