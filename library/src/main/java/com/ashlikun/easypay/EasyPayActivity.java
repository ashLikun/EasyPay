package com.ashlikun.easypay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/23　16:05
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：微信支付返回的Activity
 * <activity-alias
 * android:name=".wxapi.WXPayEntryActivity"
 * android:exported="true"
 * android:targetActivity="com.ashlikun.easypay.EasyPayActivity"></activity-alias>
 */

public class EasyPayActivity extends Activity {
    static EasyPay easyPay = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        parseIntent(getIntent());
    }

    public void parseIntent(Intent intent) {
        //微信返回的支付结果
        if (easyPay != null && easyPay.payEntity != null) {
            new WXIntentHandler(this, easyPay, intent);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        easyPay = null;
    }
}