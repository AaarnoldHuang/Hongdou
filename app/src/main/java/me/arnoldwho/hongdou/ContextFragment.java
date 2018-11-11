package me.arnoldwho.hongdou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
这是显示详细信息的Fragment
 @author Arnold (hgx)
 @version 1.0
 @see “这是显示留言详细信息的Fragment， 由MessagesFragment跳转过来”
*/
public class ContextFragment extends Fragment {

    private View view;

    //Fragment使用的构造函数-hgx
    public ContextFragment() {
    }

    //Butterkinfe绑定的控件-hgx
    @BindView(R.id.title_view)
    TextView _title_view;
    @BindView(R.id.conetxt_view)
    TextView _context_view;
    @BindView(R.id.like_num_view)
    TextView _like_num_view;
    @BindView(R.id.avatar_view)
    ImageView _avatar_view;
    @BindView(R.id.like_view)
    ImageView _like_view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_context, container, false);
        //Butterknife绑定控件-hgx
        ButterKnife.bind(this, view);

        //显示详细内容-hgx
        display();
        return view;
    }

    //Butterknife的控件事件监听-hgx
    @OnClick(R.id.like_view)
    public void onButtonCLick(View view) {
        switch (view.getId()) {

            //点击爱心图标时执行的动作，弹出一个Toast-hgx
            case R.id.like_view:
                Toast.makeText(view.getContext(), "Developing...", Toast.LENGTH_LONG).show();
                break;
        }
    }


    public void display() {

        //从Bundle中获取传递进来的数据-hgx
        _title_view.setText(getArguments().getString("title"));
        _context_view.setText(getArguments().getString("context"));
        _like_num_view.setText(getArguments().getString("like_num"));

        //把avatar_num从字符串变为int型数据-hgx
        int avatar_num = Integer.valueOf(getArguments().getString("avatar_num"));
        _like_view.setImageResource(R.drawable.icon_like);
        //根据值确定头像
        switch (avatar_num) {
            case 1:
                _avatar_view.setImageResource(R.drawable.avatar1);
                break;
            case 2:
                _avatar_view.setImageResource(R.drawable.avatar2);
                break;
            case 3:
                _avatar_view.setImageResource(R.drawable.avatar3);
                break;
            case 4:
                _avatar_view.setImageResource(R.drawable.avatar4);
                break;
            case 5:
                _avatar_view.setImageResource(R.drawable.avatar5);
                break;
            default:
                break;
        }
    }

}
