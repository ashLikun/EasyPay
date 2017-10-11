# 支付宝与微信支付,银联支付
## 使用方法

    build.gradle文件中添加:
    compile 'com.github.ashLikun:EasyPay:1.0.1'
    //注意各个sdk的添加，和混淆

## 详细介绍
   
* 注册 activity  
    
        <!-- 微信支付 -->
        <!-- 1.需要将以下"替换成自己 APK 的包名"换成在微信平台上注册填写的包名 -->
        <!-- 2.WxPayEntryActivity 这个类在 SDK 内部实现，开发者不需要额外实现该类 -->
        <activity-alias
            android:name="替换成自己APK的包名.wxapi.WXPayEntryActivity"
            android:exported="true"
            android:targetActivity="com.ashlikun.easypay.EasyPayActivity" />
       
        <!-- 核心activity -->
        <activity
                android:name="com.ashlikun.easypay.EasyPayActivity"
                android:configChanges="orientation|keyboardHidden|navigation|screenSize"
                android:launchMode="singleTop"
                android:theme="@android:style/Theme.Translucent.NoTitleBar"></activity>
* 处理返回值

        下面代码统一处理支付结果
        在onActivityResult
        if (requestCode == EasyPay.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK)
        
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
### 银联
[银联文档](https://open.unionpay.com/ajweb/help/file/techFile?productId=3)
* 开始支付

        EasyPay.startPay(this, PayEntity.get(EasyPay.CHANNEL_UPACP).setOrderInfo((String) msg.obj).setUpDebug(false));

### 支付宝
[支付宝文档](https://docs.open.alipay.com/204/105296/)
* 开始支付

        EasyPay.startPay(this,PayEntity.get(EasyPay.CHANNEL_ALIPAY).setOrderInfo("orderinfo"));
    
### 微信
[微信文档](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_5)
* 开始支付

        EasyPay.startPay(this,PayEntity.get(EasyPay.CHANNEL_WECHAT)
                    .setAppId("")
                    .setPartnerId("")
                    .setPrepay_id("")
                    .setSign("")
                    .setTimeStamp(""));
        