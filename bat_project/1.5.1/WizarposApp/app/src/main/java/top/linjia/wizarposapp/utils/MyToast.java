package top.linjia.wizarposapp.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import top.linjia.wizarposapp.global.WizarPosApp;

/**
 * Created by 邻家新锐 on 2016/10/22 09:46
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class MyToast {
    private static Toast mToast;
    public static void showToast(String str){
        if(mToast == null){
            mToast = Toast.makeText(WizarPosApp.mContext,str,Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
        }
        mToast.setText(str);
        mToast.show();
    }
    /**
     * @Title: myToast
     * @Description: 自定义土司并制定位置
     * @Params: text
     * @Data: 2017/2/14 14:33
     * @Author: 李鹏鹏
     */
    public static void showToast(Context context, String text){
        TextView tv = new TextView(context);
        tv.setWidth(120);
        tv.setHeight(40);
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(14);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.BLACK);
        tv.getBackground().setAlpha(140);
        Toast toast = new Toast(context);
        toast.setView(tv);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
