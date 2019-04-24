package com.ashlikun.easypay.simple;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.ashlikun.easypay.PayResult;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/5/17 0017　下午 6:00
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public class PayResultActivity extends AppCompatActivity {
    TextView result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        result = findViewById(R.id.result);
        PayResult res = (PayResult) getIntent().getSerializableExtra("res");
        if (res != null) {
            result.setText(res.result);
        }
    }

    public static void startUi(MainActivity mainActivity, PayResult result) {
        Intent intent = new Intent(mainActivity, PayResultActivity.class);
        intent.putExtra("res", result);
        mainActivity.startActivity(intent);
    }
}
