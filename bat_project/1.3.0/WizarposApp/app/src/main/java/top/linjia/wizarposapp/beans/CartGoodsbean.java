package top.linjia.wizarposapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import top.linjia.wizarposapp.parentClass.holder.UpCartNumber;

/**
 * @className: top.linjia.wizarposapp.beans CartGoodsbean
 * @description: 购物车的商品bean类
 * @author 陈文豪
 * @crete 2017/1/4 15:57
 * @copyright: 2016 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class CartGoodsbean {

    /**
     * result : [{"cartId":24169,"cartNumber":11,"discPrice":96,"disDyq":true,"disGwq":false,"goodsId":437,"goodsNo":"6901035602204","moq":10,"number":15,"path":"upload/images/goods/1612211832248913.jpg","praise":0,"productionDate":"2016-08-16","unit":"箱"},{"cartId":24170,"cartNumber":5,"discPrice":510,"disDyq":false,"disGwq":false,"goodsId":438,"goodsNo":"6901035610407","moq":1,"number":66,"path":"upload/images/goods/161221156744425.jpg","praise":0,"productionDate":"2016-11-12","unit":"箱"}]
     * state : 1
     * desc :
     */

    private int state;
    private String desc;
    private List<ResultBean> result;


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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean extends UpCartNumber implements Parcelable{
        /**
         * cartId : 24169
         * cartNumber : 11
         * discPrice : 96
         * disDyq : true
         * disGwq : false
         * goodsId : 437
         * goodsNo : 6901035602204
         * moq : 10
         * number : 15
         * path : upload/images/goods/1612211832248913.jpg
         * praise : 0
         * productionDate : 2016-08-16
         * unit : 箱
         */

        private int cartId;
        private int cartNumber;
        private double discPrice;
        private boolean disDyq;
        private boolean disGwq;
        private String goodsNo;
        private int number;
        private String path;
        private int praise;
        private String productionDate;
        private String unit;
        private boolean usableAdd;
        private boolean usableSubject;
        private boolean isCheckBox = true;//
        private String stname;
        private String name;

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            cartId = in.readInt();
            cartNumber = in.readInt();
            discPrice = in.readDouble();
            disDyq = in.readByte() != 0;
            disGwq = in.readByte() != 0;
            goodsNo = in.readString();
            number = in.readInt();
            path = in.readString();
            praise = in.readInt();
            productionDate = in.readString();
            unit = in.readString();
            usableAdd = in.readByte() != 0;
            usableSubject = in.readByte() != 0;
            isCheckBox = in.readByte() != 0;
            stname = in.readString();
            name = in.readString();
        }

        public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel in) {
                return new ResultBean(in);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };

        public String getStname() {
            return stname;
        }

        public void setStname(String stname) {
            this.stname = stname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isUsableAdd() {
            return usableAdd;
        }

        public void setUsableAdd(boolean usableAdd) {
            this.usableAdd = usableAdd;
        }

        public boolean isUsableSubject() {
            return usableSubject;
        }

        public void setUsableSubject(boolean usableSubject) {
            this.usableSubject = usableSubject;
        }

        public boolean isCheckBox() {
            return isCheckBox;
        }

        public void setCheckBox(boolean checkBox) {
            isCheckBox = checkBox;
        }

        public int getCartId() {
            return cartId;
        }

        public void setCartId(int cartId) {
            this.cartId = cartId;
        }

        public int getCartNumber() {
            return cartNumber;
        }

        public void setCartNumber(int cartNumber) {
            this.cartNumber = cartNumber;
        }

        public double getDiscPrice() {
            return discPrice;
        }

        public void setDiscPrice(double discPrice) {
            this.discPrice = discPrice;
        }

        public boolean isDisDyq() {
            return disDyq;
        }

        public void setDisDyq(boolean disDyq) {
            this.disDyq = disDyq;
        }

        public boolean isDisGwq() {
            return disGwq;
        }

        public void setDisGwq(boolean disGwq) {
            this.disGwq = disGwq;
        }

        public String getGoodsNo() {
            return goodsNo;
        }

        public void setGoodsNo(String goodsNo) {
            this.goodsNo = goodsNo;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

        public String getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(String productionDate) {
            this.productionDate = productionDate;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(cartId);
            dest.writeInt(cartNumber);
            dest.writeDouble(discPrice);
            dest.writeByte((byte) (disDyq ? 1 : 0));
            dest.writeByte((byte) (disGwq ? 1 : 0));
            dest.writeString(goodsNo);
            dest.writeInt(number);
            dest.writeString(path);
            dest.writeInt(praise);
            dest.writeString(productionDate);
            dest.writeString(unit);
            dest.writeByte((byte) (usableAdd ? 1 : 0));
            dest.writeByte((byte) (usableSubject ? 1 : 0));
            dest.writeByte((byte) (isCheckBox ? 1 : 0));
            dest.writeString(stname);
            dest.writeString(name);
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "cartId=" + cartId +
                    ", cartNumber=" + cartNumber +
                    ", discPrice=" + discPrice +
                    ", disDyq=" + disDyq +
                    ", disGwq=" + disGwq +
                    ", goodsNo='" + goodsNo + '\'' +
                    ", number=" + number +
                    ", path='" + path + '\'' +
                    ", praise=" + praise +
                    ", productionDate='" + productionDate + '\'' +
                    ", unit='" + unit + '\'' +
                    ", usableAdd=" + usableAdd +
                    ", usableSubject=" + usableSubject +
                    ", isCheckBox=" + isCheckBox +
                    ", stname='" + stname + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
