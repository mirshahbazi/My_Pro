package top.linjia.wizarposapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import top.linjia.wizarposapp.parentClass.holder.UpCartNumber;

/**
 * Created by 邻家新锐 on 2016/10/24 12:07
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class GoodsList implements Parcelable{

    /**
     * list : [{"discPrice":0.01,"disDyq":false,"disGwq":false,"goodsId":1770,"goodsNo":"6921581540096","moq":1,"name":"三得利沁柠水柠檬味550ml*15","number":5,"path":"upload/images/goods/1611242021098204.jpg","praise":0,"price":0.01,"stname":"三得利沁柠水柠檬味550ml*15","unit":"箱"},{"discPrice":1,"disDyq":false,"disGwq":false,"goodsId":1769,"goodsNo":"6921581540287","moq":1,"name":"三得利利趣拿铁480ml*15","number":45,"path":"upload/images/goods/161124497590338.jpg","praise":2,"price":1,"stname":"三得利利趣拿铁480ml*15","unit":"箱"},{"discPrice":0.01,"disDyq":false,"disGwq":false,"goodsId":1768,"goodsNo":"6925303751364","moq":1,"name":"统一绿茶500ml*15","number":1,"path":"upload/images/goods/161124752061406.jpg","praise":2,"price":0.01,"stname":"统一绿茶500ml*15","unit":"箱"},{"discPrice":1092,"disDyq":false,"disGwq":false,"goodsId":1767,"goodsNo":"6925303735579","moq":1,"name":"统一阿萨姆原味奶茶500ml*15","number":108,"path":"upload/images/goods/1611241111979118.jpg","praise":0,"price":1092,"stname":"统一阿萨姆原味奶茶500ml*15","unit":"箱"},{"discPrice":1,"disDyq":false,"disGwq":false,"goodsId":1766,"goodsNo":"6921581530547","moq":1,"name":"三得利乌龙茶无糖500ml*15","number":5,"path":"upload/images/goods/1611251926308280.png","praise":0,"price":1,"stname":"三得利乌龙茶无糖500ml*15","unit":"箱"},{"discPrice":1,"disDyq":false,"disGwq":true,"goodsId":1776,"goodsNo":"LJ_003","moq":1,"name":"测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱111111111111111111111111111测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱","number":5,"path":"upload/images/goods/1611282143652525.jpg","praise":81,"price":1,"stname":"测试商品箱","unit":null},{"discPrice":1,"disDyq":true,"disGwq":true,"goodsId":1777,"goodsNo":"6920459989456","moq":1,"name":"康师傅冰糖雪梨550ml*15瓶","number":7,"path":"upload/images/goods/161130939357893.jpg","praise":0,"price":1,"stname":"550ml*15瓶","unit":null},{"discPrice":1,"disDyq":false,"disGwq":false,"goodsId":1765,"goodsNo":"6924743915084","moq":1,"name":"乐事意大利香浓红烩味40g*10包","number":7,"path":"upload/images/goods/1611301587361424.jpg","praise":0,"price":1,"stname":"乐事意大利香浓红烩味40g*10包","unit":null},{"discPrice":1,"disDyq":false,"disGwq":false,"goodsId":1763,"goodsNo":"6924743919259","moq":1,"name":"乐事马铃薯片黄瓜味70g","number":108,"path":"upload/images/goods/1611301519774002.jpg","praise":0,"price":1,"stname":"乐事马铃薯片黄瓜味70g","unit":null},{"discPrice":1,"disDyq":false,"disGwq":false,"goodsId":1762,"goodsNo":"6924743915077","moq":1,"name":"乐事得克萨斯烧烤味40g*10包","number":108,"path":"upload/images/goods/1611301471246453.jpg","praise":0,"price":1,"stname":"乐事得克萨斯烧烤味40g*10包","unit":null}]
     * pageNumber : 1
     * pageSize : 10
     * totalPage : 3
     * totalRow : 22
     */

    private ResultBean result;
    /**
     * result : {"list":[{"discPrice":0.01,"disDyq":false,"disGwq":false,"goodsId":1770,"goodsNo":"6921581540096","moq":1,"name":"三得利沁柠水柠檬味550ml*15","number":5,"path":"upload/images/goods/1611242021098204.jpg","praise":0,"price":0.01,"stname":"三得利沁柠水柠檬味550ml*15","unit":"箱"},{"discPrice":1,"disDyq":false,"disGwq":false,"goodsId":1769,"goodsNo":"6921581540287","moq":1,"name":"三得利利趣拿铁480ml*15","number":45,"path":"upload/images/goods/161124497590338.jpg","praise":2,"price":1,"stname":"三得利利趣拿铁480ml*15","unit":"箱"},{"discPrice":0.01,"disDyq":false,"disGwq":false,"goodsId":1768,"goodsNo":"6925303751364","moq":1,"name":"统一绿茶500ml*15","number":1,"path":"upload/images/goods/161124752061406.jpg","praise":2,"price":0.01,"stname":"统一绿茶500ml*15","unit":"箱"},{"discPrice":1092,"disDyq":false,"disGwq":false,"goodsId":1767,"goodsNo":"6925303735579","moq":1,"name":"统一阿萨姆原味奶茶500ml*15","number":108,"path":"upload/images/goods/1611241111979118.jpg","praise":0,"price":1092,"stname":"统一阿萨姆原味奶茶500ml*15","unit":"箱"},{"discPrice":1,"disDyq":false,"disGwq":false,"goodsId":1766,"goodsNo":"6921581530547","moq":1,"name":"三得利乌龙茶无糖500ml*15","number":5,"path":"upload/images/goods/1611251926308280.png","praise":0,"price":1,"stname":"三得利乌龙茶无糖500ml*15","unit":"箱"},{"discPrice":1,"disDyq":false,"disGwq":true,"goodsId":1776,"goodsNo":"LJ_003","moq":1,"name":"测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱111111111111111111111111111测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱测试商品箱","number":5,"path":"upload/images/goods/1611282143652525.jpg","praise":81,"price":1,"stname":"测试商品箱","unit":null},{"discPrice":1,"disDyq":true,"disGwq":true,"goodsId":1777,"goodsNo":"6920459989456","moq":1,"name":"康师傅冰糖雪梨550ml*15瓶","number":7,"path":"upload/images/goods/161130939357893.jpg","praise":0,"price":1,"stname":"550ml*15瓶","unit":null},{"discPrice":1,"disDyq":false,"disGwq":false,"goodsId":1765,"goodsNo":"6924743915084","moq":1,"name":"乐事意大利香浓红烩味40g*10包","number":7,"path":"upload/images/goods/1611301587361424.jpg","praise":0,"price":1,"stname":"乐事意大利香浓红烩味40g*10包","unit":null},{"discPrice":1,"disDyq":false,"disGwq":false,"goodsId":1763,"goodsNo":"6924743919259","moq":1,"name":"乐事马铃薯片黄瓜味70g","number":108,"path":"upload/images/goods/1611301519774002.jpg","praise":0,"price":1,"stname":"乐事马铃薯片黄瓜味70g","unit":null},{"discPrice":1,"disDyq":false,"disGwq":false,"goodsId":1762,"goodsNo":"6924743915077","moq":1,"name":"乐事得克萨斯烧烤味40g*10包","number":108,"path":"upload/images/goods/1611301471246453.jpg","praise":0,"price":1,"stname":"乐事得克萨斯烧烤味40g*10包","unit":null}],"pageNumber":1,"pageSize":10,"totalPage":3,"totalRow":22}
     * state : 1
     */

    private int state;

    protected GoodsList(Parcel in) {
        state = in.readInt();
    }

    public static final Creator<GoodsList> CREATOR = new Creator<GoodsList>() {
        @Override
        public GoodsList createFromParcel(Parcel in) {
            return new GoodsList(in);
        }

        @Override
        public GoodsList[] newArray(int size) {
            return new GoodsList[size];
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
        private int pageNumber;
        private int pageSize;
        private int totalPage;
        private int totalRow;
        /**
         * discPrice : 0.01
         * disDyq : false
         * disGwq : false
         * goodsId : 1770
         * goodsNo : 6921581540096
         * moq : 1
         * name : 三得利沁柠水柠檬味550ml*15
         * number : 5
         * path : upload/images/goods/1611242021098204.jpg
         * praise : 0
         * price : 0.01
         * stname : 三得利沁柠水柠檬味550ml*15
         * unit : 箱
         */

        private List<ListBean> list;

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalRow() {
            return totalRow;
        }

        public void setTotalRow(int totalRow) {
            this.totalRow = totalRow;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean extends UpCartNumber{
            private double discPrice;
            private boolean disDyq;
            private boolean disGwq;
            private String goodsNo;
            private String name;
            private int number;
            private String path;
            private int praise;
            private double price;
            private String stname;
            private String unit;
            private int cartNumber;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
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
        }
    }
}
