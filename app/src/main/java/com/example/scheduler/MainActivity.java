package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button title = (Button) findViewById(R.id.title);
        title.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent); //액티비티 이동
            }
        }));
        ImageButton login = (ImageButton) findViewById(R.id.login);
        login.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent); //액티비티 이동
            }
        }));

        AppCompatButton btn_make = (AppCompatButton) findViewById(R.id.btn_make);
        btn_make.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MakeTimetable.class);
                startActivity(intent); //액티비티 이동
            }
        }));
    }
}