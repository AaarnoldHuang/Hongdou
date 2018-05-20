package me.arnoldwho.hongdou;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private List<Messages> myMessagesList;
    private LocalBroadcastManager localBroadcastManager;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView _avatar;
        TextView _title;
        ImageView _likes;
        TextView _likes_num;

        public ViewHolder(View view) {
            super(view);
            _avatar = (ImageView) view.findViewById(R.id.avatar);
            _title = (TextView) view.findViewById(R.id.show_title);
            _likes = (ImageView) view.findViewById(R.id.likes);
            _likes_num = (TextView) view.findViewById(R.id.likes_number);
        }
    }

    public MessageAdapter(List<Messages> messagesList){
        myMessagesList = messagesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_style,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);
        localBroadcastManager = LocalBroadcastManager.getInstance(parent.getContext());
        final Intent intent = new Intent(MessagesFragment.LOCAL_BROADCAST);
        holder._title.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Messages messages = myMessagesList.get(position);
                intent.putExtra("id", messages.getId());
                intent.putExtra("title", messages.getTitles());
                intent.putExtra("like_num", messages.getLikes_num());
                Log.d("hahahaha", messages.getLikes_num());
                localBroadcastManager.sendBroadcast(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Messages messages = myMessagesList.get(position);
        holder._title.setText(messages.getTitles());
        holder._likes_num.setText(messages.getLikes_num());
        holder._avatar.setImageResource(R.drawable.ic_women_selected);
        holder._likes.setImageResource(R.drawable.icon_like);
    }

    @Override
    public int getItemCount(){
        return myMessagesList.size();
    }
}
