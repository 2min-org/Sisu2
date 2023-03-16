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
import androidx.viewpager2.widget.ViewPager2;

import com.sanjit.sisu2.R;

public class VaccineInformation extends Fragment {

    Button btn1, btn2, btn3,btn4,btn5,btn6,btn7;

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
        btn4 = view.findViewById(R.id.btnFrag4);
        btn5 = view.findViewById(R.id.btnFrag5);
        btn6 = view.findViewById(R.id.btnFrag6);
        btn7 = view.findViewById(R.id.btnFrag7);

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

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Frag4(),1);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Frag5(),1);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Frag6(),1);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Frag7(),1);
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
            ft.add(R.id.fragment_container,fragment);
        else
            ft.replace(R.id.fragment_container,fragment);

        ft.commit();

    }

}