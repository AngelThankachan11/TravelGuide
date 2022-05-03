package com.travel.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.ServiceableCityResBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder>{

    List<ServiceableCityResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnCityClicked(int position);
    }

    public CityAdapter(Context context, List<ServiceableCityResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_city, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.txtCity.setText(ItemList.get(position).getName());
        holder.txtState.setText(ItemList.get(position).getStateName());
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgItem);
        if (ItemList.get(position).getIsSelected()){
            holder.consRoot.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner10_app_border));
        }else {
            holder.consRoot.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_white));
        }
        holder.cardRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnCityClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView cardRoot;
        TextView txtCity, txtState;
        ConstraintLayout consRoot;
        ImageView imgItem;

        public MyViewHolder(View view) {
            super(view);
            cardRoot = view.findViewById(R.id.cardRoot);
            txtCity = view.findViewById(R.id.txtCity);
            txtState = view.findViewById(R.id.txtState);
            consRoot = view.findViewById(R.id.consRoot);
            imgItem = view.findViewById(R.id.imgItem);
        }
    }
}










