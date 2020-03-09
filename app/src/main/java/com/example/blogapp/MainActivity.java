package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth fauth;
    private Button logout_button,account_button;
    private Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tb=findViewById(R.id.main_toolbar);
        getSupportActionBar().setTitle("Yogdaan Work");
        fauth=FirebaseAuth.getInstance();



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(fauth.getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_Logout:fauth.signOut();startActivity(new Intent(MainActivity.this,LoginActivity.class));finish();
            case R.id.toolbar_Account:startActivity(new Intent(MainActivity.this,Main2Activity.class));finish();
        }
        return false;
    }
}
