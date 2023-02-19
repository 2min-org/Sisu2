package com.sanjit.sisu2.ui.aboutus;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sanjit.sisu2.R;

public class AboutUs extends Fragment implements View.OnClickListener {
    TextView detailsText;
    LinearLayout layout;
    ImageView imageViewrojan;

    View view;
    CardView cardView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_about_us, container, false);
        imageViewrojan=view.findViewById(R.id.rojanabus);
        layout=view.findViewById(R.id.abs1);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        cardView=view.findViewById(R.id.cd1);
        cardView.setOnClickListener(this);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AboutUsViewModel mViewModel = new ViewModelProvider(this).get(AboutUsViewModel.class);
        // TODO: Use the ViewModel
    }

    public void expand(View view) {
        int v=(imageViewrojan.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(layout,new AutoTransition());
        imageViewrojan.setVisibility(v);
    }

    @Override
    public void onClick(View v) {
        expand(v);
    }
}