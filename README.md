# 支付宝与微信支付
## 使用方法

   build.gradle文件中添加:

   compile 'com.github.ashLikun:EasyPay:1.0.0'


## 详细介绍
    集成支付宝与微信支付


### 支付宝
        [支付宝文档](https://docs.open.alipay.com/204/105296/)

### 微信
        [微信文档](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_5)

        我使用了ping++的实现方式，利用回调
          <activity-alias
                android:name=".wxapi.WXPayEntryActivity"
                android:exported="true"
                android:targetActivity="pay.hbung.com.pay.PayActivity">
          </activity-alias>