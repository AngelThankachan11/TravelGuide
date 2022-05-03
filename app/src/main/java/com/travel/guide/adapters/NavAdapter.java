package com.travel.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;

public class NavAdapter extends RecyclerView.Adapter<NavAdapter.MyViewHolder>{

    Context context;
    private String[] navItems;
    private int[] navImages;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener{
        void onNavItemClick(int Position);
    }

    public NavAdapter(Context context, String[] navItems, int[] navImages, ItemClickListener clickListener) {
        this.context = context;
        this.navItems = navItems;
        this.navImages = navImages;
        this.itemClickListener = clickListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_child_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int position) {
        viewHolder.txtNavName.setText(navItems[position]);
        viewHolder.imgNavIcon.setImageDrawable(context.getResources().getDrawable(navImages[position]));

        viewHolder.layNavItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onNavItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return navItems.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layNavItem;
        TextView txtNavName;
        ImageView imgNavIcon, imgArrow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layNavItem = itemView.findViewById(R.id.layNavItem);
            txtNavName = itemView.findViewById(R.id.txtNavName);
            imgNavIcon = itemView.findViewById(R.id.imgNavIcon);
            imgArrow = itemView.findViewById(R.id.imgArrow);

        }
    }
}




