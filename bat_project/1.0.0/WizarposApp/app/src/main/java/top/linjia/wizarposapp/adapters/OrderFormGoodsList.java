package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * Created by 河南巧脉信息技术 on 2016/11/10 14:36
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 *
 * 这个类 购物车中listview的adapter 在这里实现了条目 的checkbox点击事件
 */
public class OrderFormGoodsList extends BaseAdapter {
    private TextView mTotalMoney;
    private CheckBox mTotalCheckBox;
    private BigDecimal mCountMoney;
    private Context mContext;
    private List<User_CartInfo> mList;
    private DecimalFormat df2;

    public OrderFormGoodsList(TextView mTotalMoney, CheckBox mTotalCheckBox, double mCountMoney, Context mContext, List<User_CartInfo> mList) {
        this.mTotalMoney = mTotalMoney;
        this.mTotalCheckBox = mTotalCheckBox;
        this.mCountMoney = new BigDecimal("0");
        this.mContext = mContext;
        this.mList = mList;
        df2 = new DecimalFormat("0.00");
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

    public ArrayList<User_CartInfo> getSelectedGoodsList(){
        ArrayList<User_CartInfo> list = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if(mList.get(i).isCheckBox()) {
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
                if(!userDao.deletefromCart(remove.getGoodsname())){
                    MyToast.showToast("删除商品失败╮(╯▽╰)╭");
                    return;
                }
                if(remove.isCheckBox()) {
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

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mCartItemCheckbox = (CheckBox) rootView.findViewById(R.id.cart_item_checkbox);
            this.mCartItemGoodsName = (TextView) rootView.findViewById(R.id.cart_item_goods_name);
            this.mCartItemStandard = (TextView) rootView.findViewById(R.id.cart_item_standard);
            this.mCartItemPrice = (TextView) rootView.findViewById(R.id.cart_item_price);
            this.mCartItemNumber = (TextView) rootView.findViewById(R.id.cart_item_number);
            this.mCartItemDeleteBtn = (Button) rootView.findViewById(R.id.cart_item_delete_btn);
        }

    }
}
