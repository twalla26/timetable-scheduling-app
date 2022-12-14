package com.example.scheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scheduler.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private boolean isIdChecked = false;
    private EditText userName, userID, password, passwordCh, email;
    private String URL;
    private String response = " ";
    private AlertDialog dialog;
    private Button registerbtn, validatebtn;
    JSONObject idJSON = new JSONObject();
    JSONObject inputJSON = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.user_name);
        userID = findViewById(R.id.user_id);
        password = findViewById(R.id.password);
        passwordCh = findViewById(R.id.password_ch);
        email = findViewById(R.id.email);

        // ID validate Check
        validatebtn = findViewById(R.id.validateButton);
        validatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userid = userID.getText().toString();
                URL = "http://59.18.221.32:5000/auth/signup/check_dup"; // change address for validata

                if (userid.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                try {
                    idJSON.put("user_id", userid);
                } catch (JSONException e){
                    e.printStackTrace();
                }
                sendRequestDup(idJSON, URL);
            }
        });

        // Register
        registerbtn = findViewById(R.id.btn_register);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userid = userID.getText().toString();
                final String username = userName.getText().toString();
                final String _password = password.getText().toString();
                final String _passwordCh = passwordCh.getText().toString();
                final String _email = email.getText().toString();

                URL = "http://59.18.221.32:5000/auth/signup";

                if (username.equals("") || _password.equals("") || _passwordCh.equals("") || _email.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (!_email.matches("^[A-z|0-9]([A-z|0-9]*)(@)([A-z]*)(\\.)([A-z]*)$")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("올바른 이메일을 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (!_password.equals(_passwordCh)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호가 일치하지 않습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (!isIdChecked){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디 중복체크를 확인해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                try {
                    inputJSON.put("user_id", userid);
                    inputJSON.put("password", _password);
                    inputJSON.put("username", username);
                    inputJSON.put("email", _email);
                } catch (JSONException e){
                    e.printStackTrace();
                }
                sendRequestReg(inputJSON, URL);
            }
        });
    }
    // send Request to Server and get response
    public void sendRequestDup(JSONObject inputJSON, String URL){
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
                    OkHttpClient client = new OkHttpClient();
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
                    response = responses.body().string();
                    System.out.println(response);
                    if (response.contains("unusable")){
                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                            }
                        });
                    } else if (response.contains("usable")){
                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용가능한 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                isIdChecked = true;
                                validatebtn.setBackgroundColor(Color.GRAY);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        sendData sendData = new sendData();
        sendData.execute();
    }
    public void sendRequestReg(JSONObject inputJSON, String URL){
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
                    OkHttpClient client = new OkHttpClient();
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
                    response = responses.body().string();
                    System.out.println(response);
                    if (response.contains("success")){
                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        sendData sendData = new sendData();
        sendData.execute();
    }
}
