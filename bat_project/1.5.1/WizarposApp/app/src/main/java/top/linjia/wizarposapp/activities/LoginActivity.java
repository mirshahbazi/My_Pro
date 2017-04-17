package top.linjia.wizarposapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
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
 * @author 陈文豪
 * @className: top.linjia.wizarposapp.activities LoginActivity
 * @description: 登陆界面 登陆成功后把用户信息保存为全局
 * @crete 2016/12/26 9:23
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
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

    /**
     * @param savedInstanceState
     * @Title: onCreate
     * @Description: 创建activity的时候 初始化界面以及判断是否自动登录
     * @date 2016/12/27 13:59
     * @author 陈文豪
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        initView();
        isSkipLogin();//判断是否自动登陆
    }

    /**
     * @param view
     * @Title: onClick
     * @Description: 点击事件统一管理的中心 根据id判断执行哪些代码
     * @date 2016/12/27 14:02
     * @author 陈文豪
     */
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.loginactivity_btnlog) {//判断是否是登录按钮
            String bossPassWordNoMD5 = null;
            try {
                bossName = loginactivityBossname.getText().toString().trim();
                bossPassWordNoMD5 = loginactivityBosspassword.getText().toString().trim();
                bossPassWord = MD5.md5Encode(bossPassWordNoMD5);//进行MD5加密
            } catch (Exception e) {
                Log.e("MD5", "MD5 fail");
            }
            if (TextUtils.isEmpty(bossName) || TextUtils.isEmpty(bossPassWordNoMD5)) {
                Toast.makeText(this, this.getString(R.string.usernameAndpassword_notNull), Toast.LENGTH_SHORT).show();
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
                            WizarPosApp.setmPersonalInfo(GsonUtil.parseJsonToBean(resultStr, PersonalInfo.class));
                            if (WizarPosApp.getmPersonalInfo() != null && !"".equals(resultStr)) {
                                if (loginactivityCheckbox.isChecked()) { //判断checkbox是否是选中状态

                                    PreferenceUtils.setPrefBoolean(LoginActivity.this, PreferenceCfg.IS_SAVETOKEN,
                                            loginactivityCheckbox.isChecked());//保存check的状态

                                    PreferenceUtils.setPrefString(LoginActivity.this, PreferenceCfg.TOKEN,
                                            WizarPosApp.getmPersonalInfo().getAppToken().toString());//保存token
                                } else {
                                    PreferenceUtils.setPrefBoolean(LoginActivity.this, PreferenceCfg.IS_SAVETOKEN,
                                            loginactivityCheckbox.isChecked());//保存check的状态

                                    PreferenceUtils.setPrefString(LoginActivity.this, PreferenceCfg.TOKEN, "");//保存token
                                }
                            } else {
                                /**
                                 * 如果返回的结果不对 就告知用户用户名或者密码错误
                                 */
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyToast.showToast(LoginActivity.this.getString(R.string.loginActvity_usernameOrPasswrod_error));
                                        mDialog.dismiss();
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
                                        if (WizarPosApp.getmPersonalInfo().getState() == 1) {
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                            /**
                                             * 保存账号到本地
                                             */
                                            PreferenceUtils.setPrefString(LoginActivity.this, PreferenceCfg.ACCOUNT_NUMBER, bossName);
                                        }
                                    } else {
                                        MyToast.showToast(LoginActivity.this.getString(R.string.loginActvity_usernameOrPasswrod_error));
                                        mDialog.dismiss();
                                    }
                                }
                            });
                        } catch (IOException e) {
                            /**
                             * 网络不可用或者断开 给用户提示
                             */
                            runOnUiThread(new Runnable() {//如果访问超时 给出提示
                                @Override
                                public void run() {
                                    MyToast.showToast(LoginActivity.this.getString(R.string.internet_errer));
                                    mDialog.dismiss();
                                }
                            });
                        }
                    }
                });

            }
        }
    }

    /**
     * @Title: initView
     * @Description: 初始化控件 获取控件的操作对象
     * @date 2016/12/27 15:52
     * @author 陈文豪
     */
    private void initView() {
        loginactivityBossname = (EditText) findViewById(R.id.loginactivity_bossname);
        loginactivityBosspassword = (EditText) findViewById(R.id.loginactivity_bosspassword);
        loginactivityBtnlog = (Button) findViewById(R.id.loginactivity_btnlog);
        loginactivityBtnlog.setOnClickListener(LoginActivity.this);
        loginactivityCheckbox = (CheckBox) findViewById(R.id.loginactivity_checkbox);
        loginactivityCheckbox.setOnClickListener(LoginActivity.this);
        spinner = (Spinner) findViewById(R.id.login_spinner);
        mDialog = new LodingDialog(this);
        /**
         * 加载spinner 并初始化数据
         */
        lodingSpinner();
        /**
         * 加载保存的账户名
         */
        lodingAccountNumber();
    }

    /**
     * @Title: lodingAccountNumber
     * @Description: 加载账号
     * @date 2017/1/13 10:16
     * @author 陈文豪
     */
    private void lodingAccountNumber() {
        String number = PreferenceUtils.getPrefString(this, PreferenceCfg.ACCOUNT_NUMBER, null);
        if (number != null) {
            loginactivityBossname.setText(number);
            loginactivityBossname.setSelection(number.length());
        }
    }

    /**
     * @Title: lodingSpinner
     * @Description: 加载spinner并初始化数据
     * @date 2016/12/27 16:14
     * @author 陈文豪
     */
    private void lodingSpinner() {

        //数据
        data_list = new ArrayList<String>();
        data_list.add(this.getString(R.string.Linjia_name));

        //适配器
        arr_adapter = new ArrayAdapter<String>(this, R.layout.login_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(new SpinnerAdapter(data_list, this));
    }

    /**
     * 这个方法判断是否跳过自动登录
     */
    public void isSkipLogin() {
        boolean prefBoolean = PreferenceUtils.getPrefBoolean(this, PreferenceCfg.IS_SAVETOKEN, false);//获取到checkbox的状态
        final String token = PreferenceUtils.getPrefString(LoginActivity.this, PreferenceCfg.TOKEN, "");//获取到token
        if (!token.equals("")) {//token和空字符验证 判断是否自动登录

            /**
             * 初始化加载框
             */
            final LodingDialog lodingDialog = new LodingDialog(this);
            lodingDialog.setmTextView(this.getString(R.string.login_verifyIng));
            lodingDialog.setCancelable(false);
            lodingDialog.show();

            /**
             * 打开子线程
             */
            WizarPosApp.getInternetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    FormBody formBody = new FormBody.Builder()
                            .add("appToken", token)
                            .build();
                    Response loginResponse = null;
                    try {
                        loginResponse = OkHttpUtil.postResponseFormServer(Url.USER_LOGIN_URL, formBody);
                        String resultStr = loginResponse.body().string();

                        /**
                         * 判断返回的字符串是否为空
                         */
                        if (resultStr != null) {
                            //解析成bean存储到application类里面
                            WizarPosApp.setmPersonalInfo(GsonUtil.parseJsonToBean(resultStr, PersonalInfo.class));
                            /**
                             * 更新ui 跳转到MainActivity
                             */
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (WizarPosApp.getmPersonalInfo() == null) {
                                        MyToast.showToast(LoginActivity.this.getString(R.string.login_token_alreday_dated));
                                        lodingDialog.dismiss();
                                        return;
                                    }
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                    MyToast.showToast(LoginActivity.this.getString(R.string.identity_verify_accomplish));
                                }
                            });
                        } else {
                            throw new IOException();
                        }
                    } catch (IOException e) {

                        /**
                         * 弹出toast 提示网络无效
                         */
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyToast.showToast(LoginActivity.this.getString(R.string.now_internet_Invalidations));
                                lodingDialog.dismiss();
                            }
                        });
                    }
                }
            });

        }

    }
}
