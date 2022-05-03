package com.travel.guide.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;

import java.util.List;

public class ProfileBtnAdapter extends RecyclerView.Adapter<ProfileBtnAdapter.MyViewHolder>{

    List<String> ItemList;
    List<Integer> IconList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnItemClicked(int Position);
    }

    public ProfileBtnAdapter(Context context, List<String> ItemList, List<Integer> IconList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
        this.IconList = IconList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_profile_btn, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtProfileBtnTitle.setText(ItemList.get(position));
        holder.imgNavIcon.setImageDrawable(context.getResources().getDrawable(IconList.get(position)));
        /*Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgItem);
        holder.txtItemName.setText(ItemList.get(position).getTitle());*/
        holder.layRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout layRoot;
        public TextView txtProfileBtnTitle;
        public ImageView imgNavIcon;

        public MyViewHolder(View view) {
            super(view);

            layRoot = view.findViewById(R.id.layRoot);
            txtProfileBtnTitle = view.findViewById(R.id.txtProfileBtnTitle);
            imgNavIcon = view.findViewById(R.id.imgNavIcon);
        }
    }
}








