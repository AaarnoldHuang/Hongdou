package me.arnoldwho.hongdou;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

 public class MySocket {

    private OutputStream outputStream;
    private BufferedReader bufferedReader;
    String response;

     public String getResponse(String sendData, Socket socket) {
        try{
            outputStream = socket.getOutputStream();
            outputStream.write(sendData.getBytes("utf-8"));
            outputStream.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
     }

 }
