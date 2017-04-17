package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * @ClassName: SubmitActCouCoinBean
 * @Description: 未支付订单跳转支付页面是请求的卡券信息及邻家币信息实体类
 * @Data: 2017/1/6 10:54
 * @Author: 李鹏鹏
 */
public class SubmitActCouCoinBean {
    /**
     * payMoney : 976.6
     * money : 1466.6
     * coupons : [{"code":"a329b8f7-3bf5-475b-849e-06f9faffa7bc","couponId":11,"createDate":"2017-01-08 15:16:50.000","expiryDate":"2017-02-06 23:59:59.000","hasSendYzd":false,"id":14131,"introduction":"50元购物券： 满10种商品且满1100元可用","isEnabled":true,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":1238,"mid":null,"minimumPoint":50,"minimumQuantity":1100,"modifyDate":"2017-01-08 15:16:50.000","NAME":"50元购物券","orderId":null,"point":50,"prefix":"g","pushTicketId":null,"skuNum":10,"sno":null,"source":0,"type":2,"usedDate":null}]
     * linjiaMoney : 440
     * payBylinjiaMoney : 440
     * sendMoney : 0
     */

    private ResultBean result;
    /**
     * result : {"payMoney":976.6,"money":1466.6,"coupons":[{"code":"a329b8f7-3bf5-475b-849e-06f9faffa7bc","couponId":11,"createDate":"2017-01-08 15:16:50.000","expiryDate":"2017-02-06 23:59:59.000","hasSendYzd":false,"id":14131,"introduction":"50元购物券： 满10种商品且满1100元可用","isEnabled":true,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":1238,"mid":null,"minimumPoint":50,"minimumQuantity":1100,"modifyDate":"2017-01-08 15:16:50.000","NAME":"50元购物券","orderId":null,"point":50,"prefix":"g","pushTicketId":null,"skuNum":10,"sno":null,"source":0,"type":2,"usedDate":null}],"linjiaMoney":440,"payBylinjiaMoney":440,"sendMoney":0}
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
        private double payMoney;
        private double money;
        private double linjiaMoney;
        private double payBylinjiaMoney;
        private double sendMoney;
        /**
         * code : a329b8f7-3bf5-475b-849e-06f9faffa7bc
         * couponId : 11
         * createDate : 2017-01-08 15:16:50.000
         * expiryDate : 2017-02-06 23:59:59.000
         * hasSendYzd : false
         * id : 14131
         * introduction : 50元购物券： 满10种商品且满1100元可用
         * isEnabled : true
         * isUsed : 0
         * lockDate : null
         * maximumPoint : null
         * maximumQuantity : null
         * memberId : 1238
         * mid : null
         * minimumPoint : 50
         * minimumQuantity : 1100
         * modifyDate : 2017-01-08 15:16:50.000
         * NAME : 50元购物券
         * orderId : null
         * point : 50
         * prefix : g
         * pushTicketId : null
         * skuNum : 10
         * sno : null
         * source : 0
         * type : 2
         * usedDate : null
         */

        private List<CouponsBean> coupons;

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

        public void setSendMoney(double sendMoney) {
            this.sendMoney = sendMoney;
        }

        public List<CouponsBean> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<CouponsBean> coupons) {
            this.coupons = coupons;
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
            private Object lockDate;
            private Object maximumPoint;
            private Object maximumQuantity;
            private int memberId;
            private Object mid;
            private int minimumPoint;
            private int minimumQuantity;
            private String modifyDate;
            private String NAME;
            private Object orderId;
            private int point;
            private String prefix;
            private Object pushTicketId;
            private int skuNum;
            private Object sno;
            private int source;
            private int type;
            private Object usedDate;

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

            public Object getLockDate() {
                return lockDate;
            }

            public void setLockDate(Object lockDate) {
                this.lockDate = lockDate;
            }

            public Object getMaximumPoint() {
                return maximumPoint;
            }

            public void setMaximumPoint(Object maximumPoint) {
                this.maximumPoint = maximumPoint;
            }

            public Object getMaximumQuantity() {
                return maximumQuantity;
            }

            public void setMaximumQuantity(Object maximumQuantity) {
                this.maximumQuantity = maximumQuantity;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public Object getMid() {
                return mid;
            }

            public void setMid(Object mid) {
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

            public Object getOrderId() {
                return orderId;
            }

            public void setOrderId(Object orderId) {
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

            public Object getPushTicketId() {
                return pushTicketId;
            }

            public void setPushTicketId(Object pushTicketId) {
                this.pushTicketId = pushTicketId;
            }

            public int getSkuNum() {
                return skuNum;
            }

            public void setSkuNum(int skuNum) {
                this.skuNum = skuNum;
            }

            public Object getSno() {
                return sno;
            }

            public void setSno(Object sno) {
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

            public Object getUsedDate() {
                return usedDate;
            }

            public void setUsedDate(Object usedDate) {
                this.usedDate = usedDate;
            }
        }
    }
}
