package com.startup.oneSQYD;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Buy_Adapter extends  RecyclerView.Adapter<Buy_Adapter.BuyLandViewHolder>{

    private Context mCtx;
    public ArrayList<JSONObject> BuyCardList,filterList;
    CustomFilter filter;
    String CardUsage;
    ArrayList<String> CardLandIds = null;
    SQLiteDatabase db;


    public Buy_Adapter(Context mCtx, ArrayList<JSONObject> buycardlist, String cardUsage) {
        this.mCtx = mCtx;
        BuyCardList = buycardlist;
        filterList = buycardlist;
        CardUsage = cardUsage;
    }

    public boolean isListEmpty(){
        if(this.BuyCardList == null){
            return true;
        }
        else
            return false;
    }


    @NonNull
    @Override
    public BuyLandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cardview_buy, null);
        BuyLandViewHolder holder = new BuyLandViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull BuyLandViewHolder holder, int position) {
        final JSONObject buyCard = BuyCardList.get(position);

        try{
        if(!CardLandIds.contains(buyCard.getString("LandId"))) {
            db = mCtx.openOrCreateDatabase("1SQYD", MODE_PRIVATE, null);
            System.out.println(buyCard);
            Cursor c1 = null, c2 = null;
            c1 = db.rawQuery("SELECT SUM(Available_units) from Buytable WHERE LandId = '" + buyCard.getString("LandId") + "'", null);
            c2 = db.rawQuery("SELECT MIN(Cost_unit_value) AS MinCost from Buytable WHERE LandId = '" + buyCard.getString("LandId") + "'", null);
//            System.out.println(c1.getColumnCount());
//            System.out.println(c1.getCount());
            c1.moveToFirst();
            c2.moveToFirst();
            holder.City_name.setText(buyCard.getString("City"));
            holder.Available_units.setText(c1.getString(0));
            holder.Min_cost.setText(c2.getString(0));
            holder.Land_unit.setText(buyCard.getString("Land_unit_value"));

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (CardUsage == "buy") {
                        Intent intentbuy = new Intent(mCtx, BuyLandActivity.class);
                        intentbuy.putExtra("BuyCardSelected", buyCard.toString());
                        mCtx.startActivity(intentbuy);


                    }
                    if (CardUsage == "invest")
                        Toast.makeText(mCtx, "Invest not yet developed", Toast.LENGTH_SHORT).show();

                }
            });
            CardLandIds.add(buyCard.getString("LandId"));
        }
        else{

        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return BuyCardList.size();
    }


    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }

        return filter;
    }

    static class BuyLandViewHolder extends RecyclerView.ViewHolder {

        TextView City_name,Land_unit,Min_cost,Available_units;

        CardView cardView;


        public BuyLandViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            City_name = itemView.findViewById(R.id.City);
            Land_unit = itemView.findViewById(R.id.Land_unit_size);
            Min_cost = itemView.findViewById(R.id.Min_cost);
            Available_units = itemView.findViewById(R.id.Units);

        }



    }
}
