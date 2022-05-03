package com.travel.guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.travel.guide.databinding.ActivityUserDetailsBinding;


public class UserDetailsActivity extends BaseActivity{

    ActivityUserDetailsBinding binding;
    String[] country = { "Select User Type" ,"Student", "Parent"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spin_drop_down_view,country){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = super.getView(position, convertView, parent);

                TextView text = (TextView)view.findViewById(R.id.txtSpinItem);
                text.setTextColor(getResources().getColor(R.color.black));

                return view;

            }};
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinUserType.setAdapter(aa);
        binding.spinUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnProceed:
                startActivity(new Intent(UserDetailsActivity.this, OtpActivity.class));
            break;
            default:
                break;
        }
    }
}