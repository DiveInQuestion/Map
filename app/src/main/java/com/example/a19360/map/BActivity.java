package com.example.a19360.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

public class BActivity extends Activity {
    Button btn1,btn2;
    private EditText txt2;
    private String str2;
    private int REQUEST_CODE_SCAN = 111;
    BookCollection bookCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bactivity);
        txt2=(EditText) findViewById(R.id.editText1);
        btn1 = (Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);

        Bundle bundle=this.getIntent().getExtras();
        String str1 = bundle.getString("text1");
        txt2.setText(str1);

        btn1.setOnClickListener(new btnclock1());
        btn2.setOnClickListener(new btnclock2());
    }
    class btnclock1 implements OnClickListener
    {
        public void onClick(View v)
        {
            Intent intent = new Intent(BActivity.this, CaptureActivity.class);
            ZxingConfig config = new ZxingConfig();
            config.setPlayBeep(true);//是否播放扫描声音 默认为true
            config.setShake(true);//是否震动  默认为true
            config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
            config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
            config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
            config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
        }
    }
    class btnclock2 implements OnClickListener
    {
        public void onClick(View v)
        {
            str2=txt2.getText().toString();
            //Toast.makeText(getApplicationContext(),str2, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(BActivity.this,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("text2",str2);
            intent.putExtras(bundle);
            //Toast.makeText(getApplicationContext(),str2, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK,intent);
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                if(content.startsWith("978")&&content.length()==13) {
                    String url_s = "https://api.douban.com/v2/book/isbn/" + content;
                    bookCollection = new BookCollection();
                    Handler handler = new Handler() {
                        public void handleMessage(Message msg) {
                            txt2.setText(bookCollection.getBook().getName());
                        }
                    };
                    bookCollection.download(handler, url_s);
                }
                else
                    Toast.makeText(getApplicationContext(),"无法识别书籍条形码", Toast.LENGTH_SHORT).show();
            }
        }
    }
}