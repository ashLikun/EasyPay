package com.ashlikun.easypay;

import java.io.Serializable;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/9　16:19
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：支付的数据
 */

public class PayEntity implements Serializable {
    //必须参数
    @EasyPay.PayChannel
    transient int channel;
    //这下面是微信的参数
    public String appId;
    public String partnerId;//商户id
    public String prepayId;//预支付id
    public String sign;//签名
    public String nonceStr;//临时数据
    public String timeStamp;//时间戳
    public String extData;//扩展数据

    //支付宝与银联的
    public String orderInfo;//订单信息，银联的TN号
    /*****************************************************************
     * mode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    public transient String mode = "01";

    private PayEntity(@EasyPay.PayChannel int channel) {
        this.channel = channel;
    }

    public static PayEntity get(@EasyPay.PayChannel int channel) {
        return new PayEntity(channel);
    }

    public PayEntity setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public PayEntity setExtData(String extData) {
        this.extData = extData;
        return this;
    }

    public PayEntity setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
        return this;
    }

    public PayEntity setPrepayId(String prepayId) {
        this.prepayId = prepayId;
        return this;
    }

    public PayEntity setPartnerId(String partnerId) {
        this.partnerId = partnerId;
        return this;
    }

    public PayEntity setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public PayEntity setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    //支付宝与银联的
    public PayEntity setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
        return this;
    }

    //银联的测试模式
    public PayEntity setUpDebug(boolean isDebug) {
        this.mode = isDebug ? "01" : "00";
        return this;
    }
}
