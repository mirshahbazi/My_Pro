package top.linjia.wizarposapp.beans;

import com.google.gson.Gson;

/**
 * @className: top.linjia.wizarposapp.beans UpCartNumberBean
 * @description: 购物车修改返回结果 bean
 * @author 陈文豪
 * @crete 2017/1/3 12:01
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class UpCartNumberBean {

    /**
     * result : {"number":12,"cartGoodsNum":23,"state":1,"desc":"操作成功"}
     * state : 1
     * desc :
     */

    private ResultBean result;
    private int state;
    private String desc;

    public static UpCartNumberBean objectFromData(String str) {

        return new Gson().fromJson(str, UpCartNumberBean.class);
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class ResultBean {
        /**
         * number : 12
         * cartGoodsNum : 23
         * state : 1
         * desc : 操作成功
         */

        private int number;
        private int cartGoodsNum;
        private int state;
        private String desc;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getCartGoodsNum() {
            return cartGoodsNum;
        }

        public void setCartGoodsNum(int cartGoodsNum) {
            this.cartGoodsNum = cartGoodsNum;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
