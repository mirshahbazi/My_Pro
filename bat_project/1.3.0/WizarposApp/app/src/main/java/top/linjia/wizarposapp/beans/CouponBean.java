package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * @ClassName: CouponBean
 * @Description: 卡券列表实体类。
 * @Author: 李鹏鹏
 * @Data: 2016/12/19
 * @Copyright: 河南巧脉信息技术有限公司
 */
public class CouponBean {


    /**
     * list : [{"code":"46ccda5d-4b39-460f-b0cd-e7bec1d4bf6e","couponId":11,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1012,"introduction":"50元购物券： 满10种商品且满1100元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":50,"minimumQuantity":1100,"modifyDate":"2016-11-30 09:53:52.000","NAME":"50元购物券","orderId":null,"point":50,"prefix":"g","pushTicketId":null,"skuNum":10,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"448e275d-b476-400b-8308-fcb642ff51ec","couponId":12,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1013,"introduction":"100元购物券： 满15种商品且满2000元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":100,"minimumQuantity":2000,"modifyDate":"2016-11-30 09:53:52.000","NAME":"100元购物券","orderId":null,"point":100,"prefix":"g","pushTicketId":null,"skuNum":15,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"f5aebf2b-53af-4527-bc12-9d912bc12276","couponId":12,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1014,"introduction":"100元购物券： 满15种商品且满2000元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":100,"minimumQuantity":2000,"modifyDate":"2016-11-30 09:53:52.000","NAME":"100元购物券","orderId":null,"point":100,"prefix":"g","pushTicketId":null,"skuNum":15,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"9a1f4191-e4ce-48d8-b744-552b34f637d5","couponId":13,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1015,"introduction":"200元购物券： 满20种商品且满3600元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":200,"minimumQuantity":3600,"modifyDate":"2016-11-30 09:53:52.000","NAME":"200元购物券","orderId":null,"point":200,"prefix":"g","pushTicketId":null,"skuNum":20,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"e8d4839d-6363-4504-b5c8-2d8ad933609b","couponId":14,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1017,"introduction":"300元购物券： 满30种商品且满5000元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":300,"minimumQuantity":5000,"modifyDate":"2016-11-30 09:53:52.000","NAME":"300元购物券","orderId":null,"point":300,"prefix":"g","pushTicketId":null,"skuNum":30,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"ca7a7a5b-1000-4753-9a87-ee21a2b49f95","couponId":14,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1018,"introduction":"300元购物券： 满30种商品且满5000元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":300,"minimumQuantity":5000,"modifyDate":"2016-11-30 09:53:52.000","NAME":"300元购物券","orderId":null,"point":300,"prefix":"g","pushTicketId":null,"skuNum":30,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"aea8113d-632f-4f8f-8da7-6a0a4ff9e778","couponId":15,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1019,"introduction":"500元购物券：满50种商品且满7200元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":500,"minimumQuantity":7200,"modifyDate":"2016-11-30 09:53:52.000","NAME":"500元购物券","orderId":null,"point":500,"prefix":"g","pushTicketId":null,"skuNum":50,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"443937e9-c025-448a-8ef1-a82432e272b8","couponId":15,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1020,"introduction":"500元购物券：满50种商品且满7200元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":500,"minimumQuantity":7200,"modifyDate":"2016-11-30 09:53:52.000","NAME":"500元购物券","orderId":null,"point":500,"prefix":"g","pushTicketId":null,"skuNum":50,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"1aa6df5e-78ba-4666-bdd7-150d6fdc91a9","couponId":16,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1021,"introduction":"1000元购物券： 满80种商品且满11800元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":1000,"minimumQuantity":11800,"modifyDate":"2016-11-30 09:53:52.000","NAME":"1000元购物券","orderId":null,"point":1000,"prefix":"g","pushTicketId":null,"skuNum":80,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"83ddb7a0-3224-4551-b24d-74339a9b2cf5","couponId":16,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1022,"introduction":"1000元购物券： 满80种商品且满11800元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":1000,"minimumQuantity":11800,"modifyDate":"2016-11-30 09:53:52.000","NAME":"1000元购物券","orderId":null,"point":1000,"prefix":"g","pushTicketId":null,"skuNum":80,"sno":null,"source":0,"type":2,"usedDate":null}]
     * pageNumber : 1
     * pageSize : 10
     * totalPage : 2
     * totalRow : 16
     */

