package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * @ClassName: CouponShowBean
 * @Description: 卡券展示页面网络数据实体类
 * @Data: 2017/1/8 15:09
 * @Author: 李鹏鹏
 */
public class CouponShowBean {

    private ResultBean result;
    /**
     * result : {"canNotUsedList":[{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"1000元购物券","code":"e1eb4f69-ae9f-4306-9d7c-6636b92099f6","couponId":16,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14113,"introduction":"1000元购物券： 满50种商品且满11800元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":1000,"minimumQuantity":11800,"modifyDate":"2017-01-06 16:22:19.000","point":1000,"prefix":"g","skuNum":50,"source":0,"type":2},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"500元购物券","code":"79b628ae-a1e8-4237-9edf-3e5fa5226520","couponId":15,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14112,"introduction":"500元购物券：满40种商品且满7200元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":500,"minimumQuantity":7200,"modifyDate":"2017-01-06 16:22:19.000","point":500,"prefix":"g","skuNum":40,"source":0,"type":2},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"300元购物券","code":"7336920c-8a10-465f-bdf3-a2dff89ccfb2","couponId":14,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14111,"introduction":"300元购物券： 满30种商品且满5000元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":300,"minimumQuantity":5000,"modifyDate":"2017-01-06 16:22:19.000","point":300,"prefix":"g","skuNum":30,"source":0,"type":2}],"canUsedList":[{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"200元购物券","code":"af9ea544-a30a-4cea-9eb0-dcf949541cf5","couponId":13,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14110,"introduction":"200元购物券： 满20种商品且满3600元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":200,"minimumQuantity":3600,"modifyDate":"2017-01-06 16:22:19.000","point":200,"prefix":"g","skuNum":20,"source":0,"type":2},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"100元购物券","code":"2ea62069-ccdc-4638-b296-2e7a1cf5f7ef","couponId":12,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14109,"introduction":"100元购物券： 满15种商品且满2000元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":100,"minimumQuantity":2000,"modifyDate":"2017-01-06 16:22:19.000","point":100,"prefix":"g","skuNum":15,"source":0,"type":2},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"100元抵用券","code":"7309e362-d3e9-11e6-b084-5254007a2c6f","couponId":2,"createDate":"2017-01-06 16:23:49.000","expiryDate":"2018-01-28 23:59:59.000","hasSendYzd":false,"id":14114,"introduction":"100元抵用券：订单满299元可以使用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":100,"minimumQuantity":299,"modifyDate":"2017-01-06 16:23:49.000","point":100,"prefix":"g","skuNum":1,"source":0,"type":1},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"100元抵用券","code":"74c4596d-d3e9-11e6-b084-5254007a2c6f","couponId":2,"createDate":"2017-01-06 16:23:52.000","expiryDate":"2018-01-28 23:59:59.000","hasSendYzd":false,"id":14115,"introduction":"100元抵用券：订单满299元可以使用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":100,"minimumQuantity":299,"modifyDate":"2017-01-06 16:23:52.000","point":100,"prefix":"g","skuNum":1,"source":0,"type":1},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"50元购物券","code":"c989fba4-a170-424b-9825-57b3ea6a0e79","couponId":11,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14107,"introduction":"50元购物券： 满10种商品且满1100元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":50,"minimumQuantity":1100,"modifyDate":"2017-01-06 16:22:19.000","point":50,"prefix":"g","skuNum":10,"source":0,"type":2},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"50元购物券","code":"5cdc0c71-8ea7-4ede-9873-8162aebbdf7a","couponId":11,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14108,"introduction":"50元购物券： 满10种商品且满1100元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":50,"minimumQuantity":1100,"modifyDate":"2017-01-06 16:22:19.000","point":50,"prefix":"g","skuNum":10,"source":0,"type":2},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"20元购物券","code":"40123d09-b889-46b8-83dd-021da47cf22f","couponId":10,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14105,"introduction":"20元购物券： 满1种商品且满500元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":20,"minimumQuantity":500,"modifyDate":"2017-01-06 16:22:19.000","point":20,"prefix":"g","skuNum":0,"source":0,"type":2},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"20元购物券","code":"3c49ed9b-fc46-4606-b021-2512c3da3fa0","couponId":10,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14106,"introduction":"20元购物券： 满1种商品且满500元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":20,"minimumQuantity":500,"modifyDate":"2017-01-06 16:22:19.000","point":20,"prefix":"g","skuNum":0,"source":0,"type":2}],"expireList":[{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"1000元购物券","code":"e1eb4f69-ae9f-4306-9d7c-6636b92099f6","couponId":16,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14113,"introduction":"1000元购物券： 满50种商品且满11800元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":1000,"minimumQuantity":11800,"modifyDate":"2017-01-06 16:22:19.000","point":1000,"prefix":"g","skuNum":50,"source":0,"type":2},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"500元购物券","code":"79b628ae-a1e8-4237-9edf-3e5fa5226520","couponId":15,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14112,"introduction":"500元购物券：满40种商品且满7200元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":500,"minimumQuantity":7200,"modifyDate":"2017-01-06 16:22:19.000","point":500,"prefix":"g","skuNum":40,"source":0,"type":2},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"300元购物券","code":"7336920c-8a10-465f-bdf3-a2dff89ccfb2","couponId":14,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14111,"introduction":"300元购物券： 满30种商品且满5000元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":300,"minimumQuantity":5000,"modifyDate":"2017-01-06 16:22:19.000","point":300,"prefix":"g","skuNum":30,"source":0,"type":2}]}
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
        /**
         * lockDate : 1
         * maximumPoint : 1
         * maximumQuantity : 1
         * mid : 1
         * orderId : 1
         * pushTicketId : 1
         * sno : 1
         * usedDate : 1
         * NAME : 1000元购物券
         * code : e1eb4f69-ae9f-4306-9d7c-6636b92099f6
         * couponId : 16
         * createDate : 2017-01-06 16:22:19.000
         * expiryDate : 2017-02-04 23:59:59.000
         * hasSendYzd : false
         * id : 14113
         * introduction : 1000元购物券： 满50种商品且满11800元可用
         * isEnabled : true
         * isUsed : 0
         * memberId : 1238
         * minimumPoint : 1000
         * minimumQuantity : 11800
         * modifyDate : 2017-01-06 16:22:19.000
         * point : 1000
         * prefix : g
         * skuNum : 50
         * source : 0
         * type : 2
         */

