package org.xuxiaoxiao.xiao.infrastructure;

/**
 * Created by WuQiang on 2017/4/11.
 */

public class SendEmotion {
    private String emotionName;

    public SendEmotion(String emotionName) {
        this.emotionName = emotionName;
    }

    public String getEmotionName() {
        return emotionName;
    }

    public void setEmotionName(String emotionName) {
        this.emotionName = emotionName;
    }
}
