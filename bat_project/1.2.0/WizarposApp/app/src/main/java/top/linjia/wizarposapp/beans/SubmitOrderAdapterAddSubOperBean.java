package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * @ClassName: SubmitOrderAdapterAddSubOperBean
 * @Description: 点击加减号按钮网络请求数据实体类
 * @Data: 2017/1/7 15:22
 * @Author: 李鹏鹏
 */
public class SubmitOrderAdapterAddSubOperBean {

    /**
     * cartItems : [{"cartId":24407,"cartNumber":18,"disDyq":false,"disGwq":false,"discPrice":45.5,"goodsId":443,"goodsNo":"6902538005394","memId":1238,"moq":1,"name":"脉动荔枝味600ML*15","number":121,"path":"upload/images/goods/1611141031687838.jpg","praise":0,"productionDate":"2016-09-19","stname":"600ML*15","unit":"箱"},{"cartId":24406,"cartNumber":10,"disDyq":false,"disGwq":false,"discPrice":50,"goodsId":848,"goodsNo":"6907992512761","memId":1238,"moq":1,"name":"安慕希希腊风味酸奶205G*12","number":537,"path":"upload/images/goods/1612091903615121.jpg","praise":0,"productionDate":"2016-11-03","stname":"205G*12","unit":"提"},{"cartId":24409,"cartNumber":5,"disDyq":false,"disGwq":false,"discPrice":225,"goodsId":1453,"goodsNo":"6935145302815","memId":1238,"moq":1,"name":"5度柠檬味伏特加鸡尾酒275ml*24瓶","number":5,"path":"upload/images/goods/1612081531088382.jpg","praise":0,"productionDate":"2016-08-01","stname":"275ml*24瓶","unit":"箱"},{"cartId":24408,"cartNumber":4,"disDyq":false,"disGwq":false,"discPrice":294,"goodsId":1799,"goodsNo":"6901584197305","memId":1238,"moq":1,"name":"张裕特制干红葡萄酒11.5度（750ml*6瓶）","number":4,"path":"upload/images/goods/1612061908000966.jpg","praise":0,"productionDate":"2015-03-03","stname":"750ml*6瓶","unit":"箱"},{"cartId":24405,"cartNumber":1,"disDyq":false,"disGwq":false,"discPrice":38,"goodsId":1859,"goodsNo":"6948195801405","memId":1238,"moq":1,"name":"元宝牌大豆油5L","number":752,"path":"upload/images/goods/161208872470834.jpg","praise":0,"productionDate":"2016-12-05","stname":"5L","unit":"瓶"},{"cartId":24404,"cartNumber":1,"disDyq":false,"disGwq":false,"discPrice":2.4,"goodsId":1934,"goodsNo":"6903148015834","memId":1238,"moq":1,"name":"佳洁士草本水晶牙膏清爽薄荷香型90g","number":58,"path":"upload/images/goods/161209137343912.jpg","praise":0,"productionDate":"2016-10-24","stname":"90g","unit":"支"},{"cartId":24403,"cartNumber":4,"disDyq":false,"disGwq":false,"discPrice":5.5,"goodsId":1981,"goodsNo":"6951934709061","memId":1238,"moq":2,"name":"龙和香香辣鸡腿80g","number":250,"path":"upload/images/goods/161209887632533.jpg","praise":5,"productionDate":"2016-10-15","stname":"80g","unit":"个"},{"cartId":24402,"cartNumber":6,"disDyq":true,"disGwq":false,"discPrice":1.1,"goodsId":1980,"goodsNo":"6922376015119","memId":1238,"moq":6,"name":"强尔萨回乡特色酱菜40g","number":174,"path":"upload/images/goods/16122084826009.jpg","praise":0,"productionDate":"2016-10-13","stname":"40g","unit":"袋"},{"cartId":24401,"cartNumber":5,"disDyq":false,"disGwq":false,"discPrice":2.5,"goodsId":1978,"goodsNo":"6937100500908","memId":1238,"moq":5,"name":"宁杨炖牛羊肉料25g","number":400,"path":"upload/images/goods/1612091861755371.jpg","praise":0,"productionDate":"2016-10-25","stname":"25g","unit":"包"},{"cartId":24400,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":8,"goodsId":1977,"goodsNo":"6937100500533","memId":1238,"moq":2,"name":"宁杨酸菜鱼调料300g","number":150,"path":"upload/images/goods/161209879276671.jpg","praise":0,"productionDate":"2016-09-05","stname":"300g","unit":"袋"},{"cartId":24399,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":5.5,"goodsId":1976,"goodsNo":"6937100500021","memId":1238,"moq":2,"name":"宁杨古方麻辣火锅清油底料150g","number":291,"path":"upload/images/goods/1612091347386867.jpg","praise":0,"productionDate":"2016-10-25","stname":"150g","unit":"袋"},{"cartId":24398,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":6.5,"goodsId":1975,"goodsNo":"6937100500885","memId":1238,"moq":2,"name":"宁杨清汤火锅底料180g","number":246,"path":"upload/images/goods/1612092018037101.jpg","praise":0,"productionDate":"2016-10-25","stname":"180g","unit":"袋"},{"cartId":24397,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":3.4,"goodsId":1974,"goodsNo":"6937100500076","memId":1238,"moq":2,"name":"宁杨麻婆豆腐调料80g","number":490,"path":"upload/images/goods/1612091375107483.jpg","praise":0,"productionDate":"2016-11-01","stname":"80g","unit":"袋"},{"cartId":24396,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":5.5,"goodsId":1973,"goodsNo":"6937100500243","memId":1238,"moq":2,"name":"宁杨新疆大盘鸡调料150g","number":298,"path":"upload/images/goods/1612091845952299.jpg","praise":0,"productionDate":"2016-10-01","stname":"150g","unit":"袋"},{"cartId":24395,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":5.5,"goodsId":1972,"goodsNo":"6937100500502","memId":1238,"moq":2,"name":"宁杨红焖牛羊肉调料150g","number":298,"path":"upload/images/goods/1612091253898789.jpg","praise":0,"productionDate":"2016-10-15","stname":"150g","unit":"袋"},{"cartId":24394,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":5.5,"goodsId":1971,"goodsNo":"6937100500229","memId":1238,"moq":2,"name":"宁杨古方麻辣鱼调味料150g","number":298,"path":"upload/images/goods/161209323804876.jpg","praise":0,"productionDate":"2016-10-25","stname":"150g","unit":"袋"},{"cartId":24393,"cartNumber":3,"disDyq":false,"disGwq":false,"discPrice":2,"goodsId":1970,"goodsNo":"6922376014099-2","memId":1238,"moq":3,"name":"强尔萨回乡开味菜108g","number":289,"path":"upload/images/goods/161220739063603.jpg","praise":0,"productionDate":"2016-10-04","stname":"108g","unit":"袋"},{"cartId":24378,"cartNumber":5,"disDyq":false,"disGwq":false,"discPrice":2,"goodsId":1969,"goodsNo":"6922376014099-1","memId":1238,"moq":3,"name":"强尔萨回乡杂拌108g","number":285,"path":"upload/images/goods/1612201006450162.jpg","praise":0,"productionDate":"2016-10-30","stname":"108g","unit":"袋"},{"cartId":24392,"cartNumber":3,"disDyq":false,"disGwq":false,"discPrice":2,"goodsId":1968,"goodsNo":"6922376014099","memId":1238,"moq":3,"name":"强尔萨回乡三丁108g","number":289,"path":"upload/images/goods/1612202008928247.jpg","praise":0,"productionDate":"2016-10-22","stname":"108g","unit":"袋"},{"cartId":24377,"cartNumber":6,"disDyq":false,"disGwq":false,"discPrice":10,"goodsId":1967,"goodsNo":"6937100500595","memId":1238,"moq":1,"name":"宁杨辣爆牛肉酱210g","number":120,"path":"upload/images/goods/161209853386812.jpg","praise":0,"productionDate":"2016-10-25","stname":"210g","unit":"瓶"},{"cartId":24376,"cartNumber":4,"disDyq":false,"disGwq":false,"discPrice":40,"goodsId":1965,"goodsNo":"6931099014019","memId":1238,"moq":1,"name":"塞外香糙米米汁240ml*12罐","number":187,"path":"upload/images/goods/161211362482559.jpg","praise":0,"productionDate":"2016-11-01","stname":"240ml*12罐","unit":"箱"},{"cartId":24375,"cartNumber":4,"disDyq":false,"disGwq":false,"discPrice":118,"goodsId":1964,"goodsNo":"6901035613019","memId":1238,"moq":1,"name":"青岛啤酒鸿运当头355ml*12瓶","number":100,"path":"upload/images/goods/1612091060890663.jpg","praise":0,"productionDate":"2016-12-20","stname":"355ml*12瓶","unit":"箱"}]
     * coupons : [{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"200元购物券","code":"af9ea544-a30a-4cea-9eb0-dcf949541cf5","couponId":13,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14110,"introduction":"200元购物券： 满20种商品且满3600元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":200,"minimumQuantity":3600,"modifyDate":"2017-01-06 16:22:19.000","point":200,"prefix":"g","skuNum":20,"source":0,"type":2},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"20元购物券","code":"40123d09-b889-46b8-83dd-021da47cf22f","couponId":10,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14105,"introduction":"20元购物券： 满1种商品且满500元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":20,"minimumQuantity":500,"modifyDate":"2017-01-06 16:22:19.000","point":20,"prefix":"g","skuNum":0,"source":0,"type":2}]
     * linjiaMoney : 440
     * money : 4495.3
     * payBylinjiaMoney : 440
     * payMoney : 3835.3
     * sendMoney : 0
     */

