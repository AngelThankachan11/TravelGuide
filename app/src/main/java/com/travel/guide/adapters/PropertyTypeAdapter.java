package com.travel.guide.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.HomeDataResBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PropertyTypeAdapter extends RecyclerView.Adapter<PropertyTypeAdapter.MyViewHolder>{

    List<HomeDataResBean.CategoryItem> ItemList;
    private Context context;
    private PropertTypeClickListener listener;

    public interface PropertTypeClickListener{
        void OnPropertyTypeClicked(int position);
    }

    public PropertyTypeAdapter(Context context, List<HomeDataResBean.CategoryItem> ItemList, PropertTypeClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_property_type, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtProperty.setText(ItemList.get(position).getName());
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgProperty);

        holder.layRoot.setOnClickListener(new View.OnClickListener() {
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
        public LinearLayout layRoot;
        private TextView txtProperty;
        private ImageView imgProperty;

        public MyViewHolder(View view) {
            super(view);
            layRoot = view.findViewById(R.id.layRoot);
            txtProperty = view.findViewById(R.id.txtProperty);
            imgProperty = view.findViewById(R.id.imgProperty);
        }
    }
}







