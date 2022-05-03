package com.travel.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.BR;
import com.travel.guide.R;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.RecentlyVisitedResBean;
import com.travel.guide.databinding.ChildRecentlyVisitedHostelBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecentlyVisitedHostelAdapter extends RecyclerView.Adapter<RecentlyVisitedHostelAdapter.MyViewHolder>{

    List<RecentlyVisitedResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnPropertyClicked(int Position);
    }

    public RecentlyVisitedHostelAdapter(Context context, List<RecentlyVisitedResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.child_recently_visited_hostel, parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.getBinding().setVariable(BR.item,ItemList.get(position));
        holder.getBinding().setVariable(BR.index,position);
        holder.getBinding().setVariable(BR.ItemClickListener,listener);
        ChildRecentlyVisitedHostelBinding binding = (ChildRecentlyVisitedHostelBinding) holder.getBinding();
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(binding.imgProperty);

    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }

        public ViewDataBinding getBinding(){
            return binding;
        }
    }
}








