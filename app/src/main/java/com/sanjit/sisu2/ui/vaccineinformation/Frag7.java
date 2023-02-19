package com.sanjit.sisu2.ui.vaccineinformation;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.sanjit.sisu2.R;
import com.sanjit.sisu2.ui.home.SliderAdapter;
import com.sanjit.sisu2.models.SliderItem;

import java.util.ArrayList;
import java.util.List;


public class Frag7 extends Fragment {

    private ViewPager2 viewPager3;
    private final Handler sliderHandler = new Handler();

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

    public Frag7() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_frag1, container, false);
        viewPager3=view.findViewById(R.id.viewPagerImageSliderDisease);

        List<SliderItem> sliderItems=new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.diptheriavacc));
        sliderItems.add(new SliderItem(R.drawable.pv));
        sliderItems.add(new SliderItem(R.drawable.tetanusvacc));
        sliderItems.add(new SliderItem(R.drawable.pertusisvacc));
        sliderItems.add(new SliderItem(R.drawable.cpv));

        viewPager3.setAdapter(new SliderAdapter(sliderItems,viewPager3));

        viewPager3.setClipToPadding(false);
        viewPager3.setClipChildren(false);
        viewPager3.setOffscreenPageLimit(3);
        viewPager3.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(5));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
                page.setScaleX(0.85f + r * 0.15f);
            }
        });
        viewPager3.setPageTransformer(compositePageTransformer);

        return view;
    }
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager3.setCurrentItem(viewPager3.getCurrentItem() + 1);
        }
    };
}