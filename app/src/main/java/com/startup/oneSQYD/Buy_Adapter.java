package com.startup.oneSQYD;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Buy_Adapter extends  RecyclerView.Adapter<Buy_Adapter.BuyLandViewHolder>{

    private Context mCtx;
    public ArrayList<JSONObject> BuyCardList,filterList;
    CustomFilter filter;
    String CardUsage;

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
        db = mCtx.openOrCreateDatabase("1SQYD", MODE_PRIVATE,null);

        

        try {
            holder.City_name.setText(buyCard.getString("City"));
            holder.Available_units.setText(buyCard.getString("Available_units"));
            holder.Min_cost.setText(buyCard.getString("Cost_unit_value"));
            holder.Land_unit.setText(buyCard.getString("Land_unit_value"));
        }catch (JSONException e){
            e.printStackTrace();
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CardUsage=="buy"){
//                    Toast.makeText(mCtx, "Land Image : "+landcard.getLandImage()+"Survey Image : "+landcard.getSurveyImage(), Toast.LENGTH_SHORT).show();

                    Intent intentbuy = new Intent(mCtx,BuyLandActivity.class);
                    intentbuy.putExtra("BuyCardSelected",buyCard.toString());
                    mCtx.startActivity(intentbuy);


                }
                if(CardUsage=="invest")
                    Toast.makeText(mCtx, "Invest not yet developed", Toast.LENGTH_SHORT).show();

            }
        });

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

    class BuyLandViewHolder extends RecyclerView.ViewHolder {

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
