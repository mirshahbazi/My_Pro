package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.OrderFormGoodsList;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.CouponBean;
import top.linjia.wizarposapp.beans.CouponVerificationBean;
import top.linjia.wizarposapp.beans.CreateOrderFormReturn;
import top.linjia.wizarposapp.beans.OrderForm;
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
import top.linjia.wizarposapp.utils.GetSystemInfoUtil;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;
import top.linjia.wizarposapp.utils.couponutils.ComputerCouponHelper;
/**
 * Created by 河南巧脉信息技术 on 2016/12/22 18:47
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 *
 * 提交订单的activity
 * 负责显示卡券 选取卡券 请求卡券的list列表
 */

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
    private TextView mCommitOrderformMoneyRealpay;
    private TextView mCommitOrderFormFreight;
    private TextView mCommitOrderFormTotalmoney;
    private TextView mShowShopCoupon;
    private TextView mShowOffsetCoupon;

    private String className = this.getClass().getName();


    private ArrayList<User_CartInfo> payGoodsList;
    private BigDecimal totalMoney;//商品总价值
    private BigDecimal everythingPay;
    private TextView mCommitOrderFormTotalmoneyNumber;//商品总价textview
    private TextView mCommitPaymentMoney;//商品实付
    private TextView mCommitOrderFormFreightNumber;//运费

    private OrderFormGoodsList submitOrderFormAdapter;//适配器

    CouponVerificationBean couponVerificationBean = null;//卡券验证返回结果

    private List<CouponBean.ResultBean.ListBean> shopList;
    private List<CouponBean.ResultBean.ListBean> offsetList;
    private List<CouponBean.ResultBean.ListBean> favorableList;

    {
        shopList = new ArrayList<>();
        offsetList = new ArrayList<>();
    }

    private int stateCode;


    private DecimalFormat df2;
    private boolean isPay;
    private String OrderFromON;
    private int OrderFromID;
    private PayInfo payInfo;
    private String codeString;
    private String payChancel;
    private ImageView mShopCoupon;
    private ImageView mOffsetCoupon;
    private SelectCouponDialog mSelectCoupon;
    private CouponBean mCouponBean;
    private ImageView mBackIv;
    private LinearLayout mActivityCommitGoodsTitle;
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
        OrderFromID = intent.getIntExtra("OrderFromID",0);
        initCouponData();
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


        mBackIv.setOnClickListener(this);

        //设置初始化的价格
        df2 = new DecimalFormat("0.00");
        mCommitOrderFormTotalmoneyNumber.setText(df2.format(totalMoney));
        mCommitPaymentBtn.setOnClickListener(this);


        payInfo = new PayInfo();


        //根据传来的数据不同 做出不同变化
        compatibilityInitView();

    }

    private void initCouponData() {
        final LodingDialog lodingDialog = new LodingDialog(this);
        lodingDialog.setmTextView("加载卡券中。。。");
        lodingDialog.setCancelable(false);
        lodingDialog.show();
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {

                    @Override
                    public void run() {
                        FormBody.Builder builder = new FormBody.Builder();
                        /**
                         *  {"appToken":"0d6dae266a8e4a378bb5c1107e9c432d","couponStatus":1,"prefix":"d" }
                         "appToken"           :  必填
                         "couponStatus"    :  查询状态："0"查询所有（不填默认为0）,"1"查询可用券,"2"查询已使用,"3"查询已过期
                         "prefix"                   :查询卡券的类别："d" 抵扣券  "g"购物券
                         */
                        builder.add("appToken", WizarPosApp.getmPersonalInfo().getAppToken());
                        builder.add("couponStatus", "1");//查询模式
                        builder.add("pageSize","1000");
                        builder.add("pageNumber","1");
                        FormBody build = builder.build();
                        try {
                            mCouponBean = OkHttpUtil.postBeanFormServer(Url.REQUEST_COUPON_DATA_URL, build, CouponBean.class);
                            if (mCouponBean != null) {
                                stateCode = ComputerCouponHelper.parse2Coupon(mCouponBean.getResult().getList(), shopList, offsetList);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lodingDialog.dismiss();
                                        MyToast.showToast("加载成功");
                                        Log.d(className,"stateCode:"+stateCode);
                                        initListView();
                                        initView();
                                        relyStateLayout(stateCode);
                                    }
                                });
                            } else {
                                requestFail("返回值为空");
                            }
                        } catch (IOException e) {
                            requestFail("网络访问出错");
                        }


                    }

                    private void requestFail(final String msg) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lodingDialog.dismiss();
                                initListView();
                                initView();
                                MyToast.showToast(msg + "╮(╯▽╰)╭");
                            }
                        });
                    }

                });
            }
        });
    }

    /**
     * 没有价格就返回0
     *
     * @param stateCode
     * @param couponList
     * @return
     */
    public BigDecimal selectCouponShow(int stateCode, List<CouponBean.ResultBean.ListBean> couponList,String type) {

        BigDecimal bd = new BigDecimal("0");
        switch (stateCode) {
            case ComputerCouponHelper.TOTAL_HAVE:
                if(type.equals("g")) {
                    bd = showShopCoupon(couponList);
                }else {
                    bd = showOffsetCoupon(couponList);
                }
                break;
            case ComputerCouponHelper.OFFSET_NOT:
                bd = showShopCoupon(couponList);
                break;
            case ComputerCouponHelper.SHOP_NOT:
                bd = showOffsetCoupon(couponList);
                break;
            case ComputerCouponHelper.TOTAL_NOT:
                break;
        }
        return bd;
    }

    private BigDecimal showCouponInfo(List<CouponBean.ResultBean.ListBean> couponList) {
        BigDecimal bd = new BigDecimal("0");

        for (int i = 0; i < couponList.size(); i++) {
            bd = bd.add(new BigDecimal(couponList.get(i).getPoint()));
        }

        return bd;
    }

    private BigDecimal showShopCoupon(List<CouponBean.ResultBean.ListBean> couponList) {
        String str;
        BigDecimal bd;
        if (couponList != null) {
            bd = showCouponInfo(couponList);
            str = "(选择" + couponList.size() + "张购物券，" + "共计优惠" +
                    bd + "元）";
        } else {
            str = "(暂时无可用购物券)";
            bd = new BigDecimal("0");
        }
        mShowShopCoupon.setText(str);
        return bd;
    }

    private BigDecimal showOffsetCoupon(List<CouponBean.ResultBean.ListBean> couponList) {
        String str;
        BigDecimal bd;
        if (couponList != null) {
            bd = showCouponInfo(couponList);
            str = "(选择" + couponList.size() + "张抵用券，" + "共计优惠" +
                    bd + "元）";
        } else {
            str = "(选择0张抵用券，共计优惠0元）";
            bd = new BigDecimal("0");
        }
        mShowOffsetCoupon.setText(str);
        return bd;
    }


    /**
     * 依赖状态嘛做出布局响应
     *
     * @param code
     */
    public void relyStateLayout(int code) {

        List<CouponBean.ResultBean.ListBean> listBeen = ComputerCouponHelper.computerUsableCoupon(shopList,
                ComputerCouponHelper.COUPON_SHOP,
                payGoodsList, stateCode, submitOrderFormAdapter.getmCountMoney());

        List<CouponBean.ResultBean.ListBean> listBeen1 = ComputerCouponHelper.computerUsableCoupon(offsetList, ComputerCouponHelper.COUPON_OFFSET,
                payGoodsList, stateCode, submitOrderFormAdapter.getmCountMoney());
        switch (code) {
            case ComputerCouponHelper.TOTAL_HAVE:

                if(listBeen == null){
                    mShopCoupon.setEnabled(false);
                }
                if(listBeen1 == null){
                    mOffsetCoupon.setEnabled(false);
                }else if(listBeen1.size() != 0){
                    mShowOffsetCoupon.setText("(选择0张抵用券，共计优惠0元）");
                }
                break;
            case ComputerCouponHelper.OFFSET_NOT:
                mOffsetCoupon.setEnabled(false);
                if(listBeen == null){
                    mShopCoupon.setEnabled(false);
                }
                break;
            case ComputerCouponHelper.SHOP_NOT:
                mShopCoupon.setEnabled(false);
                if(listBeen1 == null){
                    mShopCoupon.setEnabled(false);
                }
                break;
            case ComputerCouponHelper.TOTAL_NOT:
                mOffsetCoupon.setEnabled(false);
                mShopCoupon.setEnabled(false);
                break;
        }
    }

    private void initListView() {
        mActivityCommitOrderformList = (ListView) findViewById(R.id.activity_commit_orderform_list);//展示提交订单中的商品的listview
        mActivityCommitGoodsTitle = (LinearLayout) findViewById(R.id.activity_commit_goods_title);
        mHeaderView = View.inflate(this, R.layout.submit_userinfo_list_title, null);
        mHeaderView2 = View.inflate(this, R.layout.global_goods_title, null);
        mActivityCommitOrderformList.addHeaderView(mHeaderView,null,false);
        mActivityCommitOrderformList.addHeaderView(mHeaderView2);
        mCommitOrderFormTotalmoneyNumber = (TextView) findViewById(R.id.commit_orderForm_totalmoney_number);//订单总价
        submitOrderFormAdapter = new OrderFormGoodsList(mCommitOrderFormTotalmoneyNumber,
                new CheckBox(this), 0.0, this, payGoodsList, totalMoney);
        mActivityCommitOrderformList.setAdapter(submitOrderFormAdapter);
        mActivityCommitOrderformList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    if (mActivityCommitGoodsTitle.getVisibility() != View.VISIBLE) {
                        mActivityCommitGoodsTitle.setVisibility(View.VISIBLE);
                    }
                } else if (firstVisibleItem < 1) {
                    mActivityCommitGoodsTitle.setVisibility(View.GONE);
                }
            }
        });


        mShowShopCoupon = (TextView) mHeaderView.findViewById(R.id.show_shop_coupon);
        mShowOffsetCoupon = (TextView) mHeaderView.findViewById(R.id.show_offset_coupon);


        mShopCoupon = (ImageView) mHeaderView.findViewById(R.id.submit_activity_shop_coupon);
        mOffsetCoupon = (ImageView) mHeaderView.findViewById(R.id.submit_activity_offset_coupon);
        //配置onclick
        mShopCoupon.setOnClickListener(this);
        mOffsetCoupon.setOnClickListener(this);
        /**
         * 当前总价改变监听的 匿名内部类
         */
        mCommitOrderFormTotalmoneyNumber.addTextChangedListener(new TextWatcher() {
            private CharSequence beforeText;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double i1 = Double.parseDouble(s.toString());
                favorableList = ComputerCouponHelper.automationSelectCoupon(
                        shopList, offsetList, payGoodsList, submitOrderFormAdapter.
                                getmCountMoney());
                                resumeMoney(i1,true,"g");//选择完成后 更新哪个textview （购物券 抵用券）

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void resumeMoney(double i1,boolean isBtnChange,String type){
        BigDecimal favorableMoney = selectCouponShow(stateCode, favorableList,type);

        /**
         * 做运费的计算
         */
        if (i1 > 499) {
            mCommitOrderFormFreightNumber.setText("0.0");
            everythingPay = submitOrderFormAdapter.getmCountMoney();
            everythingPay = everythingPay.subtract(favorableMoney);
            everythingPay = everythingPay.compareTo(new BigDecimal("0")) == 1 ? everythingPay : new BigDecimal("0");
            String pay_money = df2.format(everythingPay);
            mCommitPaymentMoney.setText(pay_money);

        } else if (i1 < 499) {
            mCommitOrderFormFreightNumber.setText(String.valueOf(ConfigurationInfo.FREIGHT_MONEY));
            everythingPay = submitOrderFormAdapter.getmCountMoney().add(ConfigurationInfo.FREIGHT_MONEY);
            everythingPay = everythingPay.subtract(favorableMoney);
            everythingPay = everythingPay.compareTo(new BigDecimal("0")) == 1 ? everythingPay : new BigDecimal("0");
            String pay_money = df2.format(everythingPay);
            mCommitPaymentMoney.setText(pay_money);
        }

        /**
         * 修改favorable为购物券的按钮
         *
         */
        List<CouponBean.ResultBean.ListBean> listBeen = ComputerCouponHelper.computerUsableCoupon(shopList, ComputerCouponHelper.COUPON_SHOP,
                payGoodsList, stateCode, submitOrderFormAdapter.getmCountMoney());
        if(isBtnChange){
            if (listBeen != null) {
                if (listBeen.size() == 0 && mShopCoupon.isEnabled()) {
                    mShopCoupon.setEnabled(false);
                } else if(listBeen.size() != 0 && !mShopCoupon.isEnabled()){
                    mShopCoupon.setEnabled(true);
                }
            } else {
                mShopCoupon.setEnabled(false);
            }
            List<CouponBean.ResultBean.ListBean> listBeen1 = ComputerCouponHelper.computerUsableCoupon(offsetList, ComputerCouponHelper.COUPON_OFFSET,
                    payGoodsList, stateCode, submitOrderFormAdapter.getmCountMoney());
            if(listBeen1 != null){
                if(listBeen1.size() == 0 && mOffsetCoupon.isEnabled()){
                    mOffsetCoupon.setEnabled(false);
                }else if(listBeen1.size() != 0 && !mOffsetCoupon.isEnabled()){
                    mOffsetCoupon.setEnabled(true);
                    mShowOffsetCoupon.setText("(选择0张抵用券，共计优惠0元）");
                }
            }else {
                mOffsetCoupon.setEnabled(false);
            }
        }

        Log.d(className, "shopList:" + shopList + "\n" + "offsetList:" + offsetList +
                "\neverythingpay:" + everythingPay + "\nfavorableMoney:" +
                favorableMoney + "\nfavorableList:" + favorableList);
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
                    preparePayIng();
                    //如果已经生成订单 直接去进行支付
                }
            });
        } else {
            submitOrderFormAdapter.setShowOperation(true);
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
                if(favorableList != null){
                    if(favorableList.size() != 0) {
                        if (favorableList.get(0).getPrefix().equals("d")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("提示"); //设置标题
                            builder.setMessage("抵用券和购物券不能同时使用噢?"); //设置内容

                            builder.setIcon(R.mipmap.linjia_login);//设置图标，图片id即可
                            builder.create().show();
                            break;
                        }
                    }
                }
                    List<CouponBean.ResultBean.ListBean> list = ComputerCouponHelper.computerUsableCoupon(
                            shopList, ComputerCouponHelper.COUPON_SHOP,
                            payGoodsList, stateCode, submitOrderFormAdapter.getmCountMoney());
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).nextUsable = true;
                    list.get(i).sign = false;
                }
                    mSelectCoupon = new SelectCouponDialog(this, list, favorableList,payGoodsList,
                            submitOrderFormAdapter.getmCountMoney());
                selectCoupon(mSelectCoupon.SHOP_COUPON);//传入标记
                break;
            case R.id.submit_activity_offset_coupon:
                    List<CouponBean.ResultBean.ListBean> list2 = ComputerCouponHelper.computerUsableCoupon(
                            offsetList, ComputerCouponHelper.COUPON_OFFSET,
                            payGoodsList, stateCode, submitOrderFormAdapter.getmCountMoney());

                if(favorableList != null){
                    if(favorableList.size() != 0) {
                        if (favorableList.get(0).getPrefix().equals("g")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("提示"); //设置标题
                            builder.setMessage("抵用券和购物券不能同时使用噢?"); //设置内容

                            builder.setIcon(R.mipmap.linjia_login);//设置图标，图片id即可
                            builder.create().show();
                            break;
                        }
                    }
                }
                    mSelectCoupon = new SelectCouponDialog(this, list2, favorableList,payGoodsList,
                            submitOrderFormAdapter.getmCountMoney());
                selectCoupon(mSelectCoupon.OFFSET_COUPON);
                break;
            case R.id.activity_submit_order_form_top_back_iv:
                this.onBackPressed();
                break;
        }
    }

    private void selectCoupon(final String type) {

        mSelectCoupon.setCouponType(type);
        mSelectCoupon.setItemCouponID(new SelectCouponDialog.CouponInfo() {
            @Override
            public void getSelCouponInfo(List<CouponBean.ResultBean.ListBean> selectedList) {
                favorableList = selectedList;
                resumeMoney(Double.parseDouble(mCommitOrderFormTotalmoneyNumber.getText().toString()),// TODO: 2016/12/21 这里有问题
                        false,type);
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
        lodingDialog.setCancelable(false);
        lodingDialog.show();
        if (result == null) {
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
                        OrderFromID = createOrderFormReturn.getResult().getOrderId();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lodingDialog.dismiss();
                                MyToast.showToast("订单创建成功！");
                                deleteCartGoods();
                                preparePayIng();
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

    private void preparePayIng() {
        final LodingDialog lodingDialog = new LodingDialog(this);
        lodingDialog.setmTextView("准备支付中。。。");
        lodingDialog.show();
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String,Object> hashMap = new HashMap();
                hashMap.put("appToken",WizarPosApp.getmPersonalInfo().getAppToken());
                hashMap.put("orderId",String.valueOf(OrderFromID));
                hashMap.put("couponIds",filterCouponId(favorableList));
                String json = GsonUtil.parseMapToJson(hashMap);

                try {
                    couponVerificationBean = OkHttpUtil.postBeanFormServer(
                            Url.COUPON_VERIFICATION, json, CouponVerificationBean.class);
                } catch (IOException e) {
                    InternetFailure("网络故障╮(╯▽╰)╭");
                }
                if(couponVerificationBean == null){
                    InternetFailure("准备失败");
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(couponVerificationBean.getResult().isIsCanUse()){
                            showSelctctPay(String.valueOf(couponVerificationBean.getResult().getOrder().getPayMoney()));
                        }else{
                            MyToast.showToast(couponVerificationBean.getResult().getDesc());
                        }
                        lodingDialog.dismiss();
                    }
                });
            }

            private void InternetFailure(final String str){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyToast.showToast(str);
                        lodingDialog.dismiss();
                    }
                });
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

    private void showSelctctPay(String msg) {
        SelectPayDialog selectPayDialog = new SelectPayDialog(this);
        selectPayDialog.setOnItemClick(new SelectPayDialog.ItemClick() {
            @Override
            public void payClick(String code) {
                payChancel = code;
                scanCode();
            }
        });
        selectPayDialog.show();
        selectPayDialog.setmUltimatelyPayTextContent(msg);
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
        } else {

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
                payInfo.setCouponIds(favorableList);
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

    private List<Integer> filterCouponId(List<CouponBean.ResultBean.ListBean> couponList){
        List<Integer> tempList = new ArrayList<>();
        if(couponList == null){
            return tempList;
        }
        for (int i = 0; i < couponList.size(); i++) {
            tempList.add(couponList.get(i).getId());
        }
        return tempList;
    }
}
