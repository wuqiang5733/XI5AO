package org.xuxiaoxiao.xiao.infrastructure;

import java.util.ArrayList;

/**
 * Created by WuQiang on 2017/4/10.
 */

public class EmotionSeries {
    private String seriesName;  // 系列的名字
    private int motionNum;      // 系列下有几个表情
    private ArrayList<Emotion> emotions;  // 包含系列下所有表情的数组

    public EmotionSeries(String seriesName, int motionNum, ArrayList<Emotion> emotions) {
        this.seriesName = seriesName;
        this.motionNum = motionNum;
        this.emotions = emotions;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public int getMotionNum() {
        return motionNum;
    }

    public void setMotionNum(int motionNum) {
        this.motionNum = motionNum;
    }

    public ArrayList<Emotion> getEmotions() {
        return emotions;
    }

    public void setEmotions(ArrayList<Emotion> emotions) {
        this.emotions = emotions;
    }
}
