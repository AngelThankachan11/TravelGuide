package com.travel.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.InstituteListResBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class InstituteAdapter extends RecyclerView.Adapter<InstituteAdapter.MyViewHolder>{

    List<InstituteListResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;
    private ArrayAdapter bindingAdapter;
    String selectedBranchId="";

    public interface ItemClickListener{
        void OnInstituteClicked(int position, String selectedBranchId);
    }

    public InstituteAdapter(Context context, List<InstituteListResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_institute, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        ArrayList<String> branchArray = new ArrayList<>();
        ArrayList<String> branchIdArray = new ArrayList<>();
        holder.txtInstituteName.setText(ItemList.get(position).getName());
        branchArray.add("Select branch");
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgInstitute);
        for (int i=0; i<ItemList.get(position).getInstitutecopies().size(); i++){
            branchArray.add(ItemList.get(position).getInstitutecopies().get(i).getBranchName());
            branchIdArray.add(""+ItemList.get(position).getInstitutecopies().get(i).getId());
        }
        bindingAdapter = new ArrayAdapter(context,R.layout.spin_drop_down_view2, branchArray){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = super.getView(position, convertView, parent);

                TextView text = (TextView)view.findViewById(R.id.txtSpinItem);
                text.setTextColor(context.getResources().getColor(R.color.app_color));

                return view;

            }
        };
        bindingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        holder.spinBranch.setAdapter(bindingAdapter);
        holder.spinBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position>0) {
                    selectedBranchId = branchIdArray.get(position-1);
                    holder.txtBranch.setText(branchArray.get(position));
                    holder.txtBranch.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner10_app_border));
                }else {
                    selectedBranchId = null;
                    holder.txtBranch.setText(branchArray.get(position));
                    holder.txtBranch.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_gray_border));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.consProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnInstituteClicked(position, selectedBranchId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView layRoot;
        public Spinner spinBranch;
        public ImageView imgInstitute;
        public TextView txtInstituteName, txtBranch;
        ConstraintLayout consProceed;

        public MyViewHolder(View view) {
            super(view);

            layRoot = view.findViewById(R.id.layRoot);
            spinBranch = view.findViewById(R.id.spinBranch);
            imgInstitute = view.findViewById(R.id.imgInstitute);
            txtInstituteName = view.findViewById(R.id.txtInstituteName);
            consProceed = view.findViewById(R.id.consProceed);
            txtBranch = view.findViewById(R.id.txtBranch);
        }
    }
}








