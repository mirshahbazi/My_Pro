package top.linjia.wizarposapp.apiengine;


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.linjia.wizarposapp.utils.GsonUtil;

/**
 * Created by 邻家新锐 on 2016/10/17g
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 * 加载数据 并且调度缓存数据
 */
public class DataEngine {
    private OkHttpClient mClient;
    private Request mRequest;
    private Response mResponse;
    private static DataEngine mDataEngine = new DataEngine();

    private DataEngine() {
        mClient = new OkHttpClient();
    }

    public static DataEngine getInstance() {
        return mDataEngine;
    }

    /**
     * @param url
     * @param clazz
     * @param <T>
     * @return clazz实例
     * @throws IOException
     */
    public <T> T getBean(String url, Class<T> clazz) throws IOException {
        return OkHttpUtil.getBeanFromServer(url,clazz);
    }


}
