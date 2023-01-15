package com.sanjit.sisu2.ui.Imageupload;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanjit.sisu2.R;

import java.util.zip.Inflater;


public class imageUpload extends Fragment {


    public imageUpload() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_upload, container, false);



        return view;
    }
}