package com.sanjit.sisu2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanjit.sisu2.adapters.sec_doc;
import com.sanjit.sisu2.databinding.ActivityMainBinding;
import com.sanjit.sisu2.ui.MeroProfile;
import com.sanjit.sisu2.ui.Setting;
import com.sanjit.sisu2.ui.login_register_user.Doctor_info;
import com.sanjit.sisu2.ui.login_register_user.Login;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements sec_doc.sec_doc_listener , NavigationView.OnNavigationItemSelectedListener {

    //declarations
    private AppBarConfiguration mAppBarConfiguration;
    SharedPreferences sharedPreferences;
    NavController navController;
    boolean nightMode;
    NavOptions navOptions = new NavOptions.Builder()
            .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
            .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
            .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
            .setPopUpTo(R.id.nav_home,true)
            .build();
    public DrawerLayout drawer;
    SharedPreferences.Editor editor;
    private final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    public NavigationView navigationView;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1;
    private List<String> appointment_id ;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    //declarations

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //binding is replacement of view for easier access to layout
        com.sanjit.sisu2.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        drawer = binding.drawerLayout;
        navigationView = binding.navView;

        //setting up values from shared preferences

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        String Email = sharedPreferences.getString("Email", "Not Specified");
        String FullName = sharedPreferences.getString("FullName", "Not Specified");
        String User_id = sharedPreferences.getString("User_id", "Not Specified");
        String ProfilePic = sharedPreferences.getString("ProfilePic", "Not Specified");
        String User_mode = sharedPreferences.getString("User_mode", "Not Specified");
        String Specialization = sharedPreferences.getString("Specialization", "Not Specified");

        //end of setting up values from shared preferences

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.RECEIVE_BOOT_COMPLETED }, NOTIFICATION_PERMISSION_REQUEST_CODE);


            return;
        }

        //checking if user is doctor or not

        if(User_mode.equals("Doctor")) {

            //if user is doctor check if he has filled his Specialization or not
            try {

                if (Specialization.equals("Not Specified")) {

                    // making a new collection with information of doctor in firebase
                    Doctor_info doctor_info = new Doctor_info(FullName, Email, "Not Specified", appointment_id, User_id, ProfilePic);
                    db.collection("Doctors").document(User_id).set(doctor_info)
                            .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Doctor info added", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Doctor info not added", Toast.LENGTH_SHORT).show());

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
                R.id.nav_appointments,
                R.id.nav_book_doctor,
                R.id.nav_about_us)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            }
        }
    }

            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem item){

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                        //we use navOptions to make sure that when we click on home button it does not create a new instance of home fragment
                        Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
                                .navigate(R.id.nav_home, null, navOptions);
                        break;
                    case R.id.nav_appointments:
                        navController.popBackStack(R.id.nav_home, false);
                        Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
                                .navigate(R.id.nav_appointments, null, navOptions);
                        break;
                    case R.id.nav_book_doctor:
                        navController.popBackStack(R.id.nav_home, false);
                        Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
                                .navigate(R.id.nav_book_doctor);
                        break;
                    case R.id.nav_image_upload:
                        navController.popBackStack(R.id.nav_home, false);
                        Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
                                .navigate(R.id.nav_image_upload);
                        break;
                    case R.id.nav_vaccine_schedule:
                        navController.popBackStack(R.id.nav_home, false);
                        Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
                                .navigate(R.id.nav_vaccine_schedule);
                        break;
                    case R.id.nav_vaccine_information:
                        navController.popBackStack(R.id.nav_home, false);
                        Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
                                .navigate(R.id.nav_vaccine_information);
                        break;
                    case R.id.nav_disease_information:
                        navController.popBackStack(R.id.nav_home, false);
                        Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
                                .navigate(R.id.nav_disease_information);
                        break;
                    case R.id.nav_about_us:
                        navController.popBackStack(R.id.nav_home, false);
                        Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
                                .navigate(R.id.nav_about_us);
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

    @Override
            public boolean onCreateOptionsMenu (Menu menu){
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.main, menu);

                MenuItem item = menu.findItem(R.id.user);
                View view = MenuItemCompat.getActionView(item);

                Log.println(Log.ASSERT, "TAG", "onCreateOptionsMenu: " + view);

                CircleImageView profile = view.findViewById(R.id.profile_image);
                profile.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, MeroProfile.class);
                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                });

                SharedPreferences User = getSharedPreferences("User", MODE_PRIVATE);
                String ProfilePic = User.getString("ProfilePic", "Not Specified");

                if (!ProfilePic.equals("Not Specified")) {
                    Glide.with(getApplicationContext()).load(ProfilePic).into(profile);
                } else {

                    //setting image of user in action bar from the url in fire store database

                    db.collection("Users").document(mAuth.getCurrentUser().getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    try {
                                        String url = documentSnapshot.getString("ProfilePic");
                                        Glide.with(getApplicationContext()).load(url).into(profile);
                                        SharedPreferences User = getSharedPreferences("User", MODE_PRIVATE);
                                        SharedPreferences.Editor editor1 = User.edit();
                                        editor1.putString("ProfilePic", url);
                                        editor1.apply();
                                    } catch (Exception e) {
                                        Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show());
                }

                return super.onCreateOptionsMenu(menu);
            }

            @Override
            public boolean onSupportNavigateUp () {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
                return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                        || super.onSupportNavigateUp();
            }

            public boolean onOptionsItemSelected (MenuItem item){
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
            public void applyTexts (String spec_doc){
                Toast.makeText(this, "Specialist Doctor: " + spec_doc, Toast.LENGTH_SHORT).show();
                db.collection("Users").document(mAuth.getCurrentUser().getUid()).update("Specialization", spec_doc);
                db.collection("Doctors").document(mAuth.getCurrentUser().getUid()).update("Specialization", spec_doc);
                db.collection("Doctors").document(mAuth.getCurrentUser().getUid()).update("appointment_id", null);

                SharedPreferences User = getSharedPreferences("User", MODE_PRIVATE);
                SharedPreferences.Editor editor = User.edit();
                editor.putString("Specialization", spec_doc);
                editor.apply();
            }
        }