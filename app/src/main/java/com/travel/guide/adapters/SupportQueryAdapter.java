package com.travel.guide.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.apicalls.model.FaqResBean;

import java.util.List;

public class SupportQueryAdapter extends RecyclerView.Adapter<SupportQueryAdapter.MyViewHolder>{

    List<FaqResBean.FaqItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnQuestionClicked(int position);
    }

    public SupportQueryAdapter(Context context, List<FaqResBean.FaqItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_lay_support_query, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (ItemList.get(position).getIsOpen()){
            holder.txtContent.setVisibility(View.VISIBLE);
            holder.imgArrow.setRotation(180);

        }else {
            holder.txtContent.setVisibility(View.GONE);
            holder.imgArrow.setRotation(0);
        }
        holder.txtTitle.setText(ItemList.get(position).getTitle());
        holder.txtContent.setText(ItemList.get(position).getDescription());
        holder.layRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnQuestionClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView layRoot;
        TextView txtTitle, txtContent;
        ImageView imgArrow;

        public MyViewHolder(View view) {
            super(view);
            layRoot = view.findViewById(R.id.layRoot);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtContent = view.findViewById(R.id.txtContent);
            imgArrow = view.findViewById(R.id.imgArrow);
        }
    }
}










