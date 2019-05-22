package com.ashlikun.easypay;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.IntDef;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/9　16:00
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class EasyPay implements LifecycleObserver {
    /**
     * 每次start不能重复请求，间隔默认1秒，可以更改
     */
    public static int DELAY_START = 1000;
    public static long CURRENT_TIME = 0;


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

    /**
     * 支付的实体
     */
    PayEntity payEntity = null;
    /**
     * 支付结果
     */
    PayResult payResult = null;
    /**
     * 支付回调
     */
    OnPayCallback callback = null;
    /**
     * 全局Activity
     */
    Activity activity = null;


    @IntDef({CHANNEL_UPACP, CHANNEL_WECHAT, CHANNEL_ALIPAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PayChannel {
    }

    public EasyPay(Activity activity, OnPayCallback callback, PayEntity payEntity) {
        this.payEntity = payEntity;
        this.callback = callback;
        this.activity = activity;
        //如果是微信支付就保存当前类准备回掉
        if (payEntity.channel == CHANNEL_WECHAT) {
            EasyPayActivity.easyPay = this;
        }
    }

    /**
     * 开始发起支付
     */
    private void start() {
        if (payEntity.channel == EasyPay.CHANNEL_ALIPAY) {
            PayUtils.alipay(this);
        } else if (payEntity.channel == EasyPay.CHANNEL_WECHAT) {
            PayUtils.wxpay(this);
        } else if (payEntity.channel == EasyPay.CHANNEL_UPACP) {
            PayUtils.upPay(this);
        } else {
            setUnknownResult("不支持的支付渠道");
        }
    }

    /**
     * 设置返回结果
     */
    public void setResutl() {
        if (payResult == null) {
            payResult = new PayResult();
        }
        if (callback != null) {
            callback.onPayResult(payResult);
        }
        clean();
    }

    /**
     * 设置未定义错误
     *
     * @param msg
     */
    public void setUnknownResult(String msg) {
        if (payResult != null) {
            payResult = new PayResult();
        }
        payResult.result = PayResult.RESULT_UNKNOWN;
        payResult.errorMsg = msg;
        if (callback != null) {
            callback.onPayResult(payResult);
        }
        clean();
    }

    /**
     * 清空数据
     */
    public void clean() {
        payResult = null;
        payEntity = null;
        callback = null;
        activity = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        clean();
    }

    /**
     * 银联支付的回掉
     *
     * @param data
     */
    public void onUpActivityResult(Intent data) {
        if (payEntity.channel == EasyPay.CHANNEL_UPACP) {
            //银联支付的回掉
            if (data == null || data.getExtras() == null) {
                if (payResult == null) {
                    payResult = new PayResult();
                }
                payResult.setFail();
                setResutl();
                return;
            }
            payResult.setUpResult(data);
            setResutl();
        }
    }

    /**
     * 开始支付
     */
    public static EasyPay startPay(FragmentActivity activity, PayEntity payEntity, OnPayCallback callback) {
        if ((System.currentTimeMillis() - CURRENT_TIME) > DELAY_START) {
            EasyPay pay = new EasyPay(activity, callback, payEntity);
            activity.getLifecycle().addObserver(pay);
            pay.start();
            return pay;
        }
        CURRENT_TIME = System.currentTimeMillis();
        return null;
    }

    /**
     * 开始支付
     */
    public static EasyPay startPay(Fragment fragment, PayEntity payEntity, OnPayCallback callback) {
        if ((System.currentTimeMillis() - CURRENT_TIME) > DELAY_START) {
            EasyPay pay = new EasyPay(fragment.getActivity(), callback, payEntity);
            fragment.getLifecycle().addObserver(pay);
            pay.start();
            return pay;
        }
        CURRENT_TIME = System.currentTimeMillis();
        return null;
    }


}
