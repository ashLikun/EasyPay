package com.ashlikun.easypay.wxpay;

import android.app.Activity;
import android.content.Intent;

import com.ashlikun.easypay.PayResult;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.ashlikun.easypay.EasyPayActivity.payResult;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/9　16:58
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class WXIntentHandler implements IWXAPIEventHandler {
    Activity activity;

    public WXIntentHandler(String appId, Activity activity, Intent intent) {
        this.activity = activity;
        IWXAPI api = WXAPIFactory.createWXAPI(activity, appId);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/8/23 16:17
     * 邮箱　　：496546144@qq.com
     * 方法功能：baseResp code : 0：支付成功   -1:支付失败    -2：支付取消
     */
    public void onResp(BaseResp baseResp) {
        if (payResult == null) {
            payResult = new PayResult();
        }
        payResult.setWxResult(baseResp);
        activity.finish();
    }
}
