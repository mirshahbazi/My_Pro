package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.CouponShowBean;

/**
* @ClassName: CouponShowGridAdapter
* @Description: 卡券展示列表适配器
* @Data: 2017/1/10 17:47
* @Author: 李鹏鹏
*/
public class CouponShowGridAdapter extends BaseAdapter {
    private Context context;
    private List<CouponShowBean.ResultBean.CanNotUsedListBean> canNotUsedListBeens;
    private List<CouponShowBean.ResultBean.CanUsedListBean> canUsedListBeens;
    private List<CouponShowBean.ResultBean.ExpireListBean> expireListBeens;

    public CouponShowGridAdapter(Context context, List<CouponShowBean.ResultBean.CanNotUsedListBean> canNotUsedListBeens, List<CouponShowBean.ResultBean.CanUsedListBean> canUsedListBeens,
                                 List<CouponShowBean.ResultBean.ExpireListBean> expireListBeens) {
        this.context = context;
        this.canNotUsedListBeens = canNotUsedListBeens;
        this.canUsedListBeens = canUsedListBeens;
        this.expireListBeens = expireListBeens;
    }

    @Override
    public int getCount() {
        if (canNotUsedListBeens != null) {
            return canNotUsedListBeens.size();
        }
        if (canUsedListBeens != null) {
            return canUsedListBeens.size();
        }
        if (expireListBeens != null) {
            return expireListBeens.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (canNotUsedListBeens != null) {
            return canNotUsedListBeens.get(position);
        }
        if (canUsedListBeens != null) {
            return canUsedListBeens.get(position);
        }
        if (expireListBeens != null) {
            return expireListBeens.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.submitactivity_coupon_pic, null);
            holder = new ViewHolder();
            holder.couponType = (TextView) convertView.findViewById(R.id.coupon_pic_classify);
            holder.couponNum = (TextView) convertView.findViewById(R.id.coupon_pic_num);
            holder.couponValue = (TextView) convertView.findViewById(R.id.coupon_pic_value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.couponNum.setVisibility(View.GONE);
        if (canNotUsedListBeens != null) {
            if (canNotUsedListBeens.get(position).getPrefix().equals("g")) {
                holder.couponType.setText(context.getResources().getString(R.string.giftCard_goodsCard));
            } else {
                holder.couponType.setText(context.getResources().getString(R.string.giftCard_useCard));
            }
            holder.couponValue.setText("￥" + canNotUsedListBeens.get(position).getPoint());
        }
        if (canUsedListBeens != null) {
            if (canUsedListBeens.get(position).getPrefix().equals("g")) {
                holder.couponType.setText(context.getResources().getString(R.string.giftCard_goodsCard));
            } else {
                holder.couponType.setText(context.getResources().getString(R.string.giftCard_useCard));
            }
            holder.couponValue.setText("￥" + canUsedListBeens.get(position).getPoint());
        }
        if (expireListBeens != null) {
            if (expireListBeens.get(position).getPrefix().equals("g")) {
                holder.couponType.setText(context.getResources().getString(R.string.giftCard_goodsCard));
            } else {
                holder.couponType.setText(context.getResources().getString(R.string.giftCard_useCard));
            }
            holder.couponValue.setText("$" + expireListBeens.get(position).getPoint());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView couponType;
        TextView couponNum;
        TextView couponValue;
    }
}
