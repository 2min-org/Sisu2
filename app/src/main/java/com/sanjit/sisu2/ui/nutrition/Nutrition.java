package com.sanjit.sisu2.ui.nutrition;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sanjit.sisu2.R;
import com.sanjit.sisu2.models.SliderItem;
import com.sanjit.sisu2.ui.vaccineinformation.Frag1;
import com.sanjit.sisu2.ui.vaccineinformation.Frag2;
import com.sanjit.sisu2.ui.vaccineinformation.Frag3;
import com.sanjit.sisu2.ui.vaccineinformation.Frag4;
import com.sanjit.sisu2.ui.vaccineinformation.Frag5;
import com.sanjit.sisu2.ui.vaccineinformation.Frag6;
import com.sanjit.sisu2.ui.vaccineinformation.Frag7;
import com.sanjit.sisu2.ui.vaccineinformation.VaccineInformation;
import com.sanjit.sisu2.ui.vaccineinformation.VaccineInformationViewModel;
import com.sanjit.sisu2.ui.vaccineinformation.adapter;

import java.util.ArrayList;
import java.util.List;

public class Nutrition extends Fragment {
    private ViewPager2 viewPager4;
    private final Handler sliderHandler = new Handler();
    private NutritionViewModel mViewModel;
    Button btn1, btn2, btn3,btn4,btn5,btn6,btn7;

    public static Nutrition newInstance() {
        return new Nutrition();
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Nutrition() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NutritionViewModel.class);
    }

        public void loadFragment(Fragment fragment,int flag){
            FragmentManager fm= requireActivity().getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();

            if(flag==0)
                ft.add(R.id.fragment_container,fragment);
            else
                ft.replace(R.id.fragment_container,fragment);

            ft.commit();

        }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nutrition, container, false);

        btn1 = view.findViewById(R.id.btnFrag1);
        btn2 = view.findViewById(R.id.btnFrag2);
        btn3 = view.findViewById(R.id.btnFrag3);
        btn4 = view.findViewById(R.id.btnFrag4);
        btn5 = view.findViewById(R.id.btnFrag5);
        btn6 = view.findViewById(R.id.btnFrag6);

        loadFragment(new nutfrag1(),0);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new nutfrag1(),1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new nutfrag2(),1);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new nutfrag3(),1);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new nutfrag4(),1);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new nutfrag5(),1);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new nutfrag6(),1);
            }
        });

        return view;
    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager4.setCurrentItem(viewPager4.getCurrentItem() + 1);
        }
    };
}
