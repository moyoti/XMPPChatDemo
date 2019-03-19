package com.my.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.myapplication.util.ChatServerConnection;
import com.my.myapplication.util.MyChatManagerListener;

public class UserFriendList extends Activity {
    public static ArrayAdapter<String> mArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("usename");
        final ListView listView=(ListView)findViewById(R.id.friend_list);
        mArrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
//        mhandler.sendEmptyMessage(0);
        listView.setAdapter(mArrayAdapter);
        ChatServerConnection.findUsers();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String temp= (String) ((TextView)arg1).getText();
                Intent intent = new Intent();
                intent.putExtra("chatuser",temp+"@129.204.207.18");
                intent.setClass(UserFriendList.this, ChatRoom.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "Chat with " + temp,
                        Toast.LENGTH_SHORT).show();
                mArrayAdapter.notifyDataSetChanged();
            }

        });
    }
    public static Handler mhandler=new Handler()
    {
        public void handleMessage(android.os.Message message)
        {
            String temp=(String)message.obj;
            UserFriendList.mArrayAdapter.add(temp);
        }
    };

}
