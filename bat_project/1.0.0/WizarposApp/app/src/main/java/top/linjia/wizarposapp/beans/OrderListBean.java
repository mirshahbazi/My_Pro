package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * Created by 邻家新锐 on 2016/10/26 11:26
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class OrderListBean {

    @Override
    public String toString() {
        return "OrderListBean{" +
                "result=" + result +
                ", state=" + state +
                '}';
    }

    /**
     * list : [{"createTime":"2016-08-29 02:10:20.000","money":0,"orderId":11,"orderNo":"2016082900201","status":2},{"createTime":"2016-08-29 02:14:39.000","money":0,"orderId":12,"orderNo":"2016082900202","status":2},{"createTime":"2016-08-29 16:49:26.000","money":0,"orderId":13,"orderNo":"2016082900203","status":2},{"createTime":"2016-08-31 09:17:54.000","money":230,"orderId":14,"orderNo":"2016083100206","status":2},{"createTime":"2016-08-31 09:50:28.000","money":0,"orderId":15,"orderNo":"2016083100205","status":2},{"createTime":"2016-08-31 11:13:43.000","money":0,"orderId":16,"orderNo":"2016083100206","status":2},{"createTime":"2016-08-31 11:14:14.000","money":0,"orderId":17,"orderNo":"2016083100207","status":2},{"createTime":"2016-08-31 11:17:17.000","money":0,"orderId":18,"orderNo":"2016083100208","status":2},{"createTime":"2016-08-31 11:36:56.000","money":0,"orderId":19,"orderNo":"2016083100209","status":2},{"createTime":"2016-09-01 09:32:17.000","money":0,"orderId":20,"orderNo":"2016090100210","status":9}]
     * pageNumber : 1
     * pageSize : 10
     */

    private ResultBean result;
    /**
     * result : {"list":[{"createTime":"2016-08-29 02:10:20.000","money":0,"orderId":11,"orderNo":"2016082900201","status":2},{"createTime":"2016-08-29 02:14:39.000","money":0,"orderId":12,"orderNo":"2016082900202","status":2},{"createTime":"2016-08-29 16:49:26.000","money":0,"orderId":13,"orderNo":"2016082900203","status":2},{"createTime":"2016-08-31 09:17:54.000","money":230,"orderId":14,"orderNo":"2016083100206","status":2},{"createTime":"2016-08-31 09:50:28.000","money":0,"orderId":15,"orderNo":"2016083100205","status":2},{"createTime":"2016-08-31 11:13:43.000","money":0,"orderId":16,"orderNo":"2016083100206","status":2},{"createTime":"2016-08-31 11:14:14.000","money":0,"orderId":17,"orderNo":"2016083100207","status":2},{"createTime":"2016-08-31 11:17:17.000","money":0,"orderId":18,"orderNo":"2016083100208","status":2},{"createTime":"2016-08-31 11:36:56.000","money":0,"orderId":19,"orderNo":"2016083100209","status":2},{"createTime":"2016-09-01 09:32:17.000","money":0,"orderId":20,"orderNo":"2016090100210","status":9}],"pageNumber":1,"pageSize":10}
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
        /**
         * createTime : 2016-08-29 02:10:20.000
         * money : 0
         * orderId : 11
         * orderNo : 2016082900201
         * status : 2
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String createTime;
            private double money;
            private int orderId;
            private String orderNo;
            private int status;

            @Override
            public String toString() {
                return "ListBean{" +
                        "createTime='" + createTime + '\'' +
                        ", money=" + money +
                        ", orderId=" + orderId +
                        ", orderNo='" + orderNo + '\'' +
                        ", status=" + status +
                        '}';
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
