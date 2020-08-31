package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    public DataManager dataManager;
    private ArrayList<TrainingPlan> trainingPlans2;
    private Menu optionMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Operation().execute();
        MaterialToolbar toolbar = (MaterialToolbar)findViewById(R.id.topAppBarMain);
        toolbar.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, MySettingsActivity.class);
                startActivity(intent);
                return false;
            }
        });
        //setSupportActionBar(toolbar);
        trainingPlans2 = new ArrayList<>();
        int test = getIntent().getIntExtra("doWhat", 3);
        tabLayout = (TabLayout)findViewById(R.id.TopTabLayout);
        final TabItem tabWeek = findViewById(R.id.TWeekTab);
        TabLayout.Tab tab;
        switch(test) {
            case -1:
            case 0:
                tab = tabLayout.getTabAt(0);
                tab.select();
                OverviewFragment overviewFragment = new OverviewFragment();


                getSupportFragmentManager().beginTransaction()
                        .add(R.id.FrameLayout, overviewFragment).commit();
                break;
            case 1:
                tab = tabLayout.getTabAt(1);
                tab.select();
                break;
            case 2:
                tab = tabLayout.getTabAt(2);
                tab.select();
                PlansFragment plansFragment = new PlansFragment();

                Bundle newBundle = getIntent().getBundleExtra("BundleNewPlan");
                plansFragment.setArguments(newBundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.FrameLayout, plansFragment).commit();
                test = -1;
                break;
            case 3:
                tab = tabLayout.getTabAt(3);
                tab.select();
                break;
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    OverviewFragment overviewFragment = new OverviewFragment();
                    overviewFragment.setArguments(getIntent().getExtras());
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
                    overviewFragment.setArguments(newBundle);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.FrameLayout, overviewFragment).commit();
                }
                else if(tab.getPosition() == 1){
                    ThisWeekFragment thisWeekFragment = new ThisWeekFragment();
                    thisWeekFragment.setArguments(getIntent().getExtras());
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
                    thisWeekFragment.setArguments(newBundle);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.FrameLayout, thisWeekFragment).commit();
                }
                else if(tab.getPosition() == 2) {
                    PlansFragment plansFragment = new PlansFragment();
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
                    plansFragment.setArguments(newBundle);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.FrameLayout, plansFragment).commit();
                }
                else {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                FrameLayout frameLayout = (FrameLayout)findViewById(R.id.FrameLayout);
                frameLayout.removeAllViews();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
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
    public void visibiltyTab(){
        TabLayout tab = findViewById(R.id.TopTabLayout);
        tab.setVisibility(View.GONE);
    }

    private  class Operation extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            dataManager = new DataManager();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        optionMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }
}