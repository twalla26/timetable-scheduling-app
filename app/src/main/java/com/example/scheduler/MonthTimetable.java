package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MonthTimetable extends AppCompatActivity {

    Button sunday0,sunday1,sunday2,sunday3,sunday4;
    Button monday0,monday1,monday2,monday3,monday4;
    Button tuesday0,tuesday1,tuesday2,tuesday3,tuesday4;
    Button wednesday0,wednesday1,wednesday2,wednesday3,wednesday4;
    Button thursday0,thursday1,thursday2,thursday3,thursday4;
    Button friday0,friday1,friday2,friday3,friday4;
    Button saturday0,saturday1,saturday2,saturday3,saturday4;

    int colored = 0;
    AppCompatButton btn_ambiguous, btn_possible, btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_timetable);


        btn_ambiguous=(AppCompatButton) findViewById(R.id.btn_ambiguous);
        btn_possible=(AppCompatButton) findViewById(R.id.btn_possible);
        btn_delete=(AppCompatButton) findViewById(R.id.btn_cancel);

        btn_ambiguous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colored=2;
            }
        });

        btn_possible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colored=3;
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colored=1;
            }
        });


        Button btn_a[]={sunday0,sunday1,sunday2,sunday3,sunday4};
        Button btn_b[]={monday0,monday1,monday2,monday3,monday4};
        Button btn_c[]={tuesday0,tuesday1,tuesday2,tuesday3,tuesday4};
        Button btn_d[]={wednesday0,wednesday1,wednesday2,wednesday3,wednesday4};
        Button btn_e[]={thursday0,thursday1,thursday2,thursday3,thursday4};
        Button btn_f[]={friday0,friday1,friday2,friday3,friday4};
        Button btn_g[]={saturday0,saturday1,saturday2,saturday3,saturday4};


        int a[]={R.id.sunday0,R.id.sunday1,R.id.sunday2,R.id.sunday3,R.id.sunday4};
        int b[]={R.id.monday0,R.id.monday1,R.id.monday2,R.id.monday3,R.id.monday4};
        int c[]={R.id.tuesday0,R.id.tuesday1,R.id.tuesday2,R.id.tuesday3,R.id.tuesday4};
        int d[]={R.id.wednesday0,R.id.wednesday1,R.id.wednesday2,R.id.wednesday3,R.id.wednesday4};
        int e[]={R.id.thursday0,R.id.thursday1,R.id.thursday2,R.id.thursday3,R.id.thursday4};
        int f[]={R.id.friday0,R.id.friday1,R.id.friday2,R.id.friday3,R.id.friday4};
        int g[]={R.id.saturday0,R.id.saturday1,R.id.saturday2,R.id.saturday3,R.id.saturday4};



        for(int i=0; i<5;i++){
            btn_a[i] = (Button) findViewById(a[i]);
            btn_b[i] = (Button) findViewById(b[i]);
            btn_c[i] = (Button) findViewById(c[i]);
            btn_d[i] = (Button) findViewById(d[i]);
            btn_e[i] = (Button) findViewById(e[i]);
            btn_f[i] = (Button) findViewById(f[i]);
            btn_g[i] = (Button) findViewById(g[i]);

            int finalI = i;
            btn_a[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(colored==2)
                    btn_a[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                else if(colored==3)
                    btn_a[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                else if(colored==1)
                    btn_a[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
        });
            btn_b[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_b[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_b[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_b[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });
            btn_c[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_c[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_c[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_c[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });

            btn_d[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_d[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_d[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_d[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });

            btn_e[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_e[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_e[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_e[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });

            btn_f[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_f[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_f[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_f[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });

            btn_g[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_g[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_g[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_g[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });
    }}


}