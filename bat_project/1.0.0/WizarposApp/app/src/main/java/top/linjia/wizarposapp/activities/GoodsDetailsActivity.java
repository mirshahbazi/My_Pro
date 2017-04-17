package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.GoodsDetail;
import top.linjia.wizarposapp.beans.PersonalInfo;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * ClassName: GoodsDetailsActivity
 * Description:  该类用于展示用户选中的具体商品的属性（价格，库存等）
 * Created by 邻家新锐 on 2016/10/31 16:56
 * 作者：李鹏鹏
 * 邮箱：ppbear_ly@163.com
 */
public class GoodsDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView commonTopBackIv;
    protected TextView commonTopText,cart_num;
    protected ImageView cart;
    ImageView goodsdetails_goodsimage;
    TextView goodsdetails_couponinfo;
    protected TextView cartNum;
    protected AbsoluteLayout commonCart;
    LinearLayout goods_details_loading_fail_ll,goods_details_loading_success_ll;
    Button goods_details_loading_fail_btn;
    protected TextView activityGoodsDetailsGoodsname;
    protected TextView activityGoodsDetailsGoodsprice;
    protected TextView activityGoodsDetailsGoodsqiding;
    protected TextView activityGoodsDetailsGoodskucun;
    protected TextView activityGoodsDetailsGoodstiaoma;
    protected TextView activityGoodsDetailsGoodsbrand;
    protected TextView activityGoodsDetailsGoodsunit;
    protected TextView activityGoodsDetailsGoodstype;
    protected TextView activityGoodsDetailsGoodsguige;
    protected TextView activityGoodsDetailsGoodspraise;
    protected TextView activityGoodsDetailsGoodscreatetime;
    protected Button activityGoodsDetailsCartreduce;
    protected TextView activityGoodsDetailsCartnum;
    protected Button activityGoodsDetailsCartplus;
    protected Button activityGoodsDetailsCartadd;
    List<User_CartInfo> user_cartInfos;
    UserDao userDao=null;
    Handler handler;
    int goodsNum=1;
    int moq=1;
    int store=1;
    int praise=1;
    int disDyq;
    int disGwq;
    String goodsNo=null;
    Bundle bundle;
    String goodsname;
    String stname;
    double price;
    int cartnum;
    int goodsId;
    boolean currentGoodsFlag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_goods_details);
        bundle=getIntent().getExtras();
        goodsNo=(String) bundle.get("goodsNo");
        initView();
        initData();
        initHandler();
    }

    /**
     * Title: initHandler
     * Description:  初始化handler
     * Created by 邻家新锐 on 2016/10/31 16:56
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    public void initHandler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.i("test","请求成功！！");
                GoodsDetail goodsDetails=(GoodsDetail) msg.obj;
                Log.i("test",(goodsDetails==null)+"");
                cartNum.setText(cartnum+"");
                goodsNum=goodsDetails.getResult().getMoq();
                moq=goodsDetails.getResult().getMoq();
                store=goodsDetails.getResult().getNumber();
                goodsname=goodsDetails.getResult().getNAME();
                stname=goodsDetails.getResult().getStname();
                price=goodsDetails.getResult().getPrice();
                goodsId=goodsDetails.getResult().getGoodsId();
                praise=goodsDetails.getResult().getPraise();
                moq=goodsDetails.getResult().getMoq();
                if(goodsDetails.getResult().getDisDyq()){
                    disDyq=0;
                }else{
                    disDyq=1;
                }
                if(goodsDetails.getResult().getDisGwq()){
                    disGwq=0;
                }else{
                    disGwq=1;
                }
                isEmptyStore(store);
                Picasso.with(GoodsDetailsActivity.this).load(Url.IMAGE_URL + goodsDetails.getResult().getPath()).placeholder(R.drawable.place_holder).into(goodsdetails_goodsimage);
                if(goodsDetails.getResult().getDisDyq()==true&&goodsDetails.getResult().getDisGwq()==true){
                    goodsdetails_couponinfo.setVisibility(View.VISIBLE);
                    goodsdetails_couponinfo.setText("此商品不参加优惠券活动");
                    goodsdetails_couponinfo.setBackgroundColor(getResources().getColor(R.color.nodiscount));
                }else if(goodsDetails.getResult().getDisDyq()==true&&goodsDetails.getResult().getDisGwq()==false){
                    goodsdetails_couponinfo.setVisibility(View.VISIBLE);
                    goodsdetails_couponinfo.setText("此商品不参加抵用券活动");
                    goodsdetails_couponinfo.setBackgroundColor(getResources().getColor(R.color.novoucher));
                }else if(goodsDetails.getResult().getDisDyq()==false&&goodsDetails.getResult().getDisGwq()==true){
                    goodsdetails_couponinfo.setVisibility(View.VISIBLE);
                    goodsdetails_couponinfo.setText("此商品不参加购物券活动");
                    goodsdetails_couponinfo.setBackgroundColor(getResources().getColor(R.color.nocoupon));
                }else{
                    goodsdetails_couponinfo.setVisibility(View.GONE);
                }
                activityGoodsDetailsGoodsname.setText(goodsDetails.getResult().getNAME());  //商品名
                activityGoodsDetailsGoodsprice.setText("￥ "+goodsDetails.getResult().getPrice()+"");  //商品价格
                activityGoodsDetailsGoodsqiding.setText(goodsDetails.getResult().getMoq()+"");     //商品起订数量
                activityGoodsDetailsGoodskucun.setText(goodsDetails.getResult().getNumber()+"");   //库存量
                activityGoodsDetailsGoodstype.setText(goodsDetails.getResult().getCname());   //商品类型名
                activityGoodsDetailsCartnum.setText(goodsDetails.getResult().getMoq()+"");       //商品起订数量
                activityGoodsDetailsGoodsunit.setText(goodsDetails.getResult().getUnit());
                activityGoodsDetailsGoodsguige.setText(goodsDetails.getResult().getStname());
                activityGoodsDetailsGoodstiaoma.setText(goodsDetails.getResult().getBrcode());
                activityGoodsDetailsGoodsbrand.setText(goodsDetails.getResult().getBrandId()+"");
                if(goodsDetails.getResult().getPraise()>0){
                    if(goodsDetails.getResult().getPraise()<goodsDetails.getResult().getMoq()){
                        activityGoodsDetailsGoodspraise.setText("不限");
                    }else{
                        activityGoodsDetailsGoodspraise.setText(goodsDetails.getResult().getPraise()+"");
                    }
                    if(goodsDetails.getResult().getMoq()>store){
                        activityGoodsDetailsCartadd.setText("暂时无法预订");
                        activityGoodsDetailsCartadd.setFocusable(false);
                    }
                    if(goodsDetails.getResult().getPraise()>store){
                        activityGoodsDetailsGoodspraise.setText("不限");
                    }
                }else{
                    activityGoodsDetailsGoodspraise.setText("不限");
                }
                activityGoodsDetailsGoodscreatetime.setText(goodsDetails.getResult().getProductionDate());
                super.handleMessage(msg);
                Log.i("test","当前商品的最低起订量是："+goodsDetails.getResult().getMoq());
                Log.i("test","当前商品的库存量是："+goodsDetails.getResult().getNumber());
            }
        };
    }
    /**
     * Title: initData
     * Description:  初始化数据，从ClassifyActivity页面中通过bundle传值获得数据
     * Created by 邻家新锐 on 2016/10/31 16:56
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    private void initData() {
        cartnum=userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size();
        goods_details_loading_success_ll.setVisibility(View.VISIBLE);
        goods_details_loading_fail_ll.setVisibility(View.GONE);
        Log.i("test",goodsNo+"");
        if(goodsNo!=null){
            new Thread(){
                @Override
                public void run() {
                    FormBody formBody=new FormBody.Builder()
                            .add("appToken",WizarPosApp.getmPersonalInfo().getAppToken())
                            .add("goodCode",goodsNo)
                            .build();
                    try {
                        Response response=OkHttpUtil.postResponseFormServer(Url.BASE_URL+"goods/goodsDetaild",formBody);
                        String jsonStr=response.body().string();
                        Log.i("test",jsonStr);
                        GoodsDetail goodsDetails=GsonUtil.parseJsonToBean(jsonStr,GoodsDetail.class);
                        Log.i("test","解析后的实体类是否为空："+(goodsDetails==null));
                        if(goodsDetails!=null){
                            Message msg=Message.obtain();
                            msg.obj=goodsDetails;
                            handler.sendMessage(msg);
                        }
                    } catch (IOException e) {
                         loadingfail();
                    }
                }
            }.start();
        }else{
            loadingfail();
        }
    }

    /**
     * Title: onClick
     * Description:  监听事件，响应该页面各个按钮的点击事件（后退，数量选择，购物车等）
     * Created by 邻家新锐 on 2016/11/03 18:13
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.activity_goods_details_top_back_iv:
                finish();
                break;
            case R.id.common_cart:
                Intent cartIntent = new Intent(this, CartActivity.class);
                startActivity(cartIntent);
                break;
            case R.id.activity_goods_details_cartreduce:
                if(goodsNum>moq){
                    goodsNum-=1;
                    activityGoodsDetailsCartnum.setText(goodsNum+"");
                }else{
                    MyToast.showToast("当前已是最低起订数量");
                    activityGoodsDetailsCartnum.setText(goodsNum+"");
                }
                break;
            case R.id.activity_goods_details_cartplus:
                if(goodsNum<store){
                    if(praise>0){
                        if(goodsNum<praise){
                            goodsNum+=1;
                            activityGoodsDetailsCartnum.setText(goodsNum+"");
                        }else{
                            MyToast.showToast("当前数量已经是最大限订量了亲");
                        }
                    }else{
                        goodsNum+=1;
                        activityGoodsDetailsCartnum.setText(goodsNum+"");
                    }

                }else{
                    MyToast.showToast("当前数量已经是库存最大量");
                }
                break;
            case R.id.activity_goods_details_cartadd:
                if(store==0){
                    MyToast.showToast("抱歉，邻家正在补货中。。。");
                    return;
                }
                User_CartInfo info=new User_CartInfo();
                info.setGoodsname(goodsname);
                info.setStname(stname);
                info.setPrice(price);
                info.setNum(goodsNum);
                info.setId(WizarPosApp.getmPersonalInfo().getResult().getId());
                info.setGoodsId(goodsId);
                info.setPraise(praise);
                info.setMoq(moq);
                info.setDisDyq(disDyq);
                info.setDisGwq(disGwq);
                Log.i("test",WizarPosApp.getmPersonalInfo().getAppToken());
                boolean flag=userDao.insertCart(info);
                Log.i("test","往数据库中添加是否成功："+flag);
                if(flag){
                    cartnum+=1;
                    if(currentGoodsFlag){
                        cartNum.setText(cartnum+"");
                    }
                    MyToast.showToast("已加入购物车");
                    /*List<User_CartInfo> data=userDao.queryCart(info.getId());
                    Log.i("test","数据库中数据条数是："+data.size()+"");
                    for(int i=0;i<data.size();i++){
                        Log.i("test",data.get(i).getGoodsname()+"----------------->>>>>>>>>"+(data==null));
                        Log.i("test",data.get(i).getStname()+"----------------->>>>>>>>>"+(data==null));
                        Log.i("test",data.get(i).getNum()+"----------------->>>>>>>>>"+(data==null));
                        Log.i("test",data.get(i).getId()+"----------------->>>>>>>>>"+(data==null));
                        Log.i("test",data.get(i).getPrice()+"----------------->>>>>>>>>"+(data==null));
                        Log.i("test",data.get(i).getGoodsId()+"----------------->>>>>>>>>"+(data==null));
                    }*/
                    currentGoodsFlag=false;
                }else{
                    MyToast.showToast("接口异常");
                }
                break;
            case R.id.goods_details_loading_fail_btn:
                initData();
                break;
        }
    }

    /**
     * Title: initView
     * Description:  初始化View
     * Created by 邻家新锐 on 2016/10/31 17:13
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    private void initView() {
        userDao=new UserDao(GoodsDetailsActivity.this);
        user_cartInfos=new ArrayList<>();
        commonTopBackIv = (ImageView) findViewById(R.id.activity_goods_details_top_back_iv);
        commonTopBackIv.setOnClickListener(GoodsDetailsActivity.this);
        commonTopText = (TextView) findViewById(R.id.activity_goods_details_top_text);
        cart = (ImageView) findViewById(R.id.cart);
        cartNum = (TextView) findViewById(R.id.cart_num);
        commonCart = (AbsoluteLayout) findViewById(R.id.common_cart);
        commonCart.setOnClickListener(GoodsDetailsActivity.this);
        activityGoodsDetailsGoodsname = (TextView) findViewById(R.id.activity_goods_details_goodsname);
        activityGoodsDetailsGoodsprice = (TextView) findViewById(R.id.activity_goods_details_goodsprice);
        activityGoodsDetailsGoodsqiding = (TextView) findViewById(R.id.activity_goods_details_goodsqiding);
        activityGoodsDetailsGoodskucun = (TextView) findViewById(R.id.activity_goods_details_goodskucun);
        activityGoodsDetailsGoodstiaoma = (TextView) findViewById(R.id.activity_goods_details_goodstiaoma);
        activityGoodsDetailsGoodsbrand = (TextView) findViewById(R.id.activity_goods_details_goodsbrand);
        activityGoodsDetailsGoodsunit = (TextView) findViewById(R.id.activity_goods_details_goodsunit);
        activityGoodsDetailsGoodstype = (TextView) findViewById(R.id.activity_goods_details_goodstype);
        activityGoodsDetailsGoodsguige = (TextView) findViewById(R.id.activity_goods_details_goodsguige);
        activityGoodsDetailsGoodspraise=(TextView)findViewById(R.id.activity_goods_details_praise);
        activityGoodsDetailsGoodscreatetime=(TextView)findViewById(R.id.activity_goods_details_crteatetime);
        activityGoodsDetailsCartreduce = (Button) findViewById(R.id.activity_goods_details_cartreduce);
        activityGoodsDetailsCartreduce.setOnClickListener(GoodsDetailsActivity.this);
        activityGoodsDetailsCartnum = (TextView) findViewById(R.id.activity_goods_details_cartnum);
        activityGoodsDetailsCartplus = (Button) findViewById(R.id.activity_goods_details_cartplus);
        activityGoodsDetailsCartplus.setOnClickListener(GoodsDetailsActivity.this);
        activityGoodsDetailsCartadd = (Button) findViewById(R.id.activity_goods_details_cartadd);
        activityGoodsDetailsCartadd.setOnClickListener(GoodsDetailsActivity.this);
        goodsdetails_goodsimage=(ImageView)findViewById(R.id.goodsdetails_goodsimage);
        goodsdetails_couponinfo=(TextView)findViewById(R.id.goodsdetails_couponinfo);
        goods_details_loading_fail_ll=(LinearLayout)findViewById(R.id.goods_details_loading_fail_ll);
        goods_details_loading_success_ll=(LinearLayout)findViewById(R.id.goods_details_loading_fail_ll);
        goods_details_loading_fail_btn=(Button)findViewById(R.id.goods_details_loading_fail_btn);
    }

    public void loadingfail(){
        Log.i("test","请求异常！");
        goods_details_loading_success_ll.setVisibility(View.GONE);
        goods_details_loading_fail_ll.setVisibility(View.VISIBLE);
    }

    public void isEmptyStore(int num){
        if(num==0){
            activityGoodsDetailsCartadd.setText("火速补货中");
        }else{
            activityGoodsDetailsCartadd.setClickable(true);
        }
    }

    @Override
    protected void onRestart() {
        currentGoodsFlag=true;
        cartnum=userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size();
        cartNum.setText(cartnum+"");
        super.onRestart();
    }
}
