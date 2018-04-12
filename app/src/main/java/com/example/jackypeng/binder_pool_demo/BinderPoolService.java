package com.example.jackypeng.binder_pool_demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by jackypeng on 2018/4/11.
 */

public class BinderPoolService extends Service {
    public static final String TAG = "BinderPoolService";
    private Binder bindPool = new BinderPool.BindPoolImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "---onBind---");
        return bindPool;
    }
}
