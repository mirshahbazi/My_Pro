package top.linjia.wizarposapp.customs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.SelectCouponAdapter;
import top.linjia.wizarposapp.beans.CouponBean;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.utils.couponutils.ComputerCouponHelper;

/**
 * @className: top.linjia.wizarposapp.customs SelectCouponDialog
 * @description: 自定义选择卡券dialog
 * @author 陈文豪
 * @crete 2016/12/26 18:22
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class SelectCouponDialog extends Dialog {

    private String couponType = "g";
    private ListView mCustomDialogCouponListview;
    public final String OFFSET_COUPON = "d";
    public final String SHOP_COUPON = "g";
    private Context mContext;
    private ProgressBar mProgressbar;
    private CouponInfo mCouponInfo;
    private CouponBean request;
    private TextView mTextViewNotCoupon;
    private List<CouponBean.ResultBean.ListBean> mSelectedList = new ArrayList<>();
    private List<CouponBean.ResultBean.ListBean> favorableList;
    private BigDecimal mTotalMoney;

    List<CouponBean.ResultBean.ListBean> couponList;
    //item three view
    private TextView mName;
    private TextView mRule;
    private TextView mDate;
    private List<User_CartInfo> goodList;
    private SelectCouponAdapter selectCouponAdapter;

    /**
     * @Description: 构造方法接受上下文等参数
     * @param context
     * @param couponList
     * @param favorableList
     * @param goodsList
     * @param mTotalMoney
     * @date 2016/12/29 11:19
     * @author 陈文豪
     */
    public SelectCouponDialog(Context context, List<CouponBean.ResultBean.ListBean> couponList,
                              List<CouponBean.ResultBean.ListBean> favorableList, List<User_CartInfo> goodsList,
                              BigDecimal mTotalMoney) {
        super(context, R.style.select_coupon_dialog);
        mContext = context;
        this.couponList = couponList;
        this.favorableList = favorableList;
        this.goodList = goodsList;
        this.mTotalMoney = mTotalMoney;
    }

    /**
     * @Title: onCreate
     * @Description: 创建dialog时候初始化view
     * @param savedInstanceState
     * @return
     * @date 2016/12/29 11:19
     * @author 陈文豪
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.select_coupon_layout);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        initView();
    }

    /**
     * @Title: show
     * @Description: 每次show时重新更新ui
     * @param
     * @return
     * @date 2016/12/29 11:22
     * @author 陈文豪
     */
    @Override
    public void show() {
        super.show();
        mProgressbar.setVisibility(View.GONE);
        mCustomDialogCouponListview.setVisibility(View.VISIBLE);
        mTextViewNotCoupon.setVisibility(View.GONE);
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

     /**
       * @Title: initView
       * @Description: 初始化控件
       * @param
       * @date 2016/12/26 18:20
       * @author 陈文豪
       */
    private void initView() {
        mCustomDialogCouponListview = (ListView) findViewById(R.id.custom_dialog_coupon_listview);
        mProgressbar = (ProgressBar) findViewById(R.id.coupon_loading);
        mTextViewNotCoupon = (TextView) findViewById(R.id.select_coupon_dialog_text);
        mName = (TextView) findViewById(R.id.select_coupon_name);
        mDate = (TextView) findViewById(R.id.select_coupon_date);
        mRule = (TextView) findViewById(R.id.select_coupon_rule);
        selectCouponAdapter = new SelectCouponAdapter(mContext, couponList, favorableList);
        mCustomDialogCouponListview.setAdapter(selectCouponAdapter);
        notifyCouponList(couponType);

        mCustomDialogCouponListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CouponBean.ResultBean.ListBean listBean = couponList.get(position);
                SelectCouponAdapter.ViewHolder tag = (SelectCouponAdapter.ViewHolder) view.getTag();
                listBean.sign = !listBean.sign;
                tag.mSelectCouponFlag.setVisibility(listBean.sign ? View.VISIBLE : View.INVISIBLE);

                notifyCouponList(couponType);
            }
        });
    }

     /**
       * @Title: notifyCouponList
       * @Description: 通知list数据更新
       * @param
       * @date 2016/12/26 18:21
       * @author 陈文豪
       */
    private void notifyCouponList(String couponType){
        /**
         * 判断是shop还是offset、以执行不同的选择规则
         *
         */
        if(couponType.equals(this.SHOP_COUPON)) {
            int selectedNum = 0;
            BigDecimal selectedBd = new BigDecimal("0");
            for (int i = 0; i < couponList.size(); i++) {
                if (couponList.get(i).sign) {
                    selectedBd = selectedBd.add(new BigDecimal(String.valueOf(couponList.get(i).getMinimumQuantity())));
                    selectedNum = selectedNum + couponList.get(i).getSkuNum();
                }
            }

            ComputerCouponHelper.thisListShop thisListShop = ComputerCouponHelper.
                    getThisListShopNotUsable(goodList, new BigDecimal("0"), 0);
            thisListShop.num = goodList.size() - thisListShop.num;
            thisListShop.bd = mTotalMoney.subtract(thisListShop.bd);
            for (int i = 0; i < couponList.size(); i++) {
                CouponBean.ResultBean.ListBean listBean2 = couponList.get(i);
                if (!listBean2.sign) {
                    BigDecimal tempBd = selectedBd.add(new BigDecimal(String.valueOf(listBean2.getMinimumQuantity())));
                    int tempNum = selectedNum + listBean2.getSkuNum();
                    if (thisListShop.bd.compareTo(tempBd) == -1 || tempNum > thisListShop.num) {
                        couponList.get(i).nextUsable = false;
                    } else {
                        couponList.get(i).nextUsable = true;
                    }
                }
            }
        }else{
            for (int i = 0; i < couponList.size(); i++) {
                if(couponList.get(i).sign){
                    for (int j = 0; j < couponList.size(); j++) {
                        if(j != i){
                            couponList.get(j).nextUsable = false;
                        }
                    }
                    break;
                }else if(i == couponList.size() - 1){
                    for (int j = 0; j < couponList.size(); j++) {
                        couponList.get(j).nextUsable = true;
                    }
                }
            }
        }
        selectCouponAdapter.notifyDataSetChanged();
    }

     /**
       * @Title: cancel
       * @Description: 重写回调 点击它时回传数据
       * @param
       * @date 2016/12/26 18:21
       * @author 陈文豪
       */
    @Override
    public void cancel() {
        List<CouponBean.ResultBean.ListBean> tList = new ArrayList<>();
        for (int i = 0; i < couponList.size(); i++) {
            if(couponList.get(i).sign) {
                tList.add(couponList.get(i));
            }
        }

        mCouponInfo.getSelCouponInfo(tList);
        super.cancel();
    }



    public void setItemCouponID(CouponInfo couponInfo){
        mCouponInfo = couponInfo;
    }

    /**
     * @className: top.linjia.wizarposapp.customs SelectCouponDialog
     * @description: 回传已经选择的卡券list接口
     * @author 陈文豪
     * @crete 2016/12/26 18:22
     * @copyright: 2016 河南巧脉信息技术有限公司
     * @email firstwenshao@163.com
     */
    public interface CouponInfo{
        void getSelCouponInfo(List<CouponBean.ResultBean.ListBean> selectedList);
    }
}
