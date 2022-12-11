package com.example.scheduler;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import android.widget.CalendarView;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;


public class MakeActivity extends AppCompatActivity {

    ImageButton boxbutton, boxbutton2, boxbutton3, boxbutton4;
    ImageButton checkbutton, checkbutton2, checkbutton3, checkbutton4;
    EditText duration,time;

    CalendarView cal;
    TextView tv_text;

    int week;
    int monthtoday;
    int selectedweek;
    int daytoday;
    char date;

    int i = 0;
    int j = 0;
    int k = 0;
    int l = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        Calendar calendar = Calendar.getInstance();

        week=calendar.get(Calendar.DAY_OF_WEEK);//요일
        daytoday=calendar.get(Calendar.DAY_OF_MONTH);//날짜
        monthtoday=calendar.get(Calendar.MONTH);//월
        cal = findViewById(R.id.calendarView);

        tv_text = findViewById(R.id.tv_text);


        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                int a=day-daytoday;
                if(monthtoday!=month){
                while(monthtoday!=month){
                    if (monthtoday == 0 || monthtoday == 2 || monthtoday == 4 || monthtoday == 6 || monthtoday == 7 || monthtoday == 9 || monthtoday == 11) {
                        int b=31;
                        a+=b;
                    }
                    else if(monthtoday==1){
                        int c=28;
                        a+=c;
                    }
                    else{
                        int d=30;
                        a+=d;
                    }
                    if(monthtoday==11){
                        monthtoday=0;
                    }
                    else{
                        monthtoday++;
                    }
                    }}

                    while (a >= 7) {
                        a = a - 7;
                    }
                selectedweek=(week+a)%7;
                if(selectedweek==0){
                    selectedweek=7;
                }
                switch(selectedweek){
                    case 1 :
                        date='일';
                        break;
                    case 2:
                        date='월';
                        break;
                    case 3 :
                        date='화';
                        break;
                    case 4:
                        date='수';
                        break;
                    case 5 :
                        date='목';
                        break;
                    case 6:
                        date='금';
                        break;
                    case 7:
                        date='토';
                        break;
                }

                tv_text.setText(year + "년 " + (month + 1) + "월 " + day + "일 " + date);
                week=calendar.get(Calendar.DAY_OF_WEEK);//요일
                daytoday=calendar.get(Calendar.DAY_OF_MONTH);//날짜
                monthtoday=calendar.get(Calendar.MONTH);
            }

        });

        AppCompatButton register = (AppCompatButton) findViewById(R.id.btn_register);
        register.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MakeActivity.this, MakeTimetable.class);
                startActivity(intent); //액티비티 이동
            }
        }));

        boxbutton = (ImageButton) findViewById(R.id.checkbox1);
        checkbutton = (ImageButton) findViewById(R.id.check1);
        time=(EditText) findViewById(R.id.get_time);
        duration=(EditText) findViewById(R.id.get_duration);
        checkbutton.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        duration.setVisibility(View.INVISIBLE);


        boxbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 0) {
                    boxbutton.setVisibility(View.VISIBLE);
                    checkbutton.setVisibility(View.VISIBLE);
                    boxbutton2.setVisibility(View.VISIBLE);
                    checkbutton2.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.VISIBLE);
                    duration.setVisibility(View.INVISIBLE);
                    i = 1;
                } else {
                    boxbutton.setVisibility(View.VISIBLE);
                    checkbutton.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    i = 0;
                }
            }
        });

        boxbutton2 = (ImageButton) findViewById(R.id.checkbox2);
        checkbutton2= (ImageButton) findViewById(R.id.check2);
        checkbutton2.setVisibility(View.INVISIBLE);

        boxbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (j == 0) {
                    boxbutton2.setVisibility(View.VISIBLE);
                    checkbutton2.setVisibility(View.VISIBLE);
                    checkbutton.setVisibility(View.INVISIBLE);
                    checkbutton2.setVisibility(View.VISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    duration.setVisibility(View.VISIBLE);
                    j = 1;
                } else {
                    boxbutton2.setVisibility(View.VISIBLE);
                    checkbutton2.setVisibility(View.INVISIBLE);
                    duration.setVisibility(View.INVISIBLE);
                    j = 0;
                }
            }
        });
/*
        boxbutton3 = (ImageButton) findViewById(R.id.checkbox3);
        checkbutton3= (ImageButton) findViewById(R.id.check3);
        checkbutton3.setVisibility(View.INVISIBLE);

        boxbutton3.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                if (k == 0) {
                    boxbutton3.setVisibility(View.VISIBLE);
                    checkbutton3.setVisibility(View.VISIBLE);
                    k = 1;
                } else {
                    boxbutton3.setVisibility(View.VISIBLE);
                    checkbutton3.setVisibility(View.INVISIBLE);
                    k = 0;
                }
            }
        });

        boxbutton4 = (ImageButton) findViewById(R.id.checkbox4);
        checkbutton4= (ImageButton) findViewById(R.id.check4);
        checkbutton4.setVisibility(View.INVISIBLE);

        boxbutton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (l == 0) {
                    boxbutton4.setVisibility(View.VISIBLE);
                    checkbutton4.setVisibility(View.VISIBLE);
                    l = 1;
                } else {
                    boxbutton4.setVisibility(View.VISIBLE);
                    checkbutton4.setVisibility(View.INVISIBLE);
                    l = 0;
                }
            }
        });*/
    }
}

