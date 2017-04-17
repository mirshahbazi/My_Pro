package top.linjia.wizarposapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @ClassName: SearchActivity
 * @date 创建时间 2016/10/22
 * @author: 王文亮
 * @Description:
 * @version: V1.0
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView commonTopBackIv;
    protected EditText activitySearchEdittext;
    TextView activity_search_top_text;
    ExecutorService executorService=null;
    GoodsList goodsList=null;
    List<GoodsList.ResultBean.ListBean> goods_data;
    String searchText="";
    String goodsName="";
    String brcode="";
    Handler handler=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_search);
        initView();
        initHandler();
    }

    public void initHandler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                goodsList=(GoodsList) msg.obj;
                Log.i("test","goodslist的长度是："+goodsList.getResult().getList().size());
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.activity_search_top_text:
                //initExecutorService();
                searchText=activitySearchEdittext.getText().toString();
                searchType(searchText);
                if(goodsName!=null&&!(goodsName.equals(""))){
                    Intent classifyActivity = new Intent(this,ClassifyActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("searchText",goodsName);
                    bundle.putInt("flag",1);
                    classifyActivity.putExtras(bundle);
                    startActivity(classifyActivity);
                }else if(brcode!=null&&!(brcode.equals(""))){
                    Intent classifyActivity = new Intent(this,ClassifyActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("searchText",brcode);
                    bundle.putInt("flag",2);
                    classifyActivity.putExtras(bundle);
                    startActivity(classifyActivity);
                }else{
                    MyToast.showToast("输入内容不能为空！");
                }
                break;
            case R.id.activity_search_top_back_iv:
                finish();
                break;

        }
    }

    /**
     * @date: 2016/10/26  13:28
     * @Description: 拦截软键盘确定键的响应事件
     * @author 王文亮
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputMethodManager.isActive()){
                Log.i("test","隐藏软键盘");
                inputMethodManager.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

            // 网络请求到数据后再执行跳转
            /*initExecutorService();
            Log.i("test","商品列表长度是："+goods_data.size()+"");
            Log.i("test","商品列表是够为空："+(goodsList.getResult().getList()==null));
            Log.i("test","Edittext中的内容是："+goodsName);
            Log.i("test","商品列表长度是：--------"+goodsList.getResult().getList().size());*/
            searchText=activitySearchEdittext.getText().toString();
            searchType(searchText);
            if(goodsName!=null&&!(goodsName.equals(""))){
                Intent classifyActivity = new Intent(this,ClassifyActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("searchText",goodsName);
                bundle.putInt("flag",1);
                classifyActivity.putExtras(bundle);
                startActivity(classifyActivity);
            }else if(brcode!=null&&!(brcode.equals(""))){
                Intent classifyActivity = new Intent(this,ClassifyActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("searchText",brcode);
                bundle.putInt("flag",2);
                classifyActivity.putExtras(bundle);
                startActivity(classifyActivity);
            }else{
                MyToast.showToast("输入内容不能为空！");
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void initView() {
        commonTopBackIv = (ImageView) findViewById(R.id.activity_search_top_back_iv);
        commonTopBackIv.setOnClickListener(this);
        activity_search_top_text=(TextView)findViewById(R.id.activity_search_top_text);
        activity_search_top_text.setOnClickListener(this);
        activitySearchEdittext = (EditText) findViewById(R.id.activity_search_edittext);
    }

    public void initExecutorService(){
        goodsName=activitySearchEdittext.getText().toString();
        if(goodsName!=null){
            executorService= WizarPosApp.getInternetThreadPool();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    FormBody formBody=new FormBody.Builder()
                            .add("appToken",WizarPosApp.getmPersonalInfo().getAppToken())
                            .add("pagerNumber","1")
                            .add("pagerSize","10")
                            .add("likeName",goodsName)
                            .build();
                    try {
                        Response response= OkHttpUtil.postResponseFormServer(Url.BASE_URL+"goods/goodsList",formBody);
                        String jsonStr=response.body().string();
                        Log.i("test","jsonStr是:"+jsonStr);
                        goodsList= GsonUtil.parseJsonToBean(jsonStr,GoodsList.class);
                        goods_data=goodsList.getResult().getList();
                        Message msg=Message.obtain();
                        msg.obj=goodsList;
                        handler.sendMessage(msg);
                        Log.i("test","商品列表长度是---》"+goods_data.size()+"");
                        Log.i("test","商品列表是够为空---》："+(goodsList.getResult().getList()==null));
                        Log.i("test","Edittext中的内容是：---》"+goodsName);

                    } catch (IOException e) {
                    }
                }
            });
        }else{
            MyToast.showToast("输入内容为空");
        }
    }

    public void searchType(String text){
        if(text!=null) {
            if (text.length() == 13 && text.substring(0, 2).equals("69")) {
                brcode = text;
            } else {
                goodsName = text;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