        private List<CanNotUsedListBean> canNotUsedList;
        /**
         * lockDate : 1
         * maximumPoint : 1
         * maximumQuantity : 1
         * mid : 1
         * orderId : 1
         * pushTicketId : 1
         * sno : 1
         * usedDate : 1
         * NAME : 200元购物券
         * code : af9ea544-a30a-4cea-9eb0-dcf949541cf5
         * couponId : 13
         * createDate : 2017-01-06 16:22:19.000
         * expiryDate : 2017-02-04 23:59:59.000
         * hasSendYzd : false
         * id : 14110
         * introduction : 200元购物券： 满20种商品且满3600元可用
         * isEnabled : true
         * isUsed : 0
         * memberId : 1238
         * minimumPoint : 200
         * minimumQuantity : 3600
         * modifyDate : 2017-01-06 16:22:19.000
         * point : 200
         * prefix : g
         * skuNum : 20
         * source : 0
         * type : 2
         */

        private List<CanUsedListBean> canUsedList;
        /**
         * lockDate : 1
         * maximumPoint : 1
         * maximumQuantity : 1
         * mid : 1
         * orderId : 1
         * pushTicketId : 1
         * sno : 1
         * usedDate : 1
         * NAME : 1000元购物券
         * code : e1eb4f69-ae9f-4306-9d7c-6636b92099f6
         * couponId : 16
         * createDate : 2017-01-06 16:22:19.000
         * expiryDate : 2017-02-04 23:59:59.000
         * hasSendYzd : false
         * id : 14113
         * introduction : 1000元购物券： 满50种商品且满11800元可用
         * isEnabled : true
         * isUsed : 0
         * memberId : 1238
         * minimumPoint : 1000
         * minimumQuantity : 11800
         * modifyDate : 2017-01-06 16:22:19.000
         * point : 1000
         * prefix : g
         * skuNum : 50
         * source : 0
         * type : 2
         */

        private List<ExpireListBean> expireList;

        public List<CanNotUsedListBean> getCanNotUsedList() {
            return canNotUsedList;
        }

        public void setCanNotUsedList(List<CanNotUsedListBean> canNotUsedList) {
            this.canNotUsedList = canNotUsedList;
        }

        public List<CanUsedListBean> getCanUsedList() {
            return canUsedList;
        }

        public void setCanUsedList(List<CanUsedListBean> canUsedList) {
            this.canUsedList = canUsedList;
        }

        public List<ExpireListBean> getExpireList() {
            return expireList;
        }

