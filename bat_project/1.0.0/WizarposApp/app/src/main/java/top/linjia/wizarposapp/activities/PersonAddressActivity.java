package top.linjia.wizarposapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.global.WizarPosApp;

public class PersonAddressActivity extends AppCompatActivity {

    ImageView back_btn;
    TextView person_textview,phone_textview,address_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_address);
        initView();
        initListener();
        initData();
    }
    public void initView(){
        back_btn=(ImageView) findViewById(R.id.address_top_back_iv);
        person_textview=(TextView)findViewById(R.id.myreceive_address_person);
        phone_textview=(TextView)findViewById(R.id.myreceive_address_phone);
        address_textview=(TextView)findViewById(R.id.myreceive_address_address);
    }
    public void initData(){
        person_textview.setText(WizarPosApp.getmPersonalInfo().getResult().getUname());
        phone_textview.setText(WizarPosApp.getmPersonalInfo().getResult().getPhone());
        address_textview.setText(WizarPosApp.getmPersonalInfo().getResult().getAddress());
    }
    public void initListener(){
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
