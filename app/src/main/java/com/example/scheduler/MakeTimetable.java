package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MakeTimetable extends AppCompatActivity {

    Button sunday0,sunday1,sunday2,sunday3,sunday4,sunday5,sunday6;
    Button monday0,monday1,monday2,monday3,monday4,monday5;
    Button tuesday0,tuesday1,tuesday2,tuesday3,tuesday4, tuesday5;
    Button wednesday0,wednesday1,wednesday2,wednesday3,wednesday4, wednesday5;
    Button thursday0,thursday1,thursday2,thursday3,thursday4, thursday5;
    Button friday0,friday1,friday2,friday3,friday4, friday5;
    Button saturday0,saturday1,saturday2,saturday3,saturday4, saturday5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_timetable);

        AppCompatButton btn_save = (AppCompatButton) findViewById(R.id.btn_save);
        btn_save.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MakeTimetable.this, ResultTime.class);
                startActivity(intent); //액티비티 이동
            }
        }));



    }
}