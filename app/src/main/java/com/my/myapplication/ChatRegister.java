package com.my.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.my.myapplication.util.ChatServerConnection;

public class ChatRegister extends Activity {
    EditText username;
    EditText password;
    Button register_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        username=(EditText)findViewById(R.id.uname);
        password=(EditText)findViewById(R.id.upass);
        register_btn=(Button)findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    public void run() {
                        if(username.getText().toString()!=null&&password.getText().toString()!=null){
                            ChatServerConnection.registerUser(username.getText().toString(),password.getText().toString());
                        }
                    }
                }.start();
            }
        });
    }
}
