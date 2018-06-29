package com.startup.oneSQYD;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyLandFinalActivity extends AppCompatActivity {


    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_land_final);
        Intent currentIntent = getIntent();
        String selectedBuyCard = (String) currentIntent.getSerializableExtra("BuyCardSelected");

        final EditText Quantity_tobuy = (EditText) findViewById(R.id.Units_to_buy);
        final EditText Phone_number = (EditText) findViewById(R.id.Phone_number_buy);

        Button buyland = (Button) findViewById(R.id.Buy_land);





        final Sessionmanager FragmentSession;
        FragmentSession = new Sessionmanager(this.getApplicationContext());
        final HashMap<String, String> profile =  FragmentSession.getProfileDetails();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(selectedBuyCard);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JSONObject finalJsonObject = jsonObject;
        buyland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                String Quantity_to_buy_string = Quantity_tobuy.getText().toString();
                String Phone_number_string = Phone_number.getText().toString();
                UserClient.OrderObject newOrderObject = new UserClient.OrderObject(finalJsonObject.getString("LandId"),
                        profile.get(FragmentSession.personEmail),
                        finalJsonObject.getString("Email"),
                        Quantity_to_buy_string,
                        finalJsonObject.getString("Land_unit_value"),
                        finalJsonObject.getString("Cost_unit_value"),
                        finalJsonObject.getString("Total_size"),
                        finalJsonObject.getString("Percent_sold"),
                        finalJsonObject.getString("Total_units"),
                        profile.get(FragmentSession.personDisplayName),
                        Phone_number_string,
                        finalJsonObject.getString("City"));


//                    JSONObject requestJson = new JSONObject();
//                    requestJson.put("LandId", finalJsonObject.getString("LandId"));
//                    requestJson.put("Email", profile.get(FragmentSession.personEmail));
//                    requestJson.put("Owner_email", finalJsonObject.getString("Email"));
//                    requestJson.put("Quantity", Quantity_to_buy_string);
//                    requestJson.put("Land_unit_value", finalJsonObject.getString("Land_unit_value"));
//                    requestJson.put("Cost_unit_value", finalJsonObject.getString("Cost_unit_value"));
//                    requestJson.put("Total_size", finalJsonObject.getString("Total_size"));
//                    requestJson.put("Percent_sold", finalJsonObject.getString("Percent_sold"));
//                    requestJson.put("Total_units", finalJsonObject.getString("Total_units"));
//                    requestJson.put("Owner_name", profile.get(FragmentSession.personDisplayName));
//                    requestJson.put("Phone_number", Phone_number_string);
//                    requestJson.put("City", finalJsonObject.getString("City"));

//                    System.out.println(requestJson);

                    UploadFile(newOrderObject);
                }catch (JSONException e){
                    e.printStackTrace();
                }


            }
        });







    }


    public void UploadFile(UserClient.OrderObject requestbody) {
        final SQLiteDatabase db;
        db = openOrCreateDatabase("1SQYD",MODE_PRIVATE,null);
        Sessionmanager FragmentSession;
        FragmentSession = new Sessionmanager(this.getApplicationContext());
        final HashMap<String, String> profile =  FragmentSession.getProfileDetails();

        // create upload service client
        UserClient service =
                ServiceGenerator.createServiceSignIn(UserClient.class);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization","bearer "+profile.get(FragmentSession.Token));
        map.put("Content-Type","application/json");

        // finally, execute the request
        Call<ResponseBody> call = service.BuyLand(map,requestbody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if(response.code()==201) {
                            Toast.makeText(getApplicationContext(), "Purchase Successfull", Toast.LENGTH_SHORT).show();
                            Intent newIntent = new Intent(BuyLandFinalActivity.this,MainActivity.class);
                            BuyLandFinalActivity.this.startActivity(newIntent);
                            db.execSQL("DELETE FROM Buytable");
                            db.close();

                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

}
