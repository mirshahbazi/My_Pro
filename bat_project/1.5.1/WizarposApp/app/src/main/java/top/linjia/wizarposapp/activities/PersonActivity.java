package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.OrderListBean;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;
import top.linjia.wizarposapp.view.fragment.TitleFragment;

/**
 * @ClassName: PersonActivity
 * @Description: 该Activity是掌柜中心页面，用来展示用户所有订单状态以及查看购物券，收货地址信息。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class PersonActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView commonTopBackIv;
    protected TextView activityPersonUnpay;
    protected LinearLayout activityPersonLlUnpay;
    protected TextView activityPersonUngetgoods;
    protected LinearLayout activityPersonLlUngetgoods;
    protected TextView activityPersonGetgoods;
    protected LinearLayout activityPersonLlGetgoods;
    protected TextView activityPersonMyorder;
    protected LinearLayout activityPersonLlMyorder;
    protected RelativeLayout activity_person_location_ll, activity_person_giftcart_ll;
    protected TextView verison;
    protected Call call = null;
    private long mPressedTime = 0;
    int unfinishOrder = 0;
    int finishOrder = 0;
    int getOrder = 0;
    int orderTotal = 0;
    String versionName = "";
    LinearLayout mPersonalVersionLiner;
    private TitleFragment mPersonalTitleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_person);
        initView();
        versionName = getVersion();
        initData();

    }

    /**
     * @Title: onClick
     * @Description: 设置该页面中各个按钮的监听事件
     * @param: View
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    public void onClick(View view) {
        Intent intent = null;
        if (view.getId() == R.id.activity_person_top_back_iv) {
            finish();
        } else if (view.getId() == R.id.activity_person_ll_unpay) {
            Intent myOrderFormActivity = new Intent(this, MyOrderFormActivity.class);
            myOrderFormActivity.putExtra("orderStatus", 0);
            startActivity(myOrderFormActivity);
        } else if (view.getId() == R.id.activity_person_ll_ungetgoods) {
            Intent unGetGoods = new Intent(this, MyOrderFormActivity.class);
            unGetGoods.putExtra("orderStatus", 2);
            startActivity(unGetGoods);
        } else if (view.getId() == R.id.activity_person_ll_getgoods) {
            Intent getGoods = new Intent(this, MyOrderFormActivity.class);
            getGoods.putExtra("orderStatus", 9);
            startActivity(getGoods);
        } else if (view.getId() == R.id.activity_person_ll_myorder) {
            Intent myOrderFormIntent = new Intent(this, MyOrderFormActivity.class);
            myOrderFormIntent.putExtra("orderStatus", 0);
            startActivity(myOrderFormIntent);
        } else if (view.getId() == R.id.activity_person_location_ll) {
            intent = new Intent(this, PersonAddressActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.activity_person_giftcart_ll) {
            intent = new Intent(this, CouponActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.personal_version_liner) {
            intent = new Intent(this, VersionInfoActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Title: initView
     * Description:  初始化View
     * Created by 邻家新锐 on 2016/11/21 10:56
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    private void initView() {
        activityPersonUnpay = (TextView) findViewById(R.id.activity_person_unpay);
        activityPersonLlUnpay = (LinearLayout) findViewById(R.id.activity_person_ll_unpay);
        activityPersonLlUnpay.setOnClickListener(PersonActivity.this);
        activityPersonUngetgoods = (TextView) findViewById(R.id.activity_person_ungetgoods);
        activityPersonLlUngetgoods = (LinearLayout) findViewById(R.id.activity_person_ll_ungetgoods);
        activityPersonLlUngetgoods.setOnClickListener(PersonActivity.this);
        activityPersonGetgoods = (TextView) findViewById(R.id.activity_person_getgoods);
        activityPersonLlGetgoods = (LinearLayout) findViewById(R.id.activity_person_ll_getgoods);
        activityPersonLlGetgoods.setOnClickListener(PersonActivity.this);
        activityPersonMyorder = (TextView) findViewById(R.id.activity_person_myorder);
        activityPersonLlMyorder = (LinearLayout) findViewById(R.id.activity_person_ll_myorder);
        activityPersonLlMyorder.setOnClickListener(PersonActivity.this);
        activity_person_location_ll = (RelativeLayout) findViewById(R.id.activity_person_location_ll);
        activity_person_location_ll.setOnClickListener(this);
        activity_person_giftcart_ll = (RelativeLayout) findViewById(R.id.activity_person_giftcart_ll);
        activity_person_giftcart_ll.setOnClickListener(this);
        mPersonalVersionLiner = (LinearLayout) findViewById(R.id.personal_version_liner);
        mPersonalVersionLiner.setOnClickListener(this);
        verison = (TextView) findViewById(R.id.version);

        mPersonalTitleFragment = (TitleFragment) getFragmentManager().findFragmentById(R.id.personal_title_fragment);
        mPersonalTitleFragment.thisWhereActivity(TitleFragment.PERSIONAL);

    }

    /**
     * @Title: initData
     * @Description: 初始化掌柜中心页面所需网络数据
     * @param: View
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void initData() {
        /**
         * 首先设置支付状态View为显示状态
         * */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityPersonUnpay.setVisibility(View.VISIBLE);
                activityPersonUngetgoods.setVisibility(View.VISIBLE);
                activityPersonGetgoods.setVisibility(View.VISIBLE);
                activityPersonMyorder.setVisibility(View.VISIBLE);
                verison.setText(getResources().getString(R.string.versioncode) + versionName);
            }
        });
        /**
         * 开启线程从网络请求掌柜信息数据
         * */
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                FormBody formBody = new FormBody.Builder()
                        .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                        .add("pageNumber", "1")
                        .add("pageSize", "100")
                        .add("queryAll", "1")
                        .build();
                call = OkHttpUtil.postCallFormServer(Url.BASE_URL + "order/orderList", formBody);
                try {
                    Response response = call.execute();
                    String jsonStr = response.body().string();
                    OrderListBean orderListBean = GsonUtil.parseJsonToBean(jsonStr, OrderListBean.class);
                    /**
                     * 从网络请求到的数据中获取订单列表然后根据订单状态字段减三中状态订单分离添加到指定List中
                     * */
                    if (orderListBean != null) {
                        if (orderListBean.getResult().getList().size() != 0) {
                            for (int i = 0; i < orderListBean.getResult().getList().size(); i++) {
                                if (orderListBean.getResult().getList().get(i).getStatus() == 2 || orderListBean.getResult().getList().get(i).getStatus() == 1) {
                                    finishOrder += 1;
                                    orderTotal += 1;
                                } else if (orderListBean.getResult().getList().get(i).getStatus() == 0) {
                                    unfinishOrder += 1;
                                    orderTotal += 1;
                                } else if (orderListBean.getResult().getList().get(i).getStatus() == 9) {
                                    getOrder += 1;
                                    orderTotal += 1;
                                }
                            }
                            /**
                             * 根据订单状态选择性显示Ui界面
                             * */
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (unfinishOrder == 0) {
                                        activityPersonUnpay.setVisibility(View.GONE);
                                    }
                                    if (finishOrder == 0) {
                                        activityPersonUngetgoods.setVisibility(View.GONE);
                                    }
                                    if (getOrder == 0) {
                                        activityPersonGetgoods.setVisibility(View.GONE);
                                    }
                                    if (orderTotal == 0) {
                                        activityPersonMyorder.setVisibility(View.GONE);
                                    }
                                    activityPersonUnpay.setText(unfinishOrder + "");
                                    activityPersonUngetgoods.setText(finishOrder + "");
                                    activityPersonGetgoods.setText(getOrder + "");
                                    activityPersonMyorder.setText(orderTotal + "");
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activityPersonUnpay.setVisibility(View.GONE);
                                    activityPersonUngetgoods.setVisibility(View.GONE);
                                    activityPersonGetgoods.setVisibility(View.GONE);
                                    activityPersonMyorder.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    Log.i("test", "网络错误");
                }
            }
        });
    }

    /**
     * @Title: onRestart
     * @Description: Activity重新可见后刷新数据
     * @param: View
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    protected void onRestart() {
        unfinishOrder = 0;
        finishOrder = 0;
        getOrder = 0;
        orderTotal = 0;
        initData();
        super.onRestart();
    }

    /**
     * @Title: getVersion
     * @Description: 获取当前应用的版本号
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public String getVersion() {
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title: onBackPressed
     * @Description: 双击返回键是做出退出操作
     * @param:
     * @Date: 2016/12/19
     * @author: 陈文豪
     */
    @Override
    public void onBackPressed() {
        /**
         * 做双击退出的计算
         */
        long mNowTime = System.currentTimeMillis();
        if ((mNowTime - mPressedTime) > 2000) {
            MyToast.showToast(getResources().getString(R.string.exit_hint));
            mPressedTime = mNowTime;
        } else {;
            if (call != null) {
                call.cancel();
            }
            this.finish();
//            android.os.Process.killProcess(android.os.Process.myPid());//杀死当前进程
        }
    }
}
