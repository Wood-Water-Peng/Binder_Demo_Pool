package com.example.jackypeng.binder_pool_demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


import java.util.concurrent.CountDownLatch;

/**
 * Created by jackypeng on 2018/4/11.
 */

public class BinderPool {

    public static final int BINDER_ENCRYPT_MODULE = 0;
    public static final int BINDER_DECRYPT_MODULE = 1;
    public static final int BINDER_NONE = -1;
    private static final String TAG = "BinderPool";

    private static volatile BinderPool sInstance;
    private CountDownLatch mConnectionBinderPoolCountDownLatch;
    private Context mContext;
    private IBinderPool mBinderPool;

    private BinderPool(Context context) {
        this.mContext = context;
        connectBinderPool();
    }

    public static BinderPool getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    private void connectBinderPool() {
        Log.i(TAG, "connectBinderPool:"+Thread.currentThread().getName());
        mConnectionBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(intent, mBindPoolConnection, Context.BIND_AUTO_CREATE);
        try {
            mConnectionBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int code) {
        IBinder binder = null;
        if (mBinderPool == null) {
            throw new IllegalStateException("you should invoke connectBinderPool method first!");
        } else {
            try {
                binder = mBinderPool.queryBinder(code);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }

    private ServiceConnection mBindPoolConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            Log.i(TAG, "onServiceConnected:"+Thread.currentThread().getName());
            mConnectionBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public static class BindPoolImpl extends IBinderPool.Stub {

        @Override
        public IBinder queryBinder(int code) throws RemoteException {
            IBinder binder = null;
            switch (code) {
                case BINDER_ENCRYPT_MODULE:
                    binder = new EncryptModuleBinderImpl();
                    break;
                case BINDER_DECRYPT_MODULE:
                    binder = new DecryptModuleBinderImpl();
                    break;
                default:
                    break;
            }
            return binder;
        }
    }
}
