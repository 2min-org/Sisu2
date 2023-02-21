package com.sanjit.sisu2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.sanjit.sisu2.MainActivity;
import com.sanjit.sisu2.R;
import com.sanjit.sisu2.adapters.sec_doc;
import com.sanjit.sisu2.ui.appointments.appointment_model;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeroProfile extends AppCompatActivity {

    ArrayList<appointment_model> appointment_model_arr = new ArrayList<>();
    private final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mero_profile);

        TextView namep = findViewById(R.id.aayoname);
        TextView emailp = findViewById(R.id.aayomail);
        TextView phonep =findViewById(R.id.aayotelephone);
        TextView addressp = findViewById(R.id.aayoaddress);
        TextView dobp = findViewById(R.id.aayobirthday);
        TextView genderp = findViewById(R.id.aayogender);

        CircleImageView profile = findViewById(R.id.aayophoto);

        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        String Email = sharedPreferences.getString("Email", "Not Specified");
        String FullName = sharedPreferences.getString("FullName", "Not Specified");
        String Phone = sharedPreferences.getString("Phone", "Not Specified");
        String Address = sharedPreferences.getString("Address", "Not Specified");
        String DOB = sharedPreferences.getString("DOB", "Not Specified");
        String Gender = sharedPreferences.getString("Gender", "Not Specified");


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

}
