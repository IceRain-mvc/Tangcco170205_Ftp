package com.tangcco170205_ftp.adapter;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.tangcco170205_ftp.R;


/**
 * Created by katsuyagoto on 15/06/19.
 */
public class GroupHelper {

    public static final String CATEGORY_POPULAR = "牧者专栏";

    public static final String CATEGORY_LATEST = "推荐视频";

    public static final String WORK = "Work";

    public static final String FOOD = "Food";

    public static final String TRAVEL = "Travel";

    public static final String PRIVATE = "Private";

    /**
     *
     * @param groupName
     * @param imageView
     */
    public static void setupDefaultGroupImage(@NonNull String groupName, ImageView imageView) {
        switch (groupName) {
            case WORK:
                imageView.setImageResource(R.drawable.group_work);
                break;
            case FOOD:
                imageView.setImageResource(R.drawable.group_food);
                break;
            case TRAVEL:
                imageView.setImageResource(R.drawable.group_travel);
                break;
            case PRIVATE:
                imageView.setImageResource(R.drawable.group_private);
                break;
        }
    }
}
