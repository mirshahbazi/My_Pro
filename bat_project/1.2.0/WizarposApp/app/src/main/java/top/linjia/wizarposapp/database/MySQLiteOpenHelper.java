package top.linjia.wizarposapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import top.linjia.wizarposapp.global.WizarPosApp;

/**
 * @ClassName: MySQLiteOpenHelper
 * @Description: 数据库助手类，建了两张表，一张购物车表，一种用户信息表。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public MySQLiteOpenHelper(Context context) {
        super(context, "user_cartinfo.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists user_cart_info");
        db.execSQL("create table user_cart_info(userId integer,goodsname varchar(50) primary key,stname varchar(20),price double,num integer,goodsId integer,praise integer,moq integer,disDyq integer,disGwq integer)");
        Log.i("test", "表创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user_cart_info");
        db.execSQL("create table user_cart_info(userId integer,goodsname varchar(50) primary key,stname varchar(20),price double,num integer,goodsId integer,praise integer,moq integer,disDyq integer,disGwq integer)");
        Log.i("test", "数据库升级成功");
    }

    /**
     * 数据库升级后保存旧版本购物车数据库中的商品信息，主要保存商品名称和商品数量
     */
    public List<HashMap<Integer, Integer>> obtainCartInfo() {
        if (WizarPosApp.getmPersonalInfo() != null) {
            List<HashMap<Integer, Integer>> list = new ArrayList<>();
            Cursor cursor = this.getReadableDatabase().rawQuery("select * from user_cart_info", new String[]{WizarPosApp.getmPersonalInfo().getResult().getId() + ""});
            while (cursor.moveToNext()) {
                Integer goodsId = cursor.getInt(cursor.getColumnIndex("goodsId"));
                int num = cursor.getInt(cursor.getColumnIndex("num"));
                HashMap<Integer, Integer> hashMap = new HashMap<>();
                hashMap.put(goodsId, num);
                list.add(hashMap);
            }
            return list;
        }
        return null;
    }
}
