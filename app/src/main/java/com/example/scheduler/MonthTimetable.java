package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MonthTimetable extends AppCompatActivity {
    TextView title, content;

    Button sunday0,sunday1,sunday2,sunday3,sunday4,sunday5;
    Button monday0,monday1,monday2,monday3,monday4,monday5;
    Button tuesday0,tuesday1,tuesday2,tuesday3,tuesday4, tuesday5;
    Button wednesday0,wednesday1,wednesday2,wednesday3,wednesday4, wednesday5;
    Button thursday0,thursday1,thursday2,thursday3,thursday4, thursday5;
    Button friday0,friday1,friday2,friday3,friday4, friday5;
    Button saturday0,saturday1,saturday2,saturday3,saturday4, saturday5;

    Button save;

    double[] result = new double[42];
    double[] sun = new double[7];
    double[] mon = new double[7];
    double[] tue = new double[7];
    double[] wed = new double[7];
    double[] thu = new double[7];
    double[] fri = new double[7];
    double[] sat = new double[7];

    double[] forSend = new double[30];

    int colored = 0;
    int firstDay;
    AppCompatButton btn_ambiguous, btn_possible, btn_delete;

    String response;
    String code, URL;

    JSONObject inputJSON = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_timetable);


        title = findViewById(R.id.title1);
        content = findViewById(R.id.detail_info);

        Intent intent = getIntent();
        code = intent.getExtras().getString("code");
        firstDay = intent.getExtras().getInt("firstDay");

        URL = "http://59.18.221.32:5000/plan/timetable/" + code;
        sendRequest(URL);


        btn_ambiguous=(AppCompatButton) findViewById(R.id.btn_ambiguous);
        btn_possible=(AppCompatButton) findViewById(R.id.btn_possible);
        btn_delete=(AppCompatButton) findViewById(R.id.btn_cancel);

        Drawable.ConstantState poss = getResources().getDrawable(R.drawable.cell_shape_possible).getConstantState();
        Drawable.ConstantState ambi = getResources().getDrawable(R.drawable.cell_shape_ambiguous).getConstantState();
        Drawable.ConstantState impo =  getResources().getDrawable(R.drawable.cell_shape1).getConstantState();

        save = findViewById(R.id.btn_save);

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


        Button btn_a[]={sunday0,sunday1,sunday2,sunday3,sunday4, sunday5};
        Button btn_b[]={monday0,monday1,monday2,monday3,monday4, monday5};
        Button btn_c[]={tuesday0,tuesday1,tuesday2,tuesday3,tuesday4, tuesday5};
        Button btn_d[]={wednesday0,wednesday1,wednesday2,wednesday3,wednesday4, wednesday5};
        Button btn_e[]={thursday0,thursday1,thursday2,thursday3,thursday4, thursday5};
        Button btn_f[]={friday0,friday1,friday2,friday3,friday4, friday5};
        Button btn_g[]={saturday0,saturday1,saturday2,saturday3,saturday4, saturday5};


        int a[]={R.id.sunday0,R.id.sunday1,R.id.sunday2,R.id.sunday3,R.id.sunday4, R.id.sunday5};
        int b[]={R.id.monday0,R.id.monday1,R.id.monday2,R.id.monday3,R.id.monday4, R.id.monday5};
        int c[]={R.id.tuesday0,R.id.tuesday1,R.id.tuesday2,R.id.tuesday3,R.id.tuesday4,R.id.tuesday5};
        int d[]={R.id.wednesday0,R.id.wednesday1,R.id.wednesday2,R.id.wednesday3,R.id.wednesday4, R.id.wednesday5};
        int e[]={R.id.thursday0,R.id.thursday1,R.id.thursday2,R.id.thursday3,R.id.thursday4, R.id.thursday5};
        int f[]={R.id.friday0,R.id.friday1,R.id.friday2,R.id.friday3,R.id.friday4, R.id.friday5};
        int g[]={R.id.saturday0,R.id.saturday1,R.id.saturday2,R.id.saturday3,R.id.saturday4, R.id.saturday5};



        for(int i=0; i<6;i++){
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
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<6; i++){
                    Drawable.ConstantState state_a = btn_a[i].getBackground().getConstantState();
                    if (state_a.equals(poss)){
                        sun[i] = 1;
                    } else if (state_a.equals(ambi)){
                        sun[i] = 0.5;
                    } else if (state_a.equals(impo)){
                        sun[i] = 0;
                    }
                    Drawable.ConstantState state_b = btn_b[i].getBackground().getConstantState();
                    if (state_b.equals(poss)){
                        mon[i] = 1;
                    } else if (state_b.equals(ambi)){
                        mon[i] = 0.5;
                    } else if (state_b.equals(impo)){
                        mon[i] = 0;
                    }
                    Drawable.ConstantState state_c = btn_c[i].getBackground().getConstantState();
                    if (state_c.equals(poss)){
                        tue[i] = 1;
                    } else if (state_c.equals(ambi)){
                        tue[i] = 0.5;
                    } else if (state_c.equals(impo)){
                        tue[i] = 0;
                    }
                    Drawable.ConstantState state_d = btn_d[i].getBackground().getConstantState();
                    if (state_d.equals(poss)){
                        wed[i] = 1;
                    } else if (state_d.equals(ambi)){
                        wed[i] = 0.5;
                    } else if (state_d.equals(impo)){
                        wed[i] = 0;
                    }
                    Drawable.ConstantState state_e = btn_e[i].getBackground().getConstantState();
                    if (state_e.equals(poss)){
                        thu[i] = 1;
                    } else if (state_e.equals(ambi)){
                        thu[i] = 0.5;
                    } else if (state_e.equals(impo)){
                        thu[i] = 0;
                    }
                    Drawable.ConstantState state_f = btn_f[i].getBackground().getConstantState();
                    if (state_f.equals(poss)){
                        fri[i] = 1;
                    } else if (state_f.equals(ambi)){
                        fri[i] = 0.5;
                    } else if (state_f.equals(impo)){
                        fri[i] = 0;
                    }
                    Drawable.ConstantState state_g = btn_g[i].getBackground().getConstantState();
                    if (state_g.equals(poss)){
                        sat[i] = 1;
                    } else if (state_g.equals(ambi)){
                        sat[i] = 0.5;
                    } else if (state_g.equals(impo)){
                        sat[i] = 0;
                    }
                }
                for (int j=0; j<6; j++){
                    int index = j*7;
                    result[index] = sun[j];
                    result[index+1] = mon[j];
                    result[index+2] = tue[j];
                    result[index+3] = wed[j];
                    result[index+4] = thu[j];
                    result[index+5] = fri[j];
                    result[index+6] = sat[j];
                }
                for (int i=0; i<30; i++){
                    forSend[i] = result[i+firstDay];
                }
                System.out.println(Arrays.toString(result));
                System.out.println(Arrays.toString(forSend));
                String forsendS = Arrays.toString(forSend);
                System.out.println(forsendS);
                try{
                    inputJSON.put("timetable_array", forsendS);
                } catch (JSONException e){
                    e.printStackTrace();
                }
                sendRequestResult(inputJSON, URL);
            }
        });
    }
    public int getInt(String key) {
        SharedPreferences prefs = MonthTimetable.this.getSharedPreferences("firstDay", Context.MODE_PRIVATE);
        int value = prefs.getInt(key, 0);
        return value;
    }
    public void sendRequest(String URL){
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
                    CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MonthTimetable.this));
                    String sessionid = getString("session");
                    List<Cookie> cookieList = cookieJar.loadForRequest(HttpUrl.parse(URL));
                    System.out.println(sessionid);
                    System.out.println(cookieList);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .build();

                    Request request = new Request.Builder()
                            .addHeader("Cookie", sessionid)
                            .url(URL)
                            .build();
                    Response responses = null;
                    responses = client.newCall(request).execute();
                    response = responses.body().string();
                    System.out.println(response);

                    JSONObject jsonObject = new JSONObject(response);
                    int startDay = jsonObject.getInt("first_day");
                    String titleS = jsonObject.getString("title");
                    String contentS = jsonObject.getString("detail");

                    MonthTimetable.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            title.setText(String.valueOf(titleS));
                            content.setText(contentS);
                        }
                    });

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
                            R.id.sunday5, R.id.monday5, R.id.tuesday5, R.id.wednesday5, R.id.thursday5, R.id.friday5, R.id.saturday5};

                    for(int i=0; i<42;i++){
                        month[i]=(Button)findViewById(month1[i]);
                    }

                    int a=(startDay/10)%100;
                    System.out.println(a);
                    int b=startDay%10;
                    System.out.println(b);
                    int c=(startDay/1000)%100;
                    System.out.println(c);

                    for(int i=0;i<30;i++){
                        int finalB = b;
                        int finalA = a;
                        MonthTimetable.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                month[finalB].setText(String.valueOf(finalA));
                            }
                        });
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
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
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
    public void sendRequestResult(JSONObject jsonObject, String URL){
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
                    CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MonthTimetable.this));
                    String sessionid = getString("session");
                    List<Cookie> cookieList = cookieJar.loadForRequest(HttpUrl.parse(URL));
                    System.out.println(sessionid);
                    System.out.println(cookieList);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .build();

                    RequestBody requestBody = RequestBody.create(
                            MediaType.parse("application/json; charset=uft-8"),
                            jsonObject.toString()
                    );
                    Request request = new Request.Builder()
                            .addHeader("Cookie", sessionid)
                            .post(requestBody)
                            .url(URL)
                            .build();
                    Response responses = null;
                    responses = client.newCall(request).execute();
                    response = responses.body().string();
                    System.out.println(response);

                    if (response.contains("success")){
                        try{
                            JSONObject resJSON = new JSONObject(response);
                            code = resJSON.getString("code");
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(MonthTimetable.this, ResultMonth.class);
                        intent.putExtra("code", code);
                        startActivity(intent);
                    } else {
                        MonthTimetable.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
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