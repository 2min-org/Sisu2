package com.sanjit.sisu2.ui.vaccineinformation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.sanjit.sisu2.R;
import com.sanjit.sisu2.adapters.SliderAdapter;
import com.sanjit.sisu2.models.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class VaccineInformation extends Fragment {

    private VaccineInformationViewModel mViewModel;
    private ViewPager2 viewPager2;
    public VaccineInformation(){}
    public static VaccineInformation newInstance() {
        return new VaccineInformation();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_vaccine_information, container, false);
        viewPager2= root.findViewById(R.id.viewPagerImageSlider);

        //Preparing a list of images from drawable folder
        //You can also use images from API as well

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.pv));
        sliderItems.add(new SliderItem(R.drawable.cpv));
        sliderItems.add(new SliderItem(R.drawable.mgl));
        sliderItems.add(new SliderItem(R.drawable.iv));
        sliderItems.add(new SliderItem(R.drawable.mv));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(ViewPager2.OVER_SCROLL_NEVER);

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
        viewPager2.setPageTransformer(compositePageTransformer);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VaccineInformationViewModel.class);
        // TODO: Use the ViewModel
    }

}