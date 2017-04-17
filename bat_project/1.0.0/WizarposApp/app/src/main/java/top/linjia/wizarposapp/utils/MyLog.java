package top.linjia.wizarposapp.utils;

import android.util.Log;

/**
 * ClassName: Mylog
 * Description: 日志工具类
 * Created by 邻家新锐 on 2016/10/27 12:02
 * 作者：李鹏鹏
 * 邮箱：ppbear_ly@163.com
 */


public class MyLog {
    public boolean flag;
    public void printLog(String string){
        if(flag){
            Log.i("test",string);
        }
    }
}
