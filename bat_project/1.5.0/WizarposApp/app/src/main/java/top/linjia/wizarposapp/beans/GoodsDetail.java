package top.linjia.wizarposapp.beans;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassName: GoodsDetail
 * @Description: 商品详情实体类。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class GoodsDetail implements Parcelable{

    /**
     * brandId : 1
     * brcode : LJ_003
     * cbrand : null
     * cname : 食品
     * discPrice : 1
     * goodsId : 1776
     * moq : 1
     * NAME : 测试商品箱
     * number : 5
     * path : upload/images/goods/161128792900364.jpg
     * position : 5
     * praise : 81
     * price : 1
     * productionDate : null
     * stname : 测试商品箱
     * unit : null
     * disDyq: false,
     *disGwq: false,
     * brandName:String
     */

    private ResultBean result;
    /**
     * result : {"brandId":1,"brcode":"LJ_003","cbrand":null,"cname":"食品","discPrice":1,"goodsId":1776,"moq":1,"NAME":"测试商品箱","number":5,"path":"upload/images/goods/161128792900364.jpg","position":5,"praise":81,"price":1,"productionDate":null,"stname":"测试商品箱","unit":null}
     * state : 1
     */

    private int state;


    protected GoodsDetail(Parcel in) {
        state = in.readInt();
    }

    public static final Creator<GoodsDetail> CREATOR = new Creator<GoodsDetail>() {
        @Override
        public GoodsDetail createFromParcel(Parcel in) {
            return new GoodsDetail(in);
        }

        @Override
        public GoodsDetail[] newArray(int size) {
            return new GoodsDetail[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(state);
    }


    public static class ResultBean {
        private int brandId;
        private String brcode;
        private String cbrand;
        private String cname;
        private double discPrice;
        private int goodsId;
        private int moq;
        private String NAME;
        private int number;
        private String path;
        private int position;
        private int praise;
        private double price;
        private String productionDate;
        private String stname;
        private String unit;
        private int cartNumber;
        private boolean disDyq;
        private boolean disGwq;
        private String brandName;

        public int getCartNumber() {
            return cartNumber;
        }

        public void setCartNumber(int cartNumber) {
            this.cartNumber = cartNumber;
        }

        public boolean isDisDyq() {
            return disDyq;
        }

        public boolean isDisGwq() {
            return disGwq;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public String getBrcode() {
            return brcode;
        }

        public void setBrcode(String brcode) {
            this.brcode = brcode;
        }

        public String getCbrand() {
            return cbrand;
        }

        public void setCbrand(String cbrand) {
            this.cbrand = cbrand;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public double getDiscPrice() {
            return discPrice;
        }

        public void setDiscPrice(double discPrice) {
            this.discPrice = discPrice;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getMoq() {
            return moq;
        }

        public void setMoq(int moq) {
            this.moq = moq;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
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

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(String productionDate) {
            this.productionDate = productionDate;
        }

        public String getStname() {
            return stname;
        }

        public void setStname(String stname) {
            this.stname = stname;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public void setDisDyq(boolean disDyq){
            this.disDyq=disDyq;
        }
        public boolean getDisDyq(){
            return disDyq;
        }
        public void setDisGwq(boolean disGwq){
            this.disGwq=disGwq;
        }
        public boolean getDisGwq(){
            return disGwq;
        }
        public void setBrandName(String brandName){
            this.brandName=brandName;
        }
        public String getBrandName(){
            return brandName;
        }
    }
}
