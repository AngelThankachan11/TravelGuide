package com.travel.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.HomeDataResBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder>{

    List<HomeDataResBean.ServicePropertyItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnPopularClicked(int Position);
    }

    public PopularAdapter(Context context, List<HomeDataResBean.ServicePropertyItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_popular, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgProperty);
        holder.txtHostelName.setText(ItemList.get(position).getBusinessName());
        holder.txtPropertyType.setText(ItemList.get(position).getBusinesscat().getName());
        holder.txtPropertyAddress.setText(ItemList.get(position).getAddress());
        holder.layRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnPopularClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView layRoot;
        public ImageView imgProperty;
        TextView txtHostelName, txtPropertyType, txtPropertyAddress;

        public MyViewHolder(View view) {
            super(view);

            layRoot = view.findViewById(R.id.layRoot);
            imgProperty = view.findViewById(R.id.imgProperty);
            txtHostelName = view.findViewById(R.id.txtHostelName);
            txtPropertyType = view.findViewById(R.id.txtPropertyType);
            txtPropertyAddress = view.findViewById(R.id.txtPropertyAddress);
        }
    }
}







