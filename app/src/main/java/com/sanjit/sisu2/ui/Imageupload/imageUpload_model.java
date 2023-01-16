package com.sanjit.sisu2.ui.Imageupload;

import java.util.ArrayList;

public class imageUpload_model {

    private ArrayList<basic_model> basic_model_arr;

    public imageUpload_model(ArrayList<basic_model> basic_model_arr) {
        this.basic_model_arr = basic_model_arr;
    }

    public ArrayList<basic_model> getBasic_model_arr() {
        return basic_model_arr;
    }

    public void setBasic_model_arr(ArrayList<basic_model> basic_model_arr) {
        this.basic_model_arr = basic_model_arr;
    }
}
