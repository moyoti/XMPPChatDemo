package com.my.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.my.myapplication.util.ChatServerConnection;

public class ChatLogin extends Activity implements View.OnClickListener {
    public static final int FAIL_CON = 0;
    public static final int SUCC_CON = 0;
    private String Tag = "MainAcy";
    private String account;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_login);
        findViewById(R.id.chatlogin).setOnClickListener(this);
        findViewById(R.id.signup).setOnClickListener(this);
    }

    private void accountLogin() {
        new Thread() {
            public void run() {
                account = ((EditText) findViewById(R.id.chatusername)).getText().toString();
                pwd = ((EditText) findViewById(R.id.chatpassword)).getText().toString();
                boolean is = ChatServerConnection.login(account, pwd);
                if(is){
                    insHandler.sendEmptyMessage(1);
                }else {
                    insHandler.sendEmptyMessage(0);
                }
            }
        }.start();
    }

    private android.os.Handler insHandler = new android.os.Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                // 登录成功
                case 1:
                    Toast.makeText(ChatLogin.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    Log.d(Tag, "login suc");
                    //跳转页面
                    Intent intent = new Intent();
                    intent.putExtra("usename", account);
                    //传值
                    intent.setClass(ChatLogin.this, UserFriendList.class);
                    startActivity(intent);
                    break;
                case 0:
                    Toast.makeText(ChatLogin.this, "FAIL", Toast.LENGTH_SHORT).show();
                    Log.d(Tag, "login fail");
                    accountLogin();
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chatlogin:
                accountLogin();
                break;
            case R.id.signup:
                Intent intent = new Intent();
                intent.setClass(ChatLogin.this, ChatRegister.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
