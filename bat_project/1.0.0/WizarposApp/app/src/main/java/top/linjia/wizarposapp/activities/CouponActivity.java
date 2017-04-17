package top.linjia.wizarposapp.activities;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.CouponListAdapter;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.CouponBean;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

public class CouponActivity extends AppCompatActivity {
    CouponBean couponBean;
    ListView couponList;
    TextView ruleCoupon,coupon_uncoupon;
    LinearLayout coupon_loading_fail_ll;
    Button coupon_loading_fail_btn;
    ExecutorService executorService=null;
    CouponListAdapter adapter;
    Call myCall=null;
    ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        initView();
        initListener();
        initData();
    }

    public void initData(){
        coupon_loading_fail_ll.setVisibility(View.GONE);
        coupon_uncoupon.setVisibility(View.GONE);
        couponList.setVisibility(View.VISIBLE);
        executorService= WizarPosApp.getInternetThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                FormBody formBody=new FormBody.Builder()
                        .add("appToken",WizarPosApp.getmPersonalInfo().getAppToken())
                        .build();
                myCall= OkHttpUtil.postCallFormServer(Url.TO_PAY_BY_COUPON,formBody);
                try {
                    Response response=myCall.execute();
                    String jsonStr=response.body().string();
                    Log.i("test",jsonStr);
                    couponBean= GsonUtil.parseJsonToBean(jsonStr,CouponBean.class);
                    if(couponBean!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter=new CouponListAdapter(CouponActivity.this,couponBean.getResult().getList());
                                couponList.setAdapter(adapter);
                            }
                        });
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                coupon_uncoupon.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            couponList.setVisibility(View.GONE);
                            coupon_loading_fail_ll.setVisibility(View.VISIBLE);
                            Log.i("test","网络异常");
                        }
                    });
                }
            }
        });
    }
    public void initView(){
        back_btn=(ImageView)findViewById(R.id.coupon_top_back_iv);
        coupon_loading_fail_ll=(LinearLayout)findViewById(R.id.coupon_loading_fail_ll);
        coupon_loading_fail_btn=(Button)findViewById(R.id.coupon_loading_fail_btn);
        ruleCoupon=(TextView)findViewById(R.id.coupon_rulecoupon);
        couponList=(ListView)findViewById(R.id.coupon_listview);
        coupon_uncoupon=(TextView)findViewById(R.id.coupon_uncoupon);
    }
    public void initListener(){
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCall!=null){
                    myCall.cancel();
                }
                finish();
            }
        });
        coupon_loading_fail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCall!=null){
                   myCall.cancel();
                    initData();
                }
            }
        });
        ruleCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(CouponActivity.this);
                TextView message=new TextView(CouponActivity.this);
                message.setTextSize(20);
                message.setPadding(15,15,15,15);
                message.setTextColor(getResources().getColor(R.color.yellow));
                message.setText("1、抵用券  ：单张面值50元、100元、200元、300元\n" +
                        "50元抵用券：  单笔订单金额满150元，可以使用面值50元抵用券\n" +
                        "100元抵用券： 单笔订单金额满299元，可以使用面值100元抵用券\n" +
                        "200元抵用券： 单笔订单金额满599元，可以使用面值200元抵用券\n" +
                        "300元抵用券： 单笔订单金额满899元，可以使用面值300元抵用券\n" +
                        "2、购物券： 单张面值10-100元不等\n" +
                        "5元购物券：订单满50元可以使用\n" +
                        "10元购物券： 订单满100元可以使用\n" +
                        "20元购物券：订单满200元可以使用\n" +
                        "50元购物券：订单满500元可以使用\n" +
                        "100元购物券：订单满1000元可以使用");
                alertDialog.setView(message);
                alertDialog.create().show();
            }
        });
    }
}
