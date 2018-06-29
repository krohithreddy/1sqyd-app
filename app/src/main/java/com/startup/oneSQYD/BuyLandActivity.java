package com.startup.oneSQYD;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuyLandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_land);

        Intent currentIntent = getIntent();
        String selectedBuyCard = (String) currentIntent.getSerializableExtra("BuyCardSelected");

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(selectedBuyCard);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<JSONObject> Sellers = null;

        CardView LandDetailsCardView = (CardView) findViewById(R.id.cardViewLandDetails);

        TextView City = (TextView) findViewById(R.id.City);
        TextView Total_area = (TextView) findViewById(R.id.TotalArea);
        TextView Percent_sold = (TextView) findViewById(R.id.Percent_sold);
        TextView Land_unit_value = (TextView) findViewById(R.id.Land_unit_size);
        TextView Total_units = (TextView) findViewById(R.id.Total_units);

        try {
            City.setText(jsonObject.getString("City"));
            Total_area.setText(jsonObject.getString("Total_size"));
            Percent_sold.setText(jsonObject.getString("Percent_sold"));
            Land_unit_value.setText(jsonObject.getString("Land_unit_value"));
            Total_units.setText(jsonObject.getString("Total_units"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        RecyclerView recyclerView;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewBuyland);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor c = null;
        SQLiteDatabase db;
        db = openOrCreateDatabase("1SQYD",MODE_PRIVATE,null);
        try {
            c = db.rawQuery("SELECT * FROM Buytable WHERE LandId = '"+jsonObject.getString("LandId")+"'", null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ArrayList<JSONObject> resultSet = new ArrayList<JSONObject>();
        if (!(c.moveToFirst()) || c.getCount() == 0){

        }
        else {
            c.moveToFirst();
            while (c.isAfterLast() == false) {
                int totalColumn = c.getColumnCount();
                JSONObject rowObject = new JSONObject();
                for (int i = 0; i < totalColumn; i++) {
                    if (c.getColumnName(i) != null) {
                        try {
                            if (c.getString(i) != null) {
                                rowObject.put(c.getColumnName(i), c.getString(i));
                            } else {
                                rowObject.put(c.getColumnName(i), "");
                            }
                        } catch (Exception e) {

                        }
                    }
                }
                resultSet.add(rowObject);
                c.moveToNext();
            }
            c.close();
        }

        Buy_Adapter_Seller developAdapter = null;
        developAdapter = new Buy_Adapter_Seller(this,resultSet);

        recyclerView.setAdapter(developAdapter);


//        Toast.makeText(this, "Land Image passed : "+selectedLandCard.getLandImage()+"Survey Image : "+selectedLandCard.getSurveyImage(), Toast.LENGTH_SHORT).show();


    }
}
