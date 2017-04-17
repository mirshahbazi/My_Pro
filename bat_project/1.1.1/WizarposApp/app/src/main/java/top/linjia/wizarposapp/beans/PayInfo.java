package top.linjia.wizarposapp.beans;

import java.util.List;

/**
 * Created by 河南巧脉信息技术 on 2016/11/16 14:21
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 */
public class PayInfo {

    /**
     * out_trade_no : 2016111800428
     * terminal_no : WP14521000000010
     * pay_channel : A
     * auth_code : 282471907103551052
     * couponIds : []
     */

    private String out_trade_no;
    private String terminal_no;
    private String pay_channel;
    private String auth_code;
    private List<?> couponIds;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTerminal_no() {
        return terminal_no;
    }

    public void setTerminal_no(String terminal_no) {
        this.terminal_no = terminal_no;
    }

    public String getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public List<?> getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(List<?> couponIds) {
        this.couponIds = couponIds;
    }
}
