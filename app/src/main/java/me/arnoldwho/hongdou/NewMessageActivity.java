package me.arnoldwho.hongdou;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import java.io.IOException;
import java.net.Socket;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewMessageActivity extends AppCompatActivity {

    SendMessages sendMessages = new SendMessages();
    MySocket mySocket = new MySocket();
    Socket socket;
    String anoy;

    @BindView(R.id.send_title) EditText _send_title;
    @BindView(R.id.send_message) EditText _send_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final CheckBox anonymous = (CheckBox) findViewById(R.id.checkanonymous);
        new Thread(connect).start();
        anonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    anoy = "1";
                    anonymous.setTextColor(getResources().getColor(R.color.accent));
                }else{
                    anoy = "0";
                    anonymous.setTextColor(getResources().getColor(R.color.iron));

                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.sendtoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.send:
                final ProgressDialog progressDialog = new ProgressDialog(NewMessageActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(getResources().getString(R.string.Sending));
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences pref = getSharedPreferences("userdata", MODE_PRIVATE);
                        String username = pref.getString("username", "");
                        final String sendtitle = _send_title.getText().toString();
                        final String sendmessage = _send_message.getText().toString();
                        if (sendMessage(username, anoy, sendtitle, sendmessage, socket)){
                            progressDialog.dismiss();
                            finish();
                        }
                    }
                }).start();
                break;
        }
        return true;
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
                    return true;
                } else {
                    return false;
                }
            } else
                return false;
        } else
            return false;
    }
}
