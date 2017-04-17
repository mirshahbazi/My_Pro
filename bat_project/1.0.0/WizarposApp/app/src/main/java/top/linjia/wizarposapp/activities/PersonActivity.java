package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.CouponBean;
import top.linjia.wizarposapp.beans.OrderListBean;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

public class PersonActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView commonTopBackIv;
    protected TextView activityPersonUnpay;
    protected LinearLayout activityPersonLlUnpay;
    protected TextView activityPersonUngetgoods;
    protected LinearLayout activityPersonLlUngetgoods;
    protected TextView activityPersonGetgoods;
    protected LinearLayout activityPersonLlGetgoods;
    protected TextView activityPersonMyorder;
    protected LinearLayout activityPersonLlMyorder;
    protected  RelativeLayout activity_person_location_ll,activity_person_giftcart_ll;
    protected Call call=null;
    int unfinishOrder=0;
    int finishOrder=0;
    int getOrder=0;
    int orderTotal=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_person);
        initView();
        initData();
    }

    /**
     * Title: onClick
     * Description:  设置该页面中各个按钮的监听事件
     * Created by 邻家新锐 on 2016/11/21 10:56
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    @Override
    public void onClick(View view) {
        Intent intent=null;
        if (view.getId() == R.id.activity_person_top_back_iv) {
            finish();
        } else if (view.getId() == R.id.activity_person_ll_unpay) {
            Intent myOrderFormActivity = new Intent(this,MyOrderFormActivity.class);
            myOrderFormActivity.putExtra("orderStatus",0);
            startActivity(myOrderFormActivity);
        } else if (view.getId() == R.id.activity_person_ll_ungetgoods) {
            Intent unGetGoods = new Intent(this,MyOrderFormActivity.class);
            unGetGoods.putExtra("orderStatus",2);
            startActivity(unGetGoods);
        } else if (view.getId() == R.id.activity_person_ll_getgoods) {
            Intent getGoods = new Intent(this,MyOrderFormActivity.class);
            getGoods.putExtra("orderStatus",9);
            startActivity(getGoods);
        } else if (view.getId() == R.id.activity_person_ll_myorder) {
            Intent myOrderFormIntent = new Intent(this,MyOrderFormActivity.class);
            myOrderFormIntent.putExtra("orderStatus",0);
            startActivity(myOrderFormIntent);
        }else if(view.getId()==R.id.activity_person_location_ll){
            intent=new Intent(this,PersonAddressActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.activity_person_giftcart_ll){
            intent=new Intent(this, CouponActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Title: initView
     * Description:  初始化View
     * Created by 邻家新锐 on 2016/11/21 10:56
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    private void initView() {
        commonTopBackIv = (ImageView) findViewById(R.id.activity_person_top_back_iv);
        commonTopBackIv.setOnClickListener(PersonActivity.this);
        activityPersonUnpay = (TextView) findViewById(R.id.activity_person_unpay);
        activityPersonLlUnpay = (LinearLayout) findViewById(R.id.activity_person_ll_unpay);
        activityPersonLlUnpay.setOnClickListener(PersonActivity.this);
        activityPersonUngetgoods = (TextView) findViewById(R.id.activity_person_ungetgoods);
        activityPersonLlUngetgoods = (LinearLayout) findViewById(R.id.activity_person_ll_ungetgoods);
        activityPersonLlUngetgoods.setOnClickListener(PersonActivity.this);
        activityPersonGetgoods = (TextView) findViewById(R.id.activity_person_getgoods);
        activityPersonLlGetgoods = (LinearLayout) findViewById(R.id.activity_person_ll_getgoods);
        activityPersonLlGetgoods.setOnClickListener(PersonActivity.this);
        activityPersonMyorder = (TextView) findViewById(R.id.activity_person_myorder);
        activityPersonLlMyorder = (LinearLayout) findViewById(R.id.activity_person_ll_myorder);
        activityPersonLlMyorder.setOnClickListener(PersonActivity.this);
        activity_person_location_ll=(RelativeLayout)findViewById(R.id.activity_person_location_ll);
        activity_person_location_ll.setOnClickListener(this);
        activity_person_giftcart_ll=(RelativeLayout)findViewById(R.id.activity_person_giftcart_ll);
        activity_person_giftcart_ll.setOnClickListener(this);
    }

    /**
     * Title: initData
     * Description:  初始化数据
     * Created by 邻家新锐 on 2016/11/21 10:56
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */

    public void initData(){
        activityPersonUnpay.setVisibility(View.VISIBLE);
        activityPersonUngetgoods.setVisibility(View.VISIBLE);
        activityPersonGetgoods.setVisibility(View.VISIBLE);
        activityPersonMyorder.setVisibility(View.VISIBLE);
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                FormBody formBody = new FormBody.Builder()
                        .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                        .add("pageNumber", "1")
                        .add("pageSize", "100")
                        .add("queryAll","1")
                        .build();
                call= OkHttpUtil.postCallFormServer(Url.BASE_URL+"order/orderList",formBody);
                try {
                    Response response=call.execute();
                    String jsonStr=response.body().string();
                    Log.i("test",jsonStr);
                    OrderListBean orderListBean=GsonUtil.parseJsonToBean(jsonStr,OrderListBean.class);
                    if(orderListBean!=null){
                        if(orderListBean.getResult().getList().size()!=0){
                            for(int i=0;i<orderListBean.getResult().getList().size();i++){
                                if(orderListBean.getResult().getList().get(i).getStatus()==2||orderListBean.getResult().getList().get(i).getStatus()==1){
                                    finishOrder+=1;
                                    orderTotal+=1;
                                }else if(orderListBean.getResult().getList().get(i).getStatus()==0){
                                    unfinishOrder+=1;
                                    orderTotal+=1;
                                }else if(orderListBean.getResult().getList().get(i).getStatus()==9){
                                    getOrder+=1;
                                    orderTotal+=1;
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("test","未支付订单长度是："+unfinishOrder);
                                    Log.i("test","已支付订单长度是："+finishOrder);
                                    Log.i("test","历史订单订单长度是："+getOrder);
                                    Log.i("test","总订单长度是："+orderTotal);
                                    if(unfinishOrder==0){
                                        activityPersonUnpay.setVisibility(View.GONE);
                                    }if(finishOrder==0){
                                        activityPersonUngetgoods.setVisibility(View.GONE);
                                    }if(getOrder==0){
                                        activityPersonGetgoods.setVisibility(View.GONE);
                                    }if(orderTotal==0){
                                        activityPersonMyorder.setVisibility(View.GONE);
                                    }
                                    activityPersonUnpay.setText(unfinishOrder+"");
                                    activityPersonUngetgoods.setText(finishOrder+"");
                                    activityPersonGetgoods.setText(getOrder+"");
                                    activityPersonMyorder.setText(orderTotal+"");
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activityPersonUnpay.setVisibility(View.GONE);
                                    activityPersonUngetgoods.setVisibility(View.GONE);
                                    activityPersonGetgoods.setVisibility(View.GONE);
                                    activityPersonMyorder.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    Log.i("test","网络错误");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(call!=null){
            call.cancel();
        }
        finish();
    }

    @Override
    protected void onRestart() {
         unfinishOrder=0;
         finishOrder=0;
         getOrder=0;
         orderTotal=0;
        initData();
        super.onRestart();
    }

  /*  @Override
    protected void onResume() {
        unfinishOrder=0;
        finishOrder=0;
        getOrder=0;
        orderTotal=0;
        initData();
        super.onResume();
    }*/
}
