package top.linjia.wizarposapp.apiengine;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.linjia.wizarposapp.utils.GsonUtil;

/**
 * Created by 邻家新锐 on 2016/10/22 10:58
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */

public class OkHttpUtil {
    private static OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS);
    private static final OkHttpClient build = builder.build();
    private static OkHttpClient mOkHttpClient = build;

    static {
//        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * 该不会开启异步线程。
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     */
    public static void enqueue(Request request, Callback responseCallback) {
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * @param url
     * @param clazz
     * @param <T>
     * @return 直接输入url和bean类的字节码返回bean类
     * @throws IOException
     */
    public static <T> T getBeanFromServer(String url, Class<T> clazz) throws IOException {
        Request request = new Request.Builder().url(url).get().build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            return GsonUtil.parseJsonToBean(response.body().string(), clazz);
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     *
     * @param url
     * @param formBody
     * @return Response
     * @throws IOException
     * 传入url和formbody 返回Response
     */
    public static Response postResponseFormServer(String url, FormBody formBody) throws IOException {
        return postCallFormServer(url,formBody).execute();
    }


    /**
     *
     * @param url
     * @param formBody
     * @return Call 用于取消请求
     */
    public static Call postCallFormServer(String url,FormBody formBody){
        Request request = new Request.Builder().url(url).post(formBody).build();
        return mOkHttpClient.newCall(request);
    }

    private static final String CHARSET_NAME = "UTF-8";


    /**
     *
     * @param url
     * @param formBody
     * @param clazz
     * @param <T>
     * @return 传入参数返回bean类
     * @throws IOException
     */
    public static <T>T postBeanFormServer(String url,FormBody formBody,Class<T> clazz) throws IOException {
        Response response = postResponseFormServer(url,formBody);
        String strJson = response.body().string();//// TODO: 2016/10/25 加入缓存操作
        System.out.println("-----------------------------"+strJson);
        T instance = GsonUtil.parseJsonToBean(strJson,clazz);
        return instance;
    }

    /**
     * 为HttpGet 的 url 方便的添加1个name value 参数。
     */
    public static String attachHttpGetParam(String url, String name, String value) {
        return url + "?" + name + "=" + value;
    }

    /**
     * 指定方式的去post提交
     *
     * 这个方法向服务器提交json格式的数据
     * @param url
     * @param requestBody
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T>T postBeanFormServer(String url, String postJson, Class<T> clazz) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson); //设置post提交类型
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response =  mOkHttpClient.newCall(request).execute();
        String jsonStr = "";
        if(response.isSuccessful()) {
            jsonStr = response.body().string();
        }
        System.out.println("(づ￣ 3￣)づ"+jsonStr);
        return GsonUtil.parseJsonToBean(jsonStr,clazz);
    }

    /**
     *
     * @param call
     * @param clazz
     * @param <T>
     * @return 返回通过Call解析好的bean类 用于做取消请求时的快捷类
     * @throws IOException
     */
    public static <T> T inCallOutBean(Call call,Class<T> clazz) throws IOException {
        String result = call.execute().body().string();
        return GsonUtil.parseJsonToBean(result,clazz);
    }

    /**
     * post异步请求回调接口
     * 作者：陈文豪
     */
    interface postResult{
        public void postSucceed();
        public void postFail();
    }
}
