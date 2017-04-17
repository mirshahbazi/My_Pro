package top.linjia.wizarposapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wizarpos.barcode.decode.DecodeResult;
import com.wizarpos.barcode.scanner.ScannerActivity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.customs.MyEditText;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @ClassName: SearchActivity
 * @Description: 该Activity是搜索页面，可是通过商品关键字或者商品条码实现搜索商品的功能。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView commonTopBackIv;
    protected MyEditText activitySearchEdittext;
    TextView activity_search_top_text;
    ExecutorService executorService = null;
    GoodsList goodsList = null;
    List<GoodsList.ResultBean.ListBean> goods_data;
    String searchText = "";
    String goodsName = "";
    String brcode = "";
    Handler handler = null;
    String scanInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_search);
        initView();
        initHandler();
        // initListener();
    }

    public void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                goodsList = (GoodsList) msg.obj;
                Log.i("test", "goodslist的长度是：" + goodsList.getResult().getList().size());
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_search_top_text:
                //initExecutorService();
                searchText = activitySearchEdittext.getText().toString();
                searchType(searchText);
                if (goodsName != null && !(goodsName.equals(""))) {
                    Intent classifyActivity = new Intent(this, ClassifyActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("searchText", goodsName);
                    bundle.putInt("flag", 1);
                    classifyActivity.putExtras(bundle);
                    startActivity(classifyActivity);
                } else if (brcode != null && !(brcode.equals(""))) {
                    Intent classifyActivity = new Intent(this, ClassifyActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("searchText", brcode);
                    bundle.putInt("flag", 2);
                    classifyActivity.putExtras(bundle);
                    startActivity(classifyActivity);
                } else {
                    MyToast.showToast("输入内容不能为空！");
                }
                break;
            case R.id.activity_search_top_back_iv:
                finish();
                break;
        }
    }

    /**
  * @Title: dispatchkeyEvent
  * @Description: 拦截软键盘确定键的响应事件
  * @param: KeyEvent
  * @Date: 2016/12/19
  * @author: 李鹏鹏
  * */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                Log.i("test", "隐藏软键盘");
                inputMethodManager.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

            // 网络请求到数据后再执行跳转
            /*initExecutorService();
            Log.i("test","商品列表长度是："+goods_data.size()+"");
            Log.i("test","商品列表是够为空："+(goodsList.getResult().getList()==null));
            Log.i("test","Edittext中的内容是："+goodsName);
            Log.i("test","商品列表长度是：--------"+goodsList.getResult().getList().size());*/
            searchText = activitySearchEdittext.getText().toString();
            searchType(searchText);
            if (goodsName != null && !(goodsName.equals(""))) {
                Intent classifyActivity = new Intent(this, ClassifyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("searchText", goodsName);
                bundle.putInt("flag", 1);
                classifyActivity.putExtras(bundle);
                startActivity(classifyActivity);
            } else if (brcode != null && !(brcode.equals(""))) {
                Intent classifyActivity = new Intent(this, ClassifyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("searchText", brcode);
                bundle.putInt("flag", 2);
                classifyActivity.putExtras(bundle);
                startActivity(classifyActivity);
            } else {
                MyToast.showToast("输入内容不能为空！");
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * @Title: initView
     * @Description: 初始化View
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     * */
    private void initView() {
        commonTopBackIv = (ImageView) findViewById(R.id.activity_search_top_back_iv);
        commonTopBackIv.setOnClickListener(this);
        activity_search_top_text = (TextView) findViewById(R.id.activity_search_top_text);
        activity_search_top_text.setOnClickListener(this);
        activitySearchEdittext = (MyEditText) findViewById(R.id.activity_search_edittext);
    }

    /**
     * @Title: initListener
     * @Description: 初始化监听，监听为搜索框中右侧图片的点击监听，主要用来实现扫码搜索功能
     * @param:
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     * */
    public void initListener() {
        activitySearchEdittext.setDrawableRightListener(new MyEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                TextView tv = new TextView(SearchActivity.this);
                tv.setWidth(300);
                tv.setHeight(80);
                tv.setText(getResources().getString(R.string.goods_search_scan));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(30);
                tv.setBackgroundColor(getResources().getColor(R.color.colorLine));
                Toast toast = new Toast(SearchActivity.this);
                toast.setView(tv);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
                int REQUEST = 1;
                String formatStandard = "ONE_D_FORMATS";
                Intent intent = new Intent(SearchActivity.this, ScannerActivity.class);
                intent.putExtra("formatStandard", formatStandard);
                startActivityForResult(intent, REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                scanInfo = bundle.getString(DecodeResult.RESULT);
                Intent intent = new Intent(SearchActivity.this, ClassifyActivity.class);
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

    public void initExecutorService() {
        goodsName = activitySearchEdittext.getText().toString();
        if (goodsName != null) {
            executorService = WizarPosApp.getInternetThreadPool();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    FormBody formBody = new FormBody.Builder()
                            .add("appToken", WizarPosApp.getmPersonalInfo().getAppToken())
                            .add("pagerNumber", "1")
                            .add("pagerSize", "10")
                            .add("likeName", goodsName)
                            .build();
                    try {
                        Response response = OkHttpUtil.postResponseFormServer(Url.BASE_URL + "goods/goodsList", formBody);
                        String jsonStr = response.body().string();
                        Log.i("test", "jsonStr是:" + jsonStr);
                        goodsList = GsonUtil.parseJsonToBean(jsonStr, GoodsList.class);
                        goods_data = goodsList.getResult().getList();
                        Message msg = Message.obtain();
                        msg.obj = goodsList;
                        handler.sendMessage(msg);
                        Log.i("test", "商品列表长度是---》" + goods_data.size() + "");
                        Log.i("test", "商品列表是够为空---》：" + (goodsList.getResult().getList() == null));
                        Log.i("test", "Edittext中的内容是：---》" + goodsName);
                    } catch (IOException e) {
                    }
                }
            });
        } else {
            MyToast.showToast(getResources().getString(R.string.goods_search_empty_input));
        }
    }

    /***
   * @Title: searchType
   * @Description: 根据用户输入内容判断是按条码搜索还是商品名称关键字搜索
   * @param: KeyEvent
   * @Date: 2016/12/19
   * @author: 李鹏鹏
   * */
    public void searchType(String text) {
        if (text != null) {
            if (text.length() == 13 && text.substring(0, 2).equals("69")) {
                brcode = text;
            } else {
                goodsName = text;
            }
        }
    }

    /***
     * @Title: onStop
     * @Description: activity的生周期方法，当当前页面不可见时关闭掉当前页面
     * @param: KeyEvent
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     * */
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
