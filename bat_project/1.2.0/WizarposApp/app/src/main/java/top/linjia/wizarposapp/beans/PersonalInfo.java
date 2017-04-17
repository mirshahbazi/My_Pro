package top.linjia.wizarposapp.beans;

/**
 * Created by 邻家新锐 on 2016/10/21 13:45
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 * 个人信息的bean类
 */
public class PersonalInfo {

    /**
     * address : null
     * answer : null
     * bankAcct : null
     * bankName : null
     * birthday : null
     * brandId : null
     * cardNo : null
     * cardType : null
     * cent : null
     * city : null
     * demand : null
     * email : lipengpeng
     * hordinate : 117.574633
     * huiyinMid : null
     * id : 1252
     * income : 0
     * lastIp : 127.0.0.1
     * lastTime : 2016-11-17 14:53:43
     * loginCount : 313
     * loginer : lipengpeng
     * maxim : null
     * maxRate : 0.9
     * money : 0
     * openId : null
     * organ : null
     * parentId : 0
     * password : e10adc3949ba59abbe56e057f20f883e
     * payId : 0
     * phone : 13121231212
     * pic : null
     * postno : null
     * praise : 0
     * project : null
     * prov : null
     * proxy : null
     * qq : null
     * qr : null
     * question : null
     * region : null
     * regTime : null
     * sendMoney : 0
     * sex : false
     * skill : null
     * store : null
     * suppId : null
     * suppqr : null
     * swId : null
     * tel : null
     * uname : 李鹏鹏_测试
     * utype : 1
     * vordinate : 32.921498
     * wxno : null
     * yzdCardNum : null
     * yzdPosSN : null
     * yzdRetailerNo : null
     * yzdUserID : null
     */

    private ResultBean result;
    /**
     * result : {"address":null,"answer":null,"bankAcct":null,"bankName":null,"birthday":null,"brandId":null,"cardNo":null,"cardType":null,"cent":null,"city":null,"demand":null,"email":"lipengpeng","hordinate":"117.574633","huiyinMid":null,"id":1252,"income":0,"lastIp":"127.0.0.1","lastTime":"2016-11-17 14:53:43","loginCount":313,"loginer":"lipengpeng","maxim":null,"maxRate":0.9,"money":0,"openId":null,"organ":null,"parentId":0,"password":"e10adc3949ba59abbe56e057f20f883e","payId":0,"phone":"13121231212","pic":null,"postno":null,"praise":0,"project":null,"prov":null,"proxy":null,"qq":null,"qr":null,"question":null,"region":null,"regTime":null,"sendMoney":0,"sex":false,"skill":null,"store":null,"suppId":null,"suppqr":null,"swId":null,"tel":null,"uname":"李鹏鹏_测试","utype":1,"vordinate":"32.921498","wxno":null,"yzdCardNum":null,"yzdPosSN":null,"yzdRetailerNo":null,"yzdUserID":null}
     * appToken : b3dd44cf3edc411bbc750b6d0454a7ac
     * state : 1
     * desc :
     */

    private String appToken;
    private int state;
    private String desc;

    public int getCartGoodsNum() {
        return cartGoodsNum;
    }

    public void setCartGoodsNum(int cartGoodsNum) {
        this.cartGoodsNum = cartGoodsNum;
    }

