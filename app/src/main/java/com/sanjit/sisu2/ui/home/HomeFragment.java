package com.sanjit.sisu2.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.sanjit.sisu2.R;
import com.sanjit.sisu2.adapters.HomeVerAdapter;
import com.sanjit.sisu2.adapters.SliderAdapter;
import com.sanjit.sisu2.adapters.home_horizontal_adapter;
import com.sanjit.sisu2.models.HomeVerModel;
import com.sanjit.sisu2.models.Home_hor_model;
import com.sanjit.sisu2.models.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
   private ViewPager2 viewPager2;
   private Handler sliderHandler = new Handler();


//    RecyclerView home_hor_recycler,home_ver_recycler,home_ver_recycler2,home_ver_recycler3;
//    List<Home_hor_model> home_horizontal_modelList;
//    home_horizontal_adapter home_horizontal_adapter;
//    List<HomeVerModel> homeVerModelList;
//    HomeVerAdapter homeVerAdapter;

    public HomeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager2=root.findViewById(R.id.viewPagerImageSliderHome);

        //Here,i'm preparing list of images from drawable
        // You can also prepare list of images from server or get it from API.
        List<SliderItem> sliderItems=new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.shr5));
        sliderItems.add(new SliderItem(R.drawable.shr2));
        sliderItems.add(new SliderItem(R.drawable.shr3));
        sliderItems.add(new SliderItem(R.drawable.shr4));
        sliderItems.add(new SliderItem(R.drawable.shr1));
        sliderItems.add(new SliderItem(R.drawable.shr5));
        sliderItems.add(new SliderItem(R.drawable.shr2));
        sliderItems.add(new SliderItem(R.drawable.shr3));
        sliderItems.add(new SliderItem(R.drawable.shr4));
        sliderItems.add(new SliderItem(R.drawable.shr1));
        sliderItems.add(new SliderItem(R.drawable.shr5));
        sliderItems.add(new SliderItem(R.drawable.shr2));
        sliderItems.add(new SliderItem(R.drawable.shr3));
        sliderItems.add(new SliderItem(R.drawable.shr4));
        sliderItems.add(new SliderItem(R.drawable.shr1));
        sliderItems.add(new SliderItem(R.drawable.shr5));
        sliderItems.add(new SliderItem(R.drawable.shr2));
        sliderItems.add(new SliderItem(R.drawable.shr3));
        sliderItems.add(new SliderItem(R.drawable.shr4));
        sliderItems.add(new SliderItem(R.drawable.shr1));

        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,3000); //Slide duration 3 seconds
            }
        });


