package com.travel.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.MessDetailsResBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessImageAdapter extends RecyclerView.Adapter<MessImageAdapter.MyViewHolder>{

    List<MessDetailsResBean.DataItem.MessImageItem> ItemList;
    private Context context;

    public MessImageAdapter(Context context, List<MessDetailsResBean.DataItem.MessImageItem> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_room_image, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgRoomImage);

    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgRoomImage;

        public MyViewHolder(View view) {
            super(view);

            imgRoomImage = view.findViewById(R.id.imgRoomImage);
        }
    }
}













