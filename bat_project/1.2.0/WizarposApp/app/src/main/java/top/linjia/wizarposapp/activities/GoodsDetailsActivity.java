package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.GoodsDetail;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.beans.UpCartNumberBean;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.helperComputer.CartNumberHelper;
import top.linjia.wizarposapp.parentClass.holder.UpCartNumber;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @ClassName: GoodsDetailsActivity
 * @Description: 该Activity是商品详情页面，主要用来展示某商品的详细信息并实现加入购物车功能。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class GoodsDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView commonTopBackIv;
    protected TextView commonTopText, cart_num;
    protected ImageView cart;
    ImageView goodsdetails_goodsimage;
    TextView goodsdetails_couponinfo;
    protected TextView cartNum;
    protected AbsoluteLayout commonCart;
    LinearLayout goods_details_loading_fail_ll, goods_details_loading_success_ll;
    Button goods_details_loading_fail_btn;
    protected TextView activityGoodsDetailsGoodsname;
    protected TextView activityGoodsDetailsGoodsprice;
    protected TextView activityGoodsDetailsGoodsqiding;
    protected TextView activityGoodsDetailsGoodskucun;
    protected TextView activityGoodsDetailsGoodstiaoma;
    protected TextView activityGoodsDetailsGoodsbrand;
    protected TextView activityGoodsDetailsGoodsunit;
    protected TextView activityGoodsDetailsGoodstype;
    protected TextView activityGoodsDetailsGoodsguige;
    protected TextView activityGoodsDetailsGoodspraise;
    protected TextView activityGoodsDetailsGoodscreatetime;
    protected Button activityGoodsDetailsCartreduce;
    protected TextView activityGoodsDetailsCartnum;
    protected Button activityGoodsDetailsCartplus;
    protected Button activityGoodsDetailsCartadd;
    List<User_CartInfo> user_cartInfos;
    UserDao userDao = null;
    Handler handler;
    int goodsNum = 1;  //商品数量
    int moq = 1;
    int store = 1;
    int praise = 1;
    int disDyq;
    int disGwq;
    String goodsNo = null;
    Bundle bundle;
    String goodsname;
    String stname;
    double price;
    int cartnum;
    int goodsId;
    boolean currentGoodsFlag = true;
    int goodsAllNum = 0;
    private UpCartNumberBean upCartNumberBean;
    private boolean lock = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_goods_details);
        bundle = getIntent().getExtras();
        goodsNo = (String) bundle.get("goodsNo");
        initView();
        initData();
        initHandler();
    }

    /**
     * @Title: initHandler
     * @Description: 初始化handler
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                /**
                 * 首先根据请求成功后的数据创建一个购物车对象
                 * */
                GoodsDetail goodsDetails = (GoodsDetail) msg.obj;
                CartNumberHelper.computerCartSum(cartNum, WizarPosApp.getmPersonalInfo().getCartGoodsNum());
                moq = goodsDetails.getResult().getMoq();
                store = goodsDetails.getResult().getNumber();
                goodsname = goodsDetails.getResult().getNAME();
                stname = goodsDetails.getResult().getStname();
                price = goodsDetails.getResult().getPrice();
                goodsId = goodsDetails.getResult().getGoodsId();
                praise = goodsDetails.getResult().getPraise();
                moq = goodsDetails.getResult().getMoq();
                /**
                 * 从网络请求到的数据中提取信息并刷新UI（购物车数量，商品图片，单价，规格，库存等）
                 * */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        user_cartInfos = userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
                    }
                });
                boolean flag = cartTraverse(user_cartInfos, goodsname);
                if (flag) {
                    goodsNum = userDao.queryGoodsNum(goodsname);
                } else {
                    goodsNum = goodsDetails.getResult().getMoq();
                }
                if (goodsDetails.getResult().getDisDyq()) {
                    disDyq = 0;
                } else {
                    disDyq = 1;
                }
                if (goodsDetails.getResult().getDisGwq()) {
                    disGwq = 0;
                } else {
                    disGwq = 1;
                }
                isEmptyStore(store);
                Picasso.with(GoodsDetailsActivity.this).load(Url.IMAGE_URL + goodsDetails.getResult().getPath()).placeholder(R.drawable.place_holder).into(goodsdetails_goodsimage);
                if (goodsDetails.getResult().getDisDyq() == true && goodsDetails.getResult().getDisGwq() == true) {
                    goodsdetails_couponinfo.setVisibility(View.VISIBLE);
                    goodsdetails_couponinfo.setText(getResources().getString(R.string.coupon_use_nodiscount));
                    goodsdetails_couponinfo.setBackgroundColor(getResources().getColor(R.color.nodiscount));
                } else if (goodsDetails.getResult().getDisDyq() == true && goodsDetails.getResult().getDisGwq() == false) {
                    goodsdetails_couponinfo.setVisibility(View.VISIBLE);
                    goodsdetails_couponinfo.setText(getResources().getString(R.string.coupon_use_nodiscoupon));
                    goodsdetails_couponinfo.setBackgroundColor(getResources().getColor(R.color.novoucher));
                } else if (goodsDetails.getResult().getDisDyq() == false && goodsDetails.getResult().getDisGwq() == true) {
                    goodsdetails_couponinfo.setVisibility(View.VISIBLE);
                    goodsdetails_couponinfo.setText(getResources().getString(R.string.coupon_use_coupon));
                    goodsdetails_couponinfo.setBackgroundColor(getResources().getColor(R.color.nocoupon));
                } else {
                    goodsdetails_couponinfo.setVisibility(View.GONE);
                }
                activityGoodsDetailsGoodsname.setText(goodsDetails.getResult().getNAME());  //商品名
                activityGoodsDetailsGoodsprice.setText("￥ " + goodsDetails.getResult().getPrice() + "");  //商品价格
                activityGoodsDetailsGoodsqiding.setText(goodsDetails.getResult().getMoq() + "");     //商品起订数量
                activityGoodsDetailsGoodskucun.setText(goodsDetails.getResult().getNumber() + "");   //库存量
                activityGoodsDetailsGoodstype.setText(goodsDetails.getResult().getCname());   //商品类型名
                activityGoodsDetailsCartnum.setText(String.valueOf(goodsNum));       //商品起订数量
                activityGoodsDetailsGoodsunit.setText(goodsDetails.getResult().getUnit());
                activityGoodsDetailsGoodsguige.setText(goodsDetails.getResult().getStname());
                activityGoodsDetailsGoodstiaoma.setText(goodsDetails.getResult().getBrcode());
                if (!(goodsDetails.getResult().getBrandName() == null)) {
                    activityGoodsDetailsGoodsbrand.setText(goodsDetails.getResult().getBrandName() + "");
                }
                if (goodsDetails.getResult().getPraise() > 0) {
                    if (goodsDetails.getResult().getPraise() < goodsDetails.getResult().getMoq()) {
                        activityGoodsDetailsGoodspraise.setText(getResources().getString(R.string.select_coupon_dialog_listitem_not_limit_time));
                    } else {
                        activityGoodsDetailsGoodspraise.setText(goodsDetails.getResult().getPraise() + "");
                    }
                    if (goodsDetails.getResult().getMoq() > store) {
                        activityGoodsDetailsCartadd.setText(getResources().getString(R.string.goods_purchase_cantorder));
                        activityGoodsDetailsCartadd.setFocusable(false);
                    }
                    if (goodsDetails.getResult().getPraise() > store) {
                        activityGoodsDetailsGoodspraise.setText(getResources().getString(R.string.select_coupon_dialog_listitem_not_limit_time));
                    }
                } else {
                    activityGoodsDetailsGoodspraise.setText(getResources().getString(R.string.select_coupon_dialog_listitem_not_limit_time));
                }
                activityGoodsDetailsGoodscreatetime.setText(goodsDetails.getResult().getProductionDate());
                super.handleMessage(msg);
                Log.i("test", "当前商品的最低起订量是：" + goodsDetails.getResult().getMoq());
                Log.i("test", "当前商品的库存量是：" + goodsDetails.getResult().getNumber());
                Log.i("test", "当前商品的限订量是：" + goodsDetails.getResult().getPraise());
            }
        };
    }

    /**
     * Title: initData
     * Description:  初始化数据，从ClassifyActivity页面中通过bundle传值获得数据
     * Created by 邻家新锐 on 2016/10/31 16:56
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    private void initData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cartnum = userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size();
                goods_details_loading_success_ll.setVisibility(View.VISIBLE);
                goods_details_loading_fail_ll.setVisibility(View.GONE);
                goodsAllNum = userDao.queryGoodsAllNum(WizarPosApp.getmPersonalInfo().getResult().getId());
            }
        });
        Log.i("test", goodsNo + "");
        if (goodsNo != null) {
            new Thread() {
                @Override
                public void run() {
                    FormBody formBody = new FormBody.Builder()
                            .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                            .add("goodCode", goodsNo)
                            .build();
                    try {
                        Response response = OkHttpUtil.postResponseFormServer(Url.BASE_URL + "goods/goodsDetaild", formBody);
                        Log.i("test_url", Url.BASE_URL + "goods/goodsDetaild");
                        String jsonStr = response.body().string();
                        Log.i("test", jsonStr);
                        GoodsDetail goodsDetails = GsonUtil.parseJsonToBean(jsonStr, GoodsDetail.class);
                        Log.i("test", "解析后的实体类是否为空：" + (goodsDetails == null));
                        if (goodsDetails != null) {
                            Message msg = Message.obtain();
                            msg.obj = goodsDetails;
                            handler.sendMessage(msg);
                        }
                    } catch (IOException e) {
                        loadingfail();
                    }
                }
            }.start();
        } else {
            loadingfail();
        }
    }

    /**
     * Title: onClick
     * Description:  监听事件，响应该页面各个按钮的点击事件（后退，数量选择，购物车等）
     * Created by 邻家新锐 on 2016/11/03 18:13
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_goods_details_top_back_iv:
                finish();
                break;
            case R.id.common_cart:
                Intent cartIntent = new Intent(this, CartActivity.class);
                startActivity(cartIntent);
                break;
            case R.id.activity_goods_details_cartreduce:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (goodsNum > moq) {
                            goodsNum -= 1;
                            activityGoodsDetailsCartnum.setText(goodsNum + "");
                        } else {
                            MyToast.showToast(getResources().getString(R.string.goods_purchase_lastnum));
                            activityGoodsDetailsCartnum.setText(goodsNum + "");
                        }
                    }
                });
                break;
            case R.id.activity_goods_details_cartplus:
                /**
                 * 商品数量增加按钮的监听事件
                 * */
                if (goodsNum < store) {
                    if (praise > 0) {
                        /**
                         * 当商品限订数量大于0时
                         * */
                        if (praise > moq) {
                            if (praise < store && goodsNum < praise) {
                                goodsNum += 1;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        activityGoodsDetailsCartnum.setText(goodsNum + "");
                                    }
                                });
                            } else if (praise < store && goodsNum == praise) {
                                MyToast.showToast(getResources().getString(R.string.goods_purchase_praisenum));
                            } else if (praise > store && goodsNum < praise) {
                                goodsNum += 1;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        activityGoodsDetailsCartnum.setText(goodsNum + "");
                                    }
                                });
                            } else if (praise > store && goodsNum == praise) {
                                MyToast.showToast(getResources().getString(R.string.goods_purchase_praisenum));
                            }
                        }
                        /**
                         * 当限订量小于起订量，不限订数量
                         * */
                        else {
                            if (goodsNum < store) {
                                goodsNum += 1;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        activityGoodsDetailsCartnum.setText(goodsNum + "");
                                    }
                                });
                            } else {
                                MyToast.showToast(getResources().getString(R.string.goods_purchase_praisenum));
                            }

                        }
                    } else {
                        /**
                         * 限订小于0不限定商品数量
                         * */
                        if (goodsNum < store) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    goodsNum += 1;
                                    activityGoodsDetailsCartnum.setText(goodsNum + "");
                                }
                            });
                        } else {
                            MyToast.showToast(getResources().getString(R.string.goods_purchase_praisenum));
                        }
                    }

                } else {
                    MyToast.showToast(getResources().getString(R.string.goods_purchase_storenum));
                }
                break;
            case R.id.activity_goods_details_cartadd:
                /**
                 * 商品添加到购物车的点击监听事件,首先根据商品数量创建购物车中商品对象
                 * */
                if (store == 0) {
                    MyToast.showToast(getResources().getString(R.string.goods_purchase_supplenum));
                    return;
                }
                if (!lock) {
                    MyToast.showToast(this.getString(R.string.not_click));
                    return;
                }
                lock = false;
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            upCartNumberBean = CartNumberHelper.upCartNumber(goodsId, goodsNum);
                            if (upCartNumberBean != null) {
                                /**
                                 * 更新购物车总量
                                 */
                                WizarPosApp.getmPersonalInfo().setCartGoodsNum(upCartNumberBean.getResult().getCartGoodsNum());
                                if (upCartNumberBean.getState() != 0) {

                                    upUI(upCartNumberBean.getResult().getDesc());
                                } else {
                                    upUI(upCartNumberBean.getDesc());
                                }
                            } else {
                                upUI(GoodsDetailsActivity.this.getString(R.string.not_know));
                            }

                        } catch (IOException e) {
                            upCartNumberBean = null;
                            upUI(GoodsDetailsActivity.this.getString(R.string.internet_errer));
                        }
                    }
                });
                break;
            case R.id.goods_details_loading_fail_btn:
                initData();
                break;
        }
    }

    /**
     * @param msg
     * @return void
     * @Title: upUi
     * @Description: 修改购物车数量请求成功后更新ui
     * @date 2017/1/4 18:12
     * @author 陈文豪
     */
    private void upUI(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (msg != null) {
                    MyToast.showToast(msg);
                }
                if (upCartNumberBean.getState() != 0) {
                    cartNum.setText(String.valueOf(upCartNumberBean.getResult().getCartGoodsNum()));
                }

                lock = true;

            }
        });
    }

    /**
     * Title: initView
     * Description:  初始化View
     * Created by 邻家新锐 on 2016/10/31 17:13
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    private void initView() {
        userDao = new UserDao(GoodsDetailsActivity.this);
        user_cartInfos = new ArrayList<>();
        commonTopBackIv = (ImageView) findViewById(R.id.activity_goods_details_top_back_iv);
        commonTopBackIv.setOnClickListener(GoodsDetailsActivity.this);
        commonTopText = (TextView) findViewById(R.id.activity_goods_details_top_text);
        cart = (ImageView) findViewById(R.id.cart);
        cartNum = (TextView) findViewById(R.id.cart_num);
        commonCart = (AbsoluteLayout) findViewById(R.id.common_cart);
        commonCart.setOnClickListener(GoodsDetailsActivity.this);
        activityGoodsDetailsGoodsname = (TextView) findViewById(R.id.activity_goods_details_goodsname);
        activityGoodsDetailsGoodsprice = (TextView) findViewById(R.id.activity_goods_details_goodsprice);
        activityGoodsDetailsGoodsqiding = (TextView) findViewById(R.id.activity_goods_details_goodsqiding);
        activityGoodsDetailsGoodskucun = (TextView) findViewById(R.id.activity_goods_details_goodskucun);
        activityGoodsDetailsGoodstiaoma = (TextView) findViewById(R.id.activity_goods_details_goodstiaoma);
        activityGoodsDetailsGoodsbrand = (TextView) findViewById(R.id.activity_goods_details_goodsbrand);
        activityGoodsDetailsGoodsunit = (TextView) findViewById(R.id.activity_goods_details_goodsunit);
        activityGoodsDetailsGoodstype = (TextView) findViewById(R.id.activity_goods_details_goodstype);
        activityGoodsDetailsGoodsguige = (TextView) findViewById(R.id.activity_goods_details_goodsguige);
        activityGoodsDetailsGoodspraise = (TextView) findViewById(R.id.activity_goods_details_praise);
        activityGoodsDetailsGoodscreatetime = (TextView) findViewById(R.id.activity_goods_details_crteatetime);
        activityGoodsDetailsCartreduce = (Button) findViewById(R.id.activity_goods_details_cartreduce);
        activityGoodsDetailsCartreduce.setOnClickListener(GoodsDetailsActivity.this);
        activityGoodsDetailsCartnum = (TextView) findViewById(R.id.activity_goods_details_cartnum);
        activityGoodsDetailsCartplus = (Button) findViewById(R.id.activity_goods_details_cartplus);
        activityGoodsDetailsCartplus.setOnClickListener(GoodsDetailsActivity.this);
        activityGoodsDetailsCartadd = (Button) findViewById(R.id.activity_goods_details_cartadd);
        activityGoodsDetailsCartadd.setOnClickListener(GoodsDetailsActivity.this);
        goodsdetails_goodsimage = (ImageView) findViewById(R.id.goodsdetails_goodsimage);
        goodsdetails_couponinfo = (TextView) findViewById(R.id.goodsdetails_couponinfo);
        goods_details_loading_fail_ll = (LinearLayout) findViewById(R.id.goods_details_loading_fail_ll);
        goods_details_loading_success_ll = (LinearLayout) findViewById(R.id.goods_details_loading_fail_ll);
        goods_details_loading_fail_btn = (Button) findViewById(R.id.goods_details_loading_fail_btn);
    }

    /**
     * @Title: loadingfail
     * @Description: 当页面网络请求失败后的UI显示的处理
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void loadingfail() {
        Log.i("test", "请求异常！");
        goods_details_loading_success_ll.setVisibility(View.GONE);
        goods_details_loading_fail_ll.setVisibility(View.VISIBLE);
    }

    /**
     * @Title: isEmptyStore
     * @Description: 根据数据库是否为空判断View显示
     * @param: int
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void isEmptyStore(int num) {
        if (num == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityGoodsDetailsCartadd.setText(getResources().getString(R.string.goods_purchase_supplegoods));
                }
            });
        } else {
            activityGoodsDetailsCartadd.setClickable(true);
        }
    }

    /**
     * @Title: onRestart
     * @Description: 当前Activity重新可见后刷新UI
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    protected void onRestart() {
        CartNumberHelper.computerCartSum(cartNum, WizarPosApp.getmPersonalInfo().getCartGoodsNum());
        super.onRestart();
    }

    /**
     * @Title: cartTraverse
     * @Description: 遍历数据库判断是否包含某条商品数据
     * @param: List<User_CartInfo>
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    private boolean cartTraverse(List<User_CartInfo> cartInfos, String goodsname) {
        for (int i = 0; i < cartInfos.size(); i++) {
            if (cartInfos.get(i).getGoodsname().equals(goodsname)) {
                return true;
            }
        }
        return false;
    }
}
