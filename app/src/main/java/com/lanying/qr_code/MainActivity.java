package com.lanying.qr_code;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * ZXing :zebra crossing 斑马线
 * QR Code : Quick Response Code
 * 解析二维码：QR Code --> String
 * 生成二维码：String --> QR Code
 */
public class MainActivity extends AppCompatActivity {
    private TextView mTvResult;
    private EditText mEditText ;
    private ImageView mImageViewResult;
    private CheckBox mChkLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mEditText = (EditText) findViewById(R.id.et_input);
        mImageViewResult = (ImageView) findViewById(R.id.iv_result);
        mChkLogo = (CheckBox) findViewById(R.id.chk_logo);
    }

    /**
     * 解析：扫描二维码，并将结果显示在TextView上
     * @param view
     */
    public void scan(View view) {
        // 开启library中的CaptureActivity
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 0);// 请求码必须>=0，否则不会触发onActivityResult
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode){
            case Activity.RESULT_OK:
                Bundle bundle = data.getExtras();
                String result = bundle.getString("result");
                mTvResult.setText(result);
                break;
        }
    }


    /**
     * 生成二维码：根据EditText中输入的String，生成相应的二维码
     * @param view
     */
    public void make(View view) {
        String data = mEditText.getText().toString().trim();
        if(TextUtils.isEmpty(data)){
            Toast.makeText(MainActivity.this, "请输入数据", Toast.LENGTH_SHORT).show();
        }else{
            Bitmap bitmap = EncodingUtils.createQRCode(data,500,500,
                    mChkLogo.isChecked()
                            ? BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)
                            :null);
            mImageViewResult.setImageBitmap(bitmap);
        }
    }
}
