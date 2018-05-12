package me.arnoldwho.hongdou;

import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MySocket2 {

    private OutputStream outputStream;
    private BufferedReader bufferedReader;
    String response;
    private Socket socket;

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
    public String getResponse(String sendData) {
        try{
            //socket = new Socket("45.63.91.170", 20566);
            new Thread(connect).start();
            SystemClock.sleep(1500);
            outputStream = socket.getOutputStream();
            outputStream.write(sendData.getBytes("utf-8"));
            outputStream.flush();
            BufferedReader bufferedRader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
