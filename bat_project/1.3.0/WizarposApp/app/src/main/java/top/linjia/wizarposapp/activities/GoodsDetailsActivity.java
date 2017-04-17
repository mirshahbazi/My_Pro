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
import top.linjia.wizarposapp.beans.UpCartNumberBean;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.helperComputer.CartNumberHelper;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;
import top.linjia.wizarposapp.view.dialog.UpCartNumberDialog;

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
    protected TextView couponDot;
    protected TextView couponInfo;
    ImageView goodsdetails_goodsimage;
    TextView goodsdetails_couponinfo;
    protected TextView cartNum;
    protected AbsoluteLayout commonCart;
    LinearLayout goods_details_loading_fail_ll, goods_details_loading_success_ll;
    Button goods_details_loading_fail_btn;
    Button go_cart;
    protected TextView activityGoodsDetailsGoodsname;
    protected TextView activityGoodsDetailsGoodsprice;
    protected TextView activityGoodsDetailsGoodskucun;
    protected TextView activityGoodsDetailsGoodsbrand;
    protected TextView activityGoodsDetailsGoodsunit;
    protected TextView activityGoodsDetailsGoodstype;
    protected TextView activityGoodsDetailsGoodsguige;
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
    private UpCartNumberDialog upCartNumberDialog;
    private int thisCartNum;
    private ImageView mGoodsDetailsSoldOut;

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
                CartNumberHelper.cartSumCount(cartNum, WizarPosApp.getmPersonalInfo().getCartGoodsNum());
                moq = goodsDetails.getResult().getMoq();
                store = goodsDetails.getResult().getNumber();
                goodsname = goodsDetails.getResult().getNAME();
                stname = goodsDetails.getResult().getStname();
                price = goodsDetails.getResult().getPrice();
                goodsId = goodsDetails.getResult().getGoodsId();
                upCartNumberDialog.setGoodsId(goodsId);
                praise = goodsDetails.getResult().getPraise();
                moq = goodsDetails.getResult().getMoq();
                thisCartNum = goodsDetails.getResult().getCartNumber();

                /**
                 * 判断是否有货 如果没货做出响应
                 */
                if(goodsDetails.getResult().getNumber() < goodsDetails.getResult().getMoq()){
                    activityGoodsDetailsCartreduce.setEnabled(false);
                    activityGoodsDetailsCartnum.setEnabled(false);
                    activityGoodsDetailsCartplus.setEnabled(false);
                    activityGoodsDetailsCartreduce.setBackgroundResource(R.mipmap.not_use_substract);
                    activityGoodsDetailsCartplus.setBackgroundResource(R.mipmap.not_use_add);
                    mGoodsDetailsSoldOut.setVisibility(View.VISIBLE);
                }
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
                //.setBackgroundColor();
                goodsdetails_couponinfo.setVisibility(View.VISIBLE);
                goodsdetails_couponinfo.getBackground().setAlpha(100);
                goodsdetails_couponinfo.setText(goodsDetails.getResult().getBrcode());
                if (goodsDetails.getResult().getDisDyq() == true && goodsDetails.getResult().getDisGwq() == true) {
                    couponDot.setVisibility(View.VISIBLE);
                    couponInfo.setText(getResources().getString(R.string.coupon_use_nodiscount));
                    //goodsdetails_couponinfo.setBackgroundColor(getResources().getColor(R.color.nodiscount));
                } else if (goodsDetails.getResult().getDisDyq() == true && goodsDetails.getResult().getDisGwq() == false) {
                    couponDot.setVisibility(View.VISIBLE);
                    couponInfo.setText(getResources().getString(R.string.coupon_use_nodiscoupon));
                   // goodsdetails_couponinfo.setBackgroundColor(getResources().getColor(R.color.novoucher));
                } else if (goodsDetails.getResult().getDisDyq() == false && goodsDetails.getResult().getDisGwq() == true) {
                    couponDot.setVisibility(View.VISIBLE);
                    couponInfo.setText(getResources().getString(R.string.coupon_use_coupon));
                  //  goodsdetails_couponinfo.setBackgroundColor(getResources().getColor(R.color.nocoupon));
                } else {
                    couponDot.setVisibility(View.GONE);
                    couponInfo.setVisibility(View.VISIBLE);
                }
                go_cart.setText(getResources().getString(R.string.go_cart)+"("+String.valueOf(WizarPosApp.getmPersonalInfo().getCartGoodsNum())+")");
                activityGoodsDetailsGoodsname.setText(goodsDetails.getResult().getNAME());  //商品名
                activityGoodsDetailsGoodsprice.setText("￥ " + goodsDetails.getResult().getPrice() + "");  //商品价格
                activityGoodsDetailsGoodskucun.setText(goodsDetails.getResult().getNumber() + "");   //库存量
                activityGoodsDetailsGoodstype.setText(goodsDetails.getResult().getCname());   //商品类型名
                activityGoodsDetailsCartnum.setText(String.valueOf(thisCartNum == 0 ? moq : thisCartNum));       //商品起订数量
                activityGoodsDetailsGoodsunit.setText(goodsDetails.getResult().getUnit());
                activityGoodsDetailsGoodsguige.setText(goodsDetails.getResult().getStname());
                if (!(goodsDetails.getResult().getBrandName() == null)) {
                    activityGoodsDetailsGoodsbrand.setText(goodsDetails.getResult().getBrandName() + "");
                }
              /*  if (goodsDetails.getResult().getPraise() > 0) {
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
                }*/
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
                go_cart.setText(getResources().getString(R.string.go_cart)+"("+String.valueOf(WizarPosApp.getmPersonalInfo().getCartGoodsNum())+")");
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingfail();
                            }
                        });
                    }
                }
            }.start();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingfail();
                }
            });
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
                if (!lock) {
                    MyToast.showToast(this.getString(R.string.not_click));
                    return;
                }
                lock = false;
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            upCartNumberBean = CartNumberHelper.subtractCartNumber(goodsId);
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
            case R.id.activity_goods_details_cartplus:
                if (!lock) {
                    MyToast.showToast(this.getString(R.string.not_click));
                    return;
                }
                lock = false;
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (thisCartNum == 0) {
                                upCartNumberBean = CartNumberHelper.upCartNumber(goodsId, moq);
                            } else {
                                upCartNumberBean = CartNumberHelper.addCartNumber(goodsId);
                            }
                            if (upCartNumberBean != null) {
                                /**
                                 * 更新购物车总量
                                 */
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
            case R.id.activity_goods_details_cartnum:
                upCartNumberDialog.showUpDialog();
                break;
            case R.id.goods_details_gocart:
                Intent intent=new Intent(GoodsDetailsActivity.this,CartActivity.class);
                startActivity(intent);
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
                go_cart.setText(getResources().getString(R.string.go_cart)+"("+String.valueOf(WizarPosApp.getmPersonalInfo().getCartGoodsNum())+")");
                if (msg != null) {
                    MyToast.showToast(msg);
                }
                if (upCartNumberBean.getState() != 0) {
                    CartNumberHelper.cartSumCount(cartNum, upCartNumberBean.getResult().getCartGoodsNum());
                } else {
                    return;
                };

                if (upCartNumberBean.getResult().getNumber() != 0) {
                    thisCartNum = upCartNumberBean.getResult().getCartGoodsNum();

                    activityGoodsDetailsCartnum.setText(String.valueOf(upCartNumberBean.getResult().getNumber()));
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
        mGoodsDetailsSoldOut = (ImageView) findViewById(R.id.goods_details_sold_out);
        go_cart= (Button) findViewById(R.id.goods_details_gocart);
        activityGoodsDetailsGoodsname = (TextView) findViewById(R.id.activity_goods_details_goodsname);
        activityGoodsDetailsGoodsprice = (TextView) findViewById(R.id.activity_goods_details_goodsprice);
        activityGoodsDetailsGoodskucun = (TextView) findViewById(R.id.activity_goods_details_goodskucun);
        activityGoodsDetailsGoodsbrand = (TextView) findViewById(R.id.activity_goods_details_goodsbrand);
        activityGoodsDetailsGoodsunit = (TextView) findViewById(R.id.activity_goods_details_goodsunit);
        activityGoodsDetailsGoodstype = (TextView) findViewById(R.id.activity_goods_details_goodstype);
        activityGoodsDetailsGoodsguige = (TextView) findViewById(R.id.activity_goods_details_goodsguige);
        activityGoodsDetailsGoodscreatetime = (TextView) findViewById(R.id.activity_goods_details_crteatetime);
        activityGoodsDetailsCartreduce = (Button) findViewById(R.id.activity_goods_details_cartreduce);
        activityGoodsDetailsCartreduce.setOnClickListener(GoodsDetailsActivity.this);
        activityGoodsDetailsCartnum = (TextView) findViewById(R.id.activity_goods_details_cartnum);
        activityGoodsDetailsCartplus = (Button) findViewById(R.id.activity_goods_details_cartplus);
        activityGoodsDetailsCartplus.setOnClickListener(GoodsDetailsActivity.this);
        activityGoodsDetailsCartadd = (Button) findViewById(R.id.activity_goods_details_cartadd);
        activityGoodsDetailsCartadd.setOnClickListener(GoodsDetailsActivity.this);
        goodsdetails_goodsimage = (ImageView) findViewById(R.id.goodsdetails_goodsimage);
        couponDot= (TextView) findViewById(R.id.goods_details_coupondot);
        couponInfo= (TextView) findViewById(R.id.goods_details_couponinfo);
        goodsdetails_couponinfo = (TextView) findViewById(R.id.goodsdetails_couponinfo);
        goods_details_loading_fail_ll = (LinearLayout) findViewById(R.id.goods_details_loading_fail_ll);
        goods_details_loading_success_ll = (LinearLayout) findViewById(R.id.goods_details_loading_fail_ll);
        goods_details_loading_fail_btn = (Button) findViewById(R.id.goods_details_loading_fail_btn);
        upCartNumberDialog = UpCartNumberDialog.getUpCartNumberDialog(this, goodsId, cartNum, activityGoodsDetailsCartnum);
        upCartNumberDialog.prepareShow();

    }

    /**
     * @Title: loadingfail
     * @Description: 当页面网络请求失败后的UI显示的处理
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
     * @param: num
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
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    protected void onRestart() {
        initData();
        upCartNumberDialog = UpCartNumberDialog.getUpCartNumberDialog(this, goodsId, cartNum, activityGoodsDetailsCartnum);
        upCartNumberDialog.prepareShow();
        CartNumberHelper.cartSumCount(cartNum, WizarPosApp.getmPersonalInfo().getCartGoodsNum());
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
