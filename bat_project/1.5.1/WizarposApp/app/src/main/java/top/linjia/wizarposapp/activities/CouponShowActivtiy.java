package top.linjia.wizarposapp.activities;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.CouponShowGridAdapter;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.CouponShowBean;
import top.linjia.wizarposapp.customs.MyGridView;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @ClassName: CouponShowActivtiy
 * @Description: 更多卡券展示页面。
 * @Author: 李鹏鹏
 * @Data: 2017/01/05
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class CouponShowActivtiy extends AppCompatActivity implements View.OnClickListener {
    private ImageView backImage;
    private TextView nearOutCoupon;
    private TextView usingCoupon;
    private TextView cantCoupon;
    private LinearLayout nearOutLine;
    private LinearLayout usingLine;
    private LinearLayout cantUseLine;
    private MyGridView gvNearOutData;
    private MyGridView gvUsing;
    private MyGridView gvCantUse;
    private List<CouponShowBean.ResultBean.CanNotUsedListBean> canNotUsedListBeen;
    private List<CouponShowBean.ResultBean.CanUsedListBean> canUsedListBeen;
    private List<CouponShowBean.ResultBean.ExpireListBean> expireListBeen;
    private Handler handler;
    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_show_activtiy);
        orderId = getIntent().getExtras().getInt("orderId");
        initView();
        initData();
        initHandler();
    }

    /**
     * @Title: initHandler
     * @Description: 刷新UI
     * @Params:
     * @Data: 2017/1/8 16:10
     * @Author: 李鹏鹏
     */
    public void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                CouponShowBean couponShowBean = (CouponShowBean) msg.obj;
                canNotUsedListBeen = couponShowBean.getResult().getCanNotUsedList();
                canUsedListBeen = couponShowBean.getResult().getCanUsedList();
                expireListBeen = couponShowBean.getResult().getExpireList();
                CouponShowGridAdapter expireAdapter = new CouponShowGridAdapter(CouponShowActivtiy.this, null, null, expireListBeen);
                CouponShowGridAdapter canuseAdapter = new CouponShowGridAdapter(CouponShowActivtiy.this, null, canUsedListBeen, null);
                CouponShowGridAdapter cannotAdapter = new CouponShowGridAdapter(CouponShowActivtiy.this, canNotUsedListBeen, null, null);
                gvNearOutData.setAdapter(expireAdapter);
                gvCantUse.setAdapter(cannotAdapter);
                gvUsing.setAdapter(canuseAdapter);
                isEmptyCoupon(couponShowBean);
                super.handleMessage(msg);
            }
        };
    }

    /**
     * @Title: initView
     * @Description: 初始化View
     * @Params:
     * @Data: 2017/1/6 10:43
     * @Author: 李鹏鹏
     */
    public void initView() {
        backImage = (ImageView) findViewById(R.id.choice_coupon_back);
        backImage.setOnClickListener(this);
        nearOutCoupon = (TextView) findViewById(R.id.activity_coupon_show_nocoupon_nearoutdata);
        usingCoupon = (TextView) findViewById(R.id.activity_coupon_show_nocoupon_useble);
        cantCoupon = (TextView) findViewById(R.id.activity_coupon_show_nocoupon_cantuse);
        nearOutLine = (LinearLayout) findViewById(R.id.coupon_grid_nearoutable_ll);
        usingLine = (LinearLayout) findViewById(R.id.coupon_grid_useable_ll);
        cantUseLine = (LinearLayout) findViewById(R.id.coupon_grid_cantuse_ll);
        gvNearOutData = (MyGridView) findViewById(R.id.activity_grid_nearoutdata);
        gvUsing = (MyGridView) findViewById(R.id.activity_grid_useable);
        gvCantUse = (MyGridView) findViewById(R.id.activity_grid_cantuse);
    }

    /**
     * @Title: initData
     * @Description: 初始化卡券数据
     * @Params:
     * @Data: 2017/1/6 10:43
     * @Author: 李鹏鹏
     */
    public void initData() {
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                FormBody formBody;
                if (orderId == 0) {
                    formBody = new FormBody.Builder()
                            .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                            .build();
                } else {
                    formBody = new FormBody.Builder()
                            .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                            .add("orderId", String.valueOf(orderId))
                            .build();
                }
                try {
                    Response response = OkHttpUtil.postResponseFormServer(Url.BASE_URL + "cart/calcSingleUsedCouponsForCart", formBody);
                    String jsonStr = response.body().string();
                    Log.i("test", jsonStr);
                    CouponShowBean couponShowBean = GsonUtil.parseJsonToBean(jsonStr, CouponShowBean.class);
                    if (couponShowBean != null) {
                        Message msg = Message.obtain();
                        msg.obj = couponShowBean;
                        handler.sendMessage(msg);
                    } else {
                        MyToast.showToast(getResources().getString(R.string.data_loaging_fail));
                    }
                } catch (IOException e) {
                    MyToast.showToast(getResources().getString(R.string.net_wrong));
                }
            }
        });
    }

    /**
     * @Title: isEmptyCoupon
     * @Description: 判断某种类型的卡券是否为空，刷新Ui
     * @Params: couponShowBean
     * @Data: 2017/1/8 17:28
     * @Author: 李鹏鹏
     */
    public void isEmptyCoupon(final CouponShowBean couponShowBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (couponShowBean.getResult().getCanNotUsedList().size() == 0) {
                    cantUseLine.setVisibility(View.GONE);
                    cantCoupon.setVisibility(View.VISIBLE);
                }
                if (couponShowBean.getResult().getCanUsedList().size() == 0) {
                    usingLine.setVisibility(View.GONE);
                    usingCoupon.setVisibility(View.VISIBLE);
                }
                if (couponShowBean.getResult().getExpireList().size() == 0) {
                    nearOutLine.setVisibility(View.GONE);
                    nearOutCoupon.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
