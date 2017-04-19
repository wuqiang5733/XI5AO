package org.xuxiaoxiao.xiao.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.model.WilddogUser;
import com.wilddog.wilddogcore.WilddogApp;

import org.xuxiaoxiao.xiao.infrastructure.User;
import org.xuxiaoxiao.xiao.infrastructure.XiaoApplication;

/**
 * Created by WuQiang on 2017/4/19.
 */

public class BaseActivity extends AppCompatActivity {
    protected XiaoApplication application;
    protected User user;
    protected WilddogApp wilddogApp;
    protected WilddogAuth wilddogAuth;
    protected WilddogUser wUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.application = (XiaoApplication) getApplication();
        this.user = application.getUser();
        this.wUser = application.getwUser();
    }
}
