package com.startup.oneSQYD;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ActivityInvest extends AppCompatActivity{

    Sessionmanager FragmentSession;

    RecyclerView recyclerView;
    Buy_Adapter buyAdapter;


    SwipeRefreshLayout swipeRefreshLayout;


    List<LandCard> LandList;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent0 = new Intent(ActivityInvest.this, MainActivity.class);
                    startActivity(intent0);
                    overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_invest:

//                    mTextMessage.setText("Invest");
                    return true;
                case R.id.navigation_reports:
                    Intent intent2 = new Intent(ActivityInvest.this, ActivityReports.class);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
//                    mTextMessage.setText("Reports");
                    return true;

                case R.id.navigation_settings:
                    Intent intent3 = new Intent(ActivityInvest.this, ActivitySettings.class);
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
        setContentView(R.layout.activity_invest);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        EditText searchfilter = (EditText) findViewById(R.id.searchFilter);
        searchfilter.setSelected(false);
        searchfilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ArrayList<JSONObject> investLandList = new ArrayList<JSONObject>();
//        investLandList.add(new LandCard(
//                "98273429874","allirohan@gmail.com","9989889655","LandImage1","SurveyImage1","Kukatpally","Alli Rohan","9849072573","100000","50","10"
//
//        ));
//        investLandList.add(new LandCard(
//                "allirohan9@gmail.com","9989889655","LandImage2","SurveyImage2"
//
//        ));
//        investLandList.add(new LandCard(
//                "allirohan9@gmail.com","9989889655","LandImage3","SurveyImage3"
//
//        ));
//        investLandList.add(new LandCard(
//                "allirohan9@gmail.com","9989889655","LandImage4","SurveyImage4"
//
//        ));
//        investLandList.add(new LandCard(
//                "allirohan9@gmail.com","9989889655","LandImage5","SurveyImage5"
//
//        ));
//        investLandList.add(new LandCard(
//                "allirohan9@gmail.com","9989889655","LandImage6","SurveyImage6"
//
//        ));
//        investLandList.add(new LandCard(
//                "allirohan9@gmail.com","9989889655","LandImage7","SurveyImage7"
//
//        ));
//        investLandList.add(new LandCard(
//                "allirohan9@gmail.com","9989889655","LandImage8","SurveyImage8"
//
//        ));
//        investLandList.add(new LandCard(
//                "allirohan9@gmail.com","9989889655","LandImage9","SurveyImage9"
//
//        ));
//        investLandList.add(new LandCard(
//                "allirohan9@gmail.com","9989889655","LandImage10","SurveyImage10"
//
//        ));


        Buy_Adapter developAdapter = new Buy_Adapter(this,investLandList,"invest");

        recyclerView.setAdapter(developAdapter);


    }
}
