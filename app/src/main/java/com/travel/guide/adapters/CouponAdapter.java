package com.travel.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.OfferResBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.MyViewHolder>{

    List<OfferResBean.DataItem> ItemList;
    private Context context;
    ItemClickListener listener;

    public interface ItemClickListener{
        void OnCouponClicked(int position);
    }

    public CouponAdapter(Context context, List<OfferResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_coupon, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getBanner()).into(holder.imgCoupon);
        holder.txtCouponName.setText(ItemList.get(position).getTitle());
        holder.txtDiscount.setText(ItemList.get(position).getDiscount());
        holder.txtCouponDescription.setText(ItemList.get(position).getDescription());
        holder.txtCouponCode.setText(ItemList.get(position).getCouponCode());
        holder.txtGetCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnCouponClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgCoupon;
        public TextView txtCouponName, txtDiscount, txtCouponDescription, txtCouponCode, txtGetCoupon;

        public MyViewHolder(View view) {
            super(view);

            imgCoupon = view.findViewById(R.id.imgCoupon);
            txtCouponName = view.findViewById(R.id.txtCouponName);
            txtDiscount = view.findViewById(R.id.txtDiscount);
            txtCouponDescription = view.findViewById(R.id.txtCouponDescription);
            txtCouponCode = view.findViewById(R.id.txtCouponCode);
            txtGetCoupon = view.findViewById(R.id.txtGetCoupon);
        }
    }
}










