package me.arnoldwho.hongdou;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.arnoldwho.hongdou.MySocket;
import me.arnoldwho.hongdou.R;

public class SignupActivity extends AppCompatActivity {

    public Socket socket;
    MySocket mySocket = new MySocket();
    String response;
    String str;
    private boolean manSelected = false;
    private boolean womanSelected = false;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    @BindView(R.id.signupinfo) TextView _signupinfo;
    @BindView(R.id.icon_man) ImageView _iconMan;
    @BindView(R.id.icon_woman) ImageView _iconWoman;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        new Thread(connect).start();

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _iconMan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                _iconMan.setImageResource(R.drawable.ic_man_selected);
                _iconWoman.setImageResource(R.drawable.ic_women);
                manSelected = true;
                womanSelected = false;
            }
        });

        _iconWoman.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                _iconMan.setImageResource(R.drawable.ic_man);
                _iconWoman.setImageResource(R.drawable.ic_women_selected);
                manSelected = false;
                womanSelected = true;
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
               // finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    Runnable connect = new Runnable() {
        @Override
        public void run() {
            try{
                socket = new Socket("167.179.72.106", 20566);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public void signup() {
        if (!validate()) {
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!socket.isConnected()){
                    new Thread(connect).start();
                }
                final String name = _nameText.getText().toString();
                final String password = _passwordText.getText().toString();

                response = mySocket.getResponse("/newUser", socket);
                if (response.equals("/sure")){
                    if (manSelected && !womanSelected){
                        str = "Name=" + name + ";;" + "Passwd=" + password + ";;" +"Sex=" + "male";
                    } else if (!manSelected && womanSelected){
                        str = "Name=" + name + ";;" + "Passwd=" + password + ";;" +"Sex=" + "female";
                    }
                    response = mySocket.getResponse(str, socket);
                    if (response.equals("/Successed")){
                        saveInfo();
                        progressDialog.dismiss();
                        try{
                            socket.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        //finish();
                    }
                    else {
                        progressDialog.dismiss();
                        _signupinfo.setText("Wrong Password!");
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
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3 || name.contains(" ")) {
            _nameText.setError("at least 3 characters and can not have any space!");
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

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}