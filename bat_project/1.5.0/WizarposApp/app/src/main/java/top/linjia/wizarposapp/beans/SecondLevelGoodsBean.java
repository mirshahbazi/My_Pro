package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * @className: top.linjia.wizarposapp.beans SecondLevelGoodsBean
 * @description: 二级分类 的分类数据
 * @author 陈文豪
 * @crete 2017/2/16 11:06
 * @copyright: 2017 河南巧脉信息技术有限公司
 * @email firstwenshao@163.com
 */
public class SecondLevelGoodsBean {


    /**
     * content : [{"createDate":null,"modifyDate":null,"creatorId":null,"modifierId":null,"id":"41d5b63c-9f6c-4704-8968-9446525e8096","sellPrice":100,"marketPrice":100,"buyPrice":null,"minOrderQuantity":80,"limitOrderQuantity":90,"sort":null,"stock":213,"productDate":"1970-01-01","state":null,"described":null,"goodsId":null,"shopId":null,"goods":{"createDate":null,"modifyDate":null,"creatorId":null,"modifierId":null,"id":null,"name":"高露洁防蛀牙膏三重功效清爽薄荷 90g*72支（整）","categoryId":null,"brandId":null,"standard":"90g*72支","unit":"箱","packingQuantity":null,"packingUnit":null,"sort":null,"barcode":"16920354836098","skucode":"16920354836098","sizeLength":null,"sizeWidth":null,"sizeHeight":null,"volume":null,"weight":null,"relatedGoodsSkucode":null,"relatedGoodsQuantity":null,"relatedGoods":null,"isImport":null,"goodsPictureList":null,"goodsBrand":null,"goodsCategoryList":null,"imgUrlOfList":null,"cartId":null,"cartNumber":null}},{"createDate":null,"modifyDate":null,"creatorId":null,"modifierId":null,"id":"e79952c6-de31-4280-a542-8125c781da41","sellPrice":1,"marketPrice":1,"buyPrice":null,"minOrderQuantity":1,"limitOrderQuantity":1,"sort":null,"stock":13,"productDate":null,"state":null,"described":null,"goodsId":null,"shopId":null,"goods":{"createDate":null,"modifyDate":null,"creatorId":null,"modifierId":null,"id":null,"name":"高露洁劲白竹炭薄荷180g*48支（整）","categoryId":null,"brandId":null,"standard":"180g*48支","unit":"箱","packingQuantity":null,"packingUnit":null,"sort":null,"barcode":"16920354809917","skucode":"16920354809917","sizeLength":null,"sizeWidth":null,"sizeHeight":null,"volume":null,"weight":null,"relatedGoodsSkucode":null,"relatedGoodsQuantity":null,"relatedGoods":null,"isImport":null,"goodsPictureList":null,"goodsBrand":null,"goodsCategoryList":null,"imgUrlOfList":null,"cartId":"e88700c1-8051-4356-b8c8-1156d60f7bce","cartNumber":1}}]
     * totalElements : 2
     * totalPages : 1
     * last : true
     * number : 0
     * size : 10
     * sort : null
     * numberOfElements : 2
     * first : true
     */

    private int totalElements;
    private int totalPages;
    private boolean last;
    private int number;
    private int size;
    private Object sort;
    private int numberOfElements;
    private boolean first;
    private List<ContentBean> content;

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * createDate : null
         * modifyDate : null
         * creatorId : null
         * modifierId : null
         * id : 41d5b63c-9f6c-4704-8968-9446525e8096
         * sellPrice : 100
         * marketPrice : 100
         * buyPrice : null
         * minOrderQuantity : 80
         * limitOrderQuantity : 90
         * sort : null
         * stock : 213
         * productDate : 1970-01-01
         * state : null
         * described : null
         * goodsId : null
         * shopId : null
         * goods : {"createDate":null,"modifyDate":null,"creatorId":null,"modifierId":null,"id":null,"name":"高露洁防蛀牙膏三重功效清爽薄荷 90g*72支（整）","categoryId":null,"brandId":null,"standard":"90g*72支","unit":"箱","packingQuantity":null,"packingUnit":null,"sort":null,"barcode":"16920354836098","skucode":"16920354836098","sizeLength":null,"sizeWidth":null,"sizeHeight":null,"volume":null,"weight":null,"relatedGoodsSkucode":null,"relatedGoodsQuantity":null,"relatedGoods":null,"isImport":null,"goodsPictureList":null,"goodsBrand":null,"goodsCategoryList":null,"imgUrlOfList":null,"cartId":null,"cartNumber":null}
         */

