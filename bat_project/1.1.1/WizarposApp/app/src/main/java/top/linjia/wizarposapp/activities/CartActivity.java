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
 * @className: top.linjia.wizarposapp.activities CartActivity
 * @description: 购物车activity类
 * @author 陈文豪
 * @crete 2016/12/26 13:43
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class CartActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView mActivityCartTopBackIv;
    private TextView mActivityCartTopText;
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
    private ArrayList<User_CartInfo> user_cartInfos;
    private LodingDialog lodingDialog;
    private OrderFormGoodsList mOrderFormGoodsList;
    private DecimalFormat df2;

    /**
     * @Title: onCreate
     * @Description:  在创建方法中初始化控件和数据
     * @param savedInstanceState
     * @return
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
       * @Title: initList
       * @Description: 初始化listview
       * @param
       * @date 2016/12/26 17:16
       * @author 陈文豪
       */
    private void initList() {
        df2 =new DecimalFormat("0.00");
        mOrderFormGoodsList = new OrderFormGoodsList(mCartPaymentMoney, mOrderFromTotalCheckbox, 0.00, this, user_cartInfos,"0");
        mOrderFormGoodsList.setShowCheckBox(true);
        mOrderFormGoodsList.setShowOperation(true);
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

     /**
       * @Title: initData
       * @Description: TODO 初始化数据
       * @param
       * @date 2016/12/26 17:16
       * @author 陈文豪
       * @return
       * @throws
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


     /**
       * @Title: onRestart
       * @Description: TODO 在生命周期方法刷新数据
       * @param
       * @date 2016/12/26 17:17
       * @author 陈文豪
       * @return
       * @throws
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
            temp = temp.add(new BigDecimal(user_cartInfos.get(i).getNum()).multiply(
                    new BigDecimal(String.valueOf(user_cartInfos.get(i).getPrice()))));
        }
        return temp;
    }

     /**
       * @Title: nitifyMoney
       * @Description: TODO
       * @param flag
       * @param money
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
     * @Title: initView
     * @Description: 初始化控件 获取控件的对象
     * @param
     * @return
     * @date 2016/12/27 16:17
     * @author 陈文豪
     */
    private void initView() {
        mActivityCartTopBackIv = (ImageView) findViewById(R.id.activity_cart_top_back_iv);
        mActivityCartTopText = (TextView) findViewById(R.id.activity_cart_top_text);
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
       * @Title: onClick
       * @Description: 接受点击事件 并跟去他们的id分别处理他们
       * @param
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
                ArrayList<User_CartInfo> selectedGoodsList = mOrderFormGoodsList.getSelectedGoodsList();
                if(selectedGoodsList.size()==0){
                    MyToast.showToast(this.getString(R.string.not_selected_any_goods));
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
       * @Title: deleteAllGoods
       * @Description: 批量删除商品
       * @param
       * @date 2016/12/26 17:20
       * @author 陈文豪
       */
    private void deleteAllGoods() {
        List<User_CartInfo> selectedGoodsList = mOrderFormGoodsList.getSelectedGoodsList();
        if (selectedGoodsList.size() != 0) {
            if(mOrderFormGoodsList.getmList().removeAll(selectedGoodsList)){
                UserDao userDao = new UserDao(this);
                userDao.deleteListFromCart(selectedGoodsList);
                mOrderFormGoodsList.setmCountMoney(new BigDecimal("0"));
                mCartPaymentMoney.setText("0.00");
                mOrderFormGoodsList.notifyDataSetInvalidated();
            }
        } else {
            MyToast.showToast(this.getString(R.string.not_selected_any_goods));
        }
    }

}
