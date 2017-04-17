package top.linjia.wizarposapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * ClassName: MySQLiteOpenHelper
 * Description: 数据库助手类，建了两张表，一张购物车表，一种用户信息表
 * Created by 邻家新锐 on 2016/11/7 14:10
 * 作者：李鹏鹏
 * 邮箱：ppbear_ly@163.com
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper{
    public MySQLiteOpenHelper(Context context){
        super(context,"user_cartinfo.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists " + "user_cart_info");
        db.execSQL("create table user_cart_info(userId integer,goodsname varchar(50) primary key,stname varchar(20),price double,num integer,goodsId integer,praise integer,moq integer,disDyq integer,disGwq integer)");
        Log.i("test","表创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + "user_cart_info");
        onCreate(db);
    }
}
