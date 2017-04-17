package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.GoodsType;

/**
 * ClassName: GoodsListAdapter
 * Description: 这个适配器将从网上下载的数据填充到listView中
 * Created by 邻家新锐 on 2016/10/27 12:02
 * 作者：李鹏鹏
 * 邮箱：ppbear_ly@163.com
 */
public class GoodsListAdapter extends BaseAdapter {
    Context context;
    List<GoodsType.ResultBean> data;
    public GoodsListAdapter(Context context,List<GoodsType.ResultBean> data){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.classify_classify_listview_item,null);
            holder.category=(TextView) convertView.findViewById(R.id.activity_classify_classifylist_text);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.category.setText(data.get(position).getCname());
        return convertView;
    }
    static class ViewHolder{
        TextView category;
    }
}
