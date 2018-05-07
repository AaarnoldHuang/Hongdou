package me.arnoldwho.hongdou;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketActivity extends AppCompatActivity {

    private static final int TCP_SERVER_PORT = 20566;
    private static final String TCP_SERVER_IP = "45.63.91.170";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        runTcpClient();
    }
    private void runTcpClient() {
        try {
            Socket s = new Socket(TCP_SERVER_IP, TCP_SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            String outMsg = "TCP connecting to " + TCP_SERVER_PORT + System.getProperty("line.separator");
            out.write(outMsg);
            out.flush();
            Log.i("TcpClient", "sent: " + outMsg);
            //accept server response
            String inMsg = in.readLine() + System.getProperty("line.separator");
            Log.i("TcpClient", "received: " + inMsg);
            //close connection
            s.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   /* private void runTcpClientAsService() {
        Intent lIntent = new Intent(this.getApplicationContext(), TcpClientService.class);
        this.startService(lIntent);
    }*/

}
