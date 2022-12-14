package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MonthTimetable extends AppCompatActivity {

    Button sunday0,sunday1,sunday2,sunday3,sunday4,sunday5;
    Button monday0,monday1,monday2,monday3,monday4,monday5;
    Button tuesday0,tuesday1,tuesday2,tuesday3,tuesday4,tuesday5;
    Button wednesday0,wednesday1,wednesday2,wednesday3,wednesday4,wednesday5;
    Button thursday0,thursday1,thursday2,thursday3,thursday4,thursday5;
    Button friday0,friday1,friday2,friday3,friday4,friday5;
    Button saturday0,saturday1,saturday2,saturday3,saturday4,saturday5;


    int colored = 0;
    AppCompatButton btn_ambiguous, btn_possible, btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_timetable);

        AppCompatButton btn_save = (AppCompatButton) findViewById(R.id.btn_save);
        btn_save.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MonthTimetable.this, ResultMonth.class);
                startActivity(intent); //액티비티 이동
            }
        }));



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

            Button month[]={sunday0, monday0, tuesday0, wednesday0, thursday0, friday0, saturday0,
                    sunday1, monday1, tuesday1, wednesday1, thursday1, friday1, saturday1,
                    sunday2, monday2, tuesday2, wednesday2, thursday2, friday2, saturday2,
                    sunday3, monday3, tuesday3, wednesday3, thursday3, friday3, saturday3,
                    sunday4, monday4, tuesday4, wednesday4, thursday4, friday4, saturday4};

            }
    }
    public void sendRequest(JSONObject inputJSON, String URL){
        class sendData extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {super.onPreExecute();}
            @Override
            protected void onPostExecute(String s) {super.onPostExecute(s);}
            @Override
            protected void onProgressUpdate(Void... values){
                super.onProgressUpdate(values);
            }
            @Override
            protected void onCancelled(String s){
                super.onCancelled(s);
            }
            @Override
            protected void onCancelled(){
                super.onCancelled();
            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MonthTimetable.this));
                    List<Cookie> cookieList = cookieJar.loadForRequest(HttpUrl.parse(URL));

                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .build();

                    RequestBody requestBody = RequestBody.create(
                            MediaType.parse("application/json; charset=uft-8"),
                            inputJSON.toString()
                    );
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url(URL)
                            .build();
                    Response responses = null;
                    responses = client.newCall(request).execute();
                    String res_cookie = responses.headers().get("Set-Cookie");
                    System.out.println(res_cookie);

                    String response = responses.body().string();
                    JSONObject jsonObject = new JSONObject(response);
                    int startDay = jsonObject.getInt("first_day");


                    Button[] month={sunday0, monday0, tuesday0, wednesday0, thursday0, friday0, saturday0,
                            sunday1, monday1, tuesday1, wednesday1, thursday1, friday1, saturday1,
                            sunday2, monday2, tuesday2, wednesday2, thursday2, friday2, saturday2,
                            sunday3, monday3, tuesday3, wednesday3, thursday3, friday3, saturday3,
                            sunday4, monday4, tuesday4, wednesday4, thursday4, friday4, saturday4,
                            sunday5, monday5, tuesday5, wednesday5, thursday5, friday5, saturday5};

                    int [] month1={R.id.sunday0, R.id.monday0, R.id.tuesday0, R.id.wednesday0, R.id.thursday0, R.id.friday0, R.id.saturday0,
                            R.id.sunday1, R.id.monday1, R.id.tuesday1, R.id.wednesday1, R.id.thursday1, R.id.friday1, R.id.saturday1,
                            R.id.sunday2, R.id.monday2, R.id.tuesday2, R.id.wednesday2, R.id.thursday2, R.id.friday2, R.id.saturday2,
                            R.id.sunday3, R.id.monday3, R.id.tuesday3, R.id.wednesday3, R.id.thursday3, R.id.friday3, R.id.saturday3,
                            R.id.sunday4, R.id.monday4, R.id.tuesday4, R.id.wednesday4, R.id.thursday4, R.id.friday4, R.id.saturday4,
                            R.id.sunday4, R.id.monday5, R.id.tuesday5, R.id.wednesday5, R.id.thursday5, R.id.friday5, R.id.saturday5};

                    for(int i=0; i<35;i++){
                        month[i]=(Button)findViewById(month1[i]);
                    }

                int a=(startDay/10)%100;
                int b=startDay%10;
                int c=(startDay/1000)%100;

                for(int i=0;i<31;i++){
                    month[b].setText(String.valueOf(a));
                    b++;
                    if((c==12||c==1||c==3||c==5||c==7||c==8||c==10)&&a==32){
                        a=0;
                    }
                    else if((c==2)&&a==29) {
                        a=0;
                    }
                    else if (a==31)
                        a=0;
                        a++;
                }



                    String id = res_cookie.split("=")[0];
                    String value = res_cookie.split("=")[1].toString().split(";")[0];
                    String session = id + "=" + value;

                    Cookie cookie = new Cookie.Builder()
                            .name(id)
                            .value(value)
                            .domain("39.124.122.32")
                            .path("/auth/checkSession/")
                            .build();
                    cookieList.add(cookie);

                    setString(id, session);
                    String check = getString(id);
                    System.out.println(check);


                } catch (JSONException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            private void setString(String key, String value){
                SharedPreferences prefs = MonthTimetable.this.getSharedPreferences("session", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(key, value);
                editor.commit();
            }
            public String getString(String key) {
                SharedPreferences prefs = MonthTimetable.this.getSharedPreferences("session", Context.MODE_PRIVATE);
                String value = prefs.getString(key, " ");
                return value;
            }
        }
        sendData sendData = new sendData();
        sendData.execute();
    }

}


