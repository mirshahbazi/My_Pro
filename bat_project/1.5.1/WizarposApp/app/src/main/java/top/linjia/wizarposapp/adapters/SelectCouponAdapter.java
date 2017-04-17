package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.CouponBean;

/**
 * Created by 河南巧脉信息技术 on 2016/12/16 15:49
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class SelectCouponAdapter extends BaseAdapter {

    private Context mContext;
    private List<CouponBean.ResultBean.ListBean> mList;
    private String pat1 = "yyyy-MM-dd HH:mm:ss.SSS";
    private String pat2 = "yyyy年MM月dd日";
    private List<CouponBean.ResultBean.ListBean> favorableList;
    SimpleDateFormat sdf1;
    SimpleDateFormat sdf2;

    public SelectCouponAdapter(Context mContext, List<CouponBean.ResultBean.ListBean> mList,
                               List<CouponBean.ResultBean.ListBean> favorableList) {
        this.mContext = mContext;
        this.mList = mList;
        if(favorableList != null) {
            for (int i = 0; i < favorableList.size(); i++) {
                int indexOf = this.mList.indexOf(favorableList.get(i));
                if (indexOf != -1) {
                    this.mList.get(indexOf).sign = true;
                }
            }
        }
        sdf1 = new SimpleDateFormat(pat1) ;
        sdf2 = new SimpleDateFormat(pat2) ;
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
            convertView = View.inflate(mContext, R.layout.select_coupon_dialog_item, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        CouponBean.ResultBean.ListBean listBean = mList.get(position);
        String format = null;
        try {
            if (listBean.getExpiryDate()==null){
                throw new ParseException("not parse null",000);
            }
            format = sdf2.format(sdf1.parse(listBean.getExpiryDate()));
        } catch (ParseException e) {
            format = mContext.getResources().getString(R.string.select_coupon_dialog_listitem_not_limit_time);
        }
        mViewHolder.mSelectCouponDate.setText(format);
        mViewHolder.mSelectCouponRule.setText(listBean.getIntroduction());
        mViewHolder.mSelectCouponName.setText(listBean.getNAME());
        mViewHolder.mSelectCouponFlag.setVisibility(listBean.sign ? View.VISIBLE : View.INVISIBLE);
        if(listBean.nextUsable){
            mViewHolder.mSelectCouponNotUsable.setVisibility(View.INVISIBLE);
            mViewHolder.rootView.setEnabled(true);
        }else{
            mViewHolder.mSelectCouponNotUsable.setVisibility(View.VISIBLE);
            mViewHolder.rootView.setEnabled(false);
        }
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView mSelectCouponFlag;
        public TextView mSelectCouponName;
        public TextView mSelectCouponRule;
        public TextView mSelectCouponDate;
        public TextView mSelectCouponNotUsable;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mSelectCouponFlag = (ImageView) rootView.findViewById(R.id.select_coupon_flag);
            this.mSelectCouponName = (TextView) rootView.findViewById(R.id.select_coupon_name);
            this.mSelectCouponRule = (TextView) rootView.findViewById(R.id.select_coupon_rule);
            this.mSelectCouponDate = (TextView) rootView.findViewById(R.id.select_coupon_date);
            this.mSelectCouponNotUsable = (TextView) rootView.findViewById(R.id.select_coupon_not_usable);
        }

    }

    @Override
    public boolean isEnabled(int position) {
        return mList.get(position).nextUsable;
    }
}
