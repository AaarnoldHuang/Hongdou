package me.arnoldwho.hongdou;

import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.navigation.fragment.NavHostFragment;


public class MessagesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<Messages>messagesList = new ArrayList<>();
    public Application application;
    private View view;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private Socket socket;
    MySocket mySocket = new MySocket();
    String response;
    String myContext;
    String post_num = "0";
    String[] tempArray1 = new String[10];
    String[][] tempArray2 = new String[10][5];
    private String clickedID,  clickedTitle,  clickedLikes, clickedAvatar;
    MessageAdapter adapter = new MessageAdapter(messagesList);
    Fragment fragment = new ContextFragment();
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    public static final String LOCAL_BROADCAST = "me.arnoldwho.hongdou.LOCAL_BROADCAST";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view = inflater.inflate(R.layout.fragment_messages, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MessagesFragment.this).navigate(R.id.action_messagesFragment2_to_newPostFragment);
            }
        });
        application = getActivity().getApplication();
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerView = view.findViewById(R.id.show_messages);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark,
                R.color.accent,R.color.iron);
        new Thread(connect).start();
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localReceiver = new LocalReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(LOCAL_BROADCAST);
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
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

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            String action = intent.getAction();
            if(!action.equals(LOCAL_BROADCAST)){
                return ;
            }
            clickedID = intent.getStringExtra("id");
            clickedTitle = intent.getStringExtra("title");
            clickedLikes = intent.getStringExtra("like_num");
            clickedAvatar = intent.getStringExtra("avatar_num");
            final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!socket.isConnected()){
                        new Thread(connect).start();
                    }
                    response = mySocket.getResponse("/getDetals", socket);
                    if (response.equals("/sure")){
                        response = mySocket.getResponse(clickedID, socket);
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            myContext = "";
                            myContext = jsonArray.getString(0);
                            refreshLayout.setRefreshing(false);
                            adapter.notifyDataSetChanged();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        Intent intent1 = new Intent(getActivity(), ContextActivity.class);
                        intent1.putExtra("id", clickedID);
                        intent1.putExtra("title", clickedTitle);
                        intent1.putExtra("like_num", clickedLikes);
                        intent1.putExtra("context", myContext);
                        intent1.putExtra("avatar_num", clickedAvatar);
                        progressDialog.dismiss();
                        startActivity(intent1);
                    }
                }
            }).start();
        }
    }




    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!socket.isConnected()){
                    new Thread(connect).start();
                }
                response = mySocket.getResponse("/getMessages", socket);
                if (response.equals("/sure")){
                    final String message_info = mySocket.getResponse(post_num, socket);
                    messagesList.clear();
                    try{
                        JSONArray jsonArray = new JSONArray(message_info);
                        for (int i = 0; i < jsonArray.length(); i++){
                            Random r = new Random();
                            String a = String.valueOf(r.nextInt(5) + 1);
                            tempArray1[i] = jsonArray.getString(i);
                            tempArray2[i] = tempArray1[i].split("\\[|,|\\]");
                            Messages temp = new Messages(tempArray2[i][1], tempArray2[i][2], tempArray2[i][3],
                                    tempArray2[i][4], tempArray2[i][5], a);
                            messagesList.add(temp);
                        }
                        refreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}

