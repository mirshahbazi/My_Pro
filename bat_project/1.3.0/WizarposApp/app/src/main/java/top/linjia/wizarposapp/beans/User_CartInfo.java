package top.linjia.wizarposapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassName: User_CartInfo
 * @Description: 购物车商品实体类。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class User_CartInfo implements Parcelable{
    String goodsname;//商品名称
    String stname;//种类名称
    double price;//商品单价
    int num;//数量
    int id;//用户id
    int goodsId;
    int praise;//限定
    int moq;//起订
    int disDyq;//抵用券 0可用 1不可用
    int disGwq;//购物券
    boolean usableAdd;
    boolean usableSubject;
    boolean isCheckBox;
    int size;

    public boolean isUsableSubject() {
        return usableSubject;
    }

    public void setUsableSubject(boolean usableSubject) {
        this.usableSubject = usableSubject;
    }

    public boolean isUsableAdd() {
        return usableAdd;
    }

    public void setUsableAdd(boolean usableAdd) {
        this.usableAdd = usableAdd;
    }



    protected User_CartInfo(Parcel in) {
        goodsname = in.readString();
        stname = in.readString();
        price = in.readDouble();
        num = in.readInt();
        id = in.readInt();
        goodsId = in.readInt();
        praise = in.readInt();
        moq = in.readInt();
        disDyq = in.readInt();
        disGwq = in.readInt();
        usableAdd = in.readByte() != 0;
        usableSubject = in.readByte() != 0;
        isCheckBox = in.readByte() != 0;
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

    public boolean isCheckBox() {
        return isCheckBox;
    }

    public void setCheckBox(boolean checkBox) {
        isCheckBox = checkBox;
    }

    public User_CartInfo(int size){
        this.size=size;
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

    @Override
    public int describeContents() {
        return 0;
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
        dest.writeByte((byte) (usableAdd ? 1 : 0));
        dest.writeByte((byte) (usableSubject ? 1 : 0));
        dest.writeByte((byte) (isCheckBox ? 1 : 0));
    }

    @Override
    public String toString() {
        return "User_CartInfo{" +
                "goodsname='" + goodsname +
                '}';
    }
}
