package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * Created by 邻家新锐 on 2016/11/11 16:42
 * 作者：李鹏鹏
 * 邮箱：ppbear_ly@163.com
 */
public class OrderFromDetail {

    /**
     * address : 五台山
     * buyer : 张三
     * changeTime : 2016-11-14 15:59:49.000
     * city : null
     * cityCode : null
     * coin : 0
     * comment : null
     * commTime : null
     * county : null
     * countyCode : null
     * createTime : 2016-11-14 15:59:49.000
     * details : [{"detailId":548,"goodsName":"黄飞红麻辣花生116g","goodsNo":"LJ_PT_011","number":2,"orderId":469,"price":5.5},{"detailId":549,"goodsName":"香巴佬香辣鸭翅80g","goodsNo":"LJ_PT_012","number":2,"orderId":469,"price":3.8},{"detailId":550,"goodsName":"银鹭花生牛奶蛋白饮料1.5L瓶箱装","goodsNo":"LJ_PT_013","number":2,"orderId":469,"price":51}]
     * email : null
     * flag : 0
     * memId : 1252
     * memo : null
     * message : null
     * money : 120.6
     * omsId : null
     * orderId : 469
     * orderNo : 2016111400683
     * organ : null
     * payed : 0
     * payId : 1
     * payMoney : null
     * payState : null
     * payTime : null
     * payType : null
     * phone : 123456
     * province : null
     * provinceCode : null
     * proxy : null
     * proxyMoney : 0
     * realMoney : 0
     * recer : 张三丰
     * sendMoney : 9
     * sendTime : null
     * src : 3
     * srcCreateTime : null
     * status : 0
     * street : null
     * swId : 1
     * thirdOrderId : null
     * thirdOrderNo : null
     * thirdShopId : null
     * updateTime : null
     */

    private ResultBean result;
    /**
     * result : {"address":"五台山","buyer":"张三","changeTime":"2016-11-14 15:59:49.000","city":null,"cityCode":null,"coin":0,"comment":null,"commTime":null,"county":null,"countyCode":null,"createTime":"2016-11-14 15:59:49.000","details":[{"detailId":548,"goodsName":"黄飞红麻辣花生116g","goodsNo":"LJ_PT_011","number":2,"orderId":469,"price":5.5},{"detailId":549,"goodsName":"香巴佬香辣鸭翅80g","goodsNo":"LJ_PT_012","number":2,"orderId":469,"price":3.8},{"detailId":550,"goodsName":"银鹭花生牛奶蛋白饮料1.5L瓶箱装","goodsNo":"LJ_PT_013","number":2,"orderId":469,"price":51}],"email":null,"flag":0,"memId":1252,"memo":null,"message":null,"money":120.6,"omsId":null,"orderId":469,"orderNo":"2016111400683","organ":null,"payed":0,"payId":1,"payMoney":null,"payState":null,"payTime":null,"payType":null,"phone":"123456","province":null,"provinceCode":null,"proxy":null,"proxyMoney":0,"realMoney":0,"recer":"张三丰","sendMoney":9,"sendTime":null,"src":3,"srcCreateTime":null,"status":0,"street":null,"swId":1,"thirdOrderId":null,"thirdOrderNo":null,"thirdShopId":null,"updateTime":null}
     * state : 1
     */

    private int state;

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

    public static class ResultBean {
        private String address;
        private String buyer;
        private String changeTime;
        private String city;
        private String cityCode;
        private int coin;
        private String comment;
        private String commTime;
        private String county;
        private String countyCode;
        private String createTime;
        private String email;
        private int flag;
        private int memId;
        private String memo;
        private String message;
        private double money;
        private String omsId;
        private int orderId;
        private String orderNo;
        private String organ;
        private double payed;///   需要修改
        private int payId;
        private double payMoney;   ///   需要修改
        private String payState;   ///   需要修改
        private String payTime;
        private String payType;
        private String phone;
        private String province;
        private String provinceCode;
        private String proxy;
        private double proxyMoney;   ///   需要修改
        private double realMoney;    ///   需要修改
        private String recer;
        private double sendMoney;    ///   需要修改
        private String sendTime;
        private int src;
        private String srcCreateTime;
        private int status;
        private String street;
        private int swId;
        private String thirdOrderId;
        private String thirdOrderNo;
        private String thirdShopId;
        private String updateTime;
        /**
         * detailId : 548
         * goodsName : 黄飞红麻辣花生116g
         * goodsNo : LJ_PT_011
         * number : 2
         * orderId : 469
         * price : 5.5
         */

