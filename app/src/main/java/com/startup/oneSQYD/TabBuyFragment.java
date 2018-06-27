package com.startup.oneSQYD;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.io.ByteStreams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class TabBuyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "TabBuyFragment";

    private Button btnTEST;

    Sessionmanager FragmentSession;

    RecyclerView recyclerView;
    Buy_Adapter buyAdapter;


    SwipeRefreshLayout swipeRefreshLayout;






    public void SetAdapter(ArrayList<JSONObject> BuyList){
        ArrayList<JSONObject> responsebuylist = BuyList;
//                System.out.println(responselandlist.size());
        buyAdapter = new Buy_Adapter(getActivity().getApplicationContext(), (ArrayList<JSONObject>) responsebuylist,"buy");

        recyclerView.setAdapter(buyAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentbuy_layout,container,false);




        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiper);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadData(swipeRefreshLayout);
            }
        });


//        mProgressDialog.dismiss();

//        Button InfoButton = (Button) view.findViewById(R.id.btnExpand);
//        InfoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Button Pressed", Toast.LENGTH_SHORT).show();
//            }
//        });


//
//        Buy_Adapter.BuyLandViewHolder.MoreInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        EditText searchfilter = (EditText) view.findViewById(R.id.searchFilter);
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
                if(buyAdapter != null) {
                    buyAdapter.getFilter().filter(s);
                }

            }
        });


        return view;
    }


    public void loadData(final SwipeRefreshLayout swipeRefreshLayout){
        FragmentSession = new Sessionmanager(getActivity().getApplicationContext());
        final HashMap<String, String> profile =  FragmentSession.getProfileDetails();
        final String Email = profile.get(FragmentSession.personEmail);
        swipeRefreshLayout.setRefreshing(true);

        final SQLiteDatabase db;
        db = getActivity().openOrCreateDatabase("1SQYD",MODE_PRIVATE,null);

        String query = "CREATE TABLE IF NOT EXISTS Buytable(_id VARCHAR,TradeId VARCHAR,OrderId VARCHAR,Email VARCHAR,Owner_email VARCHAR,LandId VARCHAR,Phone_number VARCHAR,Owner_name VARCHAR,Total_units VARCHAR,Total_size VARCHAR,Available_units VARCHAR,Percent_sold VARCHAR,Land_unit_value VARCHAR,Cost_unit_value VARCHAR,City VARCHAR);";

        db.execSQL(query);

        UserClient serviceGet =
                ServiceGenerator.createServiceGetAllLands(UserClient.class,profile.get(FragmentSession.Token));

        Call<ResponseBody> call = serviceGet.getAllBuy("bearer "+profile.get(FragmentSession.Token));
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                if(response.code()==200){
                    final ArrayList<JSONObject> list = new ArrayList<JSONObject>();

                    String total;
                    JSONArray jsonObject = null;

                    try {
                        total = new String(ByteStreams.toByteArray(response.body().byteStream()));
                        jsonObject = new JSONArray(total);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (jsonObject != null) {
                        for (int i=0;i<jsonObject.length();i++){
                            try {
                                list.add(new JSONObject(String.valueOf(jsonObject.get(i))));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    for(int i=0;i<list.size();i++){
                        JSONObject json = list.get(i);
                        try{
                        if(!json.has("OrderId")) {
                            json.put("OrderId","");
                        }

                        if(!json.has("TradeId")) {
                            json.put("TradeId",null);
                        }}catch (JSONException e){
                            e.printStackTrace();
                        }

                        try{
                        db.execSQL("INSERT INTO 1SQYD VALUES('" + json.get("_id") +
                                "','" + json.get("TradeId") +
                                "','" + json.get("OrderId") +
                                "','" + json.get("Email") +
                                "','" + json.get("Owner_email") +
                                "','" + json.get("LandId") +
                                "','" + json.get("Phone_number") +
                                "','" + json.get("Owner_name") +
                                "','" + json.get("Total_units") +
                                "','" + json.get("Total_size") +
                                "','" + json.get("Available_units") +
                                "','" + json.get("Percent_sold") +
                                "','" + json.get("Land_unit_value") +
                                "','" + json.get("Cost_unit_value") +
                                "','" + json.get("City") + "');");}
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            SetAdapter(list);

                        }
                    });
                    swipeRefreshLayout.setRefreshing(false);
                }
                else if(response.code()==401){
                    System.out.println(Email);
                    FragmentSession.SignIn(Email);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "App Session Expired, Reload Again ", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    swipeRefreshLayout.setRefreshing(false);
                }
                else if(response.code()==500){
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Could not get Lands, Try Again ", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    swipeRefreshLayout.setRefreshing(false);
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("Failure!!!!!"+t);
            }
        });

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swiper);
        loadData(swipeRefreshLayout);
    }
}
