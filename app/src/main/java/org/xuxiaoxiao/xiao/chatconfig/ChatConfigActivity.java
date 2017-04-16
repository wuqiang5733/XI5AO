package org.xuxiaoxiao.xiao.chatconfig;

import android.support.v4.app.Fragment;

import org.xuxiaoxiao.xiao.UniversalFragmentActivity;

/**
 * Created by WuQiang on 2017/3/31.
 */




public class ChatConfigActivity extends UniversalFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return ChatConfigFragment.newInstance();
    }
}
