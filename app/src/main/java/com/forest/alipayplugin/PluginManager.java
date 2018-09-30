package com.forest.alipayplugin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by forest on 2018/9/12 0012.
 */

public class PluginManager {

    private Resources resources;
    private DexClassLoader dexClassLoader;

    private Context context;
    private PackageInfo packageInfo;

    public Resources getResources() {
        return resources;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    private static final PluginManager ourInstance = new PluginManager();

    public static PluginManager getInstance() {
        return ourInstance;
    }

    private PluginManager() {
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    //apk
    public void loadPath(String path){
        //apk 路径 实例化
        File dexOutFile = context.getDir("dex",Context.MODE_PRIVATE);
        dexClassLoader = new DexClassLoader(path,dexOutFile.getAbsolutePath(),
                null,context.getClassLoader());
        packageInfo = context.getPackageManager().getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);


        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath",String.class);
            addAssetPath.invoke(assetManager,path);
            resources = new Resources(assetManager,context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());

        }  catch (Exception e) {
            e.printStackTrace();
        }


    }
}