    private ResultBean result;
    /**
     * result : {"cartItems":[{"cartId":24407,"cartNumber":18,"disDyq":false,"disGwq":false,"discPrice":45.5,"goodsId":443,"goodsNo":"6902538005394","memId":1238,"moq":1,"name":"脉动荔枝味600ML*15","number":121,"path":"upload/images/goods/1611141031687838.jpg","praise":0,"productionDate":"2016-09-19","stname":"600ML*15","unit":"箱"},{"cartId":24406,"cartNumber":10,"disDyq":false,"disGwq":false,"discPrice":50,"goodsId":848,"goodsNo":"6907992512761","memId":1238,"moq":1,"name":"安慕希希腊风味酸奶205G*12","number":537,"path":"upload/images/goods/1612091903615121.jpg","praise":0,"productionDate":"2016-11-03","stname":"205G*12","unit":"提"},{"cartId":24409,"cartNumber":5,"disDyq":false,"disGwq":false,"discPrice":225,"goodsId":1453,"goodsNo":"6935145302815","memId":1238,"moq":1,"name":"5度柠檬味伏特加鸡尾酒275ml*24瓶","number":5,"path":"upload/images/goods/1612081531088382.jpg","praise":0,"productionDate":"2016-08-01","stname":"275ml*24瓶","unit":"箱"},{"cartId":24408,"cartNumber":4,"disDyq":false,"disGwq":false,"discPrice":294,"goodsId":1799,"goodsNo":"6901584197305","memId":1238,"moq":1,"name":"张裕特制干红葡萄酒11.5度（750ml*6瓶）","number":4,"path":"upload/images/goods/1612061908000966.jpg","praise":0,"productionDate":"2015-03-03","stname":"750ml*6瓶","unit":"箱"},{"cartId":24405,"cartNumber":1,"disDyq":false,"disGwq":false,"discPrice":38,"goodsId":1859,"goodsNo":"6948195801405","memId":1238,"moq":1,"name":"元宝牌大豆油5L","number":752,"path":"upload/images/goods/161208872470834.jpg","praise":0,"productionDate":"2016-12-05","stname":"5L","unit":"瓶"},{"cartId":24404,"cartNumber":1,"disDyq":false,"disGwq":false,"discPrice":2.4,"goodsId":1934,"goodsNo":"6903148015834","memId":1238,"moq":1,"name":"佳洁士草本水晶牙膏清爽薄荷香型90g","number":58,"path":"upload/images/goods/161209137343912.jpg","praise":0,"productionDate":"2016-10-24","stname":"90g","unit":"支"},{"cartId":24403,"cartNumber":4,"disDyq":false,"disGwq":false,"discPrice":5.5,"goodsId":1981,"goodsNo":"6951934709061","memId":1238,"moq":2,"name":"龙和香香辣鸡腿80g","number":250,"path":"upload/images/goods/161209887632533.jpg","praise":5,"productionDate":"2016-10-15","stname":"80g","unit":"个"},{"cartId":24402,"cartNumber":6,"disDyq":true,"disGwq":false,"discPrice":1.1,"goodsId":1980,"goodsNo":"6922376015119","memId":1238,"moq":6,"name":"强尔萨回乡特色酱菜40g","number":174,"path":"upload/images/goods/16122084826009.jpg","praise":0,"productionDate":"2016-10-13","stname":"40g","unit":"袋"},{"cartId":24401,"cartNumber":5,"disDyq":false,"disGwq":false,"discPrice":2.5,"goodsId":1978,"goodsNo":"6937100500908","memId":1238,"moq":5,"name":"宁杨炖牛羊肉料25g","number":400,"path":"upload/images/goods/1612091861755371.jpg","praise":0,"productionDate":"2016-10-25","stname":"25g","unit":"包"},{"cartId":24400,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":8,"goodsId":1977,"goodsNo":"6937100500533","memId":1238,"moq":2,"name":"宁杨酸菜鱼调料300g","number":150,"path":"upload/images/goods/161209879276671.jpg","praise":0,"productionDate":"2016-09-05","stname":"300g","unit":"袋"},{"cartId":24399,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":5.5,"goodsId":1976,"goodsNo":"6937100500021","memId":1238,"moq":2,"name":"宁杨古方麻辣火锅清油底料150g","number":291,"path":"upload/images/goods/1612091347386867.jpg","praise":0,"productionDate":"2016-10-25","stname":"150g","unit":"袋"},{"cartId":24398,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":6.5,"goodsId":1975,"goodsNo":"6937100500885","memId":1238,"moq":2,"name":"宁杨清汤火锅底料180g","number":246,"path":"upload/images/goods/1612092018037101.jpg","praise":0,"productionDate":"2016-10-25","stname":"180g","unit":"袋"},{"cartId":24397,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":3.4,"goodsId":1974,"goodsNo":"6937100500076","memId":1238,"moq":2,"name":"宁杨麻婆豆腐调料80g","number":490,"path":"upload/images/goods/1612091375107483.jpg","praise":0,"productionDate":"2016-11-01","stname":"80g","unit":"袋"},{"cartId":24396,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":5.5,"goodsId":1973,"goodsNo":"6937100500243","memId":1238,"moq":2,"name":"宁杨新疆大盘鸡调料150g","number":298,"path":"upload/images/goods/1612091845952299.jpg","praise":0,"productionDate":"2016-10-01","stname":"150g","unit":"袋"},{"cartId":24395,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":5.5,"goodsId":1972,"goodsNo":"6937100500502","memId":1238,"moq":2,"name":"宁杨红焖牛羊肉调料150g","number":298,"path":"upload/images/goods/1612091253898789.jpg","praise":0,"productionDate":"2016-10-15","stname":"150g","unit":"袋"},{"cartId":24394,"cartNumber":2,"disDyq":false,"disGwq":false,"discPrice":5.5,"goodsId":1971,"goodsNo":"6937100500229","memId":1238,"moq":2,"name":"宁杨古方麻辣鱼调味料150g","number":298,"path":"upload/images/goods/161209323804876.jpg","praise":0,"productionDate":"2016-10-25","stname":"150g","unit":"袋"},{"cartId":24393,"cartNumber":3,"disDyq":false,"disGwq":false,"discPrice":2,"goodsId":1970,"goodsNo":"6922376014099-2","memId":1238,"moq":3,"name":"强尔萨回乡开味菜108g","number":289,"path":"upload/images/goods/161220739063603.jpg","praise":0,"productionDate":"2016-10-04","stname":"108g","unit":"袋"},{"cartId":24378,"cartNumber":5,"disDyq":false,"disGwq":false,"discPrice":2,"goodsId":1969,"goodsNo":"6922376014099-1","memId":1238,"moq":3,"name":"强尔萨回乡杂拌108g","number":285,"path":"upload/images/goods/1612201006450162.jpg","praise":0,"productionDate":"2016-10-30","stname":"108g","unit":"袋"},{"cartId":24392,"cartNumber":3,"disDyq":false,"disGwq":false,"discPrice":2,"goodsId":1968,"goodsNo":"6922376014099","memId":1238,"moq":3,"name":"强尔萨回乡三丁108g","number":289,"path":"upload/images/goods/1612202008928247.jpg","praise":0,"productionDate":"2016-10-22","stname":"108g","unit":"袋"},{"cartId":24377,"cartNumber":6,"disDyq":false,"disGwq":false,"discPrice":10,"goodsId":1967,"goodsNo":"6937100500595","memId":1238,"moq":1,"name":"宁杨辣爆牛肉酱210g","number":120,"path":"upload/images/goods/161209853386812.jpg","praise":0,"productionDate":"2016-10-25","stname":"210g","unit":"瓶"},{"cartId":24376,"cartNumber":4,"disDyq":false,"disGwq":false,"discPrice":40,"goodsId":1965,"goodsNo":"6931099014019","memId":1238,"moq":1,"name":"塞外香糙米米汁240ml*12罐","number":187,"path":"upload/images/goods/161211362482559.jpg","praise":0,"productionDate":"2016-11-01","stname":"240ml*12罐","unit":"箱"},{"cartId":24375,"cartNumber":4,"disDyq":false,"disGwq":false,"discPrice":118,"goodsId":1964,"goodsNo":"6901035613019","memId":1238,"moq":1,"name":"青岛啤酒鸿运当头355ml*12瓶","number":100,"path":"upload/images/goods/1612091060890663.jpg","praise":0,"productionDate":"2016-12-20","stname":"355ml*12瓶","unit":"箱"}],"coupons":[{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"200元购物券","code":"af9ea544-a30a-4cea-9eb0-dcf949541cf5","couponId":13,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14110,"introduction":"200元购物券： 满20种商品且满3600元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":200,"minimumQuantity":3600,"modifyDate":"2017-01-06 16:22:19.000","point":200,"prefix":"g","skuNum":20,"source":0,"type":2},{"lockDate":1,"maximumPoint":1,"maximumQuantity":1,"mid":1,"orderId":1,"pushTicketId":1,"sno":1,"usedDate":1,"NAME":"20元购物券","code":"40123d09-b889-46b8-83dd-021da47cf22f","couponId":10,"createDate":"2017-01-06 16:22:19.000","expiryDate":"2017-02-04 23:59:59.000","hasSendYzd":false,"id":14105,"introduction":"20元购物券： 满1种商品且满500元可用","isEnabled":true,"isUsed":0,"memberId":1238,"minimumPoint":20,"minimumQuantity":500,"modifyDate":"2017-01-06 16:22:19.000","point":20,"prefix":"g","skuNum":0,"source":0,"type":2}],"linjiaMoney":440,"money":4495.3,"payBylinjiaMoney":440,"payMoney":3835.3,"sendMoney":0}
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
        private double linjiaMoney;
        private double money;
        private double payBylinjiaMoney;
        private double payMoney;
        private double sendMoney;
        /**
         * cartId : 24407
         * cartNumber : 18
         * disDyq : false
         * disGwq : false
         * discPrice : 45.5
         * goodsId : 443
         * goodsNo : 6902538005394
         * memId : 1238
         * moq : 1
         * name : 脉动荔枝味600ML*15
         * number : 121
         * path : upload/images/goods/1611141031687838.jpg
         * praise : 0
         * productionDate : 2016-09-19
         * stname : 600ML*15
         * unit : 箱
         */

