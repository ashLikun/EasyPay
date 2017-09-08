package com.ashlikun.easypay.wxpay;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/23　15:52
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：微信支付回掉
 */

public interface OnWxPayCallback {
    void onWXFinsh(int code, WxPay.PayEntity entity);
}
