package com.example.jackypeng.binder_pool_demo;

import android.os.RemoteException;
import android.text.TextUtils;

/**
 * Created by jackypeng on 2018/4/11.
 */

public class DecryptModuleBinderImpl extends IDecryptModule.Stub {
    @Override
    public String decrypt(String aString) throws RemoteException {
        if (TextUtils.isEmpty(aString)) {
            return null;
        } else {
            return aString.toLowerCase();
        }
    }
}
