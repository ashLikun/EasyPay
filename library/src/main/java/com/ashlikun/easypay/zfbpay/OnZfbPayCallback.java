package com.ashlikun.easypay.zfbpay;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/23　15:52
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：支付宝回掉
 */

public interface OnZfbPayCallback {
    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
    void onZfbFinsh(int code, PayResult payResult);
}
