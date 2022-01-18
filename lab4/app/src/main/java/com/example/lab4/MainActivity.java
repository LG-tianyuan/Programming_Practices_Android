package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    private TextView tip1;
    private TextView tip2;
    private EditText usernameET;
    private EditText passwordET;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        instance = this;
    }
    public void initview(){
        usernameET = findViewById(R.id.username);
        passwordET = findViewById(R.id.password);
        tip1 = findViewById(R.id.tip1);
        tip2 = findViewById(R.id.tip2);
        usernameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username = usernameET.getText().toString();
                if(!username.matches("^(?=.*[A-Z])[_A-Za-z0-9]{5,10}$")){
                    tip1.setText("只允许包含_、英文字母、数字，至少一个大写字母，5-10位");
                }else{
                    tip1.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = passwordET.getText().toString();
                if(!password.matches("[0-9a-zA-Z_]{6,12}")){
                    tip2.setText("只允许包含_、英文字母、数字，6-12位");
                }else
                {
                    tip2.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Login(View view){
        username = usernameET.getText().toString();
        password = passwordET.getText().toString();
        if(!username.matches("^(?=.*[A-Z])[_A-Za-z0-9]{5,10}$")){
            Toast.makeText(MainActivity.this,"用户名不合法!",Toast.LENGTH_SHORT).show();
        }else if(!password.matches("[0-9a-zA-Z_]{6,12}")){
            Toast.makeText(MainActivity.this,"密码不合法!",Toast.LENGTH_SHORT).show();
        }else{
            toast("Login...");
            LoginRequest(username,password);
        }
    }

    public void Register(View view){
            Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
    }

    public void ResetPassword(View view){
        username = usernameET.getText().toString();
        if(username.isEmpty()){
            Toast.makeText(MainActivity.this,"请先输入用户名",Toast.LENGTH_SHORT).show();
        }else if(!username.matches("^(?=.*[A-Z])[_A-Za-z0-9]{5,10}$")){
            Toast.makeText(MainActivity.this,"用户名不合法!",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(MainActivity.this,VerifyActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }
    }

    public void LoginRequest(String username, String password){
        String myurl = "http://106.66.32.109:8080/lab4_server/Login";
        String tag = "Login";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.cancelAll(tag);
        final StringRequest request = new StringRequest(Request.Method.POST, myurl,
                response -> {
                    try{
                        JSONObject jsonObject = (JSONObject)  new JSONObject(response).get("params");
                        String result = jsonObject.getString("result");
                        if(result.equals("Success!")){
                            Success();
                        }else{
                            toast(result);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("TAG",e.getMessage(),e);
                    }
                }, error -> {
                    toast("请求失败！");
                    Log.e("TAG",error.getMessage(),error);
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    public  void Success(){
        Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public  void SetText()
    {
        usernameET.setText("");
        passwordET.setText("");
        tip1.setText("");
        tip2.setText("");
    }
}