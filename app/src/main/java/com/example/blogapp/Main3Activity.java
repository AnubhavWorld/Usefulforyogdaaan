package com.example.blogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Main3Activity extends AppCompatActivity {
    private EditText newdes;
    private ImageView newpost;
    private Button newButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        newdes=findViewById(R.id.description);
        newpost=findViewById(R.id.newimage);
        newButton=findViewById(R.id.Upload);
    }
}
