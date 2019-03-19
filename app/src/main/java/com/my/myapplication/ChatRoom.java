package com.my.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.my.myapplication.R;
import com.my.myapplication.util.ChatServerConnection;
import com.my.myapplication.util.MyChatManagerListener;

import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.EntityJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

public class ChatRoom extends Activity {
    Button send_btn;
    EditText send_msg;
    ListView messageView;
    public static ArrayAdapter<String> mConversationArrayAdapter;
    static Message msg;
    public static String userFrom;
//    private Handler handler = new Handler(){
//        public void handleMessage(android.os.Message m) {
//            msg=new Message();
//            msg=(Message) m.obj;
////            String[] message=new String[]{String.valueOf(msg.getFrom()), msg.getBody()};
//            if(msg.getBody()!=null){
//                System.out.println("==========收到消息  From：==========="+msg.getFrom());
//                System.out.println("==========收到消息  say：===========" + msg.getBody());
//                if(userFrom.split("@")[0].equals(msg.getFrom().toString().split("@")[0])){
//                    mConversationArrayAdapter.add(msg.getFrom()+"say:"+msg.getBody());
//                }
//            }
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userFrom=getIntent().getExtras().getString("chatuser");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom);
        send_btn = (Button) findViewById(R.id.chatroom_send);
        send_msg = (EditText) findViewById(R.id.chatroom_input);
        messageView = (ListView) findViewById(R.id.msg_recycler_view);
        mConversationArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        messageView.setAdapter(mConversationArrayAdapter);
        XMPPConnection con = ChatServerConnection.getConnection();
        final ChatManager cm = ChatManager.getInstanceFor(con);
//        cm.addChatListener(new ChatManagerListener() {
//            @Override
//            public void chatCreated(Chat chat, boolean createdLocally) {
//                chat.addMessageListener(new ChatMessageListener() {
//                    @Override
//                    public void processMessage(Chat chat, Message message) {
//                        android.os.Message m=handler.obtainMessage();
//                        m.obj=message;
//                        m.sendToTarget();
//                    }
//                });
//            }
//        });
        EntityBareJid jid = null;
        try {
            jid = JidCreate.entityBareFrom(userFrom);
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
        final Chat chat=cm.createChat(jid, new ChatMessageListener() {
                    @Override
                    public void processMessage(Chat chat, Message message) {
//                            message.setBody(msg_content);
//                        System.out.println(message.getFrom() + "say:" + message.getBody());
                    }
                });
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String msg_content = send_msg.getText().toString();
                android.os.Message mm = new android.os.Message();
                mm.obj = "me:" + msg_content;
                mhandle.handleMessage(mm);
                System.out.println(msg_content);
                try {
                    Message m = new Message();
                    m.setBody(msg_content);
                    chat.sendMessage(m.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Handler mhandle = new Handler() {
                public void handleMessage(android.os.Message m) {
//                    text_out = (TextView) findViewById(R.id.text_out);
                    String respond = (String) m.obj;
                    Log.i("---", respond);
                    mConversationArrayAdapter.add(respond);
                }
            };

        });
    }


}
