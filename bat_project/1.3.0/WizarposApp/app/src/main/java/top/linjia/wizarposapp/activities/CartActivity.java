package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.OrderFormGoodsList;
import top.linjia.wizarposapp.apiengine.ConfigurationGet;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.CartGoodsbean;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.beans.UpCartNumberBean;
import top.linjia.wizarposapp.customs.LodingDialog;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @author 陈文豪
 * @className: top.linjia.wizarposapp.activities CartActivity
 * @description: 购物车activity类
 * @crete 2016/12/26 13:43
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class CartActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView mActivityCartTopBackIv;
    private TextView mActivityCartTopText;
    private TextView scanCode;
    private String code = "";
    private RelativeLayout mActivityCartNavigation;
    private CheckBox mOrderFromTotalCheckbox;
    private Button mActivityCartAlldelete;
    private RelativeLayout mActivityCartAllselectrelativlayout;
    private RelativeLayout mActivityCartGoodsTitle;
    private Button mCartPaymentBtn;
    private TextView mCartPaymentMoney;
    private RelativeLayout mActivityCartBottom;
    private ListView mActivityCartList;
    private UserDao userDao;
    private List<CartGoodsbean.ResultBean> resultBeen;
    private LodingDialog lodingDialog;
    private OrderFormGoodsList mOrderFormGoodsList;
    private DecimalFormat df2;
    private String r;
    private TextView mCartExemptFreightText;
    private TextView mCartGoodsKindNumber;

    private Map<String, Object> stringObjectMap;

    /**
     * @param savedInstanceState
     * @return
     * @Title: onCreate
     * @Description: 在创建方法中初始化控件和数据
     * @date 2016/12/27 16:15
     * @author 陈文豪
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_cart);
        initView();
        initData();
    }

    /**
     * @param
     * @Title: initList
     * @Description: 初始化listview
     * @date 2016/12/26 17:16
     * @author 陈文豪
     */
    private void initList() {
        df2 = new DecimalFormat("0.00");
        mOrderFormGoodsList = new OrderFormGoodsList(mCartPaymentMoney, mOrderFromTotalCheckbox, 0.00, this, resultBeen, "0",mCartGoodsKindNumber);
        mOrderFormGoodsList.setShowCheckBox(true);
        mOrderFormGoodsList.setShowOperation(true);
        if (resultBeen != null) {
            if (resultBeen.size() == 0) {
                mOrderFromTotalCheckbox.setChecked(false);
            }
            mActivityCartList.setAdapter(mOrderFormGoodsList);

            mCartGoodsKindNumber.setText(setKindAndNumber());//初始化的时候更新商品种类和数量
        } else {
            mOrderFormGoodsList.setmList(new ArrayList<CartGoodsbean.ResultBean>());
        }
        mOrderFromTotalCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox temp = (CheckBox) v;
                notifyMoneyMothod(temp);
                mCartGoodsKindNumber.setText(setKindAndNumber());//选中的时候更新数量和种类
            }
        });
        notifyMoneyMothod(mOrderFromTotalCheckbox);
    }

    /**
     * @Title: setKindAndNumber
     * @Description: 为购物车的商品种类数量做统计显示
     * @date 2017/1/20 17:04
     * @author 陈文豪
     */
    private SpannableString setKindAndNumber() {
        int kind = 0;
        int count = 0;
        for (int i = 0; i < resultBeen.size(); i++) {
            if(resultBeen.get(i).isCheckBox()) {
                count += resultBeen.get(i).getCartNumber();
                kind += 1;
            }
        }
        //其中的4和1是隔开的间距
        int kindEnd = String.valueOf(kind).length() + 1;
        int countEnd = kindEnd + 4 + String.valueOf(count).length();
        SpannableString builder = new SpannableString(String.format(this.getString(R.string.kind_number), kind, count));
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.RED);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(28);
        AbsoluteSizeSpan absoluteSizeSpan2 = new AbsoluteSizeSpan(28);//这特么的什么设计模式

        builder.setSpan(absoluteSizeSpan2, 1, kindEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(colorSpan2, 1, kindEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setSpan(absoluteSizeSpan, kindEnd + 4, countEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(colorSpan, kindEnd + 4, countEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * @param temp
     * @Title: notifyMoneyMothod
     * @Description: 更新价格
     * @date 2017/1/5 20:50
     * @author 陈文豪
     */
    public void notifyMoneyMothod(CheckBox temp) {
        if (temp.isChecked()) {
            nitifyMoney(true, countMoney());
        } else {
            nitifyMoney(false, new BigDecimal("0"));
        }
    }

    /**
     * @Title: initData
     * @Description: 初始化数据
     * @date 2016/12/26 17:16
     * @author 陈文豪
     */
    private void initData() {
        userDao = new UserDao(this);
        lodingDialog = new LodingDialog(this);
        lodingDialog.setmTextView(this.getString(R.string.loadingIng));
        lodingDialog.show();
        lodingDialog.setCancelable(false);

        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                /**
                 * 获取购物车商品
                 * {"appToken":"390b7fa643bf4ad7b343d3349519fb2c"}
                 */
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("appToken", WizarPosApp.getmPersonalInfo().getAppToken());
                FormBody build = builder.build();
                CartGoodsbean cartGoodsbean = null;
                try {
                    cartGoodsbean = OkHttpUtil.postBeanFormServer(Url.QUERY_CART_TOTAL_GOODS,
                            build, CartGoodsbean.class);
                } catch (IOException e) {
                    upUi(CartActivity.this.getString(R.string.internet_errer));
                } finally {
                    if (cartGoodsbean != null) {
                        resultBeen = cartGoodsbean.getResult();
                    }
                    upUi(null);
                }
            }
        });
    }

    /**
     * @param msg
     * @return void
     * @Title: upUi
     * @Description: 更新界面
     * @date 2017/1/4 17:28
     * @author 陈文豪
     */
    public void upUi(final String msg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initList();
                if (msg != null) {
                    MyToast.showToast(msg);
                }
                lodingDialog.dismiss();
            }
        });
    }


    /**
     * @Title: onRestart
     * @Description: 在生命周期方法刷新数据
     * @date 2016/12/26 17:17
     * @author 陈文豪
     */
    @Override
    protected void onRestart() {
        super.onRestart();

        mOrderFromTotalCheckbox.setChecked(true);
        initData();
        mCartGoodsKindNumber.setText(setKindAndNumber());//回退的时候更新数量和种类
        mOrderFormGoodsList.setmCountMoney(new BigDecimal("0"));
        mOrderFormGoodsList.setmTotalMoneyContent("00.00");
    }


    /*
     * @Title: countMoney
     * @Description: 计算list列表里的总值
     * @return listview中商品的总金额
     * @date 2016/12/26 17:18
     * @author 陈文豪
     */
    private BigDecimal countMoney() {
        BigDecimal temp = new BigDecimal("0");
        for (int i = 0; i < mOrderFormGoodsList.getmList().size(); i++) {
            temp = temp.add(new BigDecimal(resultBeen.get(i).getCartNumber()).multiply(
                    new BigDecimal(String.valueOf(resultBeen.get(i).getDiscPrice()))));
        }
        return temp;
    }

    /**
     * @param flag
     * @param money
     * @Title: nitifyMoney
     * @Description: 更新总价格
     * @date 2016/12/26 17:19
     * @author 陈文豪
     */
    public void nitifyMoney(boolean flag, BigDecimal money) {
        for (int i = 0; i < mOrderFormGoodsList.getmList().size(); i++) {
            mOrderFormGoodsList.getmList().get(i).setCheckBox(flag);
        }
        mOrderFormGoodsList.notifyDataSetChanged();
        mOrderFormGoodsList.setmCountMoney(money);
        mCartPaymentMoney.setText(df2.format(money));
    }

    /**
     * @Title: initView
     * @Description: 初始化控件 获取控件的对象
     * @date 2016/12/27 16:17
     * @author 陈文豪
     */
    private void initView() {
        mActivityCartTopBackIv = (ImageView) findViewById(R.id.activity_cart_top_back_iv);
        mActivityCartTopText = (TextView) findViewById(R.id.activity_cart_top_text);
        scanCode = (TextView) findViewById(R.id.activity_cart_code);
        mActivityCartNavigation = (RelativeLayout) findViewById(R.id.activity_cart_navigation);
        mOrderFromTotalCheckbox = (CheckBox) findViewById(R.id.orderFrom_total_checkbox);
        mActivityCartAlldelete = (Button) findViewById(R.id.activity_cart_alldelete);
        mActivityCartAllselectrelativlayout = (RelativeLayout) findViewById(R.id.activity_cart_allselectrelativlayout);
        mCartPaymentBtn = (Button) findViewById(R.id.cart_payment_btn);
        mCartPaymentMoney = (TextView) findViewById(R.id.cart_payment_money);
        mActivityCartBottom = (RelativeLayout) findViewById(R.id.activity_cart_bottom);
        mActivityCartList = (ListView) findViewById(R.id.activity_cart_list);
        mActivityCartAlldelete.setOnClickListener(this);
        mCartPaymentBtn.setOnClickListener(this);
        mCartGoodsKindNumber = (TextView) findViewById(R.id.cart_goods_kind_number);

        //初始化免运费的文本
        mCartExemptFreightText = (TextView) findViewById(R.id.cart_exempt_freight_text);
        try {
            ConfigurationGet.asycFreightExemptValue(mCartExemptFreightText, this);
        } catch (IOException e) {
            MyToast.showToast(this.getString(R.string.internet_errer));
        }
    }

    /**
     * @param v
     * @Title: onClick
     * @Description: 接受点击事件 并跟去他们的id分别处理他们
     * @date 2016/12/26 17:20
     * @author 陈文豪
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_cart_alldelete:
                deleteAllGoods();
                break;
            case R.id.cart_payment_btn:
                ArrayList<Integer> selectedGoodsList = mOrderFormGoodsList.getSelectedGoodsList();
                if (selectedGoodsList.size() == 0) {
                    MyToast.showToast(this.getString(R.string.not_selected_any_goods));
                    return;
                }
                Intent intent = new Intent(this, SubmitOrderFormActivity.class);
                intent.putIntegerArrayListExtra("cartIds", selectedGoodsList);
                //intent.putExtra("totalMoney", mOrderFormGoodsList.getmCountMoney());
                startActivity(intent);
                break;
            case R.id.activity_cart_top_back_iv:
                finish();
                break;
            case R.id.cart_continue_shop:
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
     * @Title: deleteAllGoods
     * @Description: 批量删除商品
     * @date 2016/12/26 17:20
     * @author 陈文豪
     */
    private void deleteAllGoods() {
        final List<Integer> selectedGoodsList = mOrderFormGoodsList.getSelectedGoodsList();
        if (selectedGoodsList.size() == 0) {
            MyToast.showToast(this.getString(R.string.not_selected_any_goods));
            return;
        }
        final LodingDialog lodingDialogDelete = new LodingDialog(this);
        lodingDialogDelete.setmTextView(this.getString(R.string.delete_ing));
        lodingDialogDelete.show();
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                stringObjectMap = deleteInternetCart(selectedGoodsList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        upUiForDelete(lodingDialogDelete, selectedGoodsList);
                    }
                });
            }
        });

    }

    /**
     * @Title: upUI
     * @Description: 从服务器删除后更新ui
     * @date 2017/1/5 11:55
     * @author 陈文豪
     */
    public void upUiForDelete(LodingDialog lodingDialogDelete, List<Integer> mSelectList) {
        double state = (double) stringObjectMap.get("state");
        String desc = (String) stringObjectMap.get("desc");
        if (state == 0) {
            MyToast.showToast(desc);
            lodingDialogDelete.dismiss();
            return;
        }

        //mOrderFormGoodsList.getmList().removeAll(selectedGoodsList)
        for (int i = 0; i < mOrderFormGoodsList.getmList().size(); i++) {
            for (int j = 0; j < mSelectList.size(); j++) {
                if (mOrderFormGoodsList.getmList().get(i).getCartId() == mSelectList.get(j)) {
                    WizarPosApp.getmPersonalInfo().setCartGoodsNum(
                            WizarPosApp.getmPersonalInfo().getCartGoodsNum() -
                                    mOrderFormGoodsList.getmList().get(i).getCartNumber());

                    mOrderFormGoodsList.getmList().remove(i);
                    mOrderFormGoodsList.notifyDataSetChanged();
                }
            }
        }
        upUI(null);
        mCartGoodsKindNumber.setText(setKindAndNumber());//删除完成更新数量和种类
        lodingDialogDelete.dismiss();
        MyToast.showToast(desc);
    }

    /**
     * @param selectedGoodsList
     * @return Map<String,Object>
     * @Title: deleteInternetCart
     * @Description: 删除服务器上的数据
     * @date 2017/1/5 9:48
     * @author 陈文豪
     */
    private Map<String, Object> deleteInternetCart(final List<Integer> selectedGoodsList) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("appToken", WizarPosApp.getmPersonalInfo().getAppToken());
        map.put("cartIds", selectedGoodsList);
        String json = GsonUtil.parseMapToJson(map);
        try {
            Response deleteResponse = OkHttpUtil.postMapJsonServer(Url.DELETE_SELECT_CART_GOODS, json);
            r = deleteResponse.body().string();
            if (r == null || r.isEmpty()) {
                upUI(CartActivity.this.getString(R.string.not_know));
            }
        } catch (IOException e) {
            upUI(CartActivity.this.getString(R.string.internet_errer));
        }
        Log.e("error", r);
        return GsonUtil.parseJsonToMapObject(r);
    }

    /**
     * @param msg
     * @return void
     * @Title: upUI
     * @Description: 更新ui
     * @date 2017/1/5 9:50
     * @author 陈文豪
     */
    public void upUI(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (msg != null) {
                    MyToast.showToast(msg);
                    return;
                }
                mOrderFormGoodsList.setmCountMoney(new BigDecimal("0"));
                mCartPaymentMoney.setText("0.00");
                mOrderFormGoodsList.notifyDataSetChanged();
            }
        });

    }

    /**
     * @Title: onKeyDown
     * @Description: 监听键盘的输入内容，并且重新拼接成69码
     * @Params: keyCode
     * @Params: event
     * @Data: 2017/1/18 15:16
     * @Author: 李鹏鹏
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() >= KeyEvent.KEYCODE_0 && event.getKeyCode() <= KeyEvent.KEYCODE_9) {
            char key = (char) ('0' + event.getKeyCode() - KeyEvent.KEYCODE_0);
            code += key;
            Log.i("test", code);
            code.trim();
        }
        if (code.length() == 13) {
            Log.i("test", "开始执行搜索扫码功能");
            searchOrAddGoods(code);
            code = "";
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @Title: searchOrAddGoods
     * @Description: 搜索商品或者是添加购物车
     * @Params: code
     * @Data: 2017/1/8 19:33
     * @Author: 李鹏鹏
     */
    public void searchOrAddGoods(final String code) {
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                boolean containGoods = false;
                FormBody formBody3 = new FormBody.Builder()
                        .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                        .add("pageNumber", "1")
                        .add("pageSize", "10")
                        .add("brcode", code)
                        .build();
                try {
                    Response response = OkHttpUtil.postResponseFormServer(Url.BASE_URL + "goods/goodsList", formBody3);
                    String jsonStr = response.body().string();
                    GoodsList goodsList = GsonUtil.parseJsonToBean(jsonStr, GoodsList.class);
                    if (goodsList != null && goodsList.getResult() != null && goodsList.getResult().getList() != null && goodsList.getResult().getList().size() != 0) {
                        /**
                         * 获取商品结果并处理
                         * */
                        for (int i = 0; i < resultBeen.size(); i++) {
                            if (resultBeen.get(i).getGoodsId() == goodsList.getResult().getList().get(0).getGoodsId()) {
                                containGoods = true;
                            }
                        }
                        /**
                         * 根据购物车中是否已经含有该商品，封装不同的请求体
                         * */
                        for (int i = 0; i < goodsList.getResult().getList().size(); i++) {
                            FormBody formBody;
                            if (containGoods) {
                                FormBody formBody2 = new FormBody.Builder()
                                        .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                                        .add("goodsId", String.valueOf(goodsList.getResult().getList().get(0).getGoodsId()))
                                        .add("number", String.valueOf(1))
                                        .build();
                                formBody = formBody2;
                            } else {
                                FormBody formBody2 = new FormBody.Builder()
                                        .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                                        .add("goodsId", String.valueOf(goodsList.getResult().getList().get(0).getGoodsId()))
                                        .add("number", String.valueOf(goodsList.getResult().getList().get(0).getMoq()))
                                        .build();
                                formBody = formBody2;
                            }
                            Response response2 = OkHttpUtil.postResponseFormServer(Url.BASE_URL + "cart/updateCart", formBody);
                            String jsonStr2 = response2.body().string();
                            Log.i("test", "---------" + jsonStr2);
                            UpCartNumberBean upCartNumberBean = GsonUtil.parseJsonToBean(jsonStr2, UpCartNumberBean.class);
                            if (upCartNumberBean != null) {
                                if (upCartNumberBean.getState() == 1 && upCartNumberBean.getResult().getState() == 1) {
                                    /**
                                     * 添加到商品列表并刷新UI
                                     * */
                                    addGoodsDialog(getResources().getString(R.string.scan_success));
                                    WizarPosApp.getmPersonalInfo().setCartGoodsNum(upCartNumberBean.getResult().getCartGoodsNum());
                                    FormBody.Builder builder = new FormBody.Builder();
                                    builder.add("appToken", WizarPosApp.getmPersonalInfo().getAppToken());
                                    FormBody build = builder.build();
                                    CartGoodsbean cartGoodsbean = null;
                                    try {
                                        cartGoodsbean = OkHttpUtil.postBeanFormServer(Url.QUERY_CART_TOTAL_GOODS,
                                                build, CartGoodsbean.class);
                                    } catch (IOException e) {
                                        upUi(CartActivity.this.getString(R.string.internet_errer));
                                    } finally {
                                        if (cartGoodsbean != null) {
                                            resultBeen = cartGoodsbean.getResult();
                                            //   tempGoodsCart(resultBeen,goodsList.getResult().getList().get(0).getGoodsId());
                                        }
                                        upUi(null);
                                    }
                                } else if (upCartNumberBean.getResult().getState() == 0) {
                                    addGoodsDialog(upCartNumberBean.getResult().getDesc());
                                }
                            }
                        }
                    } else {
                        addGoodsDialog(getResources().getString(R.string.goods_category_no_goods));
                    }
                } catch (IOException e) {
                    addGoodsDialog(getResources().getString(R.string.net_wrong));
                }
            }
        });
    }

    /**
     * @Title: addGoodsDialog
     * @Description: 添加购物车提示
     * @Params: str
     * @Data: 2017/1/8 19:33
     * @Author: 李鹏鹏
     */
    public void addGoodsDialog(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyToast.showToast(str);
            }
        });
    }

    /**
     * @Title: tempGoodsCart
     * @Description: 将购物车中商品更改数量后放在商品列表第一条
     * @Params: resultBeen goodsId
     * @Data: 2017/1/18 12:08
     * @Author: 李鹏鹏
     */
    public List<CartGoodsbean.ResultBean> tempGoodsCart(List<CartGoodsbean.ResultBean> resultBeen, int goodsId) {
        for (int i = 0; i < resultBeen.size(); i++) {
            if (resultBeen.get(i).getGoodsId() == goodsId) {
                CartGoodsbean.ResultBean bean = resultBeen.get(i);
                resultBeen.remove(i);
                Collections.reverse(resultBeen);
                resultBeen.add(bean);
                Collections.reverse(resultBeen);
            }
        }
        return resultBeen;
    }

}
