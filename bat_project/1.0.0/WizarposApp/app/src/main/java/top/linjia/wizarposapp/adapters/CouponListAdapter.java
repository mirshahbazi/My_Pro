package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.CouponBean;

public class CouponListAdapter extends BaseAdapter{
    Context context;
    List<CouponBean.ResultBean.ListBean> data;
    public CouponListAdapter(Context context,List<CouponBean.ResultBean.ListBean> data){
        this.context=context;
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.coupon_listview_item,null);
            viewHolder=new ViewHolder();
            viewHolder.coupon=(TextView) convertView.findViewById(R.id.coupon_listview_item_coupon);
            viewHolder.createTime=(TextView) convertView.findViewById(R.id.coupon_listview_item_createtime);
            viewHolder.status=(TextView) convertView.findViewById(R.id.coupon_listview_item_status);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.coupon.setText(data.get(position).getNAME());
        viewHolder.createTime.setText(data.get(position).getCreateDate());
        if(data.get(position).getIsUsed()==0){
            viewHolder.status.setText("未使用");
        }else{
            viewHolder.status.setText("已使用");
        }
        return convertView;
    }
    class ViewHolder{
        TextView coupon,createTime,status;
    }
}
