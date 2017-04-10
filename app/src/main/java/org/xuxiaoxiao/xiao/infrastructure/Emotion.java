package org.xuxiaoxiao.xiao.infrastructure;

/**
 * Created by WuQiang on 2017/4/10.
 */

public class Emotion {

    //    public static int mPageSum;     // 一共需要几个页面
//    private int mPageIndex;         // 在第几个页面上
//    private int mNumber;            // 一个页面内，有几个表情
    private String mDescription;    // 表情的文字描述，或者用户自定义的搜索关键字
//    private Image mImage;         // 它的图像
//    private URI mUri                  // 它的网络地址

    public Emotion(String mDescription) {
        this.mDescription = mDescription;
    }


    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
