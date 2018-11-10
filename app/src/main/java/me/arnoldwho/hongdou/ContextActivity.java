package me.arnoldwho.hongdou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContextActivity extends AppCompatActivity {
    @BindView(R.id.title_view) TextView _tilte_view;
    @BindView(R.id.avatar_view) ImageView _avatar_view;
    @BindView(R.id.conetxt_view) TextView _context_view;
    @BindView(R.id.like_view) ImageView _like_view;
    @BindView(R.id.like_num_view) TextView _like_num_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        display();

        _like_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContextActivity.this, "Developing....", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void display(){
        Intent intent = getIntent();
        _tilte_view.setText(intent.getStringExtra("title"));
        _context_view.setText(intent.getStringExtra("context"));
        _like_num_view.setText(intent.getStringExtra("like_num"));
        _like_view.setImageResource(R.drawable.icon_like);
        if (intent.getStringExtra("avatar_num").equals("1")){
            _avatar_view.setImageResource(R.drawable.avatar1);
        } else if(intent.getStringExtra("avatar_num").equals("2")){
            _avatar_view.setImageResource(R.drawable.avatar2);
        } else if (intent.getStringExtra("avatar_num").equals("3")){
            _avatar_view.setImageResource(R.drawable.avatar3);
        } else if (intent.getStringExtra("avatar_num").equals("4")){
            _avatar_view.setImageResource(R.drawable.avatar4);
        } else if (intent.getStringExtra("avatar_num").equals("5")){
            _avatar_view.setImageResource(R.drawable.avatar5);
        }
    }

}
