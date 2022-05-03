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
import com.travel.guide.apicalls.model.SearchByLPropertyResBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchByLPropertListAdapter extends RecyclerView.Adapter<SearchByLPropertListAdapter.MyViewHolder>{

    List<SearchByLPropertyResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnItemClicked(int position);
    }

    public SearchByLPropertListAdapter(Context context, List<SearchByLPropertyResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_searchbyl_property, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgBoysHostel);
        holder.txtHostelName.setText(ItemList.get(position).getBusinessName());
        holder.txtHostelType.setText("("+ItemList.get(position).getBusinesscat().getName()+")");
        holder.txtPriceRange.setText("Price - \u20B9 "+ItemList.get(position).getMinPrice()+"-"+ItemList.get(position).getMaxPrice());
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

        public CardView layRoot;
        ImageView imgBoysHostel;
        TextView txtHostelName, txtHostelType, txtPriceRange;

        public MyViewHolder(View view) {
            super(view);

            layRoot = view.findViewById(R.id.layRoot);
            imgBoysHostel = view.findViewById(R.id.imgBoysHostel);
            txtHostelName = view.findViewById(R.id.txtHostelName);
            txtHostelType = view.findViewById(R.id.txtHostelType);
            txtPriceRange = view.findViewById(R.id.txtPriceRange);
        }
    }
}









