package at.ac.univie.expensetracker.moneymonkey.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.adaptercollection.TabOverviewFragmentAdapter;

public class TabOverviewActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager2 mViewPager2;
    TabOverviewFragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_overview);

        mTabLayout = findViewById(R.id.activitytaboverview_tablayout);
        mViewPager2 = findViewById(R.id.activitytaboverview_viewpager2);

        FragmentManager fm = getSupportFragmentManager();
        mFragmentAdapter = new TabOverviewFragmentAdapter(fm, getLifecycle());
        mViewPager2.setAdapter(mFragmentAdapter);

        // Add different tabs to tab layout
        mTabLayout.addTab(mTabLayout.newTab().setText("Accounts"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Summary"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Categories"));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

            mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    mTabLayout.selectTab(mTabLayout.getTabAt(position));
                }
            });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //Shows specific fragment called by an intent
            int position = 0;
            position = extras.getInt("viewpager_position");
            mViewPager2.setCurrentItem(position);
        }



    }

}