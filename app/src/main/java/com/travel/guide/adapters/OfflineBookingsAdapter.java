package com.travel.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.BR;
import com.travel.guide.R;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.HomeDataResBean;
import com.travel.guide.databinding.ChildLayOfflineBookingBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OfflineBookingsAdapter extends RecyclerView.Adapter<OfflineBookingsAdapter.MyViewHolder>{

    public List<HomeDataResBean.BookingsItem> ItemList;
    private Context context;
    private ItemClickListener listener;
    private boolean needToPayNow = false;

    public interface ItemClickListener{
        void OnLocationClicked(int position);
        void OnCallClicked(int position);
        void OnBookingClicked(int position);
        void OnPayNowClicked(int position);
    }

    public OfflineBookingsAdapter(Context context, List<HomeDataResBean.BookingsItem> ItemList, boolean needToPayNow, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
        this.needToPayNow = needToPayNow;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.child_lay_offline_booking, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.getBinding().setVariable(BR.item,ItemList.get(position));
        holder.getBinding().setVariable(BR.index,position);
        holder.getBinding().setVariable(BR.OnItemClickListener,listener);

        ChildLayOfflineBookingBinding binding = (ChildLayOfflineBookingBinding) holder.getBinding();
        try {
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getServiceProperty().getImage())
                    .placeholder(context.getDrawable(R.drawable.demo_hostel)).into(binding.imgProperty);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if (ItemList.get(position).getStatus().equalsIgnoreCase("accepted")) {
            binding.txtBookingConfirmed.setText("Visit Booking Confirmed");
        }else if (ItemList.get(position).getStatus().equalsIgnoreCase("pending")){
            binding.txtBookingConfirmed.setText("Visit Booking Pending");
        } else {
            binding.txtBookingConfirmed.setText("Visit Booking Cancelled");
        }

        if (needToPayNow){
            binding.txtPayNow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}








