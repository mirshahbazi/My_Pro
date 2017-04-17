package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * Created by 邻家新锐 on 2016/10/24 11:17
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class GoodsType {


    @Override
    public String toString() {
        return "GoodsType{" +
                "state=" + state +
                ", result=" + result +
                '}';
    }

    /**
     * result : [{"cateid":"fb552194-6014-4091-824b-7ed04a3f5d74","cname":"全部分类","code":"000","level":0,"parentcode":"","parentid":0},{"cateid":"LJ_001","cname":"饮料","code":"LJ_001","img":"60d59086-320f-411a-95e1-a6690112efc2","level":1,"parentcode":"000","parentid":0},{"cateid":"LJ_002","cname":"休闲食品","code":"LJ_002","img":"96fd0cfc-b529-48ac-8139-a6690112fe91","level":1,"parentcode":"000","parentid":0},{"cateid":"LJ_003","cname":"方便食品","code":"LJ_003","img":"c153293d-6776-484d-982b-a66901130b59","level":1,"parentcode":"000","parentid":0},{"cateid":"LJ_004","cname":"调料","code":"LJ_004","img":"6a5a328c-9747-4e4d-bd32-a6690113173c","level":1,"parentcode":"000","parentid":0},{"cateid":"LJ_005","cname":"纸品日化","code":"LJ_005","img":"8604c5dc-e3a1-418c-967a-a66901132283","level":1,"parentcode":"000","parentid":0},{"cateid":"LJ_006","cname":"百货","code":"LJ_006","img":"34311e2e-d993-4478-9efb-a66901132da4","level":1,"parentcode":"000","parentid":0},{"cateid":"LJ_007","cname":"酒","code":"LJ_007","img":"35bc6e60-c74b-4af8-8bf7-a66901133c9c","level":1,"parentcode":"000","parentid":0},{"cateid":"LJ_008","cname":"邻家特色","code":"LJ_008","img":"bfd19f68-d9c9-41d1-bd08-a66901134b17","level":1,"parentcode":"000","parentid":0},{"cateid":"LJ_009","cname":"冷冻","code":"LJ_009","img":"6ffff480-4e09-4a85-bc1d-a66901135624","level":1,"parentcode":"000","parentid":0},{"cateid":"34462f31-9577-4e9b-921e-a6a201867014","cname":"健康美容","code":"101","level":1,"parentcode":"000","parentid":0},{"cateid":"3b53c18a-903b-488d-acf5-a6a2008f5e40","cname":"啤酒","code":"03","level":1,"parentcode":"000","parentid":0},{"cateid":"4f4b79c8-0191-4224-a0b2-a6a400bb0a0e","cname":"方便食品","code":"102","level":1,"parentcode":"000","parentid":0},{"cateid":"87d63c19-1a2d-44bc-afac-a6a2008ad92e","cname":"软性饮料","code":"01","level":1,"parentcode":"000","parentid":0},{"cateid":"d0808ebf-9dba-4f1a-acd9-a6a2008f077f","cname":"茶/咖啡饮品","code":"02","level":1,"parentcode":"000","parentid":0},{"cateid":"038168d8-4a8d-467b-bacb-a6a20186a15b","cname":"牙膏","code":"201","level":2,"parentcode":"101","parentid":0},{"cateid":"baa02a0c-adf1-4fac-8aa7-a6a20185657b","cname":"康师傅","code":"104","level":2,"parentcode":"01","parentid":0},{"cateid":"ea460528-8672-4068-939a-a6a400bb5b23","cname":"饼干","code":"103","level":2,"parentcode":"102","parentid":0},{"cateid":"eceb00d1-13e8-4252-a852-a6a300005be3","cname":"高露洁","code":"302","level":3,"parentcode":"201","parentid":0},{"cateid":"d545d473-7331-4d81-b75b-a6a30000a45a","cname":"200g","code":"401","level":4,"parentcode":"302","parentid":0}]
     * state : 1
     */

    private int state;
    /**
     * cateid : fb552194-6014-4091-824b-7ed04a3f5d74
     * cname : 全部分类
     * code : 000
     * level : 0
     * parentcode :
     * parentid : 0
     */

    private List<ResultBean> result;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String cateid;
        private String cname;
        private String code;
        private int level;
        private String parentcode;
        private int parentid;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "cateid='" + cateid + '\'' +
                    ", cname='" + cname + '\'' +
                    ", code='" + code + '\'' +
                    ", level=" + level +
                    ", parentcode='" + parentcode + '\'' +
                    ", parentid=" + parentid +
                    '}';
        }

        public String getCateid() {
            return cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getParentcode() {
            return parentcode;
        }

        public void setParentcode(String parentcode) {
            this.parentcode = parentcode;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }
    }
}
