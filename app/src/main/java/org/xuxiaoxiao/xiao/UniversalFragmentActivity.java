package org.xuxiaoxiao.xiao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class UniversalFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

//    @LayoutRes
//    protected int getLayoutResId() {
//        // 让它的子类来决定要 inflate 那个 Layout
//        return R.layout.activity_fragment;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.top_fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.top_fragment_container, fragment)
                    .commit();
        }
    }
}