        public void setExpireList(List<ExpireListBean> expireList) {
            this.expireList = expireList;
        }

        public static class CanNotUsedListBean {
            private int lockDate;
            private int maximumPoint;
            private int maximumQuantity;
            private int mid;
            private int orderId;
            private int pushTicketId;
            private int sno;
            private int usedDate;
            private String NAME;
            private String code;
            private int couponId;
            private String createDate;
            private String expiryDate;
            private boolean hasSendYzd;
            private int id;
            private String introduction;
            private boolean isEnabled;
            private int isUsed;
            private int memberId;
            private int minimumPoint;
            private int minimumQuantity;
            private String modifyDate;
            private int point;
            private String prefix;
            private int skuNum;
            private int source;
            private int type;

            public int getLockDate() {
                return lockDate;
            }

            public void setLockDate(int lockDate) {
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

            public int getMid() {
                return mid;
            }

            public void setMid(int mid) {
                this.mid = mid;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getPushTicketId() {
                return pushTicketId;
            }

            public void setPushTicketId(int pushTicketId) {
                this.pushTicketId = pushTicketId;
            }

            public int getSno() {
                return sno;
            }

            public void setSno(int sno) {
                this.sno = sno;
            }

            public int getUsedDate() {
                return usedDate;
            }

            public void setUsedDate(int usedDate) {
                this.usedDate = usedDate;
            }

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

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

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
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

            public int getSkuNum() {
                return skuNum;
            }

            public void setSkuNum(int skuNum) {
                this.skuNum = skuNum;
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
        }

        public static class CanUsedListBean {
            private int lockDate;
            private int maximumPoint;
            private int maximumQuantity;
            private int mid;
            private int orderId;
            private int pushTicketId;
            private int sno;
            private int usedDate;
            private String NAME;
            private String code;
            private int couponId;
            private String createDate;
            private String expiryDate;
            private boolean hasSendYzd;
            private int id;
            private String introduction;
            private boolean isEnabled;
            private int isUsed;
            private int memberId;
            private int minimumPoint;
            private int minimumQuantity;
            private String modifyDate;
            private int point;
            private String prefix;
            private int skuNum;
            private int source;
            private int type;

            public int getLockDate() {
                return lockDate;
            }

            public void setLockDate(int lockDate) {
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

            public int getMid() {
                return mid;
            }

            public void setMid(int mid) {
                this.mid = mid;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getPushTicketId() {
                return pushTicketId;
            }

            public void setPushTicketId(int pushTicketId) {
                this.pushTicketId = pushTicketId;
            }

            public int getSno() {
                return sno;
            }

            public void setSno(int sno) {
                this.sno = sno;
            }

            public int getUsedDate() {
                return usedDate;
            }

            public void setUsedDate(int usedDate) {
                this.usedDate = usedDate;
            }

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

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

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
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

            public int getSkuNum() {
                return skuNum;
            }

            public void setSkuNum(int skuNum) {
                this.skuNum = skuNum;
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
        }

        public static class ExpireListBean {
            private int lockDate;
            private int maximumPoint;
            private int maximumQuantity;
            private int mid;
            private int orderId;
            private int pushTicketId;
            private int sno;
            private int usedDate;
            private String NAME;
            private String code;
            private int couponId;
            private String createDate;
            private String expiryDate;
            private boolean hasSendYzd;
            private int id;
            private String introduction;
            private boolean isEnabled;
            private int isUsed;
            private int memberId;
            private int minimumPoint;
            private int minimumQuantity;
            private String modifyDate;
            private int point;
            private String prefix;
            private int skuNum;
            private int source;
            private int type;

            public int getLockDate() {
                return lockDate;
            }

            public void setLockDate(int lockDate) {
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

            public int getMid() {
                return mid;
            }

            public void setMid(int mid) {
                this.mid = mid;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getPushTicketId() {
                return pushTicketId;
            }

            public void setPushTicketId(int pushTicketId) {
                this.pushTicketId = pushTicketId;
            }

            public int getSno() {
                return sno;
            }

            public void setSno(int sno) {
                this.sno = sno;
            }

            public int getUsedDate() {
                return usedDate;
            }

            public void setUsedDate(int usedDate) {
                this.usedDate = usedDate;
            }

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

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

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
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

            public int getSkuNum() {
                return skuNum;
            }

            public void setSkuNum(int skuNum) {
                this.skuNum = skuNum;
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
        }
    }
}
