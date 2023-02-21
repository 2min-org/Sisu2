package com.sanjit.sisu2.ui.diseaseinformation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanjit.sisu2.R;
import com.sanjit.sisu2.adapters.DIHorAdapter;
import com.sanjit.sisu2.models.DIHorModel;

import java.util.ArrayList;
import java.util.List;

public class DiseaseInformation extends Fragment {
    RecyclerView diHorizontalRec;
    List<DIHorModel>diHorModelList;
    DIHorAdapter diHorAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_disease_information,container,false);
        diHorizontalRec=root.findViewById(R.id.di_hor_rec);

        diHorModelList= new ArrayList<>();
        diHorModelList.add(new DIHorModel(R.drawable.polio,"Polio"));
        diHorModelList.add(new DIHorModel(R.drawable.commoncold,"Common cold"));
        diHorModelList.add(new DIHorModel(R.drawable.chickenpox,"Chicken pox"));
        diHorModelList.add(new DIHorModel(R.drawable.influenza,"Influenza"));
        diHorModelList.add(new DIHorModel(R.drawable.hepatitisb,"Hepatitis B"));
        diHorModelList.add(new DIHorModel(R.drawable.measlesdis,"Measles"));
        diHorModelList.add(new DIHorModel(R.drawable.coviddis,"Covid-19"));
        diHorModelList.add(new DIHorModel(R.drawable.diptheriavacc,"Diphtheria"));
        diHorModelList.add(new DIHorModel(R.drawable.tetanusdis,"Tetanus"));
        diHorModelList.add(new DIHorModel(R.drawable.meningdis,"Meningococcal"));
        diHorModelList.add(new DIHorModel(R.drawable.pertusisdis,"Pertussis"));



        diHorAdapter=new DIHorAdapter(getActivity(),diHorModelList);

        diHorizontalRec.setAdapter(diHorAdapter);

        diHorizontalRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));


        diHorizontalRec.setHasFixedSize(true);
        diHorizontalRec.setNestedScrollingEnabled(false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DiseaseInformationViewModel mViewModel = new ViewModelProvider(this).get(DiseaseInformationViewModel.class);
        // TODO: Use the ViewModel
    }

}