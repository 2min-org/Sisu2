package com.sanjit.sisu2.ui.VaccineSchedule;

import static android.content.Context.MODE_PRIVATE;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanjit.sisu2.R;
import com.sanjit.sisu2.ui.add_child.add_child;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class vaccine_schedule extends Fragment {

    //declaring variables
    private String active_child;
    private ArrayList<upcoming_vaccine_model> selected_date_vaccine = new ArrayList<>();
    private final FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private Spinner child_select_list;
    private final ArrayList<String> child_list = new ArrayList<>();
    private upcoming_vaccine_adapter upcoming_vaccine_adapter;



    //end of declaring variables


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vaccine_schedule, container, false);

        //declarations

        recyclerView = view.findViewById(R.id.vaccine_list);
        child_select_list = view.findViewById(R.id.child_select_list);
        SharedPreferences User = requireActivity().getSharedPreferences("User", MODE_PRIVATE);
        String user =User.getString("user_mode", "Patient");

        //end of declarations

        if(user.equals("Patient")) child_select_list.setVisibility(View.VISIBLE);
        else child_select_list.setVisibility(View.GONE);
        child_selection();

        //when a date is clicked on calendar

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setShowWeekNumber(false);
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            show_selected_date_vaccine(year, month, dayOfMonth);
        });

        return view;
    }


    private void child_selection() {

        db.collection("Users").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                        .get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if(documentSnapshot.exists()){
                                    Map<String, Object> data = documentSnapshot.getData();
                                    assert data != null;
                                    Log.d("data", data.toString());
                                    ArrayList<String> child_name_list = (ArrayList<String>) data.get("Children_name");
                                    assert child_name_list != null;
                                    active_child = child_name_list.get(0);
                                    Log.d("active_child", active_child);
                                    active_listener();
                                }
                            }
                        });


        child_select_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == child_list.size()-1){
                    addChild();
                    child_select_list.setSelection(0);

                }
                else{
                    setAdapter();
                    active_child = child_list.get(position);
                }
            }

            private void addChild() {
                Intent intent = new Intent(getActivity(), add_child.class);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void active_listener() {
        CollectionReference coll_ref = db.collection("Users").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .collection("Child");
        coll_ref.addSnapshotListener((value, error) -> {
            child_list.clear();

            assert value != null;
            for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                Map<String, Object> data = documentSnapshot.getData();
                assert data != null;
                String child_name = Objects.requireNonNull(data.get("name")).toString();
                child_list.add(child_name);
            }
            child_list.add("Add Child");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item,
                    child_list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            child_select_list.setAdapter(adapter);
            child_select_list.setSelection(0); //setting the default value of the spinner
            setAdapter();
        });
    }

    public void setAdapter(){

        ArrayList<upcoming_vaccine_model> upcoming_vaccine_model_async = new ArrayList<>();
        ArrayList<upcoming_vaccine_model> upcoming_vaccine_modelArrayList = new ArrayList<>();

        //dummy data
        upcoming_vaccine_modelArrayList.add(new upcoming_vaccine_model("DPT", "12", "12 12","1st dose" ));
        upcoming_vaccine_modelArrayList.add(new upcoming_vaccine_model("DPT", "12", "12 12","1st dose" ));
        upcoming_vaccine_modelArrayList.add(new upcoming_vaccine_model("DPT", "12", "12 12","1st dose" ));
        upcoming_vaccine_modelArrayList.add(new upcoming_vaccine_model("DPT", "12", "12 12","1st dose" ));
        upcoming_vaccine_modelArrayList.add(new upcoming_vaccine_model("DPT", "12", "12 12","1st dose" ));
        upcoming_vaccine_modelArrayList.add(new upcoming_vaccine_model("DPT", "12", "12 12","1st dose" ));

        //dummy data

        //setting up adapter
        upcoming_vaccine_adapter = new upcoming_vaccine_adapter(upcoming_vaccine_modelArrayList, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(upcoming_vaccine_adapter);

        //async task to get data from firebase regarding vaccine dates
        db.collection("Users").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .collection("Child").document(active_child).get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot.exists()){
                            Log.d("child_data", Objects.requireNonNull(documentSnapshot.getData()).toString());
                            Map<String, Object> child_data = documentSnapshot.getData();
                            Map<String, Object> vaccine_data = (Map<String, Object>) child_data.get("vaccine_schedule");
                            assert vaccine_data != null;
                            Log.d("vaccine_data", vaccine_data.toString());
                            for (Map.Entry<String, Object> entry : vaccine_data.entrySet()) {
                                String key = entry.getKey();
                                Map<String, Object> value = (Map<String, Object>) entry.getValue();

                                String day = Objects.requireNonNull(value.get("day")).toString();
                                String month_year = Objects.requireNonNull(value.get("month"))+" "+ Objects.requireNonNull(value.get("year"));
                                String dose = "1st dose";

                                upcoming_vaccine_model_async.add(new upcoming_vaccine_model(key, day, month_year, dose));

                            }
                            Log.d("onPostExecute", "onPostExecute:");
                            Log.d("onPostExecute", upcoming_vaccine_model_async.toString());
                            upcoming_vaccine_adapter = new upcoming_vaccine_adapter( upcoming_vaccine_model_async, getActivity());
                            recyclerView.setAdapter(upcoming_vaccine_adapter);
                        }
                    }
        });
        selected_date_vaccine = upcoming_vaccine_model_async;

    }

    private void show_selected_date_vaccine(int year, int month, int dayOfMonth) {

        String date = dayOfMonth+" "+month+" "+year;
        Log.d("date", date);
        ArrayList<upcoming_vaccine_model> vaccine_to_be_shown = new ArrayList<>();
        //loop through the selected_date_vaccine arraylist and check if the date matches

        for (upcoming_vaccine_model vaccine : selected_date_vaccine) {
            String vaccine_date = vaccine.getVaccine_day()+" "+vaccine.getVaccine_month_year();
            Log.d("vaccine_date", vaccine_date);

            if(vaccine_date.equals(date)){
                //save the vaccine detail in  separate arraylist
                Log.d("vaccine", vaccine.getVaccine_name());
                vaccine_to_be_shown.add(vaccine);

            }
        }
        //set the adapter with the new arraylist
        upcoming_vaccine_adapter = new upcoming_vaccine_adapter(vaccine_to_be_shown, getActivity());
        recyclerView.setAdapter(upcoming_vaccine_adapter);

    }

}

