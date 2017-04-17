package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * Created by 河南巧脉信息技术 on 2016/11/11 11:27
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class Card {

    /**
     * list : [{"endDate":"2017-01-28 23:59:59.000","isUsed":0,"memberId":292,"name":"100元购物券","point":100,"prefix":"g"},{"endDate":"2017-01-28 23:59:59.000","isUsed":0,"memberId":292,"name":"50元购物券","point":50,"prefix":"g"},{"isUsed":0,"memberId":292,"name":"50元抵用券","point":50,"prefix":"d"}]
     * pageNumber : 1
     * pageSize : 10
     * totalPage : 1
     * totalRow : 3
     */

    private ResultBean result;
    /**
     * result : {"list":[{"endDate":"2017-01-28 23:59:59.000","isUsed":0,"memberId":292,"name":"100元购物券","point":100,"prefix":"g"},{"endDate":"2017-01-28 23:59:59.000","isUsed":0,"memberId":292,"name":"50元购物券","point":50,"prefix":"g"},{"isUsed":0,"memberId":292,"name":"50元抵用券","point":50,"prefix":"d"}],"pageNumber":1,"pageSize":10,"totalPage":1,"totalRow":3}
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
         * endDate : 2017-01-28 23:59:59.000
         * isUsed : 0
         * memberId : 292
         * name : 100元购物券
         * point : 100
         * prefix : g
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
            private String endDate;
            private int isUsed;
            private int memberId;
            private String name;
            private int point;
            private String prefix;

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
        }
    }
}
