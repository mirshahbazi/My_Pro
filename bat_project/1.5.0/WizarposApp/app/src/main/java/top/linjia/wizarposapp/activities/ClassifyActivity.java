package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.wizarpos.barcode.decode.DecodeResult;
import com.wizarpos.barcode.scanner.ScannerActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.GoodsListAdapter;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.beans.GoodsType;
import top.linjia.wizarposapp.customs.LodingDialog;
import top.linjia.wizarposapp.customs.MyEditText;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.helperComputer.CartNumberHelper;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @ClassName: ClassifyActivity
 * @Description: 该Activity是商品分类页面，使用户可通过选择具体类别查询该类别商品，并用来向用户展示商品列表并且实现将商品添加购物车的功能。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */

public class ClassifyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2, View.OnClickListener {


    LinearLayout goodscategory_loading_fail;
    Button goodscategory_again_loading_btn;
    ListView classifyListView;
    TextView personAddress;
    PullToRefreshGridView classifyGridView;
    LinearLayout linearLayout;
    ImageView back_image;
    TextView goodsFind;
    AbsoluteLayout goodsCart;
    TextView numCart;
    RelativeLayout goodsCategory;
    TextView personal;
    TextView cartNum;
    AbsoluteLayout absoluteLayout;
    MyEditText search_editText;
    Button again_loading_btn;
    GoodsListAdapter goodsListAdapter;
    List<GoodsType.ResultBean> category_data = null;
    List<GoodsList.ResultBean.ListBean> goods_data = null;
    LodingDialog dialog;
    int pageIndex = 1;
    Handler handler;
    ExecutorService executorService = WizarPosApp.getInternetThreadPool();
    String item = null;
    List<String> cateids;
    Bundle bundle = null;
    Intent intent = null;
    String scanInfo = "";
    private long mPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_classify);
        initView();
        search_editText.setOnClickListener(this);
        classifyGridView.setOnRefreshListener(this);
        initListener();
        initHandler();
        initData();
    }

    /**
     * @Title: onNewIntent
     * @Description: 用来对不通的Intent传递过来的数据做出处理
     * @param: Intent
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        initData();
        super.onNewIntent(intent);
    }

    /**
     * @Title: initListener
     * @Description: 用来设置该页面中需要的监听。
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void initListener() {
        classifyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.i("test", cateids.get(position));
                Intent intent=new Intent(ClassifyActivity.this,SecondLevelTypeActivity.class);
                startActivity(intent);
            }
        });
        search_editText.setDrawableRightListener(new MyEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                int REQUEST = 1;
                String formatStandard = "ONE_D_FORMATS";
                Intent intent = new Intent(ClassifyActivity.this, ScannerActivity.class);
                intent.putExtra("formatStandard", formatStandard);
                startActivityForResult(intent, REQUEST);
                TextView tv = new TextView(ClassifyActivity.this);
                tv.setWidth(300);
                tv.setHeight(80);
                tv.setText(getResources().getString(R.string.goods_search_scan));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(30);
                tv.setBackgroundColor(getResources().getColor(R.color.colorLine));
                Toast toast = new Toast(ClassifyActivity.this);
                toast.setView(tv);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    /**
     * @Title: onActivityResult
     * @Description: 指定intent跳转正确后所做出的数据处理
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                scanInfo = bundle.getString(DecodeResult.RESULT);
                Intent intent = new Intent(ClassifyActivity.this, ClassifyActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("searchText", scanInfo);
                bundle1.putInt("flag", 2);
                intent.putExtras(bundle1);
                if (!scanInfo.equals("")) {
                    startActivity(intent);
                }
                // MyToast.showToast("扫码结果是：" + scanInfo);
            } else {
                MyToast.showToast(getResources().getString(R.string.goods_search_empty_result));
            }
        }
    }

    /**
     * @Title: initHandler
     * @Description: 初始化Handler，用来处理传过来的不同的适配器并刷新view
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                CartNumberHelper.cartSumCount(cartNum, WizarPosApp.getmPersonalInfo().getCartGoodsNum());
                if (WizarPosApp.getmPersonalInfo().getCartGoodsNum() != 0) {
                    numCart.setVisibility(View.VISIBLE);
                    numCart.setText(String.valueOf(WizarPosApp.getmPersonalInfo().getCartGoodsNum()));
                } else {
                    numCart.setVisibility(View.GONE);
                }
                switch (msg.what) {
                    case 1:
                        classifyGridView.setAdapter(goodsListAdapter);
                        break;
                    case 2:
                        dialog.dismiss();
                        classifyListView.setVisibility(View.GONE);
                        classifyGridView.setVisibility(View.GONE);
                        goodscategory_loading_fail.setVisibility(View.VISIBLE);
                        goodscategory_again_loading_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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
     * @Title: initData
     * @Description: 初始化ListView和GridView数据，从网络下载
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    private void initData() {
        item = null;
        pageIndex = 1;
        //  dialog.show();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                FormBody formBody = new FormBody.Builder()
                        .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                        .build();
                try {
                    Response response = OkHttpUtil.postResponseFormServer(Url.BASE_URL + "goods/categoryList", formBody);
                    String jsonStr = response.body().string();
                    Log.i("test", "商品列表是：" + jsonStr);
                    GoodsType goodsType = GsonUtil.parseJsonToBean(jsonStr, GoodsType.class);
                    /**
                     * 根据从网络请求的数据解析结果判断，结果为空或不为空向handler发送消息来刷新UI
                     * */
                    if (goodsType != null) {
                        category_data = goodsType.getResult();
                        if (category_data.size() != 0) {
                            for (int i = 0; i < category_data.size(); i++) {
                                cateids.add(category_data.get(i).getCateid());
                            }
                            goodsListAdapter = new GoodsListAdapter(ClassifyActivity.this, category_data);
                            Message msg = Message.obtain();
                            msg.what = 1;
                            msg.obj = goodsListAdapter;
                            handler.sendMessage(msg);
                        } else {
                            Message msg2 = Message.obtain();
                            msg2.what = 2;
                            handler.sendMessage(msg2);
                        }
                    } else {
                        Message msg2 = Message.obtain();
                        msg2.what = 2;
                        handler.sendMessage(msg2);
                    }
                } catch (IOException e) {
                    Message msg2 = Message.obtain();
                    msg2.what = 2;
                    handler.sendMessage(msg2);
                }
            }
        });
    }

    /**
     * @Title: initView
     * @Description: 初始化View
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    private void initView() {
        cateids = new ArrayList<>();
        goods_data = new ArrayList<>();
        category_data = new ArrayList<>();
        personAddress = (TextView) findViewById(R.id.common_top_text);
      //  personAddress.setAlpha(0.2f);
        personAddress.setText(WizarPosApp.getmPersonalInfo().getResult().getAddress());
        goodscategory_loading_fail = (LinearLayout) findViewById(R.id.goodscategory_loading_fail);
        goodscategory_again_loading_btn = (Button) findViewById(R.id.goodscategory_again_loading_btn);
        classifyGridView = (PullToRefreshGridView) findViewById(R.id.activity_classify_gridview);
        linearLayout = (LinearLayout) findViewById(R.id.goodslist_loading_fail);
        again_loading_btn = (Button) findViewById(R.id.goodslist_again_loading_btn);
        dialog = new LodingDialog(ClassifyActivity.this);
        dialog.setmTextView(getResources().getString(R.string.dialog_loading));
        back_image = (ImageView) findViewById(R.id.common_top_back_iv);
        goodsFind = (TextView) findViewById(R.id.common_top_zhaohuo);
        goodsFind.setOnClickListener(this);
        goodsCategory = (RelativeLayout) findViewById(R.id.common_top_category);
        goodsCart = (AbsoluteLayout) findViewById(R.id.common_top_cart);
        goodsCart.setOnClickListener(this);
        numCart = (TextView) findViewById(R.id.category_cartnum);
        personal = (TextView) findViewById(R.id.common_top_personal);
        personal.setOnClickListener(this);
        cartNum = (TextView) findViewById(R.id.cart_num);
        absoluteLayout = (AbsoluteLayout) findViewById(R.id.common_cart);
        absoluteLayout.setOnClickListener(this);
        search_editText = (MyEditText) findViewById(R.id.common_top_search);
    }

    /**
     * @Title: onItemClick
     * @Description: listView的条目监听, 点击商品分类实现不同类别商品在本页面的浏览
     * @param: AdapterView<?> View int long
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
        Log.i("test",cateids.get(position));
        //int firstVisiblePosition=classifyListView.getFirstVisiblePosition();
        //item=cateids.get(position-firstVisiblePosition);
    }

    /**
     * @Title: onPullDownToRefresh
     * @Description: GridView的条目监听, 点击商品分类实现不同类别商品在本页面的浏览
     * @param: PullToRefreshBase
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        classifyGridView.onRefreshComplete();
    }

    /**
     * @Title: onPullUpToRefresh
     * @Description: GridView的条目监听, 点击商品分类实现不同类别商品在本页面的浏览
     * @param: PullToRefreshBase
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        dialog.dismiss();
        classifyGridView.onRefreshComplete();
    }

    /**
     * @Title: onClick
     * @Description: 返回按键监听, 返回上个页面
     * @param: View
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_top_search:
                Intent intent = new Intent(ClassifyActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.common_top_back_iv:
                finish();
                break;
            case R.id.common_top_zhaohuo:
                Intent mainActivity = new Intent(ClassifyActivity.this, MainActivity.class);
                startActivity(mainActivity);
                break;
            case R.id.common_top_cart:
                Intent cartActivity = new Intent(ClassifyActivity.this, CartActivity.class);
                startActivity(cartActivity);
                break;
            case R.id.common_top_personal:
                Intent personActivity = new Intent(ClassifyActivity.this, PersonActivity.class);
                startActivity(personActivity);
                break;
        }
    }

    /**
     * @Title: onRestart
     * @Description: 当前Activity重新可见后刷新UI
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    protected void onRestart() {
        CartNumberHelper.cartSumCount(cartNum, WizarPosApp.getmPersonalInfo().getCartGoodsNum());
        goodsListAdapter.notifyDataSetInvalidated();
        super.onRestart();
    }

    /**
     * @Title: onBackPressed
     * @Description: 双击返回键是做出退出操作
     * @param:
     * @Date: 2016/12/19
     * @author: 陈文豪
     */
    @Override
    public void onBackPressed() {
        /**
         * 做双击退出的计算
         */
        long mNowTime = System.currentTimeMillis();
        if ((mNowTime - mPressedTime) > 2000) {
            MyToast.showToast(getResources().getString(R.string.exit_hint));
            mPressedTime = mNowTime;
        } else {;
            this.finish();
//            android.os.Process.killProcess(android.os.Process.myPid());//杀死当前进程
        }
    }
}
