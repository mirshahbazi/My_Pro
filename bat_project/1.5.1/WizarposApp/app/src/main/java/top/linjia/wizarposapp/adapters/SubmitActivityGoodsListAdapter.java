package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.activities.SubmitOrderFormActivity;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.CartGoodsbean;
import top.linjia.wizarposapp.beans.SubmitOrderAdapterAddSubOperBean;
import top.linjia.wizarposapp.beans.SubmitOrderBean;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.helperComputer.CartNumberHelper;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

public class SubmitActivityGoodsListAdapter extends BaseAdapter {
    private TextView mTotalMoney;  //总额
    private TextView sendMoney;  //运费
    private TextView payMoney;   //实付
    private LinearLayout mActivitySubmitOrderFormCouponShow; //卡券展示区
    private View areaForCouponShow;
    private TextView couponType;   //卡券类型
    private TextView couponNum;   //卡券数量
    private TextView couponValue;  //卡券面值
    private TextView mActivitySubmitOrderFormLinjiaCoinsUse;
    private CheckBox mTotalCheckBox;
    private BigDecimal mCountMoney;
    private Context mContext;
    private List<CartGoodsbean.ResultBean> mList;
    private DecimalFormat df2;
    private boolean isShowCheckBox;
    private boolean isShowOperation;
    private static final int REFRESH_UI = 1;   //刷新UI
    private static final int REFRESH_ITEM = 2;  //刷新商品条目数量
    private Handler handler;
    private TextView mSubmitFavorable;
    private TextView mSubmitGoodsKindNumber;

    private double payMon;

    /**
     * @Title: initHandler
     * @Description: 点击加减号后刷新Ui
     * @Params:
     * @Data: 2017/1/7 17:45
     * @Author: 李鹏鹏
     */
    public void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REFRESH_UI:
                        SubmitOrderAdapterAddSubOperBean soab = (SubmitOrderAdapterAddSubOperBean) msg.obj;
                        Log.i("test", "Handler执行");/**/

                        double payMoneyTemp = soab.getResult().getPayMoney();
                        double money = soab.getResult().getMoney();
                        double sendMoney = soab.getResult().getSendMoney();
                        String moneyFlag = mContext.getString(R.string.fragmentCard_renminbi);
                        StringBuilder builder = new StringBuilder("-");
                        builder.append(moneyFlag);
                        BigDecimal favorableMoney = new BigDecimal(money).subtract(new BigDecimal(payMoneyTemp)).
                                add(new BigDecimal(sendMoney));//计算优惠的额度
                        builder.append(df2.format(favorableMoney.doubleValue() < 0 ? 0 : favorableMoney.doubleValue()));
                        mSubmitFavorable.setText(builder);
                        mTotalMoney.setText(String.valueOf(money));
                        SubmitActivityGoodsListAdapter.this.sendMoney.setText(String.valueOf(sendMoney));
                        SubmitActivityGoodsListAdapter.this.payMoney.setText(String.valueOf(payMoneyTemp));
                        CartNumberHelper.cartSumCount(null, soab.getmCartCount());
                        List<SubmitOrderBean.ResultBean.CouponsBean> couponBean = soab2src(soab.getResult().getCoupons());
                        List<SubmitOrderBean.ResultBean.CouponsBean> shopList = new ArrayList<>();
                        List<SubmitOrderBean.ResultBean.CouponsBean> offsetList = new ArrayList<>();
                        SubmitOrderFormActivity.classifyCoupon(couponBean, shopList, offsetList);
                        HashMap<Integer, HashMap<Integer, Integer>> map = SubmitOrderFormActivity.parseCouponForShow(shopList, offsetList);
                        mActivitySubmitOrderFormCouponShow.removeAllViews();
                        SubmitOrderFormActivity.showCoupon(mContext, map, mActivitySubmitOrderFormCouponShow, areaForCouponShow, couponType, couponNum, couponValue);
                        mActivitySubmitOrderFormLinjiaCoinsUse.setText("￥" + soab.getResult().getPayBylinjiaMoney());
                        if (soab.getResult().getCoupons().size() == 0) {
                            mActivitySubmitOrderFormCouponShow.removeAllViews();
                        }
                        mActivitySubmitOrderFormCouponShow.invalidate();
                        break;
                    case REFRESH_ITEM:
                        int position = msg.arg1;
                        int cartNum = msg.arg2;
                        final TextView cartItemNum = (TextView) msg.obj;
                        Log.i("test", "刷新item");
                        cartItemNum.setText(String.valueOf(cartNum));

