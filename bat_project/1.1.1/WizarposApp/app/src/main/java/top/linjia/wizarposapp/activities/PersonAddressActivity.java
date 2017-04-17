package top.linjia.wizarposapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.global.WizarPosApp;

/**
 * @ClassName: PersonAddressActivity
 * @Description: 该Activity是收货地址页面，用来展示用户自己的收货地址。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class PersonAddressActivity extends AppCompatActivity {

    ImageView back_btn;
    TextView person_textview, phone_textview, address_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_address);
        initView();
        initListener();
        initData();
    }

    /**
  * @Title: initView
  * @Description: 初始化布局控件
  * @param:
  * @Date: 2016/12/19
  * @author: 李鹏鹏
  * */
    public void initView() {
        back_btn = (ImageView) findViewById(R.id.address_top_back_iv);
        person_textview = (TextView) findViewById(R.id.myreceive_address_person);
        phone_textview = (TextView) findViewById(R.id.myreceive_address_phone);
        address_textview = (TextView) findViewById(R.id.myreceive_address_address);
    }

    /**
  * @Title: initData
  * @Description: 初始化收货地址数据，并且刷新UI
  * @param: int
  * @Date: 2016/12/19
  * @author: 李鹏鹏
  * */
    public void initData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                person_textview.setText(WizarPosApp.getmPersonalInfo().getResult().getUname());
                phone_textview.setText(WizarPosApp.getmPersonalInfo().getResult().getPhone());
                address_textview.setText(WizarPosApp.getmPersonalInfo().getResult().getAddress());
            }
        });
    }

    public void initListener() {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
