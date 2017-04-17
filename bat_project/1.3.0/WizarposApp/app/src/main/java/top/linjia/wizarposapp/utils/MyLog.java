package top.linjia.wizarposapp.utils;

import android.util.Log;

/**
 * @ClassName: Mylog
 * @Description: 日志工具类。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class MyLog {
    public boolean flag;
    public void printLog(String string){
        if(flag){
            Log.i("test",string);
        }
    }
}