        private List<DetailsBean> details;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBuyer() {
            return buyer;
        }

        public void setBuyer(String buyer) {
            this.buyer = buyer;
        }

        public String getChangeTime() {
            return changeTime;
        }

        public void setChangeTime(String changeTime) {
            this.changeTime = changeTime;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCommTime() {
            return commTime;
        }

        public void setCommTime(String commTime) {
            this.commTime = commTime;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getCountyCode() {
            return countyCode;
        }

        public void setCountyCode(String countyCode) {
            this.countyCode = countyCode;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getMemId() {
            return memId;
        }

        public void setMemId(int memId) {
            this.memId = memId;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getOmsId() {
            return omsId;
        }

        public void setOmsId(String omsId) {
            this.omsId = omsId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrgan() {
            return organ;
        }

        public void setOrgan(String organ) {
            this.organ = organ;
        }

        public double getPayed() {
            return payed;
        }

        public void setPayed(double payed) {
            this.payed = payed;
        }

        public int getPayId() {
            return payId;
        }

        public void setPayId(int payId) {
            this.payId = payId;
        }

        public double getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(double payMoney) {
            this.payMoney = payMoney;
        }

        public String getPayState() {
            return payState;
        }

        public void setPayState(String payState) {
            this.payState = payState;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getProxy() {
            return proxy;
        }

        public void setProxy(String proxy) {
            this.proxy = proxy;
        }

        public double getProxyMoney() {
            return proxyMoney;
        }

        public void setProxyMoney(double proxyMoney) {
            this.proxyMoney = proxyMoney;
        }

        public double getRealMoney() {
            return realMoney;
        }

        public void setRealMoney(double realMoney) {
            this.realMoney = realMoney;
        }

        public String getRecer() {
            return recer;
        }

        public void setRecer(String recer) {
            this.recer = recer;
        }

        public double getSendMoney() {
            return sendMoney;
        }

        public void setSendMoney(double sendMoney) {
            this.sendMoney = sendMoney;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public int getSrc() {
            return src;
        }

        public void setSrc(int src) {
            this.src = src;
        }

        public String getSrcCreateTime() {
            return srcCreateTime;
        }

        public void setSrcCreateTime(String srcCreateTime) {
            this.srcCreateTime = srcCreateTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public int getSwId() {
            return swId;
        }

        public void setSwId(int swId) {
            this.swId = swId;
        }

        public String getThirdOrderId() {
            return thirdOrderId;
        }

        public void setThirdOrderId(String thirdOrderId) {
            this.thirdOrderId = thirdOrderId;
        }

        public String getThirdOrderNo() {
            return thirdOrderNo;
        }

        public void setThirdOrderNo(String thirdOrderNo) {
            this.thirdOrderNo = thirdOrderNo;
        }

        public String getThirdShopId() {
            return thirdShopId;
        }

        public void setThirdShopId(String thirdShopId) {
            this.thirdShopId = thirdShopId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
        }

        public static class DetailsBean {
            private int detailId;
            private String goodsName;
            private String goodsNo;
            private int number;
            private int orderId;
            private double price;
            private String stname;

            public int getDetailId() {
                return detailId;
            }

            public void setDetailId(int detailId) {
                this.detailId = detailId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
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

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getStname(){
                return this.stname=stname;
            }

            public void setStname(String stname){
                this.stname=stname;
            }
        }
    }
}
