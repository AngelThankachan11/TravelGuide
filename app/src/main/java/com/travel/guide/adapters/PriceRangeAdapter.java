package com.travel.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.apicalls.model.PriceRangeResBean;

import java.util.List;

public class PriceRangeAdapter extends RecyclerView.Adapter<PriceRangeAdapter.MyViewHolder>{

    List<PriceRangeResBean.DataItem> ItemList;
    private Context context;
    private PriceClickListener listener;

    public interface PriceClickListener{
        void OnPriceChecked(int position, boolean isChecked);
    }

    public PriceRangeAdapter(Context context, List<PriceRangeResBean.DataItem> ItemList, PriceClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_price_range, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.txtPriceTitle.setText(ItemList.get(position).getTitle());
        if (ItemList.get(position).getIsChecked()){
            holder.chkPrice.setChecked(true);
        }else {
            holder.chkPrice.setChecked(false);
        }

        holder.chkPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                listener.OnPriceChecked(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox chkPrice;
        TextView txtPriceTitle;

        public MyViewHolder(View view) {
            super(view);

            chkPrice = view.findViewById(R.id.chkPrice);
            txtPriceTitle = view.findViewById(R.id.txtPriceTitle);
        }
    }
}










