package top.linjia.wizarposapp.beans;

/**
 * Created by 河南巧脉信息技术 on 2016/12/22 10:33
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class CouponVerificationBean {

    /**
     * isCanUse : true
     * order : {"address":"五台山","buyer":"李鹏鹏1128","changeTime":"2016-12-20 17:41:06.000","city":null,"cityCode":null,"coin":5,"comment":null,"commTime":null,"county":null,"countyCode":null,"createTime":"2016-12-20 17:41:06.000","email":null,"flag":0,"hyStored":null,"linjiaMoney":0,"memId":1252,"memo":null,"message":null,"money":150,"omsId":null,"orderId":1202,"orderNo":"2016122001466","payed":154,"payId":1,"payMoney":154,"payState":0,"payTime":"2016-12-21 16:54:25.000","payType":null,"phone":"123456","province":null,"provinceCode":null,"proxy":null,"proxyMoney":0,"realMoney":0,"recer":"张三丰1128","reduction":null,"sendMoney":9,"sendTime":null,"src":3,"srcCreateTime":null,"status":0,"street":null,"swId":1,"thirdOrderId":null,"thirdOrderNo":null,"thirdShopId":null,"updateTime":null}
     * desc :
     */

    private ResultBean result;
    /**
     * result : {"isCanUse":true,"order":{"address":"五台山","buyer":"李鹏鹏1128","changeTime":"2016-12-20 17:41:06.000","city":null,"cityCode":null,"coin":5,"comment":null,"commTime":null,"county":null,"countyCode":null,"createTime":"2016-12-20 17:41:06.000","email":null,"flag":0,"hyStored":null,"linjiaMoney":0,"memId":1252,"memo":null,"message":null,"money":150,"omsId":null,"orderId":1202,"orderNo":"2016122001466","payed":154,"payId":1,"payMoney":154,"payState":0,"payTime":"2016-12-21 16:54:25.000","payType":null,"phone":"123456","province":null,"provinceCode":null,"proxy":null,"proxyMoney":0,"realMoney":0,"recer":"张三丰1128","reduction":null,"sendMoney":9,"sendTime":null,"src":3,"srcCreateTime":null,"status":0,"street":null,"swId":1,"thirdOrderId":null,"thirdOrderNo":null,"thirdShopId":null,"updateTime":null},"desc":""}
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
        private boolean isCanUse;
        /**
         * address : 五台山
         * buyer : 李鹏鹏1128
         * changeTime : 2016-12-20 17:41:06.000
         * city : null
         * cityCode : null
         * coin : 5
         * comment : null
         * commTime : null
         * county : null
         * countyCode : null
         * createTime : 2016-12-20 17:41:06.000
         * email : null
         * flag : 0
         * hyStored : null
         * linjiaMoney : 0
         * memId : 1252
         * memo : null
         * message : null
         * money : 150
         * omsId : null
         * orderId : 1202
         * orderNo : 2016122001466
         * payed : 154
         * payId : 1
         * payMoney : 154
         * payState : 0
         * payTime : 2016-12-21 16:54:25.000
         * payType : null
         * phone : 123456
         * province : null
         * provinceCode : null
         * proxy : null
         * proxyMoney : 0
         * realMoney : 0
         * recer : 张三丰1128
         * reduction : null
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

        private OrderBean order;
        private String desc;

        public boolean isIsCanUse() {
            return isCanUse;
        }

        public void setIsCanUse(boolean isCanUse) {
            this.isCanUse = isCanUse;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }



        public static class OrderBean {
            private String address;
            private String buyer;
            private String changeTime;
            private Object city;
            private Object cityCode;
            private int coin;
            private Object comment;
            private Object commTime;
            private Object county;
            private Object countyCode;
            private String createTime;
            private Object email;
            private int flag;
            private Object hyStored;
            private int linjiaMoney;
            private int memId;
            private Object memo;
            private Object message;
            private double money;
            private Object omsId;
            private int orderId;
            private String orderNo;
            private int payed;
            private int payId;
            private double payMoney;
            private int payState;
            private String payTime;
            private Object payType;
            private String phone;
            private Object province;
            private Object provinceCode;
            private Object proxy;
            private int proxyMoney;
            private int realMoney;
            private String recer;
            private Object reduction;
            private int sendMoney;
            private Object sendTime;
            private int src;
            private Object srcCreateTime;
            private int status;
            private Object street;
            private int swId;
            private Object thirdOrderId;
            private Object thirdOrderNo;
            private Object thirdShopId;
            private Object updateTime;

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

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public Object getCityCode() {
                return cityCode;
            }

            public void setCityCode(Object cityCode) {
                this.cityCode = cityCode;
            }

            public int getCoin() {
                return coin;
            }

            public void setCoin(int coin) {
                this.coin = coin;
            }

            public Object getComment() {
                return comment;
            }

            public void setComment(Object comment) {
                this.comment = comment;
            }

            public Object getCommTime() {
                return commTime;
            }

            public void setCommTime(Object commTime) {
                this.commTime = commTime;
            }

            public Object getCounty() {
                return county;
            }

            public void setCounty(Object county) {
                this.county = county;
            }

            public Object getCountyCode() {
                return countyCode;
            }

            public void setCountyCode(Object countyCode) {
                this.countyCode = countyCode;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public Object getHyStored() {
                return hyStored;
            }

            public void setHyStored(Object hyStored) {
                this.hyStored = hyStored;
            }

            public int getLinjiaMoney() {
                return linjiaMoney;
            }

            public void setLinjiaMoney(int linjiaMoney) {
                this.linjiaMoney = linjiaMoney;
            }

            public int getMemId() {
                return memId;
            }

            public void setMemId(int memId) {
                this.memId = memId;
            }

            public Object getMemo() {
                return memo;
            }

            public void setMemo(Object memo) {
                this.memo = memo;
            }

            public Object getMessage() {
                return message;
            }

            public void setMessage(Object message) {
                this.message = message;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public Object getOmsId() {
                return omsId;
            }

            public void setOmsId(Object omsId) {
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

            public int getPayed() {
                return payed;
            }

            public void setPayed(int payed) {
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

            public int getPayState() {
                return payState;
            }

            public void setPayState(int payState) {
                this.payState = payState;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public Object getPayType() {
                return payType;
            }

            public void setPayType(Object payType) {
                this.payType = payType;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getProvince() {
                return province;
            }

            public void setProvince(Object province) {
                this.province = province;
            }

            public Object getProvinceCode() {
                return provinceCode;
            }

            public void setProvinceCode(Object provinceCode) {
                this.provinceCode = provinceCode;
            }

            public Object getProxy() {
                return proxy;
            }

            public void setProxy(Object proxy) {
                this.proxy = proxy;
            }

            public int getProxyMoney() {
                return proxyMoney;
            }

            public void setProxyMoney(int proxyMoney) {
                this.proxyMoney = proxyMoney;
            }

            public int getRealMoney() {
                return realMoney;
            }

            public void setRealMoney(int realMoney) {
                this.realMoney = realMoney;
            }

            public String getRecer() {
                return recer;
            }

            public void setRecer(String recer) {
                this.recer = recer;
            }

            public Object getReduction() {
                return reduction;
            }

            public void setReduction(Object reduction) {
                this.reduction = reduction;
            }

            public int getSendMoney() {
                return sendMoney;
            }

            public void setSendMoney(int sendMoney) {
                this.sendMoney = sendMoney;
            }

            public Object getSendTime() {
                return sendTime;
            }

            public void setSendTime(Object sendTime) {
                this.sendTime = sendTime;
            }

            public int getSrc() {
                return src;
            }

            public void setSrc(int src) {
                this.src = src;
            }

            public Object getSrcCreateTime() {
                return srcCreateTime;
            }

            public void setSrcCreateTime(Object srcCreateTime) {
                this.srcCreateTime = srcCreateTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getStreet() {
                return street;
            }

            public void setStreet(Object street) {
                this.street = street;
            }

            public int getSwId() {
                return swId;
            }

            public void setSwId(int swId) {
                this.swId = swId;
            }

            public Object getThirdOrderId() {
                return thirdOrderId;
            }

            public void setThirdOrderId(Object thirdOrderId) {
                this.thirdOrderId = thirdOrderId;
            }

            public Object getThirdOrderNo() {
                return thirdOrderNo;
            }

            public void setThirdOrderNo(Object thirdOrderNo) {
                this.thirdOrderNo = thirdOrderNo;
            }

            public Object getThirdShopId() {
                return thirdShopId;
            }

            public void setThirdShopId(Object thirdShopId) {
                this.thirdShopId = thirdShopId;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
