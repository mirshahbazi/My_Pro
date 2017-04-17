package top.linjia.wizarposapp.beans;

/**
 * Created by 河南巧脉信息技术 on 2017/1/6 11:34
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class VersionInfobean {

    /**
     * state : 1
     * config : {"beginDate":null,"createDate":"2016-12-30 16:55:54.000","endDate":null,"id":14,"introduction":"[{\"version\":\"v1.1.1\",\"desc\":\" 1.解决安装后登陆崩溃的问题。 2.修改商品详情页品牌显示错误的问题。\"},{\"version\":\"v1.1.0\",\"desc\":\"我是版本v1.1.0\"}]","isEnabled":true,"modifyDate":"2016-12-30 16:55:54.000","name":"posVersion","value":"v1.1.1"}
     * desc : 操作成功
     */

    private int state;
    private ResultBean result;
    private String desc;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class ResultBean {
        /**
         * beginDate : null
         * createDate : 2016-12-30 16:55:54.000
         * endDate : null
         * id : 14
         * introduction : [{"version":"v1.1.1","desc":" 1.解决安装后登陆崩溃的问题。 2.修改商品详情页品牌显示错误的问题。"},{"version":"v1.1.0","desc":"我是版本v1.1.0"}]
         * isEnabled : true
         * modifyDate : 2016-12-30 16:55:54.000
         * name : posVersion
         * value : v1.1.1
         */

        private Object beginDate;
        private String createDate;
        private Object endDate;
        private int id;
        private String introduction;
        private boolean isEnabled;
        private String modifyDate;
        private String name;
        private String value;

        public Object getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(Object beginDate) {
            this.beginDate = beginDate;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
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

        public String getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(String modifyDate) {
            this.modifyDate = modifyDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
