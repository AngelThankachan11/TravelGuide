package com.travel.guide.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.databinding.FragmentSearchByInstituteBinding;
import com.travel.guide.MainActivity;
import com.travel.guide.R;
import com.travel.guide.adapters.DistanceRangeAdapter;
import com.travel.guide.adapters.InstituteAdapter;
import com.travel.guide.apicalls.model.DistanceRangeResBean;
import com.travel.guide.apicalls.model.InstituteListResBean;
import com.travel.guide.apicalls.model.SearchByInsResBean;
import com.travel.guide.apicalls.model.ServiceableCityResBean;
import com.travel.guide.apicalls.presenter.SearchByInstitutePresenter;
import com.travel.guide.apicalls.viewclass.ISearchByInstituteView;
import com.travel.guide.utils.AppUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchByInstituteFragment extends Fragment implements InstituteAdapter.ItemClickListener, View.OnClickListener, ISearchByInstituteView
        , DistanceRangeAdapter.ItemClickListener {

    FragmentSearchByInstituteBinding binding;
    ArrayList<String> cityArray = new ArrayList<>();
    ArrayList<ServiceableCityResBean.DataItem> cityList = new ArrayList<>();
    String selectedCityId="";
    String propertyTypeId="";

    List<InstituteListResBean.DataItem> instituteList = new ArrayList<>();
    InstituteAdapter instituteAdapter;
    SearchByInstitutePresenter presenter;
    Dialog dialogDistance;
    RecyclerView rvDistanceRange;
    DistanceRangeAdapter distanceRangeAdapter;
    List<DistanceRangeResBean.DistanceItem> distanceRangeList = new ArrayList<>();
    String selectedDistanceRangeId="";
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_search_by_institute, container, false);
        presenter = new SearchByInstitutePresenter();
        presenter.setView(this);
        propertyTypeId = getArguments().getString("propertyTypeId");

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_drop_down_view, R.id.txtSpinItem, cityArray);
        binding.lvCity.setAdapter(adapter);

        binding.edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                if (cs.length()>0) {
                    binding.lvCity.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter(cs);
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
                selectedCityId = cityList.get(position).getCityId();
                presenter.CallIntituteList(getActivity(), selectedCityId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
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
                presenter.CallIntituteList(getActivity(), selectedCityId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
            }
        });

        instituteAdapter = new InstituteAdapter(getActivity(), instituteList, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.recyInstitute.setLayoutManager(gridLayoutManager2);
        binding.recyInstitute.setItemAnimator(new DefaultItemAnimator());
        binding.recyInstitute.setAdapter(instituteAdapter);

        int resId = R.anim.layout_animation;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        binding.recyInstitute.setLayoutAnimation(animation);

        presenter.CityCall(getActivity(), ((MainActivity)getActivity()).userData.getACCESS_TOKEN());

        binding.imgBack.setOnClickListener(this);
        binding.cardDistanceRange.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void OnInstituteClicked(int position, String selectedBranchId) {
        if (selectedBranchId!=null) {
            presenter.CallSearchByInstitute(getActivity(), propertyTypeId, selectedDistanceRangeId, selectedCityId, "" + instituteList.get(position).getId(), selectedBranchId,
                    ((MainActivity) getActivity()).userData.getACCESS_TOKEN());
        }else {
            ((MainActivity)getActivity()).toast("Please select branch");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                getParentFragmentManager().popBackStack();
                break;

            case R.id.cardDistanceRange:
                if (distanceRangeList.size()>0){
                    showDistanceRangeDialog(getActivity());
                }else {
                    presenter.getDistanceRange(getActivity(), ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void OnDistanceChecked(int position, boolean isChecked) {
        if (isChecked) {
            for (int i = 0; i < distanceRangeList.size(); i++) {
                distanceRangeList.get(i).setIsChecked(false);
            }
            distanceRangeList.get(position).setIsChecked(true);
            selectedDistanceRangeId = distanceRangeList.get(position).getId();

            rvDistanceRange.post(new Runnable() {
                @Override
                public void run() {
                    distanceRangeAdapter.notifyDataSetChanged();
                }
            });
        }else {
            selectedDistanceRangeId="";
            distanceRangeList.get(position).setIsChecked(true);
        }
    }

    public void showDistanceRangeDialog(Activity activity){
        TextView txtSetRange;
        ImageView imgCross;

        dialogDistance = new Dialog(activity);
        dialogDistance.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDistance.setCancelable(true);
        dialogDistance.setContentView(R.layout.dialog_distance);

        rvDistanceRange = dialogDistance.findViewById(R.id.recyDistanceRange);
        txtSetRange = dialogDistance.findViewById(R.id.txtSet);
        imgCross = dialogDistance.findViewById(R.id.imgCross);

        distanceRangeAdapter = new DistanceRangeAdapter(getActivity(), distanceRangeList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        rvDistanceRange.setLayoutManager(gridLayoutManager);
        rvDistanceRange.setItemAnimator(new DefaultItemAnimator());
        rvDistanceRange.setAdapter(distanceRangeAdapter);

        txtSetRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDistance.dismiss();
            }
        });

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDistance.dismiss();
            }
        });

        dialogDistance.show();
        Window window = dialogDistance.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

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
            adapter.notifyDataSetChanged();
        }else {
            ((MainActivity)getActivity()).toast("City list not found");
        }
        selectedCityId = cityList.get(0).getCityId();
        presenter.CallIntituteList(getActivity(), selectedCityId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
    }

    @Override
    public void onDistanceRangeSuccess(DistanceRangeResBean item) {
        if (item.isStatus()){
            distanceRangeList.clear();
            distanceRangeList.addAll(item.getDistance());
            showDistanceRangeDialog(getActivity());
        }else {
            ((MainActivity)getActivity()).toast("Distance range list not found");
        }
    }

    @Override
    public void onInstituteListSuccess(InstituteListResBean item) {
        instituteList.clear();
        if (item.isStatus()){
            instituteList.addAll(item.getData());
        }else {
            ((MainActivity)getActivity()).toast("No institute found in this city");
        }
        instituteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchByInsSuccess(SearchByInsResBean item) {
        if (item.isStatus()){
            Fragment fragment = new PropertyListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("propertyData", (Serializable) item.getData());
            bundle.putSerializable("from", "SearchByIns");
            fragment.setArguments(bundle);
            AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
        }else {
            ((MainActivity)getActivity()).toast("No property found near selected institute");
        }
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
