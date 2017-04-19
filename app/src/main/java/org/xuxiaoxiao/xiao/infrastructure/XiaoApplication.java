package org.xuxiaoxiao.xiao.infrastructure;

import android.app.Application;

import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.model.WilddogUser;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

/**
 * Created by WuQiang on 2017/4/10.
 */

public class XiaoApplication extends Application {
    private  User user;
    private SoundPool soundPool;
    WilddogApp wilddogApp;
    WilddogAuth wilddogAuth;
    WilddogUser wUser;
    @Override
    public void onCreate() {
        super.onCreate();
        WilddogOptions wilddogOptions = new WilddogOptions.Builder().setSyncUrl("https://xuxiaoxiao1314.wilddogio.com").build();
        wilddogApp = WilddogApp.initializeApp(this, wilddogOptions);
        wilddogAuth = WilddogAuth.getInstance();
        wUser = wilddogAuth.getCurrentUser();
        user = new User("name","eMail");
        soundPool = new SoundPool(this);
    }
    public User getUser(){
        return user;
    }
    public SoundPool getSoundPool() {
        return soundPool;
    }

    public WilddogApp getWilddogApp() {
        return wilddogApp;
    }

    public WilddogAuth getWilddogAuth() {
        return wilddogAuth;
    }

    public WilddogUser getwUser() {
        return wUser;
    }
}
