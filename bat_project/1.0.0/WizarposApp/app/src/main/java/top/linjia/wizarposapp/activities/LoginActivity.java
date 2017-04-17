package top.linjia.wizarposapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.Response;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.SpinnerAdapter;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.PersonalInfo;
import top.linjia.wizarposapp.customs.LodingDialog;
import top.linjia.wizarposapp.data.PreferenceUtils;
import top.linjia.wizarposapp.data.utils.PreferenceCfg;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.GsonUtil;
import top.linjia.wizarposapp.utils.MD5;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * Created by 河南巧脉信息技术 on 2016/11/02 11:55
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 *
 * 登陆界面 登陆成功后把用户信息保存为全局
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected EditText loginactivityBossname;
    protected EditText loginactivityBosspassword;
    protected Button loginactivityBtnlog;
    protected CheckBox loginactivityCheckbox;
    private Spinner spinner;
    private ArrayList<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private LodingDialog mDialog;
    private String bossPassWord;
    private String bossName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        initView();
        isSkipLogin();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.loginactivity_btnlog) {
            try {
                bossName = loginactivityBossname.getText().toString().trim();
                bossPassWord = MD5.md5Encode(loginactivityBosspassword.getText().toString().trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(bossName) || TextUtils.isEmpty(bossPassWord)) {
                Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            } else {
                /**
                 * 开启子线程验证用户名账号密码 并解析返回的json成bean类
                 */

                mDialog.show();
                WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FormBody formBody = new FormBody.Builder()
                                    .add("username", bossName)
                                    .add("password", bossPassWord)
                                    .build();
                            Response loginResponse = OkHttpUtil.postResponseFormServer(Url.USER_LOGIN_URL, formBody);
                            String resultStr = loginResponse.body().string();
                            if(resultStr != null || "".equals(resultStr)) {
                                WizarPosApp.setmPersonalInfo(GsonUtil.parseJsonToBean(resultStr, PersonalInfo.class));
                                if(loginactivityCheckbox.isChecked()){
                                    PreferenceUtils.setPrefBoolean(LoginActivity.this,PreferenceCfg.IS_SAVETOKEN,
                                            loginactivityCheckbox.isChecked());
                                    PreferenceUtils.setPrefString(LoginActivity.this,PreferenceCfg.TOKEN,
                                            WizarPosApp.getmPersonalInfo().getAppToken().toString());
                                }else{
                                    PreferenceUtils.setPrefBoolean(LoginActivity.this,PreferenceCfg.IS_SAVETOKEN,
                                            loginactivityCheckbox.isChecked());
                                    PreferenceUtils.setPrefString(LoginActivity.this,PreferenceCfg.TOKEN,"");
                                }
                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyToast.showToast("服务器响应失败");
                                    }
                                });
                                return;
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //如果账号和密码正确则跳转到主页面
                                    mDialog.dismiss();
                                    if (WizarPosApp.getmPersonalInfo() != null) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("bossName", bossName);
                                        intent.putExtra("bossPassWord", bossPassWord);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        MyToast.showToast("用户名或密码不正确");
                                    }
                                }
                            });
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {//如果访问超时 给出提示
                                @Override
                                public void run() {
                                    MyToast.showToast("网络错误╮(╯▽╰)╭");
                                    mDialog.dismiss();
                                }
                            });
                        }
                    }
                });

            }
        } else if (view.getId() == R.id.loginactivity_checkbox) {
            //TODO 用户是否让记住密码
            if (loginactivityCheckbox.isChecked()) {   //选中时记住密码

            } else {                                   //未选中时，不记住密码

            }


        }
    }

    private void initView() {
        loginactivityBossname = (EditText) findViewById(R.id.loginactivity_bossname);
        loginactivityBosspassword = (EditText) findViewById(R.id.loginactivity_bosspassword);
        loginactivityBtnlog = (Button) findViewById(R.id.loginactivity_btnlog);
        loginactivityBtnlog.setOnClickListener(LoginActivity.this);
        loginactivityCheckbox = (CheckBox) findViewById(R.id.loginactivity_checkbox);
        loginactivityCheckbox.setOnClickListener(LoginActivity.this);
        spinner = (Spinner) findViewById(R.id.login_spinner);
        mDialog = new LodingDialog(this);

        lodingSpinner();
    }

    private void lodingSpinner() {

        //数据
        data_list = new ArrayList<String>();
        data_list.add("邻家新锐（深圳）电子商务");

        //适配器
        arr_adapter = new ArrayAdapter<String>(this, R.layout.login_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(new SpinnerAdapter(data_list, this));
    }


    /**
     * 判断是否跳过登陆界面 去mian里进行验证
     */
    public void isSkipLogin() {
        boolean prefBoolean = PreferenceUtils.getPrefBoolean(this, PreferenceCfg.IS_SAVETOKEN, false);
        final String prefString = PreferenceUtils.getPrefString(LoginActivity.this, PreferenceCfg.TOKEN, "");
        if(!prefString.equals("")){
            final LodingDialog lodingDialog = new LodingDialog(this);
            lodingDialog.setmTextView("验证中。。。");
            lodingDialog.setCancelable(false);
            lodingDialog.show();
            WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    FormBody formBody = new FormBody.Builder()
                            .add("appToken",prefString)
                            .build();
                    Response loginResponse = null;
                    try {
                        loginResponse = OkHttpUtil.postResponseFormServer(Url.USER_LOGIN_URL, formBody);
                        String resultStr = loginResponse.body().string();
                        if(resultStr != null) {
                            WizarPosApp.setmPersonalInfo(GsonUtil.parseJsonToBean(resultStr, PersonalInfo.class));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                    MyToast.showToast("身份验证完成");
                                }
                            });
                        }else{
                            throw new IOException();
                        }
                    } catch (IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyToast.showToast("当前网络无效(づ￣ 3￣)づ，不能自动登录");
                                lodingDialog.dismiss();
                            }
                        });
                    }
                }
            });


        }

    }
}
