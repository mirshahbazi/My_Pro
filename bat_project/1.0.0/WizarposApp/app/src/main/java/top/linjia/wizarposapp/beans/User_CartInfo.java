package top.linjia.wizarposapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ClassName: User_CartInfo
 * Description: 购物车商品实体类
 * Created by 邻家新锐 on 2016/11/7 14:02
 * 作者：李鹏鹏
 * 邮箱：ppbear_ly@163.com
 */
public class User_CartInfo implements Parcelable{
    String goodsname;//商品名称
    String stname;//商品名称
    double price;//商品单价
    int num;//数量
    int id;//用户id
    int goodsId;
    int praise;
    int moq;
    int disDyq;
    int disGwq;
    boolean isCheckBox;


    protected User_CartInfo(Parcel in) {
        goodsname = in.readString();
        stname = in.readString();
        price = in.readDouble();
        num = in.readInt();
        id = in.readInt();
        goodsId=in.readInt();
        praise=in.readInt();
        moq=in.readInt();
        disDyq=in.readInt();
        disGwq=in.readInt();
        isCheckBox = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goodsname);
        dest.writeString(stname);
        dest.writeDouble(price);
        dest.writeInt(num);
        dest.writeInt(id);
        dest.writeInt(goodsId);
        dest.writeInt(praise);
        dest.writeInt(moq);
        dest.writeInt(disDyq);
        dest.writeInt(disGwq);
        dest.writeByte((byte) (isCheckBox ? 1 : 0));
    }

    public static final Creator<User_CartInfo> CREATOR = new Creator<User_CartInfo>() {
        @Override
        public User_CartInfo createFromParcel(Parcel in) {
            return new User_CartInfo(in);
        }

        @Override
        public User_CartInfo[] newArray(int size) {
            return new User_CartInfo[size];
        }
    };

    /**
     * 接口中所要有的内部类
     */


    public boolean isCheckBox() {
        return isCheckBox;
    }

    public void setCheckBox(boolean checkBox) {
        isCheckBox = checkBox;
    }

    public User_CartInfo(){}
    public User_CartInfo(String goodsname,String stname,double price,int num,int id,int goodsId,int praise,int moq,int disDyq,int disGwq){
        this.goodsname=goodsname;
        this.stname=stname;
        this.price=price;
        this.num=num;
        this.id=id;
        this.goodsId=goodsId;
        this.isCheckBox = false;
        this.praise=praise;
        this.moq=moq;
        this.disGwq=disGwq;
        this.disDyq=disDyq;
    }
    public void setGoodsname(String goodsname){
        this.goodsname=goodsname;
    }
    public String getGoodsname(){
        return goodsname;
    }
    public void setStname(String stname){
        this.stname=stname;
    }
    public String getStname(){
        return stname;
    }
    public void setPrice(double price){
        this.price=price;
    }
    public double getPrice(){
        return price;
    }
    public void setNum(int num){
        this.num=num;
    }
    public int getNum(){
        return num;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public int getGoodsId(){
        return goodsId;
    }
    public void setGoodsId(int goodsId){
        this.goodsId=goodsId;
    }
    public void setPraise(int praise){
        this.praise=praise;
    }
    public int getPraise(){
        return praise;
    }
    public void setMoq(int moq){
        this.moq=moq;
    }
    public int getMoq(){
        return moq;
    }
    public void setDisDyq(int disDyq){
        this.disDyq=disDyq;
    }
    public int getDisDyq(){
        return disDyq;
    }
    public void setDisGwq(int disGwq){
        this.disGwq=disGwq;
    }
    public int getDisGwq(){
        return disGwq;
    }
    /**
     * 接口所要实现的方法
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public String toString() {
        return "User_CartInfo{" +
                "goodsname='" + goodsname + '\'' +
                ", stname='" + stname + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", id='" + id + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", praise='" + praise + '\'' +
                ", moq='" + moq + '\'' +
                ", disDyq='" + disDyq + '\'' +
                ", disGwq='" + disGwq + '\'' +
                ", isCheckBox=" + isCheckBox +
                '}';
    }
}
