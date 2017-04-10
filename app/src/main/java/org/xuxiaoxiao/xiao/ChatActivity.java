package org.xuxiaoxiao.xiao;

import android.support.v4.app.Fragment;

public class ChatActivity extends UniversalFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ChatFragment();
    }

}
