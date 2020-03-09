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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fb=FirebaseFirestore.getInstance();
        fauth=FirebaseAuth.getInstance();
        sref= FirebaseStorage.getInstance().getReference();
        username=findViewById(R.id.name);


        confirm_button=findViewById(R.id.confirm);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String user_id=fauth.getCurrentUser().getUid();
                Map<String ,Object> map=new HashMap<>();
                map.put("Name",username.getText().toString());
                fb.collection("Users").document(user_id).set(map);
            }
        });
    }
}
