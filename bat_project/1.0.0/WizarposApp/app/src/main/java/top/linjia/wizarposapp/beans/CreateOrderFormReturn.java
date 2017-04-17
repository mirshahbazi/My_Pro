package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * Created by 河南巧脉信息技术 on 2016/11/14 15:26
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 *
 * 创建订单返回的订单实体类
 */
public class CreateOrderFormReturn {

    private ResultBean result;
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
        private int coin;
        private String createTime;
        private int flag;
        private int memId;
        private double money;
        private int orderId;
        private String orderNo;
        private int payed;
        private int payId;
        private String phone;
        private int proxyMoney;
        private int realMoney;
        private String recer;
        private int sendMoney;
        private int src;
        private int status;
        private int swId;

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

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public int getSendMoney() {
            return sendMoney;
        }

        public void setSendMoney(int sendMoney) {
            this.sendMoney = sendMoney;
        }

        public int getSrc() {
            return src;
        }

        public void setSrc(int src) {
            this.src = src;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSwId() {
            return swId;
        }

        public void setSwId(int swId) {
            this.swId = swId;
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

            @Override
            public String toString() {
                return "DetailsBean{" +
                        "detailId=" + detailId +
                        ", goodsName='" + goodsName + '\'' +
                        ", goodsNo='" + goodsNo + '\'' +
                        ", number=" + number +
                        ", orderId=" + orderId +
                        ", price=" + price +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "address='" + address + '\'' +
                    ", buyer='" + buyer + '\'' +
                    ", changeTime='" + changeTime + '\'' +
                    ", coin=" + coin +
                    ", createTime='" + createTime + '\'' +
                    ", flag=" + flag +
                    ", memId=" + memId +
                    ", money=" + money +
                    ", orderId=" + orderId +
                    ", orderNo='" + orderNo + '\'' +
                    ", payed=" + payed +
                    ", payId=" + payId +
                    ", phone='" + phone + '\'' +
                    ", proxyMoney=" + proxyMoney +
                    ", realMoney=" + realMoney +
                    ", recer='" + recer + '\'' +
                    ", sendMoney=" + sendMoney +
                    ", src=" + src +
                    ", status=" + status +
                    ", swId=" + swId +
                    ", details=" + details +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CreateOrderFormReturn{" +
                "result=" + result +
                ", state=" + state +
                '}';
    }
}
