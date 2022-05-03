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
import com.travel.guide.apicalls.model.PropertyTypeResBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SelectNeedsAdapter extends RecyclerView.Adapter<SelectNeedsAdapter.MyViewHolder>{

    List<PropertyTypeResBean.DataItem> ItemList;
    private Context context;
    private PropertTypeClickListener listener;

    public interface PropertTypeClickListener{
        void OnPropertyTypeClicked(int position);
    }

    public SelectNeedsAdapter(Context context, List<PropertyTypeResBean.DataItem> ItemList, PropertTypeClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_select_needs, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.txtProperty.setText(ItemList.get(position).getName());
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgProperty);

        holder.consRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnPropertyTypeClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout consRoot;
        private TextView txtProperty;
        private ImageView imgProperty;

        public MyViewHolder(View view) {
            super(view);
            consRoot = view.findViewById(R.id.consRoot);
            txtProperty = view.findViewById(R.id.txtProperty);
            imgProperty = view.findViewById(R.id.imgProperty);
        }
    }
}







