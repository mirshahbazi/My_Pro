package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.User_CartInfo;

/**
 * Created by 河南巧脉信息技术 on 2016/11/14 09:58
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class SubmitOrderFormAdapter extends BaseAdapter {

    private ArrayList<User_CartInfo> mList;
    private Context mContext;

    public SubmitOrderFormAdapter(ArrayList<User_CartInfo> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.commit_orderform_list_item, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        if(position == 0){
            Log.e("submit",mList.get(position).getGoodsname());
        }
        mViewHolder.mCommitOrderformItemName.setText(mList.get(position).getGoodsname());
        mViewHolder.mCommitOrderformItemNumber.setText(String.valueOf(mList.get(position).getNum()));
        mViewHolder.mCommitOrderformItemPrice.setText(String.valueOf(mList.get(position).getPrice()));
        mViewHolder.mCommitOrderformItemStandard.setText(mList.get(position).getStname());
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView mCommitOrderformItemName;
        public TextView mCommitOrderformItemStandard;
        public TextView mCommitOrderformItemPrice;
        public TextView mCommitOrderformItemNumber;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mCommitOrderformItemName = (TextView) rootView.findViewById(R.id.commit_orderform_item_name);
            this.mCommitOrderformItemStandard = (TextView) rootView.findViewById(R.id.commit_orderform_item_standard);
            this.mCommitOrderformItemPrice = (TextView) rootView.findViewById(R.id.commit_orderform_item_price);
            this.mCommitOrderformItemNumber = (TextView) rootView.findViewById(R.id.commit_orderform_item_number);
        }

    }
}
