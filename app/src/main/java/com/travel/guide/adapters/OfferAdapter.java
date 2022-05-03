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
import com.travel.guide.apicalls.model.OfferResBean;
import com.travel.guide.databinding.ChildLayOfferBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder>{

    List<OfferResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnOfferClicked(int Position);
        void OnOfferDetailsClicked(int position);
    }

    public OfferAdapter(Context context, List<OfferResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.child_lay_offer, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.binding.setVariable(BR.item, ItemList.get(position));
        holder.binding.setVariable(BR.index, position);
        holder.binding.setVariable(BR.ItemClickListener, listener);

        ChildLayOfferBinding binding = (ChildLayOfferBinding) holder.binding;

        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getBanner()).into(binding.imgOffer);
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}











