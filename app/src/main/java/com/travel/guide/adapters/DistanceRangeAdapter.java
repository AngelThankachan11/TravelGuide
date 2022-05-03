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
import com.travel.guide.apicalls.model.DistanceRangeResBean;

import java.util.List;

public class DistanceRangeAdapter extends RecyclerView.Adapter<DistanceRangeAdapter.MyViewHolder>{

    List<DistanceRangeResBean.DistanceItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnDistanceChecked(int position, boolean isChecked);
    }

    public DistanceRangeAdapter(Context context, List<DistanceRangeResBean.DistanceItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_distance_range, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.txtDistanceTitle.setText(ItemList.get(position).getTitle());
        if (ItemList.get(position).getIsChecked()){
            holder.chkDistance.setChecked(true);
        }else {
            holder.chkDistance.setChecked(false);
        }
        holder.chkDistance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                listener.OnDistanceChecked(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox chkDistance;
        TextView txtDistanceTitle;

        public MyViewHolder(View view) {
            super(view);

            chkDistance = view.findViewById(R.id.chkDistance);
            txtDistanceTitle = view.findViewById(R.id.txtDistanceTitle);
        }
    }
}









