package com.forest.mylibrary;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Created by forest on 2018/9/12 0012.
 * 双方app都需依赖此module
 */

public interface PayInterface {

    //传递上下文
    public void attach(Activity activity);

    //传递生命周期
    public void onCreate(Bundle saveInstanceState);

    public void onStart();

    public void onResume();

    public void onPause();

    public void onStop();

    public void onDestroy();

    public void onSaveInstanceState(Bundle outState);

    public boolean onTouchEvent(MotionEvent event);

    public void onBackPressed();

}
