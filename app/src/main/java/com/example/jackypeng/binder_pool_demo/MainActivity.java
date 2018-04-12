package com.example.jackypeng.binder_pool_demo;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private EditText et_encrypt;
    private EditText et_decrypt;
    private Button btn_encrypt;
    private Button btn_decrypt;
    private BinderPool binderPool;
    private IEncryptModule encryptModule;
    private IDecryptModule decryptModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_encrypt = (EditText) findViewById(R.id.encrypt_string);
        et_decrypt = (EditText) findViewById(R.id.decrypt_string);
        btn_encrypt = (Button) findViewById(R.id.btn_encrypt);
        btn_decrypt = (Button) findViewById(R.id.btn_decrypt);
        btn_encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_encrypt = et_encrypt.getText().toString().trim();
                if (!TextUtils.isEmpty(str_encrypt)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (binderPool == null) {
                                binderPool = BinderPool.getInstance(MainActivity.this);
                            }
                            encryptModule = EncryptModuleBinderImpl.asInterface(binderPool.queryBinder(BinderPool.BINDER_ENCRYPT_MODULE));
                            try {
                                Log.i(TAG, "encrypt:" + encryptModule.encrypt(str_encrypt));
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });

        btn_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_decrypt = et_decrypt.getText().toString().trim();
                if (!TextUtils.isEmpty(str_decrypt)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (binderPool == null) {
                                binderPool = BinderPool.getInstance(MainActivity.this);
                            }
                            decryptModule = DecryptModuleBinderImpl.asInterface(binderPool.queryBinder(BinderPool.BINDER_DECRYPT_MODULE));
                            try {
                                Log.i(TAG, "decrypt:" + decryptModule.decrypt(str_decrypt));
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }
}
