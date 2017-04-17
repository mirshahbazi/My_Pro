package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.OrderFormGoodsList;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.customs.LodingDialog;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * Created by 河南巧脉信息技术 on 2016/11/8 16:03
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 * <p/>
 * 购物车activity类
 */

public class CartActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView mActivityCartTopBackIv;
    private TextView mActivityCartTopText;
    private RelativeLayout mActivityCartNavigation;
    private CheckBox mOrderFromTotalCheckbox;
    private Button mActivityCartAlldelete;
    private RelativeLayout mActivityCartAllselectrelativlayout;
    private TextView mCartScanName;
    private TextView mCartScanGuige;
    private TextView mCartScanDanjia;
    private TextView mCartScanNumber;
    private RelativeLayout mActivityCartGoodsTitle;
    private Button mCartPaymentBtn;
    private TextView mCartPaymentMoney;
    private RelativeLayout mActivityCartBottom;
    private ListView mActivityCartList;
    private UserDao userDao;
    private ArrayList<User_CartInfo> user_cartInfos;
    private LodingDialog lodingDialog;
    private OrderFormGoodsList mOrderFormGoodsList;
    private DecimalFormat df2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_cart);
        initView();
        initData();
    }

    private void initList() {
        df2 =new DecimalFormat("0.00");
        mOrderFormGoodsList = new OrderFormGoodsList(mCartPaymentMoney, mOrderFromTotalCheckbox, 0.00, this, user_cartInfos);

        if(user_cartInfos != null) {
            mActivityCartList.setAdapter(mOrderFormGoodsList);
        }
        mOrderFromTotalCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox temp = (CheckBox) v;
                if (temp.isChecked()) {
                    nitifyMoney(true, countMoney());
                } else {
                    nitifyMoney(false, new BigDecimal("0"));
                }

            }
        });
    }

    private void initData() {
        userDao = new UserDao(this);
        lodingDialog = new LodingDialog(this);
        lodingDialog.setmTextView("加载中。。。");
        lodingDialog.show();
        lodingDialog.setCancelable(false);

        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // TODO: 2016/11/21 获取数据库商品 
                user_cartInfos = (ArrayList<User_CartInfo>) userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initList();
                        lodingDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
        mOrderFormGoodsList.setmCountMoney(new BigDecimal("0"));
        mOrderFormGoodsList.setmTotalMoneyContent("00.00");
        mOrderFromTotalCheckbox.setChecked(false);
    }

    /**
     * 计算list列表里的总值
     *
     * @return double类型的总金额
     */
    private BigDecimal countMoney() {
        BigDecimal temp = new BigDecimal("0");
        for (int i = 0; i < mOrderFormGoodsList.getmList().size(); i++) {
            temp = temp.add(new BigDecimal(user_cartInfos.get(i).getNum()).multiply(
                    new BigDecimal(String.valueOf(user_cartInfos.get(i).getPrice()))));
        }
        return temp;
    }

    /**
     * 传入标记 来控制所有的item的选中与否
     * 传入money的值 money是所有商品的总价
     *
     * @param flag
     * @param money
     */
    public void nitifyMoney(boolean flag, BigDecimal money) {
        for (int i = 0; i < mOrderFormGoodsList.getmList().size(); i++) {
            mOrderFormGoodsList.getmList().get(i).setCheckBox(flag);
        }
        mOrderFormGoodsList.notifyDataSetInvalidated();
        mOrderFormGoodsList.setmCountMoney(money);
        mCartPaymentMoney.setText(df2.format(money));
    }

    private void initView() {
        mActivityCartTopBackIv = (ImageView) findViewById(R.id.activity_cart_top_back_iv);
        mActivityCartTopText = (TextView) findViewById(R.id.activity_cart_top_text);
        mActivityCartNavigation = (RelativeLayout) findViewById(R.id.activity_cart_navigation);
        mOrderFromTotalCheckbox = (CheckBox) findViewById(R.id.orderFrom_total_checkbox);
        mActivityCartAlldelete = (Button) findViewById(R.id.activity_cart_alldelete);
        mActivityCartAllselectrelativlayout = (RelativeLayout) findViewById(R.id.activity_cart_allselectrelativlayout);
        mCartScanName = (TextView) findViewById(R.id.cart_scan_name);
        mCartScanGuige = (TextView) findViewById(R.id.cart_scan_guige);
        mCartScanDanjia = (TextView) findViewById(R.id.cart_scan_danjia);
        mCartScanNumber = (TextView) findViewById(R.id.cart_scan_number);
        mActivityCartGoodsTitle = (RelativeLayout) findViewById(R.id.activity_cart_goods_title);
        mCartPaymentBtn = (Button) findViewById(R.id.cart_payment_btn);
        mCartPaymentMoney = (TextView) findViewById(R.id.cart_payment_money);
        mActivityCartBottom = (RelativeLayout) findViewById(R.id.activity_cart_bottom);
        mActivityCartList = (ListView) findViewById(R.id.activity_cart_list);
        mActivityCartAlldelete.setOnClickListener(this);
        mCartPaymentBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_cart_alldelete:
                deleteAllGoods();
                break;
            case R.id.cart_payment_btn:
                ArrayList<User_CartInfo> selectedGoodsList = mOrderFormGoodsList.getSelectedGoodsList();
                if(selectedGoodsList.size()==0){
                    MyToast.showToast("你没有选中任何商品哦(⊙o⊙)");
                    return;
                }
                Intent intent = new Intent(this, SubmitOrderFormActivity.class);
                intent.putParcelableArrayListExtra("payGoodsList",selectedGoodsList);
                intent.putExtra("totalMoney",mOrderFormGoodsList.getmCountMoney());
                startActivity(intent);
                break;
            case R.id.activity_cart_top_back_iv:
                finish();
                break;
        }
    }

    /**
     * 类里写删除数据库条目的语句
     */
    private void deleteAllGoods() {// TODO: 2016/11/18 如果删除时间过长弹出dialog
        List<User_CartInfo> selectedGoodsList = mOrderFormGoodsList.getSelectedGoodsList();
        if (selectedGoodsList.size() != 0) {
            if(mOrderFormGoodsList.getmList().removeAll(selectedGoodsList)){
                UserDao userDao = new UserDao(this);
                userDao.deleteListFromCart(selectedGoodsList);
                mOrderFormGoodsList.setmCountMoney(new BigDecimal("0"));
                mCartPaymentMoney.setText("0.00");
                mOrderFormGoodsList.notifyDataSetInvalidated();
            }

            {

            }
        } else {
            MyToast.showToast("没有选中的项");
        }
    }

}
