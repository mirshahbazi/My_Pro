package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.MyOrderListViewAdapter;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.OrderListBean;
import top.linjia.wizarposapp.beans.PersonalInfo;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.customs.LodingDialog;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GsonUtil;

/**
 * @ClassName: MyOrderFormActivity
 * @date 创建时间 2016/11/11
 * @author: 李鹏鹏
 * @Description: 我的订单界面
 * @version: V1.0
 */
public class MyOrderFormActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener,AdapterView.OnItemClickListener{

    protected LinearLayout myorder_loading_fail_ll;
    Button myorder_loading_fail_btn;
    protected ImageView commonTopBackIv;
    protected RadioGroup activityMyOrderFormRadiogroup;
    protected View bottomline1;
    protected View bottomline2;
    protected View bottomline3;
    protected ListView activityMyOrderFormLisrview;
    protected RadioButton activityMyOrderFormRbFinish;
    protected RadioButton activityMyOrderFormRbUnfinish;
    protected RadioButton activityMyOrderFormRbHistory;
    protected Call call;
    MyOrderListViewAdapter adapter;
    Handler handler;
    int orderFragment=1;
    List<OrderListBean.ResultBean.ListBean> finishOrder=new ArrayList<>();
    List<OrderListBean.ResultBean.ListBean> unfinishOrder=new ArrayList<>();
    List<OrderListBean.ResultBean.ListBean> historyOrder=new ArrayList<>();
    OrderListBean orderListBean=null;
    int orderId=0;
    Intent intent;
    int orderStatus=0;
    List<User_CartInfo> infos=new ArrayList<>();
    private LodingDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_my_order_form);
        initView();
        loading=new LodingDialog(MyOrderFormActivity.this);
        loading.setmTextView("加载中。。。");
        loading.show();
        initHandler();
        initData();
    }

    public void initHandler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        loading.dismiss();
                        orderFragment=1;
                        changeTextColorAndBottomLineColor(activityMyOrderFormRbUnfinish,bottomline1);
                        returnTextColorAndBottomLineColor(activityMyOrderFormRbFinish,bottomline2);
                        returnTextColorAndBottomLineColor(activityMyOrderFormRbHistory,bottomline3);
                        adapter=new MyOrderListViewAdapter(MyOrderFormActivity.this,unfinishOrder);
                        activityMyOrderFormLisrview.setAdapter(adapter);
                        break;
                    case 1:
                        loading.dismiss();
                        orderFragment=2;
                        changeTextColorAndBottomLineColor(activityMyOrderFormRbFinish,bottomline2);
                        returnTextColorAndBottomLineColor(activityMyOrderFormRbUnfinish,bottomline1);
                        returnTextColorAndBottomLineColor(activityMyOrderFormRbHistory,bottomline3);
                        adapter=new MyOrderListViewAdapter(MyOrderFormActivity.this,finishOrder);
                        activityMyOrderFormLisrview.setAdapter(adapter);
                        break;
                    case 2:
                        loading.dismiss();
                        changeTextColorAndBottomLineColor(activityMyOrderFormRbFinish,bottomline2);
                        returnTextColorAndBottomLineColor(activityMyOrderFormRbUnfinish,bottomline1);
                        returnTextColorAndBottomLineColor(activityMyOrderFormRbHistory,bottomline3);
                        adapter=new MyOrderListViewAdapter(MyOrderFormActivity.this,finishOrder);
                        activityMyOrderFormLisrview.setAdapter(adapter);
                        orderListBean=(OrderListBean) msg.obj;
                        break;
                    case 9:
                        loading.dismiss();
                        orderFragment=3;
                        changeTextColorAndBottomLineColor(activityMyOrderFormRbHistory,bottomline3);
                        returnTextColorAndBottomLineColor(activityMyOrderFormRbUnfinish,bottomline2);
                        returnTextColorAndBottomLineColor(activityMyOrderFormRbFinish,bottomline1);
                        adapter=new MyOrderListViewAdapter(MyOrderFormActivity.this,historyOrder);
                        activityMyOrderFormLisrview.setAdapter(adapter);
                        break;
                    case 10:
                        loading.dismiss();
                        myorder_loading_fail_ll.setVisibility(View.VISIBLE);
                        activityMyOrderFormRadiogroup.setClickable(true);
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case   R.id.activity_my_order_form_top_back_iv:
                call.cancel();
                finish();
                break;
            case R.id.myorder_loading_fail_btn:
                initData();
                break;
        }
    }

    private void initView() {
        commonTopBackIv = (ImageView) findViewById(R.id.activity_my_order_form_top_back_iv);
        commonTopBackIv.setOnClickListener(MyOrderFormActivity.this);
        activityMyOrderFormRadiogroup = (RadioGroup) findViewById(R.id.activity_my_order_form_radiogroup);
        activityMyOrderFormRadiogroup.setOnClickListener(MyOrderFormActivity.this);
        bottomline1 = (View) findViewById(R.id.bottomline1);
        bottomline2 = (View) findViewById(R.id.bottomline2);
        bottomline3 = (View) findViewById(R.id.bottomline3);
        activityMyOrderFormLisrview = (ListView) findViewById(R.id.activity_my_order_form_lisrview);
        activityMyOrderFormLisrview.setOnItemClickListener(this);
        activityMyOrderFormRbFinish = (RadioButton) findViewById(R.id.activity_my_order_form_rb_finish);
        activityMyOrderFormRbUnfinish = (RadioButton) findViewById(R.id.activity_my_order_form_rb_unfinish);
        activityMyOrderFormRbHistory = (RadioButton) findViewById(R.id.activity_my_order_form_rb_history);
        activityMyOrderFormRadiogroup.setOnCheckedChangeListener(this);
        orderStatus=getIntent().getExtras().getInt("orderStatus");
        myorder_loading_fail_ll=(LinearLayout)findViewById(R.id.myorder_loading_fail_ll);
        myorder_loading_fail_btn=(Button)findViewById(R.id.myorder_loading_fail_btn);
        myorder_loading_fail_btn.setOnClickListener(this);
    }

    public void initData(){
        myorder_loading_fail_ll.setVisibility(View.GONE);
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                FormBody body=new FormBody.Builder()
                        .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                        .add("pageNumber", "1")
                        .add("pageSize", "100")
                        .add("queryAll","1")
                        .build();
                try {
                    call= OkHttpUtil.postCallFormServer(Url.BASE_URL+"order/orderList",body);
                    Response response=call.execute();
                    String jsonStr=response.body().string();
                    Log.i("test","订单："+jsonStr);
                    orderListBean= GsonUtil.parseJsonToBean(jsonStr,OrderListBean.class);
                    if(orderListBean!=null){
                        for(int i=0;i<orderListBean.getResult().getList().size();i++){
                            Log.i("test","所有订单长度："+orderListBean.getResult().getList().size()+"");
                            switch(orderListBean.getResult().getList().get(i).getStatus()){
                                case 0:
                                    Log.i("test",(unfinishOrder==null)+"");
                                    Log.i("test",(orderListBean==null)+"");
                                    unfinishOrder.add(orderListBean.getResult().getList().get(i));
                                    Collections.reverse(unfinishOrder);
                                    break;
                                case 2:
                                    finishOrder.add(orderListBean.getResult().getList().get(i));
                                    Collections.reverse(finishOrder);
                                    break;
                                case 9:
                                    historyOrder.add(orderListBean.getResult().getList().get(i));
                                    Collections.reverse(historyOrder);
                                    break;
                            }
                        }
                    }else{
                        activityMyOrderFormRadiogroup.setFocusable(false);
                        Message msg4=Message.obtain();
                        msg4.what=10;
                        handler.sendMessage(msg4);
                    }
                    Message msg3=Message.obtain();
                    msg3.obj=orderListBean;
                    Log.i("test","接收到的orderStatus是："+orderStatus);
                    if(orderStatus==0){
                        msg3.what=0;
                    }else if(orderStatus==2){
                        msg3.what=1;
                    }else if(orderStatus==9){
                        msg3.what=9;
                    }
                    handler.sendMessage(msg3);
                } catch (IOException e) {
                    Log.i("test","网络异常");
                    activityMyOrderFormRadiogroup.setFocusable(false);
                    Message msg4=Message.obtain();
                    msg4.what=10;
                    handler.sendMessage(msg4);
                }
            }
        });

    }

    /**
     * @date: 2016/11/11  13:30
     * @Description: 响应已完成定单、未完成订单、历史订单之间的切换
     * @author 李鹏鹏
     */

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.activity_my_order_form_rb_unfinish:
                orderFragment=1;
                Message msg1=Message.obtain();
                msg1.what=0;
                handler.sendMessage(msg1);
                break;
            case R.id.activity_my_order_form_rb_finish:
                orderFragment=2;
                Message msg=Message.obtain();
                msg.what=1;
                handler.sendMessage(msg);
                break;
            case R.id.activity_my_order_form_rb_history:
                orderFragment=3;
                Message msg2=Message.obtain();
                msg2.what=9;
                handler.sendMessage(msg2);
                break;
        }
    }

    // 设置选中的字体颜色为黄色
    private void changeTextColorAndBottomLineColor(RadioButton tv, View view) {
        tv.setTextColor(getResources().getColor(R.color.yellow));
        view.setBackgroundColor(getResources().getColor(R.color.yellow));
    }

    //设置未选中的字体颜色为黑色
    private void returnTextColorAndBottomLineColor(RadioButton tv, View view) {
        tv.setTextColor(getResources().getColor(R.color.black));
        view.setBackgroundColor(getResources().getColor(R.color.realwhite));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(MyOrderFormActivity.this,OrderFormDetailActivity.class);
        int orderId;
        Log.i("test","当前页面是："+orderFragment);
        if(orderFragment==2){
            orderId=finishOrder.get(position).getOrderId();
            Log.i("test","当前已完成的订单号是："+finishOrder.get(position).getOrderId());
        }else if(orderFragment==1){
            Log.i("test","当前未完成的订单号是："+unfinishOrder.get(position).getOrderId());
            orderId=unfinishOrder.get(position).getOrderId();
        }else{
            orderId=historyOrder.get(position).getOrderId();
        }
        Log.i("test","要传过去的商品Id是："+orderId);
        intent.putExtra("orderId",orderId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(call!=null){
            call.cancel();
        }
        finish();
    }
}
