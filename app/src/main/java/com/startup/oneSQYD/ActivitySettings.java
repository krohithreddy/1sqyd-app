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

public class ActivitySettings extends AppCompatActivity{
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent0 = new Intent(ActivitySettings.this, MainActivity.class);
                    startActivity(intent0);
                    overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_invest:
                    Intent intent1 = new Intent(ActivitySettings.this, ActivityInvest.class);
                    startActivity(intent1);
                    overridePendingTransition(0,0);
//                    mTextMessage.setText("Invest");
                    return true;
                case R.id.navigation_reports:
                    Intent intent2 = new Intent(ActivitySettings.this, ActivityReports.class);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
//                    mTextMessage.setText("Reports");
                    return true;

                case R.id.navigation_settings:

                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView title = (TextView) findViewById(R.id.activityTitle3);
        title.setText("This is Settings");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
    }
}