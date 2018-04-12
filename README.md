# Binder_Demo_Pool

![binder线程池.png](https://upload-images.jianshu.io/upload_images/7769455-a390223bf4b138f7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在服务端Service中，有三个Binder类，那么服务端也就开了3个线程。

步骤一：通过IPC获取BinderPool在本地的引用(这是个Proxy对象，系统会帮你创建)。

步骤二：通过该Proxy对象直接和Service端的Binder进行通信

包结构如下：

![Binder线程池包结构.png](https://upload-images.jianshu.io/upload_images/7769455-306b61b2ec6d6f9e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

    interface IBinderPool {
        /**
         * 根据code返回对应的Binder对象
         */
       IBinder queryBinder(int code); 
    }

客户端首先和Service端的BinderPool对象进行一次IPC通信，客户端拿到自己想要的Binder代理，比如EncryptMoudle,然后通过该代理直接和服务端对应的Binder通信。
