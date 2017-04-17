package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SyncStatusObserver;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wizarpos.barcode.decode.DecodeResult;
import com.wizarpos.barcode.scanner.ScannerActivity;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.SubmitOrderFormAdapter;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.CouponBean;
import top.linjia.wizarposapp.beans.CreateOrderFormReturn;
import top.linjia.wizarposapp.beans.PayInfo;
import top.linjia.wizarposapp.beans.PayResultInfo;
import top.linjia.wizarposapp.beans.SubmitOrderFromInfo;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.customs.LodingDialog;
import top.linjia.wizarposapp.customs.SelectCouponDialog;
import top.linjia.wizarposapp.customs.SelectPayDialog;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.ConfigurationInfo;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.interfaces.InternetLoadingData;
import top.linjia.wizarposapp.utils.GetSystemInfoUtil;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

public class SubmitOrderFormActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int REQUEST_CODE = 1;
    private ImageView mActivitySubmitOrderFormTopBackIv;
    private TextView mActivitySubmitOrderFormTopText;
    private RelativeLayout mActivitySubmitOrderNavigation;
    private TextView mActivitySubmitOrderFormStoreText;
    private EditText mActivitySubmitOrderFormStore;
    private TextView mActivitySubmitOrderFormReceivegoodsmanText;
    private EditText mActivitySubmitOrderFormReceivegoodsman;
    private TextView mActivitySubmitOrderFormLinkmanphoneText;
    private EditText mActivitySubmitOrderFormLinkmanphone;
    private TextView mActivitySubmitOrderFormAddressText;
    private EditText mActivitySubmitOrderFormAddress;
    private TextView mActivitySubmitOrderFormRemarkText;
    private EditText mActivitySubmitOrderFormRemark;
    private TextView mCartScanName;
    private TextView mCartScanGuige;
    private TextView mCartScanDanjia;
    private TextView mCartScanNumber;
    private ListView mActivityCommitOrderformList;
    private Button mCommitPaymentBtn;
    private TextView mCommitPaymentMoney;
    private TextView mCommitOrderformMoneyRealpay;
    private TextView mCommitOrderFormFreightNumber;
    private TextView mCommitOrderFormFreight;
    private TextView mCommitOrderFormTotalmoneyNumber;
    private TextView mCommitOrderFormTotalmoney;
    private ArrayList<User_CartInfo> payGoodsList;
    private BigDecimal totalMoney;
    private SubmitOrderFormAdapter submitOrderFormAdapter;
    private DecimalFormat df2;
    private boolean isPay;
    private String OrderFromON;
    private PayInfo payInfo;
    private String codeString;
    private String payChancel;
    private ImageView mShopCoupon;
    private ImageView mOffsetCoupon;
    private SelectCouponDialog mSelectCoupon;
    private CouponBean mCouponBean;
    private ImageView mBackIv;
    private RelativeLayout mActivityCommitGoodsTitle;
    private View mHeaderView;
    private View mHeaderView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_submit_order_form);
        Intent intent = getIntent();
        payGoodsList = intent.getParcelableArrayListExtra("payGoodsList");
        totalMoney = (BigDecimal) intent.getSerializableExtra("totalMoney");
        isPay = intent.getBooleanExtra("isPay", false);
        OrderFromON = intent.getStringExtra("OrderFromON");//如果没有传入数据会返回null、
        initListView();
        initView();
    }



    private void initView() {
        mActivitySubmitOrderFormStore = (EditText) mHeaderView.findViewById(R.id.activity_submit_order_form_store);
        mActivitySubmitOrderFormReceivegoodsman = (EditText) mHeaderView.findViewById(R.id.activity_submit_order_form_receivegoodsman);
        mActivitySubmitOrderFormLinkmanphone = (EditText) mHeaderView.findViewById(R.id.activity_submit_order_form_linkmanphone);
        mActivitySubmitOrderFormAddress = (EditText) mHeaderView.findViewById(R.id.activity_submit_order_form_address);
        mActivitySubmitOrderFormRemark = (EditText) mHeaderView.findViewById(R.id.activity_submit_order_form_remark);

        mCommitPaymentBtn = (Button) findViewById(R.id.commit_payment_btn);
        mCommitPaymentMoney = (TextView) findViewById(R.id.commit_payment_money);
        mCommitOrderFormFreightNumber = (TextView) findViewById(R.id.commit_orderForm_freight_number);
        mCommitOrderFormTotalmoneyNumber = (TextView) findViewById(R.id.commit_orderForm_totalmoney_number);
        mShopCoupon = (ImageView) findViewById(R.id.submit_activity_shop_coupon);
        mOffsetCoupon = (ImageView) findViewById(R.id.submit_activity_offset_coupon);
        mBackIv = (ImageView) findViewById(R.id.activity_submit_order_form_top_back_iv);


        //设置初始化的值
        mActivitySubmitOrderFormStore.setText(WizarPosApp.getmPersonalInfo().getResult().getOrgan());
        mActivitySubmitOrderFormReceivegoodsman.setText(WizarPosApp.getmPersonalInfo().getResult().getUname());
        mActivitySubmitOrderFormLinkmanphone.setText(WizarPosApp.getmPersonalInfo().getResult().getPhone());
        mActivitySubmitOrderFormAddress.setText(WizarPosApp.getmPersonalInfo().getResult().getAddress());

        //配置该有的属性
        mActivitySubmitOrderFormStore.setFocusable(false);
        mActivitySubmitOrderFormReceivegoodsman.setFocusable(false);
        mActivitySubmitOrderFormLinkmanphone.setFocusable(false);
        mActivitySubmitOrderFormAddress.setFocusable(false);

        //配置onclick
        mShopCoupon.setOnClickListener(this);
        mOffsetCoupon.setOnClickListener(this);
        mBackIv.setOnClickListener(this);

        //设置初始化的价格
        df2 = new DecimalFormat("0.00");
        mCommitOrderFormTotalmoneyNumber.setText(df2.format(totalMoney));
        mCommitPaymentBtn.setOnClickListener(this);

        /**
         * 判断订单是否小于499
         */
        if (totalMoney.doubleValue() < 499) {
            mCommitOrderFormFreightNumber.setText(String.valueOf(ConfigurationInfo.FREIGHT_MONEY));
            totalMoney = totalMoney.add(new BigDecimal("9"));
        }



        mCommitPaymentMoney.setText(df2.format(totalMoney));


        payInfo = new PayInfo();


        //根据传来的数据不同 做出不同变化
        compatibilityInitView();

    }

    private void initListView() {
        mActivityCommitOrderformList = (ListView) findViewById(R.id.activity_commit_orderform_list);//展示提交订单中的商品的listview
        mActivityCommitGoodsTitle = (RelativeLayout) findViewById(R.id.activity_commit_goods_title);
        mHeaderView = View.inflate(this, R.layout.submit_userinfo_list_title, null);
        mHeaderView2 = View.inflate(this, R.layout.submit_userinfo_list_title2, null);
        mActivityCommitOrderformList.addHeaderView(mHeaderView);
        mActivityCommitOrderformList.addHeaderView(mHeaderView2);
        submitOrderFormAdapter = new SubmitOrderFormAdapter(payGoodsList, this);
        mActivityCommitOrderformList.setAdapter(submitOrderFormAdapter);
        mActivityCommitOrderformList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem >=1){
                    if(mActivityCommitGoodsTitle.getVisibility() != View.VISIBLE) {
                        mActivityCommitGoodsTitle.setVisibility(View.VISIBLE);
                    }
                }else if(firstVisibleItem < 1){
                    mActivityCommitGoodsTitle.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * 做多页面 跳转兼容的初始化方法
     */
    private void compatibilityInitView() {
        if (isPay) {
            mCommitPaymentBtn.setText(R.string.submitActivity_button_already_pay);
            mCommitPaymentBtn.setEnabled(false);
            mActivitySubmitOrderFormRemark.setEnabled(false);
        } else if (OrderFromON != null) {
            mActivitySubmitOrderFormRemark.setEnabled(false);
            mCommitPaymentBtn.setText(R.string.submitActivity_button_topay);
            mCommitPaymentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSelctctPay();
                    //如果已经生成订单 直接去进行支付
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_payment_btn:
                submit();
                break;

            case R.id.common_top_back_iv:
                finish();
                break;
            case R.id.submit_activity_shop_coupon:
                if(mSelectCoupon == null) {
                    mSelectCoupon = new SelectCouponDialog(this);
                }
                selectCoupon(mSelectCoupon.SHOP_COUPON);
                break;
            case R.id.submit_activity_offset_coupon:
                if(mSelectCoupon == null) {
                    mSelectCoupon = new SelectCouponDialog(this);
                }
                selectCoupon(mSelectCoupon.OFFSET_COUPON);
                break;
            case R.id.activity_submit_order_form_top_back_iv:
                this.onBackPressed();
                break;
        }
    }

    private void selectCoupon(String type) {

        mSelectCoupon.setCouponType(type);
        mSelectCoupon.setItemCouponID(new SelectCouponDialog.CouponInfo() {
            @Override
            public void getSelCouponInfoID(CouponBean.ResultBean.ListBean card) {

            }
        });
        mSelectCoupon.show();
    }

    private void submit() {
        // validate
        String store = mActivitySubmitOrderFormStore.getText().toString().trim();
        if (TextUtils.isEmpty(store)) {
            MyToast.showToast("店铺名称不能为空");
            return;
        }

        String receivegoodsman = mActivitySubmitOrderFormReceivegoodsman.getText().toString().trim();
        if (TextUtils.isEmpty(receivegoodsman)) {
            MyToast.showToast("收货人不能为空");
            return;
        }

        String linkmanphone = mActivitySubmitOrderFormLinkmanphone.getText().toString().trim();
        if (TextUtils.isEmpty(linkmanphone)) {
            MyToast.showToast("电话号码不能为空");
            return;
        }

        String address = mActivitySubmitOrderFormAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            MyToast.showToast("地址不能为空");
            return;
        }

        // TODO validate success, do something
        commitOrderForm();
    }


    private void commitOrderForm() {
        String storeName = mActivitySubmitOrderFormStore.getText().toString().trim();
        String consignee = mActivitySubmitOrderFormReceivegoodsman.getText().toString().trim();
        String phone = mActivitySubmitOrderFormLinkmanphone.getText().toString().trim();
        String address = mActivitySubmitOrderFormAddress.getText().toString().trim();
        ArrayList<SubmitOrderFromInfo.OrderBean.DetailsBean> submitGoods = new ArrayList<>();
        filterIdAndNumber(payGoodsList, submitGoods);
        SubmitOrderFromInfo.OrderBean OrderFormPersonalInfo = new SubmitOrderFromInfo.OrderBean(storeName, consignee, phone, address, submitGoods);
        final String result = GsonUtil.beanToJson(new SubmitOrderFromInfo(WizarPosApp.getmPersonalInfo().getAppToken(), OrderFormPersonalInfo));
        final LodingDialog lodingDialog = new LodingDialog(this);
        lodingDialog.setmTextView("提交订单中，请稍候。。。");
        lodingDialog.show();
        if(result == null){
            MyToast.showToast("参数错误╮(╯▽╰)╭");
            return;
        }
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {//创建订单的线程
                try {
                    CreateOrderFormReturn createOrderFormReturn = OkHttpUtil.postBeanFormServer(Url.CREATE_ORDERFORM, result,
                            CreateOrderFormReturn.class);//返回的订单详情列表
                    if (createOrderFormReturn != null) {
                        OrderFromON = createOrderFormReturn.getResult().getOrderNo();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lodingDialog.dismiss();
                                MyToast.showToast("订单创建成功！");
                                deleteCartGoods();
                                showSelctctPay();
                                compatibilityInitView();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyToast.showToast("订单创建失败！");
                                lodingDialog.dismiss();
                            }
                        });
                    }
                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyToast.showToast("订单创建失败！");
                            lodingDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    /**
     * 订单创建成功 删除购物车内的信息
     */
    private void deleteCartGoods() {
        UserDao userDao = new UserDao(this);
        userDao.deleteListFromCart(payGoodsList);
    }

    private void showSelctctPay() {
        SelectPayDialog selectPayDialog = new SelectPayDialog(this);
        selectPayDialog.setOnItemClick(new SelectPayDialog.ItemClick() {
            @Override
            public void payClick(String code) {
                payChancel = code;
                scanCode();
            }
        });
        selectPayDialog.show();
    }

    final String DECODE_FORMAT = "decodeformat";
    final int REQUEST = 1;

    private void scanCode() {
        Intent intent = new Intent(this, ScannerActivity.class);
        intent.putExtra(DECODE_FORMAT, ConfigurationInfo.CODE_MODULE＿TOTAL);
        startActivityForResult(intent, REQUEST);
    }


    /**
     * 二维码解析完成返回数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                codeString = extras.getString(DecodeResult.RESULT);
//                String resultFormat = extras.getString(DecodeResult.RESULT_FORMAT);//获取解码类型 如QE_CODE
                Toast.makeText(this, "扫码成功！", Toast.LENGTH_SHORT).show();
                toConnectionPay();
            }
        }else{

        }
    }


    /**
     * 向后台请求进行支付，支付成功后进行跳转提示
     */
    private void toConnectionPay() {

        final LodingDialog lodingDialogPay = new LodingDialog(this);
        lodingDialogPay.setmTextView("支付中。。。");
        lodingDialogPay.setBreack(false);
        lodingDialogPay.show();
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                payInfo.setAuth_code(codeString);
                payInfo.setOut_trade_no(OrderFromON);
                payInfo.setPay_channel(payChancel);
                payInfo.setTerminal_no(GetSystemInfoUtil.getLocalSN());
                String json = GsonUtil.beanToJson(payInfo);
                PayResultInfo payResultInfo = null;
                try {
                    payResultInfo = OkHttpUtil.postBeanFormServer(Url.TO_PAY_URL, json, PayResultInfo.class);
                } catch (IOException e) {

                }
                if (payResultInfo != null) {
                    System.out.println(payResultInfo);
                    Intent intent = new Intent(SubmitOrderFormActivity.this, PayResultActivity.class);
                    System.out.println("payResultInfo:" + payResultInfo);
                    intent.putExtra("pay_Result", payResultInfo);
                    startActivity(intent);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lodingDialogPay.dismiss();
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lodingDialogPay.dismiss();
                            MyToast.showToast("未知错误");

                        }
                    });
                }

            }
        });
    }

    private void filterIdAndNumber(ArrayList<User_CartInfo> payGoodsList, ArrayList<SubmitOrderFromInfo.OrderBean.DetailsBean> submitGoods) {
        for (int i = 0; i < payGoodsList.size(); i++) {
            submitGoods.add(new SubmitOrderFromInfo.OrderBean.DetailsBean(payGoodsList.get(i).getGoodsId(), payGoodsList.get(i).getNum()));
        }
    }
}
