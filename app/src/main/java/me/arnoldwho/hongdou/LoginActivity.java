package me.arnoldwho.hongdou;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class LoginActivity extends AppCompatActivity {

    private Handler mHandler;
    String response;
    private Socket socket;
    private OutputStream outputStream;
    private EditText username, password;
    MySocket mySocket = new MySocket();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText)findViewById(R.id.passwd);
        new Thread(connect).start();

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Toast.makeText(LoginActivity.this, val, Toast.LENGTH_SHORT).show();
        }
    };


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

    Runnable loginsocket = new Runnable() {
        @Override
        public void run() {
            if (!socket.isConnected()){
                new Thread(connect).start();
            }
            response = mySocket.getResponse("/login", socket);
            if (response.equals("/sure")){
                String str = username.getText().toString() + " " + password.getText().toString();
                response = mySocket.getResponse(str, socket);
                if (response.equals("/Successed")){
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("value", "Success");
                    msg.setData(data);
                    handler.sendMessage(msg);
                }
                else {
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("value", "Failed");
                    msg.setData(data);
                    handler.sendMessage(msg);
                }
            }
        }
    };


public void Login(View v){
    new Thread(loginsocket).start();
    }

    public void Signup(View v){
    Toast.makeText(LoginActivity.this, "Developing.....", Toast.LENGTH_SHORT).show();
    }
}
