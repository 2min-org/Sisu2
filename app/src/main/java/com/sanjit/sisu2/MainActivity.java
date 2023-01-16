package com.sanjit.sisu2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.sanjit.sisu2.adapters.sec_doc;
import com.sanjit.sisu2.databinding.ActivityMainBinding;
import com.sanjit.sisu2.ui.MeroProfile;
import com.sanjit.sisu2.ui.Setting;
import com.sanjit.sisu2.ui.login_register_user.Doctor_info;
import com.sanjit.sisu2.ui.login_register_user.Login;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements sec_doc.sec_doc_listener {

    //declarations
    private AppBarConfiguration mAppBarConfiguration;
    SharedPreferences sharedPreferences;
    boolean nightMode;
    public String url;
    SharedPreferences.Editor editor;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private List<String> appointment_id ;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    //declarations

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //binding is replacement of view for easier access to layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        //setting up values from shared preferences

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        String Email = sharedPreferences.getString("Email", "Not Specified");
        String FullName = sharedPreferences.getString("FullName", "Not Specified");
        String User_id = sharedPreferences.getString("User_id", "Not Specified");
        String ProfilePic = sharedPreferences.getString("ProfilePic", "Not Specified");
        String User_mode = sharedPreferences.getString("User_mode", "Not Specified");
        String Specialization = sharedPreferences.getString("Specialization", "Not Specified");

        //end of setting up values from shared preferences



        //checking if user is doctor or not

        if(User_mode.equals("Doctor")) {

            //if user is doctor check if he has filled his Specialization or not
            try {
                String spec_doc = Specialization;
                String dName = FullName;
                String dEmail = Email;
                String dProfilePic = ProfilePic;

                if (spec_doc.equals("Not Specified")) {

                    // making a new collection with information of doctor in firebase
                    Doctor_info doctor_info = new Doctor_info( dName, dEmail, "Not Specified", appointment_id, User_id,dProfilePic);
                    db.collection("Doctors").document(User_id).set(doctor_info)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this, "Doctor info added", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Doctor info not added", Toast.LENGTH_SHORT).show();
                                }
                            });

                                   //calling a dialog box to get specialization of doctor
                    sec_doc sec_doc = new sec_doc();
                    sec_doc.show(getSupportFragmentManager(), "sec_doc");

                }
            }
            catch (Exception e){
                Toast.makeText(MainActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_disease_information,
                R.id.nav_vaccine_information,
                R.id.nav_image_upload,
                R.id.nav_childcare_centres,
                R.id.nav_appointments,
                R.id.nav_book_doctor,
                R.id.nav_about_us)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //we hide appointments for user who are not doctors

        if (!User_mode.equals("Doctor")) {
            Menu menu = navigationView.getMenu();
            MenuItem nav_appointments = menu.findItem(R.id.nav_appointments);
            nav_appointments.setVisible(false);
        }

        //we hide book appointments for user who are not patients

        if (!User_mode.equals("Patient")) {
            Menu menu = navigationView.getMenu();
            MenuItem nav_appointments = menu.findItem(R.id.nav_book_doctor);
            nav_appointments.setVisible(false);
        }

    }

    private void openDialog() {
        sec_doc sec_doc = new sec_doc();
        sec_doc.show(getSupportFragmentManager(),"sec_doc");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.user);
        View view = MenuItemCompat.getActionView(item);

        Log.println(Log.ASSERT, "TAG", "onCreateOptionsMenu: "+ view);

        CircleImageView profile = view.findViewById(R.id.profile_image);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MeroProfile.class);
                Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        SharedPreferences imageURL = getSharedPreferences("ImageURL", MODE_PRIVATE);
        String image = imageURL.getString("imageURL", "null");
        if (!image.equals("null")){
            Glide.with(getApplicationContext()).load(image).into(profile);
        }
        else {

            //setting image of user in action bar from the url in firestore database

            db.collection("Users").document(mAuth.getCurrentUser().getUid()).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            try {
                                String url = documentSnapshot.getString("ProfilePic");
                                Glide.with(getApplicationContext()).load(url).into(profile);
                                SharedPreferences.Editor editor = imageURL.edit();
                                editor.putString("imageURL", url);
                                editor.apply();
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
                return true;

            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                return true;

            case R.id.action_change_theme:
                Toast.makeText(this, "Change Theme", Toast.LENGTH_SHORT).show();
                sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE);
                nightMode = sharedPreferences.getBoolean("night", false); // Light mode is the default mode

                if (nightMode) {
                    item.setChecked(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                        if (nightMode) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            editor = sharedPreferences.edit();
                            editor.putBoolean("night", false);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            editor = sharedPreferences.edit();
                            editor.putBoolean("night", true);
                        }
                        editor.apply();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void applyTexts(String spec_doc) {
        Toast.makeText(this, "Specialist Doctor: "+spec_doc, Toast.LENGTH_SHORT).show();
        db.collection("Users").document(mAuth.getCurrentUser().getUid()).update("Specialization",spec_doc);
        db.collection("Doctors").document(mAuth.getCurrentUser().getUid()).update("Specialization",spec_doc);
        db.collection("Doctors").document(mAuth.getCurrentUser().getUid()).update("appointment_id",null);

        SharedPreferences User = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor editor = User.edit();
        editor.putString("Specialization", spec_doc);
        editor.apply();
    }
}