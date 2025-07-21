package com.example.finex_mobile.screens.admin_subscriptions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finex_mobile.R;
import com.example.finex_mobile.entities.subcription.Subscription;

public class AdminManageSubscriptions extends AppCompatActivity {
    private AdminManageSubscriptionsAdapter adminManageSubscriptionsAdapter;
    private ListView manageSubscriptionsListView;
    private int currentSubscriptionPosition;
    private ActivityResultLauncher<Intent> subscriptionFormLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setTitle("Subscriptions");
        setContentView(R.layout.activity_admin_manage_subscriptions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        currentSubscriptionPosition = -1;

        adminManageSubscriptionsAdapter = new AdminManageSubscriptionsAdapter(this, Subscription.getSupscriptionList());
        manageSubscriptionsListView = findViewById(R.id.manageSubscriptionsListView);
        manageSubscriptionsListView.setAdapter(adminManageSubscriptionsAdapter);
        manageSubscriptionsListView.setOnItemLongClickListener(this::onSubscriptionItemLongClick);

        subscriptionFormLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onSubscriptionFormActivityResult);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_subscriptions_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.addSubscriptionAction) {
            currentSubscriptionPosition = -1;
            startSubscriptionForm();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onPopupMenuItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.editSubscriptionAction) {
            startSubscriptionForm();
            return true;
        }

        if (itemId == R.id.removeSubscriptionAction) {
            startConfirmDeleteDialog();
            return true;
        }

        return super.onContextItemSelected(item);
    }

    private boolean onSubscriptionItemLongClick(AdapterView parent, View view, int position, long id) {
        currentSubscriptionPosition = position;
        PopupMenu menu = new PopupMenu(this, view);
        menu.getMenuInflater().inflate(R.menu.supscription_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(this::onPopupMenuItemSelected);
        menu.show();
        return true;
    }

    private void startSubscriptionForm() {
        Intent intent = new Intent(this, SubscriptionForm.class);
        intent.putExtra("subscriptionPosition", currentSubscriptionPosition);
        subscriptionFormLauncher.launch(intent);
    }

    private void onSubscriptionFormActivityResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_CANCELED) {
            return;
        }
        
        if (result.getResultCode() != RESULT_OK) {
            Toast.makeText(this, "Save subscription failed!", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Save subscription successfully!", Toast.LENGTH_SHORT).show();
        adminManageSubscriptionsAdapter.notifyDataSetChanged();
    }

    private void startConfirmDeleteDialog() {
        Subscription subscription = Subscription.getSupscriptionList().get(currentSubscriptionPosition);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Deleting Subscription")
                .setMessage("You are deleting " + subscription.getName() + " subscription!")
                .setPositiveButton("Remove", this::handleDeleteSubscription)
                .setNegativeButton("Cancel", (dialog, id) -> {
                    dialog.cancel();
                });

        builder.create().show();
    }

    private void handleDeleteSubscription(DialogInterface dialog, int id) {
        Subscription.getSupscriptionList().remove(currentSubscriptionPosition);
        adminManageSubscriptionsAdapter.notifyDataSetChanged();
        dialog.dismiss();
    }
}