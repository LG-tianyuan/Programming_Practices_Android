package com.example.lab4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class VerifyActivity extends AppCompatActivity{
    private Button BT1;
    private Button BT2;
    private TextView usernameTV;
    private TextView tip1;
    private EditText telET;
    private EditText vcET;
    private String username;//用户名
    private String tel;//手机号
    private TimerTask tt;
    private Timer tm;
    private int TIME = 60;//倒计时60s这里应该多设置些因为mob后台需要60s,我们前端会有差异的建议设置90，100或者120
    public String country="86";//这是中国区号，如果需要其他国家列表，可以使用getSupportedCountries();获得国家区号

    private static final int CODE_REPEAT = 1; //重新发送


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        initview();
        MobSDK.init(this,"34f7f2e08fe70","9a67e1406a343454aa136d32b589e6cd");
        MobSDK.submitPolicyGrantResult(true,null);
        SMSSDK.registerEventHandler(eh);
    }

    public void initview(){
        username = getIntent().getStringExtra("username");
        usernameTV = findViewById(R.id.username);
        tip1 = findViewById(R.id.tip1);
        telET = findViewById(R.id.tel);
        vcET = findViewById(R.id.verifycode);
        BT1 = findViewById(R.id.button1);
        BT2 = findViewById(R.id.button2);
        usernameTV.setText(username);
        telET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tel = telET.getText().toString();
                if(!tel.isEmpty()&&!tel.matches("[1][3-9][0-9]{9}")){
                    tip1.setText("请输入有效的11位手机号");
                }else{
                    tip1.setText("");
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
                Toast.makeText(VerifyActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void VerifyRequest(String username,String tel){
        String myurl = "http://106.66.32.109:8080/lab4_server/Verify";
        String tag = "Verify";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.cancelAll(tag);
        final StringRequest request = new StringRequest(Request.Method.POST, myurl,
                response -> {
                    try{
                        JSONObject jsonObject = (JSONObject)  new JSONObject(response).get("params");
                        String result = jsonObject.getString("result");
                        if(result.equals("Success!")){
                            Intent intent = new Intent(VerifyActivity.this,ResetPasswordActivity.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                            this.finish();
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
                params.put("tel",tel);
                return params;
            }
        };
        request.setTag(tag);
        requestQueue.add(request);
    }

    private Handler hd = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == CODE_REPEAT) {
                BT1.setEnabled(true);
                BT2.setEnabled(true);
                tm.cancel();//取消任务
                tt.cancel();//取消任务
                TIME = 60;//时间重置
                BT1.setText("重新获取");
            }else {
                BT1.setText(TIME + "秒后重新获取");
            }
            return true;
        }
    });

    EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    toast("Verification succeed!");
                    VerifyRequest(username,tel);

                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){       //获取验证码成功
                    toast("Get verification code succeed!");
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//如果你调用了获取国家区号类表会在这里回调
                    //返回支持发送验证码的国家列表
                }
            }else{//错误等在这里（包括验证失败）
                //错误码请参照http://wiki.mob.com/android-api-错误码参考/这里我就不再继续写了
                ((Throwable)data).printStackTrace();
                String str = data.toString();
                //toast(str);
                toast("Verify Code Error!");
            }
        }
    };

    private void alterWarning() {
        //构造器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hint"); //设置标题
        builder.setMessage(tel + " will get a verify code"); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
                //通过sdk发送短信验证（请求获取短信验证码，在监听（eh）中返回）
                SMSSDK.getVerificationCode(country, tel);
                //做倒计时操作
                Toast.makeText(VerifyActivity.this, "Has sent", Toast.LENGTH_SHORT).show();
                BT1.setEnabled(false);
                BT2.setEnabled(true);
                tm = new Timer();
                tt = new TimerTask() {
                    @Override
                    public void run() {
                        hd.sendEmptyMessage(TIME--);
                    }
                };
                tm.schedule(tt,0,1000);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(VerifyActivity.this, "Has canceled" , Toast.LENGTH_SHORT).show();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }

    public  void  Goback(View view){
        this.finish();
    }

    public void getVC(View view){
        tel= telET.getText().toString();
        if (!TextUtils.isEmpty(tel)) {
            //定义需要匹配的正则表达式的规则
            String REGEX_MOBILE_SIMPLE =  "[1][3-9][0-9]{9}";
            //把正则表达式的规则编译成模板
            Pattern pattern = Pattern.compile(REGEX_MOBILE_SIMPLE);
            //把需要匹配的字符给模板匹配，获得匹配器
            Matcher matcher = pattern.matcher(tel);
            // 通过匹配器查找是否有该字符，不可重复调用重复调用matcher.find()
            if (matcher.find()) {//匹配手机号是否存在
                alterWarning();
            } else {
                toast("Phone number's format error ");
            }
        } else {
            toast("Please input phone number");
        }
    }

    public void Submit(View view) {
        //获得用户输入的验证码
        String code = vcET.getText().toString().replaceAll("/s","");
        if (!TextUtils.isEmpty(code)) {//判断验证码是否为空
            //验证
            SMSSDK.submitVerificationCode(country, tel, code);
        }else{//如果用户输入的内容为空，提醒用户
            toast("please input verify code!");
        }
    }

    //销毁短信注册
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销回调接口registerEventHandler必须和unregisterEventHandler配套使用，否则可能造成内存泄漏。
        SMSSDK.unregisterEventHandler(eh);
    }
}