                        mSubmitGoodsKindNumber.setText(setKindAndNumber());
                        cartItemNum.invalidate();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    public SubmitActivityGoodsListAdapter(LinearLayout mActivitySubmitOrderFormCouponShow, View areaForCouponShow, TextView mTotalMoney, TextView sendMoney, TextView payMoney, CheckBox mTotalCheckBox, Context mContext,
                                          List<CartGoodsbean.ResultBean> mList, Object moneyBaseValue, TextView couponType, TextView couponNum, TextView couponValue, TextView mActivitySubmitOrderFormLinjiaCoinsUse, double payMon, TextView mSubmitFavorable
    ,TextView mSubmitGoodsKindNumber) {
        initHandler();
        this.mTotalMoney = mTotalMoney;
        this.sendMoney = sendMoney;
        this.payMoney = payMoney;
        this.mTotalCheckBox = mTotalCheckBox;
        this.mActivitySubmitOrderFormCouponShow = mActivitySubmitOrderFormCouponShow;
        this.areaForCouponShow = areaForCouponShow;
        this.couponType = couponType;
        this.couponNum = couponNum;
        this.couponValue = couponValue;
        this.mActivitySubmitOrderFormLinjiaCoinsUse = mActivitySubmitOrderFormLinjiaCoinsUse;
        this.payMon = payMon;
        if (moneyBaseValue instanceof BigDecimal) {
            this.mCountMoney = (BigDecimal) moneyBaseValue;
        } else if (moneyBaseValue instanceof String) {
            this.mCountMoney = new BigDecimal((String) moneyBaseValue);
        } else {
            this.mCountMoney = new BigDecimal("0.0");
        }
        this.mContext = mContext;
        this.mList = mList;
        this.mSubmitFavorable = mSubmitFavorable;
        this.mSubmitGoodsKindNumber = mSubmitGoodsKindNumber;
        df2 = new DecimalFormat("0.00");
    }

    public boolean isShowOperation() {
        return isShowOperation;
    }

    public void setShowOperation(boolean showOperation) {
        isShowOperation = showOperation;
    }

    public TextView getmTotalMoney() {
        return mTotalMoney;
    }

    public void setmTotalMoneyContent(String msg) {
        this.mTotalMoney.setText(msg);
    }

    public BigDecimal getmCountMoney() {
        return mCountMoney;
    }

    public void setmCountMoney(BigDecimal mCountMoney) {
        this.mCountMoney = mCountMoney;
    }


