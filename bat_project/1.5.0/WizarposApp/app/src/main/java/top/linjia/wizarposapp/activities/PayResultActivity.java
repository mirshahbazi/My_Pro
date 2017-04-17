package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.PayResultInfo;

public class PayResultActivity extends AppCompatActivity implements View.OnClickListener {

    private PayResultInfo payResult;
    private TextView mActivityPayresultTitle;
    private ImageView mPayResultActivityImage;
    private TextView mPayResultActivityMsg;
    private Button mReturnMainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        Intent intent = getIntent();
        payResult = intent.getParcelableExtra("pay_Result");
        initView();
    }


    private void initView() {
        mActivityPayresultTitle = (TextView) findViewById(R.id.activity_payresult_title);
        mPayResultActivityImage = (ImageView) findViewById(R.id.payResult_activity_image);
        mPayResultActivityMsg = (TextView) findViewById(R.id.payResult_activity_msg);
        mReturnMainBtn = (Button) findViewById(R.id.return_main_btn);

        mReturnMainBtn.setOnClickListener(this);
        if(!"0".equals(payResult.getCode())) {
            responseLayout();
        }
        mPayResultActivityMsg.setText(payResult.getMessage());
    }

    /**
     * 响应式
     */
    private void responseLayout() {
        mActivityPayresultTitle.setText(R.string.payResult_activity_title_fail);
        mPayResultActivityImage.setImageResource(R.mipmap.pay_fail);
    }

    @Override
    public void onBackPressed() {
        toMainPage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_main_btn:
                toMainPage();
                break;
        }
    }

    private void toMainPage() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
