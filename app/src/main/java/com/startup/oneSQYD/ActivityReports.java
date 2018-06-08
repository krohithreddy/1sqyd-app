package com.startup.oneSQYD;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityReports extends AppCompatActivity{
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent0 = new Intent(ActivityReports.this, MainActivity.class);
                    startActivity(intent0);
                    overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_invest:
                    Intent intent1 = new Intent(ActivityReports.this, ActivityInvest.class);
                    startActivity(intent1);
                    overridePendingTransition(0,0);
//                    mTextMessage.setText("Invest");
                    return true;
                case R.id.navigation_reports:

                    return true;

                case R.id.navigation_settings:
                    Intent intent3 = new Intent(ActivityReports.this, ActivitySettings.class);
                    startActivity(intent3);
                    overridePendingTransition(0,0);
//                    mTextMessage.setText("Settings");
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        TextView title = (TextView) findViewById(R.id.activityTitle2);
        title.setText("This is Reports");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
    }
}
