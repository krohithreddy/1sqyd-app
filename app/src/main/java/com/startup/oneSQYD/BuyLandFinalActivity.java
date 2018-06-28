package com.startup.oneSQYD;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class BuyLandFinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_land_final);
        Intent currentIntent = getIntent();
        String selectedBuyCard = (String) currentIntent.getSerializableExtra("BuyCardSelected");

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(selectedBuyCard);
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}
