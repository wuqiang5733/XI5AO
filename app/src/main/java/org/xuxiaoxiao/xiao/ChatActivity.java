package org.xuxiaoxiao.xiao;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xuxiaoxiao.xiao.infrastructure.EBHiddFuncPanel;
import org.xuxiaoxiao.xiao.infrastructure.ToggleFunctionPanel;

public class ChatActivity extends UniversalFragmentActivity implements FunctionPageView.Contract{

//    private FrameLayout frameLayout;

    @Override
    protected Fragment createFragment() {
        return new ChatFragment();
    }
/*
    @Override
    public void onFunctionPanelSelected() {

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
    public void hideFunctionPanel() {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.bottom_fragment_container);

        frameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onSendEmotion(String emotionName) {
        ChatFragment listFragment = (ChatFragment)getSupportFragmentManager().
                findFragmentById(R.id.top_fragment_container);
        listFragment.sendEmotion(emotionName);
    }
*/
    @Subscribe
    public void onMessageEvent(ToggleFunctionPanel event) {

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
    @Subscribe
    public void HiddenFunctionPanel(EBHiddFuncPanel event){
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.bottom_fragment_container);

        frameLayout.setVisibility(View.GONE);
    }

    /**
     * greenrobot/EventBus
     * 好像就是那里需要接收，那里就得 注册 与 取消 一下
     * 发送发 只需要 Post 一下就发了
     */
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onEmotionClick(String emotionName) {
        Toast.makeText(this,emotionName,Toast.LENGTH_SHORT).show();
    }
}
