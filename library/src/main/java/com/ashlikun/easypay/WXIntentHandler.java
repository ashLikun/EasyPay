package com.ashlikun.easypay;

import android.app.Activity;
import android.content.Intent;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/9　16:58
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class WXIntentHandler implements IWXAPIEventHandler {
    EasyPay pay;

    public WXIntentHandler(Activity activity, EasyPay pay, Intent intent) {
        this.pay = pay;
        IWXAPI api = WXAPIFactory.createWXAPI(activity.getApplicationContext(), pay.payEntity.appId);
        api.registerApp(pay.payEntity.appId);
        boolean result = api.handleIntent(intent, this);
        if (!result) {
            pay.setUnknownResult("微信返回失败");
        }
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
    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (pay.payResult == null) {
                pay.payResult = new PayResult();
            }
            pay.payResult.setWxResult(baseResp);
            pay.setResutl();
        }
    }
}
