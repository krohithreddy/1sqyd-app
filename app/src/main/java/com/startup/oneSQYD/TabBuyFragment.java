package com.startup.oneSQYD;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TabBuyFragment extends Fragment {
    private static final String TAG = "TabBuyFragment";

    private Button btnTEST;

    Sessionmanager FragmentSession;

    RecyclerView recyclerView;
    Buy_Adapter buyAdapter;



    List<LandCard> LandList;

    public void SetAdapter(ArrayList<LandCard> landlist){
        ArrayList<LandCard> responselandlist = landlist;
//                System.out.println(responselandlist.size());
        buyAdapter = new Buy_Adapter(getActivity().getApplicationContext(), (ArrayList<LandCard>) responselandlist);
        recyclerView.setAdapter(buyAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentbuy_layout,container,false);

        //Get Request


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        FragmentSession = new Sessionmanager(getActivity().getApplicationContext());
        final HashMap<String, String> profile =  FragmentSession.getProfileDetails();

        UserClient serviceGet =
                ServiceGenerator.createServiceGetAllLands(UserClient.class,profile.get(FragmentSession.Token));

        Call<ArrayList<LandCard>> call = serviceGet.getAllLands("bearer "+profile.get(FragmentSession.Token));
        call.enqueue(new Callback<ArrayList<LandCard>>() {
            @Override
            public void onResponse(Call<ArrayList<LandCard>> call, final Response<ArrayList<LandCard>> response) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        SetAdapter(response.body());
                    }
                });


            }

            @Override
            public void onFailure(Call<ArrayList<LandCard>> call, Throwable t) {

                System.out.println("Failure!!!!!"+t);
            }
        });

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
                buyAdapter.getFilter().filter(s);
            }
        });


        return view;
    }
}
