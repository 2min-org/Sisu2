package com.sanjit.sisu2.ui.VaccineSchedule;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;

import com.sanjit.sisu2.R;

import java.util.ArrayList;


public class vaccine_schedule extends Fragment {

    //declaring variables

    Spinner child_select_list;
    ArrayList<String> child_list = new ArrayList<>();
    CalendarView calendarView;

    //end of declaring variables

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vaccine_schedule, container, false);

        //declarations

        calendarView = view.findViewById(R.id.calendarView);
        child_select_list = view.findViewById(R.id.child_select_list);
        SharedPreferences User = getActivity().getSharedPreferences("User", MODE_PRIVATE);
        String user =User.getString("user_mode", "Patient");

        //end of declarations

        if(user.equals("Patient")) child_select_list.setVisibility(View.VISIBLE);
        else child_select_list.setVisibility(View.GONE);

        child_selection();




        return view;
    }

    private void child_selection() {
        child_list.add("Child 1");
        child_list.add("Child 2");
        child_list.add("Add Child");

        ArrayAdapter<String> child_select_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, child_list);

        child_select_list.setAdapter( child_select_adapter);
        child_select_list.setSelection(0);
        child_select_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == child_list.size()-1){
                    addChild();
                }
            }

            private void addChild() {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}