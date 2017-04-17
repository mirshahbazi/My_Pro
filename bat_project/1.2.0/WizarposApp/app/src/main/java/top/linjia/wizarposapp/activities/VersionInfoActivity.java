package top.linjia.wizarposapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.VersionListAdapter;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.VersionInfobean;
import top.linjia.wizarposapp.customs.LodingDialog;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GetSystemInfoUtil;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MyToast;

public class VersionInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mActivityPersonTopBackIv;
    private TextView mActivityPersonTopText;
    private ListView mVersionListview;
    private TextView thisVersion;
    private View header;
    private HashMap<String, String>[] listDataMap;
    private LodingDialog loading;
    private VersionInfobean versionInfobean;

    /**
     * @param savedInstanceState
     * @return
     * @Title: onCreate
     * @Description: 创建activity时候初始化各项操作
     * @date 2016/12/29 15:38
     * @author 陈文豪
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_info);
        initData();
    }

    /**
     * @return
     * @Title: initData
     * @Description: 加载数据
     * @date 2017/1/6 10:20
     * @author 陈文豪
     */
    private void initData() {
        beginLoading();
        WizarPosApp.getInternetThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    versionInfobean = OkHttpUtil.postBeanFormServer(Url.GET_VERSION_INFO, getJsonRequestBody(), VersionInfobean.class);
                    if (versionInfobean != null) {
                        listDataMap = GsonUtil.parseJsonToArray(versionInfobean.getResult().getIntroduction());
                        Log.e("error", versionInfobean.getResult().getIntroduction());
                        upUiDataLoading((String) versionInfobean.getDesc());
                    } else {
                        upUiDataLoading(VersionInfoActivity.this.getString(R.string.not_know));
                    }
                } catch (IOException e) {
                    upUiDataLoading(VersionInfoActivity.this.getString(R.string.internet_errer));
                }
            }
        });
    }

    /**
     * @Title: beginLoading
     * @Description: 开始展示加载弹框
     * @date 2017/1/6 10:38
     * @author 陈文豪
     */
    private void beginLoading() {
        loading = new LodingDialog(this);
        loading.setmTextView(this.getString(R.string.loadingIng));
        loading.show();
    }

    /**
     * @Title: beginLoading
     * @Description: 关闭加载弹框
     * @date 2017/1/6 10:38
     * @author 陈文豪
     */
    private void concludeLoading() {
        loading.dismiss();
        loading = null;
    }

    /**
     * @param msg
     * @return
     * @Title: upUiDataLoading
     * @Description: 数据加载完成后更新ui
     * @date 2017/1/6 10:34
     * @author 陈文豪
     */
    private void upUiDataLoading(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (msg != null) {
                    MyToast.showToast(msg);
                }

                concludeLoading();
                initView();
            }
        });
    }


    /**
     * @Title: getJsonRequestBody
     * @Description: 生成请求体
     * @date 2017/1/6 10:30
     * @author 陈文豪
     */
    private String getJsonRequestBody() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "posVersion");
        return GsonUtil.parseMapToJson(hashMap);
    }

    /**
     * @param
     * @Title: initView
     * @Description: 初始化控件
     * @date 2016/12/29 15:39
     * @author 陈文豪
     */
    private void initView() {
        mActivityPersonTopBackIv = (ImageView) findViewById(R.id.activity_person_top_back_iv);
        mActivityPersonTopText = (TextView) findViewById(R.id.activity_person_top_text);
        mVersionListview = (ListView) findViewById(R.id.version_listview);
        mActivityPersonTopBackIv.setOnClickListener(this);
        /**
         * 填充布局
         */
        header = View.inflate(this, R.layout.version_info_title, null);
        thisVersion = (TextView) header.findViewById(R.id.version_this_number);

        /**
         * 赋值
         */
        thisVersion.setText(this.getString(R.string.this_version_number) +
                GetSystemInfoUtil.getThisVersionNumber(this));

        initListView();
    }

    /**
     * @param
     * @return
     * @Title: initListView
     * @Description: 初始化listview
     * @date 2016/12/29 16:14
     * @author 陈文豪
     */
    private void initListView() {
        mVersionListview.addHeaderView(header, null, false);
        mVersionListview.setAdapter(new VersionListAdapter(this, listDataMap));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_person_top_back_iv:
                this.finish();
                break;
        }
    }
}
