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
    ArrayList<String> CardLandIds = new ArrayList<String>();

    SQLiteDatabase db;


    public Buy_Adapter(Context mCtx, ArrayList<JSONObject> buycardlist, String cardUsage) {
        this.mCtx = mCtx;
        BuyCardList = buycardlist;
        filterList = buycardlist;
        CardUsage = cardUsage;
        CardLandIds.add(0,"");
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
        return new BuyLandViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull BuyLandViewHolder holder, int position) {
        final JSONObject buyCard = BuyCardList.get(position);

        try{
            System.out.println(buyCard.getString("LandId"));
        if(!CardLandIds.contains(buyCard.getString("LandId"))) {
//            System.out.println("Print Card View");
            db = mCtx.openOrCreateDatabase("1SQYD", MODE_PRIVATE, null);
            System.out.println(buyCard);
            holder.c1 = db.rawQuery("SELECT SUM(Available_units) AS SumUnits from Buytable WHERE LandId = '" + buyCard.getString("LandId") + "'", null);
            holder.c2 = db.rawQuery("SELECT MIN(Cost_unit_value) AS MinCost from Buytable WHERE LandId = '" + buyCard.getString("LandId") + "'", null);
            if(holder.c1!=null && holder.c2!=null) {
                holder.c1.moveToFirst();
                holder.c2.moveToFirst();
                System.out.println(holder.c1.getString(0));
                System.out.println(holder.c2.getString(0));
                holder.City_name.setText(buyCard.getString("City"));
                holder.Available_units.setText(holder.c1.getString(0));
                holder.Min_cost.setText(holder.c2.getString(0));
                holder.Land_unit.setText(buyCard.getString("Land_unit_value"));
            }
            holder.c1.close();
            holder.c2.close();

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
        db.close();
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

        Cursor c1,c2;


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
