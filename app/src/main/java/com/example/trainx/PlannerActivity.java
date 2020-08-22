package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class PlannerActivity extends AppCompatActivity {
    Menu navMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_planner);
        final MaterialToolbar toolbar = (MaterialToolbar)findViewById(R.id.toolbarPlanner);
        setSupportActionBar(toolbar);
        //toolbar.inflateMenu(R.menu.menu_planner);
        navMenu = toolbar.getMenu();
        MenuItem menuItemSave = navMenu.findItem(R.id.saveWeek);
        menuItemSave.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(PlannerActivity.this, "test on click", Toast.LENGTH_SHORT).show();
                /*
                new MaterialAlertDialogBuilder(PlannerActivity.this)
                        .setTitle("Saving new plan")
                        .setMessage("Are you sure to save this plan?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();*/
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_planner, menu);
        navMenu = menu;
        return true;
    }

}