    private int cartGoodsNum;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class ResultBean {
        private String address;
        private String answer;
        private String bankAcct;
        private String bankName;
        private String birthday;
        private String brandId;
        private String cardNo;
        private String cardType;
        private String cent;
        private String city;
        private String demand;
        private String email;
        private String hordinate;
        private String huiyinMid;
        private int id;
        private int income;
        private String lastIp;
        private String lastTime;
        private int loginCount;
        private String loginer;
        private String maxim;
        private double maxRate;
        private int money;
        private String openId;
        private String organ;
        private int parentId;
        private String password;
        private int payId;
        private String phone;
        private String pic;
        private String postno;
        private int praise;
        private String project;
        private String prov;
        private String proxy;
        private String qq;
        private String qr;
        private String question;
        private String region;
        private String regTime;
        private int sendMoney;
        private boolean sex;
        private String skill;
        private String store;
        private String suppId;
        private String suppqr;
        private String swId;
        private String tel;
        private String uname;
        private int utype;
        private String vordinate;
        private String wxno;
        private String yzdCardNum;
        private String yzdPosSN;
        private String yzdRetailerNo;
        private String yzdUserID;



        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getBankAcct() {
            return bankAcct;
        }

        public void setBankAcct(String bankAcct) {
            this.bankAcct = bankAcct;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCent() {
            return cent;
        }

        public void setCent(String cent) {
            this.cent = cent;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDemand() {
            return demand;
        }

        public void setDemand(String demand) {
            this.demand = demand;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHordinate() {
            return hordinate;
        }

        public void setHordinate(String hordinate) {
            this.hordinate = hordinate;
        }

        public String getHuiyinMid() {
            return huiyinMid;
        }

        public void setHuiyinMid(String huiyinMid) {
            this.huiyinMid = huiyinMid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIncome() {
            return income;
        }

        public void setIncome(int income) {
            this.income = income;
        }

        public String getLastIp() {
            return lastIp;
        }

        public void setLastIp(String lastIp) {
            this.lastIp = lastIp;
        }

        public String getLastTime() {
            return lastTime;
        }

        public void setLastTime(String lastTime) {
            this.lastTime = lastTime;
        }

        public int getLoginCount() {
            return loginCount;
        }

        public void setLoginCount(int loginCount) {
            this.loginCount = loginCount;
        }

        public String getLoginer() {
            return loginer;
        }

        public void setLoginer(String loginer) {
            this.loginer = loginer;
        }

        public String getMaxim() {
            return maxim;
        }

        public void setMaxim(String maxim) {
            this.maxim = maxim;
        }

        public double getMaxRate() {
            return maxRate;
        }

        public void setMaxRate(double maxRate) {
            this.maxRate = maxRate;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getOrgan() {
            return organ;
        }

        public void setOrgan(String organ) {
            this.organ = organ;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPostno() {
            return postno;
        }

        public void setPostno(String postno) {
            this.postno = postno;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public String getProxy() {
            return proxy;
        }

        public void setProxy(String proxy) {
            this.proxy = proxy;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getQr() {
            return qr;
        }

        public void setQr(String qr) {
            this.qr = qr;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public int getSendMoney() {
            return sendMoney;
        }

        public void setSendMoney(int sendMoney) {
            this.sendMoney = sendMoney;
        }

        public boolean isSex() {
            return sex;
        }

        public void setSex(boolean sex) {
            this.sex = sex;
        }

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getSuppId() {
            return suppId;
        }

        public void setSuppId(String suppId) {
            this.suppId = suppId;
        }

        public String getSuppqr() {
            return suppqr;
        }

        public void setSuppqr(String suppqr) {
            this.suppqr = suppqr;
        }

        public String getSwId() {
            return swId;
        }

        public void setSwId(String swId) {
            this.swId = swId;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public int getUtype() {
            return utype;
        }

        public void setUtype(int utype) {
            this.utype = utype;
        }

        public String getVordinate() {
            return vordinate;
        }

        public void setVordinate(String vordinate) {
            this.vordinate = vordinate;
        }

        public String getWxno() {
            return wxno;
        }

        public void setWxno(String wxno) {
            this.wxno = wxno;
        }

        public String getYzdCardNum() {
            return yzdCardNum;
        }

        public void setYzdCardNum(String yzdCardNum) {
            this.yzdCardNum = yzdCardNum;
        }

        public String getYzdPosSN() {
            return yzdPosSN;
        }

        public void setYzdPosSN(String yzdPosSN) {
            this.yzdPosSN = yzdPosSN;
        }

        public String getYzdRetailerNo() {
            return yzdRetailerNo;
        }

        public void setYzdRetailerNo(String yzdRetailerNo) {
            this.yzdRetailerNo = yzdRetailerNo;
        }

        public String getYzdUserID() {
            return yzdUserID;
        }

        public void setYzdUserID(String yzdUserID) {
            this.yzdUserID = yzdUserID;
        }
    }
}
