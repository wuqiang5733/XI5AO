package org.xuxiaoxiao.xiao.infrastructure;

import android.app.Application;

import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

/**
 * Created by WuQiang on 2017/4/10.
 */

public class XiaoApplication extends Application {
    private  User user;
    private SoundPool soundPool;
    WilddogApp wilddogApp;
    @Override
    public void onCreate() {
        super.onCreate();
        WilddogOptions wilddogOptions = new WilddogOptions.Builder().setSyncUrl("https://wuxu1314.wilddogio.com").build();
        wilddogApp = WilddogApp.initializeApp(this, wilddogOptions);
        user = new User("6789");
        soundPool = new SoundPool(this);
    }
    public User getUser(){
        return user;
    }
    public SoundPool getSoundPool() {
        return soundPool;
    }
}
