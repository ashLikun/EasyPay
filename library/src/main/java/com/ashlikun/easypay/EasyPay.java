package com.ashlikun.easypay;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/9　16:00
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class EasyPay {
    //请求code
    public static final int REQUEST_CODE_PAYMENT = 1010;
    //请求的数据intent  flag
    public static final String INTENT_FLAG = "PAYDATA";
    //返回给调用者的数据
    public static final String PAY_RESULT = "PAY_RESULT";

    /**
     * 银联支付渠道
     */
    public static final int CHANNEL_UPACP = 1;
    /**
     * 微信支付渠道
     */
    public static final int CHANNEL_WECHAT = 2;

    /**
     * 支付宝支付渠道
     */
    public static final int CHANNEL_ALIPAY = 3;

    @IntDef({CHANNEL_UPACP, CHANNEL_WECHAT, CHANNEL_ALIPAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PayChannel {
    }


    public static void startPay(Activity activity, PayEntity payEntity) {
        Intent intent = new Intent(activity, EasyPayActivity.class);
        intent.putExtra(INTENT_FLAG, payEntity);
        activity.startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    public static void startPay(Fragment fragment, PayEntity payEntity) {
        Intent intent = new Intent(fragment.getActivity(), EasyPayActivity.class);
        intent.putExtra(INTENT_FLAG, payEntity);
        fragment.startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    public static PayResult getResult(Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            return null;
        }
        return (PayResult) intent.getExtras().getSerializable(EasyPay.PAY_RESULT);
    }
}
