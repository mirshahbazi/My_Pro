package top.linjia.wizarposapp.beans;

import java.util.List;

public class SubmitOrderBean {

    /**
     * payMoney : 9
     * money : 0
     * coupons : [{"code":"a5410ff7-3159-41dc-9e57-875ccbf8d7f7","couponId":16,"createDate":"2016-12-22 16:57:19.000","expiryDate":"2017-01-20 23:59:59.000","hasSendYzd":false,"id":14095,"introduction":"1000元购物券： 满50种商品且满11800元可用","isEnabled":true,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":1000,"minimumQuantity":11800,"modifyDate":"2016-12-22 16:57:19.000","NAME":"1000元购物券","orderId":null,"point":1000,"prefix":"g","pushTicketId":null,"skuNum":50,"sno":null,"source":0,"type":2,"usedDate":null}]
     * linjiaMoney : 0
     * payBylinjiaMoney : 0
     * sendMoney : 9
     * cartItems : [{"cartId":24168,"cartNumber":12,"discPrice":96,"disDyq":true,"disGwq":false,"goodsId":437,"goodsNo":"6901035602204","moq":10,"number":15,"path":"upload/images/goods/1612211832248913.jpg","praise":0,"productionDate":"2016-08-16","unit":"箱"}]
     */

    private ResultBean result;
    /**
     * result : {"payMoney":9,"money":0,"coupons":[{"code":"a5410ff7-3159-41dc-9e57-875ccbf8d7f7","couponId":16,"createDate":"2016-12-22 16:57:19.000","expiryDate":"2017-01-20 23:59:59.000","hasSendYzd":false,"id":14095,"introduction":"1000元购物券： 满50种商品且满11800元可用","isEnabled":true,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":1000,"minimumQuantity":11800,"modifyDate":"2016-12-22 16:57:19.000","NAME":"1000元购物券","orderId":null,"point":1000,"prefix":"g","pushTicketId":null,"skuNum":50,"sno":null,"source":0,"type":2,"usedDate":null}],"linjiaMoney":0,"payBylinjiaMoney":0,"sendMoney":9,"cartItems":[{"cartId":24168,"cartNumber":12,"discPrice":96,"disDyq":true,"disGwq":false,"goodsId":437,"goodsNo":"6901035602204","moq":10,"number":15,"path":"upload/images/goods/1612211832248913.jpg","praise":0,"productionDate":"2016-08-16","unit":"箱"}]}
     * state : 1
     * desc :
     */

    private int state;
    private String desc;

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
        private double payMoney;
        private double money;
        private double linjiaMoney;
        private double payBylinjiaMoney;
        private double sendMoney;
        /**
         * code : a5410ff7-3159-41dc-9e57-875ccbf8d7f7
         * couponId : 16
         * createDate : 2016-12-22 16:57:19.000
         * expiryDate : 2017-01-20 23:59:59.000
         * hasSendYzd : false
         * id : 14095
         * introduction : 1000元购物券： 满50种商品且满11800元可用
         * isEnabled : true
         * isUsed : 0
         * lockDate : null
         * maximumPoint : null
         * maximumQuantity : null
         * memberId : 44
         * mid : null
         * minimumPoint : 1000
         * minimumQuantity : 11800
         * modifyDate : 2016-12-22 16:57:19.000
         * NAME : 1000元购物券
         * orderId : null
         * point : 1000
         * prefix : g
         * pushTicketId : null
         * skuNum : 50
         * sno : null
         * source : 0
         * type : 2
         * usedDate : null
         */

