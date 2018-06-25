package com.startup.oneSQYD;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_invest:
                    Intent intent1 = new Intent(MainActivity.this, ActivityInvest.class);
                    startActivity(intent1);
                    overridePendingTransition(0,0);
//                    mTextMessage.setText("Invest");
                    return true;
                case R.id.navigation_reports:
                    Intent intent2 = new Intent(MainActivity.this, ActivityReports.class);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
//                    mTextMessage.setText("Reports");
                    return true;

                case R.id.navigation_settings:
                    Intent intent3 = new Intent(MainActivity.this, ActivitySettings.class);
                    startActivity(intent3);
                    overridePendingTransition(0,0);
//                    mTextMessage.setText("Settings");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.containermain);
        setupViewPager(mViewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        tabLayout.getTabAt(0).setText("BUY");
        tabLayout.getTabAt(1).setText("SELL");
        tabLayout.getTabAt(2).setText("TRADE");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);



    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabBuyFragment());
        adapter.addFragment(new TabSellFragment());
        adapter.addFragment(new TabtradeFragment());
        viewPager.setAdapter(adapter);
    }

}