    @Override
    public int getCount() {
        return this.mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.cart_list_item, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mCartItemGoodsName.setText(mList.get(position).getName());
        mViewHolder.mCartItemPrice.setText(String.valueOf(mList.get(position).getDiscPrice()));
        mViewHolder.mCartItemNumber.setText(String.valueOf(mList.get(position).getCartNumber()));
        mViewHolder.mCartItemStandard.setText(mList.get(position).getStname());
        mViewHolder.mCartItemMinOrder.setText(String.valueOf(mList.get(position).getMoq()));
        String praise = mList.get(position).getPraise() == 0 ? mContext.getResources().
                getString(R.string.select_coupon_dialog_listitem_not_limit_time) :
                String.valueOf(mList.get(position).getPraise());
        mViewHolder.mCartItemMaxOrder.setText(praise);
        /**
         * 设置是否显示复选框和操作按钮
         * */
        mViewHolder.mCartItemCheckbox.setVisibility(isShowCheckBox ? View.VISIBLE : View.GONE);  //isshowCheckBox默认为false
        mViewHolder.mCartItemGoodsSubtractBtn.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);   // 控制减号的显示
        mViewHolder.getmCartItemGoodsAddBtn.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);    //   控制加号的显示
        mViewHolder.mCartItemMaxOrderTag.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);       //   起订的显示
        mViewHolder.mCartItemMinOrderTag.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);       //   限订的显示
        mViewHolder.mCartItemMinOrder.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);          //   起订数量的显示
        mViewHolder.mCartItemMaxOrder.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);         //    限订数量的显示

        /**
         * 商品使用卡券类型的显示
         * */
        int disDyq = (mList.get(position).isDisDyq() ? 0 : 1);
        int disGwq = (mList.get(position).isDisGwq() ? 0 : 1);
        if (disDyq == disGwq) {//判断两个是否相等 两种情况 都可用或都不可用
            if (disDyq == 1) {//如果都可用
                mViewHolder.mCartItemFavorableType.setVisibility(View.GONE);//不显示提示信息
            } else {//如果都不可用
                mViewHolder.mCartItemFavorableType.setVisibility(View.VISIBLE);//显示提示信息
//                mViewHolder.mCartItemFavorableType.setText(R.string.cartItem_fvorable_type_not_tatol);
            }
        } else {//如果有可用的有不可用的
            mViewHolder.mCartItemFavorableType.setVisibility(View.VISIBLE);//显示提示信息
//            mViewHolder.mCartItemFavorableType.setText(disDyq == 0 ? R.string.cartItem_fvorable_type_not_offsetCart :
//                    R.string.cartItem_fvorable_type_not_shopCart);//三元表达式判断是哪一种
        }
        /**
         * 设置按钮是否可减
         */
        if (mList.get(position).getMoq() == mList.get(position).getNumber() && mList.get(position).isUsableSubject()) {
            mList.get(position).setUsableSubject(false);
        } else if (!mList.get(position).isUsableSubject()) {
            mList.get(position).setUsableSubject(true);
        }
        boolean usableSubject = mList.get(position).isUsableSubject();
        mViewHolder.mCartItemGoodsSubtractBtn.setImageResource(usableSubject ? R.mipmap.goodsnum_reduce : R.mipmap.not_use_substract);//根据标记设置可否点击和显示
        mViewHolder.mCartItemGoodsSubtractBtn.setEnabled(usableSubject);

        /**
         *设置按钮是否可加
         */
        if (mList.get(position).getPraise() == mList.get(position).getNumber() && mList.get(position).isUsableAdd()) {
            mList.get(position).setUsableAdd(false);
        } else if (!mList.get(position).isUsableAdd()) {
            mList.get(position).setUsableAdd(true);
        }
        boolean usableAdd = mList.get(position).isUsableAdd();
        mViewHolder.getmCartItemGoodsAddBtn.setImageResource(usableAdd ? R.mipmap.goodsnum_plus : R.mipmap.not_use_add);
        mViewHolder.getmCartItemGoodsAddBtn.setEnabled(usableAdd);

        /**
         * 点击减号减少商品数量
         * */
        mViewHolder.mCartItemGoodsSubtractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.i("test", String.valueOf(mList.get(position).getCartNumber()));
                if (mList.get(position).getCartNumber() > 1) {
                    cartItemOper(Url.BASE_URL + "cart/updateCheckedCartItem", position, mList.get(position).getCartNumber() - 1, mViewHolder.mCartItemNumber);
                }
            }

            /**
             * 加减号操作后控制加减按钮的显示
             * */

        });
        /**
         * 点击加号增加商品数量
         * */
        mViewHolder.getmCartItemGoodsAddBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                cartItemOper(Url.BASE_URL + "cart/updateCheckedCartItem", position, mList.get(position).getCartNumber() + 1, mViewHolder.mCartItemNumber);
            }

        });

        mViewHolder.mCartItemCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double price = Double.parseDouble(mViewHolder.mCartItemPrice.getText().toString());
                int number = Integer.parseInt(mViewHolder.mCartItemNumber.getText().toString());

                if (((CheckBox) v).isChecked()) {
                    mCountMoney = mCountMoney.add(new BigDecimal(String.valueOf(price)).multiply(new BigDecimal(number)));
                } else {
                    mCountMoney = mCountMoney.subtract(new BigDecimal(String.valueOf(price)).multiply(new BigDecimal(number)));
                    if (mTotalCheckBox.isChecked()) {
                        mTotalCheckBox.setChecked(false);
                    }
                }
                mList.get(position).setCheckBox(((CheckBox) v).isChecked());
                mTotalMoney.setText(df2.format(mCountMoney));
            }
        });
        return convertView;
    }


    public static class ViewHolder {
        public View rootView;
        public CheckBox mCartItemCheckbox;
        public TextView mCartItemGoodsName;
        public TextView mCartItemStandard;
        public TextView mCartItemPrice;
        public TextView mCartItemNumber;
        public Button mCartItemDeleteBtn;
        public ImageView mCartItemFavorableType;
        public TextView mCartItemMinOrder;
        public TextView mCartItemMaxOrder;
        public ImageView mCartItemGoodsSubtractBtn;
        public ImageView getmCartItemGoodsAddBtn;
        public TextView mCartItemMinOrderTag;
        public TextView mCartItemMaxOrderTag;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mCartItemFavorableType = (ImageView) rootView.findViewById(R.id.cart_item_favorable_type);
            this.mCartItemCheckbox = (CheckBox) rootView.findViewById(R.id.cart_item_checkbox);
            this.mCartItemGoodsName = (TextView) rootView.findViewById(R.id.cart_item_goods_name);
            this.mCartItemStandard = (TextView) rootView.findViewById(R.id.cart_item_standard);
            this.mCartItemPrice = (TextView) rootView.findViewById(R.id.cart_item_price);
            this.mCartItemNumber = (TextView) rootView.findViewById(R.id.cart_item_number);
            this.mCartItemDeleteBtn = (Button) rootView.findViewById(R.id.cart_item_delete_btn);
            mCartItemMinOrder = (TextView) rootView.findViewById(R.id.cart_item_minOrder);
            mCartItemMaxOrder = (TextView) rootView.findViewById(R.id.cart_item_maxOrder);
            mCartItemGoodsSubtractBtn = (ImageView) rootView.findViewById(R.id.cart_item_goods_subtract_btn);
            getmCartItemGoodsAddBtn = (ImageView) rootView.findViewById(R.id.cart_item_goods_add_btn);
            mCartItemMinOrderTag = (TextView) rootView.findViewById(R.id.cart_item_minOrder_tag);
            mCartItemMaxOrderTag = (TextView) rootView.findViewById(R.id.cart_item_maxOrder_tag);
        }

    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public void cartItemOper(final String url, final int position, int cartnumber, final TextView cartitemnum) {
        final FormBody formbody = new FormBody.Builder()
                .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                .add("cartId", String.valueOf(mList.get(position).getCartId()))
                .add("cartNumber", String.valueOf(cartnumber))
                .build();
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = OkHttpUtil.postResponseFormServer(url, formbody);
                    String jsonStr = response.body().string();
                    Log.i("test", jsonStr);
                    SubmitOrderAdapterAddSubOperBean submitOrderAdapterAddSubOperBean = GsonUtil.parseJsonToBean(jsonStr, SubmitOrderAdapterAddSubOperBean.class);

                    submitOrderAdapterAddSubOperBean.setmCartCount(CartNumberHelper.getCartNumber());//获取购物车总数量
                    if (submitOrderAdapterAddSubOperBean != null) {
                        Message msg = Message.obtain();
                        msg.what = REFRESH_UI;
                        msg.obj = submitOrderAdapterAddSubOperBean;
                        Message msg1 = Message.obtain();
                        msg1.arg1 = position;
                        msg1.arg2 = submitOrderAdapterAddSubOperBean.getResult().getCartItems().get(position).getCartNumber();
                        msg1.what = REFRESH_ITEM;
                        msg1.obj = cartitemnum;
                        mList.get(position).setCartNumber(submitOrderAdapterAddSubOperBean.getResult().getCartItems().get(position).getCartNumber());
                        handler.sendMessage(msg1);
                        handler.sendMessage(msg);
                        TextView cartItemNum = cartitemnum;
                        payMon = submitOrderAdapterAddSubOperBean.getResult().getPayMoney();

                    } else {
                        MyToast.showToast(mContext.getResources().getString(R.string.goods_purchase_wrong));
                    }
                } catch (IOException e) {
                    MyToast.showToast(mContext.getResources().getString(R.string.net_wrong));
                }
            }
        });
    }

    /**
     * @Title: soab2src
     * @Description: 将点击加减号返回的实体类中的卡券列表转化为提交订单实体类列表并
     * @Params: couponsBeen
     * @Data: 2017/1/7 17:10
     * @Author: 李鹏鹏
     */
    public ArrayList<SubmitOrderBean.ResultBean.CouponsBean> soab2src(List<SubmitOrderAdapterAddSubOperBean.ResultBean.CouponsBean> couponsBeen) {
        ArrayList<SubmitOrderBean.ResultBean.CouponsBean> subb = new ArrayList<>();
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
            subb.add(b);
        }
        return subb;
    }

    /**
     * @Title: setKindAndNumber
     * @Description: 为购物车的商品种类数量做统计显示
     * @date 2017/1/20 17:04
     * @author 陈文豪
     */
    private SpannableString setKindAndNumber() {
        int kind = mList.size();
        int count = 0;
        for (int i = 0; i < mList.size(); i++) {
            count += mList.get(i).getCartNumber();
        }
        //其中的4和1是隔开的间距
        int kindEnd = String.valueOf(kind).length() + 1;
        int countEnd = kindEnd + 4 + String.valueOf(count).length();
        SpannableString builder = new SpannableString(String.format(mContext.getString(R.string.kind_number), kind, count));
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.RED);

        builder.setSpan(colorSpan2, 1, kindEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(colorSpan, kindEnd + 4, countEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

}
