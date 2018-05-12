package me.arnoldwho.hongdou;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class NewMessageActivity extends AppCompatActivity {

    SendMessages sendMessages = new SendMessages();
    MySocket mySocket = new MySocket();
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Thread(connect).start();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (sendMessage("arnold", "0",
                                "这是客户端发送的\uD83D\uDE44第一条留言", "这是我用客户端发送的第一条留言" +
                                        "，纪念一下", socket)){
                            Toast.makeText(NewMessageActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).start();
            }
        });
    }

    Runnable connect = new Runnable() {
        @Override
        public void run() {
            try{
                socket = new Socket("45.63.91.170", 20566);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public boolean sendMessage(String username, String anonymous, String title, String message, Socket socket){
        String response = mySocket.getResponse("/newMessage", socket);
        if (response.equals("/sure")){
            String str = "User=" + username + ";;" + "Anoymous=" + anonymous + ";;" + "Title=" + title;
            response = mySocket.getResponse(str, socket);
            if (response.equals("/GotInfo")){
                response = mySocket.getResponse(message, socket);
                if (response.equals("/Successed")){
                    Log.d("haha", "Success!");
                    return true;
                } else {
                    Log.d("haha", "Failed");
                    return false;
                }
            } else
                return false;
        } else
            return false;
    }
}
