package me.arnoldwho.hongdou;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.io.IOException;
import java.net.Socket;

import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends Fragment {

    private  View view;
    private MySocket mySocket = new MySocket();
    private Socket socket;
    private String anoy;

    @BindView(R.id.send_title)
    EditText _send_title;
    @BindView(R.id.send_message)
    EditText _send_message;
    @BindView(R.id.send_btn_in_postfragment)
    Button _send_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_post, container, false);
        ButterKnife.bind(this, view);
        final CheckBox anonymous = view.findViewById(R.id.checkanonymous);
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
        return view;
    }

    @OnClick(R.id.send_btn_in_postfragment)
    public void onButtonClick(View view) {
        switch (view.getId()){
            case R.id.send_btn_in_postfragment:
                final ProgressDialog progressDialog = new ProgressDialog(view.getContext(),
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(getResources().getString(R.string.Sending));
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences pref = NewPostFragment.this.getContext().getSharedPreferences("userdata", MODE_PRIVATE);
                        String username = pref.getString("username", "");
                        final String sendtitle = _send_title.getText().toString();
                        final String sendmessage = _send_message.getText().toString();
                        if (sendMessage(username, anoy, sendtitle, sendmessage, socket)){
                            progressDialog.dismiss();
                            NavHostFragment.findNavController(NewPostFragment.this).navigate(R.id.action_newPostFragment_to_messagesFragment2);
                        }
                    }
                }).start();
                break;
        }
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
                return response.equals("/Successed");
            } else
                return false;
        } else
            return false;
    }

}
