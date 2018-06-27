package com.startup.oneSQYD;


import android.widget.Filter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CustomFilter extends Filter{

    Buy_Adapter adapter;
    ArrayList<JSONObject> filterList;

    public CustomFilter(ArrayList<JSONObject> filterList,Buy_Adapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<JSONObject> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                try {
                    if (filterList.get(i).getString("City").toUpperCase().contains(constraint)) {
                        //ADD PLAYER TO FILTERED PLAYERS
                        filteredPlayers.add(filterList.get(i));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.BuyCardList = (ArrayList<JSONObject>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}