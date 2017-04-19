package org.xuxiaoxiao.xiao.chatconfig;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.request.UserProfileChangeRequest;
import com.wilddog.wilddogauth.model.WilddogUser;

import org.xuxiaoxiao.xiao.R;
import org.xuxiaoxiao.xiao.base.BaseFragment;
import org.xuxiaoxiao.xiao.login.LoginActivity;

/**
 * Created by WuQiang on 2017/3/31.
 */

public class ChatConfigFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {
    private ToggleButton soundConfig;
    private EditText userNameEditText;
    private String userName;
    private Button logout;

    public static ChatConfigFragment newInstance() {
        return new ChatConfigFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_config, container, false);
//        View view = inflater.inflate(R.layout.fragment_chat_config, container, false);
        soundConfig = (ToggleButton) view.findViewById(R.id.soundConfig);
        soundConfig.setOnCheckedChangeListener(this);
//        soundConfig.setChecked(application.isAlarmOn ? true : false);
        userNameEditText = (EditText) view.findViewById(R.id.userEditText);
        logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                application.getUser().setLoggedIn(false);
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (userNameEditText.getText() != null) {
            // 设置用户名
            userName = userNameEditText.getText().toString();
            user.setName(userName);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.soundConfig) {
//            application.isAlarmOn = isChecked;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.chat_config_exit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 转到配置页面
            case R.id.done:
                TaskClass task = new TaskClass();
                Thread thread = new Thread(task);
                thread.start();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private class TaskClass implements Runnable {
        public TaskClass() {
        }

        @Override
        public void run() {
            WilddogUser user = wilddogAuth.getCurrentUser();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName("name7575")
                    .setPhotoUri(Uri.parse("https://example.com/path/photo.jpg7575"))
                    .build();
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(com.wilddog.wilddogauth.core.Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "更新失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
