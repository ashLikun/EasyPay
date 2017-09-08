package com.ashlikun.easypay.wxpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/23　16:05
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：微信支付返回的Activity
 * <activity-alias
 * android:name=".wxapi.WXPayEntryActivity"
 * android:exported="true"
 * android:targetActivity="pay.hbung.com.pay.PayActivity"></activity-alias>
 */

public class WXPayActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (WxPay.onPayCallback == null) {
            finish();
        } else {
            api = WXAPIFactory.createWXAPI(this, WxPay.payEntity.appId);
            api.handleIntent(getIntent(), this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/8/23 16:17
     * 邮箱　　：496546144@qq.com
     * 方法功能：baseResp code : 0：支付成功   -1:支付失败    -2：支付取消
     */
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (WxPay.onPayCallback != null) {
                WxPay.onPayCallback.onWXFinsh(baseResp.errCode, WxPay.payEntity);
                WxPay.release();
            }
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WxPay.release();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        WxPay.release();
    }
}