//        home_hor_recycler = root.findViewById(R.id.home_horizontal_recycler);
//        home_ver_recycler = root.findViewById(R.id.home_vertical_recycler);
//        home_ver_recycler2 = root.findViewById(R.id.home_vertical_recycler2);
//        home_ver_recycler3 = root.findViewById(R.id.home_vertical_recycler3);
//
//        home_horizontal_modelList = new ArrayList<Home_hor_model>();
//        homeVerModelList = new ArrayList<HomeVerModel>();
//
//        setHome_horizontal_modelList(home_horizontal_modelList);
//        setHomeVerModelList(homeVerModelList);
//
//        home_horizontal_adapter = new home_horizontal_adapter(getActivity(),home_horizontal_modelList);
//        homeVerAdapter = new HomeVerAdapter(getActivity(),homeVerModelList);
//
//        home_hor_recycler.setAdapter(home_horizontal_adapter);
//        home_ver_recycler.setAdapter(homeVerAdapter);
//        home_ver_recycler2.setAdapter(homeVerAdapter);
//
//        home_hor_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
//        home_ver_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
//        home_ver_recycler2.setLayoutManager(new GridLayoutManager(getActivity(),2));
//
//
//
//        home_hor_recycler.setHasFixedSize(true);
//        home_hor_recycler.setNestedScrollingEnabled(true);
//
//
//        home_ver_recycler.setHasFixedSize(true);
//        home_ver_recycler.setNestedScrollingEnabled(true);
//
//        home_ver_recycler2.setHasFixedSize(false);
//        home_ver_recycler2.setNestedScrollingEnabled(false);
//
//
//
//        //imageSlider
//
//        viewPager2= root.findViewById(R.id.viewPagerImageSlider);
//
//        //Preparing a list of images from drawable folder
//        //You can also use images from API as well
//
//        List<SliderItem> sliderItems = new ArrayList<>();
//        sliderItems.add(new SliderItem(R.drawable.shr4));
//        sliderItems.add(new SliderItem(R.drawable.shr2));
//        sliderItems.add(new SliderItem(R.drawable.shr3));
//        sliderItems.add(new SliderItem(R.drawable.shr1));
//        sliderItems.add(new SliderItem(R.drawable.shr5));
//
//        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
//
//        viewPager2.setClipToPadding(false);
//        viewPager2.setClipChildren(false);
//        viewPager2.setOffscreenPageLimit(3);
//        viewPager2.getChildAt(0).setOverScrollMode(ViewPager2.OVER_SCROLL_NEVER);
//
//        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
//        compositePageTransformer.addTransformer(new MarginPageTransformer(5));
//        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float r = 1 - Math.abs(position);
//                page.setScaleY(0.85f + r * 0.15f);
//                page.setScaleX(0.85f + r * 0.15f);
//            }
//        });
//        viewPager2.setPageTransformer(compositePageTransformer);
//
//
//        return root;
//    }
//
//    private void setHomeVerModelList(List<HomeVerModel> homeVerModelList) {
//        this.homeVerModelList = homeVerModelList;
//        homeVerModelList.add(new HomeVerModel(R.drawable.img_johnson_johnson,"jonson"));
//        homeVerModelList.add(new HomeVerModel(R.drawable.img_astra_zeneca,"albert"));
//        homeVerModelList.add(new HomeVerModel(R.drawable.img_moderna,"moderna"));
//        homeVerModelList.add(new HomeVerModel(R.drawable.img_pfizer,"pfizer"));
//        homeVerModelList.add(new HomeVerModel(R.drawable.img_johnson_johnson,"sputnik"));
//        homeVerModelList.add(new HomeVerModel(R.drawable.img_astra_zeneca,"albert"));
//        homeVerModelList.add(new HomeVerModel(R.drawable.img_moderna,"moderna"));
//        homeVerModelList.add(new HomeVerModel(R.drawable.img_pfizer,"pfizer"));
//        homeVerModelList.add(new HomeVerModel(R.drawable.img_johnson_johnson,"sputnik"));
//
//    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//    }
//
//    public void setHome_horizontal_modelList(List<Home_hor_model> home_horizontal_modelList) {
//        this.home_horizontal_modelList = home_horizontal_modelList;
//        home_horizontal_modelList.add(new Home_hor_model(R.drawable.ic_logo,"Polio"));
//        home_horizontal_modelList.add(new Home_hor_model(R.drawable.ic_logo,"DPT"));
//        home_horizontal_modelList.add(new Home_hor_model(R.drawable.ic_logo,"Measles"));
//        home_horizontal_modelList.add(new Home_hor_model(R.drawable.ic_logo,"Hepatitis B"));
//        home_horizontal_modelList.add(new Home_hor_model(R.drawable.ic_logo,"Hepatitis A"));
//        home_horizontal_modelList.add(new Home_hor_model(R.drawable.ic_logo,"Hib"));
//        home_horizontal_modelList.add(new Home_hor_model(R.drawable.ic_logo,"Rotavirus"));
//        home_horizontal_modelList.add(new Home_hor_model(R.drawable.ic_logo,"Pneumococcal"));
//        home_horizontal_modelList.add(new Home_hor_model(R.drawable.ic_logo,"Influenza"));
//        home_horizontal_modelList.add(new Home_hor_model(R.drawable.ic_logo,"HPV"));
//        home_horizontal_modelList.add(new Home_hor_model(R.drawable.ic_logo,"Meningococcal"));
//        home_horizontal_modelList.add(new Home_hor_model(R.drawable.ic_logo,"Tdap"));
//    }

        return root;
    }
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
}