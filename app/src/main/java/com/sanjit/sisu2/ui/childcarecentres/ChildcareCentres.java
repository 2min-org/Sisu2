package com.sanjit.sisu2.ui.childcarecentres;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sanjit.sisu2.R;

public class ChildcareCentres extends Fragment {

    private ChildcareCentresViewModel mViewModel;

    public static ChildcareCentres newInstance() {
        return new ChildcareCentres();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_childcare_centres, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChildcareCentresViewModel.class);
        // TODO: Use the ViewModel
    }

}