package me.arnoldwho.hongdou;

public class backup {

    /*import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private Socket socket;
    private Handler mMainHandler;
    private ExecutorService mThreadPool;
    private OutputStream outputStream;
    private InputStream inputStream;
    InputStreamReader inputStreamReader;
    BufferedReader bufferedReader;
    String response;
    //private Button connectbtn, sendbtn;
    private TextView textview;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button connectbtn = (Button)findViewById(R.id.connectbtn);
        Button sendbtn = (Button)findViewById(R.id.sendbtn);
        textview = (TextView)findViewById(R.id.textview);
        editText = (EditText)findViewById(R.id.edittext);

        mThreadPool = Executors.newCachedThreadPool();

        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        textview.setText(response);
                        break;
                }
            }
        };
    }

    public void Connect(View v){
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    socket = new Socket("45.63.91.170", 20566);
                    if (socket.isConnected()){
                        textview.setText("Connected!");
                    }
                    else
                        textview.setText("Failed!");

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

   public void Receved(){
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    inputStream = socket.getInputStream();
                    inputStreamReader = new InputStreamReader(inputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    response = bufferedReader.readLine();
                    /*Message msg = Message.obtain();
                    msg.what = 0;
                    mMainHandler.sendMessage(msg);
                    textview.setText(response);

} catch (IOException e) {
        e.printStackTrace();
        }
        }
        });
        }

public void send(View v){
        mThreadPool.execute(new Runnable() {
@Override
public void run() {

        try {
        outputStream = socket.getOutputStream();
        outputStream.write((editText.getText().toString()).getBytes("utf-8"));
        outputStream.flush();
        //inputStream = socket.getInputStream();
        //inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        response = bufferedReader.readLine();
        Log.d("LogingActivity", response);
        textview.setText(response);

        } catch (IOException e) {
        e.printStackTrace();
        }

        }
        });
        }
        }
        */
}
