package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wizarpos.barcode.decode.DecodeResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.MainGoodsListAdapter;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.customs.LodingDialog;
import top.linjia.wizarposapp.customs.MyEditText;
import top.linjia.wizarposapp.database.UserDao;
import top.linjia.wizarposapp.global.ConfigurationInfo;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.helperComputer.CartNumberHelper;
import top.linjia.wizarposapp.interfaces.InternetLoadingData;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;
import top.linjia.wizarposapp.view.fragment.TitleFragment;

/**
 * @ClassName: MainActivity
 * @Description: 该Activity是主页面，用来向用户展示商品列表并且实现将商品添加购物车的功能。
 * @Author: 李鹏鹏
 * @Date: 2016/12/29
 * @Copyright: 河南巧脉信息技术有限公司
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mPersonal;
    private ImageView goodsType;
    private ImageView shopCar;
    MyEditText main_btn_search;
    TextView main_cartnum;
    private GoodsList mGoodsList;
    private LodingDialog mLodingDialog;
    private PullToRefreshListView mPullToRefreshListView;
    private String brcode = "";
    private String search = "";
    private int pageCount = 1;
    private LinearLayout mainLoadingFail;
    private Button againLoading;
    private MainGoodsListAdapter mainGoodsListAdapter;
    private long mPressedTime = 0;
    private Call mCall;
    private UserDao userDao = null;
    private List<User_CartInfo> infos;
    int goodsAllNum = 0;
    String scanInfo = "";
    private TextView mMainVersion;
    private TitleFragment mMainTitleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * @Title: onClick
     * @Description: 设置界面中各个可点击控件的点击监听
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    public void onClick(View view) {

    }

    /**
     * @Title: initView
     * @Description: 初始化View
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    private void initView() {
        infos = new ArrayList<>();
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.main_pullrefreshlistview);
        mLodingDialog = new LodingDialog(this);
        mainLoadingFail = (LinearLayout) findViewById(R.id.main_list_loading_fail);
        againLoading = (Button) findViewById(R.id.again_loading_btn);
        mLodingDialog.setmTextView(getResources().getString(R.string.dialog_loading));
        mainGoodsListAdapter = new MainGoodsListAdapter(MainActivity.this, main_cartnum);
        mMainTitleFragment = (TitleFragment) getFragmentManager().findFragmentById(R.id.main_title_fragment);
        mMainTitleFragment.thisWhereActivity(TitleFragment.FIND_THE_GOODS);
        initListen();
        initPullToRefresh();
        initStur();
    }

    /**
     * @Title: initListen
     * @Description: 初始化网络加载失败后重新加载按钮的点击监听
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    private void initListen() {
        //设置点击数据加载失败页面重新加载按钮事件
        againLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initStur();
            }
        });
        //设置dialog的onbacklisten键的回调
        mLodingDialog.setCacelDialogEvent(new LodingDialog.CacelDialogEvent() {
            @Override
            public void cacelEvent() {
                mCall.cancel();
            }
        });
        //点击扫码按钮完成按条码搜索商品功能
       /* main_btn_search.setDrawableRightListener(new MyEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
               // MyToast.showToast("--------竖屏扫码奥亲！--------");
                TextView tv=new TextView(MainActivity.this);
                tv.setWidth(300);
                tv.setHeight(80);
                tv.setText(getResources().getString(R.string.goods_search_scan));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(30);
                tv.setBackgroundColor(getResources().getColor(R.color.colorLine));
                Toast  toast=new Toast(MainActivity.this);
                toast.setView(tv);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
                int REQUEST = 1;
                String formatStandard="ONE_D_FORMATS";
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                intent.putExtra("formatStandard",formatStandard);
                startActivityForResult(intent, REQUEST);
            }
        });*/
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
                Intent intent = new Intent(MainActivity.this, ClassifyActivity.class);
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
     * @Title: initPullToRefresh
     * @Description: 初始化PullTorefresh监听事件
     * @param:
     * @Date: 2016/12/19
     * @author: 陈文豪
     */
    private void initPullToRefresh() {
        /**
         * pulltorefresh的条目监听
         * */
        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // TODO: 2016/11/3 这里记录了listview的item点击该怎么做 下午完成
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainGoodsListAdapter.MainGoodsHolder temp = (MainGoodsListAdapter.MainGoodsHolder) view.getTag();
                Intent intent = new Intent(MainActivity.this, GoodsDetailsActivity.class);
                intent.putExtra("goodsNo", temp.getGoodsNo());
                Log.i("test", "商品号是：" + temp.getGoodsNo());
                startActivity(intent);
            }
        });
        /**
         * pulltorefresh的滑动监听
         */
        mPullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            // TODO: 2016/11/3 这里做滑动事件取消更新或者加载  查下这两个方法的作用和参数
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_FLING && mPullToRefreshListView.isRefreshing()) {
                    mCall.cancel();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}
        });
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageCount = 1;
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        initDataList(new InternetLoadingData<GoodsList>() {
                            @Override
                            public void succeed(GoodsList instancs) {//数据加载成功后在这里做处理
                                mainGoodsListAdapter.setmList(instancs.getResult().getList());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mPullToRefreshListView.setAdapter(mainGoodsListAdapter);
                                        mPullToRefreshListView.onRefreshComplete();
                                        MyToast.showToast(getResources().getString(R.string.goods_list_refresh));
                                    }
                                });
                            }

                            @Override
                            public void fail(String msg) {//数据加载失败在这里做处理
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyToast.showToast(getResources().getString(R.string.goods_list_refresh_fail));//刷新失败后给出提示 并不显示数据
                                        mPullToRefreshListView.setVisibility(View.GONE);
                                        mainLoadingFail.setVisibility(View.VISIBLE);
                                        mPullToRefreshListView.onRefreshComplete();
                                    }
                                });
                            }
                        });
                    }
                });
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        initDataList(new InternetLoadingData<GoodsList>() {
                            @Override
                            public void succeed(GoodsList instancs) {
                                mainGoodsListAdapter.addmList(instancs.getResult().getList());
                                int[] positions = new int[mainGoodsListAdapter.getmList().size()];
                                for (int i = 0; i < positions.length; i++) {
                                    positions[i] = mainGoodsListAdapter.getmList().get(i).getMoq();
                                }
                                mainGoodsListAdapter.setGoods_datalength(positions.length);
                                mainGoodsListAdapter.setGoods_datamoq(positions);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainGoodsListAdapter.notifyDataSetChanged();
                                        mPullToRefreshListView.onRefreshComplete();
                                        pageCount++;//加载完成后请求的页数加1 以便于下次请求请求的是下一页
                                        MyToast.showToast(getResources().getString(R.string.goods_list_refresh_complete));
                                    }
                                });
                            }

                            @Override
                            public void fail(String msg) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyToast.showToast(getResources().getString(R.string.goods_list_load_fail));
                                        mPullToRefreshListView.onRefreshComplete();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * @Title: initStur
     * @Description: 实现网络请求结果接口
     * @param:
     * @Date: 2016/12/19
     * @author: 陈文豪
     */
    private void initStur() {
        mLodingDialog.show();
        initDataList(new InternetLoadingData<GoodsList>() {
            @Override
            public void succeed(GoodsList instancs) {
                Log.d("getState", String.valueOf(mGoodsList.getState()));
                if (mGoodsList.getState() == 1) {
                    mainGoodsListAdapter.setmList(mGoodsList.getResult().getList());
                    int[] positions = new int[mGoodsList.getResult().getList().size()];
                    for (int i = 0; i < positions.length; i++) {
                        positions[i] = mGoodsList.getResult().getList().get(i).getMoq();
                    }
                    mainGoodsListAdapter.setGoods_datalength(positions.length);
                    mainGoodsListAdapter.setGoods_datamoq(positions);
                    mPullToRefreshListView.post(new Runnable() {
                        @Override
                        public void run() {
                            mPullToRefreshListView.setAdapter(mainGoodsListAdapter);
                            mainLoadingFail.setVisibility(View.GONE);
                            mPullToRefreshListView.setVisibility(View.VISIBLE);
                            mLodingDialog.dismiss();
                            pageCount++;
                        }
                    });
                }
            }

            @Override
            public void fail(String msg) {
                System.out.println(msg);
                mainLoadingFail.post(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshListView.setVisibility(View.GONE);
                        mainLoadingFail.setVisibility(View.VISIBLE);
                        mLodingDialog.dismiss();
                        MyToast.showToast(getResources().getString(R.string.net_wrong));
                    }
                });
            }
        });

    }

    /**
     * @Title: initDataList
     * @Description: 初始化商品列表，发起网络请求
     * @param: InternetLoadingData<GoodsList>
     * @Date: 2016/12/19
     * @author: 陈文豪
     */
    private void initDataList(final InternetLoadingData<GoodsList> internetLoadingData) {
        /**
         * 网络请求前先从本地数据库中取出数据并显示到UI中
         */
//        userDao = new UserDao(MainActivity.this);
//        new Thread() {
//            @Override
//            public void run() {
//                infos = userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (infos.size() == 0) {
//                            main_cartnum.setVisibility(View.GONE);
//                        } else {
//                            main_cartnum.setVisibility(View.VISIBLE);
//                        }
//                        goodsAllNum = userDao.queryGoodsAllNum(WizarPosApp.getmPersonalInfo().getResult().getId());
//                        main_cartnum.setText(String.valueOf(goodsAllNum));
//                    }
//                });
//            }
//        }.start();
        /**
         * 开启线程向服务器请求商品数据
         */
        WizarPosApp.getInternetThreadPool().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        FormBody formBody = new FormBody.Builder().add("appToken", WizarPosApp.getmPersonalInfo().getAppToken()).
                                add("pageNumber", String.valueOf(pageCount)).//第几页
                                add("pageSize", String.valueOf(ConfigurationInfo.GOODSLIST_LOADING_PAGE_NUMBER)).//每页数量
                                add("likeName", search).//模糊查询
                                add("brcode", brcode).build();//条码
                        try {
                            mCall = OkHttpUtil.postCallFormServer(Url.GOODSLIST_URL, formBody);
                            Response goodsListResponse = mCall.execute();
                            String resultData = goodsListResponse.body().string();
                            mGoodsList = GsonUtil.parseJsonToBean(resultData, GoodsList.class);
                            if (mGoodsList != null) {
                                internetLoadingData.succeed(mGoodsList);
                            } else {
                                internetLoadingData.fail(resultData);
                            }
                        } catch (IOException e) {
                            internetLoadingData.fail(getResources().getString(R.string.net_wrong));
                        }
                    }
                });
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
        } else {
            mCall.cancel();
            this.finish();
//            android.os.Process.killProcess(android.os.Process.myPid());//杀死当前进程
        }
    }

    /**
     * @Title: onRestart
     * @Description: 当当前Activity重新可见时刷新UI
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    @Override
    protected void onRestart() {
        CartNumberHelper.cartSumCount(main_cartnum,WizarPosApp.getmPersonalInfo().getCartGoodsNum());
        pageCount = 1;
        initStur();
        super.onRestart();
    }
}
