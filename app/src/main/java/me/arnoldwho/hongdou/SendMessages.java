package me.arnoldwho.hongdou;

import android.util.Log;
import java.net.Socket;

public class SendMessages {

    MySocket mySocket = new MySocket();

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
