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


    Button monday0;
    int colored = 0;

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


        monday0 = (Button) findViewById(R.id.monday0);
        monday0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (colored == 0){
                    monday0.setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    colored = 1;
                }
                else if (colored == 1){
                    monday0.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    colored = 0;
                }
            }
        });
    }
}