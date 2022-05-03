package com.travel.guide.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.guide.databinding.FragmentSearchByLocationBinding;
import com.travel.guide.MainActivity;
import com.travel.guide.PropertyDetailsActivity;
import com.travel.guide.R;
import com.travel.guide.adapters.SearchByLPropertListAdapter;
import com.travel.guide.apicalls.model.AreaListResBean;
import com.travel.guide.apicalls.model.SearchByLPropertyResBean;
import com.travel.guide.apicalls.model.ServiceableCityResBean;
import com.travel.guide.apicalls.presenter.SearchByLocationPresenter;
import com.travel.guide.apicalls.viewclass.ISearchByLView;

import java.util.ArrayList;
import java.util.List;

public class SearchByLocationFragment extends Fragment implements SearchByLPropertListAdapter.ItemClickListener, View.OnClickListener, ISearchByLView {

    FragmentSearchByLocationBinding binding;
    ArrayList<String> cityArray = new ArrayList<>();
    ArrayList<ServiceableCityResBean.DataItem> cityList = new ArrayList<>();
    //private ArrayAdapter cityAdapter;
    String selectedCityId="";
    String propertyTypeId = "", rangeId="";

    List<SearchByLPropertyResBean.DataItem> propertyList = new ArrayList<>();
    SearchByLPropertListAdapter searchByLPropertListAdapter;
    SearchByLocationPresenter presenter;
    String selectedAreaId = "";
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_search_by_location, container, false);
        presenter = new SearchByLocationPresenter();
        presenter.setView(this);
        propertyTypeId = getArguments().getString("propertyTypeId");
        rangeId = getArguments().getString("rangeId");

        /*cityAdapter = new ArrayAdapter(getContext(),R.layout.spin_drop_down_view, cityArray){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = super.getView(position, convertView, parent);

                TextView text = (TextView)view.findViewById(R.id.txtSpinItem);
                text.setTextColor(getResources().getColor(R.color.app_color));

                return view;

            }};

        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        binding.spinCity.setAdapter(cityAdapter);
        binding.spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedAreaId = "";
                selectedCityId = cityList.get(position).getCityId();
                presenter.callAreaList(getActivity(), selectedCityId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
                //presenter.CallSearchByLocation(getActivity(), selectedCityId, propertyTypeId, rangeId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_drop_down_view, R.id.txtSpinItem, cityArray);
        binding.lvCity.setAdapter(adapter);

        binding.edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                if (cs.length()>0) {
                    binding.lvCity.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter(cs);
                    /*List<String> temp = new ArrayList();
                    for(String d: cityArray){
                        //or use .equal(text) with you want equal match
                        //use .toLowerCase() for better matches
                        if(d.contains(cs)){
                            temp.add(d);
                        }
                    }
                    if (temp.size()==0){
                        ((MainActivity)getActivity()).toast("No city found");
                    }*/
                    if (adapter.getCount()==0){
                        ((MainActivity)getActivity()).toast("No data found");
                    }

                }else {
                    binding.lvCity.setVisibility(View.GONE);
                    adapter.getFilter().filter("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        /*binding.lvCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedAreaId = "";
                selectedCityId = cityList.get(position).getCityId();
                presenter.callAreaList(getActivity(), selectedCityId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        binding.lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                selectedCityId = "";
                for (int i=0; i<cityList.size(); i++){
                    if (cityList.get(i).getName().equalsIgnoreCase(binding.lvCity.getItemAtPosition(position).toString())){
                        selectedCityId = cityList.get(i).getCityId();
                    }
                }

                binding.edtSearch.setText(binding.lvCity.getItemAtPosition(position).toString());
                binding.lvCity.setVisibility(View.GONE);
                presenter.callAreaList(getActivity(), selectedCityId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
            }
        });

        searchByLPropertListAdapter = new SearchByLPropertListAdapter(getActivity(), propertyList, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.recyProperty.setLayoutManager(gridLayoutManager2);
        binding.recyProperty.setItemAnimator(new DefaultItemAnimator());
        binding.recyProperty.setAdapter(searchByLPropertListAdapter);

        int resId = R.anim.layout_animation;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        binding.recyProperty.setLayoutAnimation(animation);

        presenter.CityCall(getActivity(), ((MainActivity)getActivity()).userData.getACCESS_TOKEN());

        binding.imgBack.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void OnItemClicked(int Position) {
        Intent intent = new Intent(getActivity(), PropertyDetailsActivity.class);
        intent.putExtra("propertyId", ""+propertyList.get(Position).getId());
        intent.putExtra("couponId", "");
        getActivity().startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                getParentFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCitySucess(ServiceableCityResBean item) {
        if (item.isStatus()){
            cityList.clear();
            cityArray.clear();
            cityList.addAll(item.getData());
            for (int i=0; i<item.getData().size(); i++){
                cityArray.add(item.getData().get(i).getName());
            }
        }else {
            ((MainActivity)getActivity()).toast("City list not found");
        }
        adapter.notifyDataSetChanged();
        selectedCityId = cityList.get(0).getCityId();
        presenter.callAreaList(getActivity(), selectedCityId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
        //cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchByLSuccess(SearchByLPropertyResBean item) {
        propertyList.clear();
        if (item.isStatus()){
            propertyList.addAll(item.getData());
        }else {
            ((MainActivity)getActivity()).toast("No result found");
        }
        searchByLPropertListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAreaListSuccess(AreaListResBean item) {
        binding.consPopularHostelBlogs.removeAllViews();
        if (item.isStatus()){
            for (int i=0; i<item.getData().size(); i++) {
                View childView = LayoutInflater.from(getContext()).inflate(R.layout.child_popular_blogs, binding.consPopularHostelBlogs, false);
                TextView txtHostelName = childView.findViewById(R.id.txtHostelName);
                txtHostelName.setText(item.getData().get(i).getArea());
                childView.setId(i);
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*Intent intent = new Intent(getActivity(), PropertyDetailsActivity.class);
                        intent.putExtra("propertyId", item.getData().get(view.getId()).getId());
                        intent.putExtra("couponId", "");
                        getActivity().startActivity(intent);*/
                        //txtHostelName.setTextColor(getResources().getColor(R.color.gray));
                        selectedAreaId = item.getData().get(view.getId()).getId();
                        presenter.CallSearchByLocation(getActivity(), selectedCityId, selectedAreaId, propertyTypeId, rangeId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
                    }
                });
                binding.consPopularHostelBlogs.addView(childView);
            }
        }else {
            ((MainActivity)getActivity()).toast("Popular list not found");
        }

        presenter.CallSearchByLocation(getActivity(), selectedCityId, selectedAreaId, propertyTypeId, rangeId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {
        ((MainActivity)getActivity()).enableLoadingBar(context, enable);
    }

    @Override
    public void onError(String reason) {
        ((MainActivity)getActivity()).toast(reason);
    }
}

