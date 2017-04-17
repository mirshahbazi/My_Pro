package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.CartGoodsbean;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.helperComputer.CartNumberHelper;
import top.linjia.wizarposapp.utils.MyToast;
import top.linjia.wizarposapp.view.dialog.InquiryDialog;
import top.linjia.wizarposapp.view.dialog.UpCartNumberDialog;

/**
 * @author 陈文豪
 * @className: top.linjia.wizarposapp.adapters OrderFormGoodsList
 * @description: 提交订单界面 购物车界面的adapter
 * @crete 2017/1/4 16:06
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class OrderFormGoodsList extends BaseAdapter {
    private TextView mTotalMoney;
    private CheckBox mTotalCheckBox;
    private BigDecimal mCountMoney;
    private Context mContext;
    private List<CartGoodsbean.ResultBean> mList;
    private DecimalFormat df2;
    private boolean isShowCheckBox;
    private boolean isShowOperation;
    private TextView mCartGoodsKindNumber;
    InquiryDialog inquiryDialog;

    public OrderFormGoodsList(TextView mTotalMoney, CheckBox mTotalCheckBox, double mCountMoney, Context mContext, List<CartGoodsbean.ResultBean> mList, Object moneyBaseValue
            , TextView mCartGoodsKindNumber) {
        this.mTotalMoney = mTotalMoney;
        this.mTotalCheckBox = mTotalCheckBox;
        if (moneyBaseValue instanceof BigDecimal) {
            this.mCountMoney = (BigDecimal) moneyBaseValue;
        } else if (moneyBaseValue instanceof String) {
            this.mCountMoney = new BigDecimal((String) moneyBaseValue);
        } else {
            this.mCountMoney = new BigDecimal("0.0");
        }
        this.mCartGoodsKindNumber = mCartGoodsKindNumber;
        this.mContext = mContext;
        this.mList = mList;
        inquiryDialog = InquiryDialog.getInquiryDialog(mContext);
        inquiryDialog.prepareShow(null);
        df2 = new DecimalFormat("0.00");
    }

    public boolean isShowOperation() {
        return isShowOperation;
    }

    public void setShowOperation(boolean showOperation) {
        isShowOperation = showOperation;
    }

    public boolean isShowCheckBox() {
        return isShowCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        isShowCheckBox = showCheckBox;
    }

    public TextView getmTotalMoney() {
        return mTotalMoney;
    }

    public void setmTotalMoneyContent(String msg) {
        this.mTotalMoney.setText(msg);
    }

    public CheckBox getmTotalCheckBox() {
        return mTotalCheckBox;
    }

    public void setmTotalCheckBox(CheckBox mTotalCheckBox) {
        this.mTotalCheckBox = mTotalCheckBox;
    }

    public BigDecimal getmCountMoney() {
        return mCountMoney;
    }

    public void setmCountMoney(BigDecimal mCountMoney) {
        this.mCountMoney = mCountMoney;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<CartGoodsbean.ResultBean> getmList() {
        return mList;
    }

    public void setmList(List<CartGoodsbean.ResultBean> mList) {
        this.mList = mList;
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

    public ArrayList<Integer> getSelectedGoodsList() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isCheckBox()) {
                list.add(mList.get(i).getCartId());
            }
        }
        return list;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return View
     * @Title: getView
     * @Description: 提供Listview的item的方法
     * @date 2017/1/9 11:16
     * @author 陈文豪
     */
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
        mViewHolder.mCartItemCheckbox.setChecked(mList.get(position).isCheckBox());
        mViewHolder.mCartItemGoodsName.setText(mList.get(position).getName());
        mViewHolder.mCartItemPrice.setText(String.valueOf(mList.get(position).getDiscPrice()));
        mViewHolder.mCartItemNumber.setText(String.valueOf(mList.get(position).getCartNumber()));
        mViewHolder.mCartItemStandard.setText(mList.get(position).getStname());
        mViewHolder.mCartItemMinOrder.setText(String.valueOf(mList.get(position).getMoq()));
        mViewHolder.mCartItemMaxOrder.setText(String.valueOf(mList.get(position).getPraise()));
        //设置是否显示复选框和操作按钮
        mViewHolder.mCartItemCheckbox.setVisibility(isShowCheckBox ? View.VISIBLE : View.GONE);
        mViewHolder.mCartItemGoodsSubtractBtn.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);
        mViewHolder.getmCartItemGoodsAddBtn.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);
        mViewHolder.mCartItemMaxOrderTag.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);
        mViewHolder.mCartItemMinOrderTag.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);
        mViewHolder.mCartItemMinOrder.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);
        mViewHolder.mCartItemMaxOrder.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);

        /**
         * 指定限订按钮显示方式，’不限/n‘两种方式
         * */
        if (mList.get(position).getPraise() == 0 && mViewHolder.mCartItemMaxOrder.getVisibility() != View.GONE) {
            mViewHolder.mCartItemMaxOrder.setVisibility(View.GONE);
            mViewHolder.mCartItemMaxOrderTag.setVisibility(View.GONE);
        } else if (mList.get(position).getPraise() != 0 && mViewHolder.mCartItemMaxOrder.getVisibility() != View.VISIBLE) {
            mViewHolder.mCartItemMaxOrder.setVisibility(View.VISIBLE);
            mViewHolder.mCartItemMaxOrderTag.setVisibility(View.VISIBLE);
        }
        /**
         * 指定起订显示方式
         */
        if (mList.get(position).getMoq() == 1 && mViewHolder.mCartItemMinOrder.getVisibility() != View.GONE) {
            mViewHolder.mCartItemMinOrder.setVisibility(View.GONE);
            mViewHolder.mCartItemMinOrderTag.setVisibility(View.GONE);
        } else if (mList.get(position).getMoq() != 1 && mViewHolder.mCartItemMinOrder.getVisibility() != View.VISIBLE) {
            mViewHolder.mCartItemMinOrder.setVisibility(View.VISIBLE);
            mViewHolder.mCartItemMinOrderTag.setVisibility(View.VISIBLE);
        }


        isOperation(position, mViewHolder);


        int disDyq = mList.get(position).isDisDyq() ? 0 : 1;
        int disGwq = mList.get(position).isDisGwq() ? 0 : 1;

        if (disDyq == disGwq) {//判断两个是否相等 两种情况 都可用 都不可用
            if (disDyq == 1) {//如果都可用
                mViewHolder.mCartItemFavorableType.setVisibility(View.GONE);//不显示提示信息
            } else {//如果都不可用
                mViewHolder.mCartItemFavorableType.setVisibility(View.VISIBLE);//显示提示信息
                mViewHolder.mCartItemFavorableType.setImageResource(R.mipmap.not_favorable_total);
            }
        } else {//如果有可用的有不可用的
            mViewHolder.mCartItemFavorableType.setVisibility(View.VISIBLE);//显示提示信息
            mViewHolder.mCartItemFavorableType.setImageResource(disDyq == 0 ? R.mipmap.not_favorable_deduction :
                    R.mipmap.not_favorable_shop);//三元表达式判断是哪一种
        }


        mViewHolder.mCartItemGoodsSubtractBtn.setOnClickListener(new View.OnClickListener() {

            CartGoodsbean.ResultBean resultBean = mList.get(position);
            private boolean flag;
            int numberMethod;

            @Override
            public void onClick(final View v) {
                if (resultBean.getCartNumber() == resultBean.getMoq()) {
                    MyToast.showToast(mContext.getString(R.string.equals_min_number));
                    return;
                }

                if (flag) {
                    MyToast.showToast("~~~~(>_<)~~~~ 歇会吧");
                    return;
                }
                flag = true;
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            numberMethod = CartNumberHelper.
                                    upCartNumberMethod(
                                            CartNumberHelper.SUBSTRAT_CART_NUMBER,
                                            position, mList, mContext);
                            upNumberUi(null);
                        } catch (IOException e) {
                            upNumberUi(mContext.getString(R.string.internet_errer));
                        }

                    }
                });

            }

            /**
             * 更新视图
             * @param msg
             */
            private void upNumberUi(String msg) {
                if (msg != null) {
                    CartNumberHelper.upUI(msg, mContext);
                    return;
                }

                CartNumberHelper.upUI(new Runnable() {
                    @Override
                    public void run() {
                        coreMethod(numberMethod != -1);
                    }
                }, mContext);
            }

            /**
             * @Title: coreMethod
             * @Description: 传入删除成功是否的boolean值 内部自动进行判断 更新ui
             * @param b
             * @return android.view.View
             * @date 2017/1/5 15:58
             * @author 陈文豪
             */
            private void coreMethod(boolean b) {
                if (b) {
                    int num = resultBean.getCartNumber() - 1;
                    resultBean.setCartNumber(num);
                    mViewHolder.mCartItemNumber.setText(String.valueOf(num));
                    mCartGoodsKindNumber.setText(setKindAndNumber());
                    ImageView mCartItemGoodsSubtractBtn = mViewHolder.mCartItemGoodsSubtractBtn;
//                    if (num == resultBean.getMoq()) {
//                        mCartItemGoodsSubtractBtn.setImageResource(R.mipmap.not_use_substract);
//                        mCartItemGoodsSubtractBtn.setEnabled(false);
//                        resultBean.setUsableSubject(false);
//                        // 这里设置不可点击的按钮标记 减号
//                    }
//                    if (!mViewHolder.getmCartItemGoodsAddBtn.isEnabled()) {
//                        mViewHolder.getmCartItemGoodsAddBtn.setImageResource(R.mipmap.goodsnum_plus);
//                        mViewHolder.getmCartItemGoodsAddBtn.setEnabled(true);
//                        resultBean.setUsableAdd(true);
//                        //这里设置按钮恢复点击的标记 加号
//                    }

                    if (mViewHolder.mCartItemCheckbox.isChecked() || !isShowCheckBox) {
                        mCountMoney = mCountMoney.subtract(new BigDecimal(String.valueOf(resultBean.getDiscPrice())));
                        mTotalMoney.setText(df2.format(mCountMoney));
                    }

                    resultBean.setCartNumber(numberMethod);
                    mViewHolder.mCartItemNumber.setText(String.valueOf(mList.get(position).getCartNumber()));
                    flag = false;
                } else {
                    MyToast.showToast("~~~~(>_<)~~~~ 删减失败了");
                    flag = false;
                }
            }
        });

        mViewHolder.getmCartItemGoodsAddBtn.setOnClickListener(new View.OnClickListener() {

            private int numberMethod;
            CartGoodsbean.ResultBean resultBean = mList.get(position);
            private boolean flag;
            String id = String.valueOf(resultBean.getGoodsId());

            @Override
            public void onClick(final View v) {
                if (resultBean.getCartNumber() == resultBean.getPraise()) {
                    MyToast.showToast(mContext.getString(R.string.equals_max_number));
                    return;
                }

                if (flag) {
                    MyToast.showToast(mContext.getString(R.string.not_click));
                    return;
                }
                flag = true;
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            numberMethod = CartNumberHelper.
                                    upCartNumberMethod(
                                            CartNumberHelper.ADD_CART_NUMBER,
                                            position, mList, mContext);
                            upNumberUi(null);
                        } catch (IOException e) {
                            upNumberUi(mContext.getString(R.string.internet_errer));
                        }
                    }
                });


            }

            /**
             * 更新视图
             * @param msg
             */
            private void upNumberUi(String msg) {
                if (msg != null) {
                    CartNumberHelper.upUI(msg, mContext);
                    return;
                }

                CartNumberHelper.upUI(new Runnable() {
                    @Override
                    public void run() {
                        coreMethod(numberMethod != -1);
                    }
                }, mContext);
            }

            private void coreMethod(boolean b) {
                if (b) {
                    int num = resultBean.getCartNumber() + 1;
                    resultBean.setCartNumber(num);
                    mViewHolder.mCartItemNumber.setText(String.valueOf(num));
                    mCartGoodsKindNumber.setText(setKindAndNumber());
                    ImageView getmCartItemGoodsAddBtn = mViewHolder.getmCartItemGoodsAddBtn;
//                    if (num == resultBean.getPraise()) {
//                        getmCartItemGoodsAddBtn.setImageResource(R.mipmap.not_use_add);
//                        getmCartItemGoodsAddBtn.setEnabled(false);
//                        resultBean.setUsableAdd(false);
//                        //这里设置不可点击的标记 加号
//                    }
//                    if (!mViewHolder.mCartItemGoodsSubtractBtn.isEnabled()) {
//                        mViewHolder.mCartItemGoodsSubtractBtn.setImageResource(R.mipmap.goodsnum_reduce);
//                        mViewHolder.mCartItemGoodsSubtractBtn.setEnabled(true);
//                        resultBean.setUsableSubject(true);
//                        //这里设置恢复减号点击的标记
//                    }

                    if (mViewHolder.mCartItemCheckbox.isChecked() || !isShowCheckBox) {
                        mCountMoney = mCountMoney.add(new BigDecimal(String.valueOf(resultBean.getDiscPrice())));
                        mTotalMoney.setText(df2.format(mCountMoney));
                    }

                    resultBean.setCartNumber(numberMethod);
                    mViewHolder.mCartItemNumber.setText(String.valueOf(numberMethod));

                    flag = false;
                } else {
                    flag = false;
                }
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
                mCartGoodsKindNumber.setText(setKindAndNumber());
            }
        });

        mViewHolder.mCartItemNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpCartNumberDialog upCartNumberDialog = UpCartNumberDialog.getUpCartNumberDialog(mContext, mList.get(position).getGoodsId(),
                        null, mViewHolder.mCartItemNumber);
                upCartNumberDialog.setmUpUnknown(new UpCartNumberDialog.upUnknown() {
                    @Override
                    public void upSucceedBody(Map<String, Object> map) {
                        int upNumber = ((Double) map.get("number")).intValue();
                        if (mList.get(position).isCheckBox()) {
                            //算出增加的数量
                            int number = upNumber - mList.get(position).getCartNumber();

                            BigDecimal bigDecimal = new BigDecimal(number).multiply
                                    (new BigDecimal(mList.get(position).getDiscPrice()));
                            mCountMoney = mCountMoney.add(bigDecimal);

                            mTotalMoney.setText(df2.format(mCountMoney));
                        }

                        mList.get(position).setCartNumber(upNumber);
                        mCartGoodsKindNumber.setText(setKindAndNumber());
                    }

                    @Override
                    public void upFailureBody() {

                    }
                });
                upCartNumberDialog.prepareShow();
                upCartNumberDialog.showUpDialog();
            }
        });

        mViewHolder.mCartItemDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inquiryDialog.setIsEnter(new InquiryDialog.IsEnter() {
                    @Override
                    public void enter() {
                        Log.d("error","enter");
                    }

                    @Override
                    public void dismiss() {
                        Log.d("error","dismiss");
                    }
                });
                inquiryDialog.showDialog();
            }
        });
        return convertView;
    }

    public void isOperation(int position, ViewHolder mViewHolder) {

        CartGoodsbean.ResultBean temp = mList.get(position);
        int cartNumber = temp.getCartNumber();
        int number = temp.getNumber();
        /**
         * 设置按钮是否可减
         */
//        if (temp.getMoq() == cartNumber && temp.isUsableSubject()) {
//            temp.setUsableSubject(false);
//        } else if (!temp.isUsableSubject() && temp.getMoq() != cartNumber) {
//            temp.setUsableSubject(true);
//        }
//        boolean usableSubject = temp.isUsableSubject();
//        mViewHolder.mCartItemGoodsSubtractBtn.setImageResource(usableSubject ? R.mipmap.goodsnum_reduce : R.mipmap.not_use_substract);//根据标记设置可否点击和显示
//        mViewHolder.mCartItemGoodsSubtractBtn.setEnabled(usableSubject);
//
//        /**
//         *设置按钮是否可加
//         */
//        if (temp.getPraise() == cartNumber && temp.isUsableAdd()) {
//            temp.setUsableAdd(false);
//        } else if (!temp.isUsableAdd() && temp.getPraise() != cartNumber) {
//            temp.setUsableAdd(true);
//        }
//        boolean usableAdd = temp.isUsableAdd();
//        mViewHolder.getmCartItemGoodsAddBtn.setImageResource(usableAdd ? R.mipmap.goodsnum_plus : R.mipmap.not_use_add);
//        mViewHolder.getmCartItemGoodsAddBtn.setEnabled(usableAdd);
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

    /**
     * @Title: setKindAndNumber
     * @Description: 为购物车的商品种类数量做统计显示
     * @date 2017/1/20 17:04
     * @author 陈文豪
     */
    private SpannableString setKindAndNumber() {
        int kind = 0;
        int count = 0;
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isCheckBox()) {
                count += mList.get(i).getCartNumber();
                kind += 1;
            }
        }
        //其中的4和1是隔开的间距
        int kindEnd = String.valueOf(kind).length() + 1;
        int countEnd = kindEnd + 4 + String.valueOf(count).length();
        SpannableString builder = new SpannableString(String.format(mContext.getString(R.string.kind_number), kind, count));
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

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
