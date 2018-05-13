package me.arnoldwho.hongdou;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    public Socket socket;
    MySocket mySocket = new MySocket();
    String response;
    String name;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.logininfo) TextView _logininfo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        new Thread(connect).start();
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
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

    public void login() {
        if (!validate()) {
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!socket.isConnected()){
                    new Thread(connect).start();
                }
                final String name = _nameText.getText().toString();
                final String password = _passwordText.getText().toString();

                response = mySocket.getResponse("/login", socket);
                if (response.equals("/sure")){
                    String str = name + " " + password;
                    response = mySocket.getResponse(str, socket);
                    if (response.equals("/Successed")){
                        saveInfo();
                        progressDialog.dismiss();
                        try{
                            socket.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else if (response.equals("/WrongPassword")){
                        progressDialog.dismiss();
                        _logininfo.setText("Wrong Password!");
                    } else if(response.equals("/NoUsername")){
                        progressDialog.dismiss();
                        _logininfo.setText("No username");
                    }
                }
            }
        }).start();
    }

    public void saveInfo(){
        SharedPreferences.Editor editor = getSharedPreferences("userdata",MODE_PRIVATE).edit();
        editor.putString("username", _nameText.getText().toString());
        editor.apply();

    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.contains(" ")) {
            _nameText.setError("Plz check your username!");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}