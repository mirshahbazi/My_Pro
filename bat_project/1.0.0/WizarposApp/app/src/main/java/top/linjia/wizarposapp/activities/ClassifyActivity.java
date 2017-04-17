package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.GoodsGridAdapter;
import top.linjia.wizarposapp.adapters.GoodsListAdapter;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.beans.GoodsType;
import top.linjia.wizarposapp.customs.LodingDialog;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GoodsCategoeySort;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * ClassName: ClassifyActivity
 * Description: 该页面主要向用户展示商品分类及各种分类下的具体商品
 * Created by 邻家新锐 on 2016/10/27 12:02
 * 作者：李鹏鹏
 * 邮箱：ppbear_ly@163.com
 */

public class ClassifyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener2,View.OnClickListener{

    LinearLayout goodscategory_loading_fail;
    Button goodscategory_again_loading_btn;
    TextView goodslist_again_loading_text,activity_classify_listview_text;
    ListView classifyListView;
    PullToRefreshGridView classifyGridView;
    LinearLayout linearLayout;
    ImageView back_image;
    TextView cartNum;
    AbsoluteLayout absoluteLayout;
    EditText search_editText;
    Button again_loading_btn;
    GoodsListAdapter goodsListAdapter;
    GoodsGridAdapter goodsGridAdapter;
    List<GoodsType.ResultBean> category_data =null;
    List<GoodsList.ResultBean.ListBean> goods_data=null;
    GoodsList goodsList=null;
    String goodsName="";
    String brcode="";
    String searchText="";
    int flag=1;
    Handler handler;
    LodingDialog dialog;
    int pageIndex=1;
    boolean loadingcomplete=true;
    ExecutorService executorService=WizarPosApp.getInternetThreadPool();
    String item=null;
    Thread itemloadThread=null;
    List<String> cateids;
    Bundle bundle=null;
    UserDao userDao=null;
    int goodsNum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_classify);
        bundle=getIntent().getExtras();
        searchText=(String) bundle.get("searchText");
        flag=bundle.getInt("flag");
        initView();
        back_image.setOnClickListener(this);
        search_editText.setOnClickListener(this);
        classifyListView.setAdapter(goodsListAdapter);
        classifyListView.setOnItemClickListener(this);
        classifyGridView.setOnRefreshListener(this);
        initListener();
        initHandler();
        initData();
        initLoad();
    }

    /**
     * Title: initLoad
     * Description:  判断是否为搜索页面传过来的数据，选择性加载数据。
     * Created by 邻家新锐 on 2016/12/05 11:20
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    public void initLoad(){
        Log.i("test","执行initLoad：");
        Log.i("test","商品名称是："+goodsName+"商品列表是否为空："+(goodsList==null));
        if(flag==1||flag==2){
            initGirdView(searchText,flag);
            goodsName=searchText;
        }else{
            initGirdView("",0);
        }
    }
    /**
     * Title: initListener
     * Description:  用来设置该页面中需要的监听
     * Created by 邻家新锐 on 2016/11/15 11:20
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    public void initListener(){
        classifyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                boolean flag=(goods_data==null);
                Intent intent=new Intent(ClassifyActivity.this,GoodsDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("goodsNo",goods_data.get(position).getGoodsNo());
                Log.i("test","goods_data.get(position).getGoodsNo():"+goods_data.get(position).getGoodsNo());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * Title: initHandler
     * Description:  初始化Handler，用来处理传过来的不同的适配器并刷新view
     * Created by 邻家新锐 on 2016/11/03 11:20
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    public void initHandler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                cartNum.setText(goodsNum+"");
                switch(msg.what){
                    case 1:
                        dialog.dismiss();
                        classifyListView.setAdapter((GoodsListAdapter)msg.obj);
                        break;
                    case 2:
                        dialog.dismiss();
                        linearLayout.setVisibility(View.GONE);
                        classifyGridView.setVisibility(View.VISIBLE);
                        classifyListView.setVisibility(View.VISIBLE);
                        goodscategory_loading_fail.setVisibility(View.GONE);
                        List<GoodsList.ResultBean.ListBean> goods_data1= (List<GoodsList.ResultBean.ListBean>) msg.obj;
                        goodsGridAdapter=new GoodsGridAdapter(ClassifyActivity.this,goods_data1,cartNum);
                        int [] positions=new int[goods_data1.size()] ;
                        for (int i=0;i<positions.length;i++){
                            positions[i]=goods_data1.get(i).getMoq();
                        }
                        goodsGridAdapter.setGoods_datalength(goods_data1.size());
                        goodsGridAdapter.setGoods_datamoq(positions);
                        classifyGridView.setAdapter(goodsGridAdapter);
                        break;
                    case 3:
                        dialog.dismiss();
                        classifyGridView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                        if(msg.arg1==1){
                            goodslist_again_loading_text.setText("抱歉，暂时没有该类商品！");
                        }
                        Log.i("test","已经重新设置过View");
                        again_loading_btn.setVisibility(View.GONE);
                        again_loading_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("test","item:"+item);
                                itemloadThread.interrupt();
                                itemloadThread=null;
                                initThread();
                                itemloadThread.start();
                            }
                        });
                        break;
                    case 4:
                        dialog.dismiss();
                        MyToast.showToast("刷新成功");
                        goodsGridAdapter.setGoods_datalength(msg.arg1);
                        List<GoodsList.ResultBean.ListBean> goods_data2= (List<GoodsList.ResultBean.ListBean>) msg.obj;
                        int [] positions1=new int[goods_data2.size()] ;
                        for (int i=0;i<positions1.length;i++){
                            positions1[i]=goods_data2.get(i).getMoq();
                        }
                        goodsGridAdapter.setGoods_datamoq(positions1);
                        goodsGridAdapter.notifyDataSetChanged();
                        break;
                    case 5:
                        dialog.dismiss();
                        classifyListView.setVisibility(View.GONE);
                        classifyGridView.setVisibility(View.GONE);
                        goodscategory_loading_fail.setVisibility(View.VISIBLE);
                        goodscategory_again_loading_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("test","刷新成功！");
                                initData();
                            }
                        });
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    /**
     * Title: initData
     * Description:  初始化ListView和GridView数据，从网络下载
     * Created by 邻家新锐 on 2016/10/27 12:02
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    private void initData() {
        item=null;
        pageIndex=1;
        dialog.show();
        userDao=new UserDao(ClassifyActivity.this);
        goodsNum=userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size();
        executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        FormBody formBody = new FormBody.Builder()
                                                .add("appToken",WizarPosApp.getmPersonalInfo().getAppToken())
                                                .build();
                                        try {
                                            Response response= OkHttpUtil.postResponseFormServer(Url.BASE_URL + "goods/categoryList",formBody);
                                            String jsonStr=response.body().string();
                                            Log.i("test","商品列表是："+jsonStr);
                                            GoodsType goodsType= GsonUtil.parseJsonToBean(jsonStr,GoodsType.class);
                                           // GoodsCategoeySort goodsCategoeySort=new GoodsCategoeySort();
                                            //category_data =goodsCategoeySort.sort(goodsType.getResult());
                                            if(goodsType!=null){
                                                category_data=goodsType.getResult();
                                                if(category_data.size()!=0){
                                                    for(int i=0;i<category_data.size();i++){
                                                        cateids.add(category_data.get(i).getCateid());
                                                    }
                                                    //Log.i("test","cateids的长度是："+cateids.size());
                                                    goodsListAdapter=new GoodsListAdapter(ClassifyActivity.this, category_data);
                                                    Message msg=Message.obtain();
                                                    msg.what=1;
                                                    msg.obj=goodsListAdapter;
                                                    handler.sendMessage(msg);
                                                    //initGirdView("");
                                                }else{
                                                    Log.i("test","listView数据下载失败");
                                                    Message msg2=Message.obtain();
                                                    msg2.what=5;
                                                    handler.sendMessage(msg2);
                                                }
                                            }else{
                                                Message msg2=Message.obtain();
                                                msg2.what=5;
                                                handler.sendMessage(msg2);
                                            }
                                            Log.i("test","集合长度"+ category_data.size()+"");
                                        } catch (IOException e) {
                                            Log.i("test","异常");
                                            //网络异常时点击再次加载
                                            Message msg2=Message.obtain();
                                            msg2.what=5;
                                            handler.sendMessage(msg2);
                                        }
                                    }
        });
    }

    public void initGirdView(final String searchText, int flag){
       // final String goodsname=goodsName;
        final int searchType=flag;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //为GirdView添加第一类商品的数据
                try {
                Response response1 = null;
                if(searchType==1){
                    FormBody formBody1=new FormBody.Builder()
                            .add("appToken",WizarPosApp.getmPersonalInfo().getAppToken())
                            .add("pageNumber","1")
                            .add("pageSize","10")
                            .add("likeName",searchText)
                            .build();
                    response1 = OkHttpUtil.postResponseFormServer(Url.BASE_URL+"goods/goodsList", formBody1);
                }else if(searchType==2){
                    Log.i("test","按条码搜索："+searchText);
                    FormBody formBody1=new FormBody.Builder()
                            .add("appToken",WizarPosApp.getmPersonalInfo().getAppToken())
                            .add("pageNumber","1")
                            .add("pageSize","10")
                            .add("brcode",searchText)
                            .build();
                    response1 = OkHttpUtil.postResponseFormServer("http://test.linjia.top/linjia-api/api/v1.0/goods/goodsList", formBody1);
                }else{
                    FormBody formBody1=new FormBody.Builder()
                            .add("appToken",WizarPosApp.getmPersonalInfo().getAppToken())
                            .add("pageNumber","1")
                            .add("pageSize","10")
                            .add("likeName",searchText)
                            .build();
                    response1 = OkHttpUtil.postResponseFormServer(Url.BASE_URL+"goods/goodsList", formBody1);
                }
                    String jsonStr1=response1.body().string();
                    Log.i("test","具体商品："+jsonStr1);
                    GoodsList goodsList=GsonUtil.parseJsonToBean(jsonStr1, GoodsList.class);
                    if(goodsList!=null){
                        goods_data=goodsList.getResult().getList();
                        if(goods_data.size()==0){
                            Log.i("test",(classifyGridView==null)+"");
                            Log.i("test","暂时没有该类产品");
                            Message msg1=Message.obtain();
                            msg1.what=3;
                            msg1.arg1=1;
                            handler.sendMessage(msg1);
                        }else{
                            Log.i("test","已成功获取到数据");
                            Message msg1=Message.obtain();
                            msg1.what=2;
                            msg1.obj=goods_data;
                            handler.sendMessage(msg1);
                        }
                    }else{
                        Message msg1=Message.obtain();
                        msg1.what=5;
                        handler.sendMessage(msg1);
                    }

                } catch (IOException e) {
                    Log.i("test","异常");
                    //网络异常时点击再次加载
                    Message msg2=Message.obtain();
                    msg2.what=5;
                    handler.sendMessage(msg2);
                }
                //Gridview加载完毕
            }
        });
    }

    /**
     * Title: initView
     * Description:  初始化View
     * Created by 邻家新锐 on 2016/10/27 16:15
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    private void initView(){
        cateids=new ArrayList<>();
        goods_data=new ArrayList<>();
        category_data=new ArrayList<>();
        goodscategory_loading_fail=(LinearLayout) findViewById(R.id.goodscategory_loading_fail);
        goodscategory_again_loading_btn=(Button)findViewById(R.id.goodscategory_again_loading_btn);
        goodslist_again_loading_text=(TextView)findViewById(R.id.goodslist_again_loading_text);
        activity_classify_listview_text=(TextView)findViewById(R.id.activity_classify_listview_text);
        activity_classify_listview_text.setOnClickListener(this);
        classifyListView=(ListView) findViewById(R.id.activity_classify_classifylist);
        classifyGridView=(PullToRefreshGridView)findViewById(R.id.activity_classify_gridview);
        linearLayout=(LinearLayout)findViewById(R.id.goodslist_loading_fail);
        again_loading_btn=(Button)findViewById(R.id.goodslist_again_loading_btn);
        dialog= new LodingDialog(ClassifyActivity.this);
        dialog.setmTextView("加载中。。。");
        back_image=(ImageView)findViewById(R.id.common_top_back_iv);
        cartNum = (TextView) findViewById(R.id.cart_num);
        absoluteLayout=(AbsoluteLayout)findViewById(R.id.common_cart);
        absoluteLayout.setOnClickListener(this);
        search_editText=(EditText)findViewById(R.id.common_top_search);
    }

    /**
     * Title: onItemClick
     * Description:  listView的条目监听,点击商品分类实现不同类别商品在本页面的浏览
     * Created by 邻家新锐 on 2016/10/27 12:15
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
        //int firstVisiblePosition=classifyListView.getFirstVisiblePosition();
        //item=cateids.get(position-firstVisiblePosition);
        item=cateids.get(position);
        Log.i("test","当前的商品分类号是："+item);
        initThread();
        itemloadThread.start();
    }

    /**
     * Title: initThread
     * Description:  初始化线程
     * Created by 邻家新锐 on 2016/11/23 14:15
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    public void initThread(){
        itemloadThread=new Thread(new Runnable() {
            @Override
            public void run() {
                FormBody formBody=new FormBody.Builder()
                        .add("appToken",WizarPosApp.getmPersonalInfo().getAppToken())
                        .add("pageNumber","1")
                        .add("pageSize","10")
                        .add("cateCode",item)
                        .build();
                try {
                    Log.i("test","加载item");
                    Response response = OkHttpUtil.postResponseFormServer(Url.BASE_URL+"goods/goodsList", formBody);
                    String jsonStr=response.body().string();
                    Log.i("test","具体商品："+jsonStr);
                    GoodsList goodsList=GsonUtil.parseJsonToBean(jsonStr, GoodsList.class);
                    boolean flag=(goodsList==null);
                    Log.i("test",flag+"");
                    goods_data=goodsList.getResult().getList();
                    Log.i("test",goods_data.size()+"");
                    if(goods_data.size()==0){
                        boolean flag1=(classifyGridView==null);
                        Log.i("test",flag1+"");
                        Log.i("test","暂时没有该类产品");
                        Message msg=Message.obtain();
                        msg.what=3;
                        msg.arg1=1;
                        handler.sendMessage(msg);
                    }else{
                        Log.i("test","已成功获取到数据");
                        Message msg=Message.obtain();
                        msg.what=2;
                        msg.obj=goods_data;
                        handler.sendMessage(msg);
                    }
                    Log.i("test","具体商品："+jsonStr);
                } catch (IOException e) {
                    Log.i("test","异常！");
                    Message msg=Message.obtain();
                    msg.what=3;
                    msg.arg1=2;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    /**
     * Title: onPullDownToRefresh   onPullUpToRefresh
     * Description:  GridView的条目监听,点击商品分类实现不同类别商品在本页面的浏览
     * Created by 邻家新锐 on 2016/10/28 9:50
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        MyToast.showToast("上拉才能刷新奥！");
        classifyGridView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        dialog.dismiss();
        if(loadingcomplete){
            loadingcomplete=false;
            new Thread(){
                @Override
                public void run() {
                    Log.i("test",item+"");
                    Log.i("test","刷新前页面面号是："+pageIndex);
                    try {
                        Log.i("test",WizarPosApp.getmPersonalInfo().getAppToken()+"|"+String.valueOf(pageIndex)+"|"+"pageSize|"+20+"cateCode|"+item);
                        Response response=null;
                        if(item==null){
                            FormBody formBody=new FormBody.Builder()
                                    .add("appToken",WizarPosApp.getmPersonalInfo().getAppToken())
                                    .add("pageNumber",String.valueOf(++pageIndex))
                                    .add("pageSize",String.valueOf(10))
                                    .add("likeName",goodsName)
                                    .build();
                            response = OkHttpUtil.postResponseFormServer(Url.BASE_URL+"goods/goodsList", formBody);
                        }else{
                            FormBody formBody1=new FormBody.Builder()
                                    .add("appToken",WizarPosApp.getmPersonalInfo().getAppToken())
                                    .add("pageNumber",String.valueOf(++pageIndex))
                                    .add("pageSize",String.valueOf(10))
                                    .add("cateCode",item)
                                    .add("brcode","")
                                    .build();
                            response = OkHttpUtil.postResponseFormServer(Url.BASE_URL+"goods/goodsList", formBody1);
                        }
                        Log.i("test","刷新后页面号是："+pageIndex+"");
                        String jsonStr=response.body().string();
                        Log.i("test","具体商品---->："+jsonStr);
                        GoodsList goodsList=GsonUtil.parseJsonToBean(jsonStr, GoodsList.class);
                        Log.i("test","刷新前商品列表长度是："+goods_data.size());
                        goods_data.addAll(goodsList.getResult().getList());
                        Log.i("test","刷新后商品列表长度是："+goods_data.size());
                        int goods_data_length=goods_data.size();
                        Message msg=Message.obtain();
                        msg.what=4;
                        msg.obj=goods_data;
                        msg.arg1=goods_data_length;
                        handler.sendMessage(msg);
                    } catch (IOException e) {
                        Log.i("test","异常！");
                    }
                }
            }.start();
            loadingcomplete=true;
        }
        classifyGridView.onRefreshComplete();
    }
    /**
     * Title:  onClick
     * Description:  返回按键监听,返回上个页面
     * Created by 邻家新锐 on 2016/10/28 12:20
     * 作者：李鹏鹏
     * 邮箱：ppbear_ly@163.com
     */
    @Override
    public void onClick(View v) {
       switch(v.getId()){
           case  R.id.common_top_search:
               //MyToast.showToast("暂时不能搜索奥亲！");
               Intent intent=new Intent(ClassifyActivity.this,SearchActivity.class);
               startActivity(intent);
               break;
           case R.id.common_top_back_iv:
               finish();
               break;
           case R.id.activity_classify_listview_text:
               initData();
               initGirdView("",0);
               break;
           case R.id.common_cart:
               Intent cartActivity=new Intent(ClassifyActivity.this,CartActivity.class);
               startActivity(cartActivity);
               break;
       }
    }

    @Override
    protected void onRestart() {
        if(userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size()!=0){
            cartNum.setVisibility(View.VISIBLE);
            cartNum.setText(userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size()+"");
        }else{
            cartNum.setVisibility(View.GONE);
        }
        super.onRestart();
    }
}
