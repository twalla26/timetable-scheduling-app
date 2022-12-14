package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;

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
                Intent intent = new Intent(MainActivity.this, MakeActivity.class);
                startActivity(intent); //액티비티 이동
            }
        }));

        AppCompatButton btn_join = (AppCompatButton) findViewById(R.id.btn_join);
        btn_join.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Popup.class);
                startActivity(intent); //액티비티 이동
            }
        }));

        drawerLayout = (DrawerLayout) findViewById (R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);

        ImageButton menu = (ImageButton) findViewById(R.id.menu);
        menu.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawerView);
            }
        }));

        Button btn_close = (Button) findViewById((R.id.btn_close));
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
            }
        });

    }
}