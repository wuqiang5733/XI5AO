package org.xuxiaoxiao.xiao.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wilddog.wilddogauth.WilddogAuth;

import org.xuxiaoxiao.xiao.ChatActivity;
import org.xuxiaoxiao.xiao.R;
import org.xuxiaoxiao.xiao.base.BaseActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WuQiang on 2017/4/19.
 */

public class LoginActivity extends BaseActivity {
    WilddogAuth wilddogAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wilddogAuth = WilddogAuth.getInstance();

        final EditText email = (EditText)findViewById(R.id.login_email);
        final EditText passWord = (EditText)findViewById(R.id.login_password);
        Button registerButton = (Button)findViewById(R.id.register_button);

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

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String emailStr = email.getText().toString().trim();
               String passWordStr = passWord.getText().toString().trim();
                if(!isEmailValid(emailStr)){
                    email.setError("格式不正确");
                }
                if (passWordStr.length()<8){
                    passWord.setError("长度不能小于8");
                }

                Toast.makeText(getApplicationContext(),emailStr + passWordStr ,Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
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