    private ResultBean result;
    /**
     * result : {"list":[{"code":"46ccda5d-4b39-460f-b0cd-e7bec1d4bf6e","couponId":11,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1012,"introduction":"50元购物券： 满10种商品且满1100元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":50,"minimumQuantity":1100,"modifyDate":"2016-11-30 09:53:52.000","NAME":"50元购物券","orderId":null,"point":50,"prefix":"g","pushTicketId":null,"skuNum":10,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"448e275d-b476-400b-8308-fcb642ff51ec","couponId":12,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1013,"introduction":"100元购物券： 满15种商品且满2000元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":100,"minimumQuantity":2000,"modifyDate":"2016-11-30 09:53:52.000","NAME":"100元购物券","orderId":null,"point":100,"prefix":"g","pushTicketId":null,"skuNum":15,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"f5aebf2b-53af-4527-bc12-9d912bc12276","couponId":12,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1014,"introduction":"100元购物券： 满15种商品且满2000元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":100,"minimumQuantity":2000,"modifyDate":"2016-11-30 09:53:52.000","NAME":"100元购物券","orderId":null,"point":100,"prefix":"g","pushTicketId":null,"skuNum":15,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"9a1f4191-e4ce-48d8-b744-552b34f637d5","couponId":13,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1015,"introduction":"200元购物券： 满20种商品且满3600元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":200,"minimumQuantity":3600,"modifyDate":"2016-11-30 09:53:52.000","NAME":"200元购物券","orderId":null,"point":200,"prefix":"g","pushTicketId":null,"skuNum":20,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"e8d4839d-6363-4504-b5c8-2d8ad933609b","couponId":14,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1017,"introduction":"300元购物券： 满30种商品且满5000元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":300,"minimumQuantity":5000,"modifyDate":"2016-11-30 09:53:52.000","NAME":"300元购物券","orderId":null,"point":300,"prefix":"g","pushTicketId":null,"skuNum":30,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"ca7a7a5b-1000-4753-9a87-ee21a2b49f95","couponId":14,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1018,"introduction":"300元购物券： 满30种商品且满5000元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":300,"minimumQuantity":5000,"modifyDate":"2016-11-30 09:53:52.000","NAME":"300元购物券","orderId":null,"point":300,"prefix":"g","pushTicketId":null,"skuNum":30,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"aea8113d-632f-4f8f-8da7-6a0a4ff9e778","couponId":15,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1019,"introduction":"500元购物券：满50种商品且满7200元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":500,"minimumQuantity":7200,"modifyDate":"2016-11-30 09:53:52.000","NAME":"500元购物券","orderId":null,"point":500,"prefix":"g","pushTicketId":null,"skuNum":50,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"443937e9-c025-448a-8ef1-a82432e272b8","couponId":15,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1020,"introduction":"500元购物券：满50种商品且满7200元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":500,"minimumQuantity":7200,"modifyDate":"2016-11-30 09:53:52.000","NAME":"500元购物券","orderId":null,"point":500,"prefix":"g","pushTicketId":null,"skuNum":50,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"1aa6df5e-78ba-4666-bdd7-150d6fdc91a9","couponId":16,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1021,"introduction":"1000元购物券： 满80种商品且满11800元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":1000,"minimumQuantity":11800,"modifyDate":"2016-11-30 09:53:52.000","NAME":"1000元购物券","orderId":null,"point":1000,"prefix":"g","pushTicketId":null,"skuNum":80,"sno":null,"source":0,"type":2,"usedDate":null},{"code":"83ddb7a0-3224-4551-b24d-74339a9b2cf5","couponId":16,"createDate":"2016-11-30 09:53:52.000","expiryDate":"2016-12-29 23:59:59.000","hasSendYzd":false,"id":1022,"introduction":"1000元购物券： 满80种商品且满11800元可用","isEnabled":false,"isUsed":0,"lockDate":null,"maximumPoint":null,"maximumQuantity":null,"memberId":44,"mid":null,"minimumPoint":1000,"minimumQuantity":11800,"modifyDate":"2016-11-30 09:53:52.000","NAME":"1000元购物券","orderId":null,"point":1000,"prefix":"g","pushTicketId":null,"skuNum":80,"sno":null,"source":0,"type":2,"usedDate":null}],"pageNumber":1,"pageSize":10,"totalPage":2,"totalRow":16}
     * state : 1
     */

    private int state;

    @Override
    public String toString() {
        return "CouponBean{" +
                "result=" + result +
                ", state=" + state +
                '}';
    }

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
        private int pageNumber;
        private int pageSize;
        private int totalPage;
        private int totalRow;
        /**
         * code : 46ccda5d-4b39-460f-b0cd-e7bec1d4bf6e
         * couponId : 11
         * createDate : 2016-11-30 09:53:52.000
         * expiryDate : 2016-12-29 23:59:59.000
         * hasSendYzd : false
         * id : 1012
         * introduction : 50元购物券： 满10种商品且满1100元可用
         * isEnabled : false
         * isUsed : 0
         * lockDate : null
         * maximumPoint : null
         * maximumQuantity : null
         * memberId : 44
         * mid : null
         * minimumPoint : 50
         * minimumQuantity : 1100
         * modifyDate : 2016-11-30 09:53:52.000
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

        private List<ListBean> list;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "pageNumber=" + pageNumber +
                    ", pageSize=" + pageSize +
                    ", totalPage=" + totalPage +
                    ", totalRow=" + totalRow +
                    ", list=" + list +
                    '}';
        }

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

        public static class ListBean implements Comparable<ListBean>{
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
            public boolean sign;
            public boolean nextUsable = true;

            @Override
            public String toString() {
                return "ListBean{" +
                        "minimumQuantity=" + minimumQuantity +
                        ", skuNum=" + skuNum +
                        ", point=" + point +
                        ", prefix='" + prefix + '\'' +
                        '}';
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

            @Override
            public int compareTo(ListBean o) {
                return this.point - o.point;
            }
        }
    }
}
