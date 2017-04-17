package top.linjia.wizarposapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wizarpos.barcode.decode.DecodeResult;
import com.wizarpos.barcode.scanner.ScannerActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.SubmitActivityGoodsListAdapter;
import top.linjia.wizarposapp.apiengine.ConfigurationGet;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.CartGoodsbean;
import top.linjia.wizarposapp.beans.CouponVerificationBean;
import top.linjia.wizarposapp.beans.CreateOrderFormReturn;
import top.linjia.wizarposapp.beans.PayInfo;
import top.linjia.wizarposapp.beans.PayResultInfo;
import top.linjia.wizarposapp.beans.SubmitActCouCoinBean;
import top.linjia.wizarposapp.beans.SubmitOrderBean;
import top.linjia.wizarposapp.beans.SubmitOrderFromInfo;
import top.linjia.wizarposapp.customs.LodingDialog;
import top.linjia.wizarposapp.customs.SelectPayDialog;
import top.linjia.wizarposapp.global.ConfigurationInfo;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.helperComputer.CartNumberHelper;
import top.linjia.wizarposapp.utils.GetSystemInfoUtil;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @author 陈文豪
 * @className: top.linjia.wizarposapp.activities SubmitOrderFormActivity
 * @description: 提交订单的activity 负责显示卡券 选取卡券 请求卡券的list列表
 * @crete 2016/12/27 16:27
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class SubmitOrderFormActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int TOTAL_HAVE = 0;//两种卡券都有
    private static final int OFFSET_NOT = 1;//抵用券没有
    private static final int SHOP_NOT = 2;//购物券没有
    private static final int TOTAL_NOT = 3;//都没有

    private static final int COUPON_SHOP = 0;  //购物券
    private static final int COUPON_OFFSET = 1;//抵用券

    private static final int MSG_WHAT_CART_INIT = 1;  //购物车进入页面时初始化数据发送的handler标记
    private static final int MSG_WHAT_ORDER_INIT = 2;   //订单详情页面进入页面初始化数据

    private EditText mActivitySubmitOrderFormStore;
    private EditText mActivitySubmitOrderFormReceivegoodsman;
    private EditText mActivitySubmitOrderFormLinkmanphone;
    private EditText mActivitySubmitOrderFormAddress;
    private EditText mActivitySubmitOrderFormRemark;
    private ListView mActivityCommitOrderformList;
    private LinearLayout mActivitySubmitOrderFormCouponShow; // 优惠券展示区
    private LinearLayout mActivitySubmitOrderFormCouponMore;  //更多卡券
    private TextView mActivitySubmitOrderFormLinjiaCoins; // 邻家币总额展示区
    private TextView mActivitySubmitOrderFormLinjiaCoinsUse;
    private Button mCommitPaymentBtn;

    private ArrayList<CartGoodsbean.ResultBean> payGoodsList;
    private ArrayList<Integer> cartIds;
    String jsonStr = "";
    private BigDecimal totalMoney;//商品总额
    private TextView mCommitOrderFormTotalmoneyNumber;//商品总额textview
    private TextView mCommitPaymentMoney;//商品实付
    private TextView mCommitOrderFormFreightNumber;//运费

    private SubmitActivityGoodsListAdapter submitActivityGoodsListAdapter;//适配器

    CouponVerificationBean couponVerificationBean = null;//卡券验证返回结果
    private boolean couponStatus = true;

    private List<SubmitOrderBean.ResultBean.CouponsBean> shopList;
    private List<SubmitOrderBean.ResultBean.CouponsBean> offsetList;
    private List<SubmitOrderBean.ResultBean.CouponsBean> favorableList;
    private HashMap<Integer, HashMap<Integer, Integer>> hashMap;
    private Button mCountinueShop;
    /**
     * 初始化集合
     */ {
        shopList = new ArrayList<>();
        offsetList = new ArrayList<>();
    }

    private DecimalFormat df2;
    private boolean isPay;
    private String OrderFromON = null;
    private int OrderFromID = 0;
    private double payMoney;
    private PayInfo payInfo;
    private String codeString;
    private String payChancel;
    private Button linjiaCoins;
    private ImageView mBackIv;
    private LinearLayout mActivityCommitGoodsTitle;
    private View mHeaderView;
    private View mHeaderView2;
    private View mHeaderView3;
    private View areaForCouponShow;
    private TextView couponType;
    private TextView couponNum;
    private TextView couponValue;
    private Handler handler;
    private TextView mSubmitExemptFreightText;

    private TextView mSubmitGoodsKindNumber;//商品数量和总类的统计
    private TextView mSubmitFavorable;

    /**
     * @param savedInstanceState
     * @Title: onCreate
     * @Description: 创建activity 的时候 获取购物车传过来的参数
     * @date 2016/12/27 16:34
     * @author 陈文豪
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_submit_order_form);
        Intent intent = getIntent();
        /**
         * 假数据
         * */
        cartIds = new ArrayList<>();
        // cartIds.add(24674);
        //cartIds.add(24675);
        payGoodsList = new ArrayList<>();
        totalMoney = new BigDecimal("0");
        cartIds = intent.getIntegerArrayListExtra("cartIds");  //接受传递过来的商品列表信息cartId
        payGoodsList = intent.getParcelableArrayListExtra("payGoodsList");
