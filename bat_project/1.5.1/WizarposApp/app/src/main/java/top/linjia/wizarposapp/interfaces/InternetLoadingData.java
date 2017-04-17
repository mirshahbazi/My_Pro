package top.linjia.wizarposapp.interfaces;


/**
 * Created by 河南巧脉信息技术 on 2016/11/1 17:21
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 * 网络请求回调类 极少数情况可以用到
 */
public interface InternetLoadingData<T>{
        public void succeed(T instancs);
        public void fail(String msg);
}
