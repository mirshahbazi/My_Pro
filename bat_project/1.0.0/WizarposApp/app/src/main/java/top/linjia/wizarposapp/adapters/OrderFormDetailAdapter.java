package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.beans.OrderFromDetail;
import top.linjia.wizarposapp.utils.MyLog;

/**
 * ClassName: OrderFormDetailAdapter
 * Description: 这个类是将网络上请求到的数据填充到订单详情中的liseview中
 * Created by:河南巧脉信息技术有限公司  on 2016/10/28 11:13
 * 作者: 王文亮
 * 邮箱：wwl901215@163.com
 */
public class OrderFormDetailAdapter extends BaseAdapter {

    private   List<OrderFromDetail.ResultBean.DetailsBean> data ;
    private Context context;


    // 新建类的时候传送过来OrderFromDetail订单详情，从订单详情中得到商品详情信息
    public OrderFormDetailAdapter(OrderFromDetail orderFromDetailData, Context context) {
        if (orderFromDetailData !=null){
            data = orderFromDetailData.getResult().getDetails();
        }else {
            new MyLog().printLog("OrderFormDetailAdapter：订单详情中数据为空，数据未传送成功");
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return data==null ? 0 : data.size();
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
        OrderFormDetailViewHolder holder;
        if (convertView==null){
            holder = new OrderFormDetailViewHolder();
            convertView=View.inflate(context, R.layout.activity_orderform_detail_listitem,null);
            holder.goodsName= (TextView) convertView.findViewById(R.id.activity_orderform_detail_listview_goodsname);
            holder.goodsStandard= (TextView) convertView.findViewById(R.id.activity_orderform_detail_listitem_standard);
            holder.unitPrice= (TextView) convertView.findViewById(R.id.activity_orderform_detail_listview_unitprice);
            holder.goodsNum= (TextView) convertView.findViewById(R.id.activity_orderform_detail_listview_goodsnum);

            convertView.setTag(holder);
        }else{
            holder = (OrderFormDetailViewHolder) convertView.getTag();
        }

        // 判断数据是否为空，不为空才在item上显示
        if (data !=null){
            OrderFromDetail.ResultBean.DetailsBean detailsBean = data.get(position);
            holder.goodsName.setText(detailsBean.getGoodsName());
            holder.goodsStandard.setText("暂时接口没有，gsid");
            holder.goodsNum.setText(detailsBean.getNumber());
            // 此处把int类型的数据转化为具有两个小数点的double类型的数据，然后再转化为字符串
            BigDecimal intToDouble = new BigDecimal(detailsBean.getPrice());
            String price = intToDouble.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString();//TODO 需要确认，设置的模式是四舍五入
            holder.unitPrice.setText("￥"+price);
        }
        return convertView;
    }
    //viewholder
     class OrderFormDetailViewHolder{
        private TextView goodsName;
        private TextView goodsStandard;
        private TextView unitPrice;
        private TextView goodsNum;
    }

}
