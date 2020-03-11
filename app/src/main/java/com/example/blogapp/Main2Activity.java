package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
   private ImageView iv;
   private EditText username;
   private Button confirm_button;
   private Uri mainUri=null;
   private StorageReference sref;
   private FirebaseAuth fauth;
   private FirebaseFirestore fb;
   private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fb=FirebaseFirestore.getInstance();
        fauth=FirebaseAuth.getInstance();
        iv=findViewById(R.id.userimage);
        sref= FirebaseStorage.getInstance().getReference();
        username=findViewById(R.id.name);
        user_id=fauth.getCurrentUser().getUid();
        fb.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                String name=task.getResult().getString("Name");
                String image=task.getResult().getString("Image");
                username.setText(name);
                Glide.with(Main2Activity.this).load(image).into(iv);}


            }
        });




        confirm_button=findViewById(R.id.confirm);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_id=fauth.getCurrentUser().getUid();
                StorageReference path=sref.child("iamges").child(user_id+".jpg");
                path.putFile(mainUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            UploadTask.TaskSnapshot uri=task.getResult();
                            Map<String ,Object> map=new HashMap<>();
                            map.put("Name",username.getText().toString());
                             map.put("Image",uri.toString());
                            fb.collection("Users").document(user_id).set(map);

                        }
                        else{
                            Toast.makeText(Main2Activity.this,"sorry",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Main2Activity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1
                    );


                }
                else{
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(Main2Activity.this);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fb.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String name = task.getResult().getString("name");
                username.setText(name);
            }
        });
        //super.onStart();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                mainUri=result.getUri();
                iv.setImageURI(mainUri);

            }else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error=result.getError();

            }
        }
    }
}
