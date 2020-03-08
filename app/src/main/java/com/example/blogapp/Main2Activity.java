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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Main2Activity extends AppCompatActivity {
   private ImageView iv;
   private EditText username;
   private Button confirm_button;
   private Uri mainUri=null;
   private StorageReference sref;
   private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fauth=FirebaseAuth.getInstance();
        sref= FirebaseStorage.getInstance().getReference();
        username=findViewById(R.id.username);

        iv=findViewById(R.id.userimage);
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
        confirm_button=findViewById(R.id.confirm);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String user_id=fauth.getCurrentUser().getUid();
                    StorageReference path=sref.child("iamges").child(user_id+".jpg");
                    path.putFile(mainUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Main2Activity.this,"done",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Main2Activity.this,"sorry",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            }
        });
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
