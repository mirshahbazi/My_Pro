package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

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
import top.linjia.wizarposapp.interfaces.InternetLoadingData;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * Created by 河南巧脉信息技术 on 2016/11/02 11:55
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 *
 * 主页
 *
 * 1.如果用了缓存 那么刷新就是一个问题 要好好去想一个巧妙的处理方法
 *
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
    private UserDao userDao=null;
    private List<User_CartInfo> infos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
    }

    private void lodigData() {
        /**
         * 地址           ：    http://192.168.133.38:8080/linjia-api/api/v1.0/goods/goodsList
         请求方式    ：    POST
         格式	          ：     Content-Type: application/json
         请求参数    ：    {"appToken":"asd123123123123","pageNumber":1,"pageSize":10,"likeName":"洁能保","brcode":"sas"}

         appToken        ：必填，其他都是选填
         pageNumber    : 当前页        分页必须
         pageSize           :  每页容量    分页必须
         likeName          :  模糊查询    可选
         brcode              :  条形码        可选
         cateCode           ：类型id
         */
        new Thread() {// TODO: 2016/10/24 获取数据
            @Override
            public void run() {
                /**
                 * {"result":{"list":[],"pageNumber":1,"pageSize":10},"state":1}
                 */
            }
        };
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.personal_center:
                intent = new Intent(this, PersonActivity.class);
                break;
            case R.id.goods_type:
                intent = new Intent(this, ClassifyActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("searchText","");
                bundle.putInt("flag",1);
                intent.putExtras(bundle);
                /*intent.putParcelableArrayListExtra("goodslist",null);
                intent.putExtra("goodsName","");*/
                break;
            case R.id.shopchar:
                intent = new Intent(this, CartActivity.class);
                break;
            case R.id.main_btn_search:
                intent = new Intent(this, SearchActivity.class);
                //MyToast.showToast("搜索功能暂未开通，请掌柜后续关注哦(づ￣ 3￣)づ");
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }

    private void initView() {
        infos=new ArrayList<>();
        main_btn_search= (MyEditText) findViewById(R.id.main_btn_search);
        mPersonal = (ImageView) findViewById(R.id.personal_center);
        shopCar = (ImageView) findViewById(R.id.shopchar);
        goodsType = (ImageView) findViewById(R.id.goods_type);
        main_cartnum= (TextView) findViewById(R.id.main_cartnum);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.main_pullrefreshlistview);
        mLodingDialog = new LodingDialog(this);
        mainLoadingFail = (LinearLayout) findViewById(R.id.main_list_loading_fail);
        againLoading = (Button) findViewById(R.id.again_loading_btn);
        mLodingDialog.setmTextView("加载中。。。");
        mainGoodsListAdapter = new MainGoodsListAdapter(MainActivity.this,main_cartnum);
        initListen();
        initPullToRefresh();
        initStur();
    }

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
        main_btn_search.setDrawableRightListener(new MyEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                MyToast.showToast("--------点击了扫码图片--------");
            }
        });
    }

    private void initPullToRefresh() {
        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // TODO: 2016/11/3 这里记录了listview的item点击该怎么做 下午完成
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainGoodsListAdapter.MainGoodsHolder temp = (MainGoodsListAdapter.MainGoodsHolder) view.getTag();
                Intent intent = new Intent(MainActivity.this, GoodsDetailsActivity.class);
                intent.putExtra("goodsNo",temp.getGoodsNo());
                Log.i("test","商品号是："+temp.getGoodsNo());
                startActivity(intent);
            }
        });

        mPullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            // TODO: 2016/11/3 这里做滑动事件取消更新或者加载  查下这两个方法的作用和参数
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_FLING && mPullToRefreshListView.isRefreshing()){
                    mCall.cancel();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
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
                                        MyToast.showToast("刷新成功！");
                                    }
                                });
                            }

                            @Override
                            public void fail(String msg) {//数据加载失败在这里做处理
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyToast.showToast("刷新失败！");//刷新失败后给出提示 并不显示数据
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainGoodsListAdapter.notifyDataSetChanged();
                                        mPullToRefreshListView.onRefreshComplete();
                                        pageCount++;//加载完成后请求的页数加1 以便于下次请求请求的是下一页
                                        MyToast.showToast("加载完成！");
                                    }
                                });
                            }

                            @Override
                            public void fail(String msg) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyToast.showToast("加载失败！");
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
     * 初始化加载数据 刚进页面时候加载数据
     * <p>
     * 这样写的好处是 最大程度的复用代码
     */
    private void initStur() {
        mLodingDialog.show();
        initDataList(new InternetLoadingData<GoodsList>() {
            @Override
            public void succeed(GoodsList instancs) {
                if (mGoodsList.getState() == 1) {
                    mainGoodsListAdapter.setmList(mGoodsList.getResult().getList());
                    int []positions=new int[mGoodsList.getResult().getList().size()];
                    for (int i=0;i<positions.length;i++){
                        positions[i]=mGoodsList.getResult().getList().get(i).getMoq();
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
                        MyToast.showToast("网络错误╮(╯▽╰)╭");
                    }
                });
            }
        });

    }

    /**
     * 首页加载数据引擎 主要负责加载数据 通过回调接口传给主线程
     *
     * @param internetLoadingData
     */

    private void initDataList(final InternetLoadingData<GoodsList> internetLoadingData) {
        userDao=new UserDao(MainActivity.this);
        infos=userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("test",infos.size()+"lllllllllllllllllllllllllllllllllllllllll");
                if(infos.size()==0){
                    Log.i("test",infos.size()+"lllllllllllllllllllllllllllllllllllllllll");
                    main_cartnum.setVisibility(View.GONE);
                }else{
                    main_cartnum.setVisibility(View.VISIBLE);
                }
                main_cartnum.setText(String.valueOf(infos.size()));
            }
        });
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
                            internetLoadingData.fail("网络错误╮(╯▽╰)╭");
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        /**
         * 做双击退出的计算
         */
        long mNowTime = System.currentTimeMillis();
        if((mNowTime - mPressedTime) > 2000){
            MyToast.showToast("再按就退出喽(⊙o⊙)");
            mPressedTime = mNowTime;
            }
        else{
            mCall.cancel();
            this.finish();
//            android.os.Process.killProcess(android.os.Process.myPid());//杀死当前进程
        }
    }

    @Override
    protected void onRestart() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                main_cartnum.setText(userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size()+"");
                if(userDao.queryCart(WizarPosApp.getmPersonalInfo().getResult().getId()).size()!=0){
                    main_cartnum.setVisibility(View.VISIBLE);
                }else{
                    main_cartnum.setVisibility(View.GONE);
                }
            }
        });
        super.onRestart();
    }
}
