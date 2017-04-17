package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * Created by 邻家新锐 on 2016/11/24 16:59
 * 作者：李鹏鹏
 * 邮箱：ppbear_ly@163.com
 */
public class CouponBean {

    /**
     * list : [{"code":"ab66767a-cdcc-4ca0-b36c-42dba6e8aa61","couponId":5,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":995,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"5元购物券","orderId":null,"point":5,"prefix":"g","usedDate":null},{"code":"d3eb824c-3607-47ef-8ad3-6c1ebc1c6a40","couponId":5,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":996,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"5元购物券","orderId":null,"point":5,"prefix":"g","usedDate":null},{"code":"944878b7-c009-4ab9-baeb-489fa24c165d","couponId":5,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":997,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"5元购物券","orderId":null,"point":5,"prefix":"g","usedDate":null},{"code":"dfe6cf6c-dd6a-40cd-a004-62e98883da4b","couponId":6,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":998,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"10元购物券","orderId":null,"point":10,"prefix":"g","usedDate":null},{"code":"c3dac12c-432b-4629-a80e-e9a72ee185d2","couponId":6,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":999,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"10元购物券","orderId":null,"point":10,"prefix":"g","usedDate":null},{"code":"c493c660-2a73-4898-ae64-9b38d1e3c4a2","couponId":6,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":1000,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"10元购物券","orderId":null,"point":10,"prefix":"g","usedDate":null},{"code":"86d7b672-84ae-4724-a5cf-a5bb38840ec1","couponId":6,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":1001,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"10元购物券","orderId":null,"point":10,"prefix":"g","usedDate":null},{"code":"f35c25de-247d-4753-9954-9fc81be253fa","couponId":6,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":1002,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"10元购物券","orderId":null,"point":10,"prefix":"g","usedDate":null},{"code":"4aaa4059-3707-41c0-83b9-9aeecca799c7","couponId":7,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":1003,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"20元购物券","orderId":null,"point":20,"prefix":"g","usedDate":null},{"code":"eb795484-02f8-4b64-a196-51fcb0591070","couponId":7,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":1004,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"20元购物券","orderId":null,"point":20,"prefix":"g","usedDate":null}]
     * pageNumber : 1
     * pageSize : 10
     * totalPage : 2
     * totalRow : 14
     */

    private ResultBean result;
    /**
     * result : {"list":[{"code":"ab66767a-cdcc-4ca0-b36c-42dba6e8aa61","couponId":5,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":995,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"5元购物券","orderId":null,"point":5,"prefix":"g","usedDate":null},{"code":"d3eb824c-3607-47ef-8ad3-6c1ebc1c6a40","couponId":5,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":996,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"5元购物券","orderId":null,"point":5,"prefix":"g","usedDate":null},{"code":"944878b7-c009-4ab9-baeb-489fa24c165d","couponId":5,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":997,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"5元购物券","orderId":null,"point":5,"prefix":"g","usedDate":null},{"code":"dfe6cf6c-dd6a-40cd-a004-62e98883da4b","couponId":6,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":998,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"10元购物券","orderId":null,"point":10,"prefix":"g","usedDate":null},{"code":"c3dac12c-432b-4629-a80e-e9a72ee185d2","couponId":6,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":999,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"10元购物券","orderId":null,"point":10,"prefix":"g","usedDate":null},{"code":"c493c660-2a73-4898-ae64-9b38d1e3c4a2","couponId":6,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":1000,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"10元购物券","orderId":null,"point":10,"prefix":"g","usedDate":null},{"code":"86d7b672-84ae-4724-a5cf-a5bb38840ec1","couponId":6,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":1001,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"10元购物券","orderId":null,"point":10,"prefix":"g","usedDate":null},{"code":"f35c25de-247d-4753-9954-9fc81be253fa","couponId":6,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":1002,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"10元购物券","orderId":null,"point":10,"prefix":"g","usedDate":null},{"code":"4aaa4059-3707-41c0-83b9-9aeecca799c7","couponId":7,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":1003,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"20元购物券","orderId":null,"point":20,"prefix":"g","usedDate":null},{"code":"eb795484-02f8-4b64-a196-51fcb0591070","couponId":7,"createDate":"2016-11-24 15:41:34.000","expiryDate":"2016-12-23 23:59:59.000","id":1004,"isUsed":0,"lockDate":null,"memberId":1252,"mid":null,"modifyDate":"2016-11-24 15:41:34.000","NAME":"20元购物券","orderId":null,"point":20,"prefix":"g","usedDate":null}],"pageNumber":1,"pageSize":10,"totalPage":2,"totalRow":14}
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
        private int pageNumber;
        private int pageSize;
        private int totalPage;
        private int totalRow;
        /**
         * code : ab66767a-cdcc-4ca0-b36c-42dba6e8aa61
         * couponId : 5
         * createDate : 2016-11-24 15:41:34.000
         * expiryDate : 2016-12-23 23:59:59.000
         * id : 995
         * isUsed : 0
         * lockDate : null
         * memberId : 1252
         * mid : null
         * modifyDate : 2016-11-24 15:41:34.000
         * NAME : 5元购物券
         * orderId : null
         * point : 5
         * prefix : g
         * usedDate : null
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

        public static class ListBean {
            private String code;
            private int couponId;
            private String createDate;
            private String expiryDate;
            private int id;
            private int isUsed;
            private String lockDate;
            private int memberId;
            private String mid;
            private String modifyDate;
            private String NAME;
            private String orderId;
            private int point;
            private String prefix;
            private String usedDate;

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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
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

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
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

            public String getUsedDate() {
                return usedDate;
            }

            public void setUsedDate(String usedDate) {
                this.usedDate = usedDate;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "code='" + code + '\'' +
                        ", couponId=" + couponId +
                        ", createDate='" + createDate + '\'' +
                        ", expiryDate='" + expiryDate + '\'' +
                        ", id=" + id +
                        ", isUsed=" + isUsed +
                        ", lockDate='" + lockDate + '\'' +
                        ", memberId=" + memberId +
                        ", mid='" + mid + '\'' +
                        ", modifyDate='" + modifyDate + '\'' +
                        ", NAME='" + NAME + '\'' +
                        ", orderId='" + orderId + '\'' +
                        ", point=" + point +
                        ", prefix='" + prefix + '\'' +
                        ", usedDate='" + usedDate + '\'' +
                        '}';
            }
        }
    }
}