        private Object createDate;
        private Object modifyDate;
        private Object creatorId;
        private Object modifierId;
        private String id;
        private int sellPrice;
        private int marketPrice;
        private Object buyPrice;
        private int minOrderQuantity;
        private int limitOrderQuantity;
        private Object sort;
        private int stock;
        private String productDate;
        private Object state;
        private Object described;
        private Object goodsId;
        private Object shopId;
        private GoodsBean goods;

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(Object modifyDate) {
            this.modifyDate = modifyDate;
        }

        public Object getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(Object creatorId) {
            this.creatorId = creatorId;
        }

        public Object getModifierId() {
            return modifierId;
        }

        public void setModifierId(Object modifierId) {
            this.modifierId = modifierId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(int sellPrice) {
            this.sellPrice = sellPrice;
        }

        public int getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(int marketPrice) {
            this.marketPrice = marketPrice;
        }

        public Object getBuyPrice() {
            return buyPrice;
        }

        public void setBuyPrice(Object buyPrice) {
            this.buyPrice = buyPrice;
        }

        public int getMinOrderQuantity() {
            return minOrderQuantity;
        }

        public void setMinOrderQuantity(int minOrderQuantity) {
            this.minOrderQuantity = minOrderQuantity;
        }

        public int getLimitOrderQuantity() {
            return limitOrderQuantity;
        }

        public void setLimitOrderQuantity(int limitOrderQuantity) {
            this.limitOrderQuantity = limitOrderQuantity;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getProductDate() {
            return productDate;
        }

        public void setProductDate(String productDate) {
            this.productDate = productDate;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public Object getDescribed() {
            return described;
        }

        public void setDescribed(Object described) {
            this.described = described;
        }

        public Object getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(Object goodsId) {
            this.goodsId = goodsId;
        }

        public Object getShopId() {
            return shopId;
        }

        public void setShopId(Object shopId) {
            this.shopId = shopId;
        }

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * createDate : null
             * modifyDate : null
             * creatorId : null
             * modifierId : null
             * id : null
             * name : 高露洁防蛀牙膏三重功效清爽薄荷 90g*72支（整）
             * categoryId : null
             * brandId : null
             * standard : 90g*72支
             * unit : 箱
             * packingQuantity : null
             * packingUnit : null
             * sort : null
             * barcode : 16920354836098
             * skucode : 16920354836098
             * sizeLength : null
             * sizeWidth : null
             * sizeHeight : null
             * volume : null
             * weight : null
             * relatedGoodsSkucode : null
             * relatedGoodsQuantity : null
             * relatedGoods : null
             * isImport : null
             * goodsPictureList : null
             * goodsBrand : null
             * goodsCategoryList : null
             * imgUrlOfList : null
             * cartId : null
             * cartNumber : null
             */

            private Object createDate;
            private Object modifyDate;
            private Object creatorId;
            private Object modifierId;
            private Object id;
            private String name;
            private Object categoryId;
            private Object brandId;
            private String standard;
            private String unit;
            private Object packingQuantity;
            private Object packingUnit;
            private Object sort;
            private String barcode;
            private String skucode;
            private Object sizeLength;
            private Object sizeWidth;
            private Object sizeHeight;
            private Object volume;
            private Object weight;
            private Object relatedGoodsSkucode;
            private Object relatedGoodsQuantity;
            private Object relatedGoods;
            private Object isImport;
            private Object goodsPictureList;
            private Object goodsBrand;
            private Object goodsCategoryList;
            private Object imgUrlOfList;
            private Object cartId;
            private Object cartNumber;

            public Object getCreateDate() {
                return createDate;
            }

            public void setCreateDate(Object createDate) {
                this.createDate = createDate;
            }

            public Object getModifyDate() {
                return modifyDate;
            }

            public void setModifyDate(Object modifyDate) {
                this.modifyDate = modifyDate;
            }

            public Object getCreatorId() {
                return creatorId;
            }

            public void setCreatorId(Object creatorId) {
                this.creatorId = creatorId;
            }

            public Object getModifierId() {
                return modifierId;
            }

            public void setModifierId(Object modifierId) {
                this.modifierId = modifierId;
            }

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(Object categoryId) {
                this.categoryId = categoryId;
            }

            public Object getBrandId() {
                return brandId;
            }

            public void setBrandId(Object brandId) {
                this.brandId = brandId;
            }

            public String getStandard() {
                return standard;
            }

            public void setStandard(String standard) {
                this.standard = standard;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public Object getPackingQuantity() {
                return packingQuantity;
            }

            public void setPackingQuantity(Object packingQuantity) {
                this.packingQuantity = packingQuantity;
            }

            public Object getPackingUnit() {
                return packingUnit;
            }

            public void setPackingUnit(Object packingUnit) {
                this.packingUnit = packingUnit;
            }

            public Object getSort() {
                return sort;
            }

            public void setSort(Object sort) {
                this.sort = sort;
            }

            public String getBarcode() {
                return barcode;
            }

            public void setBarcode(String barcode) {
                this.barcode = barcode;
            }

            public String getSkucode() {
                return skucode;
            }

            public void setSkucode(String skucode) {
                this.skucode = skucode;
            }

            public Object getSizeLength() {
                return sizeLength;
            }

            public void setSizeLength(Object sizeLength) {
                this.sizeLength = sizeLength;
            }

            public Object getSizeWidth() {
                return sizeWidth;
            }

            public void setSizeWidth(Object sizeWidth) {
                this.sizeWidth = sizeWidth;
            }

            public Object getSizeHeight() {
                return sizeHeight;
            }

            public void setSizeHeight(Object sizeHeight) {
                this.sizeHeight = sizeHeight;
            }

            public Object getVolume() {
                return volume;
            }

            public void setVolume(Object volume) {
                this.volume = volume;
            }

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }

            public Object getRelatedGoodsSkucode() {
                return relatedGoodsSkucode;
            }

            public void setRelatedGoodsSkucode(Object relatedGoodsSkucode) {
                this.relatedGoodsSkucode = relatedGoodsSkucode;
            }

            public Object getRelatedGoodsQuantity() {
                return relatedGoodsQuantity;
            }

            public void setRelatedGoodsQuantity(Object relatedGoodsQuantity) {
                this.relatedGoodsQuantity = relatedGoodsQuantity;
            }

            public Object getRelatedGoods() {
                return relatedGoods;
            }

            public void setRelatedGoods(Object relatedGoods) {
                this.relatedGoods = relatedGoods;
            }

            public Object getIsImport() {
                return isImport;
            }

            public void setIsImport(Object isImport) {
                this.isImport = isImport;
            }

            public Object getGoodsPictureList() {
                return goodsPictureList;
            }

            public void setGoodsPictureList(Object goodsPictureList) {
                this.goodsPictureList = goodsPictureList;
            }

            public Object getGoodsBrand() {
                return goodsBrand;
            }

            public void setGoodsBrand(Object goodsBrand) {
                this.goodsBrand = goodsBrand;
            }

            public Object getGoodsCategoryList() {
                return goodsCategoryList;
            }

            public void setGoodsCategoryList(Object goodsCategoryList) {
                this.goodsCategoryList = goodsCategoryList;
            }

            public Object getImgUrlOfList() {
                return imgUrlOfList;
            }

            public void setImgUrlOfList(Object imgUrlOfList) {
                this.imgUrlOfList = imgUrlOfList;
            }

            public Object getCartId() {
                return cartId;
            }

            public void setCartId(Object cartId) {
                this.cartId = cartId;
            }

            public Object getCartNumber() {
                return cartNumber;
            }

            public void setCartNumber(Object cartNumber) {
                this.cartNumber = cartNumber;
            }
        }
    }
}
