package org.xuxiaoxiao.xiao.infrastructure;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import org.xuxiaoxiao.xiao.base.BaseActivity;
import org.xuxiaoxiao.xiao.login.LoginActivity;

/**
 * Created by WuQiang on 2017/4/19.
 */

public abstract class BaseAuthenticatedActivity extends BaseActivity {
    @Override  // 加了 final 也就是这的子类不能 Override 了，只能是在 onYoraCreate 当中做文章
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("WQBaseAuthenActivity","BaseAuthenticatedActivity");
        super.onCreate(savedInstanceState);
        if (!application.getUser().isLoggedIn()){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return;
        }
        onXiaoCreate(savedInstanceState); //经典，这样，就只有登陆之后，才会在子类当中运行程序
    }
    protected abstract void onXiaoCreate(Bundle savedInstanceState);
    // 这个也有助于写程序 ，因为继承自这个类，会让你 Override 这个方法
}
