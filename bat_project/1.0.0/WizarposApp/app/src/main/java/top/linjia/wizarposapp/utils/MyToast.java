package top.linjia.wizarposapp.utils;

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
        }
        mToast.setText(str);
        mToast.show();
    }
}
