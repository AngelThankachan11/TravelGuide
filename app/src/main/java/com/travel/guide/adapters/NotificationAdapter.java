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
import com.travel.guide.apicalls.model.NotificationResBean;
import com.travel.guide.databinding.ChildLayNotificationBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>{

    List<NotificationResBean.NotificationItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnNotificatonClicked(int position);
    }

    public NotificationAdapter(Context context, List<NotificationResBean.NotificationItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.child_lay_notification, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.binding.setVariable(BR.item, ItemList.get(position));
        holder.binding.setVariable(BR.index,position);
        holder.binding.setVariable(BR.ItemClickListener,listener);
        ChildLayNotificationBinding binding = (ChildLayNotificationBinding) holder.getBinding();
        if (ItemList.get(position).getImage()!=null){
            binding.imgNotification.setVisibility(View.VISIBLE);
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage())
                    .placeholder(context.getDrawable(R.drawable.demo_hostel)).into(binding.imgNotification);
            binding.txtTitle.setVisibility(View.GONE);
        }else {
            binding.txtTitle.setVisibility(View.VISIBLE);
            binding.imgNotification.setVisibility(View.GONE);
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

        public ViewDataBinding getBinding(){
            return binding;
        }
    }
}










