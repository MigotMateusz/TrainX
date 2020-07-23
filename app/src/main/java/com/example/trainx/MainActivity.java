package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialToolbar toolbar = (MaterialToolbar)findViewById(R.id.topAppBarMain);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout)findViewById(R.id.TopTabLayout);
        final TabItem tabWeek = findViewById(R.id.TWeekTab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {

                }
                else if(tab.getPosition() == 1){
                    ThisWeekFragment thisWeekFragment = new ThisWeekFragment();
                    thisWeekFragment.setArguments(getIntent().getExtras());
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.FrameLayout, thisWeekFragment).commit();
                }
                else if(tab.getPosition() == 2) {
                    PlansFragment plansFragment = new PlansFragment();
                    plansFragment.setArguments(getIntent().getExtras());
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
    public void replaceFragment() {
        Fragment fragment = new PlanStructureFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.FrameLayout, fragment)
                .commit();
    }
}