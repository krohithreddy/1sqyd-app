package com.startup.oneSQYD;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Buy_Adapter extends RecyclerView.Adapter<Buy_Adapter.BuyLandViewHolder>{

    private Context mCtx;
    public ArrayList<LandCard> LandCardList,filterList;
    CustomFilter filter;


    public Buy_Adapter(Context mCtx, ArrayList<LandCard> landCardList) {
        this.mCtx = mCtx;
        LandCardList = landCardList;
        filterList = landCardList;
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
        final LandCard landcard = LandCardList.get(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Land Image : "+landcard.getLandImage()+"Survey Image : "+landcard.getSurveyImage(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.textViewTitle.setText(landcard.getEmail());
        holder.textViewRating.setText(String.valueOf(landcard.getAadhar()));
//        holder.textViewPrice.setText(String.valueOf(landcard.getId()));
        holder.textViewDesc.setText(landcard.getLandImage());
//        holder.imageView.setImageDrawable(mCtx.getDrawable(landcard.getImage()));

    }

    @Override
    public int getItemCount() {
        return LandCardList.size();
    }


    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }

        return filter;
    }

    class BuyLandViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewTitle,textViewDesc,textViewPrice,textViewRating;

        CardView cardView;


        public BuyLandViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewRating = itemView.findViewById(R.id.textViewRating);

        }



    }
}
