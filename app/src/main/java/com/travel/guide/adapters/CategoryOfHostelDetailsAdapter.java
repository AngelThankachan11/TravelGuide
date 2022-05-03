package com.travel.guide.adapters;

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
import com.travel.guide.apicalls.model.PropertyDetailsResBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryOfHostelDetailsAdapter extends RecyclerView.Adapter<CategoryOfHostelDetailsAdapter.MyViewHolder>{

    List<PropertyDetailsResBean.AmenitiesItem> ameniteisList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnItemClicked(int Position);
    }

    public CategoryOfHostelDetailsAdapter(Context context, List<PropertyDetailsResBean.AmenitiesItem> ameniteisList, ItemClickListener listener) {
        this.ameniteisList = ameniteisList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_category_of_hostel_detaisl, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.txtItemName.setText(ameniteisList.get(position).getName());
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ameniteisList.get(position).getIcon()).into(holder.imgItem);
        /*Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgItem);
        holder.txtItemName.setText(ItemList.get(position).getTitle());
        holder.layRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClicked(position);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return ameniteisList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layRoot;
        private TextView txtItemName;
        private ImageView imgItem;

        public MyViewHolder(View view) {
            super(view);
            layRoot = view.findViewById(R.id.layRoot);
            txtItemName = view.findViewById(R.id.txtItemName);
            imgItem = view.findViewById(R.id.imgItem);
        }
    }
}