//        Log.i("test",cartIds.size()+"=================");
//        Log.i("test",payGoodsList.get(0).getCartNumber()+"++++++++++++++");
        //      Log.i("test", "payGoodsList是否为空:" + (payGoodsList == null));
        isPay = intent.getBooleanExtra("isPay", false);    //接受传过来的标志来判断是否是已经支付的订单
        OrderFromON = intent.getStringExtra("OrderFromON");//如果没有传入数据会返回null、
        OrderFromID = intent.getIntExtra("OrderFromID", 0);//获取订单的id

        initData();//加载卡券及购物车或是未支付订单数据
        initHandler();
    }

    private void initHandler() {

        final StringBuilder builder = new StringBuilder("-");
        builder.append(SubmitOrderFormActivity.this.getString(R.string.fragmentCard_renminbi));
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_WHAT_CART_INIT://没有订单号码时候的更新ui
                        SubmitOrderBean sob = (SubmitOrderBean) msg.obj;
                        double money = sob.getResult().getMoney();
                        payMoney = sob.getResult().getPayMoney();
                        double sendMoney = sob.getResult().getSendMoney();
                        BigDecimal favorableMoney = new BigDecimal(money).subtract(new BigDecimal(payMoney)).
                                add(new BigDecimal(sendMoney));//计算优惠的额度
                        builder.append(df2.format(favorableMoney.doubleValue() < 0 ? 0 : favorableMoney.doubleValue()));
                        mSubmitFavorable.setText(builder);
                        mActivitySubmitOrderFormLinjiaCoins.setText(getResources().getString(R.string.linjia_coins_balance) + String.valueOf(sob.getResult().getLinjiaMoney()));
                        mActivitySubmitOrderFormLinjiaCoinsUse.setText("￥" + String.valueOf(sob.getResult().getPayBylinjiaMoney()));
                        mCommitOrderFormTotalmoneyNumber.setText(String.valueOf(money));
                        mCommitPaymentMoney.setText(String.valueOf(payMoney));
                        mCommitOrderFormFreightNumber.setText(String.valueOf(sendMoney));

                        break;
                    case MSG_WHAT_ORDER_INIT:
                        SubmitActCouCoinBean submitActCouCoinBean = (SubmitActCouCoinBean) msg.obj;
                        mActivitySubmitOrderFormLinjiaCoins.setText(getResources().getString(R.string.linjia_coins_balance) + String.valueOf(submitActCouCoinBean.getResult().getLinjiaMoney()));
                        mActivitySubmitOrderFormLinjiaCoinsUse.setText("￥" + String.valueOf(submitActCouCoinBean.getResult().getPayBylinjiaMoney()));
                        money = submitActCouCoinBean.getResult().getMoney();
                        payMoney = submitActCouCoinBean.getResult().getPayMoney();;
                        sendMoney = submitActCouCoinBean.getResult().getSendMoney();
                        favorableMoney = new BigDecimal(money).subtract(new BigDecimal(payMoney)).
                                add(new BigDecimal(sendMoney));//计算优惠的额度
                        builder.append(df2.format(favorableMoney.doubleValue() < 0 ? 0 : favorableMoney.doubleValue()));
                        mSubmitFavorable.setText(builder);

                        mCommitOrderFormTotalmoneyNumber.setText(String.valueOf(money));//总额
                        mCommitPaymentMoney.setText(String.valueOf(payMoney));//实付
                        mCommitOrderFormFreightNumber.setText(String.valueOf(sendMoney));//运费

                        SubmitOrderFormActivity.this.payMoney = submitActCouCoinBean.getResult().getPayMoney();
                        break;
                }



                try {
                    ConfigurationGet.asycFreightExemptValue(mSubmitExemptFreightText, SubmitOrderFormActivity.this);
                } catch (IOException e) {
                    MyToast.showToast(SubmitOrderFormActivity.this.getString(R.string.internet_errer));
                }

                mSubmitGoodsKindNumber.setText(setKindAndNumber());//统计商品的数量和种类
                super.handleMessage(msg);
            }
        };
    }

    /**
     * @Title: initView
     * @Description: 初始化控件
     * @date 2016/12/26 17:28
     * @author 陈文豪
     */
    private void initView() {
        /**
         * 店家信息
         * */
        mActivitySubmitOrderFormStore = (EditText) mHeaderView.findViewById(R.id.activity_submit_order_form_store);
        mActivitySubmitOrderFormReceivegoodsman = (EditText) mHeaderView.findViewById(R.id.activity_submit_order_form_receivegoodsman);
        mActivitySubmitOrderFormLinkmanphone = (EditText) mHeaderView.findViewById(R.id.activity_submit_order_form_linkmanphone);
        mActivitySubmitOrderFormAddress = (EditText) mHeaderView.findViewById(R.id.activity_submit_order_form_address);
        mActivitySubmitOrderFormRemark = (EditText) mHeaderView.findViewById(R.id.activity_submit_order_form_remark);

        mCommitPaymentBtn = (Button) findViewById(R.id.commit_payment_btn);   //提交订单按钮
        mBackIv = (ImageView) findViewById(R.id.activity_submit_order_form_top_back_iv);

        /**
         * 设置初始化的值(用户地址，店铺名称，电话等信息)
         * */
        mActivitySubmitOrderFormStore.setText(WizarPosApp.getmPersonalInfo().getResult().getOrgan());
        mActivitySubmitOrderFormReceivegoodsman.setText(WizarPosApp.getmPersonalInfo().getResult().getUname());
        mActivitySubmitOrderFormLinkmanphone.setText(WizarPosApp.getmPersonalInfo().getResult().getPhone());
        mActivitySubmitOrderFormAddress.setText(WizarPosApp.getmPersonalInfo().getResult().getAddress());

        mSubmitExemptFreightText = (TextView) findViewById(R.id.cart_exempt_freight_text);

        /**
         * 配置该有的属性
         * */
        mActivitySubmitOrderFormStore.setFocusable(false);
        mActivitySubmitOrderFormReceivegoodsman.setFocusable(false);
        mActivitySubmitOrderFormLinkmanphone.setFocusable(false);
        mActivitySubmitOrderFormAddress.setFocusable(false);

        mCountinueShop = (Button) findViewById(R.id.commit_continue_shop);

        mBackIv.setOnClickListener(this);

        /**
         * 为订单总额设置初始化数据
         * */
        df2 = new DecimalFormat("0.00");
        mCommitOrderFormTotalmoneyNumber.setText(df2.format(totalMoney));
        mCommitPaymentBtn.setOnClickListener(this);

        /**
         * 创建支付信息实体
         */
        payInfo = new PayInfo();

        compatibilityInitView();  //根据不同页面跳转传来的不同数据做出不同变化

    }

    /**
     * @Title: initData
     * @Description: TODO
     * @date 2016/12/26 17:30
     * @author 陈文豪
     */
    private void initData() {
        /**
         * 初始化dialog
         */
        final LodingDialog lodingDialog = new LodingDialog(this);
        lodingDialog.setmTextView(this.getString(R.string.dialog_loading));
        lodingDialog.setCancelable(false);
        lodingDialog.show();
        /**
         * 开启子线程请求网络数据
         */
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    /**
                     * 由购物车跳转过来
                     * */
                    if (OrderFromON == null) {
                        jsonStr = str2json(cartIds, null, null);
                        final SubmitOrderBean submitOrderBean = OkHttpUtil.postBeanFormServer(Url.BASE_URL + "cart/checkedCartItems", jsonStr, SubmitOrderBean.class);
                        Log.i("test", "submitOrderBean:" + (submitOrderBean == null));
                        Log.i("test", "payGoodsList:" + (payGoodsList == null));
                        if (submitOrderBean != null) {
                            /**
                             * 使用工具类解析集合 分为抵用券 和 购物券
                             */
                            Log.i("test", "OrderFromON:" + OrderFromON);
                            Log.i("test", "jsonStr:" + jsonStr);
                            Log.i("test", "shopList是否为空：" + (shopList.size()));
                            Log.i("test", "offsetList是否为空：" + (offsetList.size()));
                            Log.i("test", (submitOrderBean.getResult().getCartItems() == null) + "商品项为空");
                            if (submitOrderBean.getResult().getCoupons() != null) {
                                if (submitOrderBean.getResult().getCoupons().size() != 0) {
                                    favorableList = submitOrderBean.getResult().getCoupons();
                                    classifyCoupon(favorableList, shopList, offsetList);
                                    filterCouponId(favorableList);
                                    Log.i("test", favorableList.size() + "");
                                    hashMap = parseCouponForShow(shopList, offsetList);
                                    Log.i("test", "HashMap的长度是：" + hashMap.size());
                                    Log.i("test", "shopList是否为空：" + (shopList.size()));
                                    Log.i("test", "offsetList是否为空：" + (offsetList.size()));
                                    Log.i("test", (submitOrderBean.getResult().getCartItems() == null) + "商品项为空");
                                }
                            }
                            if (submitOrderBean.getResult().getCartItems() != null) {
                                Log.i("test", (submitOrderBean.getResult().getCartItems() == null) + "商品项为空");
                                if (submitOrderBean.getResult().getCartItems().size() != 0) {
                                    payGoodsList = submitBean2CartBean(submitOrderBean.getResult().getCartItems());
                                    Log.i("test", "PayGoodsList是否为空" + (payGoodsList == null));
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lodingDialog.dismiss();
                                    MyToast.showToast(SubmitOrderFormActivity.this.getString(R.string.loading_succeed));
                                    initListView(submitOrderBean.getResult().getLinjiaMoney());
                                    initView();
                                    if (hashMap != null) {
                                        showCoupon(SubmitOrderFormActivity.this, hashMap, mActivitySubmitOrderFormCouponShow, areaForCouponShow, couponType, couponNum, couponValue);
                                    }
                                    Message msg = Message.obtain();
                                    msg.obj = submitOrderBean;
                                    msg.what = MSG_WHAT_CART_INIT;
                                    handler.sendMessage(msg);
                                }
                            });
                        } else {
                            requestFail(SubmitOrderFormActivity.this.getString(R.string.data_loaging_fail));
                        }
                    } else {
                        /**
                         * 由订单详情页面跳转过来
                         * */
                        FormBody formbody = new FormBody.Builder()
                                .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                                .add("orderId", String.valueOf(OrderFromID))
                                .build();
                        Response response = OkHttpUtil.postResponseFormServer(Url.BASE_URL + "order/updateLinjiaMoneyState", formbody);
                        String jsonStr = response.body().string();
                        Log.i("test", WizarPosApp.getmPersonalInfo().getAppToken() + "=======" + String.valueOf(OrderFromID));
                        Log.i("test", "response:" + jsonStr);
                        final SubmitActCouCoinBean submitActCouCoinBean = GsonUtil.parseJsonToBean(jsonStr, SubmitActCouCoinBean.class);
                        if (submitActCouCoinBean != null) {
                            if (submitActCouCoinBean.getResult().getCoupons() != null) {
                                if (submitActCouCoinBean.getResult().getCoupons().size() != 0) {
                                    Log.i("test", "SubmitActCouCoinBean是否为空：" + (submitActCouCoinBean == null));
                                    favorableList = scrc2src(submitActCouCoinBean.getResult().getCoupons());
                                    filterCouponId(favorableList);
                                    classifyCoupon(favorableList, shopList, offsetList);
                                    hashMap = parseCouponForShow(shopList, offsetList);
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lodingDialog.dismiss();
                                    initListView(submitActCouCoinBean.getResult().getLinjiaMoney());
                                    initView();
                                    if (hashMap != null) {
                                        showCoupon(SubmitOrderFormActivity.this, hashMap, mActivitySubmitOrderFormCouponShow, areaForCouponShow, couponType, couponNum, couponValue);
                                    }
                                    Message msg = Message.obtain();
                                    msg.obj = submitActCouCoinBean;
                                    msg.what = MSG_WHAT_ORDER_INIT;
                                    handler.sendMessage(msg);
                                }
                            });
                        } else {
                            requestFail(SubmitOrderFormActivity.this.getString(R.string.data_loaging_fail));
                        }
                    }
                } catch (IOException e) {
                    requestFail(SubmitOrderFormActivity.this.getString(R.string.internetVisitError));
                }
            }

            /**
             * 加载失败更新ui
             * @param msg
             */
            private void requestFail(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lodingDialog.dismiss();
                        initListView(0);
                        initView();
                        MyToast.showToast(msg);
                    }
                });
            }

        });
    }

    /**
     * @param ljcoins
     * @Title: initListView
     * @Description: 优先初始化listview，包括商家信息，邻家币信息，优惠券信息
     * @date 2016/12/26 17:50
     * @author 陈文豪
     */
    private void initListView(double ljcoins) {
        mActivityCommitOrderformList = (ListView) findViewById(R.id.activity_commit_orderform_list);//展示提交订单中的商品的listview
        mActivityCommitGoodsTitle = (LinearLayout) findViewById(R.id.activity_commit_goods_title);  //商品名称，单价，操作标题栏

        /**
         * 填充布局
         */
        areaForCouponShow = View.inflate(this, R.layout.submitactivity_coupon_pic, null);
        couponType = (TextView) areaForCouponShow.findViewById(R.id.coupon_pic_classify);
        couponNum = (TextView) areaForCouponShow.findViewById(R.id.coupon_pic_num);
        couponValue = (TextView) areaForCouponShow.findViewById(R.id.coupon_pic_value);
        mHeaderView = View.inflate(this, R.layout.submit_userinfo_list_title, null);   //用户信息展示（店铺名称，收货地址，联系电话等）
        mHeaderView2 = View.inflate(this, R.layout.global_goods_title, null);
        mHeaderView3 = View.inflate(this, R.layout.submit_userinfo_list_linjia_coins, null);

        /**
         * 选择卡券并显示
         * */
        mActivitySubmitOrderFormCouponShow = (LinearLayout) mHeaderView.findViewById(R.id.submitActivity_textview_coupon_ll);
        mActivitySubmitOrderFormCouponMore = (LinearLayout) mHeaderView.findViewById(R.id.submitActivity_textview_coupon_more);
        mActivitySubmitOrderFormCouponMore.setOnClickListener(this);

        /**
         * 邻家币展示区
         * */
        mActivitySubmitOrderFormLinjiaCoins = (TextView) mHeaderView3.findViewById(R.id.linjia_coins_total);
        mActivitySubmitOrderFormLinjiaCoinsUse = (TextView) mHeaderView3.findViewById(R.id.linjia_coins_useable);
        linjiaCoins = (Button) mHeaderView3.findViewById(R.id.submit_userinfo_list_linjia_coins_btn);
        linjiaCoins.setOnClickListener(this);

        /**
         * 给listview设置header
         */
        mActivityCommitOrderformList.addHeaderView(mHeaderView, null, false);
        /**
         * 选择加载是否使用邻家币条目
         */
        linjiaCoinsShowView(ljcoins, mHeaderView3);
        mActivityCommitOrderformList.addHeaderView(mHeaderView2);
        /**
         * 初始化订单总额，运费，实付款View
         * */
        mCommitPaymentMoney = (TextView) findViewById(R.id.commit_payment_money);  //实付
        mCommitOrderFormFreightNumber = (TextView) findViewById(R.id.commit_orderForm_freight_number);  //运费
        mCommitOrderFormTotalmoneyNumber = (TextView) findViewById(R.id.commit_orderForm_totalmoney_number);//订单总额
        mSubmitFavorable = (TextView) findViewById(R.id.submit_favorable);//优惠额的显示
        mSubmitGoodsKindNumber = (TextView) findViewById(R.id.submit_goods_kind_number);//数量和种类的显示
        /**
         * 初始化控件 并且给listview设置adapter
         */
        if (payGoodsList == null) {
            submitActivityGoodsListAdapter = new SubmitActivityGoodsListAdapter(mActivitySubmitOrderFormCouponShow, areaForCouponShow, mCommitOrderFormTotalmoneyNumber, mCommitOrderFormFreightNumber, mCommitPaymentMoney,
                    new CheckBox(this), this, new ArrayList<CartGoodsbean.ResultBean>(), totalMoney, couponType, couponNum, couponValue, mActivitySubmitOrderFormLinjiaCoinsUse, payMoney,mSubmitFavorable,mSubmitGoodsKindNumber);
        } else {
            submitActivityGoodsListAdapter = new SubmitActivityGoodsListAdapter(mActivitySubmitOrderFormCouponShow, areaForCouponShow, mCommitOrderFormTotalmoneyNumber, mCommitOrderFormFreightNumber, mCommitPaymentMoney,
                    new CheckBox(this), this, payGoodsList, totalMoney, couponType, couponNum, couponValue, mActivitySubmitOrderFormLinjiaCoinsUse, payMoney,mSubmitFavorable,mSubmitGoodsKindNumber);
        }
        mActivityCommitOrderformList.setAdapter(submitActivityGoodsListAdapter);

        /**
         * 绑定listview的滑动事件
         */
        mActivityCommitOrderformList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            /**
             * @Title: initListView
             * @Description: 重写onScroll方法 在内部做判断是否条目隐藏和显示
             * @param view
             * @param firstVisibleItem
             * @param totalItemCount
             * @param visibleItemCount
             * @return void
             * @date 2016/12/28 14:50
             * @author 陈文豪
             */
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

    }


    /**
     * @Title: compatibilityInitView
     * @Description: 做多页面 跳转兼容的初始化方法
     * @date 2016/12/26 17:56
     * @author 陈文豪
     */
    private void compatibilityInitView() {
        if (isPay) {
            /**
             * 已经支付按钮，暂时没有这种情况
             * */
            mCommitPaymentBtn.setText(R.string.submitActivity_button_already_pay);
            mCommitPaymentBtn.setEnabled(false);
            submitActivityGoodsListAdapter.setShowOperation(false);
            mActivitySubmitOrderFormRemark.setEnabled(false);
            //隐藏继续购物按钮
            mCountinueShop.setVisibility(View.GONE);
        } else if (OrderFromON != null) {
            /**
             * 未付款的订单
             * */
            mActivitySubmitOrderFormRemark.setEnabled(false);
            submitActivityGoodsListAdapter.setShowOperation(false);
            mCommitPaymentBtn.setText(R.string.submitActivity_button_topay);
            mCountinueShop.setVisibility(View.GONE);
            mCommitPaymentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preparePayIng();
                    //如果已经生成订单 直接去进行支付
                }
            });
        } else {
            /**
             * 从购物车中跳转到当前的页面，展示加减号操作
             * */
            submitActivityGoodsListAdapter.setShowOperation(true);
        }
        submitActivityGoodsListAdapter.notifyDataSetChanged();
    }

    /**
     * @param v
     * @Title: onClick
     * @Description: 根据id处理事件
     * @date 2016/12/28 15:11
     * @author 陈文豪
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_payment_btn:
                submit();
                break;
            case R.id.common_top_back_iv:
                finish();
                break;
            case R.id.activity_submit_order_form_top_back_iv:
                this.onBackPressed();
                break;
            case R.id.submit_userinfo_list_linjia_coins_btn:
                changeButtonStatus();
                break;
            case R.id.submitActivity_textview_coupon_more:
                Intent intent = new Intent(this, CouponShowActivtiy.class);
                Bundle bundle = new Bundle();
                bundle.putInt("orderId", OrderFromID);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.commit_continue_shop:
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                main = new Intent(this, ClassifyActivity.class);
                Bundle b = new Bundle();
                b.putString("searchText", "");
                b.putInt("flag", 1);
                main.putExtras(b);
                startActivity(main);
                break;
        }
    }

    /**
     * @Title: submit
     * @Description: 提交前的非空认证
     * @date 2016/12/26 17:58
     * @author 陈文豪
     */
    @Deprecated
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


    /**
     * @Title: commitOrderForm
     * @Description: 负责所有提交订单的事务
     * @date 2016/12/26 18:03
     * @author 陈文豪
     */
    private void commitOrderForm() {
        String storeName = mActivitySubmitOrderFormStore.getText().toString().trim();
        String consignee = mActivitySubmitOrderFormReceivegoodsman.getText().toString().trim();
        String phone = mActivitySubmitOrderFormLinkmanphone.getText().toString().trim();
        String address = mActivitySubmitOrderFormAddress.getText().toString().trim();
        ArrayList<SubmitOrderFromInfo.OrderBean.DetailsBean> submitGoods = new ArrayList<>();
        if (payGoodsList.size() != 0) {
            filterIdAndNumber(payGoodsList, submitGoods);
            SubmitOrderFromInfo.OrderBean OrderFormPersonalInfo = new SubmitOrderFromInfo.OrderBean(storeName, consignee, phone, address, submitGoods);
            final String result = GsonUtil.beanToJson(new SubmitOrderFromInfo(WizarPosApp.getmPersonalInfo().getAppToken(), OrderFormPersonalInfo));
            final LodingDialog lodingDialog = new LodingDialog(this);
            lodingDialog.setmTextView(this.getString(R.string.submit_orderIng));
            lodingDialog.setCancelable(false);
            lodingDialog.show();
            if (result == null) {
                MyToast.showToast(this.getString(R.string.parameter_error));
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
                            final int cartNumber = CartNumberHelper.getCartNumber();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lodingDialog.dismiss();
                                    MyToast.showToast(SubmitOrderFormActivity.this.getString(R.string.order_create_succeeed));
                                    CartNumberHelper.cartSumCount(null, cartNumber);
                                    Log.i("test", "创建订单成功");
                                    preparePayIng();
                                    compatibilityInitView();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyToast.showToast(SubmitOrderFormActivity.this.getString(R.string.order_create_fail));
                                    lodingDialog.dismiss();
                                }
                            });
                        }
                    } catch (IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyToast.showToast(SubmitOrderFormActivity.this.getString(R.string.order_create_fail));
                                lodingDialog.dismiss();
                            }
                        });
                    }
                }
            });
        } else {
            MyToast.showToast(getResources().getString(R.string.order_submit_fail));
        }

    }

    /**
     * @Title: upLocalCartNumber
     * @Description: 更新购物车的数量
     * @date 2017/1/10 20:30
     * @author 陈文豪
     */
    private void upLocalCartNumber() throws IOException {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("appToken", WizarPosApp.getmPersonalInfo().getAppToken());
        String json = GsonUtil.parseMapToJson(stringStringHashMap);
        //获取相应体 并且解析为map
        Response response = OkHttpUtil.postMapJsonServer(Url.GET_CART_NUM, json);
        HashMap<String, Object> map = GsonUtil.parseJsonToMapObject(response.body().string());

        Map<String, Double> result = (Map<String, Double>) map.get("result");
        Double cartGoodsNum = (Double) result.get("cartGoodsNum");
        WizarPosApp.getmPersonalInfo().setCartGoodsNum(cartGoodsNum.intValue());
    }

    /**
     * @Title: preparePayIng
     * @Description: 准备支付 支付前提前验证卡券使用后的金额
     * @date 2016/12/26 18:04
     * @author 陈文豪
     */
    private void preparePayIng() {
        final LodingDialog lodingDialog = new LodingDialog(this);
        lodingDialog.setmTextView(this.getString(R.string.prepare_paying));
        lodingDialog.show();
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {

            @Override
            public void run() {
                HashMap<String, Object> hashMap = new HashMap();
                hashMap.put("appToken", WizarPosApp.getmPersonalInfo().getAppToken());
                hashMap.put("orderId", String.valueOf(OrderFromID));
                hashMap.put("ifLinjiaMoney", couponStatus);
                String json = GsonUtil.parseMapToJson(hashMap);
                /**
                 * 实付款不为零是正常支付
                 * */
                if (payMoney != 0) {
                    try {
                        couponVerificationBean = OkHttpUtil.postBeanFormServer(
                                Url.COUPON_VERIFICATION, json, CouponVerificationBean.class);
                    } catch (IOException e) {
                        InternetFailure(SubmitOrderFormActivity.this.getString(R.string.internet_errer));
                    }
                    if (couponVerificationBean == null) {
                        InternetFailure(SubmitOrderFormActivity.this.getString(R.string.prepare_fail));
                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (couponVerificationBean.getResult().isIsCanUse()) {
                                showSelctctPay(String.valueOf(couponVerificationBean.getResult().getOrder().getPayMoney()));  //弹出支付方式对话框供用户选择
                            } else {
                                MyToast.showToast(couponVerificationBean.getResult().getDesc());
                            }
                            lodingDialog.dismiss();
                        }
                    });
                }
                /**
                 * 实付款为零时直接请求支付结果，无需实际支付
                 * */
                else {
                    try {
                        Response response = OkHttpUtil.postMapJsonServer(Url.BASE_URL + "order/orderPayByLinjiaMoney", json);
                        String payResult = response.body().string();
                        JSONObject obj = new JSONObject(payResult);
                        int state = obj.optInt("state");
                        if (state == 1) {
                            /**
                             * 发起请求刷新本地购物车数量变量
                             * */
                            FormBody formBody = new FormBody.Builder()
                                    .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                                    .build();
                            Response response1 = OkHttpUtil.postResponseFormServer(Url.BASE_URL + "cart/getCartGoodsNum", formBody);
                            String cartAllNumStr = response1.body().string();
                            JSONObject cartObj = new JSONObject(cartAllNumStr);
                            JSONObject result = cartObj.getJSONObject("result");
                            int cartAllNum = result.optInt("cartGoodsNum");
                            WizarPosApp.getmPersonalInfo().setCartGoodsNum(cartAllNum);
                            /**
                             * 跳转支付结果提示页面
                             * */
                            PayResultInfo payResultInfo = new PayResultInfo();
                            payResultInfo.setMessage(getResources().getString(R.string.pay_success));
                            payResultInfo.setCode(String.valueOf(0));
                            Intent intent = new Intent(SubmitOrderFormActivity.this, PayResultActivity.class);
                            intent.putExtra("pay_Result", payResultInfo);
                            startActivity(intent);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lodingDialog.dismiss();
                                }
                            });
                        } else {
                            InternetFailure(getResources().getString(R.string.order_pay_fail));
                        }
                    } catch (Exception e) {
                        InternetFailure(getResources().getString(R.string.net_wrong));
                    }

                }

            }

            private void InternetFailure(final String str) {
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

    /**
     * @Title: scanCode
     * @Description: TODO 打开扫码支付码界面
     * @date 2016/12/26 18:07
     * @author 陈文豪
     */
    private void scanCode() {
        Intent intent = new Intent(this, ScannerActivity.class);
        intent.putExtra(DECODE_FORMAT, ConfigurationInfo.CODE_MODULE＿TOTAL);
        startActivityForResult(intent, REQUEST);
    }


    /**
     * @param data
     * @param requestCode
     * @param resultCode
     * @Title: onActivityResult
     * @Description: 二维码解析完成返回数据
     * @date 2016/12/26 18:07
     * @author 陈文豪
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                codeString = extras.getString(DecodeResult.RESULT);
//                String resultFormat = extras.getString(DecodeResult.RESULT_FORMAT);//获取解码类型 如QE_CODE
                Toast.makeText(this, this.getString(R.string.scan_code_succeed), Toast.LENGTH_SHORT).show();
                toConnectionPay();
            }
        } else {

        }
    }

    /**
     * @param
     * @Title: toConnectionPay
     * @Description: 向后台请求进行支付，支付成功后进行跳转提示
     * @date 2016/12/26 18:08
     * @author 陈文豪
     */
    private void toConnectionPay() {

        final LodingDialog lodingDialogPay = new LodingDialog(this);
        lodingDialogPay.setmTextView(this.getString(R.string.paying));
        lodingDialogPay.setBreack(false);
        lodingDialogPay.show();
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                payInfo.setAuth_code(codeString);
                payInfo.setOut_trade_no(OrderFromON);
                payInfo.setPay_channel(payChancel);
                payInfo.setCouponIds(favorableList);
                payInfo.setIfLinjiaMoney(couponStatus);
                payInfo.setTerminal_no(GetSystemInfoUtil.getLocalSN());
                String json = GsonUtil.beanToJson(payInfo);
                PayResultInfo payResultInfo = null;
                try {
                    payResultInfo = OkHttpUtil.postBeanFormServer(Url.TO_PAY_URL, json, PayResultInfo.class);
                } catch (IOException e) {

                }
                if (payResultInfo != null) {
                    Intent intent = new Intent(SubmitOrderFormActivity.this, PayResultActivity.class);
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
                            resultDataRefreshDialog(getResources().getString(R.string.pay_fail));
                        }
                    });
                }
            }
        });
    }

    /**
     * @param payGoodsList
     * @param submitGoods
     * @Title: filterIdAndNumber
     * @Description: 将商品的id和数量过滤出来
     * @date 2016/12/26 18:17
     * @author 陈文豪
     */
    private void filterIdAndNumber(ArrayList<CartGoodsbean.ResultBean> payGoodsList, ArrayList<SubmitOrderFromInfo.OrderBean.DetailsBean> submitGoods) {
        for (int i = 0; i < payGoodsList.size(); i++) {
            submitGoods.add(new SubmitOrderFromInfo.OrderBean.DetailsBean(payGoodsList.get(i).getGoodsId(), payGoodsList.get(i).getCartNumber()));
        }
    }

    private List<Integer> filterCouponId(List<SubmitOrderBean.ResultBean.CouponsBean> couponList) {
        List<Integer> tempList = new ArrayList<>();
        if (couponList == null) {
            return tempList;
        }
        for (int i = 0; i < couponList.size(); i++) {
            tempList.add(couponList.get(i).getId());
        }
        return tempList;
    }

    /**
     * @Title: changeButtonStatus
     * @Description: 点击button按钮时改变按钮状态
     * @Data: 2017/1/3 11:59
     * @Author: 李鹏鹏
     */
    private void changeButtonStatus() {
        if (linjiaCoins.isSelected()) {
            /**
             * 发起请求重新计算可用卡券和应付款
             * */
            linjiaCoins.setSelected(false);
            couponStatus = true;
            refreshUiData(Url.BASE_URL + "cart/updateLinjiaMoneyState", OrderFromID, true);
        } else {
            /**
             * 发起请求重新计算可用卡券和应付款
             * */
            linjiaCoins.setSelected(true);
            couponStatus = false;
            refreshUiData(Url.BASE_URL + "cart/updateLinjiaMoneyState", OrderFromID, false);
        }
    }

    /**
     * @Title: linjiaCoinsShowView
     * @Description: 选择性记载邻家币条目
     * @Params: boolean
     * @Params: View
     * @Data: 2017/1/3 14:00
     * @Author: 李鹏鹏
     */
    private void linjiaCoinsShowView(double ljcoins, View view) {
        if (ljcoins != 0) {
            mActivityCommitOrderformList.addHeaderView(view);
        } else {
            mActivityCommitOrderformList.removeHeaderView(view);
        }
    }

    /**
     * @Title: str2json
     * @Description: 将商品购物车Id转化为json字符串
     * @Params: cartIds
     * @Data: 2017/1/4 10:06
     * @Author: 李鹏鹏
     */
    public String str2json(List<Integer> cartIds, HashMap<String, Boolean> map1, HashMap<String, String> map2) {
        JsonObject json = new JsonObject();
        JsonArray cartIdsStr = new JsonArray();
        if (cartIds != null) {
            for (Object cartId : cartIds) {
                cartIdsStr.add(cartId + "");
            }
            json.addProperty("appToken", WizarPosApp.getmPersonalInfo().getAppToken());//填充appToken
            json.add("cartIds", cartIdsStr);
        } else {
            if (map2 != null) {
                Set set2 = map2.keySet();
                Iterator iter2 = set2.iterator();
                while (iter2.hasNext()) {
                    String key = (String) iter2.next();
                    json.addProperty(key, map2.get(key));
                }
                if (map1 != null) {
                    Set set = map1.keySet();
                    Iterator iter = set.iterator();
                    while (iter.hasNext()) {
                        json.addProperty((String) iter.next(), map1.get("ifLinjiaMoney"));
                    }
                }
            }
        }
        Log.i("test", json.toString());
        return json.toString();
    }

    /**
     * @Title: classifyCoupon
     * @Description: 将可用卡券分类并初始化两种卡券列表
     * @Params: totalList
     * @Params: shopList
     * @Params: offsetlList
     * @Data: 2017/1/4 10:30
     * @Author: 李鹏鹏
     */
    public static int classifyCoupon(List<SubmitOrderBean.ResultBean.CouponsBean> totalList,
                                     List<SubmitOrderBean.ResultBean.CouponsBean> shopList,
                                     List<SubmitOrderBean.ResultBean.CouponsBean> offsetList) {
        // shopList = new ArrayList<>();
        // offsetList = new ArrayList<>();
        for (int i = 0; i < totalList.size(); i++) {
            if (totalList.get(i).getPrefix().equals("g")) {
                shopList.add(totalList.get(i));
                Log.i("test", "添加了一张购物券");
            } else {
                offsetList.add(totalList.get(i));
                Log.i("test", "添加了一张抵用券");
            }
        }
        /**
         * 根据状态码去操作集合
         */
        if (shopList.size() == 0 && offsetList.size() == 0) {
            return TOTAL_NOT;
        } else if (shopList.size() == 0) {
            return SHOP_NOT;
        } else if (offsetList.size() == 0) {
            return OFFSET_NOT;
        } else {
            return TOTAL_HAVE;
        }
    }

    /**
     * @Title: parseCouponForShow
     * @Description: 将初始化后的两种卡券列表按照卡券类别和面值分类
     * @Params: shopList
     * @Params: offsetList
     * @Data: 2017/1/4 17:55
     * @Author: 李鹏鹏
     */
    public static HashMap<Integer, HashMap<Integer, Integer>> parseCouponForShow(List<SubmitOrderBean.ResultBean.CouponsBean> shopList, List<SubmitOrderBean.ResultBean.CouponsBean> offsetList) {
        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
        if (shopList.size() != 0 || offsetList.size() != 0) {
            /**
             * 购物券不为空时
             * */
            if (shopList.size() != 0) {
                Log.i("test", "shopList不为空");
                HashMap<Integer, Integer> shopListMap = new HashMap();
                for (int i = 0; i < shopList.size(); i++) {
                    Log.i("test", i + "");
                    int point = shopList.get(i).getPoint();
                    if (shopListMap.size() == 0) {
                        shopListMap.put(point, 1);
                    } else {
                        Log.i("test", "======已包含该购物券======");
                        if (shopListMap.containsKey(point)) {
                            Log.i("test", "已包含该购物券");
                            int num = shopListMap.get(point) + 1;
                            shopListMap.remove(point);
                            shopListMap.put(point, num);
                        } else {
                            shopListMap.put(point, 1);
                        }
                    }
                }
                map.put(COUPON_SHOP, shopListMap);
            }
            /**
             * 抵用券不为空时
             * */
            if (offsetList.size() != 0) {
                Log.i("test", "offsetList不为空");
                HashMap<Integer, Integer> offsetMap = new HashMap();
                for (int i = 0; i < offsetList.size(); i++) {
                    int point = offsetList.get(i).getPoint();
                    if (offsetMap.size() == 0) {
                        Log.i("test", "==============" + point);
                        offsetMap.put(point, 1);
                    } else {
                        if (offsetMap.containsKey(point)) {
                            int num = offsetMap.get(point) + 1;
                            offsetMap.remove(point);
                            offsetMap.put(point, num);
                        } else {
                            offsetMap.put(point, 1);
                        }
                    }
                }
                map.put(COUPON_OFFSET, offsetMap);
            }
        }
        return map;
    }

    /**
     * @Title: showCoupon
     * @Description: 从map中取出两种卡券和对应的卡券数量，然后再分别填充到卡券显示区
     * @Params: map
     * @Params: mActivitySubmitOrderFormCouponShow
     * @Params: v
     * @Data: 2017/1/4 18:45
     * @Author: 李鹏鹏
     */
    public static void showCoupon(Context context, HashMap<Integer, HashMap<Integer, Integer>> map, LinearLayout mActivitySubmitOrderFormCouponShow, View v, TextView coupontype, TextView couponnum, TextView couponvalue) {
        if (map != null && map.size() != 0) {
            for (int i = 0; i < map.size(); i++) {
                /**
                 * 卡券map中含有购物券
                 * */
                if (map.containsKey(COUPON_SHOP)) {
                    HashMap<Integer, Integer> shopmap = new HashMap();
                    shopmap = map.get(COUPON_SHOP);
                    Log.i("test", "shop的长度是：" + shopmap.size());
                    //  for (int j = 0; j < shopmap.size(); j++) {
                    Set set = shopmap.keySet();
                    Iterator iter = set.iterator();
                    while (iter.hasNext()) {
                        Log.i("test", "????????????????????????????");
                        int point = (int) iter.next();
                        int num = (int) shopmap.get(point);
                        //    mActivitySubmitOrderFormCouponShow.removeAllViews();
                        View tv = LayoutInflater.from(context).inflate(R.layout.submitactivity_coupon_pic, null);
                        TextView classifyView = (TextView) tv.findViewById(R.id.coupon_pic_classify);
                        TextView numView = (TextView) tv.findViewById(R.id.coupon_pic_num);
                        TextView valueView = (TextView) tv.findViewById(R.id.coupon_pic_value);
                        mActivitySubmitOrderFormCouponShow.addView(tv);
                        classifyView.setText("购物券");
                        numView.setText("x" + num);
                        valueView.setText("￥" + point);
                    }
                    //  }
                }
                /**
                 *卡券map中含有抵用券
                 **/
                if (map.containsKey(COUPON_OFFSET)) {
                    HashMap<Integer, Integer> offsetmap = map.get(COUPON_OFFSET);
                    Log.i("test", "map的长度是：" + map.size());
                    Log.i("test", "offsetMap长度是：" + offsetmap.size());
                    for (int j = 0; j < offsetmap.size(); j++) {
                        Set set = offsetmap.keySet();
                        Iterator iter = set.iterator();
                        while (iter.hasNext()) {
                            int point = (int) iter.next();
                            int num = (int) offsetmap.get(point);
                            View tv = LayoutInflater.from(context).inflate(R.layout.submitactivity_coupon_pic, null);
                            TextView classifyView = (TextView) tv.findViewById(R.id.coupon_pic_classify);
                            TextView numView = (TextView) tv.findViewById(R.id.coupon_pic_num);
                            TextView valueView = (TextView) tv.findViewById(R.id.coupon_pic_value);
                            mActivitySubmitOrderFormCouponShow.addView(tv);
                            classifyView.setText("抵用券");
                            numView.setText("x" + num);
                            valueView.setText("￥" + point);
                        }
                    }
                }
            }
        }
    }

    /**
     * @Title: smbmitBean2CartBean
     * @Description: 将提交订单接口中的商品列表提取出来并转化为购物车实体类李彪
     * @Params: cartItemsBeanList
     * @Data: 2017/1/5 15:08
     * @Author: 李鹏鹏
     */
    public ArrayList<CartGoodsbean.ResultBean> submitBean2CartBean(List<SubmitOrderBean.ResultBean.CartItemsBean> cartItemsBeanList) {
        //SubmitActCouCoinBean.ResultBean.
        ArrayList<CartGoodsbean.ResultBean> resultBeenList = new ArrayList<>();
        for (int i = 0; i < cartItemsBeanList.size(); i++) {
            CartGoodsbean.ResultBean resultBean = new CartGoodsbean.ResultBean();
            resultBean.setNumber(cartItemsBeanList.get(i).getNumber());
            resultBean.setCartId(cartItemsBeanList.get(i).getCartId());
            resultBean.setCartNumber(cartItemsBeanList.get(i).getCartNumber());
            resultBean.setDiscPrice(cartItemsBeanList.get(i).getDiscPrice());
            resultBean.setDisDyq(cartItemsBeanList.get(i).isDisDyq());
            resultBean.setDisGwq(cartItemsBeanList.get(i).isDisGwq());
            resultBean.setGoodsId(cartItemsBeanList.get(i).getGoodsId());
            resultBean.setGoodsNo(cartItemsBeanList.get(i).getGoodsNo());
            resultBean.setMoq(cartItemsBeanList.get(i).getMoq());
            resultBean.setPath(cartItemsBeanList.get(i).getPath());
            resultBean.setPraise(cartItemsBeanList.get(i).getPraise());
            resultBean.setProductionDate(cartItemsBeanList.get(i).getProductionDate());
            resultBean.setUnit(cartItemsBeanList.get(i).getUnit());
            resultBean.setStname(cartItemsBeanList.get(i).getStname());
            resultBean.setName(cartItemsBeanList.get(i).getName());
            resultBeenList.add(resultBean);
        }
        return resultBeenList;
    }

    /**
     * @Title: scrc2src
     * @Description: 将submitActCouCoinBean.getResult().getCoupons()转化为SubmitOrderBean.ResultBean.CouponsBean
     * @Params: couponsBeen
     * @Data: 2017/1/6 11:49
     * @Author: 李鹏鹏
     */
    public ArrayList<SubmitOrderBean.ResultBean.CouponsBean> scrc2src(List<SubmitActCouCoinBean.ResultBean.CouponsBean> couponsBeen) {
        ArrayList<SubmitOrderBean.ResultBean.CouponsBean> src = new ArrayList<>();
        if (couponsBeen != null) {
            for (int i = 0; i < couponsBeen.size(); i++) {
                SubmitOrderBean.ResultBean.CouponsBean b = new SubmitOrderBean.ResultBean.CouponsBean();
                b.setCode(couponsBeen.get(i).getCode());
                b.setCouponId(couponsBeen.get(i).getCouponId());
                b.setCreateDate(couponsBeen.get(i).getCreateDate());
                b.setExpiryDate(couponsBeen.get(i).getExpiryDate());
                b.setId(couponsBeen.get(i).getId());
                b.setIntroduction(couponsBeen.get(i).getIntroduction());
                b.setIsUsed(couponsBeen.get(i).getIsUsed());
                b.setNAME(couponsBeen.get(i).getNAME());
                b.setPoint(couponsBeen.get(i).getPoint());
                b.setType(couponsBeen.get(i).getType());
                b.setPrefix(couponsBeen.get(i).getPrefix());
                src.add(b);
            }
        }
        return src;
    }

    /**
     * @Title: refreshUiData
     * @Description: 当前页面存在订单号和不存在订单号是点击邻家币按钮改变状态刷新实付UI
     * @Params: url
     * @Data: 2017/1/6 15:46
     * @Author: 李鹏鹏
     */
    public void refreshUiData(final String url, final int orderid, final boolean ifLinjiaMoney) {
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> map1 = new HashMap<String, String>();
                map1.put("appToken", WizarPosApp.getmPersonalInfo().getAppToken());
                /**
                 * 此时页面已有订单号点击邻家币选择按钮改变状态发送请求重新计算应付款和可用卡券
                 * */
                final StringBuilder builder = new StringBuilder("-");
                builder.append(SubmitOrderFormActivity.this.getString(R.string.fragmentCard_renminbi));
                if (orderid != 0) {
                    map1.put("orderId", String.valueOf(orderid));
                    HashMap<String, Boolean> map2 = new HashMap<String, Boolean>();
                    map2.put("ifLinjiaMoney", ifLinjiaMoney);
                    String jString = str2json(null, map2, map1);
                    Log.i("test", jString);
                    try {
                        final SubmitActCouCoinBean sacc = OkHttpUtil.postBeanFormServer(url, jString, SubmitActCouCoinBean.class);
                        if (sacc != null) {
                            /**
                             * 刷新实付款UI
                             * */
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //做优惠额的计算

                                    BigDecimal favorableMoney = new BigDecimal(sacc.getResult().getMoney()).subtract(new BigDecimal(sacc.getResult().getPayMoney())).
                                            add(new BigDecimal(sacc.getResult().getSendMoney()));
                                    builder.append(df2.format(favorableMoney.doubleValue() < 0 ? 0 : favorableMoney.doubleValue()));
                                    mSubmitFavorable.setText(builder);
                                    mCommitPaymentMoney.setText(String.valueOf(sacc.getResult().getPayMoney()));
                                    payMoney = sacc.getResult().getPayMoney();
                                }
                            });
                        } else {
                            resultDataRefreshDialog(getResources().getString(R.string.goods_purchase_wrong));
                        }
                    } catch (IOException e) {
                        resultDataRefreshDialog(getResources().getString(R.string.net_wrong));
                    }
                }
                /**
                 * 此时页面未生成订单号点击邻家币选择按钮改变状态发送请求重新计算应付款和可用卡券
                 * */
                else {
                    HashMap<String, Boolean> map2 = new HashMap<String, Boolean>();
                    map2.put("ifLinjiaMoney", ifLinjiaMoney);
                    String jString = str2json(null, map2, map1);
                    try {
                        final SubmitOrderBean sob = OkHttpUtil.postBeanFormServer(url, jString, SubmitOrderBean.class);
                        if (sob != null) {
                            /**
                             * 刷新实付款UI
                             * */
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mCommitPaymentMoney.setText(String.valueOf(sob.getResult().getPayMoney()));
                                    payMoney = sob.getResult().getPayMoney();
                                    BigDecimal favorableMoney = new BigDecimal(sob.getResult().getMoney()).subtract(new BigDecimal(sob.getResult().getPayMoney())).
                                            add(new BigDecimal(sob.getResult().getSendMoney()));
                                    builder.append(df2.format(favorableMoney.doubleValue() < 0 ? 0 : favorableMoney.doubleValue()));
                                    mSubmitFavorable.setText(builder);
                                }
                            });
                        } else {
                            resultDataRefreshDialog(getResources().getString(R.string.goods_purchase_wrong));
                        }
                    } catch (IOException e) {
                        resultDataRefreshDialog(getResources().getString(R.string.net_wrong));
                    }

                }
            }
        });
    }

    /**
     * @Title: resultDataRefreshDialog
     * @Description: 线程中刷新UI弹出土司
     * @Params: string
     * @Data: 2017/1/10 11:25
     * @Author: 李鹏鹏
     */
    private void resultDataRefreshDialog(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyToast.showToast(string);
            }
        });
    }

    /**
     * @Title: setKindAndNumber
     * @Description: 为购物车的商品种类数量做统计显示
     * @date 2017/1/20 17:04
     * @author 陈文豪
     */
    private SpannableString setKindAndNumber() {
        int kind = payGoodsList.size();
        int count = 0;
        for (int i = 0; i < payGoodsList.size(); i++) {
            count += payGoodsList.get(i).getCartNumber();
        }
        //其中的4和1是隔开的间距
        int kindEnd = String.valueOf(kind).length() + 1;
        int countEnd = kindEnd + 4 + String.valueOf(count).length();
        SpannableString builder = new SpannableString(String.format(this.getString(R.string.kind_number), kind, count));
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.RED);

        builder.setSpan(colorSpan2, 1, kindEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setSpan(colorSpan, kindEnd + 4, countEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }
}
