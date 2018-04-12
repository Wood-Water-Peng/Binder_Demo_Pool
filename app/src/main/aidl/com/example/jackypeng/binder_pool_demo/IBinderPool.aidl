// IBinderPool.aidl
package com.example.jackypeng.binder_pool_demo;

// Declare any non-default types here with import statements

interface IBinderPool {
    /**
     * 根据code返回对应的Binder对象
     */
    IBinder queryBinder(int code);
}
