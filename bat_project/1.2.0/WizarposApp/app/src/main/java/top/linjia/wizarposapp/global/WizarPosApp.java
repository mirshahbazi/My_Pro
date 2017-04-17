package top.linjia.wizarposapp.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import top.linjia.wizarposapp.beans.PersonalInfo;

/**
 * @className: top.linjia.wizarposapp.global WizarPosApp
 * @description:
 * @author 陈文豪
 * @crete 2016/10/22 09:46
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class WizarPosApp extends Application{
    public static Context mContext;
    private Handler handler = new Handler();
    private static OkHttpClient mOkHttpClient = new OkHttpClient();
    private static PersonalInfo mPersonalInfo;
    private static ExecutorService internetThreadPool = Executors.newSingleThreadExecutor();

    public static ExecutorService getInternetThreadPool(){
        return internetThreadPool;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static OkHttpClient getHttpInstance(){
        return mOkHttpClient;
    }

    public static PersonalInfo getmPersonalInfo() {
        return mPersonalInfo;
    }

    public static void setmPersonalInfo(PersonalInfo mPersonalInfoData) {
        mPersonalInfo = mPersonalInfoData;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
