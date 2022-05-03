package com.travel.guide.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;

import java.util.List;

public class OfferSecondAdapter extends RecyclerView.Adapter<OfferSecondAdapter.MyViewHolder>{

    List<String> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnOfferSecondClicked(int Position);
    }

    public OfferSecondAdapter(Context context, List<String> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_offer_second, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.txtGrabNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnOfferSecondClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return ItemList.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtGrabNow;

        public MyViewHolder(View view) {
            super(view);
            txtGrabNow = view.findViewById(R.id.txtGrabNow);
        }
    }
}












