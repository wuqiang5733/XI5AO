package org.xuxiaoxiao.xiao.infrastructure;

/**
 * Created by WuQiang on 2017/4/1.
 */

public class User {
    private String name;
    private boolean isLoggedIn;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
