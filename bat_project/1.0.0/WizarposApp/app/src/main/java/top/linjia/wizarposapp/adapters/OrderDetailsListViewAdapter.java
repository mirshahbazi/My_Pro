package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.OrderFromDetail;

public class OrderDetailsListViewAdapter extends BaseAdapter{
    Context context;
    List<OrderFromDetail.ResultBean.DetailsBean> data;
    public OrderDetailsListViewAdapter(Context context,List<OrderFromDetail.ResultBean.DetailsBean> data){
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
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.orderdetail_listview_item,null);
            holder.name=(TextView)convertView.findViewById(R.id.orderdetails_listview_item_name);
            holder.stname=(TextView)convertView.findViewById(R.id.orderdetails_listview_item_stname);
            holder.price=(TextView)convertView.findViewById(R.id.orderdetails_listview_item_price);
            holder.num=(TextView)convertView.findViewById(R.id.orderdetails_listview_item_num);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.name.setText(data.get(position).getGoodsName());
        holder.stname.setText(data.get(position).getStname());
        holder.price.setText(data.get(position).getPrice()+"");
        holder.num.setText(data.get(position).getNumber()+"");
        return convertView;
    }
    class ViewHolder{
        TextView name,stname,price,num;
    }
}
