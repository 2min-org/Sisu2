package com.sanjit.sisu2.ui.add_child;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sanjit.sisu2.R;
import com.sanjit.sisu2.models.custom_date;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.UUID;

public class add_child extends AppCompatActivity {

    EditText name_text;
    custom_date date_of_birth;
    private Button dateButton;
    String name;
    Button accept, cancel;
    ImageButton add_photo;
    Calendar calendar = Calendar.getInstance();
    ImageView photo;
    private DatePickerDialog datePickerDialog;
    private final int GALLERY_REQUEST_CODE = 105;
    private StorageReference storageReference;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    Uri imageUri;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        name_text = findViewById(R.id.child_name);
        accept = findViewById(R.id.accept);
        cancel = findViewById(R.id.cancel);
        add_photo = findViewById(R.id.add_photo);
        photo = findViewById(R.id.child_image);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_photo.setEnabled(false);
                Intent igallery = new Intent(Intent.ACTION_PICK);
                igallery.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(igallery, GALLERY_REQUEST_CODE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo.setImageDrawable(null);
                name_text.setText("");
                dateButton.setText(getTodaysDate());
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upload to firebase
                name = name_text.getText().toString();

                if(imageUri!=null && !name.isEmpty()){
                    uploadToFirebase();
                } else if (imageUri==null){
                    Toast.makeText(add_child.this, "Please add image", Toast.LENGTH_SHORT).show();
                    add_photo.requestFocus();

                } else if (name.isEmpty()){
                    Toast.makeText(add_child.this, "Please insert name", Toast.LENGTH_SHORT).show();
                    name_text.requestFocus();

                } else {
                    Toast.makeText(add_child.this, "Please insert name and image", Toast.LENGTH_SHORT).show();
                    name_text.requestFocus();
                }
            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        add_photo.setEnabled(true);
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                imageUri = data.getData();
                Picasso.get().load(imageUri)
                        .resize(300, 300)
                        .centerCrop()
                        .into(photo);
            }
        }
    }
    private void uploadToFirebase() {
        //upload to firebase
        storageReference = FirebaseStorage.getInstance().getReference();
        final String randomKey = UUID.randomUUID().toString();

        accept.setEnabled(false);
        accept.setText("Uploading...");
        cancel.setEnabled(false);
        add_photo.setEnabled(false);


        StorageReference riversRef = storageReference.child("images/" + randomKey);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url = uri.toString();
                                db.collection("Users")
                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .collection("Child")
                                        .document(name)
                                        .set(new Child(name, date_of_birth , url))
                                        .addOnSuccessListener(aVoid -> {
                                            photo.setImageDrawable(null);
                                            name_text.setText("");
                                            dateButton.setText(getTodaysDate());
                                            accept.setEnabled(true);
                                            accept.setText("Accept");
                                            cancel.setEnabled(true);
                                            add_photo.setEnabled(true);
                                            Log.d("TAG", "Background task started");
                                            add_vaccine_schedule add_vaccine_schedule = new add_vaccine_schedule(name);
                                            add_vaccine_schedule.execute();
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(add_child.this, "Error adding child", Toast.LENGTH_SHORT).show());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }


    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        date_of_birth = new custom_date(day,month, year);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                date_of_birth = new custom_date(day,month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " / " + day + " / " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }


}