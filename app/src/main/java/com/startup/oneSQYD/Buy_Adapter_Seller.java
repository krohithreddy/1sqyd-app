package com.startup.oneSQYD;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Buy_Adapter_Seller extends RecyclerView.Adapter<Buy_Adapter_Seller.BuyLandViewHolder> {
    private Context mCtx;
    public String Landid;
    public ArrayList<JSONObject> BuySellerList;

    public Buy_Adapter_Seller(Context mCtx, ArrayList<JSONObject> SelectedLandId) {
        this.mCtx = mCtx;
        BuySellerList = SelectedLandId;
    }


    @NonNull
    @Override
    public BuyLandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cardview_seller_buy, null);
        BuyLandViewHolder holder = new BuyLandViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BuyLandViewHolder holder, int position) {
        final JSONObject seller = BuySellerList.get(position);

        try {
            holder.Seller_name.setText(seller.getString("Owner_name"));
            holder.Phone_number.setText(seller.getString("Phone_number"));
            holder.Available_units.setText(seller.getString("Total_units"));
            holder.Cost_unit_value.setText(seller.getString("Cost_unit_value"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(mCtx,BuyLandFinalActivity.class);
                newIntent.putExtra("BuyCardSelected", seller.toString());
                mCtx.startActivity(newIntent);


                Toast.makeText(mCtx, "Seller Selected", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return BuySellerList.size();
    }

    static class BuyLandViewHolder extends RecyclerView.ViewHolder {

        TextView Seller_name,Phone_number,Available_units,Cost_unit_value;

        CardView cardView;


        public BuyLandViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewLandDetails);
            Seller_name = itemView.findViewById(R.id.Seller_name);
            Phone_number = itemView.findViewById(R.id.Phone_number);
            Available_units = itemView.findViewById(R.id.Units_available);
            Cost_unit_value = itemView.findViewById(R.id.Cost_unit_value);

        }



    }

}
