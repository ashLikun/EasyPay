package com.ashlikun.easypay.zfbpay;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/24 16:28
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：支付宝支付
 */
public class ZfbPay {
    /**
     * 商户签约拿到的app_id，如：2013081700024223
     */
    public static String APPID = "";

    /**
     * 商户签约拿到的pid，如：2088102123816631
     */
    public static String PID = "";
    /**
     * 商户唯一标识，如：kkkkk091125
     */
    public static String TARGET_ID = "";
    public static final int SDK_PAY_FLAG = 2;
    private Activity mActivity;
    private OnZfbPayCallback callback;


    public ZfbPay(Activity mActivity, OnZfbPayCallback callback) {
        this.mActivity = mActivity;
        this.callback = callback;
    }

    //orderInfo 支付信息,服务器返回
    public void pay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (callback != null) {
                        int code = 0;
                        try {
                            code = Integer.valueOf(resultStatus);
                        } catch (NumberFormatException e) {
                        }
                        callback.onZfbFinsh(code, payResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

}
