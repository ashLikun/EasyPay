package com.ashlikun.easypay.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ashlikun.easypay.EasyPay;
import com.ashlikun.easypay.PayEntity;
import com.ashlikun.easypay.PayResult;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/5/17 0017　下午 5:51
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        PayEntity payEntity = PayEntity.get(EasyPay.CHANNEL_WECHAT);
        EasyPay.startPay(this, payEntity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EasyPay.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK) {

            PayResult result = EasyPay.getResult(data);
            if (result == null) {
                return;
            }
            String msg = "";
            if (result.isSuccess()) {
                msg = "支付成功！";
            } else if (result.isFail()) {
                msg = "支付失败！";
            } else if (result.isCancel()) {
                msg = "用户取消了支付";
            } else {
                msg = result.errorMsg;
            }
            PayResultActivity.startUi(this, result);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        }
    }
}
