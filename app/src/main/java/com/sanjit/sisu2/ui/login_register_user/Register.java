package com.sanjit.sisu2.ui.login_register_user;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sanjit.sisu2.MainActivity;
import com.sanjit.sisu2.R;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Register extends AppCompatActivity implements View.OnClickListener {

//declarations
    private EditText FullName, Email, Password, Birthday, Telephone,Address;
    private Spinner Gender, account_type;
    private ProgressBar progressBar;
    private ImageView profilePic;
    public Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    public ProgressDialog progressDialog;
    private User user;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String url;
//declarations

//onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //declarations
            profilePic= (ImageView) findViewById(R.id.photo);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            storageReference= storage.getReference();
            Button uploadPic = (Button) findViewById(R.id.upload);
            mAuth = FirebaseAuth.getInstance();
            TextView banner = (TextView) findViewById(R.id.banner);
            TextView registerUser = (TextView) findViewById(R.id.registerUser);
            FullName = (EditText) findViewById(R.id.Fullname);
            Email = (EditText) findViewById(R.id.Email);
            Password = (EditText) findViewById(R.id.Password);
            Birthday = (EditText) findViewById(R.id.Birthday);
            Telephone = (EditText) findViewById(R.id.Telephone);
            Address=(EditText)findViewById(R.id.address);
            Gender=(Spinner) findViewById(R.id.Gender);
            account_type = (Spinner) findViewById(R.id.spinner);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //declarations

        //onClickListeners
        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        banner.setOnClickListener(this);
        registerUser.setOnClickListener(this);
        //onClickListeners

        //select gender
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(adapter);

        //select user mode
        adapter = ArrayAdapter.createFromResource(this,R.array.position,android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account_type.setAdapter(adapter);
        account_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String user_mode = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //progress bar

        progressBar.setVisibility(View.GONE);

    }

    //onClick actions
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;

        }
    }

    private void registerUser() {

        //get values

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String fullname = FullName.getText().toString().trim();
        String telephone = Telephone.getText().toString().trim();
        String birthday = Birthday.getText().toString().trim();
        String gender=Gender.getSelectedItem().toString();
        String address=Address.getText().toString().trim();
        String user_mode = account_type.getSelectedItem().toString().trim();
        String doc_spec = null;

        //cases
        if (email.isEmpty()) {
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }
        if (password.length() < 8) {
            Password.setError("Minimum length of password should be 8");
            Password.requestFocus();
            return;
        }
        if (fullname.isEmpty()) {
            FullName.setError("Fullname is required");
            FullName.requestFocus();
            return;
        }
        if (telephone.isEmpty()) {
            Telephone.setError("Telephone is required");
            Telephone.requestFocus();
            return;
        }
        if (birthday.isEmpty()) {
            Birthday.setError("Birthday is required");
            Birthday.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please provide valid email");
            Email.requestFocus();
            return;
        }

        //if everything is ok now move onto
        progressBar.setVisibility(View.INVISIBLE);
        //new ProgressDialog

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = new User(mAuth.getCurrentUser().getUid(),fullname, email, birthday, telephone,gender,address,user_mode,null,null);
                    uploadPicture();

                } else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                        Toast.makeText(Register.this, "You are already registered", Toast.LENGTH_SHORT).show();

                    Toast.makeText(Register.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

        });

    }


    private void choosePicture(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            profilePic.setImageURI(imageUri);
        }
    }

    private void uploadPicture(){

        final String randomKey= UUID.randomUUID().toString();
        StorageReference riversRef=storageReference.child("images/"+randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url = uri.toString();
                                user.set_ProfilePic(url);
                                register_user();

                                SharedPreferences sharedPreferences = getSharedPreferences("ImageURL", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("imageURL", url);
                                editor.apply();
                            }
                        });
                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this,"Failed",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot tasksnapshot) {
                        double progress=(100.0*tasksnapshot.getBytesTransferred()/tasksnapshot.getTotalByteCount());
                        progressBar.setProgress((int)progress);

                    }
                });
    }
    public void register_user(){
        db.collection("Users").document(mAuth.getCurrentUser().getUid()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(Register.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        progressDialog.dismiss();
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed to set data! Try again!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }
                });

    }

}