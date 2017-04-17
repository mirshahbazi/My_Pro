package top.linjia.wizarposapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.OrderCacel;
import top.linjia.wizarposapp.beans.OrderListBean;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @ClassName: MyOrderListViewAdapter
 * @Description: 我的订单页面商品列表适配器。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class MyOrderListViewAdapter extends BaseAdapter {
    Call call = null;
    Context context;
    Handler handler;
    List<OrderListBean.ResultBean.ListBean> data;
    ExecutorService executorService = WizarPosApp.getInternetThreadPool();

    public MyOrderListViewAdapter(Context context, List<OrderListBean.ResultBean.ListBean> data) {
        this.context = context;
        this.data = data;
        initHandler();
    }

    /**
     * @Title: initHandler
     * @Description: 初始化handler，处理网络请求成功或是失败后的Ui显示
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    int poi = msg.arg1;
                    data.remove(poi);
                    notifyDataSetChanged();
                    MyToast.showToast(context.getResources().getString(R.string.order_cacel));
                } else if (msg.what == 2) {
                    MyToast.showToast(context.getResources().getString(R.string.order_request_fail));
                } else if (msg.what == 3) {
                    MyToast.showToast(context.getResources().getString(R.string.net_wrong));
                } else if (msg.what == 4) {
                    MyToast.showToast(context.getResources().getString(R.string.order_request_fail));
                }
            }
        };
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.order_listview_item, null);
            holder.orderNo = (TextView) convertView.findViewById(R.id.order_listview_item_name);
            holder.orderTime = (TextView) convertView.findViewById(R.id.order_listview_item_stname);
            holder.fee = (TextView) convertView.findViewById(R.id.order_listview_item_price);
            holder.status = (TextView) convertView.findViewById(R.id.order_listview_item_num);
            holder.delete = (ImageView) convertView.findViewById(R.id.order_listview_item_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.i("test", "订单条数：" + data.size() + "当前是第几条：" + position);
        holder.orderNo.setText(data.get(position).getOrderNo());
        holder.orderTime.setText(data.get(position).getCreateTime());
        holder.fee.setText(data.get(position).getMoney() + "");
        int status = data.get(position).getStatus();
        switch (status) {
            case 0:
                holder.delete.setVisibility(View.VISIBLE);
                holder.status.setText(context.getResources().getString(R.string.order_status_unpay));
                break;
            case 1:
                holder.delete.setVisibility(View.INVISIBLE);
                holder.status.setText(context.getResources().getString(R.string.order_status_history));
                break;
            case 2:
                holder.delete.setVisibility(View.INVISIBLE);
                holder.status.setText(context.getResources().getString(R.string.order_status_payed));
                break;
            case 9:
                holder.delete.setVisibility(View.INVISIBLE);
                holder.status.setText(context.getResources().getString(R.string.order_status_history));
                break;
        }

        /**
         * 设置删除订单按钮的监听事件
         * */
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle(context.getResources().getString(R.string.order_status_cacelorder));
                alertDialog.setPositiveButton(context.getResources().getString(R.string.order_status_cacelyes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                FormBody formbody = new FormBody.Builder()
                                        .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                                        .add("orderId", data.get(position).getOrderId() + "")
                                        .build();
                                try {
                                    call = OkHttpUtil.postCallFormServer(Url.BASE_URL + "order/cancelOrder", formbody);
                                    Response response = call.execute();
                                    String jsonStr = response.body().string();
                                    OrderCacel orderCacel = GsonUtil.parseJsonToBean(jsonStr, OrderCacel.class);
                                    Log.i("test", "取消订单返回体：" + jsonStr);
                                    if (orderCacel != null) {
                                        if (orderCacel.getState() == 1) {
                                            Log.i("test", "已成功删除订单");
                                            Message msg = Message.obtain();
                                            msg.what = 1;
                                            msg.arg1 = position;
                                            handler.sendMessage(msg);
                                        } else {
                                            Message msg = Message.obtain();
                                            msg.what = 2;
                                            handler.sendMessage(msg);
                                        }
                                        Log.i("test", "删除后的订单条数是：" + data.size());
                                    } else {
                                        Message msg = Message.obtain();
                                        msg.what = 4;
                                        handler.sendMessage(msg);
                                    }
                                } catch (IOException e) {
                                    Log.i("test", "网络异常！");
                                    Message msg = Message.obtain();
                                    msg.what = 3;
                                    handler.sendMessage(msg);
                                }
                            }
                        });
                    }
                });
                alertDialog.setNegativeButton(context.getResources().getString(R.string.order_status_cacelno), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.create().show();
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView orderNo, orderTime, fee, status;
        ImageView delete;
    }

}
