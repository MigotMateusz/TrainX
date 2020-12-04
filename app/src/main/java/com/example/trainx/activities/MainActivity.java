package com.example.trainx.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.trainx.R;
import com.example.trainx.data.DataManager;
import com.example.trainx.fragments.DataFragment;
import com.example.trainx.fragments.OverviewFragment;
import com.example.trainx.fragments.PlanStructureFragment;
import com.example.trainx.fragments.PlansFragment;
import com.example.trainx.fragments.ThisWeekFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    public DataManager dataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataManager = new DataManager(this);
        prepareToolbar();
    }

    private void prepareToolbar(){
        MaterialToolbar toolbar = findViewById(R.id.topAppBarMain);
        toolbar.getMenu().getItem(1).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(MainActivity.this, MySettingsActivity.class);
            startActivity(intent);
            return false;
        });
    }

    public void replaceFragment(String titlePlan) {
        Fragment fragment = new PlanStructureFragment();
        Bundle args = new Bundle();
        args.putString("title", titlePlan);
        args.putSerializable("DataManager", dataManager);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.FrameLayout, fragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void changeTab(int index, Fragment fragment) {
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        assert tab != null;
        tab.select();
        Bundle newBundle = getIntent().getBundleExtra("BundleNewPlan");
        fragment.setArguments(newBundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.FrameLayout, fragment).commit();
    }

    public void display() {
        tabLayout = findViewById(R.id.TopTabLayout);
        setTab();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    OverviewFragment overviewFragment = new OverviewFragment();
                    changeTab(overviewFragment);
                }
                else if(tab.getPosition() == 1){
                    ThisWeekFragment thisWeekFragment = new ThisWeekFragment();
                    changeTab(thisWeekFragment);
                }
                else if(tab.getPosition() == 2) {
                    PlansFragment plansFragment = new PlansFragment();
                    changeTab(plansFragment);
                }
                else {
                    DataFragment dataFragment = new DataFragment();
                    changeTab(dataFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                FrameLayout frameLayout = findViewById(R.id.FrameLayout);
                frameLayout.removeAllViews();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void setTab(){
        int test = getIntent().getIntExtra("doWhat", 0);
        switch(test) {
            case 0:
                OverviewFragment overviewFragment = new OverviewFragment();
                changeTab(0, overviewFragment);
                break;
            case 1:
                ThisWeekFragment thisWeekFragment = new ThisWeekFragment();
                changeTab(1, thisWeekFragment);
                break;
            case 2:
                PlansFragment plansFragment = new PlansFragment();
                changeTab(2, plansFragment);
                break;
            case 3:
                DataFragment dataFragment = new DataFragment();
                changeTab(3, dataFragment);
                break;
        }
    }
    private void changeTab(Fragment fragment){
        fragment.setArguments(getIntent().getExtras());
        Bundle newBundle;
        if(getIntent().getExtras()==null && getIntent().getBundleExtra("BundleNewPlan") == null)
            newBundle = new Bundle();
        else if(getIntent().getExtras()==null && getIntent().getBundleExtra("BundleNewPlan") != null)
            newBundle = new Bundle(getIntent().getBundleExtra("BundleNewPlan"));
        else
            newBundle = new Bundle((getIntent().getExtras()));
        if(dataManager != null) {
            newBundle.putSerializable("DataManager", dataManager);
        }
        fragment.setArguments(newBundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.FrameLayout, fragment).commit();
    }
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public DataManager getDataManager() {
        return dataManager;
    }
}