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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {
    private TextView usernameTV;
    private EditText passwordET;
    private EditText con_pwdET;
    private TextView tip1;
    private TextView tip2;
    private String username;
    private String password;
    private String con_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initview();
    }

    public void initview(){
        usernameTV = findViewById(R.id.username);
        passwordET = findViewById(R.id.password);
        con_pwdET = findViewById(R.id.confirm_password);
        Intent intent=getIntent();
        username = intent.getStringExtra("username");
        usernameTV.setText(username);
        tip1 = findViewById(R.id.tip1);
        tip2 = findViewById(R.id.tip2);
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = passwordET.getText().toString();
                if(!password.matches("[0-9a-zA-Z_]{6,12}")){
                    tip1.setText("只允许包含_、英文字母、数字，6-12位");
                }else
                {
                    tip1.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        con_pwdET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tip2.setText("请再次输入密码!");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = passwordET.getText().toString();
                con_pwd = con_pwdET.getText().toString();
                if(!con_pwd.equals(password)){
                    tip2.setText("确认密码与原密码不相等，请重新输入");
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
                Toast.makeText(ResetPasswordActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ResetPassword(View view){
        password = passwordET.getText().toString();
        con_pwd = con_pwdET.getText().toString();
        if(!password.matches("[0-9a-zA-Z_]{6,12}")){
            Toast.makeText(ResetPasswordActivity.this,"密码长度不能超过8个字符",Toast.LENGTH_SHORT).show();
        }else if(!con_pwd.equals(password)){
            Toast.makeText(ResetPasswordActivity.this,"确认密码与原密码不相等，请重新输入",Toast.LENGTH_SHORT).show();
        }else{
            toast("Reset...");
            ResetRequest(username,password);
        }
    }

    public void Goback(View view){
        //Intent intent = new Intent(ResetPasswordActivity.this,MainActivity.class);
        //startActivity(intent);
        this.finish();
    }

    public void ResetRequest(String username,String password){
        String myurl = "http://106.66.32.109:8080/lab4_server/ResetPassword";
        String tag = "ResetPassword";
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
        //Intent intent = new Intent(ResetPasswordActivity.this,MainActivity.class);
        //startActivity(intent);
        this.finish();
    }
}