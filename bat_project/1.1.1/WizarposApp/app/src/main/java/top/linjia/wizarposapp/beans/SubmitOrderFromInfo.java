package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * Created by 邻家新锐 on 2016/10/27 11:40
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 *
 * 提交订单的实体类
 */
public class SubmitOrderFromInfo {


    private String appToken;
    /**
     * uname :  店铺名称
     * recer :  收货人
     * phone :  电话号码
     * address :  地址
     * details : [{"goodsId":12,"number":2},{"goodsId":13,"number":2},{"goodsId":14,"number":2}]
     */

    private OrderBean order;

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class OrderBean {
        private String uname;
        private String recer;
        private String phone;
        private String address;

        public OrderBean(String uname, String recer, String phone, String address, List<DetailsBean> details) {
            this.uname = uname;
            this.recer = recer;
            this.phone = phone;
            this.address = address;
            this.details = details;
        }

        /**
         * goodsId : 12
         * number : 2
         */

        private List<DetailsBean> details;

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getRecer() {
            return recer;
        }

        public void setRecer(String recer) {
            this.recer = recer;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
        }

        public static class DetailsBean {
            private int goodsId;
            private int number;

            public DetailsBean(int goodsId, int number) {
                this.goodsId = goodsId;
                this.number = number;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }
        }
    }

    public SubmitOrderFromInfo(String appToken, OrderBean order) {
        this.appToken = appToken;
        this.order = order;
    }
}
