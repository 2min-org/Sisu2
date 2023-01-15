package com.sanjit.sisu2.ui.Imageupload;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sanjit.sisu2.R;

import java.net.URL;
import java.util.UUID;
import java.util.zip.Inflater;


public class imageUpload extends Fragment {


    private final int GALLERY_REQUEST_CODE = 105;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView imageView;
    Uri imageUri;
    String url;
    Button ChooseImage;
    Button UploadImage;
    Button CancelImage;
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

        UploadImage = view.findViewById(R.id.uploadImage);
        UploadImage.setEnabled(false);

        CancelImage = view.findViewById(R.id.cancelImage);
        CancelImage.setEnabled(false);

        imageView = view.findViewById(R.id.selectedImageView);
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

    //resolve the result of the image picker
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                imageUri = data.getData();

                imageView.setImageURI(imageUri);
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
        StorageReference riversRef = storageReference.child("images/"+randomKey);


        riversRef.putFile(imageUri)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                riversRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url = uri.toString();
                        db.collection("Users").document(uid).update("uploadedImage", FieldValue.arrayUnion(url));
                        Toast.makeText(getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
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
}