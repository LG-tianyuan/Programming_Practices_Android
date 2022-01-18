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

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameET;
    private EditText nameET;
    private EditText ageET;
    private EditText teleET;
    private EditText passwordET;
    private EditText con_pwdET;
    private TextView tip1;
    private TextView tip2;
    private TextView tip3;
    private TextView tip4;
    private TextView tip5;
    private TextView tip6;
    private String username;
    private String name;
    private String age;
    private String tele;
    private String password;
    private String con_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initview();
    }

    public void initview(){
        usernameET = findViewById(R.id.username);
        nameET = findViewById(R.id.name);
        ageET = findViewById(R.id.age);
        teleET = findViewById(R.id.telenumber);
        passwordET = findViewById(R.id.password);
        con_pwdET = findViewById(R.id.confirm_password);
        tip1 = findViewById(R.id.tip1);
        tip2 = findViewById(R.id.tip2);
        tip3 = findViewById(R.id.tip3);
        tip4 = findViewById(R.id.tip4);
        tip5 = findViewById(R.id.tip5);
        tip6 = findViewById(R.id.tip6);
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
        nameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = nameET.getText().toString();
                if(name.isEmpty()){
                    tip2.setText("姓名不能为空!但不能超过20个字符");
                }else if(name.length()>20){
                    tip2.setText("姓名长度不能超过20个字符");
                }else{
                    tip2.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ageET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                age = ageET.getText().toString();
                if(!age.isEmpty()&&!age.matches("0|[1-9][0-9]*")){
                    tip3.setText("年龄必须为非负整数");
                }else{
                    tip3.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        teleET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tele = teleET.getText().toString();
                if(!tele.isEmpty()&&!tele.matches("[1][3-9][0-9]{9}")){
                    tip4.setText("请输入有效的11位手机号");
                }else{
                    tip4.setText("");
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
                    tip5.setText("只允许包含_、英文字母、数字，6-12位");
                }else
                {
                    tip5.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        con_pwdET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tip6.setText("请再次输入密码!");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = passwordET.getText().toString();
                con_pwd = con_pwdET.getText().toString();
                if(!con_pwd.equals(password)){
                    tip6.setText("确认密码与原密码不相等，请重新输入!");
                }else
                {
                    tip6.setText("");
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
                Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Register(View view){
        username = usernameET.getText().toString();
        name = nameET.getText().toString();
        age = ageET.getText().toString();
        tele = teleET.getText().toString();
        password = passwordET.getText().toString();
        con_pwd = con_pwdET.getText().toString();
        if(!username.matches("^(?=.*[A-Z])[_A-Za-z0-9]{5,10}$")){
            Toast.makeText(RegisterActivity.this,"用户名不合法!",Toast.LENGTH_SHORT).show();
        }else if(name.isEmpty()||name.length()>20){
            Toast.makeText(RegisterActivity.this,"姓名不合法!",Toast.LENGTH_SHORT).show();
        }else if(!age.isEmpty()&&!age.matches("0|[1-9][0-9]*")) {
            Toast.makeText(RegisterActivity.this,"年龄必须为非负整数!",Toast.LENGTH_SHORT).show();
        }else if(!tele.isEmpty()&&!tele.matches("[1][3-9][0-9]{9}")) {
            Toast.makeText(RegisterActivity.this,"电话号码应为11位数字!",Toast.LENGTH_SHORT).show();
        }else if(!password.matches("[0-9a-zA-Z_]{6,12}")){
            Toast.makeText(RegisterActivity.this,"密码不合法!",Toast.LENGTH_SHORT).show();
        }else if(!con_pwd.equals(password)){
            Toast.makeText(RegisterActivity.this,"确认密码与原密码不相等，请重新输入!",Toast.LENGTH_SHORT).show();
        }else{
            toast("Register...");
            RegisterRequest(username,name,age,tele,password);
        }
    }

    public void Goback(View view){
        //Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        //startActivity(intent);
        this.finish();
    }

    public void RegisterRequest(String username,String name,String age,String tele,String password){
        String myurl = "http://106.66.32.109:8080/lab4_server/AddPerson";
        String tag = "Register";
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
                params.put("name",name);
                params.put("age",age);
                params.put("telenumber",tele);
                params.put("password",password);
                return params;
            }
        };
        request.setTag(tag);
        requestQueue.add(request);
    }
    public void Success(){
        Intent intent = new Intent(RegisterActivity.this,WelcomeActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
        MainActivity.instance.SetText();
        this.finish();
    }
}