package com.example.blogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth fauth;
    private Button logout_button,account_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fauth=FirebaseAuth.getInstance();
        logout_button=findViewById(R.id.logout);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fauth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        account_button=findViewById(R.id.accountSettings);
        account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(fauth.getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
    }
}
