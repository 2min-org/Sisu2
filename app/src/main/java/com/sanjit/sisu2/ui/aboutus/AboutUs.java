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
   //Rajottam
   LinearLayout layoutRajottam;
    ImageView imageViewRajottam;
    //Rojan
    ImageView imageViewRojan;
    LinearLayout layoutRojan;

    View view;
    CardView cardView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_about_us, container, false);
         //Rajottam
        imageViewRajottam=view.findViewById(R.id.rajottamabus);
        layoutRajottam=view.findViewById(R.id.abs1);
        layoutRajottam.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        cardView=view.findViewById(R.id.cd1);
        cardView.setOnClickListener(this);

        //Rojan
        imageViewRojan=view.findViewById(R.id.rojanabus);
        layoutRojan=view.findViewById(R.id.abs2);
        layoutRojan.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        cardView=view.findViewById(R.id.cd2);
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
        int ra=(imageViewRajottam.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(layoutRajottam,new AutoTransition());
        imageViewRajottam.setVisibility(ra);

        int ro=(imageViewRojan.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(layoutRojan,new AutoTransition());
        imageViewRojan.setVisibility(ro);

    }

    @Override
    public void onClick(View v) {
        expand(v);
    }
}