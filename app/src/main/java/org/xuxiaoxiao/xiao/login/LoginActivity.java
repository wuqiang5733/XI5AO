package org.xuxiaoxiao.xiao.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import org.xuxiaoxiao.xiao.ChatActivity;
import org.xuxiaoxiao.xiao.R;
import org.xuxiaoxiao.xiao.base.BaseActivity;

/**
 * Created by WuQiang on 2017/4/19.
 */

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Intent intent = new Intent(this,ChatActivity.class);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginActivity","你登陆了 。。。 ");
                application.getUser().setLoggedIn(true);
                startActivity(intent);
                finish();
            }
        });
    }
    //    @Override
//    protected Fragment createFragment() {
//        return LoginFragment.newInstance();
//    }
//    // 下面是跟权限有关的
//    @Override
//    protected String[] getDesiredPermissions() {
//        return (new String[]{WRITE_EXTERNAL_STORAGE});
//    }
//
//    @Override
//    protected void onPermissionDenied() {
//        Toast.makeText(this, R.string.msg_sorry, Toast.LENGTH_LONG).show();
//        finish();
//    }
//
//    @Override
//    protected void onReady(Bundle state) {
//
//    }
}
