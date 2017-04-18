package org.xuxiaoxiao.xiao.chatconfig;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import org.xuxiaoxiao.xiao.R;
import org.xuxiaoxiao.xiao.UniversalFragmentActivity;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by WuQiang on 2017/3/31.
 */




public class ChatConfigActivity extends UniversalFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return ChatConfigFragment.newInstance();
    }
    // 下面是跟权限有关的
    @Override
    protected String[] getDesiredPermissions() {
        return (new String[]{WRITE_EXTERNAL_STORAGE});
    }

    @Override
    protected void onPermissionDenied() {
        Toast.makeText(this, R.string.msg_sorry, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onReady(Bundle state) {

    }
}
