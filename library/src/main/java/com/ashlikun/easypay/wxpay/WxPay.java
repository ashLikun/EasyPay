package com.ashlikun.easypay.wxpay;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/23　15:45
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */


import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WxPay {
    public static OnWxPayCallback onPayCallback = null;//微信支付全局赋值
    public static WxPay.PayEntity payEntity = null;//微信支付
    Context context;

    public WxPay(Activity context, OnWxPayCallback onPayCallback) {
        this.onPayCallback = onPayCallback;
        this.context = context;
    }

    public void pay(WxPay.PayEntity payEntity) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(this.context, null);
        msgApi.registerApp(payEntity.appId);
        PayReq req = new PayReq();
        req.appId = payEntity.appId;
        req.partnerId = payEntity.partnerId;
        req.prepayId = payEntity.prepay_id;
        req.nonceStr = payEntity.nonceStr;
        req.timeStamp = payEntity.timeStamp;
        req.packageValue = "Sign=WXPay";
        req.sign = payEntity.sign;
        req.extData = payEntity.extData;
        Toast.makeText(this.context, "正常调起支付", Toast.LENGTH_SHORT).show();
        if (this.onPayCallback != null) {
            this.payEntity = payEntity;
        }
        //请求参数判断，请求是否成功
        if ((!req.checkArgs() || !msgApi.sendReq(req)) && onPayCallback != null) {
            onPayCallback.onWXFinsh(-1, this.payEntity);
            release();
        }//其他情况交给WXPayActivity处理,注意调用release方法
    }

    public static void release() {
        onPayCallback = null;
        payEntity = null;
    }

    public static class PayEntity {
        public String appId;
        public String partnerId;//商户id
        public String prepay_id;//预支付id
        public String sign;//签名
        public String nonceStr;//临时数据
        public String timeStamp;//时间戳
        public String extData;//扩展数据

        public PayEntity() {
        }

        public WxPay.PayEntity setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public WxPay.PayEntity setExtData(String extData) {
            this.extData = extData;
            return this;
        }

        public WxPay.PayEntity setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
            return this;
        }

        public WxPay.PayEntity setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
            return this;
        }

        public WxPay.PayEntity setPartnerId(String partnerId) {
            this.partnerId = partnerId;
            return this;
        }

        public WxPay.PayEntity setSign(String sign) {
            this.sign = sign;
            return this;
        }

        public WxPay.PayEntity setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }
    }
}