package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * Created by 河南巧脉信息技术 on 2016/11/10 14:36
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 * <p/>
 * 这个类 购物车中listview的adapter 在这里实现了条目 的checkbox点击事件
 */
public class OrderFormGoodsList extends BaseAdapter {
    private TextView mTotalMoney;
    private CheckBox mTotalCheckBox;
    private BigDecimal mCountMoney;
    private Context mContext;
    private List<User_CartInfo> mList;
    private DecimalFormat df2;
    private boolean isShowCheckBox;
    private boolean isShowOperation;

    public OrderFormGoodsList(TextView mTotalMoney, CheckBox mTotalCheckBox, double mCountMoney, Context mContext, List<User_CartInfo> mList, Object moneyBaseValue) {
        this.mTotalMoney = mTotalMoney;
        this.mTotalCheckBox = mTotalCheckBox;
        if (moneyBaseValue instanceof BigDecimal) {
            this.mCountMoney = (BigDecimal) moneyBaseValue;
        } else if (moneyBaseValue instanceof String) {
            this.mCountMoney = new BigDecimal((String) moneyBaseValue);
        } else {
            this.mCountMoney = new BigDecimal("0.0");
        }
        this.mContext = mContext;
        this.mList = mList;
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

    public List<User_CartInfo> getmList() {
        return mList;
    }

    public void setmList(List<User_CartInfo> mList) {
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

    public ArrayList<User_CartInfo> getSelectedGoodsList() {
        ArrayList<User_CartInfo> list = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isCheckBox()) {
                list.add(mList.get(i));
            }
        }
        return list;
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
        mViewHolder.mCartItemCheckbox.setChecked(mList.get(position).isCheckBox());
        mViewHolder.mCartItemGoodsName.setText(mList.get(position).getGoodsname());
        mViewHolder.mCartItemPrice.setText(String.valueOf(mList.get(position).getPrice()));
        mViewHolder.mCartItemNumber.setText(String.valueOf(mList.get(position).getNum()));
        mViewHolder.mCartItemStandard.setText(mList.get(position).getStname());
        mViewHolder.mCartItemMinOrder.setText(String.valueOf(mList.get(position).getMoq()));
        String praise = mList.get(position).getPraise() == 0 ? mContext.getResources().
                getString(R.string.select_coupon_dialog_listitem_not_limit_time) :
                String.valueOf(mList.get(position).getPraise());
        mViewHolder.mCartItemMaxOrder.setText(praise);
        //设置是否显示复选框和操作按钮
        mViewHolder.mCartItemCheckbox.setVisibility(isShowCheckBox ? View.VISIBLE : View.GONE);
        mViewHolder.mCartItemGoodsSubtractBtn.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);
        mViewHolder.getmCartItemGoodsAddBtn.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);
        mViewHolder.mCartItemMaxOrderTag.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);
        mViewHolder.mCartItemMinOrderTag.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);
        mViewHolder.mCartItemMinOrder.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);
        mViewHolder.mCartItemMaxOrder.setVisibility(isShowOperation ? View.VISIBLE : View.GONE);


        int disDyq = mList.get(position).getDisDyq();
        int disGwq = mList.get(position).getDisGwq();
        if (disDyq == disGwq) {//判断两个是否相等 两种情况 都可用 都不可用
            if (disDyq == 1) {//如果都可用
                mViewHolder.mCartItemFavorableType.setVisibility(View.GONE);//不显示提示信息
            } else {//如果都不可用
                mViewHolder.mCartItemFavorableType.setVisibility(View.VISIBLE);//显示提示信息
                mViewHolder.mCartItemFavorableType.setText(R.string.cartItem_fvorable_type_not_tatol);
            }
        } else {//如果有可用的有不可用的
            mViewHolder.mCartItemFavorableType.setVisibility(View.VISIBLE);//显示提示信息
            mViewHolder.mCartItemFavorableType.setText(disDyq == 0 ? R.string.cartItem_fvorable_type_not_offsetCart :
                    R.string.cartItem_fvorable_type_not_shopCart);//三元表达式判断是哪一种
        }

        /**
         * 设置按钮是否可减
         */
        if(mList.get(position).getMoq() == mList.get(position).getNum()&&mList.get(position).isUsableSubject()){
            mList.get(position).setUsableSubject(false);
        }else if(!mList.get(position).isUsableSubject()){
            mList.get(position).setUsableSubject(true);
        }
        boolean usableSubject = mList.get(position).isUsableSubject();
        mViewHolder.mCartItemGoodsSubtractBtn.setImageResource(usableSubject ? R.mipmap.goodsnum_reduce:R.mipmap.not_use_substract);//根据标记设置可否点击和显示
        mViewHolder.mCartItemGoodsSubtractBtn.setEnabled(usableSubject);

        /**
         *设置按钮是否可加
         */
        if(mList.get(position).getPraise() == mList.get(position).getNum()&&mList.get(position).isUsableAdd()){
            mList.get(position).setUsableAdd(false);
        }else if(!mList.get(position).isUsableAdd()){
            mList.get(position).setUsableAdd(true);
        }
        boolean usableAdd = mList.get(position).isUsableAdd();
        mViewHolder.getmCartItemGoodsAddBtn.setImageResource(usableAdd ? R.mipmap.goodsnum_plus:R.mipmap.not_use_add);
        mViewHolder.getmCartItemGoodsAddBtn.setEnabled(usableAdd);

        mViewHolder.mCartItemGoodsSubtractBtn.setOnClickListener(new View.OnClickListener() {

            User_CartInfo user_cartInfo = mList.get(position);
            String id = String.valueOf(user_cartInfo.getGoodsId());
            UserDao userDao = new UserDao(mContext);
            private boolean flag;

            @Override
            public void onClick(final View v) {
                if (user_cartInfo.getNum() == user_cartInfo.getMoq() || flag) {
                    MyToast.showToast("~~~~(>_<)~~~~ 歇会吧");
                    return;
                }
                flag = true;
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        final boolean b = userDao.subtractGoodsNumber(id);
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                coreMethod(b);
                            }
                        });
                    }
                });

            }

            private void coreMethod(boolean b) {
                if (b) {
                    int num = user_cartInfo.getNum() - 1;
                    user_cartInfo.setNum(num);
                    mViewHolder.mCartItemNumber.setText(String.valueOf(num));
                    ImageView mCartItemGoodsSubtractBtn = mViewHolder.mCartItemGoodsSubtractBtn;
                    if (num == user_cartInfo.getMoq()) {
                        mCartItemGoodsSubtractBtn.setImageResource(R.mipmap.not_use_substract);
                        mCartItemGoodsSubtractBtn.setEnabled(false);
                        user_cartInfo.setUsableSubject(false);
                        // 这里设置不可点击的按钮标记 减号
                    }
                    if (!mViewHolder.getmCartItemGoodsAddBtn.isEnabled()) {
                        mViewHolder.getmCartItemGoodsAddBtn.setImageResource(R.mipmap.goodsnum_plus);
                        mViewHolder.getmCartItemGoodsAddBtn.setEnabled(true);
                        user_cartInfo.setUsableAdd(true);
                        //这里设置按钮恢复点击的标记 加号
                    }

                    if (mViewHolder.mCartItemCheckbox.isChecked() || !isShowCheckBox) {
                        mCountMoney = mCountMoney.subtract(new BigDecimal(String.valueOf(user_cartInfo.getPrice())));
                        mTotalMoney.setText(df2.format(mCountMoney));
                    }
                    flag = false;
                } else {
                    MyToast.showToast("~~~~(>_<)~~~~ 删减失败了");
                    flag = false;
                }
            }
        });

        mViewHolder.getmCartItemGoodsAddBtn.setOnClickListener(new View.OnClickListener() {

            User_CartInfo user_cartInfo = mList.get(position);
            private boolean flag;
            String id = String.valueOf(user_cartInfo.getGoodsId());
            UserDao userDao = new UserDao(mContext);

            @Override
            public void onClick(final View v) {
                if (user_cartInfo.getNum() == user_cartInfo.getPraise() || flag) {
                    MyToast.showToast("QAQ 等下~~~ 让我算算");
                    return;
                }
                flag = true;
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        final boolean b = userDao.addGoodsNumber(id);
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                coreMethod(b);
                            }
                        });
                    }
                });


            }

            private void coreMethod(boolean b) {
                if (b) {
                    int num = user_cartInfo.getNum() + 1;
                    user_cartInfo.setNum(num);
                    mViewHolder.mCartItemNumber.setText(String.valueOf(num));
                    ImageView getmCartItemGoodsAddBtn = mViewHolder.getmCartItemGoodsAddBtn;
                    if (num == user_cartInfo.getPraise()) {
                        getmCartItemGoodsAddBtn.setImageResource(R.mipmap.not_use_add);
                        getmCartItemGoodsAddBtn.setEnabled(false);
                        user_cartInfo.setUsableAdd(false);
                        //这里设置不可点击的标记 加号
                    }
                    if (!mViewHolder.mCartItemGoodsSubtractBtn.isEnabled()) {
                        mViewHolder.mCartItemGoodsSubtractBtn.setImageResource(R.mipmap.goodsnum_reduce);
                        mViewHolder.mCartItemGoodsSubtractBtn.setEnabled(true);
                        user_cartInfo.setUsableSubject(true);
                        //这里设置恢复减号点击的标记
                    }

                    if (mViewHolder.mCartItemCheckbox.isChecked() || !isShowCheckBox) {
                        mCountMoney = mCountMoney.add(new BigDecimal(String.valueOf(user_cartInfo.getPrice())));
                        mTotalMoney.setText(df2.format(mCountMoney));
                    }

                    flag = false;
                } else {
                    MyToast.showToast("~~~~(>_<)~~~~ 添加失败了,请你慢一点");
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
            }
        });
        mViewHolder.mCartItemDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_CartInfo remove = mList.remove(position);
                UserDao userDao = new UserDao(mContext);
                if (!userDao.deletefromCart(remove.getGoodsname())) {
                    MyToast.showToast("删除商品失败╮(╯▽╰)╭");
                    return;
                }
                if (remove.isCheckBox()) {
                    mCountMoney = mCountMoney.subtract(new BigDecimal(remove.getNum()).
                            multiply(new BigDecimal(String.valueOf(remove.getPrice()))));
                    mTotalMoney.setText(df2.format(mCountMoney));
                }
                notifyDataSetChanged();
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
        public TextView mCartItemFavorableType;
        public TextView mCartItemMinOrder;
        public TextView mCartItemMaxOrder;
        public ImageView mCartItemGoodsSubtractBtn;
        public ImageView getmCartItemGoodsAddBtn;
        public TextView mCartItemMinOrderTag;
        public TextView mCartItemMaxOrderTag;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mCartItemFavorableType = (TextView) rootView.findViewById(R.id.cart_item_favorable_type);
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
}