        private List<CouponsBean> coupons;
        /**
         * cartId : 24168
         * cartNumber : 12
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

        private List<CartItemsBean> cartItems;

        public double getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(double payMoney) {
            this.payMoney = payMoney;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public double getLinjiaMoney() {
            return linjiaMoney;
        }

        public void setLinjiaMoney(double linjiaMoney) {
            this.linjiaMoney = linjiaMoney;
        }

        public double getPayBylinjiaMoney() {
            return payBylinjiaMoney;
        }

        public void setPayBylinjiaMoney(double payBylinjiaMoney) {
            this.payBylinjiaMoney = payBylinjiaMoney;
        }

        public double getSendMoney() {
            return sendMoney;
        }

        public void setSendMoney(int sendMoney) {
            this.sendMoney = sendMoney;
        }

        public List<CouponsBean> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<CouponsBean> coupons) {
            this.coupons = coupons;
        }

        public List<CartItemsBean> getCartItems() {
            return cartItems;
        }

        public void setCartItems(List<CartItemsBean> cartItems) {
            this.cartItems = cartItems;
        }

        public static class CouponsBean {
            private String code;
            private int couponId;
            private String createDate;
            private String expiryDate;
            private boolean hasSendYzd;
            private int id;
            private String introduction;
            private boolean isEnabled;
            private int isUsed;
            private String lockDate; //
            private int maximumPoint;   //
            private int maximumQuantity;
            private int memberId;
            private int mid;
            private int minimumPoint;
            private int minimumQuantity;
            private String modifyDate;
            private String NAME;
            private int orderId;  //
            private int point;
            private String prefix;
            private String pushTicketId;
            private int skuNum;
            private String sno;
            private int source;
            private int type;
            private String usedDate;  //

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getCouponId() {
                return couponId;
            }

            public void setCouponId(int couponId) {
                this.couponId = couponId;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getExpiryDate() {
                return expiryDate;
            }

            public void setExpiryDate(String expiryDate) {
                this.expiryDate = expiryDate;
            }

            public boolean isHasSendYzd() {
                return hasSendYzd;
            }

            public void setHasSendYzd(boolean hasSendYzd) {
                this.hasSendYzd = hasSendYzd;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public boolean isIsEnabled() {
                return isEnabled;
            }

            public void setIsEnabled(boolean isEnabled) {
                this.isEnabled = isEnabled;
            }

            public int getIsUsed() {
                return isUsed;
            }

            public void setIsUsed(int isUsed) {
                this.isUsed = isUsed;
            }

            public String getLockDate() {
                return lockDate;
            }

            public void setLockDate(String lockDate) {
                this.lockDate = lockDate;
            }

            public int getMaximumPoint() {
                return maximumPoint;
            }

            public void setMaximumPoint(int maximumPoint) {
                this.maximumPoint = maximumPoint;
            }

            public int getMaximumQuantity() {
                return maximumQuantity;
            }

            public void setMaximumQuantity(int maximumQuantity) {
                this.maximumQuantity = maximumQuantity;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getMid() {
                return mid;
            }

            public void setMid(int mid) {
                this.mid = mid;
            }

            public int getMinimumPoint() {
                return minimumPoint;
            }

            public void setMinimumPoint(int minimumPoint) {
                this.minimumPoint = minimumPoint;
            }

            public int getMinimumQuantity() {
                return minimumQuantity;
            }

            public void setMinimumQuantity(int minimumQuantity) {
                this.minimumQuantity = minimumQuantity;
            }

            public String getModifyDate() {
                return modifyDate;
            }

            public void setModifyDate(String modifyDate) {
                this.modifyDate = modifyDate;
            }

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public String getPrefix() {
                return prefix;
            }

            public void setPrefix(String prefix) {
                this.prefix = prefix;
            }

            public String getPushTicketId() {
                return pushTicketId;
            }

            public void setPushTicketId(String pushTicketId) {
                this.pushTicketId = pushTicketId;
            }

            public int getSkuNum() {
                return skuNum;
            }

            public void setSkuNum(int skuNum) {
                this.skuNum = skuNum;
            }

            public String getSno() {
                return sno;
            }

            public void setSno(String sno) {
                this.sno = sno;
            }

            public int getSource() {
                return source;
            }

            public void setSource(int source) {
                this.source = source;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUsedDate() {
                return usedDate;
            }

            public void setUsedDate(String usedDate) {
                this.usedDate = usedDate;
            }
        }

        public static class CartItemsBean {
            private int cartId;
            private int cartNumber;
            private double discPrice;
            private boolean disDyq;
            private boolean disGwq;
            private int goodsId;
            private String goodsNo;
            private int moq;
            private int number;
            private String path;
            private int praise;
            private String productionDate;
            private String unit;
            private String name;
            private String stname;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStname() {
                return stname;
            }

            public void setStname(String stname) {
                this.stname = stname;
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

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsNo() {
                return goodsNo;
            }

            public void setGoodsNo(String goodsNo) {
                this.goodsNo = goodsNo;
            }

            public int getMoq() {
                return moq;
            }

            public void setMoq(int moq) {
                this.moq = moq;
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
        }
    }
}
