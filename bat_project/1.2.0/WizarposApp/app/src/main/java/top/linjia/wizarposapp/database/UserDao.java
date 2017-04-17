package top.linjia.wizarposapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import top.linjia.wizarposapp.beans.User_CartInfo;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @ClassName: UserDao
 * @Description: 数据库访问层，提供增删改查方法供外界调用
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class UserDao {
    MySQLiteOpenHelper helper;

    public UserDao(Context context) {
        Log.i("test", "创建helper");
        helper = new MySQLiteOpenHelper(context);
    }

    /**
     * @Title: insertCart
     * @Description: 往数据库中添加一条信息
     * @param: User_CartInfo
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public boolean insertCart(User_CartInfo info) {
        Log.i("test", "商品名称是：" + info.getGoodsname());
        Log.i("test", info.getId() + "|" + info.getGoodsname() + "|" + info.getStname() + "|" + info.getPrice() + "|" + info.getNum() + "|" + info.getGoodsId());
        if (info.getGoodsname() != null) {
            SQLiteDatabase db = null;
            try {
                db = helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("select * from user_cart_info", null);
                Log.i("test", "数据库中的数据条数是：" + cursor.getCount() + "");
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    Log.i("test", "从数据库中取出的商品名称是：" + cursor.getString(cursor.getColumnIndex("goodsname")));
                    if ((cursor.getString(cursor.getColumnIndex("goodsname"))).equals(info.getGoodsname())) {
                        Log.i("test", cursor.getString(cursor.getColumnIndex("goodsname")));
                        Cursor cursor1 = db.rawQuery("select num from user_cart_info where goodsname=?", new String[]{info.getGoodsname()});
                        cursor1.moveToNext();
                        int cartnum = cursor1.getInt(cursor1.getColumnIndex("num"));
                        info.setNum(info.getNum() + cartnum);
                        db.execSQL("update user_cart_info set num=? where goodsname=?", new Object[]{info.getNum(), info.getGoodsname()});
                        Log.i("test", cursor.getString(cursor.getColumnIndex("goodsname")) + ">>>>>>>>>>>>>>>>>>>>");
                        return true;
                    }
                }
                Log.i("test", info.getId() + "|" + info.getGoodsname() + "|" + info.getStname() + "|" + info.getPrice() + "|" + info.getNum() + "|" + info.getGoodsId() + "|" + info.getPraise() + "|" + info.getMoq() + "|" + info.getDisDyq() + "|" + info.getDisGwq());
                db.execSQL("insert into user_cart_info(userId,goodsname,stname,price,num,goodsId,praise,moq,disDyq,disGwq)values(?,?,?,?,?,?,?,?,?,?)", new Object[]{info.getId(), info.getGoodsname(), info.getStname(), info.getPrice(), info.getNum(), info.getGoodsId(), info.getPraise(), info.getMoq(), info.getDisDyq(), info.getDisGwq()});
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("test", "借口异常");
            } finally {
                if (db != null && db.isOpen()) {
                    Log.i("test", (db == null) + "");
                    Log.i("test", db.getPath());
                    db.close();
                }
            }
        } else {
            MyToast.showToast("正在加载页面数据，请稍等。。");
        }
        return false;
    }

    /**
     * @Title: insertListCart
     * @Description: 往数据库中添加一个信息集合
     * @param: List<User_CartInfo>
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void insertListCart(List<User_CartInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            insertCart(list.get(i));
        }
    }

    /**
     * @Title: deletefromCart
     * @Description: 按照商品名称从数据库中删除一条数据
     * @param: String
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public boolean deletefromCart(String goodsname) {
        SQLiteDatabase db = null;
        try {
            db = helper.getReadableDatabase();
            db.execSQL("delete from user_cart_info where goodsname=?", new Object[]{goodsname});
            return true;
        } catch (Exception e) {
            Log.i("test", "借口异常");
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return false;
    }

    /**
     * @Title: deleteListFromCart
     * @Description: 从数据库中删除一个商品集合
     * @param: List<User_CartInfo>
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public void deleteListFromCart(List<User_CartInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            deletefromCart(list.get(i).getGoodsname());
        }
    }

    /**
     * @Title: updateCart
     * @Description: 按照商品名称更新购物车中对应该种商品的数量
     * @param: int String
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public boolean updateCart(int num, String goodsName) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getWritableDatabase();
            db.execSQL("update user_cart_info set num=? where goodsname=?", new Object[]{num, goodsName});
            return true;
        } catch (Exception e) {
            Log.i("test", "借口异常");
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return false;
    }

    /**
     * @Title: queryCart
     * @Description: 往购物车中插入一个商品集合
     * @param: int
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public List<User_CartInfo> queryCart(int userId) {
        SQLiteDatabase db = null;
        List<User_CartInfo> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            Log.i("test", "-----------------------------------------------------------------------" + list.size());
            db = helper.getReadableDatabase();
            cursor = db.rawQuery("select * from user_cart_info where userId=?", new String[]{userId + ""});
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("userId"));
                String goodsname = cursor.getString(cursor.getColumnIndex("goodsname"));
                String stname = cursor.getString(cursor.getColumnIndex("stname"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                int num = cursor.getInt(cursor.getColumnIndex("num"));
                int goodsId = cursor.getInt(cursor.getColumnIndex("goodsId"));
                int praise = cursor.getInt(cursor.getColumnIndex("praise"));
                int moq = cursor.getInt(cursor.getColumnIndex("moq"));
                int disDyq = cursor.getInt(cursor.getColumnIndex("disDyq"));
                int disGwq = cursor.getInt(cursor.getColumnIndex("disGwq"));
                User_CartInfo info = new User_CartInfo(goodsname, stname, price, num, id, goodsId, praise, moq, disDyq, disGwq);
                list.add(info);
                System.out.println("(づ￣ 3￣)づ");
            }
            return list;
        } catch (Exception e) {
            Log.i("test", "接口异常");
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return list;
    }

    /**
     * @Title: queryGoodsNum
     * @Description: 按照商品名称从数据库中查看该种商品的购物车数量
     * @param: String
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public int queryGoodsNum(String goodsname) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();
            cursor = db.rawQuery("select num from user_cart_info where goodsname=?", new String[]{goodsname});
            cursor.moveToNext();
            return cursor.getInt(cursor.getColumnIndex("num"));
        } catch (Exception e) {
            Log.i("test", "借口异常");
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return 0;
    }

    /**
     * @Title: queryGoodsAllNum
     * @Description: 按照用户Id从数据库中查看购物车中所有商品的总数量
     * @param: int
     * @Date: 2016/12/19
     * @author: 李鹏鹏
     */
    public int queryGoodsAllNum(int userId) {
        List<User_CartInfo> infos = queryCart(WizarPosApp.getmPersonalInfo().getResult().getId());
        if (infos != null) {
            if (infos.size() != 0) {
                int allNum = 0;
                for (int i = 0; i < infos.size(); i++) {
                    allNum += queryGoodsNum(infos.get(i).getGoodsname());
                }
                return allNum;
            } else {
                return 0;
            }
        }
        return 0;
    }

    /**
     * 传入对应的id对数据库加1
     *
     * @param goodsId
     * @return 对数据库操作成功返回true
     * 陈文豪
     */
    public boolean addGoodsNumber(String goodsId) {
        SQLiteDatabase db = null;
        db = helper.getWritableDatabase();
        try {
            db.beginTransaction();//开启事物
            db.execSQL("UPDATE user_cart_info SET num = num + 1\n" +
                    "WHERE goodsId = ?", new String[]{goodsId});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            return false;
        } finally {
            if (db != null) {
                if (db.isOpen()) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
        return true;
    }

    /**
     * 传入对应的id对数据库减1
     *
     * @param goodsId
     * @return 对数据库操作成功返回true
     * 陈文豪
     */
    public boolean subtractGoodsNumber(String goodsId) {
        SQLiteDatabase db = null;
        db = helper.getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("UPDATE user_cart_info SET num = num - 1\n" +
                    "WHERE goodsId = ?", new String[]{goodsId});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            return false;
        } finally {
            if (db != null) {
                if (db.isOpen()) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
        return true;
    }

}
