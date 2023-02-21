package com.sanjit.sisu2.ui;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.sanjit.sisu2.MainActivity;
import com.sanjit.sisu2.R;
import com.sanjit.sisu2.adapters.sec_doc;
import com.sanjit.sisu2.ui.appointments.appointment_model;
import com.sanjit.sisu2.ui.login_register_user.Login;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeroProfile extends AppCompatActivity implements View.OnClickListener{

    ArrayList<appointment_model> appointment_model_arr = new ArrayList<>();
    private final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView namep,emailp,phonep,addressp,dobp,genderp,myTextView;

    ImageView editClick, saveClick;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mero_profile);


        namep = findViewById(R.id.aayoname);
        emailp = findViewById(R.id.aayomail);
        phonep =findViewById(R.id.aayotelephone);
        addressp = findViewById(R.id.aayoaddress);
        dobp = findViewById(R.id.aayobirthday);
        genderp = findViewById(R.id.aayogender);
        saveClick = findViewById(R.id.done_click);
        editClick = findViewById(R.id.edit_click);

        CircleImageView profile = findViewById(R.id.aayophoto);

        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        String Email = sharedPreferences.getString("Email", "Not Specified");
        String FullName = sharedPreferences.getString("FullName", "Not Specified");
        String Phone = sharedPreferences.getString("Phone", "Not Specified");
        String Address = sharedPreferences.getString("Address", "Not Specified");
        String DOB = sharedPreferences.getString("DOB", "Not Specified");
        String Gender = sharedPreferences.getString("Gender", "Not Specified");

        namep.setOnClickListener(this);
        emailp.setOnClickListener(this);
        phonep.setOnClickListener(this);
        addressp.setOnClickListener(this);
        dobp.setOnClickListener(this);
        genderp.setOnClickListener(this);
        saveClick.setOnClickListener(this);
        editClick.setOnClickListener(this);

        namep.setText(FullName);
        emailp.setText(Email);
        phonep.setText(Phone);
        addressp.setText(Address);
        dobp.setText(DOB);
        genderp.setText(Gender);


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
                                Toast.makeText(MeroProfile.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MeroProfile.this, "Error!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        db.collection("Users").document(mAuth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Map<String, Object> data = documentSnapshot.getData();
                        String name = (String) data.get("Fullname");
                        String phone = (String) data.get("Telephone");
                        String profile_pic = (String) data.get("ProfilePic");
                        String email = (String) data.get("Email");
                        String address = (String) data.get("Address");
                        String dob = (String) data.get("Birthday");
                        String gender=(String) data.get("Gender");

                        namep.setText(name);
                        emailp.setText(email);
                        phonep.setText(phone);
                        addressp.setText(address);
                        dobp.setText(dob);
                        genderp.setText(gender);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MeroProfile.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_click:
                edit_profile(v);
                break;
            case R.id.done_click:
                save_profile();
                break;
            default:
                break;
        }
    }

    private void save_profile() {
        Toast.makeText(this, "Save Profile", Toast.LENGTH_SHORT).show();

        editClick.setVisibility(View.VISIBLE);
        saveClick.setVisibility(View.GONE);

        setTextViewEditable(namep,false);
        setTextViewEditable(emailp,false);
        setTextViewEditable(phonep,false);
        setTextViewEditable(addressp,false);
        setTextViewEditable(dobp,false);
        setTextViewEditable(genderp,false);

        //create a map to store the data
        Map<String, Object> data = new HashMap<>();
        data.put("Fullname", namep.getText().toString());
        data.put("Telephone", phonep.getText().toString());
        data.put("Address", addressp.getText().toString());
        data.put("Birthday", dobp.getText().toString());
        data.put("Gender", genderp.getText().toString());

        if (!Patterns.EMAIL_ADDRESS.matcher(emailp.getText().toString()).matches()) {
            // Invalid email address format
            Log.d(TAG, "Invalid email address format.");
            return;
        }

        //update the data in firestore

        db.collection("Users").document(mAuth.getCurrentUser().getUid()).update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MeroProfile.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MeroProfile.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
        //update the data in shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FullName", namep.getText().toString());
        editor.putString("Phone", phonep.getText().toString());
        editor.putString("Address", addressp.getText().toString());

        //update email in firebase authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.updateEmail(emailp.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> data = new HashMap<>();
                            data.put("Email", emailp.getText().toString());
                            db.collection("Users").document(mAuth.getCurrentUser().getUid()).update(data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(MeroProfile.this, "Email Updated!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MeroProfile.this, "Error!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                        Toast.makeText(MeroProfile.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        //if email update fails, then logout the user and redirect to login page
                        Toast.makeText(MeroProfile.this, "To update email require recent login", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MeroProfile.this, "So please log in and try again to update email", Toast.LENGTH_SHORT).show();

                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();

                    }
                });
    }

    private void edit_profile(View v) {

        Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show();

        editClick.setVisibility(View.GONE);
        saveClick.setVisibility(View.VISIBLE);

        setTextViewEditable(namep,true);
        setTextViewEditable(emailp,true);
        setTextViewEditable(phonep,true);
        setTextViewEditable(addressp,true);
        setTextViewEditable(dobp,true);
        setTextViewEditable(genderp,true);

        switch (v.getId()){
            case R.id.aayoname:
                namep.requestFocus();
                myTextView = namep;
                break;
            case R.id.aayomail:
                emailp.requestFocus();
                myTextView = emailp;
                break;
            case R.id.aayotelephone:
                phonep.requestFocus();
                myTextView = phonep;
                break;
            case R.id.aayoaddress:
                addressp.requestFocus();
                myTextView = addressp;
                break;
            case R.id.aayobirthday:
                dobp.requestFocus();
                myTextView = dobp;
                break;
            case R.id.aayogender:
                genderp.requestFocus();
                myTextView = genderp;
                break;
            default:
                break;
        }

    }
    private void setTextViewEditable(TextView textView, boolean editable) {

        textView.setFocusableInTouchMode(editable);
        textView.setFocusable(editable);
        textView.setCursorVisible(editable);

        if (editable) {
            textView.requestFocus();
        } else {
            textView.clearFocus();
        }
    }

}
