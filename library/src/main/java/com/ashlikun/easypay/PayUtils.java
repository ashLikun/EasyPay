package com.ashlikun.easypay;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

import java.util.Map;

/**
 * 作者　　: 李坤
 * 创建时间: 2019/5/22　9:41
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public class PayUtils {
    private static final int SDK_PAY_FLAG = 2;

    /**
     * 微信支付
     */
    static void wxpay(EasyPay easyPay) {
        try {
            IWXAPI msgApi = WXAPIFactory.createWXAPI(easyPay.activity.getApplication(), easyPay.payEntity.appId);
            msgApi.registerApp(easyPay.payEntity.appId);
            PayReq req = new PayReq();
            req.appId = easyPay.payEntity.appId;
            req.partnerId = easyPay.payEntity.partnerId;
            req.prepayId = easyPay.payEntity.prepayId;
            req.nonceStr = easyPay.payEntity.nonceStr;
            req.timeStamp = easyPay.payEntity.timeStamp;
            req.packageValue = TextUtils.isEmpty(easyPay.payEntity.packageValue) ? "Sign=WXPay" : easyPay.payEntity.packageValue;
            req.sign = easyPay.payEntity.sign;
            req.extData = easyPay.payEntity.extData;
            if (!req.checkArgs() || !msgApi.sendReq(req)) {
                //失败
                if (easyPay.payResult == null) {
                    easyPay.payResult = new PayResult();
                }
                easyPay.payResult.setFail();
                easyPay.setResutl();
            }
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            easyPay.setUnknownResult(easyPay.payEntity.channel + "不支持该渠道: 。缺少微信的 SDK。");
        }
    }

    /**
     * 银联支付
     */
    static void upPay(final EasyPay easyPay) {
        try {
            UPPayAssistEx.startPay(easyPay.activity, null, null, easyPay.payEntity.orderInfo, easyPay.payEntity.mode);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            easyPay.setUnknownResult(easyPay.payEntity.channel + "不支持该渠道: 。缺少银联的 SDK。");
        }
    }

    /**
     * 支付宝支付
     */
    static void alipay(final EasyPay easyPay) {
        final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(easyPay.activity);
                    // 调用支付接口，获取支付结果
                    Map<String, String> result = alipay.payV2(easyPay.payEntity.orderInfo, true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    new MyHandler(easyPay).sendMessage(msg);
                } catch (NoClassDefFoundError e) {
                    e.printStackTrace();
                    easyPay.setUnknownResult(easyPay.payEntity.channel + "不支持该渠道: 。缺少支付宝的 SDK。");
                }
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    static class MyHandler extends Handler {
        EasyPay easyPay;

        public MyHandler(EasyPay easyPay) {
            super(Looper.getMainLooper());
            this.easyPay = easyPay;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //支付宝
                case SDK_PAY_FLAG: {
                    if (easyPay.payResult == null) {
                        easyPay.payResult = new PayResult();
                    }
                    easyPay.payResult.setAlipayResult((Map<String, String>) msg.obj);
                    easyPay.setResutl();
                    break;
                }
                default:
                    break;
            }
        }
    }
}
