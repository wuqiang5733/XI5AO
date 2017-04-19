package org.xuxiaoxiao.xiao.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.result.AuthResult;
import com.wilddog.wilddogauth.model.WilddogUser;

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

        final EditText email = (EditText) findViewById(R.id.login_email);
        final EditText passWord = (EditText) findViewById(R.id.login_password);
        Button registerButton = (Button) findViewById(R.id.register_button);
        Button queryButton = (Button)findViewById(R.id.query_button);
        final TextView uidTextView = (TextView)findViewById(R.id.uid);
        final TextView providerIdTextView = (TextView)findViewById(R.id.providerId);
        final TextView phoneTextView = (TextView)findViewById(R.id.phone);
        final TextView nameTextView = (TextView)findViewById(R.id.name);
        final TextView photoUrlTextView = (TextView)findViewById(R.id.photoUrl);
        final TextView emialTextView = (TextView)findViewById(R.id.email);

        final Intent intent = new Intent(this, ChatActivity.class);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = email.getText().toString().trim();
                String passWordStr = passWord.getText().toString().trim();
                if (!isEmailValid(emailStr)) {
                    email.setError("格式不正确");
                }
                if (passWordStr.length() < 8) {
                    passWord.setError("长度不能小于8");
                }
                wilddogAuth.signInWithEmailAndPassword(emailStr, passWordStr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> var1) {
                        if (var1.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                            Log.d("success", "Login success!");  // 登录成功
                            Log.d("Anonymous", String.valueOf(var1.getResult().getWilddogUser().isAnonymous()));
                        } else {
                            Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_SHORT).show();
                            Log.d("failure", "reason:" + var1.getException().toString()); // 登录失败及错误信息
                        }
                    }
                });

//                Log.d("LoginActivity", "你登陆了 。。。 ");
//                application.getUser().setLoggedIn(true);
//                startActivity(intent);
//                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = email.getText().toString().trim();
                String passWordStr = passWord.getText().toString().trim();
                if (!isEmailValid(emailStr)) {
                    email.setError("格式不正确");
                }
                if (passWordStr.length() < 8) {
                    passWord.setError("长度不能小于8");
                }
                wilddogAuth.createUserWithEmailAndPassword(emailStr, passWordStr).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> var1) {
                                if (var1.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
//                                    Log.d("result", "Create user success");
                                } else {
                                    Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
//                                    Log.d("result", "reason:" + var1.getException().toString());
                                }
                            }
                        });
            }
        });

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WilddogUser user = wilddogAuth.getCurrentUser();
                if (user != null) {
                    String uid = user.getUid();
                    String providerId = user.getProviderId();
                    String phone = user.getPhone();
                    String name = user.getDisplayName();
                    Uri photoUrl = user.getPhotoUrl();
                    String email = user.getEmail();
                    uidTextView.setText(uid);
                    providerIdTextView.setText(providerId);
                    phoneTextView.setText(phone);
                    nameTextView.setText(name);
                    photoUrlTextView.setText(String.valueOf(photoUrl));
                    emialTextView.setText(email);
                } else {
                    // 没有用户登录.
                }
            }
        });

    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
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
