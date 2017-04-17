package top.linjia.wizarposapp.global;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import top.linjia.wizarposapp.beans.PersonalInfo;

/**
 * Created by 邻家新锐 on 2016/10/22 09:46
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class WizarPosApp extends Application{
    public static Context mContext;
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
}
