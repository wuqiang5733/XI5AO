package org.xuxiaoxiao.xiao.infrastructure;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by WuQiang on 2017/4/10.
 */

public class EmotionLab {
    private static EmotionLab emotionLab;
    private ArrayList<EmotionSeries> emotionSeries = new ArrayList<>();
    private static String[] seriesName = {"First", "Second", "Third", "Fourth", "Fifth"};
    private static int eachSeriesEmotionNum = 12;

    public static EmotionLab getEmotionLab(Context context) {
        if (emotionLab == null) {
            emotionLab = new EmotionLab(context);
        }
        return emotionLab;
    }

    private EmotionLab(Context context) {

        for (String name : seriesName) {
            ArrayList<Emotion> emotionTemp = new ArrayList<>();

            for (int i = 0; i < eachSeriesEmotionNum; i++) {
                emotionTemp.add(new Emotion(name + i));
            }
            emotionSeries.add(new EmotionSeries(name, eachSeriesEmotionNum, emotionTemp));
        }
    }

    public ArrayList<EmotionSeries> getEmotionSeries() {
        return emotionSeries;
    }

}
