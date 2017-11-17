package com.ashlikun.easypay.wxpay;

import android.content.Intent;
import android.util.Log;

import com.ashlikun.easypay.EasyPayActivity;
import com.ashlikun.easypay.PayResult;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
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
    EasyPayActivity activity;

    public WXIntentHandler(String appId, EasyPayActivity activity, Intent intent) {
        this.activity = activity;
        IWXAPI api = WXAPIFactory.createWXAPI(activity, appId);
        boolean result = api.handleIntent(intent, this);
        if (!result) {
            activity.setUnknownResult("微信返回失败");
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
            if (payResult == null) {
                payResult = new PayResult();
            }
            payResult.setWxResult(baseResp);
            activity.setResutl();
        }
    }
}
