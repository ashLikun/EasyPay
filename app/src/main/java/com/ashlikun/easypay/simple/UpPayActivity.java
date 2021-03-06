package com.ashlikun.easypay.simple;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ashlikun.easypay.EasyPay;
import com.ashlikun.easypay.PayEntity;
import com.ashlikun.easypay.PayResult;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/9　14:16
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：银联支付
 */

public class UpPayActivity extends AppCompatActivity implements View.OnClickListener, Handler.Callback, Runnable {
    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    private final String mMode = "01";
    private static final String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";
    private ProgressDialog mLoadingDialog = null;
    private Handler mHandler;

    private String ZFB = "app_id=2016090800464161&biz_content=%7B%22out_trade_no%22%3A%22123456%22%2C%22total_amount%22%3A0.01%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A1%2C%22body%22%3A%22%5Cu6d4b%5Cu8bd5%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2F121.43.181.169%2Fapp%2Fzhifubao%2Fpage_notify.php&sign_type=RSA×tamp=2017-10-31+17%3A27%3A20&version=1.0&sign=WdG2e6lQP0%2Bo9eSkUp7dJz6P4gLtGpOMChvkwgbF96%2B6llwVKYpG%2BiU%2BYRMpt9ab0nk9cx1yh8AwAi311XY6Rs6S%2B%2BWphmD5l69VniBVYNDa823MCcS8sMOWqlcIbIo3521OeKgRgHE%2BYtO7Ue1fVOMF00jmM5iErLEkh2rhyWE%3D";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler(this);
        findViewById(R.id.btn).setOnClickListener(this);
        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setTitle("提示");
        mLoadingDialog.setMessage("加载中...");
    }

    //开始支付
    @Override
    public void onClick(View v) {
        mLoadingDialog.show();
        new Thread(this).start();
    }

    @Override
    public void run() {

        String tn = null;
        InputStream is;
        try {
            String url = TN_URL_01;
            URL myURL = new URL(url);
            URLConnection ucon = myURL.openConnection();
            ucon.setConnectTimeout(120000);
            is = ucon.getInputStream();
            int i = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = is.read()) != -1) {
                baos.write(i);
            }

            tn = baos.toString();
            is.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Message msg = mHandler.obtainMessage();
        msg.obj = ZFB;
        mHandler.sendMessage(msg);
    }

    @Override
    public boolean handleMessage(Message msg) {
        Log.e("handleMessage", " " + "" + msg.obj);
        mLoadingDialog.dismiss();

        String tn = "";
        if (msg.obj == null || ((String) msg.obj).length() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("错误提示");
            builder.setMessage("网络连接失败,请重试!");
            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            EasyPay.startPay(this, PayEntity.get(EasyPay.CHANNEL_ALIPAY).setOrderInfo((String) msg.obj).setUpDebug(true));
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
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

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setInverseBackgroundForced(true);
            // builder.setCustomTitle();
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }
}
