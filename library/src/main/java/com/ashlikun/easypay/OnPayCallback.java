package com.ashlikun.easypay;

/**
 * 作者　　: 李坤
 * 创建时间: 2019/5/22　9:29
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：支付回调
 */
public interface OnPayCallback {
    void onPayResult(PayResult payResult);
}
