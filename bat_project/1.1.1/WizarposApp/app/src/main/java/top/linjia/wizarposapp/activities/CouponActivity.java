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

/**
 * @ClassName: CouponActivity
 * @Description: 该Activity用来向用户展示自己所拥有的卡券并展示卡券使用规则。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class CouponActivity extends AppCompatActivity {
    CouponBean couponBean;
    ListView couponList;
    TextView ruleCoupon, coupon_uncoupon;
    LinearLayout coupon_loading_fail_ll;
    Button coupon_loading_fail_btn;
    ExecutorService executorService = null;
    CouponListAdapter adapter;
    Call myCall = null;
    Call myCall1 = null;
    ImageView back_btn;
    int couponNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        initView();
        initListener();
        initData();
    }

    /**
    * @Title: initData
    * @Description: 初始化卡券列表
    * @param:
    * @Date: 2016/12/19
    * @author: 李鹏鹏
    * */
    public void initData() {
        /**
         * 首先初始化显示UI界面
         * */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                coupon_loading_fail_ll.setVisibility(View.GONE);
                coupon_uncoupon.setVisibility(View.GONE);
                couponList.setVisibility(View.VISIBLE);
            }
        });
        /**
         * 然后获取线程池，并开启线程开始请示卡券数据
         * */
        executorService = WizarPosApp.getInternetThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                FormBody formBody = new FormBody.Builder()
                        .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                        .build();
                myCall = OkHttpUtil.postCallFormServer(Url.TO_PAY_BY_COUPON, formBody);
                try {
                    Response response = myCall.execute();
                    String jsonStr = response.body().string();
                    Log.i("test", jsonStr);
                    couponBean = GsonUtil.parseJsonToBean(jsonStr, CouponBean.class);
                    if (couponBean != null) {
                        couponNum = couponBean.getResult().getTotalRow();
                        Log.i("test","卡券总数为："+couponNum);
                        FormBody formBody1 = new FormBody.Builder()
                                .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                                .add("pageSize", String.valueOf(couponNum))
                                .add("couponStatus", "1")
                                .add("pageNumber", String.valueOf(1))
                                .build();
                        myCall1 = OkHttpUtil.postCallFormServer(Url.TO_PAY_BY_COUPON, formBody1);
                        Response response1 = myCall1.execute();
                        String jsonStr1 = response1.body().string();
                        couponBean = GsonUtil.parseJsonToBean(jsonStr1, CouponBean.class);
                        /**
                         * 根据从网络请求到的数据解析结果是否为空来选择性刷新Ui界面
                         * */
                        if (couponBean != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = new CouponListAdapter(CouponActivity.this, couponBean.getResult().getList());
                                    couponList.setAdapter(adapter);
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    coupon_uncoupon.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            couponList.setVisibility(View.GONE);
                            coupon_loading_fail_ll.setVisibility(View.VISIBLE);
                            Log.i("test", "网络异常");
                        }
                    });
                }
            }
        });
    }

    /**
     * @Title: initView
     * @Description: 初始化view
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     * */
    public void initView() {
        back_btn = (ImageView) findViewById(R.id.coupon_top_back_iv);
        coupon_loading_fail_ll = (LinearLayout) findViewById(R.id.coupon_loading_fail_ll);
        coupon_loading_fail_btn = (Button) findViewById(R.id.coupon_loading_fail_btn);
        ruleCoupon = (TextView) findViewById(R.id.coupon_rulecoupon);
        couponList = (ListView) findViewById(R.id.coupon_listview);
        coupon_uncoupon = (TextView) findViewById(R.id.coupon_uncoupon);
    }

    /**
     * @Title: initListener
     * @Description: 初始化UI中各个点击控件的地点击监听
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     * */
    public void initListener() {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCall != null) {
                    myCall.cancel();
                }
                if (myCall1 != null) {
                    myCall1.cancel();
                }
                finish();
            }
        });
        /**
         * 网络加载失败后重新加载按钮的点击监听
         * */
        coupon_loading_fail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCall != null) {
                    myCall.cancel();
                    initData();
                }
            }
        });
        /**
         * 卡券使用规则按钮点击监听
         * */
        ruleCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CouponActivity.this);
                TextView message = new TextView(CouponActivity.this);
                message.setTextSize(20);
                message.setPadding(15, 15, 15, 15);
                message.setTextColor(getResources().getColor(R.color.yellow));
                message.setText(getResources().getString(R.string.regulation_coupon));
                alertDialog.setView(message);
                alertDialog.create().show();
            }
        });
    }

}
