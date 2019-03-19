package com.my.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.my.myapplication.util.ChatServerConnection;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatServerConnection.closeConnection();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ChatLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
