package com.sanjit.sisu2.ui.Imageupload;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sanjit.sisu2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;


public class imageUpload extends Fragment implements uploadimage_interface {


    private final int GALLERY_REQUEST_CODE = 105;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView imageView;
    Uri imageUri;
    String url;
    ArrayList<basic_model> basic_model_arr = new ArrayList<>();
    RecyclerView recyclerView;
    Button ChooseImage;
    Button UploadImage;
    Button CancelImage;
    EditText imageName;
    EditText imageDescription;
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

        ChooseImage = view.findViewById(R.id.chooseImage);

        imageName = view.findViewById(R.id.imageName);
        imageDescription = view.findViewById(R.id.imageDescription);

        UploadImage = view.findViewById(R.id.uploadImage);
        UploadImage.setEnabled(false);

        CancelImage = view.findViewById(R.id.cancelImage);
        CancelImage.setEnabled(false);

        imageView = view.findViewById(R.id.selectedImageView);

        recyclerView = view.findViewById(R.id.uploadedImageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setDataModel();



        ChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open image picker
                Intent igallery = new Intent(Intent.ACTION_PICK);
                igallery.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(igallery, GALLERY_REQUEST_CODE);

            }
        });
        UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upload image
                uploadImageToFirebase(imageUri);
                UploadImage.setEnabled(false);
                CancelImage.setEnabled(false);
                ChooseImage.setEnabled(false);

                UploadImage.setText("Uploading...");
            }
        });
        CancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancel image
                imageView.setImageDrawable(null);
                UploadImage.setEnabled(false);
                CancelImage.setEnabled(false);
                ChooseImage.setEnabled(true);
            }
        });


        return view;

    }

    private void setDataModel() {
        db.collection("Users")
          .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
          .collection("images").orderBy("timestamp", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Map<String, Object> data = documentSnapshot.getData();
                                String name = (String) data.get("name");
                                String description = (String) data.get("description");
                                String url = (String) data.get("url");
                                Timestamp timestamp = (Timestamp) data.get("timestamp");

                                Log.d("TAG", "onSuccess: "+timestamp);

                                basic_model model = new basic_model(name, description, url, timestamp);
                                basic_model_arr.add(model);
                            }
                            //set adapter
                            uploadimage_interface uploadimage_interface = imageUpload.this;
                            imageUpload_adapter adapter = new imageUpload_adapter(getContext(), basic_model_arr, uploadimage_interface);
                            recyclerView.setAdapter(adapter);

                        }
                    }
                });
    }

    //resolve the result of the image picker
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                imageUri = data.getData();

                Picasso.get().load(imageUri)
                        .resize(500, 500)
                        .centerCrop()
                        .into(imageView);
                imageView.setVisibility(View.VISIBLE);

                UploadImage.setEnabled(true);
                CancelImage.setEnabled(true);


            }
        }
    }
    public void uploadImageToFirebase(Uri imageUri) {
        //upload image to firebase storage

        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String uid = auth.getCurrentUser().getUid();
        final String randomKey= UUID.randomUUID().toString();

        String name;
        String description;
        StorageReference riversRef = storageReference.child("images/"+randomKey);

        if(imageName != null && imageName.getText().toString().trim().length() != 0){
             name = imageName.getText().toString();
        }
        else {
             name = "No Name";
        }
        if(imageDescription != null && imageDescription.getText().toString().trim().length() != 0){
             description = imageDescription.getText().toString();
        }
        else {
             description = "No Description";
        }

        riversRef.putFile(imageUri)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                riversRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        url = uri.toString();
                        Timestamp timestamp = Timestamp.now();
                        Log.d("TAG", "onSuccess: "+timestamp);
                        Log.d("TAG", "onSuccess: "+timestamp);
                        Log.d("TAG", "onSuccess: "+timestamp);

                        basic_model basic_model = new basic_model(name,description,url,Timestamp.now());

                        db.collection("Users").document(uid).update("uploaded_images", FieldValue.arrayUnion(randomKey));
                        db.collection("Users").document(uid).collection("images").document(randomKey).set(basic_model);

                        Toast.makeText(getContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();

                        UploadImage.setText("Upload");
                        ChooseImage.setEnabled(true);
                        UploadImage.setEnabled(true);
                        CancelImage.setEnabled(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle any errors
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClick(int position) {
        basic_model model = basic_model_arr.get(position);
        Toast.makeText(getContext(), model.getName(), Toast.LENGTH_SHORT).show();

    }
}