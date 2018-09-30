package com.forest.alipayplugin;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.forest.mylibrary.PayInterface;

import java.lang.reflect.Constructor;

/**
 * Created by forest on 2018/9/12 0012.
 * 添加代理Activity
 */

public class ProxyActivity extends Activity {

    //需要加载Activity的全类名
    private String className;
    private PayInterface payInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        className = getIntent().getStringExtra("classname");

        try {
            //加载activity
            Class activityClass = getClassLoader().loadClass(className);
            Constructor constructor = activityClass.getConstructor(new Class[]{});
            Object instance = constructor.newInstance(new Object[]{});
            payInterface = (PayInterface) instance;
            payInterface.attach(this);
            Bundle bundle = new Bundle();
            bundle.putString("accout","4567654");
            payInterface.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        payInterface.onStart();
        super.onStart();
    }

    @Override
    protected void onPause() {
        payInterface.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        payInterface.onDestroy();
        super.onDestroy();
    }

    @Override
    public void startActivity(Intent intent) {
        String classname = intent.getStringExtra("classname");
        Intent m = new Intent(this,ProxyActivity.class);
        m.putExtra("classname",classname);
        super.startActivity(m);
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }
}
