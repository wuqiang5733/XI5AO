package org.xuxiaoxiao.xiao;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;

public class ChatActivity extends UniversalFragmentActivity implements ChatFragment.Callbacks,FunctionPageView.Callbacks{

    @Override
    protected Fragment createFragment() {
        return new ChatFragment();
    }

    @Override
    public void onEmotionSelected() {

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.bottom_fragment_container);
        frameLayout.setVisibility((frameLayout.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);


        if (findViewById(R.id.bottom_fragment_container) == null) {
            return;
        } else {
            // 如果是平板，那么把 CrimeFragment 嵌到 detail_fragment_container 当中
//            ViewPager pager = (ViewPager)findViewById(R.id.emotion_pager);
//            pager.setAdapter(new PageViewAdapter(this, getFragmentManager());

            Fragment emotionPageView = FunctionPageView.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.bottom_fragment_container, emotionPageView)
                    .commit();
        }
    }

    @Override
    public void onSendEmotion(String emotionName) {
        ChatFragment listFragment = (ChatFragment)getSupportFragmentManager().
                findFragmentById(R.id.top_fragment_container);
        listFragment.sendEmotion(emotionName);
    }
}
