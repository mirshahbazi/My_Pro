package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import top.linjia.wizarposapp.R;

public class PayDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView commonTopBackIv;
    protected TextView activityOrderformDetailOrderformmun;
    protected ListView activityPayDetailListview;
    protected TextView activityPayDetailPayway;
    protected TextView activityPayDetailDeliverymode;
    protected TextView activityPayDetailDeliverytime;
    protected TextView activityPayDetailAllgoodsmoney;
    protected TextView activityPayDetailPrivilege;
    protected TextView activityPayDetailDeliverymoney;
    protected TextView activityOrderformDetailTime;
    protected TextView activityOrderformDetailMoneysum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_pay_details);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.activity_pay_details_top_back_iv) {
            // TODO: 2016/10/24   点击返回按钮直接跳转到首页面
            Intent toMainActivityIntent = new Intent(this,MainActivity.class);
            startActivity(toMainActivityIntent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // TODO: 2016/10/24   点击返回按钮直接跳转到首页面
        Intent toMainActivityIntent = new Intent(this,MainActivity.class);
        startActivity(toMainActivityIntent);
    }

    private void initView() {
        commonTopBackIv = (ImageView) findViewById(R.id.activity_pay_details_top_back_iv);
        commonTopBackIv.setOnClickListener(PayDetailsActivity.this);
        activityOrderformDetailOrderformmun = (TextView) findViewById(R.id.activity_orderform_detail_orderformmun);
        activityPayDetailListview = (ListView) findViewById(R.id.activity_pay_detail_listview);
        activityPayDetailPayway = (TextView) findViewById(R.id.activity_pay_detail_payway);
        activityPayDetailDeliverymode = (TextView) findViewById(R.id.activity_pay_detail_deliverymode);
        activityPayDetailDeliverytime = (TextView) findViewById(R.id.activity_pay_detail_deliverytime);
        activityPayDetailAllgoodsmoney = (TextView) findViewById(R.id.activity_pay_detail_allgoodsmoney);
        activityPayDetailPrivilege = (TextView) findViewById(R.id.activity_pay_detail_privilege);
        activityPayDetailDeliverymoney = (TextView) findViewById(R.id.activity_pay_detail_deliverymoney);
        activityOrderformDetailTime = (TextView) findViewById(R.id.activity_orderform_detail_time);
        activityOrderformDetailMoneysum = (TextView) findViewById(R.id.activity_orderform_detail_moneysum);
    }

}
