package com.travel.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.RoomDetailsResBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomTypeCardAdapter extends RecyclerView.Adapter<RoomTypeCardAdapter.MyViewHolder>{

    List<RoomDetailsResBean.DataItem> roomData;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnRoomTypeCardClicked(int Position, boolean isSelected);
    }

    public RoomTypeCardAdapter(Context context, List<RoomDetailsResBean.DataItem> roomData, ItemClickListener listener) {
        this.roomData = roomData;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_room_type_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.txtRoomType.setText(roomData.get(position).getName());
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+roomData.get(position).getDefaultIcon()).into(holder.imgIcon);
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+roomData.get(position).getIcon()).into(holder.imgDefaultIcon);
        if (roomData.get(position).getIsSelected()){
            holder.consInner.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner10_app_border));
        }else {
            holder.consInner.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        holder.layRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (roomData.get(position).getIsSelected()){
                    listener.OnRoomTypeCardClicked(position, false);
                }else {
                    listener.OnRoomTypeCardClicked(position, true);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return roomData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layRoot, consInner;
        private TextView txtRoomType;
        private ImageView imgIcon, imgDefaultIcon;

        public MyViewHolder(View view) {
            super(view);
            layRoot = view.findViewById(R.id.layRoot);
            consInner = view.findViewById(R.id.consInner);
            txtRoomType = view.findViewById(R.id.txtRoomType);
            imgIcon = view.findViewById(R.id.imgIcon);
            imgDefaultIcon = view.findViewById(R.id.imgDefaultIcon);
        }
    }
}