        private List<CartItemsBean> cartItems;
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

        private List<CouponsBean> coupons;

        public double getLinjiaMoney() {
            return linjiaMoney;
        }

        public void setLinjiaMoney(double linjiaMoney) {
            this.linjiaMoney = linjiaMoney;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public double getPayBylinjiaMoney() {
            return payBylinjiaMoney;
        }

        public void setPayBylinjiaMoney(double payBylinjiaMoney) {
            this.payBylinjiaMoney = payBylinjiaMoney;
        }

        public double getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(double payMoney) {
            this.payMoney = payMoney;
        }

        public double getSendMoney() {
            return sendMoney;
        }

        public void setSendMoney(double sendMoney) {
            this.sendMoney = sendMoney;
        }

        public List<CartItemsBean> getCartItems() {
            return cartItems;
        }

        public void setCartItems(List<CartItemsBean> cartItems) {
            this.cartItems = cartItems;
        }

        public List<CouponsBean> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<CouponsBean> coupons) {
            this.coupons = coupons;
        }

        public static class CartItemsBean {
            private int cartId;
            private int cartNumber;
            private boolean disDyq;
            private boolean disGwq;
            private double discPrice;
            private int goodsId;
            private String goodsNo;
            private int memId;
            private int moq;
            private String name;
            private int number;
            private String path;
            private int praise;
            private String productionDate;
            private String stname;
            private String unit;

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

            public double getDiscPrice() {
                return discPrice;
            }

            public void setDiscPrice(double discPrice) {
                this.discPrice = discPrice;
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

            public int getMemId() {
                return memId;
            }

            public void setMemId(int memId) {
                this.memId = memId;
            }

            public int getMoq() {
                return moq;
            }

            public void setMoq(int moq) {
                this.moq = moq;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getStname() {
                return stname;
            }

            public void setStname(String stname) {
                this.stname = stname;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }
        }

        public static class CouponsBean {
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
