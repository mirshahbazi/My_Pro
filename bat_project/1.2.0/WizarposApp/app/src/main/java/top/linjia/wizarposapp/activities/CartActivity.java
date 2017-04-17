package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.OrderFormGoodsList;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.CartGoodsbean;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.beans.UpCartNumberBean;
import top.linjia.wizarposapp.customs.LodingDialog;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.parentClass.holder.UpCartNumber;
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
    private String code;
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
        mOrderFormGoodsList = new OrderFormGoodsList(mCartPaymentMoney, mOrderFromTotalCheckbox, 0.00, this, resultBeen, "0");
        mOrderFormGoodsList.setShowCheckBox(true);
        mOrderFormGoodsList.setShowOperation(true);
        if (resultBeen != null) {
            if (resultBeen.size() == 0) {
                mOrderFromTotalCheckbox.setChecked(false);
            }
            mActivityCartList.setAdapter(mOrderFormGoodsList);
        }
        mOrderFromTotalCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox temp = (CheckBox) v;
                notifyMoneyMothod(temp);
            }
        });
        notifyMoneyMothod(mOrderFromTotalCheckbox);
    }

    /**
     * @param temp
     * @return
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
     * @param
     * @return
     * @throws
     * @Title: initData
     * @Description: TODO 初始化数据
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
     * @Description:
     * @date 2017/1/4 17:28
     * @author 陈文豪
     */
    public void upUi(String msg) {
        if (msg != null) {
            MyToast.showToast(msg);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initList();
                lodingDialog.dismiss();
            }
        });
    }


    /**
     * @param
     * @return
     * @throws
     * @Title: onRestart
     * @Description: TODO 在生命周期方法刷新数据
     * @date 2016/12/26 17:17
     * @author 陈文豪
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
        mOrderFormGoodsList.setmCountMoney(new BigDecimal("0"));
        mOrderFormGoodsList.setmTotalMoneyContent("00.00");
        mOrderFromTotalCheckbox.setChecked(false);
    }

    /*
     * @Title: countMoney
     * @Description: TODO 计算list列表里的总值
     * @param
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
     * @Description: TODO
     * @date 2016/12/26 17:19
     * @author 陈文豪
     */
    public void nitifyMoney(boolean flag, BigDecimal money) {
        for (int i = 0; i < mOrderFormGoodsList.getmList().size(); i++) {
            mOrderFormGoodsList.getmList().get(i).setCheckBox(flag);
        }
        mOrderFormGoodsList.notifyDataSetInvalidated();
        mOrderFormGoodsList.setmCountMoney(money);
        mCartPaymentMoney.setText(df2.format(money));
    }

    /**
     * @param
     * @return
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
    }

    /**
     * @param
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
        }
    }

    /**
     * @param
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
                mOrderFormGoodsList.notifyDataSetInvalidated();
            }
        });

    }

    char[] codeChar = new char[13];
    int i = 0;

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (i < 12) {
            codeChar[i] = event.getNumber();
        }
        if (i == 13) {
            codeChar[i] = 5;
            // codeChar[i]=Integer.parseInt(event.getNumber());
        }
        i++;
        searchOrAddGoods(codeChar);
        return super.onKeyDown(keyCode, event);
    }

    *//**
     * @Title: searchOrAddGoods
     * @Description: 搜索商品或者是添加购物车
     * @Params: code
     * @Data: 2017/1/8 19:33
     * @Author: 李鹏鹏
     *//*
    public void searchOrAddGoods(final char[] chars) {
        for (int j = 0; j < 13; j++) {
            Log.i("test", "--" + codeChar[j]);
        }
        for (int i = 0; i < 13; i++) {
            code += chars[i];
        }
        code.trim();
        Log.i("test", "code是：" + code);
        scanCode.setText(null);
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                FormBody formBody3 = new FormBody.Builder()
                        .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                        .add("pageNumber", "1")
                        .add("pageSize", "10")
                        .add("barcode", code)
                        .build();
                try {
                    Response response = OkHttpUtil.postResponseFormServer(Url.BASE_URL + "goods/goodsList", formBody3);
                    String jsonStr = response.body().string();
                    Log.i("test", jsonStr + "---------------------");
                    GoodsList goodsList = GsonUtil.parseJsonToBean(jsonStr, GoodsList.class);
                    if (goodsList != null) {
                        *//**
                         * 获取商品结果并处理
                         * *//*
                        for (int i = 0; i < goodsList.getResult().getList().size(); i++) {
                            FormBody formBody2 = new FormBody.Builder()
                                    .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                                    .add("goodsId", String.valueOf(goodsList.getResult().getList().get(0).getGoodsId()))
                                    .add("number", String.valueOf(goodsList.getResult().getList().get(0).getMoq()))
                                    .build();
                            Response response2 = OkHttpUtil.postResponseFormServer("http://192.168.1.248/linjia-api/api/v1.0/cart/updateCart", formBody2);
                            String jsonStr2 = response2.body().string();
                            Log.i("test", jsonStr2);
                            UpCartNumberBean upCartNumberBean = GsonUtil.parseJsonToBean(jsonStr2, UpCartNumberBean.class);
                            if (upCartNumberBean != null) {
                                if (upCartNumberBean.getState() == 1) {
                                    *//**
                                     * 添加到商品列表并刷新UI
                                     * *//*
                                    if (goodsList.getResult().getList().size() != 0) {
                                        final CartGoodsbean.ResultBean cartGoodsbean = new CartGoodsbean.ResultBean();
                                        cartGoodsbean.setName(goodsList.getResult().getList().get(i).getName());
                                        cartGoodsbean.setDiscPrice(goodsList.getResult().getList().get(i).getPrice());
                                        cartGoodsbean.setStname(goodsList.getResult().getList().get(i).getStname());
                                        cartGoodsbean.setMoq(goodsList.getResult().getList().get(i).getMoq());
                                        cartGoodsbean.setPraise(goodsList.getResult().getList().get(i).getPraise());
                                        cartGoodsbean.setDisGwq(goodsList.getResult().getList().get(i).isDisGwq());
                                        cartGoodsbean.setDisDyq(goodsList.getResult().getList().get(i).isDisDyq());
                                        cartGoodsbean.setCartId(goodsList.getResult().getList().get(i).getGoodsId());
                                        cartGoodsbean.setCartNumber(goodsList.getResult().getList().get(i).getCartNumber());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                resultBeen.add(cartGoodsbean);
                                                addGoodsDialog(getResources().getString(R.string.scan_success));
                                                mOrderFormGoodsList.notifyDataSetChanged();
                                                Log.i("test", "商品起订量是：" + cartGoodsbean.getMoq());
                                                Log.i("test", "商品先定量是：" + cartGoodsbean.getPraise());
                                            }
                                        });
                                    } else {
                                        addGoodsDialog(getResources().getString(R.string.scan_success));
                                    }
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
    }*/

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

}
