package com.sanjit.sisu2.ui.vaccineinformation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.sanjit.sisu2.R;
import com.sanjit.sisu2.adapters.SliderAdapter;
import com.sanjit.sisu2.models.SliderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VaccineInformation extends Fragment {

    Button btn1, btn2, btn3;

    private VaccineInformationViewModel mViewModel;
    private ViewPager2 viewPager2;
    public VaccineInformation(){}
    public static VaccineInformation newInstance() {
        return new VaccineInformation();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_vaccine_information, container, false);
        btn1 = view.findViewById(R.id.btnFrag1);
        btn2 = view.findViewById(R.id.btnFrag2);
        btn3 = view.findViewById(R.id.btnFrag3);

        loadFragment(new Frag1(),0);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Frag1(),1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Frag2(),1);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Frag3(),1);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VaccineInformationViewModel.class);
        // TODO: Use the ViewModel
    }

    public void loadFragment(Fragment fragment,int flag){
        FragmentManager fm= requireActivity().getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        if(flag==0)
            ft.add(R.id.container,fragment);
        else
            ft.replace(R.id.container,fragment);

        ft.commit();

    }

}