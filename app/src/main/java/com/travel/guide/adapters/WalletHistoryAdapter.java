package com.travel.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.apicalls.model.WalletResBean;

import java.util.List;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.MyViewHolder>{

    List<WalletResBean.Data.TransactionItem> ItemList;
    private Context context;

    public WalletHistoryAdapter(Context context, List<WalletResBean.Data.TransactionItem> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_wallet_history, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.txtHeading.setText(ItemList.get(position).getTransactionId()+" ("+ItemList.get(position).getTransactionType()+")");
        if (ItemList.get(position).getTransactionType().equalsIgnoreCase("CR")){
            holder.txtAmount.setText("+"+ItemList.get(position).getAmount());
            holder.txtAmount.setTextColor(context.getResources().getColor(R.color.green));
        }else {
            holder.txtAmount.setText("-"+ItemList.get(position).getAmount());
            holder.txtAmount.setTextColor(context.getResources().getColor(R.color.red));
        }
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
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layRoot;
        private TextView txtHeading, txtAmount;

        public MyViewHolder(View view) {
            super(view);
            layRoot = view.findViewById(R.id.layRoot);
            txtHeading = view.findViewById(R.id.txtHeading);
            txtAmount = view.findViewById(R.id.txtAmount);
        }
    }
}






