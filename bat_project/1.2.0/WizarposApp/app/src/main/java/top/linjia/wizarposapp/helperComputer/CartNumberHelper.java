package top.linjia.wizarposapp.helperComputer;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import top.linjia.wizarposapp.R;
import top.linjia.wizarposapp.adapters.MainGoodsListAdapter;
import top.linjia.wizarposapp.apiengine.OkHttpUtil;
import top.linjia.wizarposapp.apiengine.Url;
import top.linjia.wizarposapp.beans.GoodsList;
import top.linjia.wizarposapp.beans.UpCartNumberBean;
import top.linjia.wizarposapp.global.WizarPosApp;
import top.linjia.wizarposapp.parentClass.holder.UpCartNumber;
import top.linjia.wizarposapp.utils.MyToast;

/**
 * @author 陈文豪
 * @className: top.linjia.wizarposapp.helperComputer CartNumberHelper
 * @description: 计算和购物车相关的操作
 * @crete 2016/12/30 10:59
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class CartNumberHelper {

    public static final int ADD_CART_NUMBER = 1;//点击添加时候
    public static final int SUBSTRAT_CART_NUMBER = -1;//点击减少的时候
    public static final int FIRST_ADD_CART_NUMBER = 0;//点击展开的时候

    /**
     * @param textView
     * @return void
     * @Title: computerCartSum
     * @Description: 负责商品总量的数据显示
     * @date 2016/12/30 11:01
     * @author 陈文豪
     */
    public static void computerCartSum(TextView textView, int sum) {
        isShowAndHide(textView, sum);
        textView.setText(String.valueOf(sum));
    }

    /**
     * @param sum
     * @param textView
     * @return void
     * @Title: isShowAndHide
     * @Description: 负责总量的显示和隐藏
     * @date 2016/12/30 11:08
     * @author 陈文豪
     */
    public static void isShowAndHide(TextView textView, int sum) {
        if (sum <= 0) {
            textView.setVisibility(View.GONE);
        } else if (textView.getVisibility() == View.GONE) {
            textView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * @param goodsId
     * @param number
     * @return UpCartNumberBean
     * @throws IOException
     * @Title: upCartNumber
     * @Description: 更新数据库的购物车数量 同步执行
     * @date 2017/1/4 9:45
     * @author 陈文豪
     */
    public static UpCartNumberBean upCartNumber(int goodsId, int number) throws IOException {//{"appToken":"cc4a4ca5dcfc4943a2167eae02ad6f13","goodsId":437,"number":2}
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("appToken", WizarPosApp.getmPersonalInfo().getAppToken());
        builder.add("goodsId", String.valueOf(goodsId));
        builder.add("number", String.valueOf(number));
        FormBody formBody = builder.build();
        UpCartNumberBean upCartNumberBean = OkHttpUtil.postBeanFormServer(Url.UPDATA_CART_NUMBER, formBody, UpCartNumberBean.class);
        return upCartNumberBean;
    }

    /**
     * @param goodsId
     * @return upCartNumberBean
     * @throws IOException
     * @Title: addCartNumber
     * @Description: 向数据库加1 同步执行
     * @date 2017/1/4 9:57
     * @author 陈文豪
     */
    public static UpCartNumberBean addCartNumber(int goodsId) throws IOException {
        return upCartNumber(goodsId, 1);
    }

    /**
     * @param goodsId
     * @return UpCartNumberBean
     * @throws IOException
     * @Title: subtractCartNumber
     * @Description: 向数据库减1 同步执行
     * @date 2017/1/4 10:02
     * @author 陈文豪
     */
    public static UpCartNumberBean subtractCartNumber(int goodsId) throws IOException {
        return upCartNumber(goodsId, -1);
    }


    /**
     * @param code
     * @param position
     * @return int
     * @throws IOException
     * @Title: upCartNumber
     * @Description: 发送请求 修改购物车数量 由客户端代码直接调用
     * @date 2017/1/4 11:54
     * @author 陈文豪
     */
    public static int upCartNumberMethod(int code,
                                         int position, List<? extends UpCartNumber> mList,
                                         Context mContext) throws IOException {
        switch (code) {
            case ADD_CART_NUMBER:
                mList.get(position).setUpCartNumberBean(CartNumberHelper.addCartNumber(mList.get(position).getGoodsId()));
                break;
            case SUBSTRAT_CART_NUMBER:
                mList.get(position).setUpCartNumberBean(CartNumberHelper.subtractCartNumber(mList.get(position).getGoodsId()));
                break;
            case FIRST_ADD_CART_NUMBER:
                mList.get(position).setUpCartNumberBean(CartNumberHelper.upCartNumber(mList.get(position).getGoodsId(),
                        mList.get(position).getMoq()));
                break;
            default:
                upUI("Parse Code Error", mContext);
        }

        return verdictResult(mList.get(position).getUpCartNumberBean(), mContext);
    }

    /**
     * @param temp
     * @return int
     * @Title: verdictResult
     * @Description: 对返回的内容做出判断 并且更新购物车总数量
     * @date 2017/1/4 11:18
     * @author 陈文豪
     */
    public static int verdictResult(UpCartNumberBean temp, Context mContext) {
        /**
         * 数据为空
         */
        if (temp == null) {
            upUI(mContext.getString(R.string.not_know), mContext);
            return -1;
        }
        /**
         * 登陆状态为0
         */
        if (temp.getState() == 0) {
            upUI(temp.getDesc(), mContext);
            return -1;
        }
        /**
         * 更新状态为0
         */
        if (temp.getResult().getState() == 0) {
            upUI(temp.getResult().getDesc(), mContext);
            return -1;
        }
        //更新购物车总数量
        WizarPosApp.getmPersonalInfo().setCartGoodsNum(temp.getResult().getCartGoodsNum());
        return temp.getResult().getNumber();
    }

    /**
     * @param msg
     * @return
     * @Title: upUI
     * @Description: 传入消息 修改ui
     * @date 2017/1/4 11:28
     * @author 陈文豪
     */
    public static void upUI(final String msg, Context mContext) {
        upUI(new Runnable() {
            @Override
            public void run() {
                MyToast.showToast(msg);
            }
        }, mContext);
    }

    /**
     * @param runnable
     * @param mContext
     * @return
     * @Title: upUI
     * @Description: 传入runable 修改ui
     * @date 2017/1/4 11:28
     * @author 陈文豪
     */
    public static void upUI(Runnable runnable, Context mContext) {
        new Handler(mContext.getMainLooper()).post(runnable);
    }
}
