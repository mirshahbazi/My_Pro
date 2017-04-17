package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.OrderDetailsListViewAdapter;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.OrderFromDetail;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.customs.LodingDialog;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GsonUtil;

/**
 * @ClassName: OrderFormDetailActivity
 * @Description: 该Activity是订单详情页面，主要用来展示用户某个订单的详细信息。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */

public class OrderFormDetailActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView commonTopBackIv;
    protected TextView activityOrderformDetailTime;
    protected TextView activityOrderformDetailMoneysum;
    protected Button activityOrderformDetailBtnPay;
    protected TextView commonTopText;
    protected TextView activityOrderformDetailOrderformmun;
    protected TextView activityOrderformDetailScanName;
    protected TextView activityOrderformDetailScanGuige;
    protected TextView activityOrderformDetailScanDanjia;
    protected TextView activityOrderformDetailScanQiding;
    protected ListView activityOrderformDetailListview;
    protected TextView activityOrderformDetailTimetext;
    protected TextView activityOrderformDetailDaifukuantext;
    protected TextView activityOrderformDetailPaystatetext;
    protected TextView activityPayDetailAllgoodsmoney;
    protected TextView activityPayDetailPrivilege;
    protected TextView activityPayDetailDeliverymoney;
    protected TextView activityPayDetailPayway;
    protected TextView activityPayDetailDeliverymode;
    LinearLayout activity_orderform_detail_loading_fail_ll;
    Button activity_orderform_detail_loading_fail_btn;
    BigDecimal bm;
    protected RelativeLayout rl2;
    private LodingDialog mLodingDialog;
    Intent intent;
    int orderId;
    OrderFromDetail orderFromDetail1 = null;
    OrderDetailsListViewAdapter adapter;
    /**
     * 卡券使用说明，0不可用，1为可用
     * */
    private int COUPON_NOTUSE=0;
    private int COUPON_USE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_orderform_detail);
        initView();
        intent = getIntent();
        orderId = intent.getExtras().getInt("orderId");
        mLodingDialog = new LodingDialog(this);
        mLodingDialog.setmTextView(getResources().getString(R.string.dialog_loading));
        mLodingDialog.show();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * @Title: initData
     * @date 创建时间 2016/11/21
     * @author: 李鹏鹏
     * @Description: 初始化数据（当前页面的订单详情）
     * @version: V1.0
     */
    private void initData() {
        /**
         * 首先设置网络加载失败后的UI不可见
         * */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity_orderform_detail_loading_fail_ll.setVisibility(View.GONE);
            }
        });
        /**
         * 开启线程加载订单详细信息
         * */
        new Thread() {
            @Override
            public void run() {
                FormBody body = new FormBody.Builder()
                        .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                        .add("orderId", orderId + "")
                        .build();
                try {
                    Log.i("test", "orderId:" + orderId);
                    Log.i("test", "appToken:" + WizarPosApp.getmPersonalInfo().getAppToken());
                    Response response = OkHttpUtil.postResponseFormServer(Url.BASE_URL + "order/orderDetail", body);
                    String jsonStr = response.body().string();
                    Log.i("test", jsonStr);
                    final OrderFromDetail orderFromDetail = GsonUtil.parseJsonToBean(jsonStr, OrderFromDetail.class);
                    orderFromDetail1 = orderFromDetail;
                    Log.i("test", (orderFromDetail1 == null) + "---------");
                    /**
                     * 根据从网络上加载到的数据解析后的结果中的订单状态字段选择性加载UI
                     * */
                    if (orderFromDetail != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("test", "订单状态：" + orderFromDetail.getResult().getStatus());
                                Log.i("test", (orderFromDetail1 == null) + "---------");
                                activityOrderformDetailOrderformmun.setText(orderFromDetail.getResult().getOrderNo() + "");
                                activityPayDetailAllgoodsmoney.setText(orderFromDetail.getResult().getMoney() + "");
                                activityPayDetailPrivilege.setText(orderFromDetail.getResult().getCoin() + "");
                                activityPayDetailDeliverymoney.setText(orderFromDetail.getResult().getSendMoney() + "");
                                activityOrderformDetailTime.setText(orderFromDetail.getResult().getCreateTime());
                                activityOrderformDetailMoneysum.setText("￥ " + orderFromDetail.getResult().getPayMoney());
                                if (orderFromDetail.getResult().getPayId() == 1) {
                                    activityPayDetailPayway.setText(getResources().getString(R.string.payDetails_weiChatPay));
                                }
                                if (orderFromDetail.getResult().getStatus() == 2) {
                                    /**
                                     * 已支付订单
                                     * */
                                    activityOrderformDetailDaifukuantext.setText(getResources().getString(R.string.common_payall));
                                    activityOrderformDetailBtnPay.setText(getResources().getString(R.string.common_backpage_first));
                                    activityOrderformDetailPaystatetext.setText(getResources().getString(R.string.common_pay_already));
                                    activityOrderformDetailBtnPay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(OrderFormDetailActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                } else if (orderFromDetail.getResult().getStatus() == 0) {
                                    /**
                                     * 未支付订单
                                     * */
                                    activityOrderformDetailPaystatetext.setText(getResources().getString(R.string.common_pay_wait));
                                    activityOrderformDetailBtnPay.setText(getResources().getString(R.string.common_pay_go));
                                    final List<User_CartInfo> list = new ArrayList<>();
                                    for (int i = 0; i < orderFromDetail.getResult().getDetails().size(); i++) {
                                        User_CartInfo user_cartInfo = new User_CartInfo();
                                        user_cartInfo.setGoodsname(orderFromDetail.getResult().getDetails().get(i).getGoodsName());
                                        Log.i("test",i+orderFromDetail.getResult().getDetails().get(i).getGoodsName());
                                        user_cartInfo.setStname(orderFromDetail.getResult().getDetails().get(i).getStname());
                                        user_cartInfo.setNum(orderFromDetail.getResult().getDetails().get(i).getNumber());
                                        user_cartInfo.setPrice(orderFromDetail.getResult().getDetails().get(i).getPrice());
                                        Log.i("test",orderFromDetail.getResult().getDetails().get(i).getDisDyq()+"-----");
                                        Log.i("test",orderFromDetail.getResult().getDetails().get(i).getDisGwq()+"-----");
                                        if(orderFromDetail.getResult().getDetails().get(i).getDisDyq()){
                                            user_cartInfo.setDisDyq(COUPON_NOTUSE);
                                        }else{
                                            user_cartInfo.setDisDyq(COUPON_USE);
                                        }
                                        if(orderFromDetail.getResult().getDetails().get(i).getDisGwq()){
                                            user_cartInfo.setDisGwq(COUPON_NOTUSE);
                                        }else{
                                            user_cartInfo.setDisGwq(COUPON_USE);
                                        }
                                        list.add(user_cartInfo);
                                    }
                                    for (int i=0;i<list.size();i++){
                                        Log.i("test",list.get(i).getDisDyq()+"");
                                        Log.i("test",list.get(i).getDisGwq()+"");
                                    }
                                    /**
                                     * 前往支付页面支付订单
                                     * */
                                    activityOrderformDetailBtnPay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(OrderFormDetailActivity.this, SubmitOrderFormActivity.class);
                                            intent.putParcelableArrayListExtra("payGoodsList", (ArrayList<User_CartInfo>) list);
                                            Log.i("test", "当前订单的商品数量是：" + list.size());
                                            Log.i("test", "当前订单需付款总额是：" + orderFromDetail.getResult().getPayMoney());
                                            bm = new BigDecimal(orderFromDetail.getResult().getMoney());
                                            intent.putExtra("totalMoney", bm);
                                            intent.putExtra("isPay", false);
                                            intent.putExtra("OrderFromON", orderFromDetail.getResult().getOrderNo());
                                            intent.putExtra("OrderFromID",orderFromDetail.getResult().getOrderId());
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    /**
                                     * 已收货订单更新UI
                                     * */
                                    activityOrderformDetailPaystatetext.setText(getResources().getString(R.string.person_alreadyGet));
                                    activityOrderformDetailDaifukuantext.setText(getResources().getString(R.string.common_payall));
                                    activityOrderformDetailBtnPay.setText(getResources().getString(R.string.common_backpage_first));
                                    activityOrderformDetailBtnPay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(OrderFormDetailActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                                if (orderFromDetail.getResult().getSwId() == 1) {
                                    activityPayDetailDeliverymode.setText(getResources().getString(R.string.payDetails_linjiaDelivery));
                                }
                                adapter = new OrderDetailsListViewAdapter(OrderFormDetailActivity.this, orderFromDetail.getResult().getDetails());
                                activityOrderformDetailListview.setAdapter(adapter);
                                mLodingDialog.dismiss();
                            }
                        });
                    } else {
                        Log.i("test", "解析错误");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mLodingDialog.dismiss();
                                activity_orderform_detail_loading_fail_ll.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                } catch (IOException e) {
                    Log.i("test", "请求异常！");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLodingDialog.dismiss();
                            activity_orderform_detail_loading_fail_ll.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        }.start();
    }

    /**
     * @Title: onClick
     * @date 创建时间 2016/11/21
     * @author: 李鹏鹏
     * @Description: 设置该页面中的各个按钮的监听事件
     * @version: V1.0
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.activity_orderform_detail_top_back_iv) {
            finish();
        } else if (view.getId() == R.id.activity_orderform_detail_loading_fail_btn) {
            initData();
        }
    }

    /**
     * @Title: initView
     * @date 创建时间 2016/11/21
     * @author: 李鹏鹏
     * @Description: 初始化View
     * @version: V1.0
     */
    private void initView() {
        bm = new BigDecimal(0.0);
        commonTopBackIv = (ImageView) findViewById(R.id.activity_orderform_detail_top_back_iv);
        commonTopBackIv.setOnClickListener(OrderFormDetailActivity.this);
        activityOrderformDetailTime = (TextView) findViewById(R.id.activity_orderform_detail_time);
        activityOrderformDetailMoneysum = (TextView) findViewById(R.id.activity_orderform_detail_moneysum);
        commonTopText = (TextView) findViewById(R.id.activity_orderform_detail_top_text);
        activityOrderformDetailOrderformmun = (TextView) findViewById(R.id.activity_orderform_detail_orderformmun);
        activityOrderformDetailScanName = (TextView) findViewById(R.id.activity_orderform_detail_scan_name);
        activityOrderformDetailScanGuige = (TextView) findViewById(R.id.activity_orderform_detail_scan_guige);
        activityOrderformDetailScanDanjia = (TextView) findViewById(R.id.activity_orderform_detail_scan_danjia);
        activityOrderformDetailScanQiding = (TextView) findViewById(R.id.activity_orderform_detail_scan_qiding);
        activityOrderformDetailListview = (ListView) findViewById(R.id.activity_orderform_detail_listview);
        activityOrderformDetailTimetext = (TextView) findViewById(R.id.activity_orderform_detail_timetext);
        activityOrderformDetailDaifukuantext = (TextView) findViewById(R.id.activity_orderform_detail_daifukuantext);
        activityOrderformDetailPaystatetext = (TextView) findViewById(R.id.activity_orderform_detail_paystatetext);
        activityPayDetailAllgoodsmoney = (TextView) findViewById(R.id.activity_pay_detail_allgoodsmoney);
        activityPayDetailPrivilege = (TextView) findViewById(R.id.activity_pay_detail_privilege);
        activityPayDetailDeliverymoney = (TextView) findViewById(R.id.activity_pay_detail_deliverymoney);
        activityPayDetailPayway = (TextView) findViewById(R.id.activity_pay_detail_payway);
        activityPayDetailDeliverymode = (TextView) findViewById(R.id.activity_pay_detail_deliverymode);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
        activityOrderformDetailBtnPay = (Button) findViewById(R.id.activity_orderform_detail_btn_pay);
        activityOrderformDetailBtnPay.setOnClickListener(OrderFormDetailActivity.this);
        activity_orderform_detail_loading_fail_ll = (LinearLayout) findViewById(R.id.activity_orderform_detail_loading_fail_ll);
        activity_orderform_detail_loading_fail_btn = (Button) findViewById(R.id.activity_orderform_detail_loading_fail_btn);
        activity_orderform_detail_loading_fail_btn.setOnClickListener(this);
    }
}
