package com.travel.guide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.travel.guide.R;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.RoomDetailsResBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.MyViewHolder> implements ViewPagerAdapter.ItemClickListener {

    List<RoomDetailsResBean.DataItem> ItemList;
    private Context context;

    public RoomTypeAdapter(Context context, List<RoomDetailsResBean.DataItem> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_room_type, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ViewPagerAdapter viewPagerAdapter;
        List<String> bannerImageList = new ArrayList<>();
        int dotscount;
        ImageView[] dots;

        for (int i=0; i<ItemList.get(position).getServiceRoomTypeImages().size(); i++){
            bannerImageList.add(ItemList.get(position).getServiceRoomTypeImages().get(i).getImage());
        }

        holder.txtRoomType.setText(ItemList.get(position).getName());
        holder.txtRoomPrice.setText(ItemList.get(position).getPrice());
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getDefaultIcon()).into(holder.imgDefaultIcon);

        viewPagerAdapter = new ViewPagerAdapter(bannerImageList, context, this);
        holder.viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        if (dotscount>0) {
            holder.cardRoomImages.setVisibility(View.VISIBLE);
            dots = new ImageView[dotscount];

            for (int i = 0; i < dotscount; i++) {

                dots[i] = new ImageView(context);
                dots[i].setImageDrawable(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.nonactive_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                holder.SliderDots.addView(dots[i], params);
            }


            dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

            holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    try {
                        for (int i = 0; i < dotscount; i++) {
                            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));
                        }
                        dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }else {
            holder.cardRoomImages.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    @Override
    public void onBannerClick(int position) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtRoomType, txtRoomPrice;
        ViewPager viewPager;
        LinearLayout SliderDots;
        ImageView imgDefaultIcon;
        CardView cardRoomImages;

        public MyViewHolder(View view) {
            super(view);

            txtRoomType = view.findViewById(R.id.txtRoomType);
            txtRoomPrice = view.findViewById(R.id.txtRoomPrice);
            viewPager = view.findViewById(R.id.viewPager);
            SliderDots = view.findViewById(R.id.SliderDots);
            imgDefaultIcon = view.findViewById(R.id.imgDefaultIcon);
            cardRoomImages = view.findViewById(R.id.cardRoomImages);
        }
    }
}











