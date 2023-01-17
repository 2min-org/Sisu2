package com.sanjit.sisu2.ui.vaccineinformation;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.sanjit.sisu2.R;
import com.sanjit.sisu2.ui.home.SliderAdapter;
import com.sanjit.sisu2.models.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class Frag3 extends Fragment {

    private VaccineInformationViewModel mViewModel;
    private ViewPager2 viewPager4;
    private Handler sliderHandler = new Handler();

    public static VaccineInformation newInstance() {
        return new VaccineInformation();
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_frag2, container, false);
        viewPager4=view.findViewById(R.id.viewPagerImageSliderDisease1);

        //Your set of instruction here..
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.pv));
        sliderItems.add(new SliderItem(R.drawable.cpv));
        sliderItems.add(new SliderItem(R.drawable.mgl));
        sliderItems.add(new SliderItem(R.drawable.iv));
        sliderItems.add(new SliderItem(R.drawable.mv));

        viewPager4.setAdapter(new SliderAdapter(sliderItems, viewPager4));

        viewPager4.setClipToPadding(false);
        viewPager4.setClipChildren(false);
        viewPager4.setOffscreenPageLimit(3);
        viewPager4.getChildAt(0).setOverScrollMode(ViewPager2.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
                page.setScaleX(0.85f + r * 0.15f);
            }
        });
        viewPager4.setPageTransformer(compositePageTransformer);
        return view;
    }
}